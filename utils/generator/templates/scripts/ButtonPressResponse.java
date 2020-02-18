
    /**
     * Constructs a new ButtonPressResponse object
     * @param resultCode whether the request is successfully processed
     * @param success whether the request is successfully processed
     */
    public ButtonPressResponse(@NonNull Result resultCode, @NonNull Boolean success) {
        this();
        setResultCode(resultCode);
        setSuccess(success);
    }