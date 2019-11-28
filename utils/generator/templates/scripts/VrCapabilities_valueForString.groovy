    public static VrCapabilities valueForString(String value) {
        if (value == null) {
            return null;
        }

        if (value.equalsIgnoreCase(TEXT.toString())) {
            return TEXT;
        }

        try {
            return valueOf(value);
        } catch (Exception e) {
            return null;
        }
    }