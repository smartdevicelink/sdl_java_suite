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

    private static final String JAVA_KEY_STORE = "JKS";
    private static final String TLS = "TLS";
    private static final String SUNX509 = "SunX509";

    public static WebSocketServerFactory generateWebSocketServer(SSLConfig config){
        SSLContext context = null;
        switch (config.getSslCertificateType()){
            case SSLConfig.JKS:
                context = getSSLContextFromJKS(config);
                break;
            case SSLConfig.PEM:
                context = getSSLContextFromPem(config);
                break;
            default:
                DebugTool.logError("Unable to generateWebSocketServer. Unsupported cert type.");
                return null;
        }
        if(context != null) {
            return new DefaultSSLWebSocketServerFactory(context);
        }else{
            DebugTool.logError("SSLWebSocketFactoryGenerator: Unable to create SSL Context");
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

            SSLContext sslContext = null;
            sslContext = SSLContext.getInstance(TLS);
            sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
            return sslContext;
        }
        catch(Exception e){
            DebugTool.logError("Issue creating SSLContext with JKS : " , e);
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
            DebugTool.logError("Issue creating SSLContext with PEM Cert : " , e);
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
