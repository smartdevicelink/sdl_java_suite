package com.smartdevicelink.api.lockscreen;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;

import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.smartdevicelink.R;

public class SDLLockScreenActivity extends Activity {

	public static final String LOCKSCREEN_COLOR_EXTRA = "LOCKSCREEN_COLOR_EXTRA";
	public static final String LOCKSCREEN_ICON_EXTRA = "LOCKSCREEN_ICON_EXTRA";
	public static final String LOCKSCREEN_DEVICE_ICON_EXTRA = "LOCKSCREEN_DEVICE_ICON_EXTRA";
	public static final String LOCKSCREEN_DEVICE_ICON_BITMAP = "LOCKSCREEN_DEVICE_ICON_BITMAP";
	public static final String LOCKSCREEN_CUSTOM_VIEW_EXTRA = "LOCKSCREEN_CUSTOM_VIEW_EXTRA";
	public static final String LOCKSCREEN_ICON_DOWNLOADED = "LOCKSCREEN_ICON_DOWNLOADED";
	public static final String CLOSE_LOCK_SCREEN_ACTION = "CLOSE_LOCK_SCREEN";
	private static final String TAG = "SDLLockScreenActivity";

	private final BroadcastReceiver lockScreenBroadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent != null){
				String action = intent.getAction();
				if (action != null){
					if (action.equalsIgnoreCase(CLOSE_LOCK_SCREEN_ACTION)){
						finish();
					} else if (action.equalsIgnoreCase(LOCKSCREEN_ICON_DOWNLOADED)){
						boolean displayDeviceImage = intent.getBooleanExtra(LOCKSCREEN_DEVICE_ICON_EXTRA, true);
						Bitmap displayDeviceIcon = intent.getParcelableExtra(LOCKSCREEN_DEVICE_ICON_BITMAP);
						if (displayDeviceImage && displayDeviceIcon != null){
							displayDeviceImage(displayDeviceIcon);
						}
					}
				}
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		// set any parameters that came from the lock screen manager
		initializeActivity(getIntent());

		// register broadcast receivers
		registerReceiver(lockScreenBroadcastReceiver, new IntentFilter(CLOSE_LOCK_SCREEN_ACTION));
	}

	@Override
	protected void onDestroy() {
		unregisterReceiver(lockScreenBroadcastReceiver);
		super.onDestroy();
	}

	public void initializeActivity(Intent intent){
		if (intent != null){
			boolean displayDeviceImage = intent.getBooleanExtra(LOCKSCREEN_DEVICE_ICON_EXTRA, true);
			int customColor = intent.getIntExtra(LOCKSCREEN_COLOR_EXTRA, 0);
			int customIcon = intent.getIntExtra(LOCKSCREEN_ICON_EXTRA, 0);
			int customView = intent.getIntExtra(LOCKSCREEN_CUSTOM_VIEW_EXTRA, 0);
			Bitmap displayDeviceIcon = intent.getParcelableExtra(LOCKSCREEN_DEVICE_ICON_BITMAP);

			if (customView != 0){
				setCustomView(customView);
			} else {
				setContentView(R.layout.activity_sdllock_screen);

				if (customColor != 0){
					changeBackgroundColor(customColor);
				}

				if (customIcon != 0){
					changeIcon(customIcon);
				}

				if (displayDeviceImage && displayDeviceIcon != null){
					displayDeviceImage(displayDeviceIcon);
				}
			}
		}
	}

	private void changeBackgroundColor(int customColor) {
		RelativeLayout layout = findViewById(R.id.lockscreen_relative_layout);
		layout.setBackgroundColor(getResources().getColor(customColor));
	}

	private void changeIcon(int customIcon) {
		ImageView lockscreen_iv = findViewById(R.id.lockscreen_image);
		lockscreen_iv.setBackgroundResource(customIcon);
	}

	private void displayDeviceImage(Bitmap displayDeviceIcon) {
		ImageView display_device_iv = findViewById(R.id.display_device_image);
		if (displayDeviceIcon != null) {
			display_device_iv.setImageBitmap(displayDeviceIcon);
		}
	}

	private void setCustomView(int customView) {
		setContentView(customView);
	}

}
