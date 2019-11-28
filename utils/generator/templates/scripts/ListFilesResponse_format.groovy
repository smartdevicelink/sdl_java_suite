
    /**
     * @deprecated use {@link ListFilesResponse#ListFilesResponse(Boolean, Result)} instead <br>
     *
     * Constructs a new ListFilesResponse object
     * @param success whether the request is successfully processed
     * @param resultCode whether the request is successfully processed
     */
    @Deprecated
    public ListFilesResponse(@NonNull Boolean success, @NonNull Result resultCode, @NonNull Integer spaceAvailable) {
        this();
        setSuccess(success);
        setResultCode(resultCode);
        setSpaceAvailable(spaceAvailable);
    }

    /**
     * SpaceAvailable became optional as of RPC Spec 5.0. If a system that expected the value to
     * always have a value connects to such a system, it could return null. Check to see if there
     * is a value, and if not, set it to MAX_VALUE as defined by the RPC Spec
     *
     * @param rpcVersion the rpc spec version that has been negotiated. If value is null the
     *                   the max value of RPC spec version this library supports should be used.
     * @param formatParams if true, the format method will be called on subsequent params
     */
    @Override
    public void format(Version rpcVersion, boolean formatParams){
        if (rpcVersion == null || rpcVersion.getMajor() >= 5){
            if (getSpaceAvailable() == null){
                setSpaceAvailable(MAX_VALUE);
            }
        }
        super.format(rpcVersion, formatParams);
    }
