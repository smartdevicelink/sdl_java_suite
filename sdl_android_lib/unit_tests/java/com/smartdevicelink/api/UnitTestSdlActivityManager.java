package com.smartdevicelink.api;

import com.smartdevicelink.api.testUtil.SdlTestActivity;
import com.smartdevicelink.api.testUtil.SdlTestActivity.StateTracking;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashSet;
import java.util.Stack;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.fail;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class UnitTestSdlActivityManager{

    SdlActivityManager mSdlActivityManager;
    SdlApplication mSdlApplication;

    HashSet<SdlTestActivity> mActivitiesCreated = new HashSet<>();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setup(){

        mSdlApplication = new SdlApplication(mock(SdlConnectionService.class),mock(SdlApplicationConfig.class),mock(SdlApplication.ConnectionStatusListener.class));
        mSdlActivityManager = new SdlActivityManager();
        mSdlActivityManager.onSdlConnect();

        //setting it to the application so it receives the call for startActivity
        mSdlApplication.mSdlActivityManager = mSdlActivityManager;
    }

    @After
    public void tearDown()  {
        //TODO: ignore verification of extra callbacks if test failed already
        boolean failed= false;
        for (SdlTestActivity aMActivitiesCreated : mActivitiesCreated) {
            try {
                aMActivitiesCreated.extraStateVerification();
            } catch (SdlTestActivity.UnintendedAdditionalCallsException e) {
                e.printStackTrace();
                failed = true;
            }
        }
        if(failed)
            fail("Exceptions were caught in tear down");
        mActivitiesCreated.clear();
    }


    @Test
    public void verifySdlAppLaunchCreatesSdlActivity()  {
        SdlTestActivity tester= createInitialTestActivity();
        checkConnectedToBackground(tester);
    }

    @Test
    public void verifyCompleteForegroundTransition(){
        SdlTestActivity tester= createInitialTestActivity();
        mSdlActivityManager.onForeground();
        checkConnectedToBackground(tester);
        checkBackgroundToForeground(tester);
    }

    @Test
    public void verifyCompleteLifeCycle(){
        SdlTestActivity tester= createInitialTestActivity();
        mSdlActivityManager.onForeground();
        mSdlActivityManager.onExit();
        checkConnectedToBackground(tester);
        checkBackgroundToForeground(tester);
        checkForegroundToBackground(tester);
        checkBackgroundToDisconnected(tester);
    }

    @Test
    public void verifySuddenDisconnection(){
        SdlTestActivity tester= createInitialTestActivity();
        mSdlActivityManager.onSdlDisconnect();
        checkConnectedToBackground(tester);
        checkBackgroundToDisconnected(tester);
    }

    @Test
    public void verifyStartOfNewBackgroundActivity(){
        SdlTestActivity pushedBack= createInitialTestActivity();
        checkConnectedToBackground(pushedBack);
        pushedBack.startSdlActivity(SdlTestActivity.class, 0);
        SdlTestActivity topActivity= (SdlTestActivity) mSdlActivityManager.getTopActivity();
        mActivitiesCreated.add(topActivity);
        checkBackgroundToStopped(pushedBack);
        checkConnectedToBackground(topActivity);
    }

    @Test
    public void verifyStartOfNewForegroundActivity(){
        SdlTestActivity pushedBack= createInitialTestActivity();
        mSdlActivityManager.onForeground();
        checkConnectedToBackground(pushedBack);
        checkBackgroundToForeground(pushedBack);

        SdlTestActivity topActivity = startNextTestActivity(pushedBack,SdlTestActivity.class,0);

        checkForegroundToBackground(pushedBack);
        checkBackgroundToStopped(pushedBack);
        checkConnectedToBackground(topActivity);
        checkBackgroundToForeground(topActivity);
    }

    @Test
    public void verifyBackWithOneDoesNotRemoveFromStack(){
        SdlTestActivity tester= createInitialTestActivity();
        mSdlActivityManager.back();
        checkConnectedToBackground(tester);
    }


    @Test
    public void verifyNormalBackFromBackgroundActivity(){
        SdlTestActivity pushedBack= createInitialTestActivity();

        checkConnectedToBackground(pushedBack);
        SdlTestActivity topActivity= startNextTestActivity(pushedBack,SdlTestActivity.class,0);

        checkBackgroundToStopped(pushedBack);
        checkConnectedToBackground(topActivity);

        topActivity.onBackPressed();
        checkBackgroundToDisconnected(topActivity);
        checkStoppedToBackground(pushedBack);
    }

    @Test
    public void verifyNormalBackFromForegroundActivity(){
        SdlTestActivity pushedBack= createInitialTestActivity();
        mSdlActivityManager.onForeground();
        checkConnectedToBackground(pushedBack);
        checkBackgroundToForeground(pushedBack);
        SdlTestActivity topActivity= startNextTestActivity(pushedBack, SdlTestActivity.class, 0);

        checkForegroundToBackground(pushedBack);
        checkBackgroundToStopped(pushedBack);

        checkConnectedToBackground(topActivity);
        checkBackgroundToForeground(topActivity);

        topActivity.onBackPressed();
        checkForegroundToBackground(topActivity);
        checkBackgroundToDisconnected(topActivity);
        checkStoppedToForeground(pushedBack);
    }

    @Test
    public void verifyOnExitWithBiggerBackStack(){
        SdlTestActivity currActivity = createInitialTestActivity();
        checkConnectedToBackground(currActivity);
        for(int i=0; i<5; i++){
            //assuming separate instances for now for same class
            SdlTestActivity previousActivity = currActivity;
            currActivity = startNextTestActivity(currActivity,SdlTestActivity.class,0);
            checkConnectedToBackground(currActivity);
            checkBackgroundToStopped(previousActivity);
        }
        Stack<SdlActivity> backStackCopy = (Stack<SdlActivity>) mSdlActivityManager.getBackStack().clone();
        mSdlActivityManager.onExit();
        SdlTestActivity topWhenExit = (SdlTestActivity) backStackCopy.pop();
        checkBackgroundToDisconnected(topWhenExit);
        for(SdlActivity activity: backStackCopy){
            checkStoppedToDestroy((SdlTestActivity) activity);
        }
    }


    @Test
    public void verifyRelaunchAfterExit(){
        verifyOnExitWithBiggerBackStack();
        SdlTestActivity currActivity = createInitialTestActivity();
        checkConnectedToBackground(currActivity);

    }

    //Utils for test readability

    private SdlTestActivity createInitialTestActivity(){
        mSdlActivityManager.onSdlAppLaunch(mSdlApplication, SdlTestActivity.class);
        SdlTestActivity tester= (SdlTestActivity) mSdlActivityManager.getTopActivity();
        assertNotNull("SdlTestActivity launch activity was not instantiated by the ActivityManager",tester);
        mActivitiesCreated.add(tester);
        return tester;
    }

    private SdlTestActivity startNextTestActivity(SdlTestActivity startingActivity, Class<? extends SdlActivity> testActivity, int flags){
        startingActivity.startSdlActivity(testActivity, flags);
        SdlTestActivity tester= (SdlTestActivity) mSdlActivityManager.getTopActivity();
        assertNotNull("SdlTestActivity was not started by the ActivityManager",tester);
        mActivitiesCreated.add(tester);
        return tester;
    }


    //Application specific callback checking

    private void checkConnectedToBackground(SdlTestActivity sdlTestActivity){
        runCheck(sdlTestActivity, (new StateTracking[]{StateTracking.onCreate,StateTracking.onStart}));
    }

    private void checkBackgroundToForeground(SdlTestActivity sdlTestActivity){
        runCheck(sdlTestActivity, (new StateTracking[]{StateTracking.onForeground}));
    }

    private void checkForegroundToBackground(SdlTestActivity sdlTestActivity){
        runCheck(sdlTestActivity, (new StateTracking[]{StateTracking.onBackground}));
    }

    private void checkBackgroundToDisconnected(SdlTestActivity sdlTestActivity){
        runCheck(sdlTestActivity, (new StateTracking[]{StateTracking.onStop, StateTracking.onDestroy}));
    }

    //Activity specific callback checking

    private void checkStoppedToDestroy(SdlTestActivity sdlTestActivity){
        runCheck(sdlTestActivity, (new StateTracking[]{StateTracking.onDestroy}));
    }

    private void checkBackgroundToStopped(SdlTestActivity sdlTestActivity){
        runCheck(sdlTestActivity, (new StateTracking[]{StateTracking.onStop}));
    }

    private void checkStoppedToBackground(SdlTestActivity sdlTestActivity){
        runCheck(sdlTestActivity, (new StateTracking[]{StateTracking.onRestart, StateTracking.onStart}));
    }

    private void checkStoppedToForeground(SdlTestActivity sdlTestActivity){
        runCheck(sdlTestActivity, (new StateTracking[]{StateTracking.onRestart, StateTracking.onStart, StateTracking.onForeground}));
    }

    private void runCheck(SdlTestActivity sdlTestActivity, StateTracking[] statesToCheck){
        assertNotNull("SdlTestActivity provided to check is null",sdlTestActivity);
        for(StateTracking state:statesToCheck){
            if(sdlTestActivity.stateTracking.peek() != null){
                assertThat(sdlTestActivity.stateTracking.pop(), is(state));
            }else{
                //Always will cause a test failure
                assertThat(null,is(state));
            }
        }
    }

}