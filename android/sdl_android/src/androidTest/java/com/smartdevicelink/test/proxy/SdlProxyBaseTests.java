package com.smartdevicelink.test.proxy;

import android.content.Context;
import android.support.test.runner.AndroidJUnit4;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.smartdevicelink.exception.SdlException;
import com.smartdevicelink.exception.SdlExceptionCause;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.SdlProxyALM;
import com.smartdevicelink.proxy.SdlProxyBase;
import com.smartdevicelink.proxy.SdlProxyBuilder;
import com.smartdevicelink.proxy.SdlProxyConfigurationResources;
import com.smartdevicelink.proxy.interfaces.IProxyListenerALM;
import com.smartdevicelink.proxy.rpc.Show;
import com.smartdevicelink.proxy.rpc.ShowResponse;
import com.smartdevicelink.proxy.rpc.Speak;
import com.smartdevicelink.proxy.rpc.SpeakResponse;
import com.smartdevicelink.proxy.rpc.enums.Result;
import com.smartdevicelink.proxy.rpc.listeners.OnMultipleRequestListener;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCResponseListener;
import com.smartdevicelink.streaming.video.SdlRemoteDisplay;
import com.smartdevicelink.streaming.video.VideoStreamingParameters;
import com.smartdevicelink.test.streaming.video.SdlRemoteDisplayTest;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static android.support.test.InstrumentationRegistry.getTargetContext;

@RunWith(AndroidJUnit4.class)
public class SdlProxyBaseTests {
    public static final String TAG = "SdlProxyBaseTests";

    int onUpdateListenerCounter, onFinishedListenerCounter, onResponseListenerCounter, onErrorListenerCounter, remainingRequestsExpected;

    /**
     * Test SdlProxyBase for handling null SdlProxyConfigurationResources
     */
    @Test
    public void testNullSdlProxyConfigurationResources() {
        SdlProxyALM proxy = null;
        SdlProxyBuilder.Builder builder = new SdlProxyBuilder.Builder(mock(IProxyListenerALM.class), "appId", "appName", true, getTargetContext());
        SdlProxyConfigurationResources config = new SdlProxyConfigurationResources("path", (TelephonyManager) getTargetContext().getSystemService(Context.TELEPHONY_SERVICE));
        //Construct with a non-null SdlProxyConfigurationResources
        builder.setSdlProxyConfigurationResources(config);
        try {
            proxy = builder.build();
        } catch (Exception e) {
            Log.v(TAG, "Exception in testNullSdlProxyConfigurationResources, testing non null SdlProxyConfigurationResources");
            if (!(e instanceof SdlException) || !((SdlException) e).getSdlExceptionCause().equals(SdlExceptionCause.BLUETOOTH_ADAPTER_NULL)) {
                e.printStackTrace();
                Assert.fail("Exception in testNullSdlProxyConfigurationResources - \n" + e.toString());
            }
        }

        if (proxy != null) {
            try {
                proxy.dispose();
                proxy = null;
            }catch(SdlException e){
                e.printStackTrace();
            }
        }

        //Construct with a null SdlProxyConfigurationResources
        builder.setSdlProxyConfigurationResources(null);
        try {
            proxy = builder.build();
        } catch (Exception e) {
            Log.v(TAG, "Exception in testNullSdlProxyConfigurationResources, testing null SdlProxyConfigurationResources");
            if (!(e instanceof SdlException) || !((SdlException) e).getSdlExceptionCause().equals(SdlExceptionCause.BLUETOOTH_ADAPTER_NULL)) {
                e.printStackTrace();
                Assert.fail("Exception in testNullSdlProxyConfigurationResources, testing null SdlProxyConfigurationResources");
            }
        }
        if (proxy != null) {
            try {
                proxy.dispose();
                proxy = null;
            }catch(SdlException e){
                e.printStackTrace();
            }
        }

        //Construct with a non-null SdlProxyConfigurationResources and a null TelephonyManager
        config.setTelephonyManager(null);
        builder.setSdlProxyConfigurationResources(config);
        try {
            proxy = builder.build();
        } catch (Exception e) {
            Log.v(TAG, "Exception in testNullSdlProxyConfigurationResources, testing null TelephonyManager");
            if (!(e instanceof SdlException) || !((SdlException) e).getSdlExceptionCause().equals(SdlExceptionCause.BLUETOOTH_ADAPTER_NULL)) {
                Assert.fail("Exception in testNullSdlProxyConfigurationResources, testing null TelephonyManager");
            }
        }
        if (proxy != null) {
            try {
                proxy.dispose();
                proxy = null;
            }catch(SdlException e){
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testRemoteDisplayStreaming(){
        SdlProxyALM proxy = null;
        SdlProxyBuilder.Builder builder = new SdlProxyBuilder.Builder(mock(IProxyListenerALM.class), "appId", "appName", true, getTargetContext());
        try{
            proxy = builder.build();
            //	public void startRemoteDisplayStream(Context context, final Class<? extends SdlRemoteDisplay> remoteDisplay, final VideoStreamingParameters parameters, final boolean encrypted){
            Method m = SdlProxyALM.class.getDeclaredMethod("startRemoteDisplayStream", Context.class, SdlRemoteDisplay.class, VideoStreamingParameters.class, boolean.class);
            assertNotNull(m);
            m.setAccessible(true);
            m.invoke(proxy,getTargetContext(), SdlRemoteDisplayTest.MockRemoteDisplay.class, (VideoStreamingParameters)null, false);
            assert true;

        }catch (Exception e){
            assert false;
        }
    }

    @Test
    public void testSendRPCsAllSucceed(){
        testSendMultipleRPCs(false, 1);
    }

    @Test
    public void testSendRPCsSomeFail(){
        testSendMultipleRPCs(false, 2);
    }

    @Test
    public void testSendSequentialRPCsAllSucceed(){
        testSendMultipleRPCs(true, 1);
    }

    @Test
    public void testSendSequentialRPCsSomeFail(){
        testSendMultipleRPCs(true, 2);
    }

    private void testSendMultipleRPCs(boolean sequentialSend, int caseNumber){
        final List<RPCRequest> rpcsList = new ArrayList<>();
        final List<RPCRequest> rpcsTempList = new ArrayList<>();
        final HashMap<RPCRequest, OnRPCResponseListener> requestsMap = new HashMap<>();
        onUpdateListenerCounter = 0;
        onFinishedListenerCounter = 0;
        onResponseListenerCounter = 0;
        onErrorListenerCounter = 0;


        // We extend the SdlProxyBase to be able to override getIsConnected() &  sendRPCMessagePrivate() methods so they don't cause issues when trying to send RPCs
        // Because otherwise, they will throw exception cause there not actual connection to head unit
        SdlProxyBase proxy = new SdlProxyBase() {

            @Override
            public Boolean getIsConnected() {
                return true;
            }

            @Override
            protected void sendRPCMessagePrivate(RPCMessage message) {
                // Do nothing
            }
        };


        // We need to get list of all OnRPCResponseListeners so we can trigger onResponse/onError for each RPC to fake a response from Core
        final Answer<Void> answer = new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                RPCRequest request = (RPCRequest) invocation.getMock();
                OnRPCResponseListener listener = (OnRPCResponseListener) args[0];
                requestsMap.put(request, listener);
                rpcsTempList.add(request);
                return null;
            }
        };


        // Prepare RPCs to send
        Speak speak = mock(Speak.class);
        doReturn(RPCMessage.KEY_REQUEST).when(speak).getMessageType();
        doAnswer(answer).when(speak).setOnRPCResponseListener(any(OnRPCResponseListener.class));
        rpcsList.add(speak);

        Show show = mock(Show.class);
        doReturn(RPCMessage.KEY_REQUEST).when(show).getMessageType();
        doAnswer(answer).when(show).setOnRPCResponseListener(any(OnRPCResponseListener.class));
        rpcsList.add(show);


        // Send RPCs
        remainingRequestsExpected = rpcsList.size();
        OnMultipleRequestListener onMultipleRequestListener = new OnMultipleRequestListener() {
            @Override
            public void onUpdate(int remainingRequests) {
                onUpdateListenerCounter++;
                assertEquals(remainingRequestsExpected, remainingRequests);
            }

            @Override
            public void onFinished() {
                onFinishedListenerCounter++;
            }

            @Override
            public void onError(int correlationId, Result resultCode, String info) {
                onErrorListenerCounter++;
                remainingRequestsExpected--;
            }

            @Override
            public void onResponse(int correlationId, RPCResponse response) {
                onResponseListenerCounter++;
                remainingRequestsExpected--;
            }
        };
        try {
            if (sequentialSend) {
                proxy.sendSequentialRequests(rpcsList, onMultipleRequestListener);
            } else {
                proxy.sendRequests(rpcsList, onMultipleRequestListener);
            }
            assertTrue(true);
        } catch (SdlException e) {
            e.printStackTrace();
            fail();
        }


        // Trigger fake RPC responses
        int onUpdateListenerCounterExpected = 0, onFinishedListenerCounterExpected = 0, onResponseListenerCounterExpected = 0, onErrorListenerCounterExpected = 0;
        switch (caseNumber){
            case 1: // All RPCs succeed
                onUpdateListenerCounterExpected = 2;
                onFinishedListenerCounterExpected = 1;
                onResponseListenerCounterExpected = 2;
                onErrorListenerCounterExpected = 0;

                while (rpcsTempList.size() != 0){
                    RPCRequest request = rpcsTempList.remove(0);
                    if (request instanceof Speak) {
                        requestsMap.get(request).onResponse(request.getCorrelationID(), new SpeakResponse(true, Result.SUCCESS));
                    } else if (request instanceof Show) {
                        requestsMap.get(request).onResponse(request.getCorrelationID(), new ShowResponse(true, Result.SUCCESS));
                    }
                }
                break;
            case 2: // Some RPCs fail
                onUpdateListenerCounterExpected = 2;
                onFinishedListenerCounterExpected = 1;
                onResponseListenerCounterExpected = 1;
                onErrorListenerCounterExpected = 1;

                while (rpcsTempList.size() != 0){
                    RPCRequest request = rpcsTempList.remove(0);
                    if (request instanceof Speak) {
                        requestsMap.get(request).onError(request.getCorrelationID(), Result.DISALLOWED, "ERROR");
                    } else if (request instanceof Show) {
                        requestsMap.get(request).onResponse(request.getCorrelationID(), new ShowResponse(true, Result.SUCCESS));
                    }
                }
                break;
        }


        // Make sure the listener is called correctly
        assertEquals("onUpdate Listener was not called or called more/less frequently than expected", onUpdateListenerCounterExpected, onUpdateListenerCounter);
        assertEquals("onFinished Listener was not called or called more/less frequently than expected", onFinishedListenerCounterExpected, onFinishedListenerCounter);
        assertEquals("onResponse Listener was not called or called more/less frequently than expected", onResponseListenerCounterExpected, onResponseListenerCounter);
        assertEquals("onError Listener was not called or called more/less frequently than expected", onErrorListenerCounterExpected, onErrorListenerCounter);
    }
}
