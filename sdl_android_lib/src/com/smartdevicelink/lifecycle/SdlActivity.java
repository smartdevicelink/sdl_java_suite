package com.smartdevicelink.lifecycle;

public abstract class SdlActivity {

    private LifecycleState currentState = LifecycleState.PRECREATE;

    SdlLifecycleService mLifecycleService;

    protected SdlActivity(SdlLifecycleService service){
        mLifecycleService = service;
    }

    protected void onCreate(){
    }

    protected void onStart(){
    }

    protected void onRestart(){
    }

    protected void onForeground(){
    }

    protected void onVisible(){
    }

    protected void onObscured(){
    }

    protected void onBackground(){
    }

    protected void onStop(){
    }

    protected void onDestroy(){
    }

    public void startSdlActivity(Class<? extends SdlActivity> activity){
        mLifecycleService.startSdlActivity(activity);
    }

    /**
     * Method for driving activity state transitions. Handles state book keeping and makes all
     * lifecycle calls.
     * @param targetState The state that the activity should transition to.
     * @return Returns true if the activity ended in the desired state.
     */
    final synchronized boolean notifyStateChange(LifecycleState targetState){

        if(currentState == targetState){
            return true;
        }

        switch (currentState){

            case PRECREATE:
                if(targetState.ordinal() > LifecycleState.PRECREATE.ordinal()) {
                    onCreate();
                    currentState = LifecycleState.POSTCREATE;
                } else {
                    return false;
                }
                break;

            case POSTCREATE:
                if(targetState != LifecycleState.RESTARTING &&
                        targetState.ordinal() > LifecycleState.POSTCREATE.ordinal()) {
                    onStart();
                    currentState = LifecycleState.STARTED;
                } else {
                    return false;
                }
                break;

            case RESTARTING:
                if(targetState.ordinal() > LifecycleState.RESTARTING.ordinal()) {
                    onStart();
                    currentState = LifecycleState.STARTED;
                } else {
                    return false;
                }
                break;

            case STARTED:
                if(targetState.ordinal() > LifecycleState.STARTED.ordinal()) {
                    onForeground();
                    currentState = LifecycleState.FOREGROUND;
                } else {
                    return false;
                }
                break;

            case FOREGROUND:
                if(targetState.ordinal() > LifecycleState.FOREGROUND.ordinal()) {
                    onVisible();
                    currentState = LifecycleState.ACTIVE;
                } else {
                    return false;
                }
                break;

            case ACTIVE:
                if(targetState.ordinal() > LifecycleState.ACTIVE.ordinal()) {
                    onObscured();
                    currentState = LifecycleState.OBSCURED;
                } else {
                    return false;
                }
                break;

            case OBSCURED:
                if(targetState.ordinal() > LifecycleState.FOREGROUND.ordinal() &&
                        targetState.ordinal() <= LifecycleState.ACTIVE.ordinal()){
                    onVisible();
                    currentState = LifecycleState.ACTIVE;
                } else if (targetState.ordinal() > LifecycleState.OBSCURED.ordinal()){
                    onBackground();
                    currentState = LifecycleState.BACKGROUND;
                } else {
                    return false;
                }
                break;

            case BACKGROUND:
                if(targetState.ordinal() > LifecycleState.STARTED.ordinal() &&
                        targetState.ordinal() <= LifecycleState.ACTIVE.ordinal()){
                    onForeground();
                    currentState = LifecycleState.FOREGROUND;
                } else if (targetState.ordinal() > LifecycleState.BACKGROUND.ordinal()){
                    onStop();
                    currentState = LifecycleState.STOPPED;
                } else {
                    return false;
                }
                break;

            case STOPPED:
                if(targetState.ordinal() > LifecycleState.POSTCREATE.ordinal() &&
                        targetState.ordinal() <= LifecycleState.ACTIVE.ordinal()){
                    onRestart();
                    currentState = LifecycleState.RESTARTING;
                } else if (targetState.ordinal() > LifecycleState.STOPPED.ordinal()){
                    onDestroy();
                    currentState = LifecycleState.EXITED;
                } else {
                    return false;
                }
                break;

            case EXITED:
                return false;
        }

        if(currentState != targetState){
            return notifyStateChange(targetState);
        } else {
            return true;
        }
    }

    enum LifecycleState{
        // ORDER MATTERS. States must be arranged from start to end of lifecycle when added.
        PRECREATE,
        POSTCREATE,
        RESTARTING,
        STARTED,
        FOREGROUND,
        ACTIVE,
        OBSCURED,
        BACKGROUND,
        STOPPED,
        EXITED
    }

}