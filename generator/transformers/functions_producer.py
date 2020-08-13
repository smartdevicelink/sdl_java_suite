"""
Functions transformation
"""

import logging
import textwrap
from collections import namedtuple, OrderedDict

from model.function import Function
from model.param import Param
from transformers.common_producer import InterfaceProducerCommon


class FunctionsProducer(InterfaceProducerCommon):
    """
    Functions transformation
    """

    def __init__(self, paths, enum_names, struct_names, key_words):
        super(FunctionsProducer, self).__init__(
            container_name='params',
            enums_package=paths.enums_package,
            structs_package=paths.structs_package,
            enum_names=enum_names,
            struct_names=struct_names,
            package_name=paths.functions_package,
            key_words=key_words)
        self.logger = logging.getLogger('FunctionsProducer')
        self.request_class = paths.request_class
        self.response_class = paths.response_class
        self.notification_class = paths.notification_class
        self._params = namedtuple('Params', self._params._fields + ('SuppressWarnings',))

    def transform(self, item: Function) -> dict:
        """
        Override
        :param item: particular element from initial Model
        :return: dictionary to be applied to jinja2 template
        """
        class_name = self.replace_keywords(item.name[:1].upper() + item.name[1:])

        imports = {'java.util.Hashtable', 'com.smartdevicelink.protocol.enums.FunctionID'}
        extends_class = None
        if item.message_type.name == 'response':
            extends_class = self.response_class
            if not class_name.endswith("Response"):
                class_name += 'Response'
            imports.add('com.smartdevicelink.proxy.rpc.enums.Result')
            imports.add('androidx.annotation.NonNull')
        elif item.message_type.name == 'request':
            extends_class = self.request_class
        elif item.message_type.name == 'notification':
            extends_class = self.notification_class
        if extends_class:
            imports.add(extends_class)
            extends_class = extends_class.rpartition('.')[-1]

        params = OrderedDict()

        for param in getattr(item, self.container_name).values():
            param.origin = param.name
            param.name = self.replace_keywords(param.name)
            i, p = self.extract_param(param)
            imports.update(i)
            params.update({param.name: p})

        render = OrderedDict()
        render['kind'] = item.message_type.name
        render['package_name'] = self.package_name
        render['imports'] = self.sort_imports(imports)
        render['function_id'] = self.key(self.replace_keywords(item.name))
        render['class_name'] = class_name
        render['extends_class'] = extends_class
        render['since'] = item.since
        render['deprecated'] = item.deprecated

        description = self.extract_description(item.description)
        if description:
            render['description'] = description

        if params:
            render['params'] = tuple(params.values())
        if 'description' in render and isinstance(render['description'], str):
            render['description'] = textwrap.wrap(render['description'], 90)

        return render

    def sort_imports(self, imports: set):
        sorted_imports = []
        if 'androidx.annotation.NonNull' in imports:
            sorted_imports.append('androidx.annotation.NonNull')
            imports.remove('androidx.annotation.NonNull')
            sorted_imports.append('')
        sorted_imports.append('com.smartdevicelink.protocol.enums.FunctionID')
        imports.remove('com.smartdevicelink.protocol.enums.FunctionID')
        tmp = []
        for i in imports:
            if i.rpartition('.')[0] != self.package_name and not i.startswith('java'):
                tmp.append(i)
        if tmp:
            tmp.sort()
            sorted_imports.extend(tmp)
        if 'java.util.Hashtable' in imports or 'java.util.List' in imports:
            sorted_imports.append('')
        for i in ('java.util.Hashtable', 'java.util.List', 'java.util.ArrayList', 'java.util.Collections',
                  'java.util.zip.CRC32'):
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
        p['origin'] = param.origin
        d = self.extract_description(param.description)
        if param.name == 'success':
            d = 'whether the request is successfully processed'
        if param.name == 'resultCode':
            d = 'additional information about a response returning a failed outcome'
        if d:
            p['description'] = textwrap.wrap(d, 90)
        t = self.extract_type(param)
        if t == 'EnumSubset' and param.name == 'resultCode':
            t = 'Result'
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
            imports.add('androidx.annotation.NonNull')

        Params = namedtuple('Params', sorted(p))
        return imports, Params(**p)
