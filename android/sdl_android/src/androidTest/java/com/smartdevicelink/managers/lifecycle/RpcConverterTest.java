package com.smartdevicelink.managers.lifecycle;

import androidx.test.runner.AndroidJUnit4;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.ProtocolMessage;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.protocol.enums.MessageType;
import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.util.Version;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.InvocationTargetException;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class RpcConverterTest {

    private static final String RPC_PACKAGE             = "com.smartdevicelink.proxy.rpc.";
    private static final String RESPONSE_KEY            = "Response";
    private static final Version MAX_RPC_VERSION        = new Version(5,1,0);

    /**
     * The RPC converter relies on the function id json name to match the class name exactly.
     * Therefore we will test to ensure that they match up first.
     */
    @Test
    public void testFunctionIdsAgainstFileNames(){
        FunctionID[] functionIDs = FunctionID.values();
        for(FunctionID functionID : functionIDs) {
            switch (functionID){
                case RESERVED:
                case SYNC_P_DATA:
                case ON_SYNC_P_DATA:
                case ENCODED_SYNC_P_DATA:
                case ON_ENCODED_SYNC_P_DATA:
                case GENERIC_RESPONSE:
                case STREAM_RPC:
                case ON_LOCK_SCREEN_STATUS:
                case ON_SDL_CHOICE_CHOSEN:
                case ON_STREAM_RPC:
                    continue;
                default:

            }
            assertNotNull(getClassForFunctionId(functionID, false));
            if (FunctionID.REGISTER_APP_INTERFACE.getId() <= functionID.getId()
                    && functionID.getId() < FunctionID.ON_HMI_STATUS.getId()) {
                //Test response of the request
                assertNotNull(getClassForFunctionId(functionID, true));
            }
        }
    }


    private Class getClassForFunctionId(FunctionID functionID, boolean isResponse) {
        try {
            StringBuilder rpcClassName = new StringBuilder();
            rpcClassName.append(RPC_PACKAGE);

            switch (functionID) {
                case RESERVED:
                case SYNC_P_DATA:
                case ON_SYNC_P_DATA:
                case ENCODED_SYNC_P_DATA:
                case ON_ENCODED_SYNC_P_DATA:
                case GENERIC_RESPONSE:
                case STREAM_RPC:
                case ON_LOCK_SCREEN_STATUS:
                case ON_SDL_CHOICE_CHOSEN:
                case ON_STREAM_RPC:
                    return null;
                case SHOW_CONSTANT_TBT:
                    rpcClassName.append("ShowConstantTbt");
                    break;
                default:
                    rpcClassName.append(functionID);
                    break;
            }


            if (isResponse) {
                //Test response of the request
                rpcClassName.append(RESPONSE_KEY);
            }
            return Class.forName(rpcClassName.toString());

        } catch (Exception e) {

        }
        return null;
    }

    @Test
    public void testRpcCreation(){

        FunctionID[] functionIDs = FunctionID.values();

        for(FunctionID functionID : functionIDs){
            switch (functionID){
                case RESERVED:
                case SYNC_P_DATA:
                case ON_SYNC_P_DATA:
                case ENCODED_SYNC_P_DATA:
                case ON_ENCODED_SYNC_P_DATA:
                case GENERIC_RESPONSE:
                case STREAM_RPC:
                case ON_LOCK_SCREEN_STATUS:
                case ON_SDL_CHOICE_CHOSEN:
                case ON_STREAM_RPC:
                    continue;
                default:

            }
            assertRpc(getClassForFunctionId(functionID, false));

            if (FunctionID.REGISTER_APP_INTERFACE.getId() <= functionID.getId()
                    && functionID.getId() < FunctionID.ON_HMI_STATUS.getId()) {
                //Test response of the request
                assertRpc(getClassForFunctionId(functionID, true));
            }

        }
    }

    private void assertRpc(Class rpcClass){
        RPCMessage message = generateRpcMessage(rpcClass);
        assertNotNull(message);
        ProtocolMessage protocolMessage = generateProtocolMessageForRpc(message);
        assertNotNull(protocolMessage);
        RPCMessage newMessage = RpcConverter.extractRpc(protocolMessage,MAX_RPC_VERSION);
        assertNotNull(newMessage);

        assertEquals(message.getMessageType(), newMessage.getMessageType());
        assertEquals(message.getFunctionID(), newMessage.getFunctionID());
    }

    private RPCMessage generateRpcMessage(Class rpcClass){
        try {
            java.lang.reflect.Constructor rpcConstructor =  rpcClass.getConstructor();
            return (RPCMessage)rpcConstructor.newInstance();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    private ProtocolMessage generateProtocolMessageForRpc(RPCMessage message){
        try {

            message.format(MAX_RPC_VERSION,true);
            byte[] msgBytes = JsonRPCMarshaller.marshall(message, (byte)5);

            ProtocolMessage pm = new ProtocolMessage();
            pm.setData(msgBytes);
            pm.setSessionID((byte)0);

            pm.setMessageType(MessageType.RPC);
            pm.setSessionType(SessionType.RPC);
            pm.setFunctionID(FunctionID.getFunctionId(message.getFunctionName()));
            pm.setPayloadProtected(message.isPayloadProtected());

            if (message.getMessageType().equals(RPCMessage.KEY_REQUEST)) {
                pm.setRPCType((byte)0x00);
            }else if (message.getMessageType().equals(RPCMessage.KEY_RESPONSE)){
                pm.setRPCType((byte)0x01);
            }else if (message.getMessageType().equals(RPCMessage.KEY_NOTIFICATION)){
                pm.setRPCType((byte)0x02);
            }else{
                return null;
            }

            if (message.getBulkData() != null){
                pm.setBulkData(message.getBulkData());
            }

            return pm;

        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
        return null;
    }
}
