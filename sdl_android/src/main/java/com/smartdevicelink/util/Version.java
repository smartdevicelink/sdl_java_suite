package com.smartdevicelink.util;


public class Version {

    final int major,minor,patch;

    public Version(){
        major = 0;
        minor = 0;
        patch = 0;
    }

    public Version(int major, int minor, int patch){
        this.major = major;
        this.minor = minor;
        this.patch = patch;
    }

    public Version(String versionString){
        String[] versions = versionString.split("\\.");
        if(versions.length!=3){
            throw new IllegalArgumentException("Incorrect version string format");
        }
        major = Integer.valueOf(versions[0]);
        minor = Integer.valueOf(versions[1]);
        patch = Integer.valueOf(versions[2]);

    }

    public int getMajor() {
        return major;
    }

    public int getMinor() {
        return minor;
    }

    public int getPatch() {
        return patch;
    }

    /**
     * Method to test if this instance of Version is newer than the supplied one.
     * @param version the version to check against
     * @return 1 if this instance is newer, -1 if supplied version is newer, and 0 if they are equal
     */
    public int isNewerThan(Version version){
        if(this.major >= version.major){
            if(this.minor >= version.minor){
                if(this.patch > version.patch){
                    return 1;
                }else if(this.patch == version.patch){
                    return 0;
                }
            }
        }
        return -1;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(major);
        builder.append(".");
        builder.append(minor);
        builder.append(".");
        builder.append(patch);
        return builder.toString();
    }
}
