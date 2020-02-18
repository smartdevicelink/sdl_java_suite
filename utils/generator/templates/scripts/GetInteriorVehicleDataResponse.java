
	/**
	 * Constructs a new GetInteriorVehicleDataResponse object
	 * @param resultCode whether the request is successfully processed
	 * @param success whether the request is successfully processed
	 */
	public GetInteriorVehicleDataResponse(@NonNull Result resultCode, @NonNull Boolean success) {
		this();
		setResultCode(resultCode);
		setSuccess(success);
	}

	/**
	 * Constructs a new GetInteriorVehicleDataResponse object
	 * @param moduleData specific data for the module that was requested
	 * @param resultCode whether the request is successfully processed
	 * @param success whether the request is successfully processed
	 * @deprecated use {@link GetInteriorVehicleDataResponse#GetInteriorVehicleDataResponse(Result, Boolean)}
	 */
	@Deprecated
	public GetInteriorVehicleDataResponse(@NonNull ModuleData moduleData, @NonNull Result resultCode, @NonNull Boolean success) {
		this();
		setModuleData(moduleData);
		setResultCode(resultCode);
		setSuccess(success);
	}
