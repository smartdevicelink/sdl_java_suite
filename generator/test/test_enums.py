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
        self.producer = EnumsProducer(paths)

    def comparison(self, expected, actual):
        actual = OrderedDict(sorted(actual.items()))
        actual['params'] = tuple(sorted(actual['params'], key=lambda x: x.name))
        expected = OrderedDict(sorted(expected.items()))
        actual_params = dict(zip(map(lambda k: k.name, actual['params']), actual['params']))
        expected_params = dict(zip(map(lambda k: k.name, expected['params']), expected['params']))
        for key, param in actual_params.copy().items():
            self.assertTrue(key in expected_params)
        for key, param in expected_params.copy().items():
            self.assertTrue(key in actual_params)
            keys = actual_params[key]._asdict()
            Params = namedtuple('Params', sorted(keys))
            p = {name: getattr(param, name) for name in keys.keys()}
            expected_params[key] = Params(**p)
        expected['params'] = tuple(sorted(expected_params.values(), key=lambda x: x.name))

        self.assertDictEqual(expected, actual)

    def test_deprecated(self):
        item = Enum(name='TestDeprecated', deprecated=True, elements={
            'PRIMARY_WIDGET': EnumElement(name='PRIMARY_WIDGET', internal_name='PRIMARY_WIDGET', value=1,
                                          deprecated=True)
        })
        expected = {
            'kind': 'simple',
            'package_name': 'com.smartdevicelink.proxy.rpc.enums',
            'class_name': 'TestDeprecated',
            'params': (
                self.producer.params(name='PRIMARY_WIDGET', origin='PRIMARY_WIDGET', deprecated=True,
                                     internal=1, description=None, since=None, value=None),),
            'since': None,
            'deprecated': True
        }
        actual = self.producer.transform(item)

        self.comparison(expected, actual)

    def test_Language(self):
        item = Enum(name='Language', elements={
            'EN-US': EnumElement(name='EN-US', internal_name='EN-US')
        })
        expected = {
            'kind': 'custom',
            'package_name': 'com.smartdevicelink.proxy.rpc.enums',
            'class_name': 'Language',
            'params': (
                self.producer.params(name='EN_US', origin='EN-US', internal='"EN-US"', description=None, since=None,
                                     value=None, deprecated=None),),
            'since': None,
            'deprecated': None,
            'imports': {'java.util.EnumSet'}
        }
        actual = self.producer.transform(item)

        self.comparison(expected, actual)

    def test_PredefinedWindows(self):
        elements = OrderedDict()
        elements['DEFAULT_WINDOW'] = EnumElement(name='DEFAULT_WINDOW', value=0)
        elements['PRIMARY_WIDGET'] = EnumElement(name='PRIMARY_WIDGET', value=1)
        item = Enum(name='PredefinedWindows', elements=elements)
        expected = {
            'kind': 'simple',
            'package_name': 'com.smartdevicelink.proxy.rpc.enums',
            'class_name': 'PredefinedWindows',
            'params': (self.producer.params(name='DEFAULT_WINDOW', origin='DEFAULT_WINDOW',
                                            internal=0, description=None, since=None, value=None,
                                            deprecated=None),
                       self.producer.params(name='PRIMARY_WIDGET', origin='PRIMARY_WIDGET',
                                            internal=1, description=None, since=None, value=None,
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
            'kind': 'simple',
            'package_name': 'com.smartdevicelink.proxy.rpc.enums',
            'class_name': 'DisplayType',
            'params': (
                self.producer.params(name='CID', origin='CID', internal=None, description=None,
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
            'package_name': 'com.smartdevicelink.proxy.rpc.enums',
            'class_name': 'SpeechCapabilities',
            'params': (
                self.producer.params(name='TEXT', origin='TEXT', description=None,
                                     since=None, value=None, deprecated=None, internal=None),),
            'since': '1.0.0',
            'deprecated': None
        }
        actual = self.producer.transform(item)

        self.comparison(expected, actual)

    def test_VrCapabilities1(self):
        item = Enum(name='VrCapabilities', elements={
            'TEXT': EnumElement(name='TEXT', internal_name='VR_TEXT')
        })
        expected = {
            'kind': 'simple',
            'package_name': 'com.smartdevicelink.proxy.rpc.enums',
            'class_name': 'VrCapabilities',
            'params': (
                self.producer.params(name='TEXT', origin='TEXT', description=None,
                                     since=None, value=None, deprecated=None, internal=None),),
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
            'package_name': 'com.smartdevicelink.proxy.rpc.enums',
            'class_name': 'ButtonName',
            'params': (
                self.producer.params(deprecated=None, value=None, description=None,
                                     name='OK', origin='OK', since=None, internal=None),),
            'since': None,
            'deprecated': None
        }
        actual = self.producer.transform(item)

        self.comparison(expected, actual)

    def test_Dimension(self):
        item = Enum(name='Dimension', elements={
            'NO_FIX': EnumElement(name='NO_FIX', internal_name='Dimension_NO_FIX'),
            '2D': EnumElement(name='2D', internal_name='Dimension_2D')
        })
        expected = {
            'kind': 'custom',
            'package_name': 'com.smartdevicelink.proxy.rpc.enums',
            'class_name': 'Dimension',
            'params': (
                self.producer.params(deprecated=None, value=None, description=None,
                                     name='NO_FIX', origin='NO_FIX', since=None, internal='"NO_FIX"'),
                self.producer.params(deprecated=None, value=None, description=None,
                                     name='_2D', origin='2D', since=None, internal='"2D"'),),
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
            'package_name': 'com.smartdevicelink.proxy.rpc.enums',
            'class_name': 'VehicleDataEventStatus',
            'params': (
                self.producer.params(deprecated=None, value=None, description=None,
                                     name='NO_EVENT', origin='NO_EVENT', since=None, internal=None),),
            'since': None,
            'deprecated': None,
        }
        actual = self.producer.transform(item)

        self.comparison(expected, actual)

    def test_SystemCapabilityType(self):
        elements = OrderedDict()
        elements['DISPLAYS'] = EnumElement(name='DISPLAYS')
        elements['NAVIGATION'] = EnumElement(name='NAVIGATION')

        item = Enum(name='SystemCapabilityType', elements=elements)
        expected = {
            'kind': 'simple',
            'package_name': 'com.smartdevicelink.proxy.rpc.enums',
            'class_name': 'SystemCapabilityType',
            'params': (
                self.producer.params(deprecated=None, value=None, description=None,
                                     name='DISPLAYS', origin='DISPLAYS', since=None, internal=None),
                self.producer.params(deprecated=None, value=None, description=None,
                                     name='NAVIGATION', origin='NAVIGATION', since=None, internal=None)),
            'since': None,
            'deprecated': None,
        }
        actual = self.producer.transform(item)

        self.comparison(expected, actual)
