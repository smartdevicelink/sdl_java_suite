
    /**
     * Constructs a newly allocated Choice object
     * @param choiceID Min: 0  Max: 65535
     * @param menuName the menu name
     * @param vrCommands the List of  vrCommands
     *
     * Deprecated - use {@link #Choice(Integer, String)}
     */
    @Deprecated
    public Choice(@NonNull Integer choiceID, @NonNull String menuName, @NonNull List<String> vrCommands) {
        this();
        setChoiceID(choiceID);
        setMenuName(menuName);
        setVrCommands(vrCommands);
    }

    private boolean ignoreAddingVRItems;

    /**
     * VrCommands became optional as of RPC Spec 5.0. On legacy systems, we must still set VrCommands, as
     * they are expected, even though the developer may not specify them. <br>
     *
     * Additionally, VrCommands must be unique, therefore we will use the string value of the command's ID
     *
     * @param rpcVersion the rpc spec version that has been negotiated. If value is null the
     *                   the max value of RPC spec version this library supports should be used.
     * @param formatParams if true, the format method will be called on subsequent params
     */
    @Override
    public void format(Version rpcVersion, boolean formatParams){

        if (rpcVersion == null || rpcVersion.getMajor() < 5){

            // this is added to allow the choice set manager to disable this functionality
            if (!ignoreAddingVRItems) {
                // make sure there is at least one vr param
                List<String> existingVrCommands = getVrCommands();

                if (existingVrCommands == null || existingVrCommands.size() == 0) {
                    // if no commands set, set one due to a legacy head unit requirement
                    Integer choiceID = getChoiceID();
                    List<String> vrCommands = new ArrayList<>();
                    vrCommands.add(String.valueOf(choiceID));
                    setVrCommands(vrCommands);
                }
            }
        }

        super.format(rpcVersion, formatParams);
    }

    /**
     * This prevents the @{link Choice#format} method from adding VR commands if set to true
     * @param ignoreAddingVRItems - whether or not to let the format method add vr commands
     */
    public void setIgnoreAddingVRItems(boolean ignoreAddingVRItems){
        this.ignoreAddingVRItems = ignoreAddingVRItems;
    }