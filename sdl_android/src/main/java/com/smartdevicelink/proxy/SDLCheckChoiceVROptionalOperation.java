package com.smartdevicelink.proxy;

import android.util.Log;

import com.smartdevicelink.managers.permission.OnPermissionChangeListener;
import com.smartdevicelink.managers.permission.PermissionManager;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.interfaces.ISdl;
import com.smartdevicelink.proxy.rpc.Choice;
import com.smartdevicelink.proxy.rpc.CreateInteractionChoiceSet;
import com.smartdevicelink.proxy.rpc.OnPermissionsChange;
import com.smartdevicelink.proxy.rpc.enums.Result;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCNotificationListener;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCResponseListener;

import java.util.ArrayList;

public class SDLCheckChoiceVROptionalOperation {

    private final static String TAG = "CheckChoiceVROptional";

    public final int VR_TEST_CHOICE_ID = 0;

    //Choice VR test states
    private final int VR_CMD_TEST_ONGOING_STATE = -2;
    private final int VR_CMD_UNKNOWN_STATE = -1;
    private final int VR_CMD_REQUIRED_STATE = 1;
    private final int VR_CMD_NOT_REQUIRED_STATE = 0;

    //test result variables
    private int isVRCommandCheckExecuted = VR_CMD_UNKNOWN_STATE;
    private final Object vr_test_lock = new Object();

    //interface to send RPC
    private ISdl mISDL = null;
    private PermissionManager mPermissionManager = null;

    private static final SDLCheckChoiceVROptionalOperation instance = new SDLCheckChoiceVROptionalOperation();

    public static SDLCheckChoiceVROptionalOperation getInstance() {
        return instance;
    }

    private SDLCheckChoiceVROptionalOperation() {
    }

    private void performVRCommandCheckForChoices(){

        if(null == mISDL ){
            return;
        }
        synchronized (vr_test_lock) {
            if (VR_CMD_UNKNOWN_STATE == isVRCommandCheckExecuted) {
                isVRCommandCheckExecuted = VR_CMD_TEST_ONGOING_STATE;
                Choice choice = new Choice();
                choice.setChoiceID(VR_TEST_CHOICE_ID);
                choice.setMenuName("Test "+TAG);
                ArrayList<Choice> list = new ArrayList<>();
                list.add(choice);

                CreateInteractionChoiceSet choiceSet = new CreateInteractionChoiceSet();
                choiceSet.setChoiceSet(list);
                choiceSet.setInteractionChoiceSetID(VR_TEST_CHOICE_ID);

                choiceSet.setOnRPCResponseListener(new OnRPCResponseListener() {
                    @Override
                    public void onResponse(int correlationId, RPCResponse response) {
                        if (response.getSuccess()) {
                            Log.i(TAG, "ChoiceSet does not require VR ");
                            isVRCommandCheckExecuted = VR_CMD_NOT_REQUIRED_STATE;
                        } else {
                            Log.i(TAG, "ChoiceSet require VR ");
                            isVRCommandCheckExecuted = VR_CMD_REQUIRED_STATE;
                        }
                    }

                    @Override
                    public void onError(int correlationId, Result resultCode, String info) {
                        isVRCommandCheckExecuted = VR_CMD_REQUIRED_STATE;
                    }
                });
                mISDL.sendRPC(choiceSet);
            }
        }
    }

    public void SetRPCInterface(ISdl sendRPCInterface, PermissionManager permissionManager){
        if(null == sendRPCInterface){
            Log.e(TAG,"SDL proxy interface null...");
            return;
        }

        if (null == permissionManager){
            Log.e(TAG,"permission manager is null...");
            return;
        }

        mISDL = sendRPCInterface;
        mPermissionManager = permissionManager;

        mISDL.addOnRPCNotificationListener(FunctionID.ON_HMI_STATUS, new OnRPCNotificationListener(){

            @Override
            public void onNotified(RPCNotification notification) {
                if(mPermissionManager.isRPCAllowed(FunctionID.CREATE_INTERACTION_CHOICE_SET) &&
                        VR_CMD_UNKNOWN_STATE == isVRCommandCheckExecuted){
                    performVRCommandCheckForChoices();
                }
            }
        });
    }

    public boolean isChoiceVRRequired(){
        boolean retVal = true;
        Log.e(TAG,"choice VR optional state: "+isVRCommandCheckExecuted);
        switch (isVRCommandCheckExecuted){
            case VR_CMD_REQUIRED_STATE:
                retVal = true;
                break;
            case VR_CMD_NOT_REQUIRED_STATE:
                retVal = false;
                break;
        }
        return retVal;
    }
}
