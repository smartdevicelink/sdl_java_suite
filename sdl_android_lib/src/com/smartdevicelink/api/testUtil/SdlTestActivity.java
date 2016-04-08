package com.smartdevicelink.api.testUtil;

import com.smartdevicelink.api.SdlActivity;
import java.util.LinkedList;

/**
 * Created by mschwerz on 4/7/16.
 */
public class SdlTestActivity extends SdlActivity {

    public enum StateTracking{
        onCreate,onRestart,onStart,onForeground,onBackground,onStop,onDestroy
    }
    public LinkedList<StateTracking> stateTracking = new LinkedList<>();

    public void extraStateVerification() throws UnintendedAdditionalCallsException {
        if(!stateTracking.isEmpty()){
            String errorString= "\nStates left:";
            for (StateTracking state:stateTracking) {
                errorString+="\n"+state.toString();
            }
            throw new UnintendedAdditionalCallsException(getClass().getName()+" has calls that are beyond what should be present"+errorString);
        }
    }

    public class UnintendedAdditionalCallsException extends Exception{
        public UnintendedAdditionalCallsException(String detailMessage) {
            super(detailMessage);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        stateTracking.add(StateTracking.onCreate);
    }

    @Override
    public void onRestart() {
        super.onRestart();
        stateTracking.add(StateTracking.onRestart);
    }

    @Override
    public void onStart() {
        super.onStart();
        stateTracking.add(StateTracking.onStart);
    }

    @Override
    public void onForeground() {
        super.onForeground();
        stateTracking.add(StateTracking.onForeground);
    }

    @Override
    public void onBackground() {
        super.onBackground();
        stateTracking.add(StateTracking.onBackground);
    }

    @Override
    public void onStop() {
        super.onStop();
        stateTracking.add(StateTracking.onStop);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stateTracking.add(StateTracking.onDestroy);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


}
