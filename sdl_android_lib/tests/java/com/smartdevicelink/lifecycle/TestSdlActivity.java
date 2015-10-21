package com.smartdevicelink.lifecycle;

import android.support.test.runner.AndroidJUnit4;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

@RunWith(AndroidJUnit4.class)
public class TestSdlActivity{

    // Create tests

    @Test(timeout=100)
    public void activityTransition_create(){
        SdlActivity aut = new TestActivity(null);

        LifecycleMethod[] targetHistory = {
            // precreate
            LifecycleMethod.onCreate
            // postcreate
        };

        boolean stateChanged;

        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.POSTCREATE);
        Assert.assertTrue(stateChanged);

        testStateHistory(targetHistory, ((TestActivity) aut).lifecycleHistory);
    }

    @Test(timeout=100)
    public void activityTransition_createActive(){
        SdlActivity aut = new TestActivity(null);

        LifecycleMethod[] targetHistory = {
            // precreate
            LifecycleMethod.onCreate,
            // postcreate
            LifecycleMethod.onStart,
            LifecycleMethod.onForeground,
            LifecycleMethod.onVisible
            // active
        };

        boolean stateChanged;

        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.POSTCREATE);
        Assert.assertTrue(stateChanged);
        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.ACTIVE);
        Assert.assertTrue(stateChanged);

        testStateHistory(targetHistory, ((TestActivity) aut).lifecycleHistory);
    }

    @Test(timeout=100)
    public void activityTransition_createDestroy(){
        SdlActivity aut = new TestActivity(null);

        LifecycleMethod[] targetHistory = {
            // precreate
            LifecycleMethod.onCreate,
            // postcreate
            LifecycleMethod.onStart,
            LifecycleMethod.onForeground,
            LifecycleMethod.onVisible,
            // active
            LifecycleMethod.onObscured,
            LifecycleMethod.onBackground,
            LifecycleMethod.onStop,
            LifecycleMethod.onDestroy
            // exited
        };

        boolean stateChanged;

        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.POSTCREATE);
        Assert.assertTrue(stateChanged);
        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.ACTIVE);
        Assert.assertTrue(stateChanged);
        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.EXITED);
        Assert.assertTrue(stateChanged);

        testStateHistory(targetHistory, ((TestActivity)aut).lifecycleHistory);
    }

    // Obscured tests

    @Test(timeout=100)
    public void activityTransition_createObscured(){
        SdlActivity aut = new TestActivity(null);

        LifecycleMethod[] targetHistory = {
            // precreate
            LifecycleMethod.onCreate,
            // postcreate
            LifecycleMethod.onStart,
            LifecycleMethod.onForeground,
            LifecycleMethod.onVisible,
            // active
            LifecycleMethod.onObscured
            // obscured
        };

        boolean stateChanged;

        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.POSTCREATE);
        Assert.assertTrue(stateChanged);
        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.ACTIVE);
        Assert.assertTrue(stateChanged);
        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.OBSCURED);
        Assert.assertTrue(stateChanged);

        testStateHistory(targetHistory, ((TestActivity)aut).lifecycleHistory);
    }

    @Test(timeout=100)
    public void activityTransition_createObscuredActive(){
        SdlActivity aut = new TestActivity(null);

        LifecycleMethod[] targetHistory = {
            // precreate
            LifecycleMethod.onCreate,
            // postcreate
            LifecycleMethod.onStart,
            LifecycleMethod.onForeground,
            LifecycleMethod.onVisible,
            // active
            LifecycleMethod.onObscured,
            // obscured
            LifecycleMethod.onVisible
            // active
        };

        boolean stateChanged;

        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.POSTCREATE);
        Assert.assertTrue(stateChanged);
        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.ACTIVE);
        Assert.assertTrue(stateChanged);
        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.OBSCURED);
        Assert.assertTrue(stateChanged);
        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.ACTIVE);
        Assert.assertTrue(stateChanged);

        testStateHistory(targetHistory, ((TestActivity)aut).lifecycleHistory);
    }

    @Test(timeout=100)
    public void activityTransition_createObscuredBackground(){
        SdlActivity aut = new TestActivity(null);

        LifecycleMethod[] targetHistory = {
            LifecycleMethod.onCreate,
            // postcreate
            LifecycleMethod.onStart,
            LifecycleMethod.onForeground,
            LifecycleMethod.onVisible,
            // active
            LifecycleMethod.onObscured,
            // obscured
            LifecycleMethod.onBackground
            // background
        };

        boolean stateChanged;

        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.POSTCREATE);
        Assert.assertTrue(stateChanged);
        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.ACTIVE);
        Assert.assertTrue(stateChanged);
        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.OBSCURED);
        Assert.assertTrue(stateChanged);
        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.BACKGROUND);
        Assert.assertTrue(stateChanged);

        testStateHistory(targetHistory, ((TestActivity)aut).lifecycleHistory);
    }

    @Test(timeout=100)
    public void activityTransition_createObscuredDestroy(){
        SdlActivity aut = new TestActivity(null);

        LifecycleMethod[] targetHistory = {
            // precreate
            LifecycleMethod.onCreate,
            // postcreate
            LifecycleMethod.onStart,
            LifecycleMethod.onForeground,
            LifecycleMethod.onVisible,
            // active
            LifecycleMethod.onObscured,
            // obscured
            LifecycleMethod.onBackground,
            LifecycleMethod.onStop,
            LifecycleMethod.onDestroy
            // exited
        };

        boolean stateChanged;

        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.POSTCREATE);
        Assert.assertTrue(stateChanged);
        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.ACTIVE);
        Assert.assertTrue(stateChanged);
        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.OBSCURED);
        Assert.assertTrue(stateChanged);
        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.EXITED);
        Assert.assertTrue(stateChanged);

        testStateHistory(targetHistory, ((TestActivity)aut).lifecycleHistory);
    }

    // Background Tests

    @Test(timeout=100)
    public void activityTransition_createBackground(){
        SdlActivity aut = new TestActivity(null);

        LifecycleMethod[] targetHistory = {
            // precreate
            LifecycleMethod.onCreate,
            // postcreate
            LifecycleMethod.onStart,
            LifecycleMethod.onForeground,
            LifecycleMethod.onVisible,
            // active
            LifecycleMethod.onObscured,
            LifecycleMethod.onBackground
            // background
        };

        boolean stateChanged;

        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.POSTCREATE);
        Assert.assertTrue(stateChanged);
        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.ACTIVE);
        Assert.assertTrue(stateChanged);
        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.BACKGROUND);
        Assert.assertTrue(stateChanged);

        testStateHistory(targetHistory, ((TestActivity)aut).lifecycleHistory);
    }

    @Test(timeout=100)
    public void activityTransition_createBackgroundActive(){
        SdlActivity aut = new TestActivity(null);

        LifecycleMethod[] targetHistory = {
            // precreate
            LifecycleMethod.onCreate,
            // postcreate
            LifecycleMethod.onStart,
            LifecycleMethod.onForeground,
            LifecycleMethod.onVisible,
            // active
            LifecycleMethod.onObscured,
            LifecycleMethod.onBackground,
            // background
            LifecycleMethod.onForeground,
            LifecycleMethod.onVisible
            // active
        };

        boolean stateChanged;

        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.POSTCREATE);
        Assert.assertTrue(stateChanged);
        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.ACTIVE);
        Assert.assertTrue(stateChanged);
        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.BACKGROUND);
        Assert.assertTrue(stateChanged);
        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.ACTIVE);
        Assert.assertTrue(stateChanged);

        testStateHistory(targetHistory, ((TestActivity)aut).lifecycleHistory);
    }

    @Test(timeout=100)
    public void activityTransition_createBackgroundDestroy(){
        SdlActivity aut = new TestActivity(null);

        LifecycleMethod[] targetHistory = {
            // precreate
            LifecycleMethod.onCreate,
            // postcreate
            LifecycleMethod.onStart,
            LifecycleMethod.onForeground,
            LifecycleMethod.onVisible,
            // active
            LifecycleMethod.onObscured,
            LifecycleMethod.onBackground,
            // background
            LifecycleMethod.onStop,
            LifecycleMethod.onDestroy
            // exited
        };

        boolean stateChanged;

        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.POSTCREATE);
        Assert.assertTrue(stateChanged);
        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.ACTIVE);
        Assert.assertTrue(stateChanged);
        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.BACKGROUND);
        Assert.assertTrue(stateChanged);
        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.EXITED);
        Assert.assertTrue(stateChanged);

        testStateHistory(targetHistory, ((TestActivity)aut).lifecycleHistory);
    }

    @Test(timeout=100)
    public void activityTransition_createBackgroundStop(){
        SdlActivity aut = new TestActivity(null);

        LifecycleMethod[] targetHistory = {
            // precreate
            LifecycleMethod.onCreate,
            // postcreate
            LifecycleMethod.onStart,
            LifecycleMethod.onForeground,
            LifecycleMethod.onVisible,
            // active
            LifecycleMethod.onObscured,
            LifecycleMethod.onBackground,
            // background
            LifecycleMethod.onStop
            // stopped
        };

        boolean stateChanged;

        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.POSTCREATE);
        Assert.assertTrue(stateChanged);
        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.ACTIVE);
        Assert.assertTrue(stateChanged);
        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.BACKGROUND);
        Assert.assertTrue(stateChanged);
        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.STOPPED);
        Assert.assertTrue(stateChanged);

        testStateHistory(targetHistory, ((TestActivity)aut).lifecycleHistory);
    }

    // Stop tests

    @Test(timeout=100)
    public void activityTransition_createStop(){
        SdlActivity aut = new TestActivity(null);

        LifecycleMethod[] targetHistory = {
            // precreate
            LifecycleMethod.onCreate,
            // postcreate
            LifecycleMethod.onStart,
            LifecycleMethod.onForeground,
            LifecycleMethod.onVisible,
            // active
            LifecycleMethod.onObscured,
            LifecycleMethod.onBackground,
            LifecycleMethod.onStop
            // stopped
        };

        boolean stateChanged;

        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.POSTCREATE);
        Assert.assertTrue(stateChanged);
        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.ACTIVE);
        Assert.assertTrue(stateChanged);
        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.STOPPED);
        Assert.assertTrue(stateChanged);

        testStateHistory(targetHistory, ((TestActivity)aut).lifecycleHistory);
    }

    @Test(timeout=100)
    public void activityTransition_createStopActive(){
        SdlActivity aut = new TestActivity(null);

        LifecycleMethod[] targetHistory = {
            // precreate
            LifecycleMethod.onCreate,
            // postcreate
            LifecycleMethod.onStart,
            LifecycleMethod.onForeground,
            LifecycleMethod.onVisible,
            // active
            LifecycleMethod.onObscured,
            LifecycleMethod.onBackground,
            LifecycleMethod.onStop,
            // stopped
            LifecycleMethod.onRestart,
            LifecycleMethod.onStart,
            LifecycleMethod.onForeground,
            LifecycleMethod.onVisible
            // active
        };

        boolean stateChanged;

        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.POSTCREATE);
        Assert.assertTrue(stateChanged);
        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.ACTIVE);
        Assert.assertTrue(stateChanged);
        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.STOPPED);
        Assert.assertTrue(stateChanged);
        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.ACTIVE);
        Assert.assertTrue(stateChanged);

        testStateHistory(targetHistory, ((TestActivity)aut).lifecycleHistory);
    }

    @Test(timeout=100)
    public void activityTransition_createStopDestroy(){
        SdlActivity aut = new TestActivity(null);

        LifecycleMethod[] targetHistory = {
            // precreate
            LifecycleMethod.onCreate,
            // postcreate
            LifecycleMethod.onStart,
            LifecycleMethod.onForeground,
            LifecycleMethod.onVisible,
            // active
            LifecycleMethod.onObscured,
            LifecycleMethod.onBackground,
            LifecycleMethod.onStop,
            // stopped
            LifecycleMethod.onDestroy
            // exited
        };

        boolean stateChanged;

        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.POSTCREATE);
        Assert.assertTrue(stateChanged);
        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.ACTIVE);
        Assert.assertTrue(stateChanged);
        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.STOPPED);
        Assert.assertTrue(stateChanged);
        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.EXITED);
        Assert.assertTrue(stateChanged);

        testStateHistory(targetHistory, ((TestActivity)aut).lifecycleHistory);
    }

    @Test(timeout=100)
    public void activityTransition_navigationCycle(){
        SdlActivity aut = new TestActivity(null);

        LifecycleMethod[] targetHistory = {
            // precreate
            LifecycleMethod.onCreate,
            // postcreate
            LifecycleMethod.onStart,
            LifecycleMethod.onForeground,
            LifecycleMethod.onVisible,
            // active
            LifecycleMethod.onObscured,
            // obscured
            LifecycleMethod.onVisible,
            // active
            LifecycleMethod.onObscured,
            LifecycleMethod.onBackground,
            // background
            LifecycleMethod.onForeground,
            LifecycleMethod.onVisible,
            // active
            LifecycleMethod.onObscured,
            LifecycleMethod.onBackground,
            LifecycleMethod.onStop,
            // stopped
            LifecycleMethod.onRestart,
            LifecycleMethod.onStart,
            LifecycleMethod.onForeground,
            LifecycleMethod.onVisible,
            // active
            LifecycleMethod.onObscured,
            LifecycleMethod.onBackground,
            LifecycleMethod.onStop,
            LifecycleMethod.onDestroy
            // exited
        };

        boolean stateChanged;

        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.POSTCREATE);
        Assert.assertTrue(stateChanged);
        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.ACTIVE);
        Assert.assertTrue(stateChanged);
        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.OBSCURED);
        Assert.assertTrue(stateChanged);
        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.ACTIVE);
        Assert.assertTrue(stateChanged);
        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.BACKGROUND);
        Assert.assertTrue(stateChanged);
        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.ACTIVE);
        Assert.assertTrue(stateChanged);
        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.STOPPED);
        Assert.assertTrue(stateChanged);
        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.ACTIVE);
        Assert.assertTrue(stateChanged);
        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.EXITED);
        Assert.assertTrue(stateChanged);

        testStateHistory(targetHistory, ((TestActivity)aut).lifecycleHistory);
    }



    @Test(timeout=100)
    public void activityTransition_errorRejection(){
        SdlActivity aut = new TestActivity(null);

        LifecycleMethod[] targetHistory = {
            // precreate
            LifecycleMethod.onCreate,
            // postcreate
            LifecycleMethod.onStart,
            LifecycleMethod.onForeground,
            LifecycleMethod.onVisible,
            // active
            LifecycleMethod.onObscured,
            // obscured
            LifecycleMethod.onVisible,
            // active
            LifecycleMethod.onObscured,
            LifecycleMethod.onBackground,
            // background
            LifecycleMethod.onForeground,
            LifecycleMethod.onVisible,
            // active
            LifecycleMethod.onObscured,
            LifecycleMethod.onBackground,
            LifecycleMethod.onStop,
            // stopped
            LifecycleMethod.onRestart,
            LifecycleMethod.onStart,
            LifecycleMethod.onForeground,
            LifecycleMethod.onVisible,
            // active
            LifecycleMethod.onObscured,
            LifecycleMethod.onBackground,
            LifecycleMethod.onStop,
            LifecycleMethod.onDestroy
            // exited
        };
        
        boolean stateChanged;

        // State change POSTCREATE
        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.POSTCREATE);
        Assert.assertTrue(stateChanged);
        // Double state call -- should not call on create twice
        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.POSTCREATE);
        Assert.assertTrue(stateChanged);
        // Back navigation -- Should not leave
        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.PRECREATE);
        Assert.assertFalse(stateChanged);

        // State change ACTIVE
        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.ACTIVE);
        Assert.assertTrue(stateChanged);
        // Double state call -- should not call any methods
        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.ACTIVE);
        Assert.assertTrue(stateChanged);
        // Back navigation -- Sould not leave
        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.PRECREATE);
        Assert.assertFalse(stateChanged);
        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.POSTCREATE);
        Assert.assertFalse(stateChanged);
        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.RESTARTING);
        Assert.assertFalse(stateChanged);
        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.STARTED);
        Assert.assertFalse(stateChanged);
        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.FOREGROUND);
        Assert.assertFalse(stateChanged);

        // State change OBSCURED
        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.OBSCURED);
        Assert.assertTrue(stateChanged);
        // Double state call -- should not call any methods
        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.OBSCURED);
        Assert.assertTrue(stateChanged);
        // Back navigation -- Sould not leave
        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.PRECREATE);
        Assert.assertFalse(stateChanged);
        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.POSTCREATE);
        Assert.assertFalse(stateChanged);
        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.RESTARTING);
        Assert.assertFalse(stateChanged);
        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.STARTED);
        Assert.assertFalse(stateChanged);
        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.FOREGROUND);
        Assert.assertFalse(stateChanged);


        // State change ACTIVE -- already tested
        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.ACTIVE);
        Assert.assertTrue(stateChanged);

        // State change BACKGROUND
        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.BACKGROUND);
        Assert.assertTrue(stateChanged);
        // Double state call -- should not call any methods
        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.BACKGROUND);
        Assert.assertTrue(stateChanged);
        // Back navigation -- Sould not leave
        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.PRECREATE);
        Assert.assertFalse(stateChanged);
        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.POSTCREATE);
        Assert.assertFalse(stateChanged);
        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.RESTARTING);
        Assert.assertFalse(stateChanged);
        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.STARTED);
        Assert.assertFalse(stateChanged);
        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.OBSCURED);
        Assert.assertFalse(stateChanged);

        // State change ACTIVE -- already tested
        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.ACTIVE);
        Assert.assertTrue(stateChanged);

        // State change STOPPED
        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.STOPPED);
        Assert.assertTrue(stateChanged);
        // Double state call -- should not call any methods
        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.STOPPED);
        Assert.assertTrue(stateChanged);
        // Back navigation -- Sould not leave
        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.PRECREATE);
        Assert.assertFalse(stateChanged);
        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.POSTCREATE);
        Assert.assertFalse(stateChanged);
        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.OBSCURED);
        Assert.assertFalse(stateChanged);
        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.BACKGROUND);
        Assert.assertFalse(stateChanged);

        // State change ACTIVE -- already tested
        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.ACTIVE);
        Assert.assertTrue(stateChanged);

        // State change EXITED
        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.EXITED);
        Assert.assertTrue(stateChanged);
        // Double state call -- should not call any methods
        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.EXITED);
        Assert.assertTrue(stateChanged);
        // All states should be trapped
        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.PRECREATE);
        Assert.assertFalse(stateChanged);
        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.POSTCREATE);
        Assert.assertFalse(stateChanged);
        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.RESTARTING);
        Assert.assertFalse(stateChanged);
        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.STARTED);
        Assert.assertFalse(stateChanged);
        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.FOREGROUND);
        Assert.assertFalse(stateChanged);
        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.ACTIVE);
        Assert.assertFalse(stateChanged);
        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.OBSCURED);
        Assert.assertFalse(stateChanged);
        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.BACKGROUND);
        Assert.assertFalse(stateChanged);
        stateChanged = aut.notifyStateChange(SdlActivity.LifecycleState.STOPPED);
        Assert.assertFalse(stateChanged);

        testStateHistory(targetHistory, ((TestActivity)aut).lifecycleHistory);
    }

    private void testStateHistory(LifecycleMethod[] target, ArrayList<LifecycleMethod> actual){

        for(int i = 0; i < target.length; i++){
            Assert.assertEquals(target[i], actual.get(i));
            if(target[i] != actual.get(i)){
                break;
            }
        }

        Assert.assertEquals(target.length, actual.size());
    }


    private final class TestActivity extends SdlActivity {

        ArrayList<LifecycleMethod> lifecycleHistory = new ArrayList<>();

        protected TestActivity(SdlLifecycleService service) {
            super(service);
        }

        @Override
        protected void onCreate() {
            super.onCreate();
            lifecycleHistory.add(LifecycleMethod.onCreate);
        }

        @Override
        protected void onStart() {
            super.onStart();
            lifecycleHistory.add(LifecycleMethod.onStart);
        }

        @Override
        protected void onRestart() {
            super.onRestart();
            lifecycleHistory.add(LifecycleMethod.onRestart);
        }

        @Override
        protected void onForeground(){
            super.onForeground();
            lifecycleHistory.add(LifecycleMethod.onForeground);
        }

        @Override
        protected void onVisible() {
            super.onVisible();
            lifecycleHistory.add(LifecycleMethod.onVisible);
        }

        @Override
        protected void onObscured() {
            super.onObscured();
            lifecycleHistory.add(LifecycleMethod.onObscured);
        }

        @Override
        protected void onBackground() {
            super.onBackground();
            lifecycleHistory.add(LifecycleMethod.onBackground);
        }

        @Override
        protected void onStop() {
            super.onStop();
            lifecycleHistory.add(LifecycleMethod.onStop);
        }

        @Override
        protected void onDestroy() {
            super.onDestroy();
            lifecycleHistory.add(LifecycleMethod.onDestroy);
        }

    }

    enum LifecycleMethod{
        onCreate,
        onStart,
        onRestart,
        onForeground,
        onVisible,
        onObscured,
        onBackground,
        onStop,
        onDestroy
    }
	
}