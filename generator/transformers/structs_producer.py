"""
Structs transformation
"""

import logging
import textwrap
from collections import namedtuple, OrderedDict

from model.param import Param
from model.struct import Struct
from transformers.common_producer import InterfaceProducerCommon


class StructsProducer(InterfaceProducerCommon):
    """
    Structs transformation
    """

    def __init__(self, paths, enum_names, struct_names, key_words):
        super(StructsProducer, self).__init__(
            container_name='members',
            enums_package=paths.enums_package,
            structs_package=paths.structs_package,
            enum_names=enum_names,
            struct_names=struct_names,
            package_name=paths.structs_package,
            key_words=key_words)
        self.logger = logging.getLogger('StructsProducer')
        self.struct_class = paths.struct_class

    def transform(self, item: Struct) -> dict:
        """
        Override
        :param item: particular element from initial Model
        :return: dictionary to be applied to jinja2 template
        """
        class_name = self.replace_keywords(item.name[:1].upper() + item.name[1:])

        imports = {'java.util.Hashtable'}
        extends_class = self.struct_class

        imports.add(extends_class)
        extends_class = extends_class.rpartition('.')[-1]

        params = OrderedDict()

        for param in getattr(item, self.container_name).values():
            param.name = self.replace_keywords(param.name)
            i, p = self.extract_param(param)
            imports.update(i)
            params[param.name] = p

        render = OrderedDict()
        render['class_name'] = class_name
        render['extends_class'] = extends_class
        render['package_name'] = self.package_name
        render['imports'] = imports
        render['deprecated'] = item.deprecated
        render['since'] = item.since

        description = self.extract_description(item.description)
        if description:
            render['description'] = description
        if params:
            render['params'] = params

        if 'imports' in render:
            render['imports'] = self.sort_imports(render['imports'])
        if params:
            render['params'] = tuple(render['params'].values())
        if 'description' in render and isinstance(render['description'], str):
            render['description'] = textwrap.wrap(render['description'], 90)

        return render

    def sort_imports(self, imports: set):
        sorted_imports = []
        if 'android.support.annotation.NonNull' in imports:
            sorted_imports.append('android.support.annotation.NonNull')
            imports.remove('android.support.annotation.NonNull')
            sorted_imports.append('')
        tmp = []
        for i in imports:
            if i.rpartition('.')[0] != self.package_name and not i.startswith('java'):
                tmp.append(i)
        if tmp:
            tmp.sort()
            sorted_imports.extend(tmp)
        if 'java.util.Hashtable' in imports or 'java.util.List' in imports:
            sorted_imports.append('')
        for i in ('java.util.Hashtable', 'java.util.List', 'java.util.ArrayList', 'java.util.Collections'):
            if i in imports:
                sorted_imports.append(i)
        return sorted_imports

    def extract_param(self, param: Param):
        imports = set()
        p = OrderedDict()
        p['title'] = param.name[:1].upper() + param.name[1:]
        p['key'] = 'KEY_' + self.key(param.name)
        p['mandatory'] = param.is_mandatory
        p['last'] = param.name
        if param.since:
            p['since'] = param.since
        p['deprecated'] = param.deprecated
        p['origin'] = param.name

        d = self.extract_description(param.description)
        if d:
            p['description'] = textwrap.wrap(d, 90)
        t = self.extract_type(param)
        tr = t
        if t.startswith('List'):
            imports.add('java.util.List')
            p['SuppressWarnings'] = 'unchecked'
            tr = t.replace('List<', '').rstrip('>')
        if t.startswith('Float'):
            imports.add('com.smartdevicelink.util.SdlDataTypeConverter')
        p['return_type'] = self.replace_sync(t)

        if tr in self.enum_names:
            imports.add('{}.{}'.format(self.enums_package, tr))
        if tr in self.struct_names:
            imports.add('{}.{}'.format(self.structs_package, tr))
        if param.is_mandatory:
            imports.add('android.support.annotation.NonNull')

        Params = namedtuple('Params', sorted(p))
        return imports, Params(**p)
