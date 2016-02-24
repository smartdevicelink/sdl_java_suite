
#include <openssl/bio.h>
#include <openssl/ssl.h>
#include <openssl/err.h>
#include <openssl/conf.h>
#include <jni.h>
#include <vector>

using namespace std;

const int TLS_ERROR_NONE 		=  0;
const int TLS_ERROR_SSL		 	= -1;
const int TLS_ERROR_WANT_READ	= -2;
const int TLS_ERROR_WANT_WRITE	= -3;
const int TLS_WRITE_FAILED	 	= -4;
const int TLS_GENERIC_ERROR 	= -5;
const int TLS_SESSION_NOT_FOUND	= -6;


class TLSObject
{
	private:
		SSL_CTX *ctx   = NULL;
		SSL  	*conn  = NULL;
		BIO  	*rbio  = NULL;
		BIO  	*wbio  = NULL;
		int 	iSessionId;
	public:
		TLSObject();
		~TLSObject();
		int getSessionId();
		void setSessionId(int iVal);
		int getSSLError(int iVal, int iLen, bool bWrite);
		void cleanUpInit(JNIEnv *env, jbyteArray certdata, jbyteArray keydata, jbyte *cert_buffer, jbyte *key_buffer, X509 *cert, RSA  *rsa, BIO *cbio, BIO *kbio);
		bool isHandShakeComplete( JNIEnv* env, jobject thiz );
		int BIO_pendingData( JNIEnv* env, jobject thiz );
		int SSLPendingDataFromServer( JNIEnv* env, jobject thiz );
		int SSLWriteDataToServer( JNIEnv* env, jobject thiz, jbyteArray array );
		int SSLReadDataFromServer( JNIEnv* env, jobject thiz, jbyteArray array );
		int BIOWriteDataToServer( JNIEnv* env, jobject thiz, jbyteArray array );
		int BIOReadDataFromServer( JNIEnv* env, jobject thiz, jbyteArray array );
		bool initOpenSSL( JNIEnv* env, jobject thiz, jbyteArray certdata, jbyteArray keydata);
		void shutDownOpenSSL();
		void sslShutDownWithRetry();
};

TLSObject::TLSObject()
{
}
TLSObject::~TLSObject()
{
	shutDownOpenSSL();
}

bool TLSObject::isHandShakeComplete( JNIEnv* env, jobject thiz )
{
	if (conn == NULL)
		return false;

	if (!SSL_is_init_finished(conn))
	{
			SSL_do_handshake(conn);
	}

	bool bComplete = SSL_is_init_finished(conn);
	return bComplete;
}


void TLSObject::cleanUpInit(JNIEnv *env, jbyteArray certdata, jbyteArray keydata, jbyte *cert_buffer, jbyte *key_buffer, X509 *cert, RSA  *rsa, BIO *cbio, BIO *kbio)
{
	if (cert_buffer != NULL)
		env->ReleaseByteArrayElements(certdata, cert_buffer, 0);
	if (key_buffer != NULL)
		env->ReleaseByteArrayElements(keydata, key_buffer, 0);
	if (cert != NULL)
		X509_free(cert);
	if (rsa != NULL)
		RSA_free(rsa);
	if (cbio != NULL)
		BIO_free(cbio);
	if (kbio != NULL)
		BIO_free(kbio);
}

int TLSObject::getSSLError(int iVal, int iLen, bool bWrite)
{
	int error = SSL_get_error(conn,iVal);
	switch(error)
	{
		case SSL_ERROR_NONE:
			if(iLen!=iVal && bWrite)
				return TLS_WRITE_FAILED;
			else
				return TLS_ERROR_NONE;
		case SSL_ERROR_SSL:
			return TLS_ERROR_SSL;
		case SSL_ERROR_WANT_READ:
			return TLS_ERROR_WANT_READ;
		case SSL_ERROR_WANT_WRITE:
			return TLS_ERROR_WANT_WRITE;
		default:
			return TLS_GENERIC_ERROR;
	}
}

int TLSObject::BIO_pendingData( JNIEnv* env, jobject thiz )
{
	return BIO_pending(wbio);
}

int TLSObject::SSLPendingDataFromServer( JNIEnv* env, jobject thiz )
{
	return SSL_pending(conn);
}

int TLSObject::SSLWriteDataToServer( JNIEnv* env, jobject thiz, jbyteArray array )
{
	jsize len = env->GetArrayLength (array);
	jbyte *buf = env->GetByteArrayElements(array, 0);
	int iReturn = SSL_write(conn, buf, len);
	env->ReleaseByteArrayElements(array, buf, 0);

	int iError = getSSLError(iReturn, len, true);
	if (iError != TLS_ERROR_NONE)
		return iError;

	return iReturn;
}

int TLSObject::SSLReadDataFromServer( JNIEnv* env, jobject thiz, jbyteArray array )
{
	jbyte *body = env->GetByteArrayElements(array, 0);
	jsize len = env->GetArrayLength(array);
	int iReturn =  SSL_read(conn,body,len);
	env->ReleaseByteArrayElements(array, body, 0);

	int iError = getSSLError(iReturn, len, false);
	if (iError != TLS_ERROR_NONE)
		return iError;

	return iReturn;
}

int TLSObject::BIOWriteDataToServer( JNIEnv* env, jobject thiz, jbyteArray array )
{
	jsize len = env->GetArrayLength (array);
	jbyte *buf = env->GetByteArrayElements(array, 0);
	int iReturn = BIO_write(rbio, buf, len);
	env->ReleaseByteArrayElements(array, buf, 0);

	int iError = getSSLError(iReturn, len, false);
	if (iError != TLS_ERROR_NONE)
		return iError;

	return iReturn;
}

int TLSObject::BIOReadDataFromServer( JNIEnv* env, jobject thiz, jbyteArray array )
{
	jsize len = env->GetArrayLength(array);
	jbyte *body = env->GetByteArrayElements(array, 0);
	int res = BIO_read(wbio, body, len);
	env->ReleaseByteArrayElements(array, body, 0);

	int iError = getSSLError(res, len, false);
	if (iError != TLS_ERROR_NONE)
		return iError;

	return res;
}

bool TLSObject::initOpenSSL( JNIEnv* env, jobject thiz, jbyteArray certdata, jbyteArray keydata)
{
	X509 *cert  = NULL;
	RSA  *rsa   = NULL;
	BIO	 *cbio  = NULL;
	BIO  *kbio  = NULL;

	jsize certlen = env->GetArrayLength (certdata);
	jbyte *cert_buffer = env->GetByteArrayElements(certdata, 0);

	jsize keylen = env->GetArrayLength (keydata);
	jbyte *key_buffer = env->GetByteArrayElements(keydata, 0);

	SSL_load_error_strings();
	ERR_load_BIO_strings();
	OpenSSL_add_all_algorithms();
	SSL_library_init();

	ctx = SSL_CTX_new(TLSv1_2_server_method());
	SSL_CTX_set_verify(ctx, SSL_VERIFY_NONE, NULL);

	long options =  SSL_OP_NO_SSLv2
				| SSL_OP_NO_COMPRESSION /* CRIME attack */
				| SSL_OP_SINGLE_DH_USE | SSL_OP_SINGLE_ECDH_USE; /* small subgroup attack */

	SSL_CTX_set_options(ctx, options);

	// bool bSuccess = SSL_CTX_use_certificate_file(ctx, "/sdcard/cacert.pem" ,SSL_FILETYPE_PEM);
	cbio = BIO_new_mem_buf((void*)cert_buffer, -1);
	cert = PEM_read_bio_X509(cbio, NULL, 0, NULL);
	if (cert == NULL)
	{
		cleanUpInit(env, certdata, keydata, cert_buffer, key_buffer, cert, rsa, cbio, kbio);
		return false;
	}
	bool bSuccess = SSL_CTX_use_certificate(ctx, cert);
	if (!bSuccess)
	{
		cleanUpInit(env, certdata, keydata, cert_buffer, key_buffer, cert, rsa, cbio, kbio);
		return false;
	}
	//bSuccess = SSL_CTX_use_PrivateKey_file(ctx, "/sdcard/privkey.pem", SSL_FILETYPE_PEM);
	kbio = BIO_new_mem_buf((void*)key_buffer, -1);
	rsa = PEM_read_bio_RSAPrivateKey(kbio, NULL, 0, NULL);
	if (rsa == NULL)
	{
		cleanUpInit(env, certdata, keydata, cert_buffer, key_buffer, cert, rsa, cbio, kbio);
		return false;
	}
	bSuccess = SSL_CTX_use_RSAPrivateKey(ctx, rsa);
	if (!bSuccess)
	{
		cleanUpInit(env, certdata, keydata, cert_buffer, key_buffer, cert, rsa, cbio, kbio);
		return false;
	}
	bSuccess = SSL_CTX_check_private_key(ctx);
	if (!bSuccess)
	{
		cleanUpInit(env, certdata, keydata, cert_buffer, key_buffer, cert, rsa, cbio, kbio);
		return false;
	}
	bSuccess = SSL_CTX_set_cipher_list(ctx, "ALL");
	if (!bSuccess)
	{
		cleanUpInit(env, certdata, keydata, cert_buffer, key_buffer, cert, rsa, cbio, kbio);
		return false;
	}
	conn = SSL_new(ctx);
	if (conn == NULL)
	{
		cleanUpInit(env, certdata, keydata, cert_buffer, key_buffer, cert, rsa, cbio, kbio);
		return false;
	}
	rbio = BIO_new(BIO_s_mem());
	wbio = BIO_new(BIO_s_mem());
	BIO_set_mem_eof_return(rbio, -1);
	SSL_set_bio(conn, rbio, wbio);
	SSL_set_accept_state(conn);
	cleanUpInit(env, certdata, keydata, cert_buffer, key_buffer, cert, rsa, cbio, kbio);
	return true;
}

void TLSObject::shutDownOpenSSL()
{
	if(conn!=NULL) {
		sslShutDownWithRetry();
		SSL_free(conn);
	}
	if(ctx!=NULL) SSL_CTX_free(ctx);

	CONF_modules_unload(1);
	ERR_remove_state(0);
	ERR_free_strings();
	EVP_cleanup();
	CRYPTO_cleanup_all_ex_data();

}

void TLSObject::sslShutDownWithRetry()
{
	int rc = 0;
	for (int i = 0; i < 4; i++)
	{
		rc = SSL_shutdown(conn);
		if (rc > 0)
			break;
	}
}
int TLSObject::getSessionId()
{
	return iSessionId;
}

void TLSObject::setSessionId(int iVal)
{
	iSessionId = iVal;
}

std::vector<TLSObject*>  *manList = new vector<TLSObject*>();

TLSObject * getTLSObject(int iSessionId)
{
	TLSObject *myObj = NULL;
	for (int i=0; i<manList->size(); i++)
	{
		myObj = manList->at(i);
		if (myObj->getSessionId() == iSessionId)
			return myObj;
	}
	return NULL;
}

int getTLSObjectPos(int iSessionId)
{
	TLSObject *myObj = NULL;
	for (int i=0; i<manList->size(); i++)
	{
		myObj = manList->at(i);
		if (myObj->getSessionId() == iSessionId)
			return i;
	}
	return TLS_SESSION_NOT_FOUND;
}

extern "C" JNIEXPORT bool JNICALL
Java_com_smartdevicelink_proxy_TLSManager_isHandShakeComplete( JNIEnv* env, jobject thiz, jint iSessionId )
{
	TLSObject *theObj = getTLSObject(iSessionId);
	if (theObj == NULL)
		return false;

	return theObj->isHandShakeComplete(env,thiz);
}

extern "C" JNIEXPORT int JNICALL
Java_com_smartdevicelink_proxy_TLSManager_BIO_pending( JNIEnv* env, jobject thiz, jint iSessionId )
{
	TLSObject *theObj = getTLSObject(iSessionId);
	if (theObj == NULL)
		return TLS_SESSION_NOT_FOUND;

	return theObj->BIO_pendingData(env,thiz);
}

extern "C" JNIEXPORT int JNICALL
Java_com_smartdevicelink_proxy_TLSManager_SSLPendingDataFromServer( JNIEnv* env, jobject thiz, jint iSessionId )
{
	TLSObject *theObj = getTLSObject(iSessionId);
	if (theObj == NULL)
		return TLS_SESSION_NOT_FOUND;

	return theObj->SSLPendingDataFromServer(env,thiz);
}

extern "C" JNIEXPORT int JNICALL
Java_com_smartdevicelink_proxy_TLSManager_SSLWriteDataToServer( JNIEnv* env, jobject thiz, jbyteArray array, jint iSessionId )
{
	TLSObject *theObj = getTLSObject(iSessionId);
	if (theObj == NULL)
		return TLS_SESSION_NOT_FOUND;

	return theObj->SSLWriteDataToServer(env,thiz,array);
}

extern "C" JNIEXPORT int JNICALL
Java_com_smartdevicelink_proxy_TLSManager_SSLReadDataFromServer( JNIEnv* env, jobject thiz, jbyteArray array, jint iSessionId )
{
	TLSObject *theObj = getTLSObject(iSessionId);
	if (theObj == NULL)
		return TLS_SESSION_NOT_FOUND;

	return theObj->SSLReadDataFromServer(env,thiz,array);
}

extern "C" JNIEXPORT int JNICALL
Java_com_smartdevicelink_proxy_TLSManager_BIOWriteDataToServer( JNIEnv* env, jobject thiz, jbyteArray array, jint iSessionId )
{
	TLSObject *theObj = getTLSObject(iSessionId);
	if (theObj == NULL)
		return TLS_SESSION_NOT_FOUND;

	return theObj->BIOWriteDataToServer(env,thiz,array);
}

extern "C" JNIEXPORT int JNICALL
Java_com_smartdevicelink_proxy_TLSManager_BIOReadDataFromServer( JNIEnv* env, jobject thiz, jbyteArray array, jint iSessionId )
{
	TLSObject *theObj = getTLSObject(iSessionId);
	if (theObj == NULL)
		return TLS_SESSION_NOT_FOUND;

	return theObj->BIOReadDataFromServer(env,thiz,array);
}

extern "C" JNIEXPORT bool JNICALL
Java_com_smartdevicelink_proxy_TLSManager_initOpenSSL( JNIEnv* env, jobject thiz, jbyteArray certdata, jbyteArray keydata, jint iSessionId)
{
	TLSObject *theObj = getTLSObject(iSessionId);
	if (theObj == NULL)
		theObj = new TLSObject();

	theObj->setSessionId(iSessionId);
	manList->push_back(theObj);
	return theObj->initOpenSSL(env,thiz,certdata,keydata);
}

extern "C" JNIEXPORT void JNICALL
Java_com_smartdevicelink_proxy_TLSManager_close(JNIEnv* env, jobject thiz, jint iSessionId)
{
	TLSObject *theObj = getTLSObject(iSessionId);
	if (theObj == NULL)
		return;

	int iPos = getTLSObjectPos(iSessionId);
	if (iPos == TLS_SESSION_NOT_FOUND)
		return;

	manList->erase(manList->begin()+iPos);

	delete theObj;
}
