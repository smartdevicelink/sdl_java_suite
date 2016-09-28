package com.smartdevicelink.api;

import android.util.Log;

import java.util.HashMap;

class ActivityStateTransitionRegistry {

    private static final String TAG = ActivityStateTransitionRegistry.class.getSimpleName();

    static private HashMap<String, ActivityStateTransition> mTransitionMap = new HashMap<>();
    static private ActivityStateTransitionRegistry mInstance;

    private ActivityStateTransitionRegistry(){
    }

    /**
     * Lazy initializes the registry and {@link ActivityStateTransition} instances as requested.
     * Serves a single flyweight instance of each state to all ActivityManagers.
     * @param transition Implementation of {@link ActivityStateTransition} requested.
     * @return Instance of the desired state transition object.
     */
    static ActivityStateTransition getStateTransition(Class<? extends ActivityStateTransition> transition){
        String name = transition.getSimpleName();
        if(mInstance == null){
            mInstance = new ActivityStateTransitionRegistry();
        }

        ActivityStateTransition stateTransition = mTransitionMap.get(name);

        if(stateTransition == null){
            try {
                stateTransition = transition.newInstance();
            } catch (InstantiationException e) {
                Log.e(TAG, "Unable to instantiate " + name, e);
            } catch (IllegalAccessException e) {
                Log.e(TAG, "Unable to access constructor for " + name, e);
            }

            mTransitionMap.put(name, stateTransition);
        }

        return stateTransition;

    }

}
