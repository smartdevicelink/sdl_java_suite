package com.smartdevicelink.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;

public class FileUtls {


    /**
     *
     * @param file should include path and name
     * @return
     */
    public static boolean doesFileExist(String file){
        try{
            return false;//Files.exists(new Path(file), (LinkOption)null);
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public static byte[] getFileData(String path){
        byte[] data = null;
        if(path != null && path.length() > 0) {
            if (doesFileExist(path)) {
                try {
                   return Files.readAllBytes(new File("/path/to/file").toPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

        return null;
    }
}
