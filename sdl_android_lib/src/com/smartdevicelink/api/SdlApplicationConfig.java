package com.smartdevicelink.api;

import android.app.Service;

import com.smartdevicelink.api.file.SdlImage;
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

import java.util.EnumSet;
import java.util.Vector;

public class SdlApplicationConfig {

    // Required parameters
    private String mAppId;
    private String mAppName;
    private Boolean isMediaApp;
    private Class<? extends SdlActivity> mMainSdlActivity;
    private Class<? extends SdlApplication> mSdlApplication;

    // Optional parameters - initialized to default values
    private SdlProxyConfigurationResources mSdlProxyConfigurationResources;
    private Vector<TTSChunk> mTTSChunks;
    private String mShortAppName;
    private Vector<String> mVrSynonyms;
    private Language mLang;
    private Language mHmiLang;
    private EnumSet<Language> mSupportedLang;
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
        this.mSdlApplication = builder.sdlApplication;

        this.mSdlProxyConfigurationResources = builder.sdlProxyConfigurationResources;
        this.mTTSChunks = builder.ttsChunks;
        this.mShortAppName = builder.shortAppName;
        this.mVrSynonyms = builder.vrSynonyms;
        this.mLang = builder.language;
        this.mHmiLang = builder.hmiLanguage;
        this.mSupportedLang = builder.supportedLanguages;
        this.mSupportedLang.add(this.mLang);
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

    public Class<? extends SdlApplication> getSdlApplicationClass() {
        return mSdlApplication;
    }

    /**
     * Getter for the main SdlActivity that should be launched when the SdlApplication is started
     * by a first HMIFull from the module without a resume state saved.
     * @return {@link SdlActivity} that should be created as the entry point to the app.
     */
    public Class<? extends SdlActivity> getMainSdlActivityClass(){
        return mMainSdlActivity;
    }

    public Language getDefaultLanguage(){
        return mLang;
    }

    public boolean languageIsSupported(Language checkLanguage){
        return mSupportedLang.contains(checkLanguage);
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
        private Class<? extends SdlApplication> sdlApplication;
        private Class<? extends SdlActivity> mainSdlActivity;

        // Optional parameters - initialized to default values
        private SdlProxyConfigurationResources sdlProxyConfigurationResources = null;
        private Vector<TTSChunk> ttsChunks = null;
        private String shortAppName = null;
        private Vector<String> vrSynonyms = null;
        private Language language = Language.EN_US;
        private Language hmiLanguage = Language.EN_US;
        private EnumSet<Language> supportedLanguages = EnumSet.noneOf(Language.class);
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
         * Specifies a custom implementation of an SdlActivity.
         * @param sdlApplication Class to be used to instantiate the SdlAppliation.
         */
        public Builder setSdlApplicationClass(Class<? extends SdlApplication> sdlApplication) {
            this.sdlApplication = sdlApplication;
            return this;
        }

        /**
         * Setter for Proxy Configuration Resources
         * @param sdlProxyConfigurationResources {@link SdlProxyConfigurationResources} to be used.
         */
        public Builder setSdlProxyConfigurationResources(SdlProxyConfigurationResources sdlProxyConfigurationResources) {
            this.sdlProxyConfigurationResources = sdlProxyConfigurationResources;
            return this;
        }

        /**
         * Setter for TTS Chunks to be used by the app.
         * @param ttsChunks
         */
        public Builder setTtsChunks(Vector<TTSChunk> ttsChunks) {
            this.ttsChunks = ttsChunks;
            return this;
        }

        /**
         * Setter for the abbreviated app name.
         * @param shortAppName
         */
        public Builder setShortAppName(String shortAppName) {
            this.shortAppName = shortAppName;
            return this;
        }

        /**
         * Setter for voice recognition synonyms to be used for the app name.
         * @param vrSynonyms
         */
        public Builder setVrSynonyms(Vector<String> vrSynonyms) {
            this.vrSynonyms = vrSynonyms;
            return this;
        }

        /**
         * Setter for desired language.
         * @param language
         */
        public Builder setLanguage(Language language) {
            this.language = language;
            return this;
        }

        /**
         * Setter for desired display language.
         * @param hmiLanguage
         */
        public Builder setHmiLanguage(Language hmiLanguage) {
            this.hmiLanguage = hmiLanguage;
            return this;
        }

        /**
         * Setter for app type classification.
         * @param vrAppHMITypes
         */
        public Builder setVrAppHMITypes(Vector<AppHMIType> vrAppHMITypes) {
            this.vrAppHMITypes = vrAppHMITypes;
            return this;
        }

        /**
         * Setter for auto activate ID.
         * @param autoActivateID
         */
        public Builder setAutoActivateID(String autoActivateID) {
            this.autoActivateID = autoActivateID;
            return this;
        }

        /**
         * Setter for the desired transport type. Supports children of {@link BaseTransportConfig}
         * such as {@link BTTransportConfig} and {@link com.smartdevicelink.transport.TCPTransportConfig}.
         * @param transport
         */
        public Builder setTransport(BaseTransportConfig transport) {
            this.transport = transport;
            return this;
        }

        /**
         * Sets the resource ID from R.drawable to use for the app icon displayed on the HMI.
         * This will automatically be sent and set.
         * @param appIcon
         */
        public Builder setAppIcon(SdlImage appIcon) {
            this.appIcon = appIcon;
            return this;
        }

        public Builder setSupportedLanguages(EnumSet<Language> supportedLanguages){
            this.supportedLanguages = supportedLanguages;
            return this;
        }
    }
}
