/*
 * Copyright (c) 2019, Livio, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided with the
 * distribution.
 *
 * Neither the name of the Livio Inc. nor the names of its contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
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
        if(this.major > version.major){
            return 1;
        }else if(this.major == version.major){
            if(this.minor > version.minor){
                return 1;
            } else if(this.minor == version.minor){
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
