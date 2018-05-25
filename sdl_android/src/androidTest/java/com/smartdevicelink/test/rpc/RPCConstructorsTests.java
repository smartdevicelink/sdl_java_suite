package com.smartdevicelink.test.rpc;

import android.content.Context;
import android.test.AndroidTestCase;

import com.smartdevicelink.proxy.SdlProxyALM;
import com.smartdevicelink.streaming.video.SdlRemoteDisplay;
import com.smartdevicelink.streaming.video.VideoStreamingParameters;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is a unit test class for the SmartDeviceLink library project
 * It makes sure that for each RPC, all mandatory parameters are set in a constructor
 */

public class RPCConstructorsTests extends AndroidTestCase {


    // Parse the RPC spec xml file and return a map that has
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
            while (event != XmlPullParser.END_DOCUMENT)  {
                String name = myParser.getName();
                switch (event){
                    case XmlPullParser.START_TAG:
                        // Store the RPC name in the map
                        if(name.equals("function") || name.equals("struct")){
                            rpcName = myParser.getAttributeValue(null,"name");
                            if (name.equals("function") && myParser.getAttributeValue(null, "messagetype").equals("response") && !rpcName.contains("Response")){
                                rpcName += "Response";
                            }
                            rpcMandatoryParamsMap.put(rpcName, new ArrayList<String>());
                        }
                        // Store the mandatory params for the current RPC in the map
                        if(name.equals("param")){
                            boolean mandatory = Boolean.valueOf(myParser.getAttributeValue(null,"mandatory"));
                            if (mandatory) {
                                String paramType = myParser.getAttributeValue(null, "type");
                                rpcMandatoryParamsMap.get(rpcName).add(paramType);
                            }
                        }
                        break;
                }
                event = myParser.next();
            }

        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
        }
        return rpcMandatoryParamsMap;
    }

    public void testRpcConstructorsMandatoryParams() {
        String fileName = "xml/MOBILE_API_4.5.0.xml";
        String packagePrefix = "com.smartdevicelink.proxy.rpc.";

        // Map that has keys correspond to the RPC names and values correspond to the
        // mandatory params for that RPC. All info are loaded from the RPC spec xml file
        Map<String, List<String>> rpcMandatoryParamsMapFromXml = getRPCMandatoryParamsMap(fileName);

        // List of RPC names that doesn't have a constructor that has all mandatory params
        List<String> rpcsWithInvalidConstructor = new ArrayList<>();

        // List of the RPC names that couldn't be found in code
        // potentially because of a mismatch between name in the RPC spec xml file and name in code
        List<String> rpcsFromXmlNotFoundInCode = new ArrayList<>();

        // Loop through all RPCs that were loaded from RPC spec sml file
        // and make sure that every RPC has a constructor that has all mandatory params
        for (String rpcName : rpcMandatoryParamsMapFromXml.keySet()) {
            Class aClass;
            try {
                aClass = Class.forName(packagePrefix + rpcName);
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
                for (Parameter param : constructor.getParameters()){
                    mandatoryParamsListFromCode.add(param.getType().getSimpleName());
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
        assertTrue("The following RPCs don't have a constructor that has all the mandatory params " + rpcsWithInvalidConstructor, rpcsWithInvalidConstructor.isEmpty());
    }
}
