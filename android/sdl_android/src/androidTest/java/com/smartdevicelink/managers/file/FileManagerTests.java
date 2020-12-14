package com.smartdevicelink.managers.file;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.livio.taskmaster.Taskmaster;
import com.smartdevicelink.managers.BaseSubManager;
import com.smartdevicelink.managers.CompletionListener;
import com.smartdevicelink.managers.ISdl;
import com.smartdevicelink.managers.file.filetypes.SdlArtwork;
import com.smartdevicelink.managers.file.filetypes.SdlFile;
import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.rpc.DeleteFile;
import com.smartdevicelink.proxy.rpc.DeleteFileResponse;
import com.smartdevicelink.proxy.rpc.ListFiles;
import com.smartdevicelink.proxy.rpc.ListFilesResponse;
import com.smartdevicelink.proxy.rpc.PutFile;
import com.smartdevicelink.proxy.rpc.PutFileResponse;
import com.smartdevicelink.proxy.rpc.SdlMsgVersion;
import com.smartdevicelink.proxy.rpc.enums.FileType;
import com.smartdevicelink.proxy.rpc.enums.Result;
import com.smartdevicelink.proxy.rpc.enums.StaticIconName;
import com.smartdevicelink.proxy.rpc.listeners.OnMultipleRequestListener;
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.util.Version;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * This is a unit test class for the SmartDeviceLink library manager class :
 * {@link FileManager}
 */
@RunWith(AndroidJUnit4.class)
public class FileManagerTests {
    public static final String TAG = "FileManagerTests";
    private Context mTestContext;
    private SdlFile validFile;
    private Handler mainHandler;
    private Version rpcVersion;
    private long mtuSize;

    // SETUP / HELPERS

    @Before
    public void setUp() throws Exception {
        mTestContext = getInstrumentation().getTargetContext();
        mainHandler = new Handler(mTestContext.getMainLooper());
        rpcVersion = new Version(7, 0, 0);
        mtuSize = 131072;
        validFile = new SdlFile(TestValues.GENERAL_STRING, FileType.BINARY, TestValues.GENERAL_STRING.getBytes(), false);
    }

    private Answer<Void> onListFilesSuccess = new Answer<Void>() {
        @Override
        public Void answer(InvocationOnMock invocation) {
            Object[] args = invocation.getArguments();
            RPCRequest message = (RPCRequest) args[0];
            if (message instanceof ListFiles) {
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
            if (message instanceof PutFile) {
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
            if (message instanceof PutFile) {
                int correlationId = message.getCorrelationID();
                PutFileResponse putFileResponse = new PutFileResponse();
                putFileResponse.setSuccess(false);
                message.getOnRPCResponseListener().onResponse(correlationId, putFileResponse);
            }
            return null;
        }
    };

    private Answer<Void> onDeleteFileSuccess = new Answer<Void>() {
        @Override
        public Void answer(InvocationOnMock invocation) {
            Object[] args = invocation.getArguments();
            RPCRequest message = (RPCRequest) args[0];
            if (message instanceof DeleteFile) {
                int correlationId = message.getCorrelationID();
                DeleteFileResponse deleteFileResponse = new DeleteFileResponse();
                deleteFileResponse.setSuccess(true);
                message.getOnRPCResponseListener().onResponse(correlationId, deleteFileResponse);
            }
            return null;
        }
    };

    private Answer<Void> onDeleteFileFailure = new Answer<Void>() {
        @Override
        public Void answer(InvocationOnMock invocation) {
            Object[] args = invocation.getArguments();
            RPCRequest message = (RPCRequest) args[0];
            if (message instanceof DeleteFile) {
                int correlationId = message.getCorrelationID();
                DeleteFileResponse deleteFileResponse = new DeleteFileResponse(false, Result.REJECTED);
                deleteFileResponse.setInfo("Binary data empty");
                message.getOnRPCResponseListener().onResponse(correlationId, deleteFileResponse);
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

    /**
     * Test deleting list of files, success
     */
    @Test
    public void testDeleteRemoteFilesWithNamesSuccess() {
        final ISdl internalInterface = createISdlMock();

        doAnswer(onListFilesSuccess).when(internalInterface).sendRPC(any(ListFiles.class));
        doAnswer(onDeleteFileSuccess).when(internalInterface).sendRPC(any(DeleteFile.class));

        final List<String> fileNames = Arrays.asList("Julian", "Jake");

        FileManagerConfig fileManagerConfig = new FileManagerConfig();

        final FileManager fileManager = new FileManager(internalInterface, mTestContext, fileManagerConfig);
        fileManager.start(new CompletionListener() {
            @Override
            public void onComplete(final boolean success) {
                assertOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        assertTrue(success);
                    }
                });

                fileManager.mutableRemoteFileNames.addAll(fileNames);

                fileManager.deleteRemoteFilesWithNames(fileNames, new MultipleFileCompletionListener() {
                    @Override
                    public void onComplete(final Map<String, String> errors) {
                        assertOnMainThread(new Runnable() {
                            @Override
                            public void run() {
                                assertNull(errors);
                            }
                        });
                    }
                });
            }
        });
    }

    /**
     * Test deleting list of files, fail
     */
    @Test
    public void testDeleteRemoteFilesWithNamesFail() {
        final ISdl internalInterface = createISdlMock();

        doAnswer(onListFilesSuccess).when(internalInterface).sendRPC(any(ListFiles.class));
        doAnswer(onDeleteFileFailure).when(internalInterface).sendRPC(any(DeleteFile.class));

        final List<String> fileNames = Arrays.asList("Julian", "Jake");

        FileManagerConfig fileManagerConfig = new FileManagerConfig();

        final FileManager fileManager = new FileManager(internalInterface, mTestContext, fileManagerConfig);
        fileManager.start(new CompletionListener() {
            @Override
            public void onComplete(final boolean success) {
                assertOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        assertTrue(success);
                    }
                });

                fileManager.mutableRemoteFileNames.addAll(fileNames);
                fileManager.deleteRemoteFilesWithNames(fileNames, new MultipleFileCompletionListener() {
                    @Override
                    public void onComplete(final Map<String, String> errors) {
                        assertOnMainThread(new Runnable() {
                            @Override
                            public void run() {
                                assertEquals(2, errors.size());
                            }
                        });
                    }
                });
            }
        });
    }

    /**
     * Test reUploading failed file
     */
    @Test
    public void testFileUploadRetry() {
        final ISdl internalInterface = createISdlMock();

        doAnswer(onListFilesSuccess).when(internalInterface).sendRPC(any(ListFiles.class));
        doAnswer(onPutFileFailure).when(internalInterface).sendRPC(any(PutFile.class));

        FileManagerConfig fileManagerConfig = new FileManagerConfig();
        fileManagerConfig.setFileRetryCount(2);

        validFile.setType(FileType.AUDIO_MP3);

        final FileManager fileManager = new FileManager(internalInterface, mTestContext, fileManagerConfig);

        fileManager.start(new CompletionListener() {
            @Override
            public void onComplete(final boolean success1) {
                assertOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        assertTrue(success1);
                    }
                });

                fileManager.uploadFile(validFile, new CompletionListener() {
                    @Override
                    public void onComplete(final boolean success2) {
                        assertOnMainThread(new Runnable() {
                            @Override
                            public void run() {
                                assertFalse(success2);
                                verify(internalInterface, times(4)).sendRPC(any(RPCMessage.class));
                            }
                        });
                    }
                });
            }
        });
    }

    /**
     * Test reUploading failed Artwork
     */
    @Test
    public void testArtworkUploadRetry() {
        final ISdl internalInterface = createISdlMock();

        doAnswer(onListFilesSuccess).when(internalInterface).sendRPC(any(ListFiles.class));
        doAnswer(onPutFileFailure).when(internalInterface).sendRPC(any(PutFile.class));

        final SdlArtwork validArtwork = new SdlArtwork(TestValues.GENERAL_STRING + "1", FileType.GRAPHIC_JPEG, TestValues.GENERAL_STRING.getBytes(), false);

        FileManagerConfig fileManagerConfig = new FileManagerConfig();
        fileManagerConfig.setArtworkRetryCount(2);

        final FileManager fileManager = new FileManager(internalInterface, mTestContext, fileManagerConfig);
        fileManager.start(new CompletionListener() {
            @Override
            public void onComplete(final boolean success1) {
                assertOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        assertTrue(success1);
                    }
                });

                fileManager.uploadArtwork(validArtwork, new CompletionListener() {
                    @Override
                    public void onComplete(final boolean success2) {
                        assertOnMainThread(new Runnable() {
                            @Override
                            public void run() {
                                assertFalse(success2);
                                verify(internalInterface, times(4)).sendRPC(any(RPCMessage.class));
                            }
                        });
                    }
                });
            }
        });
    }

    /**
     * Test retry uploading failed list of files
     */
    @Test
    public void testListFilesUploadRetry() {
        final ISdl internalInterface = createISdlMock();

        doAnswer(onListFilesSuccess).when(internalInterface).sendRPC(any(ListFiles.class));
        doAnswer(onPutFileFailure).when(internalInterface).sendRPC(any(PutFile.class));

        final SdlArtwork validFile2 = new SdlArtwork(TestValues.GENERAL_STRING + "2", FileType.GRAPHIC_JPEG, TestValues.GENERAL_STRING.getBytes(), false);

        final List<SdlFile> list = Arrays.asList(validFile, validFile2);

        FileManagerConfig fileManagerConfig = new FileManagerConfig();
        fileManagerConfig.setFileRetryCount(3);
        fileManagerConfig.setArtworkRetryCount(4);

        final FileManager fileManager = new FileManager(internalInterface, mTestContext, fileManagerConfig);
        fileManager.start(new CompletionListener() {
            @Override
            public void onComplete(final boolean success1) {
                assertOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        assertTrue(success1);
                    }
                });

                fileManager.uploadFiles(list, new MultipleFileCompletionListener() {
                    @Override
                    public void onComplete(final Map<String, String> errors) {
                        assertOnMainThread(new Runnable() {
                            @Override
                            public void run() {
                                assertEquals(2, errors.size()); // We need to make sure it kept track of both Files
                                verify(internalInterface, times(9)).sendRPC(any(PutFile.class));
                            }
                        });
                    }
                });
            }
        });
    }

    /**
     * Testing the initialization of FileManager
     */
    @Test
    public void testInitializationSuccess() {
        ISdl internalInterface = createISdlMock();

        doAnswer(onListFilesSuccess).when(internalInterface).sendRPC(any(ListFiles.class));

        FileManagerConfig fileManagerConfig = new FileManagerConfig();
        final FileManager fileManager = new FileManager(internalInterface, mTestContext, fileManagerConfig);
        fileManager.start(new CompletionListener() {
            @Override
            public void onComplete(final boolean success) {
                assertOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        assertTrue(success);
                        assertEquals(fileManager.getState(), BaseSubManager.READY);
                        assertEquals(fileManager.getRemoteFileNames(), TestValues.GENERAL_STRING_LIST);
                        assertEquals(TestValues.GENERAL_INT, fileManager.getBytesAvailable());
                    }
                });
            }
        });
    }

    /**
     * Test file upload, success
     */
    @Test
    public void testFileUploadSuccess() {
        ISdl internalInterface = createISdlMock();
        doAnswer(onListFilesSuccess).when(internalInterface).sendRPC(any(ListFiles.class));
        doAnswer(onPutFileSuccess).when(internalInterface).sendRPC(any(PutFile.class));

        FileManagerConfig fileManagerConfig = new FileManagerConfig();

        final FileManager fileManager = new FileManager(internalInterface, mTestContext, fileManagerConfig);
        fileManager.start(new CompletionListener() {
            @Override
            public void onComplete(final boolean success1) {
                assertOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        assertTrue(success1);
                    }
                });

                fileManager.uploadFile(validFile, new CompletionListener() {
                    @Override
                    public void onComplete(final boolean success2) {
                        assertOnMainThread(new Runnable() {
                            @Override
                            public void run() {
                                assertTrue(success2);
                                assertTrue(fileManager.getRemoteFileNames().contains(validFile.getName()));
                                assertTrue(fileManager.hasUploadedFile(validFile));
                                assertEquals(TestValues.GENERAL_INT, fileManager.getBytesAvailable());
                            }
                        });

                    }
                });
            }
        });
    }

    /**
     * Testing failed file upload.
     */
    @Test
    public void testFileUploadFailure() {
        ISdl internalInterface = createISdlMock();

        doAnswer(onListFilesSuccess).when(internalInterface).sendRPC(any(ListFiles.class));
        doAnswer(onPutFileFailure).when(internalInterface).sendRPC(any(PutFile.class));

        FileManagerConfig fileManagerConfig = new FileManagerConfig();
        final FileManager fileManager = new FileManager(internalInterface, mTestContext, fileManagerConfig);
        fileManager.start(new CompletionListener() {
            @Override
            public void onComplete(final boolean success1) {
                assertOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        assertTrue(success1);
                    }
                });
                fileManager.uploadFile(validFile, new CompletionListener() {
                    @Override
                    public void onComplete(final boolean success2) {
                       assertOnMainThread(new Runnable() {
                           @Override
                           public void run() {
                               assertFalse(success2);
                               assertFalse(fileManager.getRemoteFileNames().contains(validFile.getName()));
                               assertFalse(fileManager.hasUploadedFile(validFile));
                           }
                       });
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
        final ISdl internalInterface = createISdlMock();

        doAnswer(onListFilesSuccess).when(internalInterface).sendRPC(any(ListFiles.class));

        FileManagerConfig fileManagerConfig = new FileManagerConfig();
        final FileManager fileManager = new FileManager(internalInterface, mTestContext, fileManagerConfig);
        fileManager.start(new CompletionListener() {
            @Override
            public void onComplete(final boolean success1) {
                assertOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        assertTrue(success1);
                    }
                });
                SdlArtwork artwork = new SdlArtwork(StaticIconName.ALBUM);
                fileManager.uploadFile(artwork, new CompletionListener() {
                    @Override
                    public void onComplete(final boolean success2) {
                        assertOnMainThread(new Runnable() {
                            @Override
                            public void run() {
                                assertFalse(success2);
                                verify(internalInterface, times(1)).sendRPC(any(RPCMessage.class));
                            }
                        });
                    }
                });
            }
        });
    }

    /**
     * Testing uploadFiles for staticIcons, verifying that it doesn't actually upload.
     */
    @Test
    public void testMultipleFileUploadsForStaticIcon() {
        final ISdl internalInterface = createISdlMock();

        doAnswer(onListFilesSuccess).when(internalInterface).sendRPC(any(ListFiles.class));
        doAnswer(onPutFileSuccess).when(internalInterface).sendRPC(any(PutFile.class));

        FileManagerConfig fileManagerConfig = new FileManagerConfig();
        final FileManager fileManager = new FileManager(internalInterface, mTestContext, fileManagerConfig);
        fileManager.start(new CompletionListener() {
            @Override
            public void onComplete(final boolean success1) {
                assertOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        assertTrue(success1);
                    }
                });

                SdlArtwork artwork1 = new SdlArtwork(StaticIconName.ALBUM);
                SdlArtwork artwork2 = new SdlArtwork(StaticIconName.FILENAME);
                List<SdlArtwork> testStaticIconUpload = Arrays.asList(artwork1, artwork2);

                fileManager.uploadFiles(testStaticIconUpload, new MultipleFileCompletionListener() {
                    @Override
                    public void onComplete(final Map<String, String> errors) {
                        assertOnMainThread(new Runnable() {
                            @Override
                            public void run() {
                                assertEquals(2, errors.size());
                                verify(internalInterface, times(0)).sendRPC(any(PutFile.class));
                            }
                        });
                    }
                });
            }
        });
    }

    /**
     * Testing uploadFiles for static icons and nonStatic icons in the same list.
     */
    @Test
    public void testMultipleFileUploadsForPartialStaticIcon() {
        final ISdl internalInterface = createISdlMock();

        doAnswer(onListFilesSuccess).when(internalInterface).sendRPC(any(ListFiles.class));
        doAnswer(onPutFileSuccess).when(internalInterface).sendRPC(any(PutFile.class));

        FileManagerConfig fileManagerConfig = new FileManagerConfig();
        final FileManager fileManager = new FileManager(internalInterface, mTestContext, fileManagerConfig);
        fileManager.start(new CompletionListener() {
            @Override
            public void onComplete(boolean success) {
                assertTrue(success);
                SdlArtwork artwork = new SdlArtwork(StaticIconName.ALBUM);
                SdlArtwork artwork2 = new SdlArtwork(StaticIconName.FILENAME);
                List<SdlFile> testFileUploads = Arrays.asList(artwork, artwork2, validFile);
                fileManager.uploadFiles(testFileUploads, new MultipleFileCompletionListener() {
                    @Override
                    public void onComplete(final Map<String, String> errors) {
                        assertOnMainThread(new Runnable() {
                            @Override
                            public void run() {
                                assertEquals(2, errors.size());
                                verify(internalInterface, times(1)).sendRPC(any(PutFile.class));
                            }
                        });
                    }
                });
            }
        });
    }

    /**
     * Test to make sure you cannot upload an SdlFile with invalid data
     */
    @Test
    public void testInvalidSdlFileInput() {
        ISdl internalInterface = createISdlMock();

        doAnswer(onListFilesSuccess).when(internalInterface).sendRPC(any(ListFiles.class));

        FileManagerConfig fileManagerConfig = new FileManagerConfig();
        final FileManager fileManager = new FileManager(internalInterface, mTestContext, fileManagerConfig);
        fileManager.start(new CompletionListener() {
            @Override
            public void onComplete(final boolean success1) {
                assertOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        assertTrue(success1);
                    }
                });

                SdlFile sdlFile;

                // Test 1 - Don't set name
                sdlFile = new SdlFile();
                sdlFile.setFileData(TestValues.GENERAL_BYTE_ARRAY);
                checkForUploadFailure(fileManager, sdlFile);

                // Test 2 - Don't set data
                sdlFile = new SdlFile();
                sdlFile.setName(TestValues.GENERAL_STRING);
                checkForUploadFailure(fileManager, sdlFile);

                // Test 3 - Give an invalid resource ID
                sdlFile = new SdlFile();
                sdlFile.setName(TestValues.GENERAL_STRING);
                sdlFile.setResourceId(TestValues.GENERAL_INT);
                checkForUploadFailure(fileManager, sdlFile);

                // Test4 - Set invalid Uri
                sdlFile = new SdlFile();
                sdlFile.setName(TestValues.GENERAL_STRING);
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
    private void checkForUploadFailure(FileManager fileManager, SdlFile sdlFile) {
        fileManager.uploadFile(sdlFile, new CompletionListener() {
            @Override
            public void onComplete(final boolean success) {
                assertOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        assertFalse(success);
                    }
                });
            }
        });
    }

    /**
     * Test Invalid SdlArtWork FileTypes
     * SdlArtwork FileTypes can only be: GRAPHIC_BMP, GRAPHIC_PNG or GRAPHIC_JPEG
     */
    @Test
    public void testInvalidSdlArtworkInput() {
        SdlArtwork sdlArtwork = new SdlArtwork();
        // Set invalid type
        for (FileType fileType : FileType.values()) {
            boolean shouldError = true, didError = false;
            if (fileType.equals(FileType.GRAPHIC_BMP) || fileType.equals(FileType.GRAPHIC_PNG) || fileType.equals(FileType.GRAPHIC_JPEG)) {
                shouldError = false;
            }
            try {
                sdlArtwork.setType(fileType);
            } catch (IllegalArgumentException e) {
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
        ISdl internalInterface = createISdlMock();

        doAnswer(onListFilesSuccess).when(internalInterface).sendRPC(any(ListFiles.class));
        doAnswer(onPutFileSuccess).when(internalInterface).sendRPC(any(PutFile.class));

        FileManagerConfig fileManagerConfig = new FileManagerConfig();
        final FileManager fileManager = new FileManager(internalInterface, mTestContext, fileManagerConfig);
        fileManager.start(new CompletionListener() {
            @Override
            public void onComplete(boolean success) {
                SdlFile validFile2 = new SdlFile(TestValues.GENERAL_STRING + "2", FileType.GRAPHIC_JPEG, TestValues.GENERAL_STRING.getBytes(), false);
                List<SdlFile> filesToUpload = Arrays.asList(validFile, validFile2);
                fileManager.uploadFiles(filesToUpload, new MultipleFileCompletionListener() {
                    @Override
                    public void onComplete(final Map<String, String> errors) {
                        assertOnMainThread(new Runnable() {
                            @Override
                            public void run() {
                                assertNull(errors);
                            }
                        });
                    }
                });
            }
        });
    }

    /**
     * Testing uploading multiple SdlArtwork files.
     */
    @Test
    public void testMultipleArtworkUploadSuccess() {
        ISdl internalInterface = createISdlMock();

        doAnswer(onListFilesSuccess).when(internalInterface).sendRPC(any(ListFiles.class));
        doAnswer(onPutFileSuccess).when(internalInterface).sendRPC(any(PutFile.class));

        FileManagerConfig fileManagerConfig = new FileManagerConfig();
        final FileManager fileManager = new FileManager(internalInterface, mTestContext, fileManagerConfig);
        fileManager.start(new CompletionListener() {
            @Override
            public void onComplete(boolean success1) {
                SdlArtwork sdlArtwork1 = new SdlArtwork("artwork1", FileType.GRAPHIC_JPEG, Uri.parse("android.resource://" + mTestContext.getPackageName() + "/drawable/ic_sdl"), false);
                SdlArtwork sdlArtwork2 = new SdlArtwork("artwork2", FileType.GRAPHIC_PNG, Uri.parse("android.resource://" + mTestContext.getPackageName() + "/drawable/sdl_tray_icon"), false);
                final List<SdlArtwork> artworkToUpload = Arrays.asList(sdlArtwork1, sdlArtwork2);

                fileManager.uploadFiles(artworkToUpload, new MultipleFileCompletionListener() {
                    @Override
                    public void onComplete(final Map<String, String> errors) {
                        assertOnMainThread(new Runnable() {
                            @Override
                            public void run() {
                                assertNull(errors);
                                List<String> uploadedFileNames = fileManager.getRemoteFileNames();
                                for (SdlArtwork artwork : artworkToUpload) {
                                    assertTrue(uploadedFileNames.contains(artwork.getName()));
                                }
                            }
                        });
                    }
                });
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
    @Test @Ignore
    public void testOverwriteFileProperty() {
        ISdl internalInterface = createISdlMock();

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
    @Test @Ignore
    public void testOverWriteFilePropertyListFiles() {
        final ISdl internalInterface = createISdlMock();

        doAnswer(onListFilesSuccess).when(internalInterface).sendRPC(any(ListFiles.class));
        doAnswer(onListFileUploadSuccess).when(internalInterface).sendRPCs(any(List.class), any(OnMultipleRequestListener.class));

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
        verify(internalInterface, times(1)).sendRPCs(any(List.class), any(OnMultipleRequestListener.class));
    }

    /**
     * Test custom overridden SdlFile equals method
     */
    @Test @Ignore
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

    // Asserts on Taskmaster threads will fail silently so we need to do the assertions on main thread if the code is triggered from Taskmaster
    private void assertOnMainThread(Runnable runnable) {
        mainHandler.post(runnable);
    }

    private ISdl createISdlMock() {
        ISdl internalInterface = mock(ISdl.class);
        Taskmaster taskmaster = new Taskmaster.Builder().build();
        taskmaster.start();

        when(internalInterface.getTaskmaster()).thenReturn(taskmaster);
        when(internalInterface.getSdlMsgVersion()).thenReturn(new SdlMsgVersion(rpcVersion));
        when(internalInterface.getMtu(any(SessionType.class))).thenReturn(mtuSize);
        return internalInterface;
    }
}