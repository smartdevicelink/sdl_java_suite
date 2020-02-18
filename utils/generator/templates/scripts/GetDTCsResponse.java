
    /**
     * Constructs a new GetDTCsResponse object
     * @param success whether the request is successfully processed
     * @param resultCode whether the request is successfully processed
     * @param ecuHeader representation of the ecu header that was returned from the GetDTC request
     * @deprecated use {@link GetDTCsResponse#GetDTCsResponse(Boolean, Result)}
     */
    @Deprecated
    public GetDTCsResponse(@NonNull Boolean success, @NonNull Result resultCode, @NonNull Integer ecuHeader) {
        this();
        setSuccess(success);
        setResultCode(resultCode);
        setEcuHeader(ecuHeader);
    }

