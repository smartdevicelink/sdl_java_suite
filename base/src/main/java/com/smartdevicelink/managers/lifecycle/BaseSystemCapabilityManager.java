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

import com.smartdevicelink.managers.ISdl;
import com.smartdevicelink.managers.ManagerUtility;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.AppServiceCapability;
import com.smartdevicelink.proxy.rpc.AppServicesCapabilities;
import com.smartdevicelink.proxy.rpc.ButtonCapabilities;
import com.smartdevicelink.proxy.rpc.DisplayCapabilities;
import com.smartdevicelink.proxy.rpc.DisplayCapability;
import com.smartdevicelink.proxy.rpc.GetSystemCapability;
import com.smartdevicelink.proxy.rpc.GetSystemCapabilityResponse;
import com.smartdevicelink.proxy.rpc.HMICapabilities;
import com.smartdevicelink.proxy.rpc.OnHMIStatus;
import com.smartdevicelink.proxy.rpc.OnSystemCapabilityUpdated;
import com.smartdevicelink.proxy.rpc.RegisterAppInterfaceResponse;
import com.smartdevicelink.proxy.rpc.SdlMsgVersion;
import com.smartdevicelink.proxy.rpc.SetDisplayLayoutResponse;
import com.smartdevicelink.proxy.rpc.SoftButtonCapabilities;
import com.smartdevicelink.proxy.rpc.SystemCapability;
import com.smartdevicelink.proxy.rpc.WindowCapability;
import com.smartdevicelink.proxy.rpc.WindowTypeCapabilities;
import com.smartdevicelink.proxy.rpc.enums.DisplayType;
import com.smartdevicelink.proxy.rpc.enums.HMILevel;
import com.smartdevicelink.proxy.rpc.enums.ImageType;
import com.smartdevicelink.proxy.rpc.enums.MediaClockFormat;
import com.smartdevicelink.proxy.rpc.enums.PredefinedWindows;
import com.smartdevicelink.proxy.rpc.enums.SystemCapabilityType;
import com.smartdevicelink.proxy.rpc.enums.WindowType;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCListener;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCResponseListener;
import com.smartdevicelink.util.CorrelationIdGenerator;
import com.smartdevicelink.util.DebugTool;
import com.smartdevicelink.util.Version;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.CopyOnWriteArrayList;

abstract class BaseSystemCapabilityManager {
    private static final String TAG = "SystemCapabilityManager";
    private final HashMap<SystemCapabilityType, Object> cachedSystemCapabilities;
    private final HashMap<SystemCapabilityType, Boolean> systemCapabilitiesSubscriptionStatus;
    private final HashMap<SystemCapabilityType, CopyOnWriteArrayList<OnSystemCapabilityListener>> onSystemCapabilityListeners;
    private final Object LISTENER_LOCK;
    private final ISdl callback;
    private boolean shouldConvertDeprecatedDisplayCapabilities;
    private HMILevel currentHMILevel;

    BaseSystemCapabilityManager(ISdl callback) {
        this.callback = callback;
        this.LISTENER_LOCK = new Object();
        this.onSystemCapabilityListeners = new HashMap<>();
        this.cachedSystemCapabilities = new HashMap<>();
        this.systemCapabilitiesSubscriptionStatus = new HashMap<>();
        this.systemCapabilitiesSubscriptionStatus.put(SystemCapabilityType.DISPLAYS, true);
        this.shouldConvertDeprecatedDisplayCapabilities = true;
        this.currentHMILevel = HMILevel.HMI_NONE;

        setupRpcListeners();
    }

    private List<DisplayCapability> createDisplayCapabilityList(RegisterAppInterfaceResponse rpc) {
        return createDisplayCapabilityList(rpc.getDisplayCapabilities(), rpc.getButtonCapabilities(), rpc.getSoftButtonCapabilities());
    }

    private List<DisplayCapability> createDisplayCapabilityList(SetDisplayLayoutResponse rpc) {
        return createDisplayCapabilityList(rpc.getDisplayCapabilities(), rpc.getButtonCapabilities(), rpc.getSoftButtonCapabilities());
    }

    private List<DisplayCapability> createDisplayCapabilityList(DisplayCapabilities display, List<ButtonCapabilities> button, List<SoftButtonCapabilities> softButton) {
        // Based on deprecated Display capabilities we don't know if widgets are supported,
        // The Default MAIN window is the only window we know is supported
        WindowTypeCapabilities windowTypeCapabilities = new WindowTypeCapabilities(WindowType.MAIN, 1);

        DisplayCapability displayCapability = new DisplayCapability();
        if (display != null) {
            if (display.getDisplayName() != null) {
                displayCapability.setDisplayName(display.getDisplayName());
            } else if (display.getDisplayType() != null) {
                displayCapability.setDisplayName(display.getDisplayType().toString());
            }
        }
        displayCapability.setWindowTypeSupported(Collections.singletonList(windowTypeCapabilities));

        // Create a window capability object for the default MAIN window
        WindowCapability defaultWindowCapability = new WindowCapability();
        defaultWindowCapability.setWindowID(PredefinedWindows.DEFAULT_WINDOW.getValue());
        defaultWindowCapability.setButtonCapabilities(button);
        defaultWindowCapability.setSoftButtonCapabilities(softButton);

        // return if display capabilities don't exist.
        if (display == null) {
            defaultWindowCapability.setTextFields(ManagerUtility.WindowCapabilityUtility.getAllTextFields());
            defaultWindowCapability.setImageFields(ManagerUtility.WindowCapabilityUtility.getAllImageFields());
            displayCapability.setWindowCapabilities(Collections.singletonList(defaultWindowCapability));
            return Collections.singletonList(displayCapability);
        }

        // HAX: Issue #1705, Ford Sync bug returning incorrect template name for "NON-MEDIA" (https://github.com/smartdevicelink/sdl_java_suite/issues/1705).
        List<String> templatesAvailable = display.getTemplatesAvailable();
        if (templatesAvailable != null) {
            for (int i = 0; i < templatesAvailable.size(); i++) {
                if (templatesAvailable.get(i).equals("NON_MEDIA")) {
                    templatesAvailable.set(i, "NON-MEDIA");
                    break;
                }
            }
        }

        // copy all available display capabilities
        defaultWindowCapability.setTemplatesAvailable(templatesAvailable);
        defaultWindowCapability.setNumCustomPresetsAvailable(display.getNumCustomPresetsAvailable());
        defaultWindowCapability.setTextFields(display.getTextFields());
        defaultWindowCapability.setImageFields(display.getImageFields());
        ArrayList<ImageType> imageTypeSupported = new ArrayList<>();
        imageTypeSupported.add(ImageType.STATIC); // static images expected to always work on any head unit
        if (display.getGraphicSupported()) {
            imageTypeSupported.add(ImageType.DYNAMIC);
        }
        defaultWindowCapability.setImageTypeSupported(imageTypeSupported);

        displayCapability.setWindowCapabilities(Collections.singletonList(defaultWindowCapability));
        return Collections.singletonList(displayCapability);
    }

    private DisplayCapabilities createDeprecatedDisplayCapabilities(String displayName, WindowCapability defaultMainWindow) {
        DisplayCapabilities convertedCapabilities = new DisplayCapabilities();
        convertedCapabilities.setDisplayType(DisplayType.SDL_GENERIC); //deprecated but it is mandatory...
        convertedCapabilities.setDisplayName(displayName);
        convertedCapabilities.setTextFields(defaultMainWindow.getTextFields());
        convertedCapabilities.setImageFields(defaultMainWindow.getImageFields());
        convertedCapabilities.setTemplatesAvailable(defaultMainWindow.getTemplatesAvailable());
        convertedCapabilities.setNumCustomPresetsAvailable(defaultMainWindow.getNumCustomPresetsAvailable());
        convertedCapabilities.setMediaClockFormats(new ArrayList<MediaClockFormat>()); // mandatory field but allows empty array
        // if there are imageTypes in the response, we must assume graphics are supported
        convertedCapabilities.setGraphicSupported(defaultMainWindow.getImageTypeSupported() != null && defaultMainWindow.getImageTypeSupported().size() > 0);

        return convertedCapabilities;
    }

    private void updateDeprecatedDisplayCapabilities() {
        WindowCapability defaultMainWindowCapabilities = getDefaultMainWindowCapability();
        List<DisplayCapability> displayCapabilityList = convertToList(getCapability(SystemCapabilityType.DISPLAYS, null, false), DisplayCapability.class);

        if (defaultMainWindowCapabilities == null || displayCapabilityList == null || displayCapabilityList.size() == 0) {
            return;
        }

        // cover the deprecated capabilities for backward compatibility
        setCapability(SystemCapabilityType.DISPLAY, createDeprecatedDisplayCapabilities(displayCapabilityList.get(0).getDisplayName(), defaultMainWindowCapabilities));
        setCapability(SystemCapabilityType.BUTTON, defaultMainWindowCapabilities.getButtonCapabilities());
        setCapability(SystemCapabilityType.SOFTBUTTON, defaultMainWindowCapabilities.getSoftButtonCapabilities());
    }

    private void updateCachedDisplayCapabilityList(List<DisplayCapability> newCapabilities) {
        if (newCapabilities == null || newCapabilities.size() == 0) {
            DebugTool.logWarning(TAG, "Received invalid display capability list");
            return;
        }

        List<DisplayCapability> oldCapabilities = convertToList(getCapability(SystemCapabilityType.DISPLAYS, null, false), DisplayCapability.class);

        if (oldCapabilities == null || oldCapabilities.size() == 0) {
            setCapability(SystemCapabilityType.DISPLAYS, newCapabilities);
            updateDeprecatedDisplayCapabilities();
            return;
        }

        DisplayCapability oldDefaultDisplayCapabilities = oldCapabilities.get(0);
        ArrayList<WindowCapability> copyWindowCapabilities = new ArrayList<>(oldDefaultDisplayCapabilities.getWindowCapabilities());

        DisplayCapability newDefaultDisplayCapabilities = newCapabilities.get(0);
        List<WindowCapability> newWindowCapabilities = newDefaultDisplayCapabilities.getWindowCapabilities();

        if (newWindowCapabilities != null && !newWindowCapabilities.isEmpty()) {
            for (WindowCapability newWindow : newWindowCapabilities) {
                ListIterator<WindowCapability> iterator = copyWindowCapabilities.listIterator();
                boolean oldFound = false;
                while (iterator.hasNext()) {
                    WindowCapability oldWindow = iterator.next();
                    int newWindowID = newWindow.getWindowID() != null ? newWindow.getWindowID() : PredefinedWindows.DEFAULT_WINDOW.getValue();
                    int oldWindowID = oldWindow.getWindowID() != null ? oldWindow.getWindowID() : PredefinedWindows.DEFAULT_WINDOW.getValue();
                    if (newWindowID == oldWindowID) {
                        iterator.set(newWindow); // replace the old window caps with new ones
                        oldFound = true;
                        break;
                    }
                }

                if (!oldFound) {
                    copyWindowCapabilities.add(newWindow); // this is a new unknown window
                }
            }
        }

        // replace the window capabilities array with the merged one.
        newDefaultDisplayCapabilities.setWindowCapabilities(copyWindowCapabilities);
        setCapability(SystemCapabilityType.DISPLAYS, Collections.singletonList(newDefaultDisplayCapabilities));
        updateDeprecatedDisplayCapabilities();
    }


    public WindowCapability getWindowCapability(int windowID) {
        List<DisplayCapability> capabilities = convertToList(getCapability(SystemCapabilityType.DISPLAYS, null, false), DisplayCapability.class);
        if (capabilities == null || capabilities.size() == 0) {
            return null;
        }
        DisplayCapability display = capabilities.get(0);
        for (WindowCapability windowCapability : display.getWindowCapabilities()) {
            int currentWindowID = windowCapability.getWindowID() != null ? windowCapability.getWindowID() : PredefinedWindows.DEFAULT_WINDOW.getValue();
            if (currentWindowID == windowID) {
                return windowCapability;
            }
        }
        return null;
    }

    public WindowCapability getDefaultMainWindowCapability() {
        return getWindowCapability(PredefinedWindows.DEFAULT_WINDOW.getValue());
    }

    void parseRAIResponse(RegisterAppInterfaceResponse response) {
        if (response != null && response.getSuccess()) {
            this.shouldConvertDeprecatedDisplayCapabilities = true; // reset the flag
            setCapability(SystemCapabilityType.DISPLAYS, createDisplayCapabilityList(response));
            setCapability(SystemCapabilityType.HMI, response.getHmiCapabilities());
            setCapability(SystemCapabilityType.DISPLAY, response.getDisplayCapabilities());
            setCapability(SystemCapabilityType.AUDIO_PASSTHROUGH, response.getAudioPassThruCapabilities());
            setCapability(SystemCapabilityType.PCM_STREAMING, response.getPcmStreamingCapabilities());
            setCapability(SystemCapabilityType.BUTTON, response.getButtonCapabilities());
            setCapability(SystemCapabilityType.HMI_ZONE, response.getHmiZoneCapabilities());
            setCapability(SystemCapabilityType.PRESET_BANK, response.getPresetBankCapabilities());
            setCapability(SystemCapabilityType.SOFTBUTTON, response.getSoftButtonCapabilities());
            setCapability(SystemCapabilityType.SPEECH, response.getSpeechCapabilities());
            setCapability(SystemCapabilityType.VOICE_RECOGNITION, response.getVrCapabilities());
            setCapability(SystemCapabilityType.PRERECORDED_SPEECH, response.getPrerecordedSpeech());
        }
    }

    private void setupRpcListeners() {
        OnRPCListener rpcListener = new OnRPCListener() {
            @Override
            public void onReceived(RPCMessage message) {
                if (message != null) {
                    if (RPCMessage.KEY_RESPONSE.equals(message.getMessageType())) {
                        switch (message.getFunctionID()) {
                            case SET_DISPLAY_LAYOUT:
                                SetDisplayLayoutResponse response = (SetDisplayLayoutResponse) message;
                                // If a setDisplayLayout fails, Capabilities did not change
                                if (!response.getSuccess()) {
                                    return;
                                }
                                setCapability(SystemCapabilityType.DISPLAY, response.getDisplayCapabilities());
                                setCapability(SystemCapabilityType.BUTTON, response.getButtonCapabilities());
                                setCapability(SystemCapabilityType.PRESET_BANK, response.getPresetBankCapabilities());
                                setCapability(SystemCapabilityType.SOFTBUTTON, response.getSoftButtonCapabilities());
                                if (shouldConvertDeprecatedDisplayCapabilities) {
                                    setCapability(SystemCapabilityType.DISPLAYS, createDisplayCapabilityList(response));
                                }
                                break;
                            case GET_SYSTEM_CAPABILITY:
                                GetSystemCapabilityResponse systemCapabilityResponse = (GetSystemCapabilityResponse) message;
                                SystemCapability systemCapability = systemCapabilityResponse.getSystemCapability();
                                if (systemCapabilityResponse.getSuccess() && SystemCapabilityType.DISPLAYS.equals(systemCapability.getSystemCapabilityType())) {
                                    shouldConvertDeprecatedDisplayCapabilities = false; // Successfully got DISPLAYS data. No conversion needed anymore
                                    List<DisplayCapability> newCapabilities = (List<DisplayCapability>) systemCapability.getCapabilityForType(SystemCapabilityType.DISPLAYS);
                                    updateCachedDisplayCapabilityList(newCapabilities);
                                }
                                break;
                        }
                    } else if (RPCMessage.KEY_NOTIFICATION.equals(message.getMessageType())) {
                        switch (message.getFunctionID()) {
                            case ON_HMI_STATUS:
                                OnHMIStatus onHMIStatus = (OnHMIStatus) message;
                                if (onHMIStatus.getWindowID() != null && onHMIStatus.getWindowID() != PredefinedWindows.DEFAULT_WINDOW.getValue()) {
                                    return;
                                }
                                currentHMILevel = onHMIStatus.getHmiLevel();
                                break;
                            case ON_SYSTEM_CAPABILITY_UPDATED:
                                OnSystemCapabilityUpdated onSystemCapabilityUpdated = (OnSystemCapabilityUpdated) message;
                                if (onSystemCapabilityUpdated.getSystemCapability() != null) {
                                    SystemCapability systemCapability = onSystemCapabilityUpdated.getSystemCapability();
                                    SystemCapabilityType systemCapabilityType = systemCapability.getSystemCapabilityType();
                                    Object capability = systemCapability.getCapabilityForType(systemCapabilityType);
                                    if (cachedSystemCapabilities.containsKey(systemCapabilityType)) { //The capability already exists
                                        switch (systemCapabilityType) {
                                            case APP_SERVICES:
                                                // App services only updates what was changed so we need
                                                // to update the capability rather than override it
                                                AppServicesCapabilities appServicesCapabilities = (AppServicesCapabilities) capability;
                                                if (capability != null) {
                                                    List<AppServiceCapability> appServicesCapabilitiesList = appServicesCapabilities.getAppServices();
                                                    AppServicesCapabilities cachedAppServicesCapabilities = (AppServicesCapabilities) cachedSystemCapabilities.get(systemCapabilityType);
                                                    //Update the cached app services
                                                    if (cachedAppServicesCapabilities != null) {
                                                        cachedAppServicesCapabilities.updateAppServices(appServicesCapabilitiesList);
                                                    }
                                                    //Set the new capability object to the updated cached capabilities
                                                    capability = cachedAppServicesCapabilities;
                                                }
                                                break;
                                            case DISPLAYS:
                                                shouldConvertDeprecatedDisplayCapabilities = false; // Successfully got DISPLAYS data. No conversion needed anymore
                                                // this notification can return only affected windows (hence not all windows)
                                                List<DisplayCapability> newCapabilities = (List<DisplayCapability>) capability;
                                                updateCachedDisplayCapabilityList(newCapabilities);
                                        }
                                    }
                                    if (capability != null) {
                                        setCapability(systemCapabilityType, capability);
                                    }
                                }
                        }
                    }
                }
            }
        };

        if (callback != null) {
            callback.addOnRPCListener(FunctionID.GET_SYSTEM_CAPABILITY, rpcListener);
            callback.addOnRPCListener(FunctionID.SET_DISPLAY_LAYOUT, rpcListener);
            callback.addOnRPCListener(FunctionID.ON_SYSTEM_CAPABILITY_UPDATED, rpcListener);
            callback.addOnRPCListener(FunctionID.ON_HMI_STATUS, rpcListener);
        }
    }

    /**
     * Sets a capability in the cached map. This should only be done when an RPC is received and contains updates to the capability
     * that is being cached in the SystemCapabilityManager.
     *
     * @param systemCapabilityType the system capability type that will be set
     * @param capability           the value of the capability that will be set
     */
    synchronized void setCapability(SystemCapabilityType systemCapabilityType, Object capability) {
        cachedSystemCapabilities.put(systemCapabilityType, capability);
        notifyListeners(systemCapabilityType, capability);
    }

    /**
     * Notifies listeners in the list about the new retrieved capability
     *
     * @param systemCapabilityType the system capability type that was retrieved
     * @param capability           the system capability value that was retrieved
     */
    private void notifyListeners(SystemCapabilityType systemCapabilityType, Object capability) {
        synchronized (LISTENER_LOCK) {
            CopyOnWriteArrayList<OnSystemCapabilityListener> listeners = onSystemCapabilityListeners.get(systemCapabilityType);
            if (listeners != null && listeners.size() > 0) {
                for (OnSystemCapabilityListener listener : listeners) {
                    listener.onCapabilityRetrieved(capability);
                }
            }
        }
    }

    /**
     * Ability to see if the connected module supports the given capability. Useful to check before
     * attempting to query for capabilities that require asynchronous calls to initialize.
     *
     * @param type the SystemCapabilityType that is to be checked
     * @return if that capability is supported with the current, connected module
     */
    public boolean isCapabilitySupported(SystemCapabilityType type) {
        if (cachedSystemCapabilities.get(type) != null) {
            //The capability exists in the map and is not null
            return true;
        } else if (cachedSystemCapabilities.containsKey(SystemCapabilityType.HMI)) {
            HMICapabilities hmiCapabilities = ((HMICapabilities) cachedSystemCapabilities.get(SystemCapabilityType.HMI));
            Version rpcVersion = null;
            if (callback != null) {
                SdlMsgVersion version = callback.getSdlMsgVersion();
                if (version != null) {
                    rpcVersion = new Version(version.getMajorVersion(), version.getMinorVersion(), version.getPatchVersion());
                }
            }
            if (hmiCapabilities != null) {
                switch (type) {
                    case NAVIGATION:
                        return hmiCapabilities.isNavigationAvailable();
                    case PHONE_CALL:
                        return hmiCapabilities.isPhoneCallAvailable();
                    case VIDEO_STREAMING:
                        if (rpcVersion != null) {
                            if (rpcVersion.isBetween(new Version(3, 0, 0), new Version(4, 4, 0)) >= 0) {
                                //This was before the system capability feature was added so check if
                                // graphics are supported instead
                                DisplayCapabilities displayCapabilities = (DisplayCapabilities) getCapability(SystemCapabilityType.DISPLAY, null, false);
                                if (displayCapabilities != null) {
                                    return displayCapabilities.getGraphicSupported() != null && displayCapabilities.getGraphicSupported();
                                }
                            }
                        }
                        return hmiCapabilities.isVideoStreamingAvailable();
                    case REMOTE_CONTROL:
                        return hmiCapabilities.isRemoteControlAvailable();
                    case APP_SERVICES:
                        if (rpcVersion != null) {
                            if (rpcVersion.getMajor() == 5 && rpcVersion.getMinor() == 1) {
                                //This is a corner case that the param was not available in 5.1.0, but
                                //the app services feature was available.
                                return true;
                            }
                        }
                        return hmiCapabilities.isAppServicesAvailable();
                    case DISPLAYS:
                        return hmiCapabilities.isDisplaysCapabilityAvailable();
                    case SEAT_LOCATION:
                        return hmiCapabilities.isSeatLocationAvailable();
                    case DRIVER_DISTRACTION:
                        return hmiCapabilities.isDriverDistractionAvailable();
                    default:
                        return false;
                }
            }
        }
        return false;
    }

    /**
     * Checks is subscriptions are available on the connected head unit.
     *
     * @return True if subscriptions are supported. False if not.
     */
    public boolean supportsSubscriptions() {
        if (callback != null && callback.getSdlMsgVersion() != null) {
            Version onSystemCapabilityNotificationRPCVersion = new Version(5, 1, 0);
            Version headUnitRPCVersion = new Version(callback.getSdlMsgVersion());
            return headUnitRPCVersion.isNewerThan(onSystemCapabilityNotificationRPCVersion) >= 0;
        }
        return false;
    }

    /**
     * Checks if the supplied capability type is currently subscribed for or not
     *
     * @param systemCapabilityType type of capability desired
     * @return true if subscribed and false if not
     */
    private boolean isSubscribedToSystemCapability(SystemCapabilityType systemCapabilityType) {
        return Boolean.TRUE.equals(systemCapabilitiesSubscriptionStatus.get(systemCapabilityType));
    }

    /**
     * Gets the capability object that corresponds to the supplied capability type by returning the currently cached value immediately (or null) as well as calling the listener immediately with the cached value, if available. If not available, the listener will retrieve a new value and return that when the head unit responds.
     * <strong>If capability is not cached, the method will return null and trigger the supplied listener when the capability becomes available</strong>
     *
     * @param systemCapabilityType type of capability desired
     * @param scListener           callback to execute upon retrieving capability
     * @param subscribe            flag to subscribe to updates of the supplied capability type. True means subscribe; false means cancel subscription; null means don't change current subscription status.
     * @param forceUpdate          flag to force getting a new fresh copy of the capability from the head unit even if it is cached
     * @return desired capability if it is cached in the manager, otherwise returns a null object and works in the background to retrieve the capability for the next call
     */
    private Object getCapabilityPrivate(final SystemCapabilityType systemCapabilityType, final OnSystemCapabilityListener scListener, final Boolean subscribe, final boolean forceUpdate) {
        Object cachedCapability = cachedSystemCapabilities.get(systemCapabilityType);

        // No need to force update if the app is subscribed to that type because updated values will be received via notifications anyway
        boolean shouldForceUpdate = forceUpdate && !isSubscribedToSystemCapability(systemCapabilityType);
        boolean shouldUpdateSystemCapabilitySubscription = (subscribe != null) && (subscribe != isSubscribedToSystemCapability(systemCapabilityType)) && supportsSubscriptions();
        boolean shouldSendGetCapabilityRequest = shouldForceUpdate || (cachedCapability == null) || shouldUpdateSystemCapabilitySubscription;
        boolean shouldCallListenerWithCachedValue = (cachedCapability != null) && (scListener != null) && !shouldSendGetCapabilityRequest;

        if (shouldCallListenerWithCachedValue) {
            scListener.onCapabilityRetrieved(cachedCapability);
        }

        if (shouldSendGetCapabilityRequest) {
            retrieveCapability(systemCapabilityType, scListener, subscribe);
        }

        return cachedCapability;
    }

    /**
     * Gets the capability object that corresponds to the supplied capability type by returning the currently cached value immediately (or null) as well as calling the listener immediately with the cached value, if available. If not available, the listener will retrieve a new value and return that when the head unit responds.
     * <strong>If capability is not cached, the method will return null and trigger the supplied listener when the capability becomes available</strong>
     *
     * @param systemCapabilityType type of capability desired
     * @param scListener           callback to execute upon retrieving capability
     * @param forceUpdate          flag to force getting a new fresh copy of the capability from the head unit even if it is cached
     * @return desired capability if it is cached in the manager, otherwise returns a null object
     */
    public Object getCapability(final SystemCapabilityType systemCapabilityType, final OnSystemCapabilityListener scListener, final boolean forceUpdate) {
        return getCapabilityPrivate(systemCapabilityType, scListener, null, forceUpdate);
    }

    /**
     * Adds a listener to be called whenever a new capability is retrieved. This method automatically subscribes to the supplied capability type and may call the listener multiple times if there are future updates, unlike getCapability() methods, which only call the listener one time.
     *
     * @param systemCapabilityType Type of capability desired
     * @param listener             callback to execute upon retrieving capability
     */
    public void addOnSystemCapabilityListener(final SystemCapabilityType systemCapabilityType, final OnSystemCapabilityListener listener) {
        synchronized (LISTENER_LOCK) {
            if (systemCapabilityType != null && listener != null) {
                if (onSystemCapabilityListeners.get(systemCapabilityType) == null) {
                    onSystemCapabilityListeners.put(systemCapabilityType, new CopyOnWriteArrayList<OnSystemCapabilityListener>());
                }
                onSystemCapabilityListeners.get(systemCapabilityType).add(listener);
            }
        }
        getCapabilityPrivate(systemCapabilityType, listener, true, false);
    }

    /**
     * Removes an OnSystemCapabilityListener that was previously added
     *
     * @param systemCapabilityType Type of capability
     * @param listener             the listener that should be removed
     * @return boolean that represents whether the removal was successful or not
     */
    public boolean removeOnSystemCapabilityListener(final SystemCapabilityType systemCapabilityType, final OnSystemCapabilityListener listener) {
        boolean success = false;
        synchronized (LISTENER_LOCK) {
            if (onSystemCapabilityListeners != null
                    && systemCapabilityType != null
                    && listener != null
                    && onSystemCapabilityListeners.get(systemCapabilityType) != null) {
                success = onSystemCapabilityListeners.get(systemCapabilityType).remove(listener);
                // If the last listener for the supplied capability type is removed, unsubscribe from the capability type
                if (success && onSystemCapabilityListeners.get(systemCapabilityType).isEmpty() && isSubscribedToSystemCapability(systemCapabilityType) && systemCapabilityType != SystemCapabilityType.DISPLAYS) {
                    retrieveCapability(systemCapabilityType, null, false);
                }
            }
        }
        return success;
    }

    /**
     * Sends a GetSystemCapability request for the supplied SystemCapabilityType and call the listener's callback if the systemCapabilityType is queryable
     *
     * @param systemCapabilityType Type of capability desired
     * @param subscribe            flag to subscribe to updates of the supplied capability type. True means subscribe; false means cancel subscription; null means don't change current subscription status.
     */
    private void retrieveCapability(final SystemCapabilityType systemCapabilityType, final OnSystemCapabilityListener scListener, final Boolean subscribe) {
        if (currentHMILevel != null && currentHMILevel.equals(HMILevel.HMI_NONE)) {
            String message = String.format("Attempted to update type: %s in HMI level NONE, which is not allowed. " +
                    "Please wait until you are in HMI BACKGROUND, LIMITED, or FULL before attempting to update any SystemCapabilityType", systemCapabilityType);
            DebugTool.logError(TAG, message);
            if (scListener != null) {
                scListener.onError(message);
            }
            return;
        }
        if (!systemCapabilityType.isQueryable() || systemCapabilityType == SystemCapabilityType.DISPLAYS) {
            String message = "This systemCapabilityType cannot be queried for";
            DebugTool.logError(TAG, message);
            if (scListener != null) {
                scListener.onError(message);
            }
            return;
        }
        final GetSystemCapability request = new GetSystemCapability();
        request.setSystemCapabilityType(systemCapabilityType);

		/*
		The subscription flag in the request should be set based on multiple variables:
		- if subscribe is null (no change), willSubscribe = current subscription status, or false if the HU does not support subscriptions
		- if subscribe is false, then willSubscribe = false
		- if subscribe is true and the HU supports subscriptions, then willSubscribe = true
		*/
        boolean shouldSubscribe = (subscribe != null) ? subscribe : isSubscribedToSystemCapability(systemCapabilityType);
        final boolean willSubscribe = shouldSubscribe && supportsSubscriptions();
        request.setSubscribe(willSubscribe);
        request.setOnRPCResponseListener(new OnRPCResponseListener() {
            @Override
            public void onResponse(int correlationId, RPCResponse response) {
                if (response.getSuccess()) {
                    Object retrievedCapability = ((GetSystemCapabilityResponse) response).getSystemCapability().getCapabilityForType(systemCapabilityType);
                    setCapability(systemCapabilityType, retrievedCapability);
                    // If the listener is not included in the onSystemCapabilityListeners map, then notify it
                    // This will be triggered if we are just getting capability without adding a listener to the map
                    if (scListener != null) {
                        synchronized (LISTENER_LOCK) {
                            CopyOnWriteArrayList<OnSystemCapabilityListener> notifiedListeners = onSystemCapabilityListeners.get(systemCapabilityType);
                            boolean listenerAlreadyNotified = (notifiedListeners != null) && notifiedListeners.contains(scListener);
                            if (!listenerAlreadyNotified) {
                                scListener.onCapabilityRetrieved(retrievedCapability);
                            }
                        }
                    }
                    if (supportsSubscriptions()) {
                        systemCapabilitiesSubscriptionStatus.put(systemCapabilityType, willSubscribe);
                    }
                } else {
                    if (scListener != null) {
                        scListener.onError(response.getInfo());
                    }
                }
            }
        });
        request.setCorrelationID(CorrelationIdGenerator.generateId());

        if (callback != null) {
            callback.sendRPC(request);
        }
    }

    /**
     * Converts a capability object into a list.
     *
     * @param object    the capability that needs to be converted
     * @param classType The class type of that should be contained in the list
     * @return a List of capabilities if object is instance of List, otherwise it will return null.
     */
    @SuppressWarnings({"unchecked"})
    public static <T> List<T> convertToList(Object object, Class<T> classType) {
        if (classType != null && object != null && object instanceof List) {
            List list = (List) object;
            if (!list.isEmpty()) {
                if (classType.isInstance(list.get(0))) {
                    return (List<T>) object;
                } else {
                    //The list is not of the correct list type
                    return null;
                }
            } else {
                //We return a new list of type T instead of null because while we don't know if
                //the original list was of type T we want to ensure that we don't throw a cast class exception
                //but still
                return new ArrayList<>();
            }
        } else {
            return null;
        }
    }
}
