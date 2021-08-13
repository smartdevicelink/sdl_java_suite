package com.smartdevicelink.test.protocol.enums;

import com.smartdevicelink.protocol.enums.QueryErrorCode;
import com.smartdevicelink.test.Validator;

import junit.framework.TestCase;

import java.util.Vector;

public class QueryErrorCodeTests extends TestCase {

    private Vector<QueryErrorCode> list = QueryErrorCode.getList();

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

            QueryErrorCode enumSuccess = (QueryErrorCode) QueryErrorCode.get(list, ERROR_SUCCESS_BYTE);
            QueryErrorCode enumInvalidQuerySize = (QueryErrorCode) QueryErrorCode.get(list, ERROR_INVALID_QUERY_SIZE_BYTE);
            QueryErrorCode enumInvalidQueryID = (QueryErrorCode) QueryErrorCode.get(list, ERROR_INVALID_QUERY_ID_BYTE);
            QueryErrorCode enumNotSupported = (QueryErrorCode) QueryErrorCode.get(list, ERROR_NOT_SUPPORTED_BYTE);
            QueryErrorCode enumServiceAlreadyProtected = (QueryErrorCode) QueryErrorCode.get(list, ERROR_SERVICE_ALREADY_PROTECTED_BYTE);
            QueryErrorCode enumServiceNotProtected = (QueryErrorCode) QueryErrorCode.get(list, ERROR_SERVICE_NOT_PROTECTED_BYTE);
            QueryErrorCode enumDecryptionFailed = (QueryErrorCode) QueryErrorCode.get(list, ERROR_DECRYPTION_FAILED_BYTE);
            QueryErrorCode enumEncryptionFailed = (QueryErrorCode) QueryErrorCode.get(list, ERROR_ENCRYPTION_FAILED_BYTE);
            QueryErrorCode enumSSLInvalidData = (QueryErrorCode) QueryErrorCode.get(list, ERROR_SSL_INVALID_DATA_BYTE);
            QueryErrorCode enumHandshakeFailed = (QueryErrorCode) QueryErrorCode.get(list, ERROR_HANDSHAKE_FAILED_BYTE);
            QueryErrorCode enumInvalidCert = (QueryErrorCode) QueryErrorCode.get(list, INVALID_CERT_BYTE);
            QueryErrorCode enumExpiredCert = (QueryErrorCode) QueryErrorCode.get(list, EXPIRED_CERT_BYTE);
            QueryErrorCode enumInternal = (QueryErrorCode) QueryErrorCode.get(list, ERROR_INTERNAL_BYTE);
            QueryErrorCode enumUnknownInternalError = (QueryErrorCode) QueryErrorCode.get(list, ERROR_UNKNOWN_INTERNAL_ERROR_BYTE);

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

            enumSuccess = (QueryErrorCode) QueryErrorCode.get(list, ERROR_SUCCESS_STRING);
            enumInvalidQuerySize = (QueryErrorCode) QueryErrorCode.get(list, ERROR_INVALID_QUERY_SIZE_STRING);
            enumInvalidQueryID = (QueryErrorCode) QueryErrorCode.get(list, ERROR_INVALID_QUERY_ID_STRING);
            enumNotSupported = (QueryErrorCode) QueryErrorCode.get(list, ERROR_NOT_SUPPORTED_STRING);
            enumServiceAlreadyProtected = (QueryErrorCode) QueryErrorCode.get(list, ERROR_SERVICE_ALREADY_PROTECTED_STRING);
            enumServiceNotProtected = (QueryErrorCode) QueryErrorCode.get(list, ERROR_SERVICE_NOT_PROTECTED_STRING);
            enumDecryptionFailed = (QueryErrorCode) QueryErrorCode.get(list, ERROR_DECRYPTION_FAILED_STRING);
            enumEncryptionFailed = (QueryErrorCode) QueryErrorCode.get(list, ERROR_ENCRYPTION_FAILED_STRING);
            enumSSLInvalidData = (QueryErrorCode) QueryErrorCode.get(list, ERROR_SSL_INVALID_DATA_STRING);
            enumHandshakeFailed = (QueryErrorCode) QueryErrorCode.get(list, ERROR_HANDSHAKE_FAILED_STRING);
            enumInvalidCert = (QueryErrorCode) QueryErrorCode.get(list, INVALID_CERT_STRING);
            enumExpiredCert = (QueryErrorCode) QueryErrorCode.get(list, EXPIRED_CERT_STRING);
            enumInternal = (QueryErrorCode) QueryErrorCode.get(list, ERROR_INTERNAL_STRING);
            enumUnknownInternalError = (QueryErrorCode) QueryErrorCode.get(list, ERROR_UNKNOWN_INTERNAL_ERROR_STRING);

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
            QueryErrorCode enumInvalid = (QueryErrorCode) QueryErrorCode.get(list, INVALID_BYTE);
            assertNull("Invalid byte match didn't return null", enumInvalid);

            enumInvalid = (QueryErrorCode) QueryErrorCode.get(list, INVALID_STRING);
            assertNull("Invalid byte match didn't return null", enumInvalid);
        } catch (IllegalArgumentException exception) {
            fail("Invalid enum throws IllegalArgumentException.");
        }
    }

    public void testNullEnum() {
        try {
            QueryErrorCode enumNull = (QueryErrorCode) QueryErrorCode.get(list, null);
            assertNull("Null lookup returns a value", enumNull);
        } catch (NullPointerException exception) {
            fail("Null string throws NullPointerException.");
        }
    }

    public void testListEnum() {
        Vector<QueryErrorCode> enumTestList = new Vector<>();
        enumTestList.add(QueryErrorCode.ERROR_SUCCESS);
        enumTestList.add(QueryErrorCode.ERROR_INVALID_QUERY_SIZE);
        enumTestList.add(QueryErrorCode.ERROR_INVALID_QUERY_ID);
        enumTestList.add(QueryErrorCode.ERROR_NOT_SUPPORTED);
        enumTestList.add(QueryErrorCode.ERROR_SERVICE_ALREADY_PROTECTED);
        enumTestList.add(QueryErrorCode.ERROR_SERVICE_NOT_PROTECTED);
        enumTestList.add(QueryErrorCode.ERROR_DECRYPTION_FAILED);
        enumTestList.add(QueryErrorCode.ERROR_ENCRYPTION_FAILED);
        enumTestList.add(QueryErrorCode.ERROR_SSL_INVALID_DATA);
        enumTestList.add(QueryErrorCode.ERROR_HANDSHAKE_FAILED);
        enumTestList.add(QueryErrorCode.INVALID_CERT);
        enumTestList.add(QueryErrorCode.EXPIRED_CERT);
        enumTestList.add(QueryErrorCode.ERROR_INTERNAL);
        enumTestList.add(QueryErrorCode.ERROR_UNKNOWN_INTERNAL_ERROR);

        assertTrue("List does not match enum test list.",
                list.containsAll(enumTestList) &&
                        enumTestList.containsAll(list));

        QueryErrorCode[] enumValueArray = QueryErrorCode.values();
        QueryErrorCode[] enumTestArray = {
                QueryErrorCode.ERROR_SUCCESS,
                QueryErrorCode.ERROR_INVALID_QUERY_SIZE,
                QueryErrorCode.ERROR_INVALID_QUERY_ID,
                QueryErrorCode.ERROR_NOT_SUPPORTED,
                QueryErrorCode.ERROR_SERVICE_ALREADY_PROTECTED,
                QueryErrorCode.ERROR_SERVICE_NOT_PROTECTED,
                QueryErrorCode.ERROR_DECRYPTION_FAILED,
                QueryErrorCode.ERROR_ENCRYPTION_FAILED,
                QueryErrorCode.ERROR_SSL_INVALID_DATA,
                QueryErrorCode.ERROR_HANDSHAKE_FAILED,
                QueryErrorCode.INVALID_CERT,
                QueryErrorCode.EXPIRED_CERT,
                QueryErrorCode.ERROR_INTERNAL,
                QueryErrorCode.ERROR_UNKNOWN_INTERNAL_ERROR
        };
        assertTrue("Array does not match enum values array.",
                Validator.validateQueryErrorCodeArray(enumValueArray, enumTestArray));
    }
}
