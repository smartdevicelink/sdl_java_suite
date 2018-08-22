package com.sdl.hellosdlandroid;

import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;

import com.smartdevicelink.proxy.rpc.enums.LockScreenStatus;

public class LockScreenActivity extends Activity {
    // This will be set to true if there is any activity running
    // onResume will set this variable to true
    // onPause will set this variable to false
    // As a fallback to old API levels this will be set to true forever
    private static boolean     ACTIVITY_RUNNING;
    // This will hold the activity instance of the lock screen if created
    // onCreate will set this variable to the current lock screen instance
    // onDestroy will set this variable to null
    private static Activity         LOCKSCREEN_INSTANCE;
    // This will hold the current lock screen status
    private static LockScreenStatus LOCKSCREEN_STATUS;
    // This will ensure that the lifecycle is registered only once
    private static boolean          ACTIVITY_LIFECYCLE_REGISTERED;
    // This will hold the lifecycle callback object
    private static ActivityLifecycleCallbacks ACTIVITY_LIFECYCLE_CALLBACK;
    // This will hold the instance of the application object
    private static Application APPLICATION;
    // This will hold the bitmap to update the lockscreen image
    static Bitmap lockscreenIcon = null;


    static {
        ACTIVITY_RUNNING = false;
        LOCKSCREEN_INSTANCE = null;
        LOCKSCREEN_STATUS = LockScreenStatus.OFF;

        ACTIVITY_LIFECYCLE_REGISTERED = false;
    }

    public static void registerActivityLifecycle(Application application) {
        // register only once
        if (ACTIVITY_LIFECYCLE_REGISTERED == false) {
            ACTIVITY_LIFECYCLE_REGISTERED = true;

            // check if API level is >= 14
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                // create the callback
                ACTIVITY_LIFECYCLE_CALLBACK = new ActivityLifecycleCallbacks() {
                    @Override
                    public void onActivityResumed(Activity activity) {
                        ACTIVITY_RUNNING = true;
                        // recall this method so the lock screen comes up when necessary
                        updateLockScreenStatus(LOCKSCREEN_STATUS);

                        ImageView lockscreenIV = (ImageView) activity.findViewById(R.id.lockscreen);
                        if(lockscreenIcon != null && lockscreenIV != null) {
                            lockscreenIV.setImageBitmap(lockscreenIcon);
                            lockscreenIcon = null;
                        }
                    }

                    @Override
                    public void onActivityPaused(Activity activity) {
                        ACTIVITY_RUNNING = false;
                    }

                    @Override
                    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

                    }

                    @Override
                    public void onActivityStarted(Activity activity) {
                    }

                    @Override
                    public void onActivityStopped(Activity activity) {
                    }

                    @Override
                    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                    }

                    @Override
                    public void onActivityDestroyed(Activity activity) {
                    }
                };

                APPLICATION = application;

                // do the activity registration
                application.registerActivityLifecycleCallbacks(ACTIVITY_LIFECYCLE_CALLBACK);
            } else {
                // fallback and assume we always have an activity
                ACTIVITY_RUNNING = true;
            }
        }
    }

    public static void updateLockScreenStatus(LockScreenStatus status) {
        LOCKSCREEN_STATUS = status;

        if (status.equals(LockScreenStatus.OFF)) {
            // do we have a lock screen? if so we need to remove it
            if (LOCKSCREEN_INSTANCE != null) {
                LOCKSCREEN_INSTANCE.finish();
            }
        } else {
            // do we miss a lock screen and app is in foreground somehow? if so we need to lock it
            if (LOCKSCREEN_INSTANCE == null && ACTIVITY_RUNNING == true) {
                Intent intent = new Intent(APPLICATION, LockScreenActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_USER_ACTION);

                APPLICATION.startActivity(intent);
            }
        }
    }

    public static void updateLockScreenImage(Bitmap icon){
        lockscreenIcon = icon;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_screen);

        LOCKSCREEN_INSTANCE = this;

        // redo the checkup
        updateLockScreenStatus(LOCKSCREEN_STATUS);
    }

    @Override
    protected void onDestroy() {
        LOCKSCREEN_INSTANCE = null;

        super.onDestroy();
    }

    @Override
    public void onBackPressed() {

    }
}
