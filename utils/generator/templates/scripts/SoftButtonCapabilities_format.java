
    @Override
    public void format(Version rpcVersion, boolean formatParams) {
        super.format(rpcVersion, formatParams);
        if(!store.containsKey(KEY_IMAGE_SUPPORTED)){
            // At some point this was added to the RPC spec as mandatory but at least in v1.0.0
            // it was not included.
            store.put(KEY_IMAGE_SUPPORTED, Boolean.FALSE);
        }
    }
