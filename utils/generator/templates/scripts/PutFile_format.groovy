
    /**
     * @deprecated as of SmartDeviceLink 4.0
     * @param offset Optional offset in bytes for resuming partial data chunks
     */
    public void setOffset(Integer offset) {
        if(offset == null){
            setOffset((Long)null);
        }else{
            setOffset(offset.longValue());
        }
    }

    /**
     * @deprecated as of SmartDeviceLink 4.0
     * @param length Optional length in bytes for resuming partial data chunks. If offset is set to 0, then length is
     *               the total length of the file to be downloaded
     */
    public void setLength(Integer length) {
        if(length == null){
            setLength((Long)null);
        }else{
            setLength(length.longValue());
        }
    }

    public void setFileData(byte[] fileData) {
        setBulkData(fileData);
    }

    public byte[] getFileData() {
        return getBulkData();
    }

    /**
     * This takes the file data as an array of bytes and calculates the
     * CRC32 for it.
     * @param fileData - the file as a byte array
     */
    public void setCRC(byte[] fileData) {
        if (fileData != null) {
            CRC32 crc = new CRC32();
            crc.update(fileData);
            parameters.put(KEY_CRC, crc.getValue());
        } else {
            parameters.remove(KEY_CRC);
        }
    }

    /**
     * This assumes you have created your own CRC32 and are setting it with the file
     * <STRONG>Please avoid using your own calculations for this, and use the method
     * included in java.util</STRONG>
     * @param crc - the CRC32 of the file being set
     */
    public void setCRC(Long crc) {
        if (crc != null) {
            parameters.put(KEY_CRC, crc);
        } else {
            parameters.remove(KEY_CRC);
        }
    }

    /**
     * This returns the CRC, if it has been set, for the file object
     * @return - a CRC32 Long
     */
    public Long getCRC() {
        final Object o = parameters.get(KEY_CRC);
        if (o == null){
            return null;
        }
        if (o instanceof Integer) {
            return ((Integer) o).longValue();
        }else if(o instanceof Long){
            return (Long) o;
        }
        return null;
    }

    @Override
    public final void setOnRPCResponseListener(OnRPCResponseListener listener) {
        super.setOnRPCResponseListener(listener);
    }

    public void setOnPutFileUpdateListener(OnPutFileUpdateListener listener) {
        super.setOnRPCResponseListener(listener); //We can use the same method because it get stored as a parent class
    }

    public OnPutFileUpdateListener getOnPutFileUpdateListener() {
        return (OnPutFileUpdateListener)getOnRPCResponseListener();
    }