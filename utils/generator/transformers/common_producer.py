"""
Common transformation
"""

import logging
import re
import textwrap
from abc import ABC
from collections import namedtuple, OrderedDict
from pathlib import Path

from model.array import Array
from model.enum import Enum
from model.struct import Struct


class InterfaceProducerCommon(ABC):
    """
    Common transformation
    """

    version = '1.0.0'

    def __init__(self, container_name, enums_package, structs_package, package_name,
                 enum_names=(), struct_names=(), mapping=None, all_mapping=None):
        if all_mapping is None:
            all_mapping = OrderedDict()
        self.logger = logging.getLogger('Generator.InterfaceProducerCommon')
        self.container_name = container_name
        self.enum_names = enum_names
        self.struct_names = struct_names
        self.enums_package = enums_package
        self.structs_package = structs_package
        self.mapping = mapping
        self.all_mapping = all_mapping
        self.package_name = package_name
        self._params = namedtuple('params', 'deprecated description key last mandatory origin return_type since title '
                                            'param_doc name')

    @property
    def get_version(self):
        return self.version

    @property
    def params(self):
        """
        :return: namedtuple params(name='', origin='')
        """
        return self._params

    @staticmethod
    def key(param: str):
        """
        Convert param string to uppercase and inserting underscores
        :param param: camel case string
        :return: string in uppercase with underscores
        """
        if re.match(r'^[A-Z_\d]+$', param):
            return param
        else:
            return re.sub(r'([a-z]|[A-Z]{2,})([A-Z]|\d$)', r'\1_\2', param).upper()

    @staticmethod
    def ending_cutter(n: str):
        """
        If string not contains only uppercase letters and end with 'ID' deleting 'ID' from end of string
        :param n: string to evaluate and deleting 'ID' from end of string
        :return: if match cut string else original string
        """
        if re.match(r'^\w+[a-z]+([A-Z]{2,})?ID$', n):
            return n[:-2]
        else:
            return n

    @staticmethod
    def extract_description(d):
        """
        Evaluate, align and delete @TODO
        :param d: list with description
        :return: evaluated string
        """
        return re.sub(r'(\s{2,}|\n|\[@TODO.+)', ' ', ''.join(d)).strip() if d else ''

    @staticmethod
    def replace_sync(name):
        """
        :param name: string with item name
        :return: string with replaced 'sync' to 'Sdl'
        """
        if name:
            return re.sub(r'^(s|S)ync(.+)$', r'\1dl\2', name)
        return name

    def extract_type(self, param):
        """
        Evaluate and extract type
        :param param: sub-element Param of element from initial Model
        :return: string with sub-element type
        """

        def evaluate(t1):
            if isinstance(t1, Struct) or isinstance(t1, Enum):
                name = t1.name
                element_type = 'enums' if isinstance(t1, Enum) else 'structs'
                if element_type in self.all_mapping and name in self.all_mapping[element_type] \
                        and 'rename' in self.all_mapping[element_type][name]:
                    name = self.all_mapping[element_type][name]['rename']
                return name
            else:
                return type(t1).__name__

        if isinstance(param.param_type, Array):
            return 'List<{}>'.format(evaluate(param.param_type.element_type))
        else:
            return evaluate(param.param_type)

    def get_file_content(self, file: str):
        """
        Used for getting content of custom scripts used in custom mapping
        :param file: relational path custom scripts
        :return: string with content of custom scripts
        """
        file = Path(__file__).absolute().parents[1].joinpath(file)
        try:
            with file.open('r') as f:
                s = f.readlines()
            return ''.join(s)
        except FileNotFoundError as e:
            self.logger.error(e)
            return ''

    def custom_mapping(self, render):
        """
        To be moved into parent class
        :param render: dictionary with moder ready for jinja template
        :return: None
        """
        custom = self.mapping[render['class_name']]

        for name in ('description', 'see', 'since', 'package_name'):
            if name in custom:
                render[name] = custom[name]
        if 'rename' in custom:
            render['class_name'] = custom['rename']
        if '-constructor' in custom:
            render['remove_constructor'] = 'simple'
        if 'imports' in custom:
            if 'imports' in render:
                render['imports'].update(custom['imports'])
        if '-imports' in custom:
            for i in custom['-imports']:
                if 'imports' in render:
                    render['imports'].remove(i)
        if '-params' in custom:
            for name in custom['-params']:
                if name in render['params']:
                    self.logger.warning('deleting parameter %s', render['params'][name])
                    del render['params'][name]
        if 'params_rename' in custom:
            for name, new_name in custom['params_rename'].items():
                if name in render['params']:
                    render['params'][new_name] = render['params'][name]._replace(name=new_name)
                    del render['params'][name]
        if 'script' in custom:
            script = self.get_file_content(custom['script'])
            if script:
                render['scripts'] = [script]
        if 'description_file' in custom:
            render['description'] = self.get_file_content(custom['description_file']).split('\n')
        if 'function_id' in custom:
            render['function_id'] = custom['function_id']
        if 'params' in custom:
            for name, value in custom['params'].items():
                if name in render['params']:
                    for k, v in value.items():
                        if isinstance(v, bool):
                            render['return_type'] = 'bool'
                            value[k] = str(v).lower()
                    d = render['params'][name]._asdict()
                    if 'description' in value:
                        d['description'] = textwrap.wrap(value['description'], 113)
                        del value['description']
                    if 'title' in value:
                        d['title'] = value['title']
                        del value['title']
                    if 'description_file' in value:
                        d['description'] = self.get_file_content(value['description_file']).split('\n')
                        del value['description_file']
                    if 'param_doc_file' in value:
                        d['param_doc'] = self.get_file_content(value['param_doc_file']).split('\n')
                        del value['param_doc_file']
                    if 'param_doc' in value:
                        d['param_doc'] = textwrap.wrap(value['param_doc'], 100)  # len(d['last'])
                        del value['param_doc']
                    d.update(value)
                    Params = namedtuple('Params', sorted(d))
                    render['params'][name] = Params(**d)
                else:
                    for k, v in value.items():
                        if isinstance(v, bool):
                            value[k] = str(v).lower()
                    value['name'] = name
                    if 'description' in value:
                        value['description'] = textwrap.wrap(value['description'], 113)
                    Params = namedtuple('Params', sorted(value))
                    render['params'][name] = Params(**value)
