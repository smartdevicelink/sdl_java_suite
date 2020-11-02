import re
import unittest
from collections import namedtuple, OrderedDict

from model.array import Array
from model.boolean import Boolean
from model.enum import Enum
from model.enum_element import EnumElement
from model.float import Float
from model.function import Function
from model.integer import Integer
from model.param import Param
from model.string import String
from model.struct import Struct
from transformers.functions_producer import FunctionsProducer
from generator import Generator


class TestFunctionsProducer(unittest.TestCase):
    def setUp(self):
        self.maxDiff = None
        Paths = namedtuple('Prop',
                           'enums_package structs_package functions_package request_class response_class '
                           'notification_class')
        paths = Paths(enums_package='com.smartdevicelink.proxy.rpc.enums',
                      structs_package='com.smartdevicelink.proxy.rpc',
                      functions_package='com.smartdevicelink.proxy.rpc',
                      request_class='com.smartdevicelink.proxy.RPCRequest',
                      response_class='com.smartdevicelink.proxy.RPCResponse',
                      notification_class='com.smartdevicelink.proxy.RPCNotification')
        self.expected_template = OrderedDict()
        self.expected_template['package_name'] = 'com.smartdevicelink.proxy.rpc'
        self.expected_template['since'] = None
        self.expected_template['deprecated'] = None
        enum_names = ('FileType', 'Language')
        struct_names = ('SdlMsgVersion', 'TemplateColorScheme', 'TTSChunk', 'Choice')
        keywords = Generator().get_key_words()
        self.producer = FunctionsProducer(paths, enum_names, struct_names, keywords)

    def comparison(self, expected, actual):
        actual_params = dict(zip(map(lambda k: k.title, actual['params']), actual['params']))
        for param in expected['params']:
            for field in self.producer.params._fields:
                self.assertEqual(getattr(param, field), getattr(actual_params[param.title], field, None))
        expected_filtered = dict(filter(lambda e: e[0] != 'params', expected.items()))
        actual_filtered = dict(filter(lambda e: e[0] != 'params', actual.items()))

        self.assertDictEqual(expected_filtered, actual_filtered)

    def test_Version(self):
        version = self.producer.get_version
        self.assertIsNotNone(version)
        self.assertTrue(re.match(r'^\d*\.\d*\.\d*$', version))

    def test_GetVehicleDataResponse(self):
        params = OrderedDict()
        params['speed'] = Param(name='speed', param_type=Float(max_value=700.0, min_value=0.0))
        item = Function(name='GetVehicleData', function_id=None,
                        message_type=EnumElement(name='response'), params=params)
        expected = self.expected_template.copy()
        expected['kind'] = 'response'
        expected['function_id'] = 'GET_VEHICLE_DATA'
        expected['class_name'] = 'GetVehicleDataResponse'
        expected['extends_class'] = 'RPCResponse'
        expected['history'] = None
        expected['imports'] = ['androidx.annotation.NonNull', '',
                               'com.smartdevicelink.protocol.enums.FunctionID',
                               'com.smartdevicelink.proxy.RPCResponse',
                               'com.smartdevicelink.proxy.rpc.enums.Result',
                               'com.smartdevicelink.util.SdlDataTypeConverter', '',
                               'java.util.Hashtable']
        expected['params'] = (self.producer.params(deprecated=None, key='KEY_SPEED', description=None,
                                                   last='speed', mandatory=True, SuppressWarnings=None,
                                                   origin='speed', return_type='Float',
                                                   since=None, title='Speed', param_doc=None, name=None),)
        actual = self.producer.transform(item)
        self.comparison(expected, actual)

    def test_RegisterAppInterfaceResponse(self):
        params = OrderedDict()
        params['language'] = Param(name='language', param_type=Enum(name='Language'))
        params['success'] = Param(name='success', param_type=Boolean())
        item = Function(name='RegisterAppInterface', function_id=None,
                        message_type=EnumElement(name='response'), params=params)
        expected = self.expected_template.copy()
        expected['kind'] = 'response'
        expected['function_id'] = 'REGISTER_APP_INTERFACE'
        expected['class_name'] = 'RegisterAppInterfaceResponse'
        expected['extends_class'] = 'RPCResponse'
        expected['history'] = None
        expected['imports'] = ['androidx.annotation.NonNull', '',
                               'com.smartdevicelink.protocol.enums.FunctionID', 'com.smartdevicelink.proxy.RPCResponse',
                               'com.smartdevicelink.proxy.rpc.enums.Language',
                               'com.smartdevicelink.proxy.rpc.enums.Result', '', 'java.util.Hashtable']
        expected['params'] = (
            self.producer.params(deprecated=None, key='KEY_LANGUAGE', last='language', mandatory=True,
                                 origin='language', return_type='Language', since=None, title='Language',
                                 description=None, SuppressWarnings=None, param_doc=None, name=None),)
        actual = self.producer.transform(item)
        self.comparison(expected, actual)

    def test_RegisterAppInterface(self):
        params = OrderedDict()
        params['syncMsgVersion'] = Param(name='syncMsgVersion', param_type=Struct(name='SyncMsgVersion'))
        params['ttsName'] = Param(name='ttsName', param_type=Array(element_type=Struct(name='TTSChunk')))
        item = Function(name='RegisterAppInterface', function_id=None,
                        message_type=EnumElement(name='request'), params=params)
        expected = self.expected_template.copy()
        expected['kind'] = 'request'
        expected['function_id'] = 'REGISTER_APP_INTERFACE'
        expected['class_name'] = 'RegisterAppInterface'
        expected['extends_class'] = 'RPCRequest'
        expected['history'] = None
        expected['imports'] = ['androidx.annotation.NonNull', '',
                               'com.smartdevicelink.protocol.enums.FunctionID',
                               'com.smartdevicelink.proxy.RPCRequest',
                               '', 'java.util.Hashtable', 'java.util.List']
        expected['params'] = (
            self.producer.params(deprecated=None, key='KEY_SDL_MSG_VERSION',
                                 last='sdlMsgVersion', mandatory=True, SuppressWarnings=None,
                                 origin='syncMsgVersion', return_type='SdlMsgVersion',
                                 since=None, title='SdlMsgVersion', description=None, param_doc=None, name=None),
            self.producer.params(SuppressWarnings='unchecked', deprecated=None,
                                 key='KEY_TTS_NAME', last='ttsName',
                                 mandatory=True, origin='ttsName', description=None,
                                 return_type='List<TTSChunk>', since=None, title='TtsName', param_doc=None, name=None))
        actual = self.producer.transform(item)
        self.comparison(expected, actual)

    def test_PutFileRequest(self):
        params = OrderedDict()
        params['fileType'] = Param(name='fileType',
                                   param_type=Enum(name='FileType', description=['Enumeration listing'], elements={
                                       'AUDIO_MP3': EnumElement(name='AUDIO_MP3')
                                   }), description=['Selected file type.'])
        params['syncFileName'] = Param(name='syncFileName', param_type=String(max_length=255, min_length=1))

        item = Function(name='PutFile', function_id=None, description=['\n            Used to'],
                        message_type=EnumElement(name='request'), params=params)
        expected = self.expected_template.copy()
        expected['kind'] = 'request'
        expected['function_id'] = 'PUT_FILE'
        expected['class_name'] = 'PutFile'
        expected['extends_class'] = 'RPCRequest'
        expected['history'] = None
        expected['imports'] = ['androidx.annotation.NonNull', '',
                               'com.smartdevicelink.protocol.enums.FunctionID', 'com.smartdevicelink.proxy.RPCRequest',
                               'com.smartdevicelink.proxy.rpc.enums.FileType', '', 'java.util.Hashtable']
        expected['description'] = ['Used to']
        expected['params'] = (
            self.producer.params(deprecated=None, description=['Selected file type.'], key='KEY_FILE_TYPE',
                                 last='fileType', mandatory=True, origin='fileType', SuppressWarnings=None,
                                 return_type='FileType', since=None, title='FileType', param_doc=None, name=None),
            self.producer.params(deprecated=None, key='KEY_SDL_FILE_NAME', last='sdlFileName', mandatory=True,
                                 origin='syncFileName', return_type='String', description=None,
                                 since=None, title='SdlFileName', SuppressWarnings=None, param_doc=None, name=None))
        actual = self.producer.transform(item)
        self.comparison(expected, actual)

    def test_OnEncodedSyncPDataNotification(self):
        item = Function(name='OnEncodedSyncPData', function_id=None, description=['\n           Callback including \n'],
                        message_type=EnumElement(name='notification'), params={
                        'URL': Param(name='URL', param_type=String(), description=['\n                If '])
                        })
        expected = self.expected_template.copy()
        expected['kind'] = 'notification'
        expected['function_id'] = 'ON_ENCODED_SYNC_PDATA'
        expected['class_name'] = 'OnEncodedSyncPData'
        expected['extends_class'] = 'RPCNotification'
        expected['history'] = None
        expected['imports'] = ['androidx.annotation.NonNull', '',
                               'com.smartdevicelink.protocol.enums.FunctionID',
                               'com.smartdevicelink.proxy.RPCNotification', '', 'java.util.Hashtable']
        expected['description'] = ['Callback including']
        expected['params'] = (
            self.producer.params(deprecated=None, description=['If'], key='KEY_URL', last='URL', title='URL',
                                 SuppressWarnings=None, mandatory=True, origin='URL', return_type='String',
                                 since=None, param_doc=None, name=None),)
        actual = self.producer.transform(item)
        self.comparison(expected, actual)

    def test_DeleteCommand(self):
        item = Function(name='DeleteCommand', function_id=None,
                        message_type=EnumElement(name='request'), params={
                            'cmdID': Param(name='cmdID', param_type=Integer(max_value=2000000000, min_value=0))
                        })
        expected = self.expected_template.copy()
        expected['kind'] = 'request'
        expected['function_id'] = 'DELETE_COMMAND'
        expected['class_name'] = 'DeleteCommand'
        expected['extends_class'] = 'RPCRequest'
        expected['history'] = None
        expected['imports'] = ['androidx.annotation.NonNull', '',
                               'com.smartdevicelink.protocol.enums.FunctionID', 'com.smartdevicelink.proxy.RPCRequest',
                               '', 'java.util.Hashtable']
        expected['params'] = (
            self.producer.params(deprecated=None, key='KEY_CMD_ID', last='cmdID', mandatory=True, origin='cmdID',
                                 return_type='Integer', since=None, title='CmdID', description=None,
                                 SuppressWarnings=None, param_doc=None, name=None),)
        actual = self.producer.transform(item)
        self.comparison(expected, actual)

    def test_Alert(self):
        item = Function(name='Alert', function_id=None,
                        message_type=EnumElement(name='request'), params={
                            'alertText2': Param(name='alertText2', param_type=String(max_length=500))
                        })
        expected = self.expected_template.copy()
        expected['kind'] = 'request'
        expected['function_id'] = 'ALERT'
        expected['class_name'] = 'Alert'
        expected['extends_class'] = 'RPCRequest'
        expected['history'] = None
        expected['imports'] = ['androidx.annotation.NonNull', '',
                               'com.smartdevicelink.protocol.enums.FunctionID',
                               'com.smartdevicelink.proxy.RPCRequest', '', 'java.util.Hashtable']
        expected['params'] = (
            self.producer.params(deprecated=None, key='KEY_ALERT_TEXT_2', last='alertText2',
                                 mandatory=True, origin='alertText2', param_doc=None,
                                 return_type='String', since=None, title='AlertText2', description=None,
                                 SuppressWarnings=None, name=None),)
        actual = self.producer.transform(item)
        self.comparison(expected, actual)

    def test_ReleaseInteriorVehicleDataModule(self):
        item = Function(name='ReleaseInteriorVehicleDataModule', function_id=None,
                        message_type=EnumElement(name='request'), params={
                            'moduleType': Param(name='moduleType', param_type=Enum(name='ModuleType'))
                        })
        expected = self.expected_template.copy()
        expected['kind'] = 'request'
        expected['function_id'] = 'RELEASE_INTERIOR_VEHICLE_DATA_MODULE'
        expected['class_name'] = 'ReleaseInteriorVehicleDataModule'
        expected['extends_class'] = 'RPCRequest'
        expected['history'] = None
        expected['imports'] = ['androidx.annotation.NonNull', '',
                               'com.smartdevicelink.protocol.enums.FunctionID',
                               'com.smartdevicelink.proxy.RPCRequest', '', 'java.util.Hashtable']
        expected['params'] = (
            self.producer.params(deprecated=None, key='KEY_MODULE_TYPE', last='moduleType', mandatory=True,
                                 origin='moduleType', return_type='ModuleType', since=None, title='ModuleType',
                                 description=None, SuppressWarnings=None, param_doc=None, name=None),)
        actual = self.producer.transform(item)
        self.comparison(expected, actual)
