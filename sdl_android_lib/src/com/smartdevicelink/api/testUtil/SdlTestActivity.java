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
    private LinkedList<StateTracking> stateTracking = new LinkedList<>();

    private int currentDebugCheckPosition= 0;

    public void extraStateVerification() throws UnintendedAdditionalCallsException {
        if(currentDebugCheckPosition!=stateTracking.size()){
            throw new UnintendedAdditionalCallsException(getClass().getName()+" has calls that are beyond what should be present"+getCurrentPlacementInCallbackHistory());
            //throw new UnintendedAdditionalCallsException(getCurrentPlacementInCallbackHistory());
        }
    }

    public class UnintendedAdditionalCallsException extends Exception{
        public UnintendedAdditionalCallsException(String detailMessage) {
            super(detailMessage);
        }
    }


    public StateTracking getCurrentCallbackCheck(){
        StateTracking currTrack = null;
        if(currentDebugCheckPosition<stateTracking.size()){
            currTrack = stateTracking.get(currentDebugCheckPosition);
            currentDebugCheckPosition++;
        }
        return currTrack;
    }

    public void moveDebugCheckToEnd(){
        currentDebugCheckPosition = stateTracking.size();
    }

    public String getCurrentPlacementInCallbackHistory(){
        String stringBuild="\nCallback History\n-----------------";
        for(int i=0; i<stateTracking.size(); i++){
            if(i==currentDebugCheckPosition){
                stringBuild+="\n<<<<Current Callback Test is here>>>>";
            }
            stringBuild+="\n"+stateTracking.get(i).toString();
        }
        return stringBuild;
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



}
