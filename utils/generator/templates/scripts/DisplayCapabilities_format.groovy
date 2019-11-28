
    /**
     * Constructs a newly allocated DisplayCapabilities object
     * @param displayName the display name (String)
     * @param textFields the List of textFields
     * @param mediaClockFormats the List of MediaClockFormat
     * @param graphicSupported true if the display supports graphics, false if it does not
     */
    public DisplayCapabilities(String displayName, @NonNull List<TextField> textFields, @NonNull List<MediaClockFormat> mediaClockFormats, @NonNull Boolean graphicSupported) {
        this();
        setDisplayName(displayName);
        setTextFields(textFields);
        setMediaClockFormats(mediaClockFormats);
        setGraphicSupported(graphicSupported);
    }

    @Override
    public void format(Version rpcVersion, boolean formatParams) {
        super.format(rpcVersion, formatParams);
        if(!store.containsKey(KEY_GRAPHIC_SUPPORTED)){
            // At some point this was added to the RPC spec as mandatory but at least in v1.0.0
            // it was not included.
            store.put(KEY_GRAPHIC_SUPPORTED, Boolean.FALSE);
        }
    }
