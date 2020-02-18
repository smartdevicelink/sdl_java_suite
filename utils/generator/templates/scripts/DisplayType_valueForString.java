    public static DisplayType valueForString(String value) {
        if (value == null) {
            return null;
        }

        for (DisplayType type : DisplayType.values()) {
            if (type.toString().equals(value)) {
                return type;
            }
        }

        return null;
    }