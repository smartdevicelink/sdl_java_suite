
	/**
	 * Constructor that takes in the mandatory parameters.
	 * @param serviceType the type of service this is
	 * @see com.smartdevicelink.proxy.rpc.enums.AppServiceType
	 */
	public AppServiceManifest(@NonNull AppServiceType serviceType) {
        this();
        setServiceType(serviceType.name());
    }

    /**
	 * This field contains the Function IDs for the RPCs that this service intends to handle correctly.
	 * This means the service will provide meaningful responses.
	 * @param handledRPCs - The List of Handled RPCs using the FunctionID enum
	 * @see #setHandledRpcs(List)
	 */
	public void setHandledRpcsUsingFunctionIDs(List<FunctionID> handledRPCs){
		if(handledRPCs != null){
			List<Integer> rpcIds = new ArrayList<>();
			for(FunctionID functionID : handledRPCs){
				rpcIds.add(functionID.getId());
			}
			setHandledRpcs(rpcIds);
		}else{
			setValue(KEY_HANDLED_RPCS, null);
		}
	}