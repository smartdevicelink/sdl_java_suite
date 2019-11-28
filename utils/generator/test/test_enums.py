import unittest
from collections import namedtuple, OrderedDict

from model.enum import Enum
from model.enum_element import EnumElement
from transformers.enums_producer import EnumsProducer


class TestEnumsProducer(unittest.TestCase):
    def setUp(self):
        self.maxDiff = None
        Paths = namedtuple('Prop', 'enums_package')
        paths = Paths(enums_package='com.smartdevicelink.proxy.rpc.enums')
        self.producer = EnumsProducer(paths, {'enums': self.mapping})

    def comparison(self, expected, actual):
        for key in ('valueForString', 'scripts'):
            if key == 'scripts':
                key = 'script'
            if key in actual:
                content = self.producer.get_file_content(self.mapping[actual['class_name']][key])
                self.assertSequenceEqual(content, actual[key])
        actual_params = dict(zip(map(lambda k: k.name, actual['params']), actual['params']))
        for param in expected['params']:
            for field in self.producer.params._fields:
                self.assertEqual(getattr(param, field), getattr(actual_params[param.name], field, None))
        expected_filtered = dict(filter(lambda e: e[0] != 'params', expected.items()))
        actual_filtered = dict(filter(lambda e: e[0] not in ('params', 'valueForString', 'scripts'), actual.items()))

        self.assertDictEqual(expected_filtered, actual_filtered)

    @property
    def mapping(self):
        return {
            'TestDeprecated': {
                'kind': 'custom'
            },
            'DisplayType': {
                'kind': 'custom',
                'valueForString': 'templates/scripts/DisplayType_valueForString.groovy',
                '-imports': [
                    'java.util.EnumSet'
                ]
            },
            'SpeechCapabilities': {
                'kind': 'simple'
            },
            'VrCapabilities': {
                'valueForString': 'templates/scripts/VrCapabilities_valueForString.groovy',
                'kind': 'simple',
                'params': {
                    'Text': {
                        'deprecated': 'any',
                        'since': '1.0'
                    }
                }
            },
            'MessageType': {
                'package_name': 'com.smartdevicelink.protocol.enums',
                'kind': 'simple',
                '-params': [
                    'notification',
                    'request',
                    'response'
                ],
                'params': {
                    'UNDEFINED': {},
                    'BULK': {},
                    'RPC': {}
                }
            },
            'HMILevel': {
                'params': {
                    'HMI_BACKGROUND': {
                        'description_file': 'templates/description/HMILevel_HMI_BACKGROUND.html'
                    }
                }
            },
            'ButtonName': {
                'script': 'templates/scripts/ButtonName_indexForPresetButton.groovy',
                'description_file': 'templates/description/ButtonName_head.html',
                'params': {
                    'OK': {
                        'description_file': 'templates/description/ButtonName_OK.html',
                        'since': '1.0',
                        'see': '#PLAY_PAUSE'
                    }
                }
            },
            'Dimension': {
                'params': {
                    'NO_FIX': {
                        'internal': '\"NO_FIX\"',
                        'description': 'No GPS at all'
                    }
                },
                '-params': [
                    '_NO_FIX'
                ]
            },
            'VehicleDataEventStatus': {
                'kind': 'simple',
                'params': {
                    'NO_EVENT': {
                        'description': 'No event available'
                    }
                }
            },
            'FunctionID': {
                'package_name': 'com.smartdevicelink.protocol.enums',
                'params_rename': {
                    'RELEASE_INTERIOR_VEHICLE_DATA_MODULE': 'RELEASE_INTERIOR_VEHICLE_MODULE'
                }
            },
            'SystemCapabilityType': {
                'kind': 'custom',
                'description_file': 'templates/description/SystemCapabilityType_head.html',
                '-imports': [
                    'java.util.EnumSet'
                ],
                'params': {
                    'NAVIGATION': {
                        'internal': True
                    },
                    'DISPLAY': {
                        'deprecated': 'any',
                        'internal': False
                    }
                }
            }
        }

    def test_deprecated(self):
        item = Enum(name='TestDeprecated', deprecated=True, elements={
            'PRIMARY_WIDGET': EnumElement(name='PRIMARY_WIDGET', internal_name='PRIMARY_WIDGET', value=1,
                                          deprecated=True)
        })
        expected = {
            'kind': 'custom',
            'return_type': 'int',
            'package_name': 'com.smartdevicelink.proxy.rpc.enums',
            'class_name': 'TestDeprecated',
            'imports': {'java.util.EnumSet'},
            'params': (
                self.producer.params(name='PRIMARY_WIDGET', origin='PRIMARY_WIDGET', deprecated=True,
                                     internal=1, description=None, since=None, value=1),),
            'since': None,
            'deprecated': True
        }
        actual = self.producer.transform(item)

        self.comparison(expected, actual)

    def test_FunctionID(self):
        elements = OrderedDict()
        elements['RESERVED'] = EnumElement(name='RESERVED', hex_value=0)
        elements['RegisterAppInterfaceID'] = EnumElement(name='RegisterAppInterfaceID', value=1, hex_value=1)
        elements['PerformAudioPassThruID'] = EnumElement(name='PerformAudioPassThruID', hex_value=10)
        elements['ReleaseInteriorVehicleDataModuleID'] = EnumElement(name='ReleaseInteriorVehicleDataModuleID',
                                                                     hex_value='3F', value=63)

        item = Enum(name='FunctionID', elements=elements, description=[
            'Enumeration linking function names with function IDs in SmartDeviceLink protocol. Assumes enumeration '
            'starts at value 0.'])
        expected = {
            'kind': 'complex',
            'return_type': 'int',
            'package_name': 'com.smartdevicelink.protocol.enums',
            'class_name': 'FunctionID',
            'description': [
                'Enumeration linking function names with function IDs in SmartDeviceLink protocol. Assumes '
                'enumeration starts at',
                'value 0.'],
            'imports': {'java.util.EnumSet', 'java.util.HashMap', 'java.util.Iterator', 'java.util.Map.Entry'},
            'params': (
                self.producer.params(name='RESERVED', origin='RESERVED', internal=None, description=None, since=None,
                                     value=None, deprecated=None),
                self.producer.params(name='REGISTER_APP_INTERFACE', origin='RegisterAppInterfaceID', deprecated=None,
                                     internal='"RegisterAppInterface"', description=None, since=None, value=1),
                self.producer.params(name='PerformAudioPassThruID', origin='PerformAudioPassThruID', internal=None,
                                     description=None, since=None, value=None, deprecated=None),
                self.producer.params(name='RELEASE_INTERIOR_VEHICLE_MODULE',
                                     origin='ReleaseInteriorVehicleDataModuleID',
                                     internal='"ReleaseInteriorVehicleDataModule"',
                                     description=None, since=None, value=63, deprecated=None)),
            'since': None,
            'deprecated': None
        }
        actual = self.producer.transform(item)

        self.comparison(expected, actual)

    def test_Language(self):
        item = Enum(name='Language', elements={
            'EN-US': EnumElement(name='EN-US', internal_name='EN-US')
        })
        expected = {
            'kind': 'custom',
            'return_type': 'String',
            'package_name': 'com.smartdevicelink.proxy.rpc.enums',
            'class_name': 'Language',
            'imports': {'java.util.EnumSet'},
            'params': (
                self.producer.params(name='EN-US', origin='EN-US', internal='"EN-US"', description=None, since=None,
                                     value=None, deprecated=None),),
            'since': None,
            'deprecated': None
        }
        actual = self.producer.transform(item)

        self.comparison(expected, actual)

    def test_PredefinedWindows(self):
        elements = OrderedDict()
        elements['DEFAULT_WINDOW'] = EnumElement(name='DEFAULT_WINDOW', value=0)
        elements['PRIMARY_WIDGET'] = EnumElement(name='PRIMARY_WIDGET', value=1)
        item = Enum(name='PredefinedWindows', elements=elements)
        expected = {
            'kind': 'complex',
            'return_type': 'int',
            'package_name': 'com.smartdevicelink.proxy.rpc.enums',
            'class_name': 'PredefinedWindows',
            'imports': {'java.util.HashMap', 'java.util.Map.Entry', 'java.util.EnumSet', 'java.util.Iterator'},
            'params': (self.producer.params(name='DEFAULT_WINDOW', origin='DEFAULT_WINDOW',
                                            internal='"DEFAULT_WINDOW"', description=None, since=None, value=0,
                                            deprecated=None),
                       self.producer.params(name='PRIMARY_WIDGET', origin='PRIMARY_WIDGET',
                                            internal='"PRIMARY_WIDGET"', description=None, since=None, value=1,
                                            deprecated=None)),
            'since': None,
            'deprecated': None
        }
        actual = self.producer.transform(item)

        self.comparison(expected, actual)

    def test_SamplingRate(self):
        item = Enum(name='SamplingRate', elements={
            '8KHZ': EnumElement(name='8KHZ', internal_name='SamplingRate_8KHZ')
        })
        expected = {
            'kind': 'custom',
            'return_type': 'String',
            'package_name': 'com.smartdevicelink.proxy.rpc.enums',
            'class_name': 'SamplingRate',
            'imports': {'java.util.EnumSet'},
            'params': (
                self.producer.params(name='_8KHZ', origin='8KHZ', internal='"8KHZ"', description=None, since=None,
                                     value=None,
                                     deprecated=None),),
            'since': None,
            'deprecated': None
        }
        actual = self.producer.transform(item)

        self.comparison(expected, actual)

    def test_Result(self):
        elements = OrderedDict()
        elements['SUCCESS'] = EnumElement(name='SUCCESS', description=['The request succeeded'])
        elements['VEHICLE_DATA_NOT_AVAILABLE'] = EnumElement(name='VEHICLE_DATA_NOT_AVAILABLE', since='2.0.0')
        item = Enum(name='Result', elements=elements)
        expected = {
            'kind': 'simple',
            'return_type': 'String',
            'package_name': 'com.smartdevicelink.proxy.rpc.enums',
            'class_name': 'Result',
            'params': (
                self.producer.params(name='SUCCESS', origin='SUCCESS', internal=None,
                                     description=['The request succeeded'],
                                     since=None, value=None, deprecated=None),
                self.producer.params(name='VEHICLE_DATA_NOT_AVAILABLE', origin='VEHICLE_DATA_NOT_AVAILABLE',
                                     internal=None, description=None, since='2.0.0', value=None, deprecated=None)),
            'since': None,
            'deprecated': None
        }
        actual = self.producer.transform(item)

        self.comparison(expected, actual)

    def test_DisplayType(self):
        item = Enum(name='DisplayType', deprecated=True, since='5.0.0', elements={
            'CID': EnumElement(name='CID', since='3.0.0')
        })
        expected = {
            'kind': 'custom',
            'return_type': 'String',
            'package_name': 'com.smartdevicelink.proxy.rpc.enums',
            'class_name': 'DisplayType',
            'imports': set(),
            'params': (
                self.producer.params(name='CID', origin='CID', internal='"CID"', description=None,
                                     since='3.0.0', value=None, deprecated=None),),
            'since': '5.0.0',
            'deprecated': True
        }
        actual = self.producer.transform(item)

        self.comparison(expected, actual)

    def test_SpeechCapabilities(self):
        item = Enum(name='SpeechCapabilities', since='1.0.0', elements={
            'TEXT': EnumElement(name='TEXT', internal_name='SC_TEXT')
        })
        expected = {
            'kind': 'simple',
            'return_type': 'String',
            'package_name': 'com.smartdevicelink.proxy.rpc.enums',
            'class_name': 'SpeechCapabilities',
            'imports': set(),
            'params': (
                self.producer.params(name='TEXT', origin='TEXT', description=None,
                                     since=None, value=None, deprecated=None, internal=None),),
            'since': '1.0.0',
            'deprecated': None
        }
        actual = self.producer.transform(item)

        self.comparison(expected, actual)

    def test_VrCapabilities(self):
        item = Enum(name='VrCapabilities', elements={
            'TEXT': EnumElement(name='TEXT', internal_name='VR_TEXT')
        })
        expected = {
            'kind': 'simple',
            'return_type': 'String',
            'package_name': 'com.smartdevicelink.proxy.rpc.enums',
            'class_name': 'VrCapabilities',
            'imports': set(),
            'params': (
                self.producer.params(name='TEXT', origin='TEXT', description=None,
                                     since=None, value=None, deprecated=None, internal=None),
                self.producer.params(name='Text', origin='TEXT', description=None,
                                     since='1.0', value=None, deprecated='any', internal=None)),
            'since': None,
            'deprecated': None
        }
        actual = self.producer.transform(item)

        self.comparison(expected, actual)

    def test_VrCapabilities(self):
        item = Enum(name='MessageType', elements={
            'NOTIFICATION': EnumElement(name='notification', internal_name='NOTIFICATION', value=2)
        })
        expected = {
            'kind': 'simple',
            'return_type': 'int',
            'package_name': 'com.smartdevicelink.protocol.enums',
            'class_name': 'MessageType',
            'imports': set(),
            'params': (
                self.producer.params(name='UNDEFINED', origin=None, description=None,
                                     since=None, value=None, deprecated=None, internal=None),
                self.producer.params(name='RPC', origin=None, description=None,
                                     since=None, value=None, deprecated=None, internal=None),
                self.producer.params(name='BULK', origin=None, description=None,
                                     since=None, value=None, deprecated=None, internal=None)),
            'since': None,
            'deprecated': None
        }
        actual = self.producer.transform(item)

        self.comparison(expected, actual)

    def test_ButtonName(self):
        item = Enum(name='ButtonName', elements={
            'OK': EnumElement(name='OK')
        })
        expected = {
            'kind': 'simple',
            'return_type': 'String',
            'package_name': 'com.smartdevicelink.proxy.rpc.enums',
            'class_name': 'ButtonName',
            'params': (
                self.producer.params(deprecated=None, value=None, description=self.producer.get_file_content(
                    self.mapping['ButtonName']['params']['OK']['description_file']).splitlines(),
                                     name='OK', origin='OK', since='1.0', internal=None),),
            'since': None,
            'deprecated': None,
            'description': self.producer.get_file_content(
                self.mapping['ButtonName']['description_file']).splitlines()
        }
        actual = self.producer.transform(item)

        self.comparison(expected, actual)

    def test_Dimension(self):
        item = Enum(name='Dimension', elements={
            'NO_FIX': EnumElement(name='NO_FIX', internal_name='Dimension_NO_FIX')
        })
        expected = {
            'kind': 'custom',
            'return_type': 'String',
            'package_name': 'com.smartdevicelink.proxy.rpc.enums',
            'class_name': 'Dimension',
            'params': (
                self.producer.params(deprecated=None, value=None, description=['No GPS at all'],
                                     name='NO_FIX', origin=None, since=None, internal='"NO_FIX"'),),
            'since': None,
            'deprecated': None,
            'imports': {'java.util.EnumSet'}
        }
        actual = self.producer.transform(item)

        self.comparison(expected, actual)

    def test_VehicleDataEventStatus(self):
        item = Enum(name='VehicleDataEventStatus', elements={
            'NO_EVENT': EnumElement(name='NO_EVENT', internal_name='VDES_NO_EVENT')
        })
        expected = {
            'kind': 'simple',
            'return_type': 'String',
            'package_name': 'com.smartdevicelink.proxy.rpc.enums',
            'class_name': 'VehicleDataEventStatus',
            'params': (
                self.producer.params(deprecated=None, value=None, description=['No event available'],
                                     name='NO_EVENT', origin='NO_EVENT', since=None, internal=None),),
            'since': None,
            'deprecated': None,
            'imports': set()
        }
        actual = self.producer.transform(item)

        self.comparison(expected, actual)

    def test_SystemCapabilityType(self):
        elements = OrderedDict()
        elements['DISPLAYS'] = EnumElement(name='DISPLAYS')
        elements['NAVIGATION'] = EnumElement(name='NAVIGATION')

        item = Enum(name='SystemCapabilityType', elements=elements)
        expected = {
            'kind': 'custom',
            'return_type': 'bool',
            'package_name': 'com.smartdevicelink.proxy.rpc.enums',
            'class_name': 'SystemCapabilityType',
            'params': (
                self.producer.params(deprecated=None, value=None, description=None,
                                     name='DISPLAYS', origin='DISPLAYS', since=None, internal='"DISPLAYS"'),
                self.producer.params(deprecated=None, value=None, description=None,
                                     name='NAVIGATION', origin='NAVIGATION', since=None, internal='true'),
                self.producer.params(deprecated='any', value=None, description=None,
                                     name='DISPLAY', origin=None, since=None, internal='false')),
            'since': None,
            'deprecated': None,
            'imports': set(),
            'description': self.producer.get_file_content(
                self.mapping['SystemCapabilityType']['description_file']).splitlines()
        }
        actual = self.producer.transform(item)

        self.comparison(expected, actual)
