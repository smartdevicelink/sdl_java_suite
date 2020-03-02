import platform
import sys
import unittest
from collections import namedtuple
from pathlib import Path

PATH = Path(__file__).absolute()

sys.path.append(PATH.parents[1].joinpath('rpc_spec/InterfaceParser').as_posix())
sys.path.append(PATH.parents[1].as_posix())

try:
    from parsers.sdl_rpc_v2 import Parser
    from transformers.structs_producer import StructsProducer
    from transformers.functions_producer import FunctionsProducer
except ImportError as message:
    print('{}. probably you did not initialize submodule'.format(message))
    sys.exit(1)


class TestOrder(unittest.TestCase):
    def setUp(self):
        self.valid_xml_name = Path(__file__).absolute().parents[1].joinpath('rpc_spec/MOBILE_API.xml').as_posix()

        Paths = namedtuple('Prop',
                           'enums_package structs_package functions_package struct_class request_class response_class '
                           'notification_class')
        self.paths = Paths(enums_package='com.smartdevicelink.proxy.rpc.enums',
                           structs_package='com.smartdevicelink.proxy.rpc',
                           functions_package='com.smartdevicelink.proxy.rpc',
                           struct_class='com.smartdevicelink.proxy.RPCStruct',
                           request_class='com.smartdevicelink.proxy.RPCRequest',
                           response_class='com.smartdevicelink.proxy.RPCResponse',
                           notification_class='com.smartdevicelink.proxy.RPCNotification')

    def test_Order(self):
        version = platform.sys.version
        if version and version[:3] == '3.5':
            print('[WARNING] the version is 3.5, which has problems with Order of Dictionaries')

        struct_keys = ('protocol', 'codec')
        function_keys = [('menuID', 'position', 'menuName', 'menuIcon', 'menuLayout'),
                         ('success', 'resultCode', 'info')]

        for n in range(15):
            interface = Parser().parse(self.valid_xml_name)
            self.assertIsNotNone(interface)
            enum_names = tuple(interface.enums.keys())
            struct_names = tuple(interface.structs.keys())

            function_items = list(filter(lambda l: l.name == 'AddSubMenu', interface.functions.values()))
            struct_item = next(filter(lambda l: l.name == 'VideoStreamingFormat', interface.structs.values()))

            self.assertTupleEqual(struct_keys, tuple(struct_item.members.keys()))
            for i in range(2):
                self.assertTupleEqual(function_keys[i], tuple(function_items[i].params.keys()))

            self.structs_producer = StructsProducer(self.paths, enum_names, struct_names)
            self.functions_producer = FunctionsProducer(self.paths, enum_names, struct_names)

            s = self.structs_producer.transform(struct_item)
            struct_keys_transformed = tuple(map(lambda l: l.origin, s['params']))
            self.assertTupleEqual(struct_keys, struct_keys_transformed)

            for i in range(2):
                f = self.functions_producer.transform(function_items[i])
                if 'params' in f:
                    function_keys_transformed = tuple(map(lambda l: l.origin, f['params']))
                    self.assertTupleEqual(function_keys[i], function_keys_transformed)


if __name__ == '__main__':
    unittest.main()
