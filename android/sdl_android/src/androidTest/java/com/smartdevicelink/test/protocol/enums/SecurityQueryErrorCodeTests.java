package com.smartdevicelink.test.protocol.enums;

import com.smartdevicelink.protocol.enums.SecurityQueryErrorCode;
import com.smartdevicelink.test.Validator;

import junit.framework.TestCase;

import java.util.Vector;

public class SecurityQueryErrorCodeTests extends TestCase {

    private Vector<SecurityQueryErrorCode> list = SecurityQueryErrorCode.getList();

    public void testValidEnums() {
        final byte ERROR_SUCCESS_BYTE = (byte) 0x00;
        final String ERROR_SUCCESS_STRING = "ERROR_SUCCESS";

        final byte ERROR_INVALID_QUERY_SIZE_BYTE = (byte) 0x01;
        final String ERROR_INVALID_QUERY_SIZE_STRING = "ERROR_INVALID_QUERY_SIZE";

        final byte ERROR_INVALID_QUERY_ID_BYTE = (byte) 0x02;
        final String ERROR_INVALID_QUERY_ID_STRING = "ERROR_INVALID_QUERY_ID";

        final byte ERROR_NOT_SUPPORTED_BYTE = (byte) 0x03;
        final String ERROR_NOT_SUPPORTED_STRING = "ERROR_NOT_SUPPORTED";

        final byte ERROR_SERVICE_ALREADY_PROTECTED_BYTE = (byte) 0x04;
        final String ERROR_SERVICE_ALREADY_PROTECTED_STRING = "ERROR_SERVICE_ALREADY_PROTECTED";

        final byte ERROR_SERVICE_NOT_PROTECTED_BYTE = (byte) 0x05;
        final String ERROR_SERVICE_NOT_PROTECTED_STRING = "ERROR_SERVICE_NOT_PROTECTED";

        final byte ERROR_DECRYPTION_FAILED_BYTE = (byte) 0x06;
        final String ERROR_DECRYPTION_FAILED_STRING = "ERROR_DECRYPTION_FAILED";

        final byte ERROR_ENCRYPTION_FAILED_BYTE = (byte) 0x07;
        final String ERROR_ENCRYPTION_FAILED_STRING = "ERROR_ENCRYPTION_FAILED";

        final byte ERROR_SSL_INVALID_DATA_BYTE = (byte) 0x08;
        final String ERROR_SSL_INVALID_DATA_STRING = "ERROR_SSL_INVALID_DATA";

        final byte ERROR_HANDSHAKE_FAILED_BYTE = (byte) 0x09;
        final String ERROR_HANDSHAKE_FAILED_STRING = "ERROR_HANDSHAKE_FAILED";

        final byte INVALID_CERT_BYTE = (byte) 0x0A;
        final String INVALID_CERT_STRING = "INVALID_CERT";

        final byte EXPIRED_CERT_BYTE = (byte) 0x0B;
        final String EXPIRED_CERT_STRING = "EXPIRED_CERT";

        final byte ERROR_INTERNAL_BYTE = (byte) 0xFF;
        final String ERROR_INTERNAL_STRING = "ERROR_INTERNAL";

        final byte ERROR_UNKNOWN_INTERNAL_ERROR_BYTE = (byte) 0xFE;
        final String ERROR_UNKNOWN_INTERNAL_ERROR_STRING = "ERROR_UNKNOWN_INTERNAL_ERROR";

        try {
            assertNotNull("QueryErrorCode list returned null", list);

            SecurityQueryErrorCode enumSuccess = (SecurityQueryErrorCode) SecurityQueryErrorCode.get(list, ERROR_SUCCESS_BYTE);
            SecurityQueryErrorCode enumInvalidQuerySize = (SecurityQueryErrorCode) SecurityQueryErrorCode.get(list, ERROR_INVALID_QUERY_SIZE_BYTE);
            SecurityQueryErrorCode enumInvalidQueryID = (SecurityQueryErrorCode) SecurityQueryErrorCode.get(list, ERROR_INVALID_QUERY_ID_BYTE);
            SecurityQueryErrorCode enumNotSupported = (SecurityQueryErrorCode) SecurityQueryErrorCode.get(list, ERROR_NOT_SUPPORTED_BYTE);
            SecurityQueryErrorCode enumServiceAlreadyProtected = (SecurityQueryErrorCode) SecurityQueryErrorCode.get(list, ERROR_SERVICE_ALREADY_PROTECTED_BYTE);
            SecurityQueryErrorCode enumServiceNotProtected = (SecurityQueryErrorCode) SecurityQueryErrorCode.get(list, ERROR_SERVICE_NOT_PROTECTED_BYTE);
            SecurityQueryErrorCode enumDecryptionFailed = (SecurityQueryErrorCode) SecurityQueryErrorCode.get(list, ERROR_DECRYPTION_FAILED_BYTE);
            SecurityQueryErrorCode enumEncryptionFailed = (SecurityQueryErrorCode) SecurityQueryErrorCode.get(list, ERROR_ENCRYPTION_FAILED_BYTE);
            SecurityQueryErrorCode enumSSLInvalidData = (SecurityQueryErrorCode) SecurityQueryErrorCode.get(list, ERROR_SSL_INVALID_DATA_BYTE);
            SecurityQueryErrorCode enumHandshakeFailed = (SecurityQueryErrorCode) SecurityQueryErrorCode.get(list, ERROR_HANDSHAKE_FAILED_BYTE);
            SecurityQueryErrorCode enumInvalidCert = (SecurityQueryErrorCode) SecurityQueryErrorCode.get(list, INVALID_CERT_BYTE);
            SecurityQueryErrorCode enumExpiredCert = (SecurityQueryErrorCode) SecurityQueryErrorCode.get(list, EXPIRED_CERT_BYTE);
            SecurityQueryErrorCode enumInternal = (SecurityQueryErrorCode) SecurityQueryErrorCode.get(list, ERROR_INTERNAL_BYTE);
            SecurityQueryErrorCode enumUnknownInternalError = (SecurityQueryErrorCode) SecurityQueryErrorCode.get(list, ERROR_UNKNOWN_INTERNAL_ERROR_BYTE);

            assertNotNull("Success byte match returned null", enumSuccess);
            assertNotNull("Invalid Query Size byte match returned null", enumInvalidQuerySize);
            assertNotNull("Invalid Query ID byte match returned null", enumInvalidQueryID);
            assertNotNull("Not Supported byte match returned null", enumNotSupported);
            assertNotNull("Service Already Protected byte match returned null", enumServiceAlreadyProtected);
            assertNotNull("Service Not Protected byte match returned null", enumServiceNotProtected);
            assertNotNull("Decryption Failed byte match returned null", enumDecryptionFailed);
            assertNotNull("Encryption Failed byte match returned null", enumEncryptionFailed);
            assertNotNull("SSL Invalid Data byte match returned null", enumSSLInvalidData);
            assertNotNull("Handshake Failed byte match returned null", enumHandshakeFailed);
            assertNotNull("Invalid Cert byte match returned null", enumInvalidCert);
            assertNotNull("Expired Cert byte match returned null", enumExpiredCert);
            assertNotNull("Internal byte match returned null", enumInternal);
            assertNotNull("Unknown Internal byte match returned null", enumUnknownInternalError);

            enumSuccess = (SecurityQueryErrorCode) SecurityQueryErrorCode.get(list, ERROR_SUCCESS_STRING);
            enumInvalidQuerySize = (SecurityQueryErrorCode) SecurityQueryErrorCode.get(list, ERROR_INVALID_QUERY_SIZE_STRING);
            enumInvalidQueryID = (SecurityQueryErrorCode) SecurityQueryErrorCode.get(list, ERROR_INVALID_QUERY_ID_STRING);
            enumNotSupported = (SecurityQueryErrorCode) SecurityQueryErrorCode.get(list, ERROR_NOT_SUPPORTED_STRING);
            enumServiceAlreadyProtected = (SecurityQueryErrorCode) SecurityQueryErrorCode.get(list, ERROR_SERVICE_ALREADY_PROTECTED_STRING);
            enumServiceNotProtected = (SecurityQueryErrorCode) SecurityQueryErrorCode.get(list, ERROR_SERVICE_NOT_PROTECTED_STRING);
            enumDecryptionFailed = (SecurityQueryErrorCode) SecurityQueryErrorCode.get(list, ERROR_DECRYPTION_FAILED_STRING);
            enumEncryptionFailed = (SecurityQueryErrorCode) SecurityQueryErrorCode.get(list, ERROR_ENCRYPTION_FAILED_STRING);
            enumSSLInvalidData = (SecurityQueryErrorCode) SecurityQueryErrorCode.get(list, ERROR_SSL_INVALID_DATA_STRING);
            enumHandshakeFailed = (SecurityQueryErrorCode) SecurityQueryErrorCode.get(list, ERROR_HANDSHAKE_FAILED_STRING);
            enumInvalidCert = (SecurityQueryErrorCode) SecurityQueryErrorCode.get(list, INVALID_CERT_STRING);
            enumExpiredCert = (SecurityQueryErrorCode) SecurityQueryErrorCode.get(list, EXPIRED_CERT_STRING);
            enumInternal = (SecurityQueryErrorCode) SecurityQueryErrorCode.get(list, ERROR_INTERNAL_STRING);
            enumUnknownInternalError = (SecurityQueryErrorCode) SecurityQueryErrorCode.get(list, ERROR_UNKNOWN_INTERNAL_ERROR_STRING);

            assertNotNull("Success string match returned null", enumSuccess);
            assertNotNull("Invalid Query Size string match returned null", enumInvalidQuerySize);
            assertNotNull("Invalid Query ID string match returned null", enumInvalidQueryID);
            assertNotNull("Not Supported string match returned null", enumNotSupported);
            assertNotNull("Service Already Protected string match returned null", enumServiceAlreadyProtected);
            assertNotNull("Service Not Protected string match returned null", enumServiceNotProtected);
            assertNotNull("Decryption Failed string match returned null", enumDecryptionFailed);
            assertNotNull("Encryption Failed string match returned null", enumEncryptionFailed);
            assertNotNull("SSL Invalid Data string match returned null", enumSSLInvalidData);
            assertNotNull("Handshake Failed string match returned null", enumHandshakeFailed);
            assertNotNull("Invalid Cert string match returned null", enumInvalidCert);
            assertNotNull("Expired Cert string match returned null", enumExpiredCert);
            assertNotNull("Internal string match returned null", enumInternal);
            assertNotNull("Unknown Internal string match returned null", enumUnknownInternalError);
        } catch (NullPointerException exception) {
            fail("Null enum list throws NullPointerException.");
        }
    }

    public void testInvalidEnum() {
        final byte INVALID_BYTE = (byte) 0xAB;
        final String INVALID_STRING = "Invalid";

        try {
            SecurityQueryErrorCode enumInvalid = (SecurityQueryErrorCode) SecurityQueryErrorCode.get(list, INVALID_BYTE);
            assertNull("Invalid byte match didn't return null", enumInvalid);

            enumInvalid = (SecurityQueryErrorCode) SecurityQueryErrorCode.get(list, INVALID_STRING);
            assertNull("Invalid byte match didn't return null", enumInvalid);
        } catch (IllegalArgumentException exception) {
            fail("Invalid enum throws IllegalArgumentException.");
        }
    }

    public void testNullEnum() {
        try {
            SecurityQueryErrorCode enumNull = (SecurityQueryErrorCode) SecurityQueryErrorCode.get(list, null);
            assertNull("Null lookup returns a value", enumNull);
        } catch (NullPointerException exception) {
            fail("Null string throws NullPointerException.");
        }
    }

    public void testListEnum() {
        Vector<SecurityQueryErrorCode> enumTestList = new Vector<>();
        enumTestList.add(SecurityQueryErrorCode.ERROR_SUCCESS);
        enumTestList.add(SecurityQueryErrorCode.ERROR_INVALID_QUERY_SIZE);
        enumTestList.add(SecurityQueryErrorCode.ERROR_INVALID_QUERY_ID);
        enumTestList.add(SecurityQueryErrorCode.ERROR_NOT_SUPPORTED);
        enumTestList.add(SecurityQueryErrorCode.ERROR_SERVICE_ALREADY_PROTECTED);
        enumTestList.add(SecurityQueryErrorCode.ERROR_SERVICE_NOT_PROTECTED);
        enumTestList.add(SecurityQueryErrorCode.ERROR_DECRYPTION_FAILED);
        enumTestList.add(SecurityQueryErrorCode.ERROR_ENCRYPTION_FAILED);
        enumTestList.add(SecurityQueryErrorCode.ERROR_SSL_INVALID_DATA);
        enumTestList.add(SecurityQueryErrorCode.ERROR_HANDSHAKE_FAILED);
        enumTestList.add(SecurityQueryErrorCode.INVALID_CERT);
        enumTestList.add(SecurityQueryErrorCode.EXPIRED_CERT);
        enumTestList.add(SecurityQueryErrorCode.ERROR_INTERNAL);
        enumTestList.add(SecurityQueryErrorCode.ERROR_UNKNOWN_INTERNAL_ERROR);

        assertTrue("List does not match enum test list.",
                list.containsAll(enumTestList) &&
                        enumTestList.containsAll(list));

        SecurityQueryErrorCode[] enumValueArray = SecurityQueryErrorCode.values();
        SecurityQueryErrorCode[] enumTestArray = {
                SecurityQueryErrorCode.ERROR_SUCCESS,
                SecurityQueryErrorCode.ERROR_INVALID_QUERY_SIZE,
                SecurityQueryErrorCode.ERROR_INVALID_QUERY_ID,
                SecurityQueryErrorCode.ERROR_NOT_SUPPORTED,
                SecurityQueryErrorCode.ERROR_SERVICE_ALREADY_PROTECTED,
                SecurityQueryErrorCode.ERROR_SERVICE_NOT_PROTECTED,
                SecurityQueryErrorCode.ERROR_DECRYPTION_FAILED,
                SecurityQueryErrorCode.ERROR_ENCRYPTION_FAILED,
                SecurityQueryErrorCode.ERROR_SSL_INVALID_DATA,
                SecurityQueryErrorCode.ERROR_HANDSHAKE_FAILED,
                SecurityQueryErrorCode.INVALID_CERT,
                SecurityQueryErrorCode.EXPIRED_CERT,
                SecurityQueryErrorCode.ERROR_INTERNAL,
                SecurityQueryErrorCode.ERROR_UNKNOWN_INTERNAL_ERROR
        };
        assertTrue("Array does not match enum values array.",
                Validator.validateQueryErrorCodeArray(enumValueArray, enumTestArray));
    }
}
