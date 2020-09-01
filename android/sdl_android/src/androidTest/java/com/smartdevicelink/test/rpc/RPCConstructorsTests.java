package com.smartdevicelink.test.rpc;


import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.smartdevicelink.proxy.rpc.enums.SystemCapabilityType;

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

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static junit.framework.TestCase.assertTrue;

@RunWith(AndroidJUnit4.class)
public class RPCConstructorsTests {

    private final String XML_FILE_NAME = "xml/MOBILE_API.xml";
    private final String RPC_PACKAGE_PREFIX = "com.smartdevicelink.proxy.rpc.";
    private Map<String, List<Parameter>> rpcMandatoryParamsMapFromXml;
    private Map<String, List<Parameter>> rpcAllParamsMapFromXml;

    private class Parameter {
        private String rpcName;
        private String name;
        private String type;
        private Class<?> javaType;
        private boolean isArray;
        private boolean isMandatory;
        private String setterName;
        private String getterName1;
        private String getterName2;
        private Object value;
        private boolean skip;


        public Parameter setRPCName(String rpcName) {
            this.rpcName = rpcName;
            return this;
        }

        public Parameter setName(String name) {
            this.name = name;
            return this;
        }

        public Parameter setType(String type) {
            this.type = type;
            return this;
        }

        public Parameter setJavaType(Class<?> javaType) {
            this.javaType = javaType;
            return this;
        }

        public Parameter setArray(boolean array) {
            isArray = array;
            return this;
        }

        public Parameter setMandatory(boolean mandatory) {
            isMandatory = mandatory;
            return this;
        }

        public Parameter setSetterName(String setterName) {
            this.setterName = setterName;
            return this;
        }

        public Parameter setGetterName1(String getterName1) {
            this.getterName1 = getterName1;
            return this;
        }

        public Parameter setGetterName2(String getterName2) {
            this.getterName2 = getterName2;
            return this;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        public Parameter setSkip(boolean skip) {
            this.skip = skip;
            return this;
        }
    }

    @Before
    public void setUp(){
        // Map that has keys correspond to the RPC names and values correspond to the params for that RPC.
        rpcMandatoryParamsMapFromXml = getRPCParamsMap(XML_FILE_NAME, true);
        rpcAllParamsMapFromXml = getRPCParamsMap(XML_FILE_NAME, false);
    }

    // This method parses the RPC spec xml file and returns a map that has
    // keys correspond to the RPC names and values correspond to the params for that RPC
    private Map<String, List<Parameter>> getRPCParamsMap(String fileName, boolean includeMandatoryOnly) {
        Map<String, List<Parameter>> rpcParamsMap = new HashMap<>();
        try {
            InputStream stream = getInstrumentation().getTargetContext().getAssets().open(fileName);
            XmlPullParserFactory xmlFactoryObject = XmlPullParserFactory.newInstance();
            XmlPullParser myParser = xmlFactoryObject.newPullParser();
            myParser.setInput(stream, null);
            int event = myParser.getEventType();
            String rpcName = null;
            boolean ignoreRPC = false;
            String setterMethodName;
            String getterMethodName1;
            String getterMethodName2;
            Class<?> javaParamType;
            boolean skipParam;
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
                                rpcParamsMap.put(rpcName, new ArrayList<Parameter>());
                            }
                        }
                        // Store the params for the current RPC in the map
                        if(name.equals("param") && myParser.getAttributeValue(null, "until") == null && !ignoreRPC){
                            setterMethodName = null;
                            getterMethodName1 = null;
                            getterMethodName2 = null;
                            javaParamType = null;
                            skipParam = false;
                            boolean isMandatory = Boolean.valueOf(myParser.getAttributeValue(null,"mandatory"));
                            if (isMandatory || !includeMandatoryOnly) {
                                String paramName = myParser.getAttributeValue(null, "name");
                                String paramType = myParser.getAttributeValue(null, "type");
                                boolean isArray = Boolean.valueOf(myParser.getAttributeValue(null, "array"));

                                // -------------- Exceptional cases because of mismatch between the RPC spec and the Android code --------------
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

                                setterMethodName = "set" + paramName.substring(0, 1).toUpperCase() + paramName.substring(1);

                                if (paramType.equals("SyncMsgVersion")){
                                    paramType = "SdlMsgVersion";
                                } else if (rpcName.equals("GPSData") && paramType.equals("Float")){
                                    paramType = "Double";
                                } else if (rpcName.equals("TouchEvent") && paramType.equals("Integer") && isArray){
                                    paramType = "Long";
                                } else if (paramType.equals("OASISAddress")) {
                                    paramType = "OasisAddress";
                                } else if (rpcName.equals("VideoStreamingCapability") && paramType.equals("Float")) {
                                    paramType = "Double";
                                } else if (rpcName.equals("UnsubscribeVehicleData") && setterMethodName.equals("setCloudAppVehicleID")) {
                                    paramType = "boolean";
                                } else if (rpcName.equals("CancelInteraction") && setterMethodName.equals("setFunctionID")) {
                                    setterMethodName = "setInteractionFunctionID";
                                } else if (rpcName.equals("NavigationCapability") && setterMethodName.equals("setGetWayPointsEnabled")) {
                                    setterMethodName = "setWayPointsEnabled";
                                } else if (rpcName.equals("UnsubscribeWayPointsResponse") && setterMethodName.equals("setGetWayPointsEnabled")) {
                                    setterMethodName = "setWayPointsEnabled";
                                } else if (rpcName.equals("HMICapabilities") && setterMethodName.equals("setNavigation")) {
                                    setterMethodName = "setNavigationAvilable";
                                } else if (rpcName.equals("HMICapabilities") && setterMethodName.equals("setPhoneCall")) {
                                    setterMethodName = "setPhoneCallAvilable";
                                } else if (rpcName.equals("HMICapabilities") && setterMethodName.equals("setDisplays")) {
                                    setterMethodName = "setDisplaysCapabilityAvailable";
                                } else if (rpcName.equals("HMICapabilities")) {
                                    setterMethodName += "Available";
                                } else if (rpcName.equals("VideoStreamingCapability") && setterMethodName.equals("setHapticSpatialDataSupported")) {
                                    setterMethodName = "setIsHapticSpatialDataSupported";
                                } else if (rpcName.equals("OnDriverDistraction") && setterMethodName.equals("setLockScreenDismissalEnabled")) {
                                    setterMethodName = "setLockscreenDismissibility";
                                    paramType = "boolean";
                                } else if (rpcName.equals("OnDriverDistraction") && setterMethodName.equals("setLockScreenDismissalWarning")) {
                                    setterMethodName = "setLockscreenWarningMessage";
                                } else if (rpcName.equals("PublishAppServiceResponse") && setterMethodName.equals("setAppServiceRecord")) {
                                    setterMethodName = "setServiceRecord";
                                } else if (setterMethodName.equals("setFuelLevel_State")) {
                                    setterMethodName = "setFuelLevelState";
                                } else if (rpcName.equals("LightCapabilities") && setterMethodName.equals("setRgbColorSpaceAvailable")) {
                                    setterMethodName = "setRGBColorSpaceAvailable";
                                } else if (rpcName.equals("CloudAppProperties") && setterMethodName.equals("setEnabled")) {
                                    paramType = "boolean";
                                } else if (rpcName.equals("DateTime") && setterMethodName.equals("setMillisecond")) {
                                    setterMethodName = "setMilliSecond";
                                } else if (rpcName.equals("DateTime") && setterMethodName.equals("setTz_hour")) {
                                    setterMethodName = "setTzHour";
                                } else if (rpcName.equals("DateTime") && setterMethodName.equals("setTz_minute")) {
                                    setterMethodName = "setTzMinute";
                                } else if (rpcName.equals("PutFile") && setterMethodName.equals("setCrc")) {
                                    setterMethodName = "setCRC";
                                    paramType = "Long";
                                } else if (rpcName.equals("AppServiceManifest") && setterMethodName.equals("setHandledRPCs")) {
                                    setterMethodName = "setHandledRpcs";
                                } else if (rpcName.equals("LocationDetails") && setterMethodName.equals("setHandledRPCs")) {
                                    setterMethodName = "setHandledRpcs";
                                } else if (rpcName.equals("SendLocation") && setterMethodName.equals("setLongitudeDegrees")) {
                                    paramType = "Double";
                                } else if (rpcName.equals("SendLocation") && setterMethodName.equals("setLatitudeDegrees")) {
                                    paramType = "Double";
                                } else if (rpcName.equals("Grid") && setterMethodName.equals("setColspan")) {
                                    setterMethodName = "setColSpan";
                                } else if (rpcName.equals("Grid") && setterMethodName.equals("setRowspan")) {
                                    setterMethodName = "setRowSpan";
                                } else if (rpcName.equals("Grid") && setterMethodName.equals("setLevelspan")) {
                                    setterMethodName = "setLevelSpan";
                                } else if (rpcName.equals("HeadLampStatus") && setterMethodName.equals("setAmbientLightSensorStatus")) {
                                    setterMethodName = "setAmbientLightStatus";
                                } else if (rpcName.equals("GetVehicleData") && setterMethodName.equals("setCloudAppVehicleID")) {
                                    paramType = "boolean";
                                } else if (rpcName.equals("GetVehicleDataResponse") && Arrays.asList("setInstantFuelConsumption", "setFuelLevel", "setSpeed", "setExternalTemperature", "setEngineTorque", "setAccPedalPosition", "setSteeringWheelAngle").contains(setterMethodName)) {
                                    paramType = "Double";
                                } else if (rpcName.equals("RdsData") && setterMethodName.equals("setPS")) {
                                    setterMethodName = "setProgramService";
                                } else if (rpcName.equals("RdsData") && setterMethodName.equals("setCT")) {
                                    setterMethodName = "setClockText";
                                } else if (rpcName.equals("RdsData") && setterMethodName.equals("setRT")) {
                                    setterMethodName = "setRadioText";
                                } else if (rpcName.equals("RdsData") && setterMethodName.equals("setPI")) {
                                    setterMethodName = "setProgramIdentification";
                                } else if (rpcName.equals("RdsData") && setterMethodName.equals("setPTY")) {
                                    setterMethodName = "setProgramType";
                                } else if (rpcName.equals("RdsData") && setterMethodName.equals("setTP")) {
                                    setterMethodName = "setTrafficProgram";
                                } else if (rpcName.equals("RdsData") && setterMethodName.equals("setTA")) {
                                    setterMethodName = "setTrafficAnnouncement";
                                } else if (rpcName.equals("RdsData") && setterMethodName.equals("setREG")) {
                                    setterMethodName = "setRegion";
                                } else if (rpcName.equals("RadioControlCapabilities") && setterMethodName.equals("setSiriusxmRadioAvailable")) {
                                    setterMethodName = "setSiriusXMRadioAvailable";
                                } else if (rpcName.equals("GetCloudAppPropertiesResponse") && setterMethodName.equals("setProperties")) {
                                    setterMethodName = "setCloudAppProperties";
                                } else if (rpcName.equals("GetFileResponse") && setterMethodName.equals("setCrc")) {
                                    setterMethodName = "setCRC";
                                } else if (rpcName.equals("RegisterAppInterfaceResponse") && setterMethodName.equals("setPcmStreamCapabilities")) {
                                    setterMethodName = "setPcmStreamingCapabilities";
                                } else if (rpcName.equals("SubscribeVehicleData") && setterMethodName.equals("setElectronicParkBrakeStatus")) {
                                    paramType = "boolean";
                                } else if (rpcName.equals("SubscribeVehicleData") && setterMethodName.equals("setCloudAppVehicleID")) {
                                    paramType = "boolean";
                                } else if (rpcName.equals("ModuleInfo") && setterMethodName.equals("setLocation")) {
                                    setterMethodName = "setModuleLocation";
                                } else if (rpcName.equals("ModuleInfo") && setterMethodName.equals("setServiceArea")) {
                                    setterMethodName = "setModuleServiceArea";
                                } else if (rpcName.equals("ModuleInfo") && setterMethodName.equals("setAllowMultipleAccess")) {
                                    setterMethodName = "setMultipleAccessAllowance";
                                } else if (rpcName.equals("OnVehicleData") && setterMethodName.equals("setSpeed")) {
                                    paramType = "Double";
                                } else if (rpcName.equals("OnVehicleData") && setterMethodName.equals("setFuelLevel")) {
                                    paramType = "Double";
                                } else if (rpcName.equals("OnVehicleData") && setterMethodName.equals("setInstantFuelConsumption")) {
                                    paramType = "Double";
                                } else if (rpcName.equals("OnVehicleData") && setterMethodName.equals("setExternalTemperature")) {
                                    paramType = "Double";
                                } else if (rpcName.equals("OnVehicleData") && setterMethodName.equals("setEngineTorque")) {
                                    paramType = "Double";
                                } else if (rpcName.equals("OnVehicleData") && setterMethodName.equals("setAccPedalPosition")) {
                                    paramType = "Double";
                                } else if (rpcName.equals("OnVehicleData") && setterMethodName.equals("setSteeringWheelAngle")) {
                                    paramType = "Double";
                                } else if (rpcName.equals("GetInteriorVehicleDataConsentResponse") && setterMethodName.equals("setAllowed")) {
                                    setterMethodName = "setAllowances";
                                } else if (rpcName.equals("SeatLocationCapability") && setterMethodName.equals("setColumns")) {
                                    setterMethodName = "setCols";
                                } else if (rpcName.equals("ShowConstantTbt") && setterMethodName.equals("setDistanceToManeuver")) {
                                    paramType = "Double";
                                } else if (rpcName.equals("ShowConstantTbt") && setterMethodName.equals("setDistanceToManeuverScale")) {
                                    paramType = "Double";
                                } else if (rpcName.equals("SingleTireStatus") && setterMethodName.equals("setTpms")) {
                                    setterMethodName = "setTPMS";
                                } else if (rpcName.equals("VehicleDataResult") && setterMethodName.equals("setOemCustomDataType")) {
                                    setterMethodName = "setOEMCustomVehicleDataType";
                                }  else if (rpcName.equals("SystemCapability") && !setterMethodName.equals("setSystemCapabilityType")) {
                                    setterMethodName = "setCapabilityForType";
                                    paramType = "SystemCapabilityType";
                                } else if (rpcName.equals("UnsubscribeWayPointsResponse") && paramName.equals("wayPoints")) {
                                    skipParam = true;
                                } else if (rpcName.equals("UnsubscribeVehicleDataResponse") && paramName.equals("clusterModes")) {
                                    skipParam = true;
                                } else if (rpcName.equals("ClimateControlCapabilities") && paramName.equals("currentTemperatureAvailable")) {
                                    skipParam = true;
                                } else if (rpcName.equals("SubscribeVehicleDataResponse") && paramName.equals("clusterModes")) {
                                    skipParam = true;
                                }

                                // -------------------------------------------------------------------------------------------------------------

                                javaParamType = findJavaTypeForParam(paramType, isArray);
                                getterMethodName1 = "get" + setterMethodName.substring(3);
                                if (paramType.equalsIgnoreCase("boolean")) {
                                    getterMethodName2 = "is" + setterMethodName.substring(3);
                                }

                                Parameter param = new Parameter()
                                        .setRPCName(rpcName)
                                        .setName(paramName)
                                        .setType(paramType)
                                        .setJavaType(javaParamType)
                                        .setArray(isArray)
                                        .setMandatory(isMandatory)
                                        .setSkip(skipParam)
                                        .setSetterName(setterMethodName)
                                        .setGetterName1(getterMethodName1)
                                        .setGetterName2(getterMethodName2)
                                        ;

                                rpcParamsMap.get(rpcName).add(param);
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
        return rpcParamsMap;
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

    // This method returns the correct java reflection method in a specific class
    private Method getMethod(Class aClass, String methodName, Class<?> paramType, String rpcName) throws NoSuchMethodException {
        Method method;
        if (methodName == null) {
            throw new NoSuchMethodException();
        }
        if (paramType == null) {
            if (rpcName.equals("SystemCapability") && !methodName.contains("SystemCapabilityType")) {
                method = aClass.getMethod(methodName, SystemCapabilityType.class);
            } else {
                method = aClass.getMethod(methodName);
            }
        } else {
            if (rpcName.equals("SystemCapability") && !methodName.contains("SystemCapabilityType")) {
                method = aClass.getMethod(methodName, paramType, Object.class);
            } else {
                method = aClass.getMethod(methodName, paramType);
            }
        }
        return method;
    }

    // This method returns the full Java type for a param
    private Class<?> findJavaTypeForParam(String type, boolean isArray) {
        String typeString = null;
        Class<?> javaType = null;

        // List of types that exist in java.lang.*
        List<String> javaLangBuiltInTypes = Arrays.asList("String", "Integer", "Long", "Float", "Double", "Boolean");

        // List of primitive types in java
        List<String> javaLangPrimitiveTypes = Arrays.asList("int", "long", "float", "double", "boolean");

        // Find the full Java type for the current param
        try {
            if (isArray) {
                javaType = List.class;
            } else if (javaLangPrimitiveTypes.contains(type)) {
                if (type.equals("int")) {
                    javaType = int.class;
                } else if (type.equals("float")) {
                    javaType = float.class;
                } else if (type.equals("double")) {
                    javaType = double.class;
                } else if (type.equals("boolean")) {
                    javaType = boolean.class;
                }
            } else {
                if (javaLangBuiltInTypes.contains(type)) {
                    typeString = "java.lang." + type;
                } else {
                    typeString = RPC_PACKAGE_PREFIX + type;
                }
                javaType = Class.forName(typeString);
            }

        } catch (ClassNotFoundException e) {
            // If the class was not found in the com.smartdevicelink.proxy.rpc package
            // try to see if it can be found in com.smartdevicelink.proxy.rpc.enums package
            typeString = RPC_PACKAGE_PREFIX + "enums." + type;
            try {
                javaType = Class.forName(typeString);
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            }
        }
        return javaType;
    }

    // This method makes sure that for every RPC, the constructor that has the mandatory params is setting the values correctly
    @Test
    public void testMandatoryParamsValues() {
        // List of RPC names that have a constructor which is not settings the values for the mandatory params correctly
        List<String> rpcsWithInvalidConstructor = new ArrayList<>();

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
                String valueString = null;
                Object value = null;

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

                mandatoryParamsTypes.add(param.javaType);
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
                    try {
                        Method getterMethod = getMethod(aClass, parameters.get(i).getterName1, null, rpcName);
                        Object val = getterMethod.invoke(instance);
                        if (val == null || !val.equals(mandatoryParamsValues.get(i))) {
                            rpcsWithInvalidConstructor.add(rpcName);
                            break;
                        }
                    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                        assertTrue("Method: " + parameters.get(i).getterName1 + " cannot be found for RPC: " + rpcName + ". Make sure that the method exists and that the parameters order and types are identical to the RPC specs", false);
                    }
                }
            }
        }

        assertTrue("The following RPCs have a constructor that is not setting the mandatory params correctly: " + rpcsWithInvalidConstructor, rpcsWithInvalidConstructor.isEmpty());
    }

    /**
     * This method makes sure that for every param in every RPC:
     * - A setter exists and its name matches the RPC spec
     * - The setter return type matches the RPC type (to make RPCs chainable)
     * - A getter exists and its name matches the RPC spec
     */
    @Test
    public void testParamsSettersAndGetters() {
        List<String> errors = new ArrayList<>();

        // Loop through all RPCs that were loaded from RPC spec XML file
        for (String rpcName : rpcAllParamsMapFromXml.keySet()) {
            Class aClass;
            try {
                aClass = Class.forName(RPC_PACKAGE_PREFIX + rpcName);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                errors.add("Class not found for rpc: "+ rpcName + ". \n");
                continue;
            }

            // Loop through all params for the current RPC and make sure everyone has a a setter and a getter
            List<Parameter> parameters = rpcAllParamsMapFromXml.get(rpcName);
            for (int i = 0; i < parameters.size(); i++) {
                Parameter parameter = parameters.get(i);
                if (parameter.skip) {
                    continue;
                }

                // Confirm that the setter is correct
                try {
                    Method setterMethod = getMethod(aClass, parameter.setterName, parameter.javaType, rpcName);
                    List<String> expectedReturnTypes = Arrays.asList(aClass.getName(), aClass.getSuperclass().getName());
                    String actualReturnType = setterMethod.getReturnType().getName();
                    if (!expectedReturnTypes.contains(actualReturnType)) {
                        String errMsg = rpcName + "." + parameter.setterName + "() is expected to return one of these types: " + expectedReturnTypes + " but it returns: " + actualReturnType + ". \n";
                        errors.add(errMsg);
                    }
                } catch (NoSuchMethodException e) {
                    String errMsg = rpcName + "." + parameter.setterName + "(" + parameter.type + ")" + " cannot be found. Make sure that the method exists. \n";
                    errors.add(errMsg);
                }

                // Confirm that the getter is correct
                Method getterMethod = null;
                try {
                    // --------------------------------------------- Exceptional cases ---------------------------------------------
                    if (parameter.getterName1.contains("Avilable")) {
                        continue;
                    } else if (parameter.getterName1.equals("getSeats")) {
                        parameter.getterName1 = "getSeatLocations";
                    }
                    // -------------------------------------------------------------------------------------------------------------
                    getterMethod = getMethod(aClass, parameter.getterName1, null, rpcName);
                } catch (NoSuchMethodException e) {
                    try {
                        getterMethod = getMethod(aClass, parameter.getterName2, null, rpcName);
                    } catch (NoSuchMethodException ex) {
                        ex.printStackTrace();
                        String errMsg = String.format(rpcName + "." + parameter.getterName1 + "()" + "%s" + " cannot be found. Make sure that the method exists. \n", parameter.type.equalsIgnoreCase("boolean")? "/" + parameter.getterName2 + "()" : "");
                        errors.add(errMsg);
                    }
                }
            }
        }

        assertTrue("There are " + errors.size() + " errors: \n" + errors, errors.isEmpty());
    }
}
