package com.smartdevicelink.test.rpc;


import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static android.support.test.InstrumentationRegistry.getTargetContext;
import static junit.framework.TestCase.assertTrue;

/**
 * This is a unit test class for the SmartDeviceLink library project
 * It makes sure that for each RPC, all mandatory parameters are set in a constructor
 */

@RunWith(AndroidJUnit4.class)
public class RPCConstructorsTests {

    private final String XML_FILE_NAME = "xml/MOBILE_API.xml";
    private final String RPC_PACKAGE_PREFIX = "com.smartdevicelink.proxy.rpc.";
    private Map<String, List<Parameter>> rpcMandatoryParamsMapFromXml;

    private class Parameter {
        private String name;
        private String type;
        private boolean isArray;

        public Parameter(String name, String type, boolean isArray) {
            this.name = name;
            this.type = type;
            this.isArray = isArray;
        }
    }

    @Before
    public void setUp(){
        // Map that has keys correspond to the RPC names and values correspond to the
        // mandatory params for that RPC. All info are loaded from the RPC spec xml file
        rpcMandatoryParamsMapFromXml = getRPCMandatoryParamsMap(XML_FILE_NAME);

    }

    // This method parses the RPC spec xml file and returns a map that has
    // keys correspond to the RPC names and values correspond to the mandatory params for that RPC
    private Map<String, List<Parameter>> getRPCMandatoryParamsMap(String fileName) {
        Map<String, List<Parameter>> rpcMandatoryParamsMap = new HashMap<>();
        try {
            InputStream stream = getTargetContext().getAssets().open(fileName);
            XmlPullParserFactory xmlFactoryObject = XmlPullParserFactory.newInstance();
            XmlPullParser myParser = xmlFactoryObject.newPullParser();
            myParser.setInput(stream, null);
            int event = myParser.getEventType();
            String rpcName = null;
            boolean ignoreRPC = false;
            while (event != XmlPullParser.END_DOCUMENT)  {
                String name = myParser.getName();
                switch (event){
                    case XmlPullParser.START_TAG:
                        // Store the RPC name in the map
                        if(name.equals("function") || name.equals("struct")){
                            rpcName = myParser.getAttributeValue(null,"name");
                            ignoreRPC = false;
                            if (name.equals("function") && myParser.getAttributeValue(null, "messagetype").equals("response") && !rpcName.contains("Response")){
                                rpcName += "Response";
                            }

                            // -------------- Exceptional cases because of mismatch between the RPC spec and the Android code --------------
                                if(rpcName.equals("SyncMsgVersion")){
                                    rpcName = "SdlMsgVersion";
                                } else if(rpcName.equals("ShowConstantTBTResponse")){
                                    rpcName = "ShowConstantTbtResponse";
                                } else if(rpcName.equals("OASISAddress")) {
                                    rpcName = "OasisAddress";
                                } else if(rpcName.equals("ShowConstantTBT")) {
                                    rpcName = "ShowConstantTbt";
                                } else if (rpcName.equals("EncodedSyncPData") || rpcName.equals("OnEncodedSyncPData") || rpcName.equals("EncodedSyncPDataResponse")){
                                    ignoreRPC = true;
                                }
                            // -------------------------------------------------------------------------------------------------------------

                            if (!ignoreRPC) {
                                rpcMandatoryParamsMap.put(rpcName, new ArrayList<Parameter>());
                            }
                        }
                        // Store the mandatory params for the current RPC in the map
                        if(name.equals("param") && myParser.getAttributeValue(null, "until") == null && !ignoreRPC){
                            boolean mandatory = Boolean.valueOf(myParser.getAttributeValue(null,"mandatory"));
                            if (mandatory) {
                                String paramName = myParser.getAttributeValue(null, "name");
                                String paramType = myParser.getAttributeValue(null, "type");
                                boolean paramIsArray = Boolean.valueOf(myParser.getAttributeValue(null, "array"));

                                // -------------- Exceptional cases because of mismatch between the RPC spec and the Android code --------------
                                if (paramType.equals("SyncMsgVersion")){
                                    paramType = "SdlMsgVersion";
                                } else if (rpcName.equals("GPSData") && paramType.equals("Float")){
                                    paramType = "Double";
                                } else if (rpcName.equals("TouchEvent") && paramType.equals("Integer") && paramIsArray){
                                    paramType = "Long";
                                }

                                if (paramName.equals("syncFileName")){
                                    paramName = "sdlFileName";
                                } else if (paramName.equals("syncMsgVersion")){
                                    paramName = "sdlMsgVersion";
                                } else if (paramName.equals("hmiPermissions")){
                                    paramName = "hMIPermissions";
                                } else if (paramName.equals("resolution")){
                                    paramName = "imageResolution";
                                } else if (paramName.equals("pressureTelltale")){
                                    paramName = "pressureTellTale";
                                }
                                // -------------------------------------------------------------------------------------------------------------

                                Parameter param = new Parameter(paramName, paramType, paramIsArray);
                                rpcMandatoryParamsMap.get(rpcName).add(param);
                            }
                        }
                        break;
                }
                event = myParser.next();
            }
            stream.close();
        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
        }
        return rpcMandatoryParamsMap;
    }

    // This method makes sure that for every RPC, there is a constructor that has all the mandatory params
    // It also checks if there are RPC in the XML file that don't exist in the code
    @Test
    public void testMandatoryParamsMatch() {
        // List of RPC names that don't have a constructor that has all mandatory params
        List<String> rpcsWithInvalidConstructor = new ArrayList<>();

        // List of the RPC names that couldn't be found in code
        // potentially because of a mismatch between name in the RPC spec xml file and name in code
        List<String> rpcsFromXmlNotFoundInCode = new ArrayList<>();

        // Loop through all RPCs that were loaded from RPC spec XML file
        // and make sure that every RPC has a constructor that has all mandatory params
        for (String rpcName : rpcMandatoryParamsMapFromXml.keySet()) {
            Class aClass;
            try {
                aClass = Class.forName(RPC_PACKAGE_PREFIX + rpcName);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                rpcsFromXmlNotFoundInCode.add(rpcName);
                continue;
            }
            List<String> mandatoryParamsListFromXML = new ArrayList<>();
            for (Parameter param : rpcMandatoryParamsMapFromXml.get(rpcName)) {
                String type = param.type;
                // If the param is a list of objects, the type should be like "List<Object>"
                if (param.isArray){
                    type = String.format("List<%s>", type);
                }
                mandatoryParamsListFromXML.add(type);
            }
            List<String> mandatoryParamsListFromCode = new ArrayList<>();
            boolean rpcHasValidConstructor = false;
            for (Constructor constructor : aClass.getConstructors()){
                mandatoryParamsListFromCode.clear();
                for (Type paramType : constructor.getGenericParameterTypes()){
                    String paramFullType = paramType.toString();
                    String paramSimpleType;

                    // If the param is a list of objects, the type should be like "List<Object>"
                    if (paramFullType.matches("java.util.List<.+>")) {
                        paramSimpleType = String.format("List<%s>", paramFullType.substring(paramFullType.lastIndexOf('.') + 1, paramFullType.length() - 1));
                    }
                    // If the param is a simple object for example "java.lang.String", the type should be the last part "String"
                    else if (!paramFullType.contains(">")){
                        paramSimpleType = paramFullType.substring(paramFullType.lastIndexOf('.') + 1, paramFullType.length());
                    }
                    else {
                        paramSimpleType = paramFullType;
                    }
                    mandatoryParamsListFromCode.add(paramSimpleType);
                }
                if (mandatoryParamsListFromCode.containsAll(mandatoryParamsListFromXML) && mandatoryParamsListFromXML.containsAll(mandatoryParamsListFromCode)){
                    rpcHasValidConstructor = true;
                    break;
                }
            }
            if (!rpcHasValidConstructor){
                rpcsWithInvalidConstructor.add(rpcName);
            }
        }
        assertTrue("The following RPCs were not found in the code: " + rpcsFromXmlNotFoundInCode, rpcsFromXmlNotFoundInCode.isEmpty());
        assertTrue("The following RPCs don't have a constructor that has all the mandatory params: " + rpcsWithInvalidConstructor, rpcsWithInvalidConstructor.isEmpty());
    }

    // This method makes sure that for every RPC, the constructor that has the mandatory params is setting the values correctly
    @Test
    public void testMandatoryParamsValues() {
        // List of RPC names that have a constructor which is not settings the values for the mandatory params correctly
        List<String> rpcsWithInvalidConstructor = new ArrayList<>();

        // List of types that exist in java.lang.*
        List<String> javaLangBuiltInTypes = Arrays.asList("String", "Integer", "Float", "Double", "Boolean");

        // Loop through all RPCs that were loaded from RPC spec XML file
        // and make sure that the constructor that has the mandatory params is setting the values correctly
        for (String rpcName : rpcMandatoryParamsMapFromXml.keySet()) {
            Class aClass;
            try {
                aClass = Class.forName(RPC_PACKAGE_PREFIX + rpcName);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                continue;
            }

            List<Parameter> parameters = rpcMandatoryParamsMapFromXml.get(rpcName);
            List<Class<?>> mandatoryParamsTypes = new ArrayList<>();
            List<Object> mandatoryParamsValues = new ArrayList<>();

            // Loop through all mandatory params for the current RPC
            // and try to find the full Java type for each param
            // also assign a value for each param from com.smartdevicelink.test.Test class
            for (Parameter param : parameters) {
                String typeString = null;
                Class<?> type = null;
                String valueString = null;
                Object value = null;

                // Find the full Java type for the current param
                try {
                    if (param.isArray) {
                        type = List.class;
                    } else {
                        if (javaLangBuiltInTypes.contains(param.type)){
                            typeString = "java.lang." + param.type;
                        } else {
                            typeString = RPC_PACKAGE_PREFIX + param.type;
                        }
                        type = Class.forName(typeString);
                    }

                } catch (ClassNotFoundException e) {
                    // If the class was not found in the com.smartdevicelink.proxy.rpc package
                    // try to see if it can be found in com.smartdevicelink.proxy.rpc.enums package
                    typeString = RPC_PACKAGE_PREFIX + "enums." + param.type;
                    try {
                        type = Class.forName(typeString);
                    } catch (ClassNotFoundException e1) {
                        e1.printStackTrace();
                        assertTrue("Type: " + typeString + " cannot be found for RPC: " + rpcName , false);
                    }
                }


                // Assign a value for the current param from com.smartdevicelink.test.Test based of the param type
                try {
                    // --------------------------------------------- Exceptional cases ---------------------------------------------
                    // This case is exceptional because the setter changes the input if it is not all digits
                    if (rpcName.equals("DialNumber") && param.type.equals("String")){
                        value = "5558675309";
                    }
                    // -------------------------------------------------------------------------------------------------------------

                    if (value == null) {
                        valueString = "GENERAL_" + param.type.toUpperCase();
                        if (param.isArray){
                            valueString += "_LIST";
                        }
                        value = Class.forName("com.smartdevicelink.test.TestValues").getDeclaredField(valueString).get(null);
                    }

                } catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                    assertTrue("Value: " + valueString + " cannot be found for RPC: " + rpcName + ". Make sure that you declared that value in com.smartdevicelink.test.Test" , false);
                }

                mandatoryParamsTypes.add(type);
                mandatoryParamsValues.add(value);
            }


            // Create an instance of the RPC object using the constructor that has all the mandatory params
            Object instance = null;
            try {
                Constructor constructor = aClass.getConstructor(mandatoryParamsTypes.toArray(new Class<?>[mandatoryParamsTypes.size()]));
                instance = constructor.newInstance(mandatoryParamsValues.toArray(new Object[mandatoryParamsValues.size()]));
            } catch (NoSuchMethodException | IllegalAccessException |  InstantiationException | InvocationTargetException e) {
                e.printStackTrace();
                assertTrue("Constructor for RPC " + rpcName + " cannot be invoked. Make sure that the constructor parameters order and types are identical to the RPC specs", false);
            }


            // Loop through all getter methods for the instance and make sure that they are returning the expected values
            if (instance != null) {
                for (int i = 0; i < parameters.size(); i++) {
                    // Find the getter method name by taking the param name, capitalize the first letter, then add thw word "get" to the beginning
                    // for example if the param name is "buttonName" the method name will be "getButtonName"
                    String getterMethodName = "get" + parameters.get(i).name.substring(0, 1).toUpperCase() + parameters.get(i).name.substring(1);

                    // --------------------------------------------- Exceptional cases ---------------------------------------------
                    if (rpcName.equals("CancelInteraction") && getterMethodName.equals("getFunctionID")){
                        getterMethodName = "getInteractionFunctionID";
                    }
                    // -------------------------------------------------------------------------------------------------------------

                    try {
                        Method getterMethod = aClass.getMethod(getterMethodName);
                        Object val = getterMethod.invoke(instance);
                        if (val == null || !val.equals(mandatoryParamsValues.get(i))) {
                            rpcsWithInvalidConstructor.add(rpcName);
                            break;
                        }
                    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                        assertTrue("Method: " + getterMethodName + " cannot be found for RPC: " + rpcName + ". Make sure that the method exists and that the parameters order and types are identical to the RPC specs", false);

                    }
                }
            }
        }

        assertTrue("The following RPCs have a constructor that is not setting the mandatory params correctly: " + rpcsWithInvalidConstructor, rpcsWithInvalidConstructor.isEmpty());
    }
}
