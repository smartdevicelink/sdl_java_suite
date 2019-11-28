
    @Override
    public void format(com.smartdevicelink.util.Version rpcVersion, boolean formatParams) {
        if (rpcVersion.getMajor() < 5) {
            if (getVideoStreamingState() == null) {
                setVideoStreamingState(VideoStreamingState.STREAMABLE);
            }
        }
        super.format(rpcVersion, formatParams);
    }

    /**
     * <p>Query whether it's the first run</p>
     * @return boolean whether it's the first run
     */
    public Boolean getFirstRun() {
        return this.firstRun;
    }
    /**
     * <p>Set the firstRun value</p>
     * @param firstRun True if it is the first run, False or not
     */
    public void setFirstRun(Boolean firstRun) {
        this.firstRun = firstRun;
    }