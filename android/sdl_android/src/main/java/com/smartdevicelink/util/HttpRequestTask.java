/*
 * Copyright (c) 2017 - 2019, SmartDeviceLink Consortium, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided with the
 * distribution.
 *
 * Neither the name of the SmartDeviceLink Consortium, Inc. nor the names of its
 * contributors may be used to endorse or promote products derived from this
 * software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package com.smartdevicelink.util;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpRequestTask extends AsyncTask<String, String, String> {
    private static final String TAG = "Http Request Task";

    public static final String REQUEST_TYPE_POST = "POST";
    public static final String REQUEST_TYPE_GET = "GET";
    public static final String REQUEST_TYPE_DELETE = "DELETE";

    HttpRequestTaskCallback cb;

    /**
     * @param hcb callback for when this task finishes
     *            <br><br><b> - When calling execute, params as followed: </b><br>
     *            1. Url String<br>
     *            2. Request type (Defined in this class) REQUEST_TYPE_POST, REQUEST_TYPE_GET, REQUEST_TYPE_DELETE<br>
     *            3. (Optional) Data to be sent. <br>
     *            4. (Optional) Content Type  Default will be application/json<br>
     *            5. (Optional) Accept Type  default will be application/json
     */
    public HttpRequestTask(HttpRequestTaskCallback hcb) {
        this.cb = hcb;
    }

    @Override
    protected String doInBackground(String... params) {
        int length = params.length;
        String urlString = params[0];
        String request_type = params[1];

        //Grab and set data to be written if included
        String data;
        if (length > 2) {
            data = params[2];
        } else {
            data = null;
        }

        //Grab and set content type for the header if included
        String contentType;
        if (length > 3) {
            contentType = params[3];
        } else {
            contentType = "application/json";
        }
        //Grab and set accept type for the header if included
        String acceptType;
        if (length > 4) {
            acceptType = params[4];
        } else {
            acceptType = "application/json";
        }

        if (urlString == null || request_type == null) {
            DebugTool.logError(TAG, "Can't process request, param error");
            if (cb != null) {
                cb.httpFailure(-1);
                cb = null;
            }
            return "Error";
        }

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod(request_type);
            urlConnection.setRequestProperty("Content-Type", contentType);
            urlConnection.setRequestProperty("Accept", acceptType);
            //If we have data, we should write it out
            if (data != null) {
                Writer writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8"));
                writer.write(data);
                writer.close();
            }
            InputStream inputStream = urlConnection.getInputStream();

            int responseCode = urlConnection.getResponseCode();
            if (responseCode == 200) { //Success
                //input stream
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    if (cb != null) {
                        cb.httpCallComplete(null);
                        cb = null;
                    }
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String inputLine;
                while ((inputLine = reader.readLine()) != null)
                    buffer.append(inputLine).append("\n");
                if (buffer.length() == 0) {
                    // Stream was empty. No point in parsing.
                    if (cb != null) {
                        cb.httpCallComplete(null);
                        cb = null;
                    }
                    return null;
                }
                String response;

                response = buffer.toString();
                //send to post execute
                if (cb != null) {
                    cb.httpCallComplete(response);
                    cb = null;
                }
                return response;
            } else {
                if (cb != null) {
                    cb.httpFailure(responseCode);
                    cb = null;
                }
                DebugTool.logError(TAG, "Failed to download file - " + responseCode);
                return null;
            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) { // Only to catch error in urlConnection.getOutputStream() - when servers are down
            e.printStackTrace();
            urlConnection = null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    DebugTool.logError(TAG, "Error closing stream", e);
                }
            }
            if (cb != null) {
                cb.httpFailure(-1);
            }
        }
        return null;
    }

    /**
     * Callback interface for HTTP requests.
     *
     * @author Joey Grover
     */
    public interface HttpRequestTaskCallback {
        /**
         * Called when HTTP request is successfully completed.
         *
         * @param response The response to the HTTP request.
         */
        void httpCallComplete(String response);

        /**
         * Called when HTTP request failed.
         *
         * @param statusCode The HTTP failure code.
         */
        void httpFailure(int statusCode);
    }

}
