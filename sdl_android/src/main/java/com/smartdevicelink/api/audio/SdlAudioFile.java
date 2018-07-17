package com.smartdevicelink.api.audio;

import java.io.File;

public class SdlAudioFile {
    private File inputFile;
    private File outputFile;

    public SdlAudioFile(File inputFile, File outputFile) {
        this.inputFile = inputFile;
        this.outputFile = outputFile;
    }

    public File getInputFile() {
        return inputFile;
    }

    public File getOutputFile() {
        return outputFile;
    }
}
