
    /**
     * Constructs a newly allocated SdlMsgVersion object
     * @param version Creates a new RPC struct SdlMsgVersion based on the utility class
     */
    public SdlMsgVersion(@NonNull Version version) {
        this();
        setMajorVersion(version.getMajor());
        setMinorVersion(version.getMinor());
        setPatchVersion(version.getPatch());

    }

    @Override
    public void format(com.smartdevicelink.util.Version rpcVersion, boolean formatParams) {
        if(getPatchVersion() == null){
            setPatchVersion(0);
        }
        super.format(rpcVersion,formatParams);
    }
