import unittest
from collections import namedtuple, OrderedDict

from model.array import Array
from model.enum import Enum
from model.float import Float
from model.param import Param
from model.string import String
from model.struct import Struct
from transformers.structs_producer import StructsProducer
from generator import Generator


class TestStructsProducer(unittest.TestCase):
    def setUp(self):
        self.maxDiff = None
        Paths = namedtuple('Prop', 'enums_package structs_package struct_class')
        paths = Paths(enums_package='com.smartdevicelink.proxy.rpc.enums',
                      structs_package='com.smartdevicelink.proxy.rpc',
                      struct_class='com.smartdevicelink.proxy.RPCStruct')
        keywords = Generator().get_key_words()
        self.producer = StructsProducer(paths, ['SamplingRate'], ('Image',), keywords)

    def comparison(self, expected, actual):
        actual_params = dict(zip(map(lambda k: k.origin, actual['params']), actual['params']))
        for param in expected['params']:
            for field in self.producer.params._fields:
                self.assertEqual(getattr(param, field), getattr(actual_params[param.origin], field, None))
        expected_filtered = dict(filter(lambda e: e[0] != 'params', expected.items()))
        actual_filtered = dict(filter(lambda e: e[0] != 'params', actual.items()))

        self.assertDictEqual(expected_filtered, actual_filtered)

    def test_AudioPassThruCapabilities(self):
        members = OrderedDict()
        members['range'] = Param(name='range', param_type=Float(max_value=10000.0, min_value=0.0))
        members['samplingRate'] = Param(name='samplingRate', param_type=Enum(name='SamplingRate'))
        item = Struct(name='AudioPassThruCapabilities', members=members)
        expected = {
            'class_name': 'AudioPassThruCapabilities',
            'extends_class': 'RPCStruct',
            'package_name': 'com.smartdevicelink.proxy.rpc',
            'imports': ['android.support.annotation.NonNull', '', 'com.smartdevicelink.proxy.RPCStruct',
                        'com.smartdevicelink.proxy.rpc.enums.SamplingRate',
                        'com.smartdevicelink.util.SdlDataTypeConverter', '', 'java.util.Hashtable'],
            'deprecated': None,
            'since': None,
            'params': (self.producer.params(deprecated=None, key='KEY_RANGE',
                                            last='range', mandatory=True,
                                            origin='range', return_type='Float',
                                            since=None, title='Range', description=None, param_doc=None, name=None),
                       self.producer.params(deprecated=None, key='KEY_SAMPLING_RATE',
                                            last='samplingRate', mandatory=True,
                                            origin='samplingRate', return_type='SamplingRate', name=None,
                                            since=None, title='SamplingRate', description=None, param_doc=None)),
        }
        actual = self.producer.transform(item)

        self.comparison(expected, actual)

    def test_CloudAppProperties(self):
        item = Struct(name='CloudAppProperties', members={
            'nicknames': Param(name='nicknames',
                               param_type=Array(element_type=String(max_length=100, min_length=0), max_size=100,
                                                min_size=0)),
        })
        expected = {
            'class_name': 'CloudAppProperties',
            'extends_class': 'RPCStruct',
            'package_name': 'com.smartdevicelink.proxy.rpc',
            'imports': ['android.support.annotation.NonNull', '', 'com.smartdevicelink.proxy.RPCStruct', '',
                        'java.util.Hashtable', 'java.util.List'],
            'deprecated': None,
            'since': None,
            'params': (self.producer.params(deprecated=None, key='KEY_NICKNAMES',
                                            last='nicknames', mandatory=True,
                                            origin='nicknames', return_type='List<String>', name=None,
                                            since=None, title='Nicknames', description=None, param_doc=None),),
        }
        actual = self.producer.transform(item)
        self.comparison(expected, actual)

    def test_SoftButton(self):
        item = Struct(name='SoftButton', members={
            'image': Param(name='image', param_type=Struct(name='Image'), description=['Optional image']),
        }, description=['\n            Describes different audio type configurations for PerformAudioPassThru.\n     '])
        expected = {
            'package_name': 'com.smartdevicelink.proxy.rpc',
            'imports': ['android.support.annotation.NonNull', '', 'com.smartdevicelink.proxy.RPCStruct', '',
                        'java.util.Hashtable'],
            'class_name': 'SoftButton',
            'extends_class': 'RPCStruct',
            'since': None,
            'deprecated': None,
            'description': ['Describes different audio type configurations for '
                            'PerformAudioPassThru.'],
            'params': (self.producer.params(deprecated=None, description=['Optional image'], key='KEY_IMAGE',
                                            last='image', mandatory=True, origin='image', return_type='Image',
                                            since=None, title='Image', param_doc=None, name=None),)
        }
        actual = self.producer.transform(item)
        self.comparison(expected, actual)

    def test_OASISAddress(self):
        item = Struct(name='OASISAddress', members={
            'countryName': Param(name='countryName', param_type=String(max_length=200))
        })
        expected = {
            'package_name': 'com.smartdevicelink.proxy.rpc',
            'imports': ['android.support.annotation.NonNull', '', 'com.smartdevicelink.proxy.RPCStruct', '',
                        'java.util.Hashtable'],
            'class_name': 'OASISAddress',
            'extends_class': 'RPCStruct',
            'since': None,
            'deprecated': None,
            'params': (
                self.producer.params(deprecated=None, key='KEY_COUNTRY_NAME', last='countryName', mandatory=True,
                                     origin='countryName', return_type='String', since=None, title='CountryName',
                                     description=None, param_doc=None, name=None),)
        }
        actual = self.producer.transform(item)
        self.comparison(expected, actual)

    def test_LocationDetails(self):
        item = Struct(name='LocationDetails', members={
            'searchAddress': Param(name='searchAddress', param_type=Struct(name='OASISAddress'))
        })
        expected = {
            'package_name': 'com.smartdevicelink.proxy.rpc',
            'imports': ['android.support.annotation.NonNull', '', 'com.smartdevicelink.proxy.RPCStruct', '',
                        'java.util.Hashtable'],
            'class_name': 'LocationDetails',
            'extends_class': 'RPCStruct',
            'since': None,
            'deprecated': None,
            'params': (
                self.producer.params(deprecated=None, key='KEY_SEARCH_ADDRESS', last='searchAddress', mandatory=True,
                                     origin='searchAddress', return_type='OASISAddress', since=None,
                                     title='SearchAddress', description=None, param_doc=None, name=None),)
        }
        actual = self.producer.transform(item)
        self.comparison(expected, actual)

    def test_SingleTireStatus(self):
        item = Struct(name='SingleTireStatus', members={
            'tpms': Param(name='tpms', param_type=Enum(name='TPMS'))
        })
        expected = {
            'package_name': 'com.smartdevicelink.proxy.rpc',
            'imports': ['android.support.annotation.NonNull', '', 'com.smartdevicelink.proxy.RPCStruct', '',
                        'java.util.Hashtable'],
            'class_name': 'SingleTireStatus',
            'extends_class': 'RPCStruct',
            'since': None,
            'deprecated': None,
            'params': (
                self.producer.params(deprecated=None, key='KEY_TPMS', last='tpms', mandatory=True, origin='tpms',
                                     return_type='TPMS', since=None, title='Tpms', description=None, param_doc=None,
                                     name=None),)
        }
        actual = self.producer.transform(item)
        self.comparison(expected, actual)
