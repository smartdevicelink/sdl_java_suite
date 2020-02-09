package com.smartdevicelink.managers.file;

/**
 * <strong>FileManagerConfig</strong> <br>
 *
 * This is set during SdlManager instantiation. <br>
 *
 * <li> artWorkRetryCount - if not set, will be set to 1 to allow for retry upload attempts for SDLArtWork</li>
 *
 * <li> fileRetryCount - if not set, will be set to 1 to allow for retry upload attempts for SDLFiles</li>
 */

public class FileManagerConfig {
    private int artworkRetryCount, fileRetryCount;

    public FileManagerConfig(){
        // set default values to 1 retry attempt
        this.artworkRetryCount = 1;
        this.fileRetryCount = 1;
    }
    public void setArtworkRetryCount(int artworkRetryCount) { this.artworkRetryCount = artworkRetryCount; }

    public int getArtworkRetryCount() {
        return artworkRetryCount;
    }

    public void setFileRetryCount(int fileRetryCount) { this.fileRetryCount = fileRetryCount; }

    public int getFileRetryCount() { return fileRetryCount; }
}
