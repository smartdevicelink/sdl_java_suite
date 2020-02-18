
    public static final String KEY_KEYPRESS_MODE = "keypressMode";

    private static final KeypressMode KEYPRESS_MODE_DEFAULT = KeypressMode.RESEND_CURRENT_ENTRY;

    public KeypressMode getKeypressMode() {
        KeypressMode kp = (KeypressMode) getObject(KeypressMode.class, KEY_KEYPRESS_MODE);
        if(kp == null){
            kp = KEYPRESS_MODE_DEFAULT;
        }
        return kp;
    }

    public void setKeypressMode(KeypressMode keypressMode) {
        if (keypressMode != null) {
            setValue(KEY_KEYPRESS_MODE, keypressMode);
        } else {
            setValue(KEY_KEYPRESS_MODE, KEYPRESS_MODE_DEFAULT);
        }
    }