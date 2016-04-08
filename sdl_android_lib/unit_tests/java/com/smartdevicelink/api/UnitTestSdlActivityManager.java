package com.smartdevicelink.api;

import com.smartdevicelink.api.testUtil.TestActivity;
import com.smartdevicelink.api.testUtil.TestActivity.StateTracking;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class UnitTestSdlActivityManager{

    SdlActivityManager sdlActivityManager;
    SdlApplication sdlApplication;

    @Before
    public void setup(){
        //sdlApplication = mock(SdlApplication.class);
        sdlApplication = new SdlApplication(mock(SdlConnectionService.class),mock(SdlApplicationConfig.class),mock(SdlApplication.ConnectionStatusListener.class));
        sdlActivityManager = new SdlActivityManager();
        sdlActivityManager.onSdlConnect();
        sdlApplication.mSdlActivityManager = sdlActivityManager;
    }


    @Test
    public void verifySdlAppLaunchCreatesSdlActivity()  {
        sdlActivityManager.onSdlAppLaunch(sdlApplication, TestActivity.class);
        TestActivity tester= (TestActivity) sdlActivityManager.getTopActivity();
        checkConnectedToBackground(tester);

        assertThat(tester.stateTracking.isEmpty(), is(true));
    }

    @Test
    public void verifyCompleteForegroundTransition(){
        sdlActivityManager.onSdlAppLaunch(sdlApplication, TestActivity.class);
        sdlActivityManager.onForeground();
        TestActivity tester= (TestActivity) sdlActivityManager.getTopActivity();
        checkConnectedToBackground(tester);
        checkBackgroundToForeground(tester);
        assertThat(tester.stateTracking.isEmpty(), is(true));
    }

    @Test
    public void verifyCompleteLifeCycle(){
        sdlActivityManager.onSdlAppLaunch(sdlApplication, TestActivity.class);
        sdlActivityManager.onForeground();
        TestActivity tester= (TestActivity) sdlActivityManager.getTopActivity();
        sdlActivityManager.onExit();
        checkConnectedToBackground(tester);
        checkBackgroundToForeground(tester);
        checkForegroundToBackground(tester);
        checkBackgroundToDisconnected(tester);
        assertThat(tester.stateTracking.isEmpty(), is(true));

    }

    @Test
    public void verifySuddenDisconnection(){
        sdlActivityManager.onSdlAppLaunch(sdlApplication, TestActivity.class);
        TestActivity tester= (TestActivity) sdlActivityManager.getTopActivity();
        sdlActivityManager.onSdlDisconnect();
        checkConnectedToBackground(tester);
        checkBackgroundToDisconnected(tester);
        assertThat(tester.stateTracking.isEmpty(), is(true));

    }

    @Test
    public void verifyStartOfNewActivity(){
        sdlActivityManager.onSdlAppLaunch(sdlApplication, TestActivity.class);
        TestActivity pushedBack= (TestActivity) sdlActivityManager.getTopActivity();
        checkConnectedToBackground(pushedBack);
        pushedBack.startSdlActivity(TestActivity.class, 0);
        TestActivity topActivity= (TestActivity) sdlActivityManager.getTopActivity();
        checkBackgroundToNotVisible(pushedBack);
        checkConnectedToBackground(topActivity);

        assertThat(pushedBack.stateTracking.isEmpty(), is(true));
        assertThat(topActivity.stateTracking.isEmpty(), is(true));
    }

    @Test
    public void verifyBackWithOneDoesNotRemoveFromStack(){
        sdlActivityManager.onSdlAppLaunch(sdlApplication, TestActivity.class);
        TestActivity tester= (TestActivity) sdlActivityManager.getTopActivity();
        sdlActivityManager.back();
        checkConnectedToBackground(tester);
        assertThat(tester.stateTracking.isEmpty(), is(true));
    }


    @Test
    public void verifyNormalBackFromBackgroundActivity(){
        sdlActivityManager.onSdlAppLaunch(sdlApplication, TestActivity.class);
        TestActivity pushedBack= (TestActivity) sdlActivityManager.getTopActivity();
        checkConnectedToBackground(pushedBack);
        pushedBack.startSdlActivity(TestActivity.class, 0);
        TestActivity topActivity= (TestActivity) sdlActivityManager.getTopActivity();
        checkBackgroundToNotVisible(pushedBack);
        checkConnectedToBackground(topActivity);

        topActivity.onBackPressed();
        checkBackgroundToDisconnected(topActivity);
        checkNotVisibleToBackground(pushedBack);

        assertThat(pushedBack.stateTracking.isEmpty(), is(true));
        assertThat(topActivity.stateTracking.isEmpty(), is(true));
    }

    @Test
    public void verifyNormalBackFromForegroundActivity(){
        sdlActivityManager.onSdlAppLaunch(sdlApplication, TestActivity.class);
        sdlActivityManager.onForeground();
        TestActivity pushedBack= (TestActivity) sdlActivityManager.getTopActivity();
        checkConnectedToBackground(pushedBack);
        checkBackgroundToForeground(pushedBack);
        pushedBack.startSdlActivity(TestActivity.class, 0);
        TestActivity topActivity= (TestActivity) sdlActivityManager.getTopActivity();
        checkForegroundToBackground(pushedBack);
        checkBackgroundToNotVisible(pushedBack);
        checkConnectedToBackground(topActivity);
        checkBackgroundToForeground(topActivity);

        topActivity.onBackPressed();
        checkForegroundToBackground(topActivity);
        checkBackgroundToDisconnected(topActivity);
        checkNotVisibleToForeground(pushedBack);

        assertThat(pushedBack.stateTracking.isEmpty(), is(true));
        assertThat(topActivity.stateTracking.isEmpty(), is(true));
    }

    @Test
    public void verifyOnExitWithBiggerBackStack(){
        sdlActivityManager.onSdlAppLaunch(sdlApplication, TestActivity.class);
        sdlActivityManager.onForeground();
        TestActivity pushedBack= (TestActivity) sdlActivityManager.getTopActivity();
        checkConnectedToBackground(pushedBack);
        checkBackgroundToForeground(pushedBack);
        pushedBack.startSdlActivity(TestActivity.class, 0);
        TestActivity topActivity= (TestActivity) sdlActivityManager.getTopActivity();

        checkForegroundToBackground(pushedBack);
        checkBackgroundToNotVisible(pushedBack);

        checkConnectedToBackground(topActivity);
        checkBackgroundToForeground(topActivity);

        topActivity.onBackPressed();
        checkForegroundToBackground(topActivity);
        checkBackgroundToDisconnected(topActivity);
        checkNotVisibleToForeground(pushedBack);

        assertThat(pushedBack.stateTracking.isEmpty(), is(true));
        assertThat(topActivity.stateTracking.isEmpty(), is(true));
    }



    //Application specific callback checking

    private void checkConnectedToBackground(TestActivity testActivity){
        assertThat(testActivity.stateTracking.pop(), is(StateTracking.onCreate));
        assertThat(testActivity.stateTracking.pop(), is(StateTracking.onStart));
    }

    private void checkBackgroundToForeground(TestActivity testActivity){
        assertThat(testActivity.stateTracking.pop(), is(StateTracking.onForeground));
    }

    private void checkForegroundToBackground(TestActivity testActivity){
        assertThat(testActivity.stateTracking.pop(), is(StateTracking.onBackground));
    }

    private void checkBackgroundToDisconnected(TestActivity testActivity){
        assertThat(testActivity.stateTracking.pop(), is(StateTracking.onStop));
        assertThat(testActivity.stateTracking.pop(), is(StateTracking.onDestory));
    }

    //Activity specific callback checking

    private void checkNotVisibleToDestroy(TestActivity testActivity){
        assertThat(testActivity.stateTracking.pop(), is(StateTracking.onDestory));
    }

    private void checkBackgroundToNotVisible(TestActivity testActivity){
        assertThat(testActivity.stateTracking.pop(), is(StateTracking.onStop));
    }

    private void checkNotVisibleToBackground(TestActivity testActivity){
        assertThat(testActivity.stateTracking.pop(), is(StateTracking.onRestart));
        assertThat(testActivity.stateTracking.pop(), is(StateTracking.onStart));
    }

    private void checkNotVisibleToForeground(TestActivity testActivity){
        assertThat(testActivity.stateTracking.pop(), is(StateTracking.onRestart));
        assertThat(testActivity.stateTracking.pop(), is(StateTracking.onStart));
        assertThat(testActivity.stateTracking.pop(), is(StateTracking.onForeground));
    }


}