
    /**
     * Gets the seat locations of this capability
     * @return the seat locations
     */
    @SuppressWarnings("unchecked")
    public List<SeatLocation> getSeatLocations() {
        return (List<SeatLocation>) getObject(SeatLocation.class, KEY_SEATS);
    }