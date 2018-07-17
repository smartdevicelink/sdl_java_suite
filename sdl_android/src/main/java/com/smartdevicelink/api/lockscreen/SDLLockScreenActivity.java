package com.smartdevicelink.api.lockscreen;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;

import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.smartdevicelink.R;
import com.smartdevicelink.util.HttpUtils;

import java.io.IOException;

public class SDLLockScreenActivity extends Activity {

	public static final String LOCKSCREEN_COLOR_EXTRA = "LOCKSCREEN_COLOR_EXTRA";
	public static final String LOCKSCREEN_ICON_EXTRA = "LOCKSCREEN_ICON_EXTRA";
	public static final String LOCKSCREEN_CUSTOM_VIEW_EXTRA = "LOCKSCREEN_CUSTOM_VIEW_EXTRA";
	public static final String CLOSE_LOCK_SCREEN_ACTION = "CLOSE_LOCK_SCREEN";
	public static final String DOWNLOAD_ICON_ACTION = "DOWNLOAD_ICON";
	public static final String DOWNLOAD_ICON_URL = "DOWNLOAD_ICON_URL";
	private static final String TAG = "SDLLockScreenActivity";

	private Bitmap lockScreenOEMIcon;
	private int customView, customIcon, customColor;

	private final BroadcastReceiver closeLockScreenBroadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			finish();
		}
	};

	private final BroadcastReceiver downloadLockScreenIconBroadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			// get data from intent
			if (intent != null) {
				Log.i(TAG, "downloadLockScreenIconBroadcastReceiver called");
				String URL = intent.getStringExtra(DOWNLOAD_ICON_URL);
				if (URL != null) {
					downloadLockScreenIcon(URL, null);
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
		registerReceiver(closeLockScreenBroadcastReceiver, new IntentFilter(CLOSE_LOCK_SCREEN_ACTION));
		registerReceiver(downloadLockScreenIconBroadcastReceiver, new IntentFilter(DOWNLOAD_ICON_ACTION));
	}


	@Override
	protected void onDestroy() {
		unregisterReceiver(closeLockScreenBroadcastReceiver);
		unregisterReceiver(downloadLockScreenIconBroadcastReceiver);
		super.onDestroy();
	}

	public void initializeActivity(Intent intent){
		if (intent != null){
			customColor = intent.getIntExtra(LOCKSCREEN_COLOR_EXTRA, 0);
			customIcon = intent.getIntExtra(LOCKSCREEN_ICON_EXTRA, 0);
			customView = intent.getIntExtra(LOCKSCREEN_CUSTOM_VIEW_EXTRA, 0);

			if (customView != 0){
				setCustomView();
			} else {
				setContentView(R.layout.activity_sdllock_screen);

				if (customColor != 0){
					changeBackgroundColor();
				}

				if (customIcon != 0){
					changeIcon();
				}
			}
		}
	}

	private void changeBackgroundColor() {
		RelativeLayout layout = findViewById(R.id.lockscreen_relative_layout);
		layout.setBackgroundColor(customColor);
	}

	private void changeIcon() {
		ImageView lockscreen_iv = findViewById(R.id.lockscreen_image);
		lockscreen_iv.setBackgroundResource(customIcon);
	}

	private void setCustomView() {
		setContentView(customView);
	}

	public void downloadLockScreenIcon(final String url, final LockScreenManager.OnLockScreenIconDownloadedListener lockScreenListener){
		new Thread(new Runnable(){
			@Override
			public void run(){
				try{
					lockScreenOEMIcon = HttpUtils.downloadImage(url);
					if(lockScreenListener != null){
						Log.i(TAG, "Lock Screen Icon Downloaded");
						lockScreenListener.onLockScreenIconDownloaded(lockScreenOEMIcon);
					}
				}catch(IOException e){
					if(lockScreenListener != null){
						Log.e(TAG, "Lock Screen Icon Error Downloading");
						lockScreenListener.onLockScreenIconDownloadError(e);
					}
				}
			}
		}).start();
	}

}
