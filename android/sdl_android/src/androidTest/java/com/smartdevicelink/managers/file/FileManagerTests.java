package com.smartdevicelink.managers.file;

import android.content.Context;
import android.net.Uri;
import android.support.test.runner.AndroidJUnit4;

import com.smartdevicelink.managers.BaseSubManager;
import com.smartdevicelink.managers.CompletionListener;
import com.smartdevicelink.managers.file.filetypes.SdlArtwork;
import com.smartdevicelink.managers.file.filetypes.SdlFile;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.interfaces.ISdl;
import com.smartdevicelink.proxy.rpc.DeleteFile;
import com.smartdevicelink.proxy.rpc.DeleteFileResponse;
import com.smartdevicelink.proxy.rpc.ListFiles;
import com.smartdevicelink.proxy.rpc.ListFilesResponse;
import com.smartdevicelink.proxy.rpc.PutFile;
import com.smartdevicelink.proxy.rpc.PutFileResponse;
import com.smartdevicelink.proxy.rpc.enums.FileType;
import com.smartdevicelink.proxy.rpc.enums.Result;
import com.smartdevicelink.proxy.rpc.enums.StaticIconName;
import com.smartdevicelink.proxy.rpc.listeners.OnMultipleRequestListener;
import com.smartdevicelink.test.TestValues;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static android.support.test.InstrumentationRegistry.getContext;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * This is a unit test class for the SmartDeviceLink library manager class :
 * {@link FileManager}
 */
@RunWith(AndroidJUnit4.class)
public class FileManagerTests {
	public static final String TAG = "FileManagerTests";
	private Context mTestContext;
	private SdlFile validFile;

	// SETUP / HELPERS

	@Before
	public void setUp() throws Exception{
		mTestContext = getContext();
		validFile = new SdlFile();
		validFile.setName(TestValues.GENERAL_STRING);
		validFile.setFileData(TestValues.GENERAL_BYTE_ARRAY);
		validFile.setPersistent(false);
	}

	private Answer<Void> onPutFileFailureOnError = new Answer<Void>() {
		@Override
		public Void answer(InvocationOnMock invocation) throws Throwable {
			Object[] args = invocation.getArguments();
			RPCRequest message = (RPCRequest) args[0];
			if (message instanceof PutFile) {
				int correlationId = message.getCorrelationID();
				Result resultCode = Result.REJECTED;
				PutFileResponse putFileResponse = new PutFileResponse();
				putFileResponse.setSuccess(false);
				message.getOnRPCResponseListener().onError(correlationId, resultCode, "Binary data empty");
			}
			return null;
		}
	};

	private Answer<Void> onSendRequestsFailOnError = new Answer<Void>() {
		@Override
		public Void answer(InvocationOnMock invocation) throws Throwable {
			Object[] args = invocation.getArguments();
			List<RPCRequest> rpcs = (List<RPCRequest>) args[0];
			OnMultipleRequestListener listener = (OnMultipleRequestListener) args[1];
			if (rpcs.get(0) instanceof PutFile) {
				Result resultCode = Result.REJECTED;
				for (RPCRequest message : rpcs) {
					int correlationId = message.getCorrelationID();
					listener.addCorrelationId(correlationId);
					PutFileResponse putFileResponse = new PutFileResponse();
					putFileResponse.setSuccess(true);
					listener.onError(correlationId, resultCode, "Binary data empty");
				}
				listener.onFinished();
			}
			return null;
		}
	};

	private Answer<Void> onListFileUploadSuccess = new Answer<Void>() {
		@Override
		public Void answer(InvocationOnMock invocation) throws Throwable {
			Object[] args = invocation.getArguments();
			List<RPCRequest> rpcs = (List<RPCRequest>) args[0];
			OnMultipleRequestListener listener = (OnMultipleRequestListener) args[1];
			if (rpcs.get(0) instanceof PutFile) {
				for (RPCRequest message : rpcs) {
					int correlationId = message.getCorrelationID();
					listener.addCorrelationId(correlationId);
					PutFileResponse putFileResponse = new PutFileResponse();
					putFileResponse.setSuccess(true);
					listener.onResponse(correlationId, putFileResponse);
				}
				listener.onFinished();
			}
			return null;
		}
	};

	private Answer<Void> onListFilesSuccess = new Answer<Void>() {
		@Override
		public Void answer(InvocationOnMock invocation) {
			Object[] args = invocation.getArguments();
			RPCRequest message = (RPCRequest) args[0];
			if(message instanceof ListFiles){
				int correlationId = message.getCorrelationID();
				ListFilesResponse listFilesResponse = new ListFilesResponse();
				listFilesResponse.setFilenames(TestValues.GENERAL_STRING_LIST);
				listFilesResponse.setSpaceAvailable(TestValues.GENERAL_INT);
				listFilesResponse.setSuccess(true);
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
				putFileResponse.setSpaceAvailable(TestValues.GENERAL_INT);
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

	private Answer<Void> onListDeleteRequestSuccess = new Answer<Void>() {
		@Override
		public Void answer(InvocationOnMock invocation) {
			Object[] args = invocation.getArguments();
			List<RPCRequest> rpcs = (List<RPCRequest>) args[0];
			OnMultipleRequestListener listener = (OnMultipleRequestListener) args[1];
			if (rpcs.get(0) instanceof DeleteFile) {
				for (RPCRequest message : rpcs) {
					int correlationId = message.getCorrelationID();
					listener.addCorrelationId(correlationId);
					DeleteFileResponse deleteFileResponse = new DeleteFileResponse();
					deleteFileResponse.setSuccess(true);
					listener.onResponse(correlationId, deleteFileResponse);
				}
				listener.onFinished();
			}
			return null;
		}
	};

	private Answer<Void> onListDeleteRequestFail = new Answer<Void>() {
		@Override
		public Void answer(InvocationOnMock invocation) {
			Object[] args = invocation.getArguments();
			List<RPCRequest> rpcs = (List<RPCRequest>) args[0];
			OnMultipleRequestListener listener = (OnMultipleRequestListener) args[1];
			if (rpcs.get(0) instanceof DeleteFile) {
				Result resultCode = Result.REJECTED;
				for (RPCRequest message : rpcs) {
					int correlationId = message.getCorrelationID();
					listener.addCorrelationId(correlationId);
					DeleteFileResponse deleteFileResponse = new DeleteFileResponse();
					deleteFileResponse.setSuccess(true);
					listener.onError(correlationId, resultCode, "Binary data empty");
				}
				listener.onFinished();
			}
			return null;
		}
	};

	private Answer<Void> onSendRequestsFailPartialOnError = new Answer<Void>() {
		@Override
		public Void answer(InvocationOnMock invocation) throws Throwable {
			Object[] args = invocation.getArguments();
			List<RPCRequest> rpcs = (List<RPCRequest>) args[0];
			OnMultipleRequestListener listener = (OnMultipleRequestListener) args[1];
			if (rpcs.get(0) instanceof PutFile) {
				Result resultCode = Result.REJECTED;
				boolean flip = false;
				for (RPCRequest message : rpcs) {
					int correlationId = message.getCorrelationID();
					listener.addCorrelationId(correlationId);
					PutFileResponse putFileResponse = new PutFileResponse();
					if (flip) {
						putFileResponse.setSuccess(true);
						flip = false;
						listener.onResponse(correlationId, putFileResponse);
					} else {
						flip = true;
						putFileResponse.setSuccess(false);
						listener.onError(correlationId, resultCode, "Binary data empty");
					}
				}
				listener.onFinished();
			}
			return null;
		}
	};

	// TESTS

	/**
	 * Test deleting list of files, success
	 */
	@Test
	public void testDeleteRemoteFilesWithNamesSuccess(){
		final ISdl internalInterface = mock(ISdl.class);

		doAnswer(onListFilesSuccess).when(internalInterface).sendRPC(any(ListFiles.class));
		doAnswer(onListDeleteRequestSuccess).when(internalInterface).sendRequests(any(List.class), any(OnMultipleRequestListener.class));

		final List<String> fileNames = new ArrayList<>();
		fileNames.add("Julian");
		fileNames.add("Jake");

		FileManagerConfig fileManagerConfig = new FileManagerConfig();
		fileManagerConfig.setFileRetryCount(2);

		final FileManager fileManager = new FileManager(internalInterface,mTestContext,fileManagerConfig);
		fileManager.start(new CompletionListener() {
			@Override
			public void onComplete(boolean success) {
				assertTrue(success);
				fileManager.deleteRemoteFilesWithNames(fileNames, new MultipleFileCompletionListener() {
					@Override
					public void onComplete(Map<String, String> errors) {
						assertTrue(errors == null);
					}
				});
			}
		});
	}

	/**
	 * Test deleting list of files, fail
	 */
	@Test
	public void testDeleteRemoteFilesWithNamesFail(){
		final ISdl internalInterface = mock(ISdl.class);

		doAnswer(onListFilesSuccess).when(internalInterface).sendRPC(any(ListFiles.class));
		doAnswer(onListDeleteRequestFail).when(internalInterface).sendRequests(any(List.class), any(OnMultipleRequestListener.class));

		final List<String> fileNames = new ArrayList<>();
		fileNames.add("Julian");
		fileNames.add("Jake");

		FileManagerConfig fileManagerConfig = new FileManagerConfig();
		fileManagerConfig.setFileRetryCount(2);

		final FileManager fileManager = new FileManager(internalInterface,mTestContext,fileManagerConfig);
		fileManager.start(new CompletionListener() {
			@Override
			public void onComplete(boolean success) {
				assertTrue(success);
				fileManager.deleteRemoteFilesWithNames(fileNames, new MultipleFileCompletionListener() {
					@Override
					public void onComplete(Map<String, String> errors) {
						assertTrue(errors.size() == 2);
					}
				});
			}
		});
	}

	/**
	 * Test reUploading failed file
	 */
	@Test
	public void testFileUploadRetry(){
		final ISdl internalInterface = mock(ISdl.class);

		doAnswer(onListFilesSuccess).when(internalInterface).sendRPC(any(ListFiles.class));
		doAnswer(onPutFileFailureOnError).when(internalInterface).sendRPC(any(PutFile.class));

		FileManagerConfig fileManagerConfig = new FileManagerConfig();
		fileManagerConfig.setFileRetryCount(2);

		validFile.setType(FileType.AUDIO_MP3);

		final FileManager fileManager = new FileManager(internalInterface, mTestContext,fileManagerConfig);

		fileManager.start(new CompletionListener() {
			@Override
			public void onComplete(boolean success) {
				assertTrue(success);
				fileManager.uploadFile(validFile, new CompletionListener() {
					@Override
					public void onComplete(boolean success) {
						assertFalse(success);
					}
				});
			}
		});
		verify(internalInterface, times(4)).sendRPC(any(RPCMessage.class));
	}

	/**
	 * Test reUploading failed Artwork
	 */
	@Test
	public void testArtworkUploadRetry(){
		final ISdl internalInterface = mock(ISdl.class);

		doAnswer(onListFilesSuccess).when(internalInterface).sendRPC(any(ListFiles.class));
		doAnswer(onPutFileFailureOnError).when(internalInterface).sendRPC(any(PutFile.class));

		final SdlFile validFile2 = new SdlFile();
		validFile2.setName(TestValues.GENERAL_STRING + "2");
		validFile2.setFileData(TestValues.GENERAL_BYTE_ARRAY);
		validFile2.setPersistent(false);
		validFile2.setType(FileType.GRAPHIC_PNG);

		final SdlFile validFile3 = new SdlFile();
		validFile3.setName(TestValues.GENERAL_STRING + "3");
		validFile3.setFileData(TestValues.GENERAL_BYTE_ARRAY);
		validFile3.setPersistent(false);
		validFile3.setType(FileType.GRAPHIC_BMP);

		validFile.setType(FileType.GRAPHIC_JPEG);

		FileManagerConfig fileManagerConfig = new FileManagerConfig();
		fileManagerConfig.setArtworkRetryCount(2);

		final FileManager fileManager = new FileManager(internalInterface, mTestContext,fileManagerConfig);
		fileManager.start(new CompletionListener() {
			@Override
			public void onComplete(boolean success) {
				assertTrue(success);
				fileManager.uploadFile(validFile, new CompletionListener() {
					@Override
					public void onComplete(boolean success) {
						assertFalse(success);
						verify(internalInterface, times(4)).sendRPC(any(RPCMessage.class));
					}
				});

				fileManager.uploadFile(validFile2, new CompletionListener() {
					@Override
					public void onComplete(boolean success) {
						assertFalse(success);
						verify(internalInterface, times(7)).sendRPC(any(RPCMessage.class));
					}
				});

				fileManager.uploadFile(validFile3, new CompletionListener() {
					@Override
					public void onComplete(boolean success) {
						assertFalse(success);
					}
				});
			}
		});
		verify(internalInterface, times(10)).sendRPC(any(RPCMessage.class));
	}

	/**
	 * Test retry uploading failed list of files
	 */
	@Test
	public void testListFilesUploadRetry(){
		final ISdl internalInterface = mock(ISdl.class);

		doAnswer(onListFilesSuccess).when(internalInterface).sendRPC(any(ListFiles.class));
		doAnswer(onSendRequestsFailOnError).when(internalInterface).sendRequests(any(List.class), any(OnMultipleRequestListener.class));

		SdlFile validFile2 = new SdlFile();
		validFile2.setName(TestValues.GENERAL_STRING + "2");
		validFile2.setFileData(TestValues.GENERAL_BYTE_ARRAY);
		validFile2.setPersistent(false);
		validFile2.setType(FileType.GRAPHIC_JPEG);

		validFile.setType(FileType.AUDIO_WAVE);

		final List<SdlFile> list = new ArrayList<>();
		list.add(validFile);
		list.add(validFile2);

		FileManagerConfig fileManagerConfig = new FileManagerConfig();
		fileManagerConfig.setArtworkRetryCount(2);
		fileManagerConfig.setFileRetryCount(4);

		final FileManager fileManager = new FileManager(internalInterface, mTestContext,fileManagerConfig);
		fileManager.start(new CompletionListener() {
			@Override
			public void onComplete(boolean success) {
				fileManager.uploadFiles(list, new MultipleFileCompletionListener() {
					@Override
					public void onComplete(Map<String, String> errors) {
						assertTrue(errors.size() == 2); // We need to make sure it kept track of both Files
					}
				});

			}
		});
		verify(internalInterface, times(5)).sendRequests(any(List.class),any(OnMultipleRequestListener.class));
	}

	/**
	 * Testing the initialization of FileManager
	 */
	@Test
	public void testInitializationSuccess() {
		ISdl internalInterface = mock(ISdl.class);

		doAnswer(onListFilesSuccess).when(internalInterface).sendRPC(any(ListFiles.class));

		FileManagerConfig fileManagerConfig = new FileManagerConfig();
		final FileManager fileManager = new FileManager(internalInterface, mTestContext, fileManagerConfig);
		fileManager.start(new CompletionListener() {
			@Override
			public void onComplete(boolean success) {
				assertTrue(success);
				assertEquals(fileManager.getState(), BaseSubManager.READY);
				assertEquals(fileManager.getRemoteFileNames(), TestValues.GENERAL_STRING_LIST);
				assertEquals(TestValues.GENERAL_INT, fileManager.getBytesAvailable());
			}
		});
	}

	/**
	 * Test file upload, success
	 */
	@Test
	public void testFileUploadSuccess() {
		ISdl internalInterface = mock(ISdl.class);

		doAnswer(onListFilesSuccess).when(internalInterface).sendRPC(any(ListFiles.class));
		doAnswer(onPutFileSuccess).when(internalInterface).sendRPC(any(PutFile.class));

		FileManagerConfig fileManagerConfig = new FileManagerConfig();

		final FileManager fileManager = new FileManager(internalInterface, mTestContext, fileManagerConfig);
		fileManager.start(new CompletionListener() {
			@Override
			public void onComplete(boolean success) {
				assertTrue(success);
				fileManager.uploadFile(validFile, new CompletionListener() {
					@Override
					public void onComplete(boolean success) {
						assertTrue(success);
					}
				});
			}
		});
		assertTrue(fileManager.getRemoteFileNames().contains(validFile.getName()));
		assertTrue(fileManager.hasUploadedFile(validFile));
		assertEquals(TestValues.GENERAL_INT, fileManager.getBytesAvailable());
	}

	/**
	 * Testing failed file upload.
	 */
	@Test
	public void testFileUploadFailure() {
		ISdl internalInterface = mock(ISdl.class);

		doAnswer(onListFilesSuccess).when(internalInterface).sendRPC(any(ListFiles.class));
		doAnswer(onPutFileFailure).when(internalInterface).sendRPC(any(PutFile.class));

		FileManagerConfig fileManagerConfig = new FileManagerConfig();
		final FileManager fileManager = new FileManager(internalInterface, mTestContext, fileManagerConfig);
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

	/**
	 * Testing uploadFile for a staticIcon, verifying that it doesn't actually upload.
	 */
	@Test
	public void testFileUploadForStaticIcon() {
		ISdl internalInterface = mock(ISdl.class);

		doAnswer(onListFilesSuccess).when(internalInterface).sendRPC(any(ListFiles.class));

		FileManagerConfig fileManagerConfig = new FileManagerConfig();
		final FileManager fileManager = new FileManager(internalInterface, mTestContext, fileManagerConfig);
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
		verify(internalInterface, times(1)).sendRPC(any(RPCMessage.class));
	}

	/**
	 * Testing uploadFiles for staticIcons, verifying that it doesn't actually upload.
	 */
	@Test
	public void testMultipleFileUploadsForStaticIcon() {
		ISdl internalInterface = mock(ISdl.class);

		doAnswer(onListFilesSuccess).when(internalInterface).sendRPC(any(ListFiles.class));
		doAnswer(onListFileUploadSuccess).when(internalInterface).sendRequests(any(List.class), any(OnMultipleRequestListener.class));

		FileManagerConfig fileManagerConfig = new FileManagerConfig();
		final FileManager fileManager = new FileManager(internalInterface, mTestContext, fileManagerConfig);
		fileManager.start(new CompletionListener() {
			@Override
			public void onComplete(boolean success) {
				assertTrue(success);
				SdlArtwork artwork = new SdlArtwork(StaticIconName.ALBUM);
				SdlArtwork artwork2 = new SdlArtwork(StaticIconName.FILENAME);
				List<SdlArtwork> testStaticIconUpload = new ArrayList<>();
				testStaticIconUpload.add(artwork);
				testStaticIconUpload.add(artwork2);
				fileManager.uploadFiles(testStaticIconUpload, new MultipleFileCompletionListener() {
					@Override
					public void onComplete(Map<String, String> errors) {
						assertTrue(errors == null);
					}
				});
			}
		});
		verify(internalInterface, times(0)).sendRequests(any(List.class), any(OnMultipleRequestListener.class));
	}

	/**
	 * Testing uploadFiles for static icons and nonStatic icons in the same list.
	 */
	@Test
	public void testMultipleFileUploadsForPartialStaticIcon() {
		ISdl internalInterface = mock(ISdl.class);

		doAnswer(onListFilesSuccess).when(internalInterface).sendRPC(any(ListFiles.class));
		doAnswer(onListFileUploadSuccess).when(internalInterface).sendRequests(any(List.class), any(OnMultipleRequestListener.class));

		FileManagerConfig fileManagerConfig = new FileManagerConfig();
		final FileManager fileManager = new FileManager(internalInterface, mTestContext, fileManagerConfig);
		fileManager.start(new CompletionListener() {
			@Override
			public void onComplete(boolean success) {
				assertTrue(success);
				SdlArtwork artwork = new SdlArtwork(StaticIconName.ALBUM);
				SdlArtwork artwork2 = new SdlArtwork(StaticIconName.FILENAME);
				List<SdlFile> testFileuploads = new ArrayList<>();
				testFileuploads.add(artwork);
				testFileuploads.add(artwork2);
				testFileuploads.add(validFile);
				fileManager.uploadFiles(testFileuploads, new MultipleFileCompletionListener() {
					@Override
					public void onComplete(Map<String, String> errors) {
						assertTrue(errors == null);
					}
				});
			}
		});
		verify(internalInterface, times(1)).sendRequests(any(List.class), any(OnMultipleRequestListener.class));
	}

	/**
	 * Test to make sure you cannot upload an SdlFile with invalid data
	 */
	@Test
	public void testInvalidSdlFileInput() {
		ISdl internalInterface = mock(ISdl.class);

		doAnswer(onListFilesSuccess).when(internalInterface).sendRPC(any(ListFiles.class));

		FileManagerConfig fileManagerConfig = new FileManagerConfig();
		final FileManager fileManager = new FileManager(internalInterface, mTestContext, fileManagerConfig);
		fileManager.start(new CompletionListener() {
			@Override
			public void onComplete(boolean success) {
				assertTrue(success);
				SdlFile sdlFile = new SdlFile();
				// Don't set name
				sdlFile.setFileData(TestValues.GENERAL_BYTE_ARRAY);
				checkForUploadFailure(fileManager, sdlFile);

				sdlFile = new SdlFile();
				sdlFile.setName(TestValues.GENERAL_STRING);
				// Don't set data
				checkForUploadFailure(fileManager, sdlFile);

				sdlFile = new SdlFile();
				sdlFile.setName(TestValues.GENERAL_STRING);
				// Give an invalid resource ID
				sdlFile.setResourceId(TestValues.GENERAL_INT);
				checkForUploadFailure(fileManager, sdlFile);

				sdlFile = new SdlFile();
				sdlFile.setName(TestValues.GENERAL_STRING);
				// Set invalid Uri
				Uri testUri = Uri.parse("http://www.google.com");
				sdlFile.setUri(testUri);
				checkForUploadFailure(fileManager, sdlFile);
			}
		});
	}

	/**
	 * Used to try and upload SdlFiles with invalid data, throw an assert error if file uploads
	 *
	 * @param fileManager - FileManager used to manage and upload files
	 * @param sdlFile     - SdlFile with invalid data to test uploading
	 */
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

	/**
	 * Test Invalid SdlArtWork FileTypes
	 * SdlArtwork FileTypes can only be: GRAPHIC_BMP, GRAPHIC_PNG or GRAPHIC_JPEG
	 */
	@Test
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

	/**
	 * Test Multiple File Uploads, success
	 */
	@Test
	public void testMultipleFileUpload() {
		ISdl internalInterface = mock(ISdl.class);

		doAnswer(onListFilesSuccess).when(internalInterface).sendRPC(any(ListFiles.class));
		doAnswer(onListFileUploadSuccess).when(internalInterface).sendRequests(any(List.class), any(OnMultipleRequestListener.class));

		FileManagerConfig fileManagerConfig = new FileManagerConfig();

		final FileManager fileManager = new FileManager(internalInterface, mTestContext, fileManagerConfig);
		fileManager.start(new CompletionListener() {
			@Override
			public void onComplete(boolean success) {
				assertTrue(success);
				final List<SdlFile> filesToUpload = new ArrayList<>();
				filesToUpload.add(validFile);

				SdlFile validFile2 = new SdlFile();
				validFile2.setName(TestValues.GENERAL_STRING + "2");
				validFile2.setFileData(TestValues.GENERAL_BYTE_ARRAY);
				validFile2.setPersistent(false);
				validFile2.setType(FileType.GRAPHIC_JPEG);
				filesToUpload.add(validFile2);

				fileManager.uploadFiles(filesToUpload, new MultipleFileCompletionListener() {
					@Override
					public void onComplete(Map<String, String> errors) {
						assertNull(errors);
					}
				});
			}
		});
	}

	/**
	 * Testing uploading multiple files with some failing.
	 */
	@Test
	public void testMultipleFileUploadPartialFailure() {
		ISdl internalInterface = mock(ISdl.class);

		doAnswer(onListFilesSuccess).when(internalInterface).sendRPC(any(ListFiles.class));
		doAnswer(onSendRequestsFailPartialOnError).when(internalInterface).sendRequests(any(List.class), any(OnMultipleRequestListener.class));

		SdlFile validFile2 = new SdlFile();
		validFile2.setName(TestValues.GENERAL_STRING + "2");
		validFile2.setFileData(TestValues.GENERAL_BYTE_ARRAY);
		validFile2.setPersistent(false);
		validFile2.setType(FileType.GRAPHIC_JPEG);

		SdlFile validFile3 = new SdlFile();
		validFile3.setName(TestValues.GENERAL_STRING + "3");
		validFile3.setFileData(TestValues.GENERAL_BYTE_ARRAY);
		validFile3.setPersistent(false);
		validFile3.setType(FileType.GRAPHIC_JPEG);

		validFile.setType(FileType.AUDIO_WAVE);

		final List<SdlFile> filesToUpload = new ArrayList<>();
		filesToUpload.add(validFile);
		filesToUpload.add(validFile2);
		filesToUpload.add(validFile3);

		FileManagerConfig fileManagerConfig = new FileManagerConfig();
		fileManagerConfig.setArtworkRetryCount(0);
		fileManagerConfig.setFileRetryCount(0);
		final FileManager fileManager = new FileManager(internalInterface, mTestContext, fileManagerConfig);
		fileManager.start(new CompletionListener() {
			@Override
			public void onComplete(boolean success) {
				fileManager.uploadFiles(filesToUpload,
						new MultipleFileCompletionListener() {
							@Override
							public void onComplete(Map<String, String> errors) {
								assertTrue(errors.size() == 2);
							}
						});
			}
		});
		assertFalse(fileManager.hasUploadedFile(validFile) && fileManager.hasUploadedFile(validFile3));
		assertTrue(fileManager.hasUploadedFile(validFile2));
	}

	/**
	 * Testing uploading multiple SdlArtwork files.
	 */
	@Test
	public void testMultipleArtworkUploadSuccess(){
		ISdl internalInterface = mock(ISdl.class);

		doAnswer(onListFilesSuccess).when(internalInterface).sendRPC(any(ListFiles.class));
		doAnswer(onListFileUploadSuccess).when(internalInterface).sendRequests(any(List.class), any(OnMultipleRequestListener.class));

		FileManagerConfig fileManagerConfig = new FileManagerConfig();
		final FileManager fileManager = new FileManager(internalInterface, mTestContext, fileManagerConfig);
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

	/**
	 * Testing uploading persistent SdlFile
	 */
	@Test
	public void testPersistentFileUploaded(){
		ISdl internalInterface = mock(ISdl.class);

		doAnswer(onListFilesSuccess).when(internalInterface).sendRPC(any(ListFiles.class));

		final SdlFile file = new SdlFile();
		file.setName(TestValues.GENERAL_STRING_LIST.get(0));
		file.setPersistent(true);

		FileManagerConfig fileManagerConfig = new FileManagerConfig();
		final FileManager fileManager = new FileManager(internalInterface, mTestContext, fileManagerConfig);
		fileManager.start(new CompletionListener() {
			@Override
			public void onComplete(boolean success) {
				assertTrue(fileManager.hasUploadedFile(file));
			}
		});
	}

	/**
	 * Test FileManagerConfig
	 */
	@Test
	public void testFileManagerConfig() {
		FileManagerConfig fileManagerConfig = new FileManagerConfig();
		fileManagerConfig.setFileRetryCount(2);
		fileManagerConfig.setArtworkRetryCount(2);
		assertEquals(fileManagerConfig.getArtworkRetryCount(), 2);
		assertEquals(fileManagerConfig.getFileRetryCount(), 2);
	}

	/**
	 * Tests overwrite property for uploading a file.
	 * Checks to make sure file does not overwrite itself if overwrite property is set to false
	 */
	@Test
	public void testOverwriteFileProperty() {
		ISdl internalInterface = mock(ISdl.class);

		doAnswer(onListFilesSuccess).when(internalInterface).sendRPC(any(ListFiles.class));
		doAnswer(onPutFileSuccess).when(internalInterface).sendRPC(any(PutFile.class));

		FileManagerConfig fileManagerConfig = new FileManagerConfig();

		final FileManager fileManager = new FileManager(internalInterface, mTestContext, fileManagerConfig);
		fileManager.start(new CompletionListener() {
			@Override
			public void onComplete(boolean success) {
				assertTrue(success);
				fileManager.uploadFile(validFile, new CompletionListener() {
					@Override
					public void onComplete(boolean success) {
						assertTrue(success);
						validFile.setOverwrite(false);
						fileManager.uploadFile(validFile, new CompletionListener() {
							@Override
							public void onComplete(boolean success) {
								assertTrue(success);
							}
						});

					}
				});
			}
		});
		verify(internalInterface, times(2)).sendRPC(any(RPCMessage.class));
	}

	/**
	 * Tests overwrite property for uploading a list of files.
	 * Checks to make sure files do not overwrite themselves if overwrite property is set to false.
	 */
	@Test
	public void testOverWriteFilePropertyListFiles() {
		final ISdl internalInterface = mock(ISdl.class);

		doAnswer(onListFilesSuccess).when(internalInterface).sendRPC(any(ListFiles.class));
		doAnswer(onListFileUploadSuccess).when(internalInterface).sendRequests(any(List.class), any(OnMultipleRequestListener.class));

		final SdlFile validFile2 = new SdlFile();
		validFile2.setName(TestValues.GENERAL_STRING + "2");
		validFile2.setFileData(TestValues.GENERAL_BYTE_ARRAY);
		validFile2.setPersistent(false);
		validFile2.setType(FileType.GRAPHIC_JPEG);

		final List<SdlFile> list = new ArrayList<>();
		list.add(validFile);
		list.add(validFile2);

		FileManagerConfig fileManagerConfig = new FileManagerConfig();
		fileManagerConfig.setArtworkRetryCount(2);
		fileManagerConfig.setFileRetryCount(4);

		final FileManager fileManager = new FileManager(internalInterface, mTestContext, fileManagerConfig);
		fileManager.start(new CompletionListener() {
			@Override
			public void onComplete(boolean success) {
				fileManager.uploadFiles(list, new MultipleFileCompletionListener() {
					@Override
					public void onComplete(Map<String, String> errors) {
						validFile.setOverwrite(false);
						validFile2.setOverwrite(false);
						fileManager.uploadFiles(list, new MultipleFileCompletionListener() {
							@Override
							public void onComplete(Map<String, String> errors) {
								assertNull(errors);
							}
						});
					}
				});

			}
		});
		verify(internalInterface, times(1)).sendRequests(any(List.class), any(OnMultipleRequestListener.class));
	}

	/**
	 * 	Test custom overridden SdlFile equals method
	 */
	@Test
	public void testSdlFileEquals() {
		// Case 1: object is null, assertFalse
		SdlFile artwork1 = new SdlFile("image1", FileType.GRAPHIC_PNG, 1, true);
		SdlFile artwork2 = null;
		assertFalse(artwork1.equals(artwork2));

		// Case 2 SoftButtonObjects are the same, assertTrue
		assertTrue(artwork1.equals(artwork1));

		// Case 3: object is not an instance of SoftButtonObject, assertFalse
		assertFalse(artwork1.equals("Test"));

		// Case 4: different StaticIcon status, assertFalse
		artwork1.setStaticIcon(true);
		artwork2 = new SdlFile("image1", FileType.GRAPHIC_PNG, 1, true);
		artwork2.setStaticIcon(false);
		assertFalse(artwork1.equals(artwork2));

		// Case 5: different Persistent status, assertFalse
		artwork1 = new SdlFile("image1", FileType.GRAPHIC_PNG, 1, false);
		artwork2 = new SdlFile("image1", FileType.GRAPHIC_PNG, 1, true);
		assertFalse(artwork1.equals(artwork2));

		// Case 6: different name, assertFalse
		artwork2 = new SdlFile("image2", FileType.GRAPHIC_PNG, 1, false);
		assertFalse(artwork1.equals(artwork2));

		// Case 7: different Uri
		Uri uri1 = Uri.parse("testUri1");
		Uri uri2 = Uri.parse("testUri2");
		artwork1 = new SdlFile("image1", FileType.GRAPHIC_PNG, uri1, false);
		artwork2 = new SdlFile("image1", FileType.GRAPHIC_PNG, uri2, false);
		assertFalse(artwork1.equals(artwork2));

		// Case 8: different FileData
		artwork1 = new SdlFile("image1", FileType.GRAPHIC_PNG, 1, false);
		artwork2 = new SdlFile("image1", FileType.GRAPHIC_PNG, 1, false);
		byte[] GENERAL_BYTE_ARRAY2 = new byte[2];
		artwork1.setFileData(TestValues.GENERAL_BYTE_ARRAY);
		artwork2.setFileData(GENERAL_BYTE_ARRAY2);
		assertFalse(artwork1.equals(artwork2));

		// Case 9 different FileType, assertFalse
		artwork1 = new SdlFile("image1", FileType.GRAPHIC_PNG, 1, false);
		artwork2 = new SdlFile("image1", FileType.AUDIO_WAVE, 1, false);
		assertFalse(artwork1.equals(artwork2));

		// Case 10: they are equal, assertTrue
		artwork1 = new SdlFile("image1", FileType.GRAPHIC_PNG, 1, false);
		artwork2 = new SdlFile("image1", FileType.GRAPHIC_PNG, 1, false);
		assertTrue(artwork1.equals(artwork2));
	}
}