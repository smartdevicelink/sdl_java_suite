package com.smartdevicelink.abstraction.asynctask;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.os.PowerManager;

import com.smartdevicelink.abstraction.asynctask.ConnectedCarTask.ConnectedCarTaskObject;
import com.smartdevicelink.abstraction.exception.FCHandler;
import com.smartdevicelink.abstraction.log.Log;

public class ConnectedCarTask extends AsyncTask<ConnectedCarTaskObject, Void, Void> {

	private static final String TAG = ConnectedCarTask.class.getSimpleName();
	private PowerManager.WakeLock mWakeLock;
	private Context mContext;

	public ConnectedCarTask(Context context) {
		mWakeLock = ((PowerManager)context.getApplicationContext().getSystemService(Application.POWER_SERVICE)).newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "GamePlayTask");
		mContext = context;
	}
	
	@Override
	protected void onPreExecute() {
		 mWakeLock.acquire();
		Log.i(TAG, "Background task ");
		super.onPreExecute();
	}

	@Override
	protected Void doInBackground(ConnectedCarTaskObject... task) {
		Thread.currentThread().setName("ConnectedCarTask");
		try{
			task[0].execute();
		}catch (Exception ex){
			Log.e(TAG, ex.getMessage(), ex);
		}
		return null;
	}

	@Override
	protected void onPostExecute(Void voids) {
		Log.i(TAG, "Background task Complete");
		 mWakeLock.release();
		super.onPostExecute(voids);
	}

	public interface ConnectedCarTaskObject{
		public void execute();
	}

}
