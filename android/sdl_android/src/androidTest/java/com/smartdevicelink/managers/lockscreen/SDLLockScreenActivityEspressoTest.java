package com.smartdevicelink.managers.lockscreen;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.smartdevicelink.R;
import com.smartdevicelink.managers.CompletionListener;
import com.smartdevicelink.managers.ISdl;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.rpc.OnDriverDistraction;
import com.smartdevicelink.proxy.rpc.OnHMIStatus;
import com.smartdevicelink.proxy.rpc.enums.DriverDistractionState;
import com.smartdevicelink.proxy.rpc.enums.HMILevel;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCNotificationListener;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;


//These tests are used to ensure the lockScreen UI Behavior
//They are ignored for CICD purposes and should be run manually during release testing
@RunWith(AndroidJUnit4.class)
@LargeTest
@Ignore //Remove this annotation before running these tests
public class SDLLockScreenActivityEspressoTest {

    private OnRPCNotificationListener onDDListener;
    private OnRPCNotificationListener onHMIListener;

    @Rule
    public ActivityScenarioRule<SDLLockScreenActivity> activityRule =
            new ActivityScenarioRule<>(SDLLockScreenActivity.class);

    @Rule
    public IntentsTestRule<SDLLockScreenActivity> intentsTestRule =
            new IntentsTestRule<>(SDLLockScreenActivity.class);

    @Test
    public void test1() {
        testLockScreenBehavior(DriverDistractionState.DD_OFF, null, true, null, false);
    }

    @Test
    public void test2() {
        testLockScreenBehavior(DriverDistractionState.DD_OFF, false, true, null, false);
    }

    @Test
    public void test3() {
        testLockScreenBehavior(DriverDistractionState.DD_OFF, true, true, null, true);
    }

    @Test
    public void test4() {
        testLockScreenBehavior(DriverDistractionState.DD_OFF, null, false, false, false);
    }

    @Test
    public void test5() {
        testLockScreenBehavior(DriverDistractionState.DD_OFF, null, false, true, true);
    }

    @Test
    public void test6() {
        testLockScreenBehavior(DriverDistractionState.DD_OFF, false, false, false, false);
    }

    @Test
    public void test7() {
        testLockScreenBehavior(DriverDistractionState.DD_OFF, false, false, true, false);
    }

    @Test
    public void test8() {
        testLockScreenBehavior(DriverDistractionState.DD_OFF, true, false, true, true);
    }

    @Test
    public void test9() {
        testLockScreenBehavior(DriverDistractionState.DD_ON, true, false, false, true);
    }

    @Test
    public void test10() {
        testLockScreenBehavior(DriverDistractionState.DD_ON, null, true, null, false);
    }

    @Test
    public void test11() {
        testLockScreenBehavior(DriverDistractionState.DD_ON, false, true, null, false);
    }

    @Test
    public void test12() {
        testLockScreenBehavior(DriverDistractionState.DD_ON, true, true, null, true);
    }

    @Test
    public void test13() {
        testLockScreenBehavior(DriverDistractionState.DD_ON, null, false, false, false);
    }

    @Test
    public void test14() {
        testLockScreenBehavior(DriverDistractionState.DD_ON, null, false, true, true);
    }

    @Test
    public void test15() {
        testLockScreenBehavior(DriverDistractionState.DD_ON, false, false, false, false);
    }

    @Test
    public void test16() {
        testLockScreenBehavior(DriverDistractionState.DD_ON, false, false, true, false);
    }

    @Test
    public void test17() {
        testLockScreenBehavior(DriverDistractionState.DD_ON, true, false, true, true);
    }

    @Test
    public void test18() {
        testLockScreenBehavior(DriverDistractionState.DD_ON, true, false, false, true);
    }

    public void testLockScreenBehavior(final DriverDistractionState dd, final Boolean lockScreenDismissibility, final boolean firstDD, final Boolean previousLockScreenDismissibility, final boolean dismissEnabled) {
        LockScreenConfig lockScreenConfig = new LockScreenConfig();
        lockScreenConfig.setDisplayMode(LockScreenConfig.DISPLAY_MODE_ALWAYS);
        lockScreenConfig.enableDismissGesture(true);
        lockScreenConfig.setCustomView(0);
        lockScreenConfig.setAppIcon(0);

        if (Looper.myLooper() == null) {
            Looper.prepare();
        }
        LockScreenManager lockScreenManager = setupLockScreenManager(lockScreenConfig);

        lockScreenManager.start(new CompletionListener() {
            @Override
            public void onComplete(boolean success) {
                OnHMIStatus onHMIStatus = new OnHMIStatus();
                onHMIStatus.setHmiLevel(HMILevel.HMI_FULL);
                onHMIListener.onNotified(onHMIStatus);

                if (!firstDD) {
                    OnDriverDistraction firstOnDriverDistraction = new OnDriverDistraction();
                    if (dd == DriverDistractionState.DD_OFF) {
                        firstOnDriverDistraction.setState(DriverDistractionState.DD_ON);
                    } else {
                        firstOnDriverDistraction.setState(DriverDistractionState.DD_OFF);
                    }

                    if (previousLockScreenDismissibility != null) {
                        firstOnDriverDistraction.setLockscreenDismissibility(previousLockScreenDismissibility);
                    }

                    onDDListener.onNotified(firstOnDriverDistraction);
                }

                OnDriverDistraction onDriverDistraction = new OnDriverDistraction();
                if (lockScreenDismissibility != null) {
                    onDriverDistraction.setLockscreenDismissibility(lockScreenDismissibility);
                }
                onDriverDistraction.setState(dd);
                onDDListener.onNotified(onDriverDistraction);


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (dismissEnabled) {
                            onView(withText(R.string.default_lockscreen_warning_message)).check(matches(isDisplayed()));
                        } else {
                            onView(withText(R.string.lockscreen_text)).check(matches(isDisplayed()));
                        }

                        BroadcastReceiver receiver = new BroadcastReceiver() {
                            @Override
                            public void onReceive(Context context, Intent intent) {
                                if (dismissEnabled) {
                                    assertEquals(intent.getAction(), SDLLockScreenActivity.KEY_LOCKSCREEN_DISMISSED);
                                } else {
                                    //Activity should not be dismissible test failed due to lock screen being dismissed
                                    fail();
                                }
                            }
                        };

                        intentsTestRule.getActivity().registerReceiver(receiver, new IntentFilter(SDLLockScreenActivity.KEY_LOCKSCREEN_DISMISSED));

                        onView(ViewMatchers.withId(R.id.lockscreen_linear_layout)).perform(ViewActions.swipeDown());
                    }
                }, 1000);
            }
        });
    }

    private LockScreenManager setupLockScreenManager(LockScreenConfig lockScreenConfig) {
        ISdl internalInterface = mock(ISdl.class);

        Answer<Void> onDDStatusAnswer = new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                onDDListener = (OnRPCNotificationListener) args[1];
                return null;
            }
        };

        Answer<Void> onHMIStatusAnswer = new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                onHMIListener = (OnRPCNotificationListener) args[1];
                return null;
            }
        };

        doAnswer(onDDStatusAnswer).when(internalInterface).addOnRPCNotificationListener(eq(FunctionID.ON_DRIVER_DISTRACTION), any(OnRPCNotificationListener.class));
        doAnswer(onHMIStatusAnswer).when(internalInterface).addOnRPCNotificationListener(eq(FunctionID.ON_HMI_STATUS), any(OnRPCNotificationListener.class));

        Context context = getInstrumentation().getContext();

        return new LockScreenManager(lockScreenConfig, context, internalInterface);
    }
}
