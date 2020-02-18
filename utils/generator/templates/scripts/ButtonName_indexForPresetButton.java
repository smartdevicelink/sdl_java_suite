    /**
     * indexForPresetButton returns the integer index for preset buttons
     * which match the preset order. E.G.: indexForPresetButton(PRESET_1)
     * returns the value 1. If the buttonName given is not a preset button,
     * the method will return null.
     *
     * @param buttonName the buttonName of PRESET_0 through PRESET_9 to
     * @return Integer that represents which preset the supplied button name represents. It will return null if the
     *         ButtonName is not one of the PRESET_# names.
     */
    public static Integer indexForPresetButton(ButtonName buttonName) {
        if (buttonName == null) {
            return null;
        }

        Integer returnIndex = null;

        switch (buttonName) {
            case PRESET_0:
                returnIndex = 0;
                break;
            case PRESET_1:
                returnIndex = 1;
                break;
            case PRESET_2:
                returnIndex = 2;
                break;
            case PRESET_3:
                returnIndex = 3;
                break;
            case PRESET_4:
                returnIndex = 4;
                break;
            case PRESET_5:
                returnIndex = 5;
                break;
            case PRESET_6:
                returnIndex = 6;
                break;
            case PRESET_7:
                returnIndex = 7;
                break;
            case PRESET_8:
                returnIndex = 8;
                break;
            case PRESET_9:
                returnIndex = 9;
                break;
            default:
                break;
        }

        return returnIndex;
    }