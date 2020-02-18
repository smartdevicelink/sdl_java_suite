
    public Object getCapabilityForType(SystemCapabilityType type) {
        if (type == null) {
            return null;
        } else if (type.equals(SystemCapabilityType.NAVIGATION)) {
            return getObject(NavigationCapability.class, KEY_NAVIGATION_CAPABILITY);
        } else if (type.equals(SystemCapabilityType.PHONE_CALL)) {
            return getObject(PhoneCapability.class, KEY_PHONE_CAPABILITY);
        } else if (type.equals(SystemCapabilityType.VIDEO_STREAMING)) {
            return getObject(VideoStreamingCapability.class, KEY_VIDEO_STREAMING_CAPABILITY);
        } else if (type.equals(SystemCapabilityType.REMOTE_CONTROL)) {
            return getObject(RemoteControlCapabilities.class, KEY_REMOTE_CONTROL_CAPABILITY);
        } else if (type.equals(SystemCapabilityType.APP_SERVICES)) {
            return getObject(AppServicesCapabilities.class, KEY_APP_SERVICES_CAPABILITIES);
        } else if (type.equals(SystemCapabilityType.SEAT_LOCATION)) {
            return getObject(SeatLocationCapability.class, KEY_SEAT_LOCATION_CAPABILITY);
        } else if (type.equals(SystemCapabilityType.DISPLAYS)) {
            return getObject(DisplayCapability.class, KEY_DISPLAY_CAPABILITIES);
        } else {
            return null;
        }
    }

    public void setCapabilityForType(SystemCapabilityType type, Object capability) {
        if (type == null) {
            return;
        } else if (type.equals(SystemCapabilityType.NAVIGATION)) {
            setValue(KEY_NAVIGATION_CAPABILITY, capability);
        } else if (type.equals(SystemCapabilityType.PHONE_CALL)) {
            setValue(KEY_PHONE_CAPABILITY, capability);
        } else if (type.equals(SystemCapabilityType.VIDEO_STREAMING)) {
            setValue(KEY_VIDEO_STREAMING_CAPABILITY, capability);
        } else if (type.equals(SystemCapabilityType.REMOTE_CONTROL)) {
            setValue(KEY_REMOTE_CONTROL_CAPABILITY, capability);
        } else if (type.equals(SystemCapabilityType.APP_SERVICES)) {
            setValue(KEY_APP_SERVICES_CAPABILITIES, capability);
        } else if (type.equals(SystemCapabilityType.SEAT_LOCATION)) {
            setValue(KEY_SEAT_LOCATION_CAPABILITY, capability);
        } else if (type.equals(SystemCapabilityType.DISPLAYS)) {
            setValue(KEY_DISPLAY_CAPABILITIES, capability);
        } else {
            return;
        }
    }
