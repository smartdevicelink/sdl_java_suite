package com.smartdevicelink.api;

import com.smartdevicelink.api.testUtil.SdlTestActivity;
import com.smartdevicelink.api.testUtil.SdlTestActivity.StateTracking;

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
        sdlActivityManager.onSdlAppLaunch(sdlApplication, SdlTestActivity.class);
        SdlTestActivity tester= (SdlTestActivity) sdlActivityManager.getTopActivity();
        checkConnectedToBackground(tester);

        assertThat(tester.stateTracking.isEmpty(), is(true));
    }

    @Test
    public void verifyCompleteForegroundTransition(){
        sdlActivityManager.onSdlAppLaunch(sdlApplication, SdlTestActivity.class);
        sdlActivityManager.onForeground();
        SdlTestActivity tester= (SdlTestActivity) sdlActivityManager.getTopActivity();
        checkConnectedToBackground(tester);
        checkBackgroundToForeground(tester);
        assertThat(tester.stateTracking.isEmpty(), is(true));
    }

    @Test
    public void verifyCompleteLifeCycle(){
        sdlActivityManager.onSdlAppLaunch(sdlApplication, SdlTestActivity.class);
        sdlActivityManager.onForeground();
        SdlTestActivity tester= (SdlTestActivity) sdlActivityManager.getTopActivity();
        sdlActivityManager.onExit();
        checkConnectedToBackground(tester);
        checkBackgroundToForeground(tester);
        checkForegroundToBackground(tester);
        checkBackgroundToDisconnected(tester);
        assertThat(tester.stateTracking.isEmpty(), is(true));

    }

    @Test
    public void verifySuddenDisconnection(){
        sdlActivityManager.onSdlAppLaunch(sdlApplication, SdlTestActivity.class);
        SdlTestActivity tester= (SdlTestActivity) sdlActivityManager.getTopActivity();
        sdlActivityManager.onSdlDisconnect();
        checkConnectedToBackground(tester);
        checkBackgroundToDisconnected(tester);
        assertThat(tester.stateTracking.isEmpty(), is(true));

    }

    @Test
    public void verifyStartOfNewBackgroundActivity(){
        sdlActivityManager.onSdlAppLaunch(sdlApplication, SdlTestActivity.class);
        SdlTestActivity pushedBack= (SdlTestActivity) sdlActivityManager.getTopActivity();
        checkConnectedToBackground(pushedBack);
        pushedBack.startSdlActivity(SdlTestActivity.class, 0);
        SdlTestActivity topActivity= (SdlTestActivity) sdlActivityManager.getTopActivity();
        checkBackgroundToStopped(pushedBack);
        checkConnectedToBackground(topActivity);

        assertThat(pushedBack.stateTracking.isEmpty(), is(true));
        assertThat(topActivity.stateTracking.isEmpty(), is(true));
    }

    @Test
    public void verifyStartOfNewForegroundActivity(){
        sdlActivityManager.onSdlAppLaunch(sdlApplication, SdlTestActivity.class);
        sdlActivityManager.onForeground();
        SdlTestActivity pushedBack= (SdlTestActivity) sdlActivityManager.getTopActivity();
        checkConnectedToBackground(pushedBack);
        checkBackgroundToForeground(pushedBack);
        pushedBack.startSdlActivity(SdlTestActivity.class, 0);
        SdlTestActivity topActivity= (SdlTestActivity) sdlActivityManager.getTopActivity();
        checkForegroundToBackground(pushedBack);
        checkBackgroundToStopped(pushedBack);
        checkConnectedToBackground(topActivity);
        checkBackgroundToForeground(topActivity);

        assertThat(pushedBack.stateTracking.isEmpty(), is(true));
        assertThat(topActivity.stateTracking.isEmpty(), is(true));
    }

    @Test
    public void verifyBackWithOneDoesNotRemoveFromStack(){
        sdlActivityManager.onSdlAppLaunch(sdlApplication, SdlTestActivity.class);
        SdlTestActivity tester= (SdlTestActivity) sdlActivityManager.getTopActivity();
        sdlActivityManager.back();
        checkConnectedToBackground(tester);
        assertThat(tester.stateTracking.isEmpty(), is(true));
    }


    @Test
    public void verifyNormalBackFromBackgroundActivity(){
        sdlActivityManager.onSdlAppLaunch(sdlApplication, SdlTestActivity.class);
        SdlTestActivity pushedBack= (SdlTestActivity) sdlActivityManager.getTopActivity();
        checkConnectedToBackground(pushedBack);
        pushedBack.startSdlActivity(SdlTestActivity.class, 0);
        SdlTestActivity topActivity= (SdlTestActivity) sdlActivityManager.getTopActivity();
        checkBackgroundToStopped(pushedBack);
        checkConnectedToBackground(topActivity);

        topActivity.onBackPressed();
        checkBackgroundToDisconnected(topActivity);
        checkStoppedToBackground(pushedBack);

        assertThat(pushedBack.stateTracking.isEmpty(), is(true));
        assertThat(topActivity.stateTracking.isEmpty(), is(true));
    }

    @Test
    public void verifyNormalBackFromForegroundActivity(){
        sdlActivityManager.onSdlAppLaunch(sdlApplication, SdlTestActivity.class);
        sdlActivityManager.onForeground();
        SdlTestActivity pushedBack= (SdlTestActivity) sdlActivityManager.getTopActivity();
        checkConnectedToBackground(pushedBack);
        checkBackgroundToForeground(pushedBack);
        pushedBack.startSdlActivity(SdlTestActivity.class, 0);
        SdlTestActivity topActivity= (SdlTestActivity) sdlActivityManager.getTopActivity();

        checkForegroundToBackground(pushedBack);
        checkBackgroundToStopped(pushedBack);

        checkConnectedToBackground(topActivity);
        checkBackgroundToForeground(topActivity);

        topActivity.onBackPressed();
        checkForegroundToBackground(topActivity);
        checkBackgroundToDisconnected(topActivity);
        checkStoppedToForeground(pushedBack);

        assertThat(pushedBack.stateTracking.isEmpty(), is(true));
        assertThat(topActivity.stateTracking.isEmpty(), is(true));
    }

    @Test
    public void verifyOnExitWithBiggerBackStack(){
        sdlActivityManager.onSdlAppLaunch(sdlApplication, SdlTestActivity.class);
        sdlActivityManager.onForeground();
        SdlTestActivity pushedBack= (SdlTestActivity) sdlActivityManager.getTopActivity();
        checkConnectedToBackground(pushedBack);
        checkBackgroundToForeground(pushedBack);
        pushedBack.startSdlActivity(SdlTestActivity.class, 0);
        SdlTestActivity topActivity= (SdlTestActivity) sdlActivityManager.getTopActivity();

        checkForegroundToBackground(pushedBack);
        checkBackgroundToStopped(pushedBack);

        checkConnectedToBackground(topActivity);
        checkBackgroundToForeground(topActivity);

        topActivity.onBackPressed();
        checkForegroundToBackground(topActivity);
        checkBackgroundToDisconnected(topActivity);
        checkStoppedToForeground(pushedBack);

        assertThat(pushedBack.stateTracking.isEmpty(), is(true));
        assertThat(topActivity.stateTracking.isEmpty(), is(true));
    }



    //Application specific callback checking

    private void checkConnectedToBackground(SdlTestActivity sdlTestActivity){
        assertThat(sdlTestActivity.stateTracking.pop(), is(StateTracking.onCreate));
        assertThat(sdlTestActivity.stateTracking.pop(), is(StateTracking.onStart));
    }

    private void checkBackgroundToForeground(SdlTestActivity sdlTestActivity){
        assertThat(sdlTestActivity.stateTracking.pop(), is(StateTracking.onForeground));
    }

    private void checkForegroundToBackground(SdlTestActivity sdlTestActivity){
        assertThat(sdlTestActivity.stateTracking.pop(), is(StateTracking.onBackground));
    }

    private void checkBackgroundToDisconnected(SdlTestActivity sdlTestActivity){
        assertThat(sdlTestActivity.stateTracking.pop(), is(StateTracking.onStop));
        assertThat(sdlTestActivity.stateTracking.pop(), is(StateTracking.onDestory));
    }

    //Activity specific callback checking

    private void checkStoppedToDestroy(SdlTestActivity sdlTestActivity){
        assertThat(sdlTestActivity.stateTracking.pop(), is(StateTracking.onDestory));
    }

    private void checkBackgroundToStopped(SdlTestActivity sdlTestActivity){
        assertThat(sdlTestActivity.stateTracking.pop(), is(StateTracking.onStop));
    }

    private void checkStoppedToBackground(SdlTestActivity sdlTestActivity){
        assertThat(sdlTestActivity.stateTracking.pop(), is(StateTracking.onRestart));
        assertThat(sdlTestActivity.stateTracking.pop(), is(StateTracking.onStart));
    }

    private void checkStoppedToForeground(SdlTestActivity sdlTestActivity){
        assertThat(sdlTestActivity.stateTracking.pop(), is(StateTracking.onRestart));
        assertThat(sdlTestActivity.stateTracking.pop(), is(StateTracking.onStart));
        assertThat(sdlTestActivity.stateTracking.pop(), is(StateTracking.onForeground));
    }


}