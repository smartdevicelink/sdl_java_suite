package com.smartdevicelink.managers.file;

/**
 * <strong>FileManagerConfig</strong> <br>
 * 
 * This is set during SdlManager instantiation. <br>
 *
 * <li> artworkRetryCount -  # of attempts allowed for SdlArtwork to be re-uploaded if they fail </li>
 *
 * <li> fileRetryCount - # of attempts allowed for SdlFiles to be re-uploaded if they fail</li>
 */
public class FileManagerConfig {
    private int artworkRetryCount, fileRetryCount;

    /**
     * Constructor for FileMangerConfig
     * Sets artworkRetryCount and fileRetryCount to a default value of 1
     */
    public FileManagerConfig() {
        // set default values to 1 retry attempt
        this.artworkRetryCount = 1;
        this.fileRetryCount = 1;
    }

    /**
     * Setter for Integer artWorkRetryCount
     *
     * @param artworkRetryCount the number of retry attempts
     */
    public void setArtworkRetryCount(int artworkRetryCount) {
        this.artworkRetryCount = artworkRetryCount;
    }

    /**
     * Getter for Integer artWorkRetryCount
     *
     * @return Integer artworkRetryCount
     */
    public int getArtworkRetryCount() {
        return artworkRetryCount;
    }

    /**
     * Setter for Integer fileRetryCount
     *
     * @param fileRetryCount the number of retry attempts
     */
    public void setFileRetryCount(int fileRetryCount) {
        this.fileRetryCount = fileRetryCount;
    }

    /**
     * Getter for Integer fileRetryCount
     *
     * @return Integer fileRetryCount
     */
    public int getFileRetryCount() {
        return fileRetryCount;
    }
}
