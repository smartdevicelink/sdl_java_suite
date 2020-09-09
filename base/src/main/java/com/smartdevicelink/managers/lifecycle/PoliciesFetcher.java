/*
 * Copyright (c) 2019, Livio, Inc.
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
 * Neither the name of the Livio Inc. nor the names of its contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
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
package com.smartdevicelink.managers.lifecycle;

import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.rpc.Headers;
import com.smartdevicelink.proxy.rpc.OnSystemRequest;
import com.smartdevicelink.proxy.rpc.PutFile;
import com.smartdevicelink.proxy.rpc.SystemRequest;
import com.smartdevicelink.proxy.rpc.enums.FileType;
import com.smartdevicelink.proxy.rpc.enums.RequestType;
import com.smartdevicelink.util.DebugTool;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;
import java.util.Vector;

class PoliciesFetcher {

    private static final String TAG = PoliciesFetcher.class.getSimpleName();
    static final int POLICIES_CORRELATION_ID = 65535;

    private static HttpURLConnection getURLConnection(Headers myHeader, String sURLString, int Timeout, int iContentLen) {
        String sContentType = "application/json";
        int CONNECTION_TIMEOUT = Timeout * 1000;
        int READ_TIMEOUT = Timeout * 1000;
        boolean bDoOutput = true;
        boolean bDoInput = true;
        boolean bUsesCaches = false;
        String sRequestMeth = "POST";

        boolean bInstFolRed = false;
        String sCharSet = "utf-8";
        int iContentLength = iContentLen;

        URL url;
        HttpURLConnection urlConnection;

        if (myHeader != null) {
            //if the header isn't null, use it and replace the hardcoded params
            int iTimeout;
            int iReadTimeout;
            sContentType = myHeader.getContentType();
            iTimeout = myHeader.getConnectTimeout();
            bDoOutput = myHeader.getDoOutput();
            bDoInput = myHeader.getDoInput();
            bUsesCaches = myHeader.getUseCaches();
            sRequestMeth = myHeader.getRequestMethod();
            iReadTimeout = myHeader.getReadTimeout();
            bInstFolRed = myHeader.getInstanceFollowRedirects();
            sCharSet = myHeader.getCharset();
            iContentLength = myHeader.getContentLength();
            CONNECTION_TIMEOUT = iTimeout * 1000;
            READ_TIMEOUT = iReadTimeout * 1000;
        }

        try {
            url = new URL(sURLString);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(CONNECTION_TIMEOUT);
            urlConnection.setDoOutput(bDoOutput);
            urlConnection.setDoInput(bDoInput);
            urlConnection.setRequestMethod(sRequestMeth);
            urlConnection.setReadTimeout(READ_TIMEOUT);
            urlConnection.setInstanceFollowRedirects(bInstFolRed);
            urlConnection.setRequestProperty("Content-Type", sContentType);
            urlConnection.setRequestProperty("charset", sCharSet);
            urlConnection.setRequestProperty("Content-Length", "" + Integer.toString(iContentLength));
            urlConnection.setUseCaches(bUsesCaches);
            return urlConnection;
        } catch (Exception e) {
            return null;
        }
    }


    public static RPCRequest fetchPolicies(OnSystemRequest msg) {
        HttpURLConnection urlConnection = null;
        boolean bLegacy = false;

        String sURLString = msg.getUrl().replaceFirst("http://", "https://");

        Integer iTimeout = msg.getTimeout();

        if (iTimeout == null)
            iTimeout = 2000;

        Headers myHeader = msg.getHeader();

        try {
            String sBodyString = msg.getBody();

            JSONObject jsonObjectToSendToServer;
            String valid_json = "";
            int length;
            if (sBodyString == null) {
                if (RequestType.HTTP.equals(msg.getRequestType())) {
                    length = msg.getBulkData().length;
                } else {
                    List<String> legacyData = msg.getLegacyData();
                    JSONArray jsonArrayOfSdlPPackets = new JSONArray(legacyData);
                    jsonObjectToSendToServer = new JSONObject();
                    jsonObjectToSendToServer.put("data", jsonArrayOfSdlPPackets);
                    bLegacy = true;
                    valid_json = jsonObjectToSendToServer.toString().replace("\\", "");
                    length = valid_json.getBytes("UTF-8").length;
                }
            } else {
                valid_json = sBodyString.replace("\\", "");
                length = valid_json.getBytes("UTF-8").length;
            }

            urlConnection = getURLConnection(myHeader, sURLString, iTimeout, length);

            if (urlConnection == null) {
                DebugTool.logInfo(TAG, "urlConnection is null, check RPC input parameters");
                return null;
            }

            DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
            if (RequestType.HTTP.equals(msg.getRequestType())) {
                wr.write(msg.getBulkData());
            } else {
                wr.writeBytes(valid_json);
            }

            wr.flush();
            wr.close();


            long BeforeTime = System.currentTimeMillis();
            long AfterTime = System.currentTimeMillis();
            final long roundtriptime = AfterTime - BeforeTime;

            int iResponseCode = urlConnection.getResponseCode();

            if (iResponseCode != HttpURLConnection.HTTP_OK) {
                DebugTool.logInfo(TAG, "Response code not HTTP_OK, returning from sendOnSystemRequestToUrl.");
                return null;
            }

            InputStream is = urlConnection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            //We've read the body
            if (RequestType.HTTP.equals(msg.getRequestType())) {
                // Create the SystemRequest RPC to send to module.
                PutFile putFile = new PutFile();
                putFile.setFileType(FileType.JSON);
                putFile.setCorrelationID(POLICIES_CORRELATION_ID);
                putFile.setSdlFileName("response_data");
                putFile.setFileData(response.toString().getBytes("UTF-8"));
                putFile.setCRC(response.toString().getBytes());
                return putFile;
            } else {
                Vector<String> cloudDataReceived = new Vector<String>();
                final String dataKey = "data";
                // Convert the response to JSON
                JSONObject jsonResponse = new JSONObject(response.toString());
                if (jsonResponse.has(dataKey)) {
                    if (jsonResponse.get(dataKey) instanceof JSONArray) {
                        JSONArray jsonArray = jsonResponse.getJSONArray(dataKey);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            if (jsonArray.get(i) instanceof String) {
                                cloudDataReceived.add(jsonArray.getString(i));
                                //DebugTool.logInfo(TAG, "sendSystemRequestToUrl", "jsonArray.getString(i): " + jsonArray.getString(i));
                            }
                        }
                    } else if (jsonResponse.get(dataKey) instanceof String) {
                        cloudDataReceived.add(jsonResponse.getString(dataKey));
                        //DebugTool.logInfo(TAG, "sendSystemRequestToUrl", "jsonResponse.getString(data): " + jsonResponse.getString("data"));
                    }
                } else {
                    DebugTool.logError(TAG, "sendSystemRequestToUrl: Data in JSON Object neither an array nor a string.");
                    //DebugTool.logInfo(TAG, "sendSystemRequestToUrl", "sendSystemRequestToUrl: Data in JSON Object neither an array nor a string.");
                    return null;
                }

                String sResponse = cloudDataReceived.toString();

                if (sResponse.length() > 512) {
                    sResponse = sResponse.substring(0, 511);
                }

                // Send new SystemRequest to SDL
                SystemRequest mySystemRequest = null;

                if (bLegacy) {
                    if (cloudDataReceived != null) {
                        mySystemRequest = new SystemRequest(true);
                        mySystemRequest.setCorrelationID(POLICIES_CORRELATION_ID);
                        mySystemRequest.setLegacyData(cloudDataReceived);
                    }
                } else {
                    if (response != null) {
                        mySystemRequest = new SystemRequest();
                        mySystemRequest.setRequestType(RequestType.PROPRIETARY);
                        mySystemRequest.setCorrelationID(POLICIES_CORRELATION_ID);
                        mySystemRequest.setBulkData(response.toString().getBytes());
                    }
                }
                return mySystemRequest;

            }
        } catch (JSONException e) {
            DebugTool.logError(TAG, "sendSystemRequestToUrl: JSONException: ", e);
        } catch (UnsupportedEncodingException e) {
            DebugTool.logError(TAG, "sendSystemRequestToUrl: Could not encode string.", e);
        } catch (ProtocolException e) {
            DebugTool.logError(TAG, "sendSystemRequestToUrl: Could not set request method to post.", e);
        } catch (MalformedURLException e) {
            DebugTool.logError(TAG, "sendSystemRequestToUrl: URL Exception when sending SystemRequest to an external server.", e);
        } catch (IOException e) {
            DebugTool.logError(TAG, "sendSystemRequestToUrl: IOException: ", e);
        } catch (Exception e) {
            DebugTool.logError(TAG, "sendSystemRequestToUrl: Unexpected Exception: ", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return null;
    }
}
