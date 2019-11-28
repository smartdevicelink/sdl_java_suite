
    /**
     * Convenience init for dismissing a specific interaction.
     * @param functionID - The ID of the type of interaction to dismiss
     * @param cancelID - The ID of the specific interaction to dismiss
     */
    public CancelInteraction(@NonNull Integer functionID, Integer cancelID) {
        this();
        setInteractionFunctionID(functionID);
        setCancelID(cancelID);
    }