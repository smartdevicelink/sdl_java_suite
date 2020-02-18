
	public static final String KEY_RESOLUTION_WIDTH = "resolutionWidth";
	public static final String KEY_RESOLUTION_HEIGHT = "resolutionHeight";

    public ImageResolution(@NonNull Integer resolutionWidth, @NonNull Integer resolutionHeight) {
        this();
        setResolutionWidth(resolutionWidth);
        setResolutionHeight(resolutionHeight);
    }

    /**
     * @param resolutionWidth the desired resolution width. Odds values cause problems in
     *                        the Android H264 decoder, as a workaround the odd value is
     *                        converted to a pair value.
     */
    public void setResolutionWidth(@NonNull Integer resolutionWidth) {
        if(resolutionWidth != null && resolutionWidth % 2 != 0) {
            resolutionWidth++;
        }
        setValue(KEY_RESOLUTION_WIDTH, resolutionWidth);
    }

    public Integer getResolutionWidth() {
        return getInteger(KEY_RESOLUTION_WIDTH);
    }

    /**
     * @param resolutionHeight the desired resolution height. Odds values cause problems in
     *                        the Android H264 decoder, as a workaround the odd value is
     *                        converted to a pair value.
     */
    public void setResolutionHeight(@NonNull Integer resolutionHeight) {
        if(resolutionHeight != null && resolutionHeight % 2 != 0) {
            resolutionHeight++;
        }
        setValue(KEY_RESOLUTION_HEIGHT, resolutionHeight);
    }

    public Integer getResolutionHeight() {
        return getInteger(KEY_RESOLUTION_HEIGHT);
    }

    @Override
    public String toString() {
        return "width=" + String.valueOf(getResolutionWidth()) +
               ", height=" + String.valueOf(getResolutionHeight());
    }