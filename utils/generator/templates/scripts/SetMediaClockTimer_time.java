    private SetMediaClockTimer(@NonNull UpdateMode updateMode, @Nullable StartTime startTime, @Nullable StartTime endTime, @Nullable AudioStreamingIndicator audioStreamingIndicator){
        this();
        this.setUpdateMode(updateMode);
        if (startTime != null) {
            this.setStartTime(startTime);
        }
        if (endTime != null) {
            this.setEndTime(endTime);
        }
        if (audioStreamingIndicator != null) {
            this.setAudioStreamingIndicator(audioStreamingIndicator);
        }
    }
    /**
     * Create a media clock timer that counts up, e.g from 0:00 to 4:18.
     *
     * @param startTimeInterval       The start time interval, e.g. (0) 0:00
     * @param endTimeInterval         The end time interval, e.g. (258) 4:18
     * @param audioStreamingIndicator playPauseIndicator An optional audio indicator to change the play/pause button
     * @return An object of SetMediaClockTimer
     */
    public static SetMediaClockTimer countUpFromStartTimeInterval(@NonNull Integer startTimeInterval, @NonNull Integer endTimeInterval, @Nullable AudioStreamingIndicator audioStreamingIndicator) {
        return new SetMediaClockTimer(UpdateMode.COUNTUP, new StartTime(startTimeInterval), new StartTime(endTimeInterval), audioStreamingIndicator);
    }
    /**
     * Create a media clock timer that counts up, e.g from 0:00 to 4:18.
     *
     * @param startTime               The start time interval, e.g. 0:00
     * @param endTime                 The end time interval, e.g. 4:18
     * @param audioStreamingIndicator An optional audio indicator to change the play/pause button
     * @return An object of SetMediaClockTimer
     */
    public static SetMediaClockTimer countUpFromStartTime(@NonNull StartTime startTime, @NonNull StartTime endTime, @Nullable AudioStreamingIndicator audioStreamingIndicator) {
        return new SetMediaClockTimer(UpdateMode.COUNTUP, startTime, endTime, audioStreamingIndicator);
    }
    /**
     * Create a media clock timer that counts down, e.g. from 4:18 to 0:00
     * This will fail if endTime is greater than startTime
     *
     * @param startTimeInterval       The start time interval, e.g. (258) 4:18
     * @param endTimeInterval         The end time interval, e.g. (0) 0:00
     * @param audioStreamingIndicator An optional audio indicator to change the play/pause button
     * @return An object of SetMediaClockTimer
     */
    public static SetMediaClockTimer countDownFromStartTimeInterval(@NonNull Integer startTimeInterval, @NonNull Integer endTimeInterval, @Nullable AudioStreamingIndicator audioStreamingIndicator) {
        return new SetMediaClockTimer(UpdateMode.COUNTDOWN, new StartTime(startTimeInterval), new StartTime(endTimeInterval), audioStreamingIndicator);
    }
    /**
     * Create a media clock timer that counts down, e.g. from 4:18 to 0:00
     * This will fail if endTime is greater than startTime
     *
     * @param startTime               The start time interval, e.g. 4:18
     * @param endTime                 The end time interval, e.g. 0:00
     * @param audioStreamingIndicator An optional audio indicator to change the play/pause button
     * @return An object of SetMediaClockTimer
     */
    public static SetMediaClockTimer countDownFromStartTime(@NonNull StartTime startTime, @NonNull StartTime endTime, @Nullable AudioStreamingIndicator audioStreamingIndicator) {
        return new SetMediaClockTimer(UpdateMode.COUNTDOWN, startTime, endTime, audioStreamingIndicator);
    }
    /**
     * Pause an existing (counting up / down) media clock timer
     *
     * @param audioStreamingIndicator An optional audio indicator to change the play/pause button
     * @return An object of SetMediaClockTimer
     */
    public static SetMediaClockTimer pauseWithPlayPauseIndicator(@Nullable AudioStreamingIndicator audioStreamingIndicator) {
        return new SetMediaClockTimer(UpdateMode.PAUSE, null, null, audioStreamingIndicator);
    }
    /**
     * Update a pause time (or pause and update the time) on a media clock timer
     *
     * @param startTimeInterval       The new start time interval
     * @param endTimeInterval         The new end time interval
     * @param audioStreamingIndicator An optional audio indicator to change the play/pause button
     * @return An object of SetMediaClockTimer
     */
    public static SetMediaClockTimer updatePauseWithNewStartTimeInterval(@NonNull Integer startTimeInterval, @NonNull Integer endTimeInterval, @Nullable AudioStreamingIndicator audioStreamingIndicator) {
        return new SetMediaClockTimer(UpdateMode.PAUSE, new StartTime(startTimeInterval), new StartTime(endTimeInterval), audioStreamingIndicator);
    }
    /**
     * Update a pause time (or pause and update the time) on a media clock timer
     *
     * @param startTime               The new start time
     * @param endTime                 The new end time
     * @param audioStreamingIndicator An optional audio indicator to change the play/pause button
     * @return An object of SetMediaClockTimer
     */
    public static SetMediaClockTimer updatePauseWithNewStartTime(@NonNull StartTime startTime, @NonNull StartTime endTime, @Nullable AudioStreamingIndicator audioStreamingIndicator) {
        return new SetMediaClockTimer(UpdateMode.PAUSE, startTime, endTime, audioStreamingIndicator);
    }
    /**
     * Resume a paused media clock timer. It resumes at the same time at which it was paused.
     *
     * @param audioStreamingIndicator An optional audio indicator to change the play/pause button
     * @return An object of SetMediaClockTimer
     */
    public static SetMediaClockTimer resumeWithPlayPauseIndicator(@Nullable AudioStreamingIndicator audioStreamingIndicator) {
        return new SetMediaClockTimer(UpdateMode.RESUME, null, null, audioStreamingIndicator);
    }
    /**
     * Remove a media clock timer from the screen
     *
     * @param audioStreamingIndicator An optional audio indicator to change the play/pause button
     * @return An object of SetMediaClockTimer
     */
    public static SetMediaClockTimer clearWithPlayPauseIndicator(@Nullable AudioStreamingIndicator audioStreamingIndicator) {
        return new SetMediaClockTimer(UpdateMode.CLEAR, null, null, audioStreamingIndicator);
    }
