
	/**
	 * Constructs a newly allocated StartTime object
	 * @param timeInterval time interval in seconds
	 */
	public StartTime(@NonNull Integer timeInterval){
		this();
		int hours = timeInterval / 3600;
		int minutes = (timeInterval % 3600) / 60;
		int seconds = timeInterval % 60;
		setHours(hours);
		setMinutes(minutes);
		setSeconds(seconds);
	}