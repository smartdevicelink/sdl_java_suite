package com.smartdevicelink.test.rpc;

import android.test.AndroidTestCase;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is a unit test class for the SmartDeviceLink library project
 * It makes sure that for each RPC, all mandatory parameters are set in a constructor
 */

public class RPCConstructorsTests extends AndroidTestCase {

    private final String XML_FILE_NAME = "xml/MOBILE_API_4.5.0.xml";
    private final String RPC_PACKAGE_PREFIX = "com.smartdevicelink.proxy.rpc.";
    private Map<String, List<String>> rpcMandatoryParamsMapFromXml;

    @Override
    public void setUp(){
        try {
            super.setUp();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Map that has keys correspond to the RPC names and values correspond to the
        // mandatory params for that RPC. All info are loaded from the RPC spec xml file
        rpcMandatoryParamsMapFromXml = getRPCMandatoryParamsMap(XML_FILE_NAME);

    }

    // This method parses the RPC spec xml file and returns a map that has
    // keys correspond to the RPC names and values correspond to the mandatory params for that RPC
    private Map<String, List<String>> getRPCMandatoryParamsMap(String fileName) {
        Map<String, List<String>> rpcMandatoryParamsMap = new HashMap<>();
        try {
            InputStream stream = this.mContext.getAssets().open(fileName);
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
                                } else if (rpcName.equals("EncodedSyncPData") || rpcName.equals("OnEncodedSyncPData") || rpcName.equals("EncodedSyncPDataResponse") || rpcName.equals("AppInfo")){
                                    ignoreRPC = true;
                                }
                            // -------------------------------------------------------------------------------------------------------------

                            if (!ignoreRPC) {
                                rpcMandatoryParamsMap.put(rpcName, new ArrayList<String>());
                            }
                        }
                        // Store the mandatory params for the current RPC in the map
                        if(name.equals("param") && !ignoreRPC){
                            boolean mandatory = Boolean.valueOf(myParser.getAttributeValue(null,"mandatory"));
                            if (mandatory) {
                                String paramType = myParser.getAttributeValue(null, "type");
                                // If the type of the param is an array of objects, we will make the type look like "List<Object>"
                                boolean paramIsArray = Boolean.valueOf(myParser.getAttributeValue(null, "array"));
                                if (paramIsArray){
                                    paramType = String.format("List<%s>", paramType);
                                }

                                // -------------- Exceptional cases because of mismatch between the RPC spec and the Android code --------------
                                if (paramType.equals("SyncMsgVersion")){
                                    paramType = "SdlMsgVersion";
                                } else if (rpcName.equals("GPSData") && paramType.equals("Float")){
                                    paramType = "Double";
                                } else if (rpcName.equals("TouchEvent") && paramType.equals("List<Integer>")){
                                    paramType = "List<Long>";
                                }
                                // -------------------------------------------------------------------------------------------------------------

                                rpcMandatoryParamsMap.get(rpcName).add(paramType);
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
    public void testRpcConstructorsMandatoryParams() {
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
            List<String> mandatoryParamsListFromXML = rpcMandatoryParamsMapFromXml.get(rpcName);
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
}
