package com.smartdevicelink.api;

import android.app.Service;

import com.smartdevicelink.api.file.SdlImage;
import com.smartdevicelink.api.menu.SdlMenu;
import com.smartdevicelink.exception.SdlException;
import com.smartdevicelink.proxy.SdlProxyALM;
import com.smartdevicelink.proxy.SdlProxyBuilder;
import com.smartdevicelink.proxy.SdlProxyConfigurationResources;
import com.smartdevicelink.proxy.interfaces.IProxyListenerALM;
import com.smartdevicelink.proxy.rpc.SdlMsgVersion;
import com.smartdevicelink.proxy.rpc.TTSChunk;
import com.smartdevicelink.proxy.rpc.enums.AppHMIType;
import com.smartdevicelink.proxy.rpc.enums.Language;
import com.smartdevicelink.transport.BTTransportConfig;
import com.smartdevicelink.transport.BaseTransportConfig;

import java.util.Vector;

public class SdlApplicationConfig {

    // Required parameters
    private String mAppId;
    private String mAppName;
    private Boolean isMediaApp;
    private Class<? extends SdlActivity> mMainSdlActivity;

    // Optional parameters - initialized to default values
    private SdlMenu mSdlMenu;
    private SdlProxyConfigurationResources mSdlProxyConfigurationResources;
    private Vector<TTSChunk> mTTSChunks;
    private String mShortAppName;
    private Vector<String> mVrSynonyms;
    private Language mLang;
    private Language mHmiLang;
    private Vector<AppHMIType> mVrAppHMITypes;
    private String mAutoActivateID;
    private BaseTransportConfig mTransport;

    private SdlImage mAppIcon;

    // Handled internally
    private boolean isCallbackToUIThread = false;
    private boolean isPreRegister = false;
    private SdlMsgVersion mSdlMsgVersion = null;


    private SdlApplicationConfig(SdlApplicationConfig.Builder builder){
        this.mAppId = builder.appId;
        this.mAppName = builder.appName;
        this.isMediaApp = builder.isMediaApp;
        this.mMainSdlActivity = builder.mainSdlActivity;

        this.mSdlMenu = builder.sdlMenu;
        this.mSdlProxyConfigurationResources = builder.sdlProxyConfigurationResources;
        this.mTTSChunks = builder.ttsChunks;
        this.mShortAppName = builder.shortAppName;
        this.mVrSynonyms = builder.vrSynonyms;
        this.mLang = builder.language;
        this.mHmiLang = builder.hmiLanguage;
        this.mVrAppHMITypes = builder.vrAppHMITypes;
        this.mAutoActivateID = builder.autoActivateID;
        this.mTransport = builder.transport;

        this.mAppIcon = builder.appIcon;
    }

    /**
     * Getter for the AppId of the SdlApplication defined by this config.
     * @return The AppId as a {@link String}
     */
    public String getAppId(){
        return mAppId;
    }

    /**
     * Getter for the AppName of the SdlApplication defined by this config.
     * @return The AppName as a {@link String}
     */
    public String getAppName(){
        return mAppName;
    }

    public SdlImage getAppIcon(){
        return mAppIcon;
    }

    /**
     * Getter for the main SdlActivity that should be launched when the SdlApplication is started
     * by a first HMIFull from the module without a resume state saved.
     * @return {@link SdlActivity} that should be created as the entry point to the app.
     */
    public Class<? extends SdlActivity> getMainSdlActivity(){
        return mMainSdlActivity;
    }

    /**
     * Builds the SdlProxy to be used by the SdlApplication described by this config.
     * @param service {@link SdlApplication} that the proxy is constructed for.
     * @param resumeHash Hash for resuming the app as a String.
     * @param listener Implementation of {@link IProxyListenerALM} to receive proxy callbacks on.
     * @return {@link SdlProxyALM} to be used by the SdlApplication to connect to the head unit.
     */
    SdlProxyALM buildProxy(Service service, String resumeHash, IProxyListenerALM listener){
        SdlProxyBuilder.Builder builder = new SdlProxyBuilder.Builder(listener, mAppId, mAppName, isMediaApp);
        builder.setService(service)
                .setAppResumeDataHash(resumeHash)
                .setTransportType(mTransport)
                .setAutoActivateID(mAutoActivateID)
                .setCallbackToUIThread(isCallbackToUIThread)
                .setPreRegister(isPreRegister)
                .setSdlMessageVersion(mSdlMsgVersion)
                .setSdlProxyConfigurationResources(mSdlProxyConfigurationResources)
                .setShortAppName(mShortAppName)
                .setVrSynonyms(mVrSynonyms)
                .setVrAppHMITypes(mVrAppHMITypes)
                .setTtsName(mTTSChunks)
                .setLangDesired(mLang)
                .setHMILangDesired(mHmiLang);
        try {
            return builder.build();
        } catch (SdlException e) {
            // TODO: Add better logging.
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Setter for whether the proxy should callback on the UI thread.
     * @param isCallbackToUIThread true will cause the proxy callbacks to be posted to the Android
     *                             Application's main UI thread.
     */
    void setIsCallbackToUIThread(boolean isCallbackToUIThread) {
        this.isCallbackToUIThread = isCallbackToUIThread;
    }

    /**
     * Setter for pre-register.
     * @param isPreRegister
     */
    void setIsPreRegister(boolean isPreRegister) {
        this.isPreRegister = isPreRegister;
    }

    /**
     * Setter for preferred SDL message version.
     * @param sdlMsgVersion {@link SdlMsgVersion} preferred for the connection.
     */
    void setSdlMsgVersion(SdlMsgVersion sdlMsgVersion) {
        mSdlMsgVersion = sdlMsgVersion;
    }

    /**
     * This class provides methods to build an {@link SdlApplicationConfig} describing an
     * {@link SdlApplication} to use with {@link SdlManager#registerSdlApplication(SdlApplicationConfig)}
     * to setup a connection with an SDL enabled head unit.
     */
    public static class Builder{
        // Required parameters
        private String appId;
        private String appName;
        private boolean isMediaApp;
        private Class<? extends SdlActivity> mainSdlActivity;

        // Optional parameters - initialized to default values
        private SdlMenu sdlMenu = null;
        private SdlProxyConfigurationResources sdlProxyConfigurationResources = null;
        private Vector<TTSChunk> ttsChunks = null;
        private String shortAppName = null;
        private Vector<String> vrSynonyms = null;
        private Language language = Language.EN_US;
        private Language hmiLanguage = Language.EN_US;
        private Vector<AppHMIType> vrAppHMITypes = null;
        private String autoActivateID = null;
        private BaseTransportConfig transport = new BTTransportConfig();

        private SdlImage appIcon;

        public Builder(String appId, String appName, boolean isMedia,
                       Class<? extends SdlActivity> mainSdlActivity){
            this.appId = appId;
            this.appName = appName;
            this.isMediaApp = isMedia;
            this.mainSdlActivity = mainSdlActivity;
        }

        /**
         * Builds an instance of {@link SdlApplicationConfig} to be registered with {@link SdlManager#registerSdlApplication(SdlApplicationConfig)}.
         * @return {@link SdlApplicationConfig} describing the desired {@link SdlApplication}.
         */
        public SdlApplicationConfig build(){
            return new SdlApplicationConfig(this);
        }

        /**
         * Setter for top level SdlMenu.
         * @param sdlMenu
         */
        public void setSdlMenu(SdlMenu sdlMenu) {
            this.sdlMenu = sdlMenu;
        }

        /**
         * Setter for Proxy Configuration Resources
         * @param sdlProxyConfigurationResources {@link SdlProxyConfigurationResources} to be used.
         */
        public void setSdlProxyConfigurationResources(SdlProxyConfigurationResources sdlProxyConfigurationResources) {
            this.sdlProxyConfigurationResources = sdlProxyConfigurationResources;
        }

        /**
         * Setter for TTS Chunks to be used by the app.
         * @param ttsChunks
         */
        public void setTtsChunks(Vector<TTSChunk> ttsChunks) {
            this.ttsChunks = ttsChunks;
        }

        /**
         * Setter for the abbreviated app name.
         * @param shortAppName
         */
        public void setShortAppName(String shortAppName) {
            this.shortAppName = shortAppName;
        }

        /**
         * Setter for voice recognition synonyms to be used for the app name.
         * @param vrSynonyms
         */
        public void setVrSynonyms(Vector<String> vrSynonyms) {
            this.vrSynonyms = vrSynonyms;
        }

        /**
         * Setter for desired language.
         * @param language
         */
        public void setLanguage(Language language) {
            this.language = language;
        }

        /**
         * Setter for desired display language.
         * @param hmiLanguage
         */
        public void setHmiLanguage(Language hmiLanguage) {
            this.hmiLanguage = hmiLanguage;
        }

        /**
         * Setter for app type classification.
         * @param vrAppHMITypes
         */
        public void setVrAppHMITypes(Vector<AppHMIType> vrAppHMITypes) {
            this.vrAppHMITypes = vrAppHMITypes;
        }

        /**
         * Setter for auto activate ID.
         * @param autoActivateID
         */
        public void setAutoActivateID(String autoActivateID) {
            this.autoActivateID = autoActivateID;
        }

        /**
         * Setter for the desired transport type. Supports children of {@link BaseTransportConfig}
         * such as {@link BTTransportConfig} and {@link com.smartdevicelink.transport.TCPTransportConfig}.
         * @param transport
         */
        public void setTransport(BaseTransportConfig transport) {
            this.transport = transport;
        }

        /**
         * Sets the resource ID from R.drawable to use for the app icon displayed on the HMI.
         * This will automatically be sent and set.
         * @param appIcon
         */
        public void setAppIcon(SdlImage appIcon) {
            this.appIcon = appIcon;
        }
    }
}
