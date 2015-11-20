package com.smartdevicelink.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import android.os.AsyncTask;
import android.util.Log;

public class HttpAsyncTask extends AsyncTask<Object, Object, Object>{
	private static final String TAG = "HttpResponseStringTask";

	HttpAsyncTaskCallback cb;
	public HttpAsyncTask( HttpAsyncTaskCallback hcb){
		this.cb = hcb;
	}
	@Override
	protected Object doInBackground(Object... params) {
		StringBuilder builder = new StringBuilder();
		
		HttpClient client = createHttpClient();//new DefaultHttpClient();
		
		try {
			//Log.i(TAG, "URL BEING CALLED: " + params[0]);
			HttpResponse response;
		//Check what type of httpResonse we want	
		if(params[0] instanceof HttpGet){
			HttpGet httpGet = (HttpGet)params[0];
			response = client.execute(httpGet);
		}else if(params[0] instanceof HttpPost){//This must be a post
			HttpPost httPost = (HttpPost)params[0];
			response = client.execute(httPost);
		}
		else{
			cb.httpFailure(0);
			return false;
		}
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode == 200) {
				HttpEntity entity = response.getEntity();
				InputStream content = entity.getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(content));
				String line;
				while ((line = reader.readLine()) != null) {
					builder.append(line);
				}
			} else {
				cb.httpFailure(statusCode);
				Log.e(TAG, "Failed to download file");
				return false;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			cb.httpFailure(-1);
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			cb.httpFailure(-1);
			return false;
		}
		cb.httpCallComplete(builder.toString());
		return true;
		
	}

	private HttpClient createHttpClient(){
	    HttpParams params = new BasicHttpParams();
	    HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
	    HttpProtocolParams.setContentCharset(params, HTTP.DEFAULT_CONTENT_CHARSET);
	    HttpProtocolParams.setUseExpectContinue(params, true);
	   // ConnManagerParams.setTimeout(params, 1000);
        //HttpConnectionParams.setConnectionTimeout(params, 5000);
	   // HttpConnectionParams.setSoTimeout(params, 10000);
	    SchemeRegistry schReg = new SchemeRegistry();
	    schReg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
	    schReg.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
	    ClientConnectionManager conMgr = new ThreadSafeClientConnManager(params, schReg);

	    return new DefaultHttpClient(conMgr, params);
	}
	
	/**
	 * Callback interface for HTTP requests.
	 * @author Joey Grover
	 *
	 */
	public interface HttpAsyncTaskCallback{
		/**
		 * Called when HTTP request is successfully completed.
		 * @param response The response to the HTTP request.
		 */
		public abstract void httpCallComplete(String response);
		/**
		 * Called when HTTP request failed.
		 * @param statusCode The HTTP failure code.
		 */
		public abstract void httpFailure(int statusCode);
	}
}
