package com.smartdevicelink.managers.file;

import android.content.Context;
import android.net.Uri;

import com.smartdevicelink.AndroidTestCase2;
import com.smartdevicelink.managers.BaseSubManager;
import com.smartdevicelink.managers.CompletionListener;
import com.smartdevicelink.managers.file.filetypes.SdlArtwork;
import com.smartdevicelink.managers.file.filetypes.SdlFile;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.interfaces.ISdl;
import com.smartdevicelink.proxy.rpc.ListFiles;
import com.smartdevicelink.proxy.rpc.ListFilesResponse;
import com.smartdevicelink.proxy.rpc.PutFile;
import com.smartdevicelink.proxy.rpc.PutFileResponse;
import com.smartdevicelink.proxy.rpc.enums.FileType;
import com.smartdevicelink.proxy.rpc.enums.Result;
import com.smartdevicelink.proxy.rpc.enums.StaticIconName;
import com.smartdevicelink.proxy.rpc.listeners.OnMultipleRequestListener;
import com.smartdevicelink.test.Test;

import junit.framework.Assert;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

/**
 * This is a unit test class for the SmartDeviceLink library manager class :
 * {@link FileManager}
 */
public class FileManagerTests extends AndroidTestCase2 {
	public static final String TAG = "FileManagerTests";
	private FileManager fileManager;
	private Context mTestContext;
	private SdlFile validFile;

	// SETUP / HELPERS

	@Override
	public void setUp() throws Exception{
		super.setUp();
		mTestContext = this.getContext();
		validFile = new SdlFile();
		validFile.setName(Test.GENERAL_STRING);
		validFile.setFileData(Test.GENERAL_BYTE_ARRAY);
		validFile.setPersistent(false);
	}

	@Override
	public void tearDown() throws Exception {
		super.tearDown();
	}

	private Answer<Void> onListFilesSuccess = new Answer<Void>() {
		@Override
		public Void answer(InvocationOnMock invocation) {
			Object[] args = invocation.getArguments();
			RPCRequest message = (RPCRequest) args[0];
			if(message instanceof ListFiles){
				int correlationId = message.getCorrelationID();
				ListFilesResponse listFilesResponse = new ListFilesResponse();
				listFilesResponse.setFilenames(Test.GENERAL_STRING_LIST);
				listFilesResponse.setSpaceAvailable(Test.GENERAL_INT);
				listFilesResponse.setSuccess(true);
				message.getOnRPCResponseListener().onResponse(correlationId, listFilesResponse);
			}
			return null;
		}
	};

	private Answer<Void> onListFilesFailure = new Answer<Void>() {
		@Override
		public Void answer(InvocationOnMock invocation) {
			Object[] args = invocation.getArguments();
			RPCRequest message = (RPCRequest) args[0];
			if(message instanceof ListFiles){
				int correlationId = message.getCorrelationID();
				ListFilesResponse listFilesResponse = new ListFilesResponse();
				listFilesResponse.setSuccess(false);
				message.getOnRPCResponseListener().onResponse(correlationId, listFilesResponse);
			}
			return null;
		}
	};

	private Answer<Void> onPutFileSuccess = new Answer<Void>() {
		@Override
		public Void answer(InvocationOnMock invocation) {
			Object[] args = invocation.getArguments();
			RPCRequest message = (RPCRequest) args[0];
			if(message instanceof PutFile){
				int correlationId = message.getCorrelationID();
				PutFileResponse putFileResponse = new PutFileResponse();
				putFileResponse.setSuccess(true);
				message.getOnRPCResponseListener().onResponse(correlationId, putFileResponse);
			}
			return null;
		}
	};

	private Answer<Void> onPutFileFailure = new Answer<Void>() {
		@Override
		public Void answer(InvocationOnMock invocation) {
			Object[] args = invocation.getArguments();
			RPCRequest message = (RPCRequest) args[0];
			if(message instanceof PutFile){
				int correlationId = message.getCorrelationID();
				PutFileResponse putFileResponse = new PutFileResponse();
				putFileResponse.setSuccess(false);
				message.getOnRPCResponseListener().onResponse(correlationId, putFileResponse);
			}
			return null;
		}
	};

	private Answer<Void> onSendRequestsSuccess = new Answer<Void>() {
		@Override
		public Void answer(InvocationOnMock invocation) {
			Object[] args = invocation.getArguments();
			List<RPCRequest> rpcs = (List<RPCRequest>) args[0];
			OnMultipleRequestListener listener = (OnMultipleRequestListener) args[1];
			if(rpcs.get(0) instanceof PutFile){
				for(RPCRequest message : rpcs){
					int correlationId = message.getCorrelationID();
					listener.addCorrelationId(correlationId);
					PutFileResponse putFileResponse = new PutFileResponse();
					putFileResponse.setSuccess(true);
					listener.onResponse(correlationId, putFileResponse);
				}
			}
			return null;
		}
	};

	// TESTS

	public void testInitializationSuccess(){
		ISdl internalInterface = mock(ISdl.class);

		doAnswer(onListFilesSuccess).when(internalInterface).sendRPCRequest(any(ListFiles.class));

		final FileManager fileManager = new FileManager(internalInterface, mTestContext);
		fileManager.start(new CompletionListener() {
			@Override
			public void onComplete(boolean success) {
				assertTrue(success);
				Assert.assertEquals(fileManager.getState(), BaseSubManager.READY);
				assertEquals(fileManager.getRemoteFileNames(), Test.GENERAL_STRING_LIST);
			}
		});
	}

	public void testInitializationFailure(){
		ISdl internalInterface = mock(ISdl.class);

		doAnswer(onListFilesFailure).when(internalInterface).sendRPCRequest(any(ListFiles.class));

		final FileManager fileManager = new FileManager(internalInterface, mTestContext);
		fileManager.start(new CompletionListener() {
			@Override
			public void onComplete(boolean success) {
				assertFalse(success);
				assertEquals(fileManager.getState(), BaseSubManager.ERROR);
			}
		});
	}

	public void testFileUploadSuccess(){
		ISdl internalInterface = mock(ISdl.class);

		doAnswer(onListFilesSuccess).when(internalInterface).sendRPCRequest(any(ListFiles.class));
		doAnswer(onPutFileSuccess).when(internalInterface).sendRPCRequest(any(PutFile.class));

		final FileManager fileManager = new FileManager(internalInterface, mTestContext);
		fileManager.start(new CompletionListener() {
			@Override
			public void onComplete(boolean success) {
				assertTrue(success);
				fileManager.uploadFile(validFile, new CompletionListener() {
					@Override
					public void onComplete(boolean success) {
						assertTrue(success);
						assertTrue(fileManager.getRemoteFileNames().contains(validFile.getName()));
						assertTrue(fileManager.hasUploadedFile(validFile));
					}
				});
			}
		});
	}

	public void testFileUploadFailure(){
		ISdl internalInterface = mock(ISdl.class);

		doAnswer(onListFilesSuccess).when(internalInterface).sendRPCRequest(any(ListFiles.class));
		doAnswer(onPutFileFailure).when(internalInterface).sendRPCRequest(any(PutFile.class));

		final FileManager fileManager = new FileManager(internalInterface, mTestContext);
		fileManager.start(new CompletionListener() {
			@Override
			public void onComplete(boolean success) {
				assertTrue(success);
				fileManager.uploadFile(validFile, new CompletionListener() {
					@Override
					public void onComplete(boolean success) {
						assertFalse(success);
						assertFalse(fileManager.getRemoteFileNames().contains(validFile.getName()));
						assertFalse(fileManager.hasUploadedFile(validFile));
					}
				});
			}
		});
	}

	public void testFileUploadForStaticIcon(){
		ISdl internalInterface = mock(ISdl.class);

		doAnswer(onListFilesSuccess).when(internalInterface).sendRPCRequest(any(ListFiles.class));

		final FileManager fileManager = new FileManager(internalInterface, mTestContext);
		fileManager.start(new CompletionListener() {
			@Override
			public void onComplete(boolean success) {
				assertTrue(success);
				SdlArtwork artwork = new SdlArtwork(StaticIconName.ALBUM);
				fileManager.uploadFile(artwork, new CompletionListener() {
					@Override
					public void onComplete(boolean success) {
						assertTrue(success);
					}
				});
			}
		});
	}

	public void testInvalidSdlFileInput(){
		ISdl internalInterface = mock(ISdl.class);

		doAnswer(onListFilesSuccess).when(internalInterface).sendRPCRequest(any(ListFiles.class));

		final FileManager fileManager = new FileManager(internalInterface, mTestContext);
		fileManager.start(new CompletionListener() {
			@Override
			public void onComplete(boolean success) {
				assertTrue(success);
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
		});
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

	public void testMultipleFileUploadThenDeleteSuccess(){
		ISdl internalInterface = mock(ISdl.class);

		doAnswer(onListFilesSuccess).when(internalInterface).sendRPCRequest(any(ListFiles.class));
		doAnswer(onSendRequestsSuccess).when(internalInterface).sendRequests(any(List.class), any(OnMultipleRequestListener.class));

		final FileManager fileManager = new FileManager(internalInterface, mTestContext);
		fileManager.start(new CompletionListener() {
			@Override
			public void onComplete(boolean success) {
				assertTrue(success);
				int fileNum = 1;
				final List<SdlFile> filesToUpload = new ArrayList<>();
				SdlFile sdlFile = new SdlFile();
				sdlFile.setName("file" + fileNum++);
				Uri uri = Uri.parse("android.resource://" + mTestContext.getPackageName() + "/drawable/ic_sdl");
				sdlFile.setUri(uri);
				filesToUpload.add(sdlFile);

				sdlFile = new SdlFile();
				sdlFile.setName("file" + fileNum++);
				sdlFile.setResourceId(com.smartdevicelink.test.R.drawable.ic_sdl);
				filesToUpload.add(sdlFile);

				fileManager.uploadFiles(filesToUpload,
						new MultipleFileCompletionListener() {
							@Override
							public void onComplete(Map<String, String> errors) {
								assertNull(errors);
								List <String> uploadedFileNames = fileManager.getRemoteFileNames();
								for(SdlFile file : filesToUpload){
									assertTrue(uploadedFileNames.contains(file.getName()));
								}
								fileManager.deleteRemoteFilesWithNames(uploadedFileNames, new MultipleFileCompletionListener() {
									@Override
									public void onComplete(Map<String, String> errors) {
										assertNull(errors);
										List <String> uploadedFileNames = fileManager.getRemoteFileNames();
										for(SdlFile file : filesToUpload){
											assertFalse(uploadedFileNames.contains(file.getName()));
										}
									}
								});
							}
						});
			}
		});
	}

	public void testMultipleFileUploadPartialFailure(){
		final String failureReason = "No space available";

		ISdl internalInterface = mock(ISdl.class);

		doAnswer(onListFilesSuccess).when(internalInterface).sendRPCRequest(any(ListFiles.class));

		Answer<Void> onSendRequestsFailure = new Answer<Void>() {
			private int responseNum = 0;
			@Override
			public Void answer(InvocationOnMock invocation) {
				Object[] args = invocation.getArguments();
				List<RPCRequest> rpcs = (List<RPCRequest>) args[0];
				OnMultipleRequestListener listener = (OnMultipleRequestListener) args[1];
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
				return null;
			}
		};
		doAnswer(onSendRequestsFailure).when(internalInterface).sendRequests(any(List.class), any(OnMultipleRequestListener.class));

		final FileManager fileManager = new FileManager(internalInterface, mTestContext);
		fileManager.start(new CompletionListener() {
			@Override
			public void onComplete(boolean success) {
				assertTrue(success);
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
								List <String> uploadedFileNames = fileManager.getRemoteFileNames();
								for(int i = 0; i < filesToUpload.size(); i++){
									if(i % 2 == 0){
										assertFalse(uploadedFileNames.contains(filesToUpload.get(i).getName()));
									}else{
										assertTrue(uploadedFileNames.contains(filesToUpload.get(i).getName()));
									}
								}
							}
						});
			}
		});
	}

	public void testMultipleArtworkUploadSuccess(){
		ISdl internalInterface = mock(ISdl.class);

		doAnswer(onListFilesSuccess).when(internalInterface).sendRPCRequest(any(ListFiles.class));
		doAnswer(onSendRequestsSuccess).when(internalInterface).sendRequests(any(List.class), any(OnMultipleRequestListener.class));

		final FileManager fileManager = new FileManager(internalInterface, mTestContext);
		fileManager.start(new CompletionListener() {
			@Override
			public void onComplete(boolean success) {
				assertTrue(success);
				int fileNum = 1;
				final List<SdlArtwork> artworkToUpload = new ArrayList<>();
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
								List < String > uploadedFileNames = fileManager.getRemoteFileNames();
								for(SdlArtwork artwork : artworkToUpload){
									assertTrue(uploadedFileNames.contains(artwork.getName()));
								}
							}
						});
			}
		});
	}

	public void testPersistentFileUploaded(){
		ISdl internalInterface = mock(ISdl.class);

		doAnswer(onListFilesSuccess).when(internalInterface).sendRPCRequest(any(ListFiles.class));

		final SdlFile file = new SdlFile();
		file.setName(Test.GENERAL_STRING_LIST.get(0));
		file.setPersistent(true);

		final FileManager fileManager = new FileManager(internalInterface, mTestContext);
		fileManager.start(new CompletionListener() {
			@Override
			public void onComplete(boolean success) {
				assertTrue(fileManager.hasUploadedFile(file));
			}
		});
	}
}