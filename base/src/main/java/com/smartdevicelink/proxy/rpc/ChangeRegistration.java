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
package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.rpc.enums.Language;

import java.util.Hashtable;
import java.util.List;

/**
 * If the app recognizes during the app registration that the SDL HMI language
 * (voice/TTS and/or display) does not match the app language, the app will be
 * able (but does not need) to change this registration with changeRegistration
 * prior to app being brought into focus.
 * 
 * <p>Function Group: Base</p>
 * 
 * <p><b>HMILevel can by any</b></p>
 * 
 * <b>Note:</b>
 * 
 * <p>SDL will send the language value confirmed to be supported by HMI via UI.GetCapabilities.</p>
 * <p><b> Parameter List</b></p>
 * 
 * <table border="1" rules="all">
 * 		<tr>
 * 			<th>Name</th>
 * 			<th>Type</th>
 * 			<th>Description</th>
 *                 <th> Req.</th>
 * 			<th>Notes</th>
 * 			<th>Version Available</th>
 * 		</tr>
 * 		<tr>
 * 			<td>Language</td>
 * 			<td>Language</td>
 * 			<td>Requested SDL voice engine (VR+TTS) language registration.</td>
 *                 <td>Y</td>
 * 			<td></td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>hmiDisplayLanguage</td>
 * 			<td>Language</td>
 * 			<td>Request display language registration.</td>
 *                 <td>Y</td>
 * 			<td>Minvalue=0; Maxvalue=2000000000</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 *
 *            <tr>
 * 			<td>appName</td>
 * 			<td>String</td>
 * 			<td>Request new app name registration</td>
 *                 <td>N</td>
 *                 <td>maxlength:100</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>ttsName</td>
 * 			<td>TTSChunk</td>
 * 			<td>Request new ttsName registration</td>
 *                 <td>N</td>
 *                 <td>minsize:1; maxsize:100</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 *            <tr>
 * 			<td>ngnMediaScreenAppName</td>
 * 			<td>String</td>
 * 			<td>Request new app short name registration</td>
 *                 <td>N</td>
 *                 <td>maxlength: 100</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>vrSynonyms</td>
 * 			<td>String</td>
 * 			<td>Request new VR synonyms registration</td>
 *                 <td>N</td>
 *                 <td>maxlength: 40; minsize:1; maxsize:100</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 *  </table>
 *  
 * <p><b>Response </b></p>
 * 
 * <p><b>Non-default Result Codes:</b></p>
 * 	<p>SUCCESS</p>
 * 	<p>INVALID_DATA</p>
 * 	<p>OUT_OF_MEMORY</p>
 * <p>	TOO_MANY_PENDING_REQUESTS</p>
 * 	<p>APPLICATION_NOT_REGISTERED</p>
 * 	<p>GENERIC_ERROR</p>
 * 	<p>REJECTED</p>
 *    <p>DISALLOWED</p>
 * 
 * @since SmartDeviceLink 2.0
 * @see RegisterAppInterface
 */
public class ChangeRegistration extends RPCRequest {
	public static final String KEY_LANGUAGE = "language";
    public static final String KEY_HMI_DISPLAY_LANGUAGE = "hmiDisplayLanguage";
    public static final String KEY_APP_NAME = "appName";
    public static final String KEY_TTS_NAME = "ttsName";
    public static final String KEY_NGN_MEDIA_SCREEN_NAME = "ngnMediaScreenAppName";
    public static final String KEY_VR_SYNONYMS = "vrSynonyms";

	/**
	 * Constructs a new ChangeRegistration object
	 */
    public ChangeRegistration() {
        super(FunctionID.CHANGE_REGISTRATION.toString());
    }

	/**
	 * <p>Constructs a new ChangeRegistration object indicated by the Hashtable
	 * parameter</p>
	 * 
	 * 
	 * @param hash
	 *            The Hashtable to use
	 */
    public ChangeRegistration(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Constructs a new ChangeRegistration object
     * @param language a language value
     * @param hmiDisplayLanguage a Language value
     */
    public ChangeRegistration(@NonNull Language language, @NonNull Language hmiDisplayLanguage) {
        this();
        setLanguage(language);
        setHmiDisplayLanguage(hmiDisplayLanguage);
    }

	/**
	 * Sets language
	 * 
	 * @param language
	 *            a language value
	 */
    public void setLanguage(@NonNull Language language) {
        setParameters(KEY_LANGUAGE, language);
    }

	/**
	 * Gets language
	 * 
	 * @return Language -a Language value
	 */
    public Language getLanguage() {
        return (Language) getObject(Language.class, KEY_LANGUAGE);
    }

	/**
	 * Sets HMI display language
	 * 
	 * @param hmiDisplayLanguage
	 *            a Language value
	 */
    public void setHmiDisplayLanguage(@NonNull Language hmiDisplayLanguage) {
        setParameters(KEY_HMI_DISPLAY_LANGUAGE, hmiDisplayLanguage);
    }

	/**
	 * Gets HMI display language
	 * 
	 * @return Language -a Language value
	 */
    public Language getHmiDisplayLanguage() {
    	return (Language) getObject(Language.class, KEY_HMI_DISPLAY_LANGUAGE);
    }
    
    /**
     * Sets app name
     * 
     * @param appName App name to set
     */
    public void setAppName(String appName){
        setParameters(KEY_APP_NAME, appName);
    }
    
    /**
     * Gets app name
     * 
     * @return The app name
     */
    public String getAppName(){
        return getString(KEY_APP_NAME);
    }
    
    /**
     * Sets NGN media screen app name
     * 
     * @param ngnAppName The NGN app name
     */
    public void setNgnMediaScreenAppName(String ngnAppName){
        setParameters(KEY_NGN_MEDIA_SCREEN_NAME, ngnAppName);
    }
    
    /**
     * Gets NGN media screen app name
     * 
     * @return The NGN app name
     */
    public String getNgnMediaScreenAppName(){
        return getString(KEY_NGN_MEDIA_SCREEN_NAME);
    }
    
    /**
     * Sets the TTS name
     * 
     * @param ttsName The TTS name to set
     */
    public void setTtsName(List<TTSChunk> ttsName){
        setParameters(KEY_TTS_NAME, ttsName);
    }
    
    /**
     * Gets the TTS name
     * 
     * @return The TTS name
     */
    @SuppressWarnings("unchecked")
    public List<TTSChunk> getTtsName(){
        return (List<TTSChunk>) getObject(TTSChunk.class, KEY_TTS_NAME);
    }
    
    /**
     * Gets the List<String> representing the an array of 1-100 elements, each
     * element containing a voice-recognition synonym
     * 
     * @return List<String> -a List value representing the an array of
     *         1-100 elements, each element containing a voice-recognition
     *         synonym
     */    
    @SuppressWarnings("unchecked")
    public List<String> getVrSynonyms() {
        return (List<String>) getObject(String.class, KEY_VR_SYNONYMS);
    }
    
    /**
     * Sets a vrSynonyms representing the an array of 1-100 elements, each
     * element containing a voice-recognition synonym
     * 
     * @param vrSynonyms
     *            a List<String> value representing the an array of 1-100
     *            elements
     *            <p>
     *            <b>Notes: </b></p>
     *            <ul>
     *            <li>Each vr synonym is limited to 40 characters, and there can
     *            be 1-100 synonyms in array</li>
     *            <li>May not be the same (by case insensitive comparison) as
     *            the name or any synonym of any currently-registered
     *            application</li>
     *            </ul>
     */    
    public void setVrSynonyms(List<String> vrSynonyms) {
        setParameters(KEY_VR_SYNONYMS, vrSynonyms);
    }
}
