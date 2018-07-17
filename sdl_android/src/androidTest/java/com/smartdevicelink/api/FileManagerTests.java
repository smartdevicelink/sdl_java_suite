package com.smartdevicelink.api;

import android.content.Context;
import android.net.Uri;
import android.test.AndroidTestCase;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.interfaces.IAudioStreamListener;
import com.smartdevicelink.proxy.interfaces.ISdl;
import com.smartdevicelink.proxy.interfaces.ISdlServiceListener;
import com.smartdevicelink.proxy.interfaces.IVideoStreamListener;
import com.smartdevicelink.proxy.interfaces.OnSystemCapabilityListener;
import com.smartdevicelink.proxy.rpc.ListFiles;
import com.smartdevicelink.proxy.rpc.ListFilesResponse;
import com.smartdevicelink.proxy.rpc.PutFile;
import com.smartdevicelink.proxy.rpc.PutFileResponse;
import com.smartdevicelink.proxy.rpc.SdlMsgVersion;
import com.smartdevicelink.proxy.rpc.TemplateColorScheme;
import com.smartdevicelink.proxy.rpc.enums.AppHMIType;
import com.smartdevicelink.proxy.rpc.enums.FileType;
import com.smartdevicelink.proxy.rpc.enums.Language;
import com.smartdevicelink.proxy.rpc.enums.Result;
import com.smartdevicelink.proxy.rpc.enums.SystemCapabilityType;
import com.smartdevicelink.proxy.rpc.listeners.OnMultipleRequestListener;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCNotificationListener;
import com.smartdevicelink.streaming.audio.AudioStreamingCodec;
import com.smartdevicelink.streaming.audio.AudioStreamingParams;
import com.smartdevicelink.streaming.video.VideoStreamingParameters;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.transport.BaseTransportConfig;
import com.smartdevicelink.transport.TCPTransportConfig;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import static com.smartdevicelink.api.SdlManagerTests.transport;

/**
 * This is a unit test class for the SmartDeviceLink library manager class :
 * {@link com.smartdevicelink.api.FileManager}
 */
public class FileManagerTests extends AndroidTestCase {
	public static final String TAG = "FileManagerTests";
	private FileManager fileManager;
	private Context mTestContext;
	private SdlFile validFile;

	protected class BaseISdl implements ISdl{
		@Override
		public void start() {

		}

		@Override
		public void stop() {

		}

		@Override
		public boolean isConnected() {
			return false;
		}

		@Override
		public void addServiceListener(SessionType serviceType, ISdlServiceListener sdlServiceListener) {

		}

		@Override
		public void removeServiceListener(SessionType serviceType, ISdlServiceListener sdlServiceListener) {

		}

		@Override
		public void startVideoService(VideoStreamingParameters parameters, boolean encrypted) {

		}

		@Override
		public void stopVideoService() {

		}

		@Override
		public IVideoStreamListener startVideoStream(boolean isEncrypted, VideoStreamingParameters parameters) {
			return null;
		}

		@Override
		public void startAudioService(boolean encrypted, AudioStreamingCodec codec, AudioStreamingParams params) {

		}

		@Override
		public void startAudioService(boolean encrypted) {

		}

		@Override
		public void stopAudioService() {

		}

		@Override
		public IAudioStreamListener startAudioStream(boolean isEncrypted, AudioStreamingCodec codec, AudioStreamingParams params) {
			return null;
		}

		@Override
		public void sendRPCRequest(RPCRequest message) {
			// Allow for successful initialization by default
			if(message instanceof ListFiles){
				int correlationId = message.getCorrelationID();
				ListFilesResponse listFilesResponse = new ListFilesResponse();
				listFilesResponse.setFilenames(Test.GENERAL_STRING_LIST);
				listFilesResponse.setSpaceAvailable(Test.GENERAL_INT);
				listFilesResponse.setSuccess(true);
				message.getOnRPCResponseListener().onResponse(correlationId, listFilesResponse);
			}
		}

		@Override
		public void sendRequests(List<? extends RPCRequest> rpcs, OnMultipleRequestListener listener) {

		}

		@Override
		public void addOnRPCNotificationListener(FunctionID notificationId, OnRPCNotificationListener listener) {

		}

		@Override
		public boolean removeOnRPCNotificationListener(FunctionID notificationId, OnRPCNotificationListener listener) {
			return false;
		}

		@Override
		public Object getCapability(SystemCapabilityType systemCapabilityType) {
			return null;
		}

		@Override
		public void getCapability(SystemCapabilityType systemCapabilityType, OnSystemCapabilityListener scListener) {

		}

		@Override
		public boolean isCapabilitySupported(SystemCapabilityType systemCapabilityType) {
			return false;
		}

		@Override
		public SdlMsgVersion getSdlMsgVersion() {
			return null;
		}
	};

	@Override
	public void setUp() throws Exception{
		super.setUp();
		mTestContext = this.getContext();
		validFile = new SdlFile();
		validFile.setName(Test.GENERAL_STRING);
		validFile.setFileData(Test.GENERAL_BYTE_ARRAY);
	}

	@Override
	public void tearDown() throws Exception {
		super.tearDown();
	}

	// SETUP / HELPERS


	// TESTS

	public void testInitializationSuccess(){
		FileManager fileManager = new FileManager(new BaseISdl(), mTestContext);
		while(fileManager.getState() == BaseSubManager.SETTING_UP);
		assertEquals(fileManager.getState(), BaseSubManager.READY);
		assertEquals(fileManager.getRemoteFileNames(), Test.GENERAL_STRING_LIST);
	}

	public void testInitializationFailure(){
		class TestISdl extends BaseISdl{
			@Override
			public void sendRPCRequest(RPCRequest message) {
				if(message instanceof ListFiles){
					int correlationId = message.getCorrelationID();
					ListFilesResponse listFilesResponse = new ListFilesResponse();
					listFilesResponse.setSuccess(false);
					message.getOnRPCResponseListener().onResponse(correlationId, listFilesResponse);
				}
			}
		}

		FileManager fileManager = new FileManager(new TestISdl(), mTestContext);
		while(fileManager.getState() == BaseSubManager.SETTING_UP);
		assertEquals(fileManager.getState(), BaseSubManager.SHUTDOWN);
	}

	public void testFileUploadSuccess(){
		class TestISdl extends BaseISdl{
			@Override
			public void sendRPCRequest(RPCRequest message) {
				super.sendRPCRequest(message);
				if(message instanceof PutFile){
					int correlationId = message.getCorrelationID();
					PutFileResponse putFileResponse = new PutFileResponse();
					putFileResponse.setSuccess(true);
					message.getOnRPCResponseListener().onResponse(correlationId, putFileResponse);
				}
			}
		}

		FileManager fileManager = new FileManager(new TestISdl(), mTestContext);
		while(fileManager.getState() == BaseSubManager.SETTING_UP);

		fileManager.uploadFile(validFile, new CompletionListener() {
			@Override
			public void onComplete(boolean success) {
				assertTrue(success);
			}
		});

		assertTrue(fileManager.getRemoteFileNames().contains(validFile.getName()));
	}

	public void testFileUploadFailure(){
		class TestISdl extends BaseISdl{
			@Override
			public void sendRPCRequest(RPCRequest message) {
				super.sendRPCRequest(message);
				if(message instanceof PutFile){
					int correlationId = message.getCorrelationID();
					PutFileResponse putFileResponse = new PutFileResponse();
					putFileResponse.setSuccess(false);
					message.getOnRPCResponseListener().onResponse(correlationId, putFileResponse);
				}
			}
		}

		FileManager fileManager = new FileManager(new BaseISdl(), mTestContext);
		while(fileManager.getState() == BaseSubManager.SETTING_UP);

		fileManager.uploadFile(validFile, new CompletionListener() {
			@Override
			public void onComplete(boolean success) {
				assertTrue(!success);
			}
		});

		assertTrue(!fileManager.getRemoteFileNames().contains(validFile.getName()));
	}

	public void testInvalidSdlFileInput(){
		FileManager fileManager = new FileManager(new BaseISdl(), mTestContext);
		while(fileManager.getState() == BaseSubManager.SETTING_UP);

		SdlFile sdlFile = new SdlFile();
		// Don't set name
		sdlFile.setFileData(Test.GENERAL_BYTE_ARRAY);
		checkForUploadFailure(fileManager, sdlFile);

		sdlFile = new SdlFile();
		sdlFile.setName(Test.GENERAL_STRING);
		// Don't set data
		checkForUploadFailure(fileManager, sdlFile);

		sdlFile = new SdlFile();
		sdlFile.setName(Test.GENERAL_STRING);
		// Give an invalid resource ID
		sdlFile.setResourceId(Test.GENERAL_INT);
		checkForUploadFailure(fileManager, sdlFile);

		sdlFile = new SdlFile();
		sdlFile.setName(Test.GENERAL_STRING);
		// Set invalid Uri
		Uri testUri = Uri.parse("http://www.google.com");
		sdlFile.setUri(testUri);
		checkForUploadFailure(fileManager, sdlFile);
	}

	private void checkForUploadFailure(FileManager fileManager, SdlFile sdlFile){
		boolean error = false;

		try {
			fileManager.uploadFile(sdlFile, new CompletionListener() {
				@Override
				public void onComplete(boolean success) {}
			});
		}catch (IllegalArgumentException e){
			error = true;
		}

		assertTrue(error);
	}

	public void testInvalidSdlArtworkInput(){
		SdlArtwork sdlArtwork = new SdlArtwork();
		// Set invalid type
		for(FileType fileType : FileType.values()){
			boolean shouldError = true, didError = false;
			if(fileType.equals(FileType.GRAPHIC_BMP) || fileType.equals(FileType.GRAPHIC_PNG)
				|| fileType.equals(FileType.GRAPHIC_JPEG)){
				shouldError = false;
			}
			try{
				sdlArtwork.setType(fileType);
			}catch(IllegalArgumentException e){
				didError = true;
			}
			assertEquals(shouldError, didError);
		}
	}

	public void testMultipleFileUploadSuccess(){
		class TestISdl extends BaseISdl{
			@Override
			public void sendRequests(List<? extends RPCRequest> rpcs, OnMultipleRequestListener listener) {
				super.sendRequests(rpcs, listener);
				if(rpcs.get(0) instanceof PutFile){
					for(RPCRequest message : rpcs){
						int correlationId = message.getCorrelationID();
						listener.addCorrelationId(correlationId);
						PutFileResponse putFileResponse = new PutFileResponse();
						putFileResponse.setSuccess(true);
						listener.onResponse(correlationId, putFileResponse);
					}
				}
			}
		}

		FileManager fileManager = new FileManager(new TestISdl(), mTestContext);
		while(fileManager.getState() == BaseSubManager.SETTING_UP);

		int fileNum = 1;
		List<SdlFile> filesToUpload = new ArrayList<>();
		SdlFile sdlFile = new SdlFile();
		sdlFile.setName("file" + fileNum++);
		Uri uri = Uri.parse("android.resource://" + mTestContext.getPackageName() + "/drawable/ic_sdl");
		sdlFile.setUri(uri);
		filesToUpload.add(sdlFile);

		sdlFile = new SdlFile();
		sdlFile.setName("file" + fileNum++);
		sdlFile.setResourceId(com.smartdevicelink.test.R.drawable.ic_sdl);
		filesToUpload.add(sdlFile);

		sdlFile = new SdlFile();
		sdlFile.setName("file" + fileNum++);
		sdlFile.setFileData(Test.GENERAL_BYTE_ARRAY);
		sdlFile.setPersistent(true);
		sdlFile.setType(FileType.BINARY);
		filesToUpload.add(sdlFile);

		fileManager.uploadFiles(filesToUpload,
				new MultipleFileCompletionListener() {
					@Override
					public void onComplete(Map<String, String> errors) {
						assertNull(errors);
					}
				});

		List < String > uploadedFileNames = fileManager.getRemoteFileNames();
		for(SdlFile file : filesToUpload){
			assertTrue(uploadedFileNames.contains(file.getName()));
		}
	}

	public void testMultipleFileUploadPartialFailure(){
		final String failureReason = "No space available";

		class TestISdl extends BaseISdl{
			private int responseNum = 0;

			@Override
			public void sendRequests(List<? extends RPCRequest> rpcs, OnMultipleRequestListener listener) {
				if(rpcs.get(0) instanceof PutFile){
					for(RPCRequest message : rpcs){
						int correlationId = message.getCorrelationID();
						listener.addCorrelationId(correlationId);
						PutFileResponse putFileResponse = new PutFileResponse();
						if(responseNum++ % 2 == 0){
							listener.onError(correlationId, Result.OUT_OF_MEMORY, failureReason);
						}else{
							putFileResponse.setSuccess(true);
							listener.onResponse(correlationId, putFileResponse);
						}
					}
				}
			}
		}

		FileManager fileManager = new FileManager(new TestISdl(), mTestContext);
		while(fileManager.getState() == BaseSubManager.SETTING_UP);

		final String baseFileName = "file";
		int fileNum = 0;
		final List<SdlFile> filesToUpload = new ArrayList<>();
		SdlFile sdlFile = new SdlFile();
		sdlFile.setName(baseFileName + fileNum++);
		Uri uri = Uri.parse("android.resource://" + mTestContext.getPackageName() + "/drawable/ic_sdl");
		sdlFile.setUri(uri);
		filesToUpload.add(sdlFile);

		sdlFile = new SdlFile();
		sdlFile.setName(baseFileName + fileNum++);
		sdlFile.setResourceId(com.smartdevicelink.test.R.drawable.ic_sdl);
		filesToUpload.add(sdlFile);

		sdlFile = new SdlFile();
		sdlFile.setName(baseFileName + fileNum++);
		sdlFile.setFileData(Test.GENERAL_BYTE_ARRAY);
		sdlFile.setPersistent(true);
		sdlFile.setType(FileType.BINARY);
		filesToUpload.add(sdlFile);

		fileManager.uploadFiles(filesToUpload,
				new MultipleFileCompletionListener() {
					@Override
					public void onComplete(Map<String, String> errors) {
						assertNotNull(errors);
						for(int i = 0; i < filesToUpload.size(); i++){
							if(i % 2 == 0){
								assertTrue(errors.containsKey(filesToUpload.get(i).getName()));
								assertEquals(FileManager.buildErrorString(Result.OUT_OF_MEMORY,
										failureReason), errors.get(filesToUpload.get(i).getName()));
							}else{
								assertFalse(errors.containsKey(filesToUpload.get(i).getName()));
							}
						}
					}
				});

		List <String> uploadedFileNames = fileManager.getRemoteFileNames();
		for(int i = 0; i < filesToUpload.size(); i++){
			if(i % 2 == 0){
				assertFalse(uploadedFileNames.contains(filesToUpload.get(i).getName()));
			}else{
				assertTrue(uploadedFileNames.contains(filesToUpload.get(i).getName()));
			}
		}
	}

	public void testMultipleArtworkUploadSuccess(){
		class TestISdl extends BaseISdl{
			@Override
			public void sendRequests(List<? extends RPCRequest> rpcs, OnMultipleRequestListener listener) {
				super.sendRequests(rpcs, listener);
				if(rpcs.get(0) instanceof PutFile){
					for(RPCRequest message : rpcs){
						int correlationId = message.getCorrelationID();
						listener.addCorrelationId(correlationId);
						PutFileResponse putFileResponse = new PutFileResponse();
						putFileResponse.setSuccess(true);
						listener.onResponse(correlationId, putFileResponse);
					}
				}
			}
		}

		FileManager fileManager = new FileManager(new TestISdl(), mTestContext);
		while(fileManager.getState() == BaseSubManager.SETTING_UP);

		int fileNum = 1;
		List<SdlArtwork> artworkToUpload = new ArrayList<>();
		SdlArtwork sdlArtwork = new SdlArtwork();
		sdlArtwork.setName("art" + fileNum++);
		Uri uri = Uri.parse("android.resource://" + mTestContext.getPackageName() + "/drawable/ic_sdl");
		sdlArtwork.setUri(uri);
		sdlArtwork.setType(FileType.GRAPHIC_PNG);
		artworkToUpload.add(sdlArtwork);

		sdlArtwork = new SdlArtwork();
		sdlArtwork.setName("art" + fileNum++);
		uri = Uri.parse("android.resource://" + mTestContext.getPackageName() + "/drawable/sdl_tray_icon");
		sdlArtwork.setUri(uri);
		sdlArtwork.setType(FileType.GRAPHIC_PNG);
		artworkToUpload.add(sdlArtwork);

		fileManager.uploadFiles(artworkToUpload,
				new MultipleFileCompletionListener() {
					@Override
					public void onComplete(Map<String, String> errors) {
						assertNull(errors);
					}
				});

		List < String > uploadedFileNames = fileManager.getRemoteFileNames();
		for(SdlArtwork artwork : artworkToUpload){
			assertTrue(uploadedFileNames.contains(artwork.getName()));
		}
	}
}