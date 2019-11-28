
    /**
     * Constructs a newly allocated GPSData object
     * @deprecated Use {@link #GPSData(@NonNull Float, @NonNull Float)()} instead
     */
    @Deprecated
    public GPSData(@NonNull Double longitudeDegrees, @NonNull Double latitudeDegrees, @NonNull Integer utcYear,
                   @NonNull Integer utcMonth, @NonNull Integer utcDay, @NonNull Integer utcHours,
                   @NonNull Integer utcMinutes, @NonNull Integer utcSeconds, @NonNull CompassDirection compassDirection,
                   @NonNull Double pdop, @NonNull Double hdop, @NonNull Double vdop, @NonNull Boolean actual,
                   @NonNull Integer satellites, @NonNull Dimension dimension, @NonNull Double altitude, @NonNull Double heading, @NonNull Double speed) {
        this();
        setLongitudeDegrees(longitudeDegrees);
        setLatitudeDegrees(latitudeDegrees);
        setUtcYear(utcYear);
        setUtcMonth(utcMonth);
        setUtcDay(utcDay);
        setUtcHours(utcHours);
        setUtcMinutes(utcMinutes);
        setUtcSeconds(utcSeconds);
        setCompassDirection(compassDirection);
        setPdop(pdop);
        setHdop(hdop);
        setVdop(vdop);
        setActual(actual);
        setSatellites(satellites);
        setDimension(dimension);
        setAltitude(altitude);
        setHeading(heading);
        setSpeed(speed);
    }
