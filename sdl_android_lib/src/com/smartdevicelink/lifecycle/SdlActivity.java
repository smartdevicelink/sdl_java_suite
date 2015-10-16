package com.smartdevicelink.lifecycle;

public abstract class SdlActivity {

    private LifecycleState currentState = LifecycleState.PRECREATE;

    SdlLifecycleService mLifecycleService;

    SdlActivity(SdlLifecycleService service){
        mLifecycleService = service;
    }

    public void onCreate(){

    }

    public void onStart(){

    }

    public void onRestart(){

    }

    public void onResume(){

    }

    public void onObscured(){

    }

    public void onBackground(){

    }

    public void onExit(){

    }

    public void onDestroy(){

    }

    public void startSdlActivity(Class<? extends SdlActivity> activity){
        mLifecycleService.startSdlActivity(activity);
    }

    final void notifyStateChange(LifecycleState targetState){

        switch (currentState){
            case PRECREATE:
                break;
            case POSTCREATE:
                break;
            case STARTED:
                break;
            case ACTIVE:
                break;
            case OBSCURED:
                break;
            case BACKGROUND:
                break;
            case EXIT:
                break;
        }

        if(currentState != targetState){
            notifyStateChange(targetState);
        }


    }

    enum LifecycleState{
        PRECREATE,
        POSTCREATE,
        STARTED,
        ACTIVE,
        OBSCURED,
        BACKGROUND,
        EXIT
    }

}