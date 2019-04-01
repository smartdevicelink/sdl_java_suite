package com.smartdevicelink.transport.utl;

import android.support.annotation.IntDef;
import android.support.annotation.NonNull;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.io.File;

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
