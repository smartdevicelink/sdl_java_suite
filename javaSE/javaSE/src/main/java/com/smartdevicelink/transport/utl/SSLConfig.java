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
package com.smartdevicelink.transport.utl;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;

import java.io.File;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class SSLConfig {

    @IntDef({JKS, PEM})
    @Retention(RetentionPolicy.SOURCE)
    public @interface SSLCertificateType {}
    public static final int JKS = 0;
    public static final int PEM = 1;


    final @SSLCertificateType int  sslCertificateType;
    String pemCertificate, privateKey, password;
    String storePassword, keyPassword;
    File jksFile;


    /**
     * This creates an SSLConfig using a PEM type certificate.
     * @param pemCertificate string representation of a PEM file that should be used for the SSL session
     * @param privateKey the private key used with the PEM file
     * @param password the password used with the PEN file
     */
    public SSLConfig(@NonNull String pemCertificate, @NonNull String privateKey, @NonNull String password){
        this.sslCertificateType = PEM;
        this.pemCertificate = pemCertificate;
        this.privateKey = privateKey;
        this.password = password;
    }


    /**
     * This creates an SSLConfig using a JKS file.
     * @param jksFile File that contains the JKS that should be used for the SSL session
     * @param storePassword the password associated with the JKS
     * @param keyPassword the key password used with the JKS
     */
    public SSLConfig(@NonNull File jksFile, @NonNull String storePassword, @NonNull String keyPassword){
        this.sslCertificateType = JKS;
        this.jksFile = jksFile;
        this.storePassword = storePassword;
        this.keyPassword = keyPassword;
    }

    public @SSLCertificateType int getSslCertificateType() {
        return sslCertificateType;
    }

    public String getPemCertificate() {
        return pemCertificate;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public String getPassword() {
        return password;
    }


    public File getJksFile() {
        return jksFile;
    }

    public String getStorePassword() {
        return storePassword;
    }

    public String getKeyPassword() {
        return keyPassword;
    }
}
