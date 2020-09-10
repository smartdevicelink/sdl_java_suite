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

import com.smartdevicelink.util.DebugTool;
import org.java_websocket.WebSocketServerFactory;
import org.java_websocket.server.DefaultSSLWebSocketServerFactory;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import javax.xml.bind.DatatypeConverter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

public class SSLWebSocketFactoryGenerator {
    private static final String TAG = "SSLWebSocketFactoryGenerator";
    private static final String JAVA_KEY_STORE = "JKS";
    private static final String TLS = "TLS";
    private static final String SUNX509 = "SunX509";

    public static WebSocketServerFactory generateWebSocketServer(SSLConfig config){
        SSLContext context;
        switch (config.getSslCertificateType()){
            case SSLConfig.JKS:
                context = getSSLContextFromJKS(config);
                break;
            case SSLConfig.PEM:
                context = getSSLContextFromPem(config);
                break;
            default:
                DebugTool.logError(TAG, "Unable to generateWebSocketServer. Unsupported cert type.");
                return null;
        }
        if(context != null) {
            return new DefaultSSLWebSocketServerFactory(context);
        }else{
            DebugTool.logError(TAG, "SSLWebSocketFactoryGenerator: Unable to create SSL Context");
            return null;
        }
    }

/* ******************************************* JKS ********************************************/

    private static SSLContext getSSLContextFromJKS(SSLConfig config){

        try {
            KeyStore ks = KeyStore.getInstance(JAVA_KEY_STORE);
            File kf = config.getJksFile();//= new File(PATHNAME + File.separator + KEYSTORE);
            ks.load(new FileInputStream(kf), config.getStorePassword().toCharArray());

            KeyManagerFactory kmf = KeyManagerFactory.getInstance(SUNX509);
            kmf.init(ks, config.getKeyPassword().toCharArray());
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(SUNX509);
            tmf.init(ks);

            SSLContext sslContext;
            sslContext = SSLContext.getInstance(TLS);
            sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
            return sslContext;
        }
        catch(Exception e){
            DebugTool.logError(TAG, "Issue creating SSLContext with JKS : " , e);
        }
        return null;
    }

    /* ******************************************* PEM ********************************************/

    private static SSLContext getSSLContextFromPem(SSLConfig config) {
        SSLContext context;

        try {
            context = SSLContext.getInstance( TLS );

            byte[] certBytes = parseDERFromPEM( config.getPemCertificate().getBytes(), "-----BEGIN CERTIFICATE-----", "-----END CERTIFICATE-----" );
            byte[] keyBytes = parseDERFromPEM( config.getPrivateKey().getBytes(), "-----BEGIN PRIVATE KEY-----", "-----END PRIVATE KEY-----" );

            X509Certificate cert = generateCertificateFromDER( certBytes );
            RSAPrivateKey key = generatePrivateKeyFromDER( keyBytes );

            KeyStore keystore = KeyStore.getInstance( JAVA_KEY_STORE );
            keystore.load( null );
            keystore.setCertificateEntry( "cert-alias", cert );
            keystore.setKeyEntry( "key-alias", key, config.getPassword().toCharArray(), new Certificate[]{ cert } );

            KeyManagerFactory kmf = KeyManagerFactory.getInstance( SUNX509 );
            kmf.init( keystore, config.getPassword().toCharArray() );

            KeyManager[] km = kmf.getKeyManagers();

            context.init( km, null, null );
        } catch ( Exception e ) {
            context = null;
            DebugTool.logError(TAG, "Issue creating SSLContext with PEM Cert : " , e);
        }
        return context;
    }

    private static byte[] parseDERFromPEM( byte[] pem, String beginDelimiter, String endDelimiter ) {
        String data = new String( pem );
        String[] tokens = data.split( beginDelimiter );
        tokens = tokens[1].split( endDelimiter );
        return DatatypeConverter.parseBase64Binary( tokens[0] );
    }

    private static RSAPrivateKey generatePrivateKeyFromDER( byte[] keyBytes ) throws InvalidKeySpecException, NoSuchAlgorithmException {
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec( keyBytes );

        KeyFactory factory = KeyFactory.getInstance( "RSA" );

        return ( RSAPrivateKey ) factory.generatePrivate( spec );
    }

    private static X509Certificate generateCertificateFromDER( byte[] certBytes ) throws CertificateException {
        CertificateFactory factory = CertificateFactory.getInstance( "X.509" );

        return ( X509Certificate ) factory.generateCertificate( new ByteArrayInputStream( certBytes ) );
    }


}
