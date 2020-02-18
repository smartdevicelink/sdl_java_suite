
    public static final String KEY_DATA = "data";

    public SystemRequest(boolean bLegacy) {
        super(FunctionID.ENCODED_SYNC_P_DATA.toString());
    }

    @SuppressWarnings("unchecked")
    public List<String> getLegacyData() {
        return (List<String>) getObject(String.class, KEY_DATA);
    }

    public void setLegacyData( List<String> data ) {
        setParameters(KEY_DATA, data);
    }