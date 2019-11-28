
    /**
     * Constructs a new GetSystemCapabilityResponse object
     * @param resultCode whether the request is successfully processed
     * @param success whether the request is successfully processed
     */
    public GetSystemCapabilityResponse(@NonNull Result resultCode, @NonNull Boolean success) {
        this();
        setResultCode(resultCode);
        setSuccess(success);
    }
    
    /**
     * Constructs a new GetSystemCapabilityResponse object
     * @param systemCapability SystemCapability object
     * @param resultCode whether the request is successfully processed
     * @param success whether the request is successfully processed
     * @deprecated use {@link GetSystemCapabilityResponse#GetSystemCapabilityResponse(Result, Boolean)}
     */
    @Deprecated
    public GetSystemCapabilityResponse(@NonNull SystemCapability systemCapability, @NonNull Result resultCode, @NonNull Boolean success) {
        this();
        setSystemCapability(systemCapability);
        setResultCode(resultCode);
        setSuccess(success);
    }
