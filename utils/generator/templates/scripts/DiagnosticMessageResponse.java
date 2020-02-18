
    /**
     * Constructs a new DiagnosticMessageResponse object
     * @param success whether the request is successfully processed
     * @param resultCode whether the request is successfully processed
     * @deprecated use {@link DiagnosticMessageResponse#DiagnosticMessageResponse(Boolean, Result)}
     */
    @Deprecated
    public DiagnosticMessageResponse(@NonNull Boolean success, @NonNull Result resultCode, @NonNull List<Integer> messageDataResult) {
        this();
        setSuccess(success);
        setResultCode(resultCode);
        setMessageDataResult(messageDataResult);
    }