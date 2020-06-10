package com.smartdevicelink.util;

@Deprecated
public class HttpRequestTask {
    public static final String REQUEST_TYPE_POST = "POST";
    public static final String REQUEST_TYPE_GET = "GET";
    public static final String REQUEST_TYPE_DELETE = "DELETE";

    @Deprecated
    public HttpRequestTask( HttpRequestTaskCallback hcb){ }

    @Deprecated
    protected String doInBackground(String... params) {
        return null;
    }

    @Deprecated
    public interface HttpRequestTaskCallback{
        public abstract void httpCallComplete(String response);
        public abstract void httpFailure(int statusCode);
    }

}
