#!/usr/bin/env python3
"""This is main runner of generator

"""
import datetime
import logging
import re
import sys
from argparse import ArgumentParser
from collections import namedtuple, OrderedDict
from inspect import getfile
from os.path import basename
from pprint import pformat
from time import sleep
from xml.etree.ElementTree import ParseError as XMLSchemaError

from jinja2 import Environment, FileSystemLoader, TemplateNotFound, UndefinedError
from pathlib2 import Path
from xmlschema import XMLSchema

ROOT = Path(__file__).absolute().parents[0]

sys.path.append(ROOT.joinpath('rpc_spec/InterfaceParser').as_posix())

try:
    from parsers.sdl_rpc_v2 import Parser
    from parsers.parse_error import ParseError as InterfaceError
    from model.interface import Interface
    from transformers.generate_error import GenerateError
    from transformers.common_producer import InterfaceProducerCommon
    from transformers.enums_producer import EnumsProducer
    from transformers.functions_producer import FunctionsProducer
    from transformers.structs_producer import StructsProducer
except ImportError as message:
    print('{}. probably you did not initialize submodule'.format(message))
    sys.exit(1)


class Generator:
    """
    This class contains only technical features, as follow:
    - parsing command-line arguments, or evaluating required Paths interactively;
    - calling parsers to get Model from xml;
    - calling producers to transform initial Model to dict used in jinja2 templates
    Not required to be covered by unit tests cause contains only technical features.
    """

    def __init__(self):
        self.logger = logging.getLogger(self.__class__.__name__)
        self._env = None

    @property
    def env(self):
        """
        :return: jinja2 Environment
        """
        return self._env

    @env.setter
    def env(self, value):
        """
        :param value: path with directory with templates
        :return: jinja2 Environment
        """
        if not Path(value).exists():
            self.logger.critical('Directory with templates not found %s', value)
            sys.exit(1)
        else:
            self._env = Environment(loader=FileSystemLoader(value))

    @property
    def get_version(self):
        """
        :return: current version of Generator
        """
        return InterfaceProducerCommon.version

    def config_logging(self, verbose):
        """
        Configure logging
        :param verbose: boolean
        """
        handler = logging.StreamHandler()
        handler.setFormatter(logging.Formatter(fmt='%(asctime)s - %(name)s - %(levelname)s - %(message)s',
                                               datefmt='%m-%d %H:%M'))
        if verbose:
            handler.setLevel(logging.DEBUG)
            self.logger.setLevel(logging.DEBUG)
        else:
            handler.setLevel(logging.ERROR)
            self.logger.setLevel(logging.ERROR)
        logging.getLogger().handlers.clear()
        root_logger = logging.getLogger()
        root_logger.addHandler(handler)

    def evaluate_source_xml_xsd(self, xml, xsd):
        """
        :param xml: path to MOBILE_API.xml file
        :param xsd: path to .xsd file (optional)
        :return: validated path to .xsd file
        """
        if not Path(xml).exists():
            self.logger.critical('File not found: %s', xml)
            sys.exit(1)

        if xsd and Path(xsd).exists():
            return xsd

        replace = xml.replace('.xml', '.xsd')
        if xsd and not Path(xsd).exists():
            self.logger.critical('File not found: %s', xsd)
            sys.exit(1)
        elif not xsd and not Path(replace).exists():
            self.logger.critical('File not found: %s', replace)
            sys.exit(1)
        else:
            return replace

    def evaluate_output_directory(self, output_directory):
        """
        :param output_directory: path to output_directory
        :return: validated path to output_directory
        """
        if output_directory.startswith('/'):
            path = Path(output_directory).absolute().resolve()
        else:
            path = ROOT.joinpath(output_directory).resolve()
        if not path.exists():
            self.logger.warning('Directory not found: %s, trying to create it', path)
            try:
                path.mkdir(parents=True, exist_ok=True)
            except OSError as message1:
                self.logger.critical('Failed to create directory %s, %s', path.as_posix(), message1)
                sys.exit(1)
        return path

    def get_parser(self):
        """
        Parsing command-line arguments, or evaluating required Paths interactively.
        :return: an instance of argparse.ArgumentParser
        """

        if len(sys.argv) == 2 and sys.argv[1] in ('-v', '--version'):
            print(self.get_version)
            sys.exit(0)

        Paths = namedtuple('Paths', 'name path')
        xml = Paths('source_xml', ROOT.joinpath('rpc_spec/MOBILE_API.xml'))
        required_source = not xml.path.exists()

        out = Paths('output_directory', ROOT.parents[0].joinpath('base/src/main/java/'))
        output_required = not out.path.exists()

        parser = ArgumentParser(description='Proxy Library RPC Generator')
        parser.add_argument('-v', '--version', action='store_true', help='print the version and exit')
        parser.add_argument('-xml', '--source-xml', '--input-file', required=required_source,
                            help='should point to MOBILE_API.xml')
        parser.add_argument('-xsd', '--source-xsd', required=False)
        parser.add_argument('-d', '--output-directory', required=output_required,
                            help='define the place where the generated output should be placed')
        parser.add_argument('-t', '--templates-directory', nargs='?', default=ROOT.joinpath('templates').as_posix(),
                            help='path to directory with templates')
        parser.add_argument('-r', '--regex-pattern', required=False,
                            help='only elements matched with defined regex pattern will be parsed and generated')
        parser.add_argument('--verbose', action='store_true', help='display additional details like logs etc')
        parser.add_argument('-e', '--enums', required=False, action='store_true',
                            help='only specified elements will be generated, if present')
        parser.add_argument('-s', '--structs', required=False, action='store_true',
                            help='only specified elements will be generated, if present')
        parser.add_argument('-m', '-f', '--functions', required=False, action='store_true',
                            help='only specified elements will be generated, if present')
        parser.add_argument('-y', '--overwrite', action='store_true',
                            help='force overwriting of existing files in output directory, ignore confirmation message')
        parser.add_argument('-n', '--skip', action='store_true',
                            help='skip overwriting of existing files in output directory, ignore confirmation message')

        args, unknown = parser.parse_known_args()

        if unknown:
            self.logger.critical('found unknown arguments: %s', ' '.join(unknown))
            parser.print_help(sys.stderr)
            sys.exit(1)

        if args.skip and args.overwrite:
            self.logger.critical('please select only one option skip or overwrite')
            sys.exit(1)

        if not args.enums and not args.structs and not args.functions:
            args.enums = args.structs = args.functions = True

        for intermediate in (xml, out):
            if not getattr(args, intermediate.name) and intermediate.path.exists():
                while True:
                    try:
                        confirm = input('Confirm default path {} for {} Y/Enter = yes, N = no'
                                        .format(intermediate.path, intermediate.name))
                        if confirm.lower() == 'y' or not confirm:
                            self.logger.warning('%s set to %s', intermediate.name, intermediate.path)
                            setattr(args, intermediate.name, intermediate.path.as_posix())
                            sleep(0.05)
                            break
                        if confirm.lower() == 'n':
                            self.logger.warning('provide argument %s', intermediate.name)
                            sys.exit(1)
                    except KeyboardInterrupt:
                        print('\nThe user interrupted the execution of the program')
                        sys.exit(1)

        self.config_logging(args.verbose)

        args.source_xsd = self.evaluate_source_xml_xsd(args.source_xml, args.source_xsd)

        args.output_directory = self.evaluate_output_directory(args.output_directory)

        self.env = args.templates_directory

        self.logger.info('parsed arguments:\n%s', pformat((vars(args))))
        return args

    def versions_compatibility_validating(self):
        """version of generator script requires the same or lesser version of parser script.
        if the parser script needs to fix a bug (and becomes, e.g. 1.0.1) and the generator script stays at 1.0.0.
        As long as the generator script is the same or greater major version, it should be parsable.
        This requires some level of backward compatibility. E.g. they have to be the same major version.

        """

        regex = r'(\d+\.\d+).(\d)'

        parser_origin = Parser().get_version
        parser_split = re.findall(regex, parser_origin).pop()
        generator_split = re.findall(regex, self.get_version).pop()

        parser_major = float(parser_split[0])
        generator_major = float(generator_split[0])

        if parser_major > generator_major:
            self.logger.critical('Generator (%s) requires the same or lesser version of Parser (%s)',
                                 self.get_version, parser_origin)
            sys.exit(1)

        self.logger.info('Parser type: %s, version %s,\tGenerator version %s',
                         basename(getfile(Parser().__class__)), parser_origin, self.get_version)

    def get_file_content(self, file_name: Path) -> list:
        """

        :param file_name:
        :return:
        """
        try:
            with file_name.open('r') as file:
                content = file.readlines()
            return content
        except FileNotFoundError as message1:
            self.logger.error(message1)
            return []

    def get_key_words(self, file_name=ROOT.joinpath('rpc_spec/RpcParser/RESERVED_KEYWORDS')):
        """
        :param file_name:
        :return:
        """
        content = self.get_file_content(file_name)
        content = tuple(map(lambda e: re.sub(r'\n', r'', e).strip(), content))
        try:
            content = tuple(filter(lambda e: not re.search(r'^#+\s+.+|^$', e), content))
            self.logger.debug('key_words: %s', ', '.join(content))
            return content
        except (IndexError, ValueError, StopIteration) as error1:
            self.logger.error('Error while getting key_words, %s %s', type(error1).__name__, error1)
            return []

    def get_paths(self, file_name=ROOT.joinpath('paths.ini')):
        """
        :param file_name: path to file with Paths
        :return: namedtuple with Paths to key elements
        """
        fields = ('struct_class', 'request_class', 'response_class',
                  'notification_class', 'enums_package', 'structs_package', 'functions_package')
        data = OrderedDict()
        content = self.get_file_content(file_name)

        for line in content:
            if line.startswith('#'):
                self.logger.warning('commented property %s, which will be skipped', line.strip())
                continue
            if re.match(r'^(\w+)\s?=\s?(.+)', line):
                if len(line.split('=')) > 2:
                    self.logger.critical('can not evaluate value, too many separators %s', str(line))
                    sys.exit(1)
                name, var = line.partition('=')[::2]
                if name.strip() in data:
                    self.logger.critical('duplicate key %s', name)
                    sys.exit(1)
                data[name.strip().lower()] = var.strip()

        for line in fields:
            if line not in data:
                self.logger.critical('in %s missed fields: %s ', content, str(line))
                sys.exit(1)

        Paths = namedtuple('Paths', ' '.join(fields))
        return Paths(**data)

    def write_file(self, file_name, template, data):
        """
        Calling producer/transformer instance to transform initial Model to dict used in jinja2 templates.
        Applying transformed dict to jinja2 templates and writing to appropriate file
        :param file_name: output java file
        :param template: name of template
        :param data: transformed model ready for apply to Jinja2 template
        """
        file_name.parents[0].mkdir(parents=True, exist_ok=True)
        try:
            render = self.env.get_template(template).render(data)
            with file_name.open('w', encoding='utf-8') as file:
                file.write(render)
        except (TemplateNotFound, UndefinedError) as message1:
            self.logger.error('skipping %s, template not found %s', file_name.as_posix(), message1)

    def process(self, directory, skip, overwrite, items, transformer):
        """
        Process each item from initial Model. According to provided arguments skipping, overriding or asking what to to.
        :param directory: output directory for writing output files
        :param skip: if file exist skip it
        :param overwrite: if file exist overwrite it
        :param items: elements initial Model
        :param transformer: producer/transformer instance
        """

        directory.mkdir(parents=True, exist_ok=True)
        template = type(items[0]).__name__.lower() + '_template.java'
        year = datetime.datetime.utcnow().year
        for item in items:
            if item.name == 'FunctionID':
                self.logger.warning('%s will be skipped', item.name)
                continue  # Skip FunctionID generation
            data = transformer.transform(item)
            data['year'] = year
            file = data['class_name'] + '.java'
            file = directory.joinpath(data['package_name'].replace('.', '/')).joinpath(file)
            if file.is_file():
                if skip:
                    self.logger.info('Skipping %s', file)
                    continue
                if overwrite:
                    self.logger.info('Overriding %s', file)
                    file.unlink()
                    self.write_file(file, template, data)
                else:
                    while True:
                        try:
                            confirm = input('File already exists {}. Overwrite? Y/Enter = yes, N = no\n'.format(file))
                            if confirm.lower() == 'y' or not confirm:
                                self.logger.info('Overriding %s', file)
                                file.unlink()
                                self.write_file(file, template, data)
                                break
                            if confirm.lower() == 'n':
                                self.logger.info('Skipping %s', file)
                                break
                        except KeyboardInterrupt:
                            print('\nThe user interrupted the execution of the program')
                            sys.exit(1)
            else:
                self.logger.info('Writing new %s', file)
                self.write_file(file, template, data)

    def parser(self, xml, xsd, pattern=None):
        """
        Validate xml to match with xsd. Calling parsers to get Model from xml. If provided pattern, filtering Model.
        :param xml: path to MOBILE_API.xml
        :param xsd: path to MOBILE_API.xsd
        :param pattern: regex-pattern from command-line arguments to filter element from initial Model
        :return: initial Model
        """
        self.logger.info('''Validating XML and generating model with following parameters:
            Source xml      : %s
            Source xsd      : %s''', xml, xsd)

        try:
            schema = XMLSchema(xsd)
            if not schema.is_valid(xml):
                raise GenerateError(schema.validate(xml))
            interface = Parser().parse(xml)
        except (InterfaceError, XMLSchemaError, GenerateError) as message1:
            self.logger.critical('Invalid XML file content: %s, %s', xml, message1)
            sys.exit(1)

        enum_names = tuple(interface.enums.keys())
        struct_names = tuple(interface.structs.keys())

        if pattern:
            intermediate = OrderedDict()
            intermediate.update({'params': interface.params})
            for kind, content in vars(interface).items():
                if kind == 'params':
                    continue
                for name, item in content.items():
                    if re.match(pattern, item.name):
                        self.logger.info('%s/%s match with %s', kind, item.name, pattern)
                        if kind in intermediate:
                            intermediate[kind].update({name: item})
                        else:
                            intermediate.update({kind: {name: item}})
            interface = Interface(**intermediate)

        self.logger.debug({'enums': tuple(interface.enums.keys()),
                           'structs': tuple(interface.structs.keys()),
                           'functions': tuple(map(lambda i: i.function_id.name, interface.functions.values())),
                           'params': interface.params})
        return enum_names, struct_names, interface

    def main(self):
        """
        Entry point for parser and generator
        :return: None
        """
        args = self.get_parser()

        self.versions_compatibility_validating()

        enum_names, struct_names, interface = self.parser(xml=args.source_xml, xsd=args.source_xsd,
                                                          pattern=args.regex_pattern)

        paths = self.get_paths()
        key_words = self.get_key_words()

        if args.enums and interface.enums:
            self.process(args.output_directory, args.skip, args.overwrite, tuple(interface.enums.values()),
                         EnumsProducer(paths, key_words))
        if args.structs and interface.structs:
            self.process(args.output_directory, args.skip, args.overwrite, tuple(interface.structs.values()),
                         StructsProducer(paths, enum_names, struct_names, key_words))
        if args.functions and interface.functions:
            self.process(args.output_directory, args.skip, args.overwrite, tuple(interface.functions.values()),
                         FunctionsProducer(paths, enum_names, struct_names, key_words))


if __name__ == '__main__':
    Generator().main()
