/*
 * Copyright (c) 2021 Livio, Inc.
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

package com.smartdevicelink.managers.screen.menu;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static com.smartdevicelink.managers.screen.menu.MenuReplaceUtilities.cloneMenuCellsList;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.os.Handler;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.livio.taskmaster.Queue;
import com.livio.taskmaster.Taskmaster;
import com.smartdevicelink.managers.ISdl;
import com.smartdevicelink.managers.file.FileManager;
import com.smartdevicelink.managers.file.MultipleFileCompletionListener;
import com.smartdevicelink.managers.file.filetypes.SdlArtwork;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.SdlMsgVersion;
import com.smartdevicelink.proxy.rpc.TextField;
import com.smartdevicelink.proxy.rpc.WindowCapability;
import com.smartdevicelink.proxy.rpc.enums.MenuLayout;
import com.smartdevicelink.proxy.rpc.enums.TriggerSource;
import com.smartdevicelink.proxy.rpc.listeners.OnMultipleRequestListener;
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.util.Version;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@RunWith(AndroidJUnit4.class)
public class MenuReplaceOperationTests {
    private Handler mainHandler;
    private Taskmaster taskmaster;
    private Queue transactionQueue;

    @Before
    public void setUp() throws Exception {
        mainHandler = new Handler(getInstrumentation().getTargetContext().getMainLooper());
        taskmaster = new Taskmaster.Builder().build();
        taskmaster.start();
        transactionQueue = taskmaster.createQueue("MenuManager", new Random().nextInt(), false);
    }

    @Test
    public void testSuccess() {
        final ISdl internalInterface = createISdlMock();
        FileManager fileManager = createFileManagerMock();
        WindowCapability windowCapability = createWindowCapability(true, true, new ArrayList<TextField>());
        MenuConfiguration menuConfiguration = new MenuConfiguration(MenuLayout.LIST, MenuLayout.LIST);

        MenuCell menuCell1_1 = new MenuCell("cell 1_1", TestValues.GENERAL_ARTWORK, null, null);
        MenuCell menuCell1 = new MenuCell("cell 1", null, TestValues.GENERAL_ARTWORK, Arrays.asList(menuCell1_1));
        MenuCell menuCell2 = new MenuCell("cell 2", TestValues.GENERAL_ARTWORK, null, null);

        final List<MenuCell> currentMenu = new ArrayList<>();
        final List<MenuCell> updatedMenu = cloneMenuCellsList(Arrays.asList(menuCell1, menuCell2));
        MenuReplaceOperation operation = new MenuReplaceOperation(internalInterface, fileManager, windowCapability, menuConfiguration, currentMenu, updatedMenu, true, new MenuManagerCompletionListener() {
            @Override
            public void onComplete(final boolean success, final List<MenuCell> currentMenuCells) {
                assertOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        assertTrue(success);
                        assertEquals(currentMenuCells, updatedMenu);
                        verify(internalInterface, Mockito.times(2)).sendRPCs(any(List.class), any(OnMultipleRequestListener.class));
                    }
                });
            }
        });
        transactionQueue.add(operation, false);
    }

    @Test
    public void testSwitchingCellsOrder() {
        // This unit test is for this bug https://github.com/smartdevicelink/sdl_java_suite/issues/1723
        final ISdl internalInterface = createISdlMock();
        final FileManager fileManager = createFileManagerMock();
        final WindowCapability windowCapability = createWindowCapability(true, true, new ArrayList<TextField>());
        final MenuConfiguration menuConfiguration = new MenuConfiguration(MenuLayout.LIST, MenuLayout.LIST);

        MenuSelectionListener listener = null;
        final MenuCell menuCell1 = new MenuCell("A", "SecondaryText", null, null, null, null, listener);
        final MenuCell menuCell2 = new MenuCell("A", null, null, null, null, null, listener);
        final MenuCell menuCell3 = new MenuCell("C", null, null, null, null, null, listener);

        MenuReplaceOperation operation = new MenuReplaceOperation(internalInterface, fileManager, windowCapability, menuConfiguration, new ArrayList<MenuCell>(), cloneMenuCellsList(Arrays.asList(menuCell1, menuCell2, menuCell3)), true, new MenuManagerCompletionListener() {
            @Override
            public void onComplete(final boolean success, final List<MenuCell> currentMenuCells1) {
                assertOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        assertTrue(success);
                        assertEquals(3, currentMenuCells1.size());
                        assertEquals("A", currentMenuCells1.get(0).getUniqueTitle());
                        assertEquals("A (2)", currentMenuCells1.get(1).getUniqueTitle());
                        assertEquals("C", currentMenuCells1.get(2).getUniqueTitle());

                        verify(internalInterface, Mockito.times(1)).sendRPCs(any(List.class), any(OnMultipleRequestListener.class));

                        MenuReplaceOperation operation = new MenuReplaceOperation(internalInterface, fileManager, windowCapability, menuConfiguration, currentMenuCells1, cloneMenuCellsList(Arrays.asList(menuCell2, menuCell1)), true, new MenuManagerCompletionListener() {
                            @Override
                            public void onComplete(final boolean success, final List<MenuCell> currentMenuCells2) {
                                assertOnMainThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        assertTrue(success);
                                        assertEquals(2, currentMenuCells2.size());
                                        assertEquals("A", currentMenuCells2.get(0).getUniqueTitle());
                                        assertEquals("A (2)", currentMenuCells2.get(1).getUniqueTitle());
                                        verify(internalInterface, Mockito.times(2)).sendRPCs(any(List.class), any(OnMultipleRequestListener.class));
                                    }
                                });
                            }
                        });
                        transactionQueue.add(operation, false);
                    }
                });
            }
        });
        transactionQueue.add(operation, false);
    }

    @Test
    public void testResendingSameCellWithDifferentListener() {
        final ISdl internalInterface = createISdlMock();
        final FileManager fileManager = createFileManagerMock();
        final WindowCapability windowCapability = createWindowCapability(true, true, new ArrayList<TextField>());
        final MenuConfiguration menuConfiguration = new MenuConfiguration(MenuLayout.LIST, MenuLayout.LIST);

        final MenuSelectionListener listener1 = new MenuSelectionListener() {
            @Override
            public void onTriggered(TriggerSource trigger) {}
        };
        final MenuSelectionListener listener2 = new MenuSelectionListener() {
            @Override
            public void onTriggered(TriggerSource trigger) {}
        };
        final MenuCell menuCell1 = new MenuCell("A", null, null, null, null, null, listener1);
        final MenuCell menuCell2 = new MenuCell("A", null, null, null, null, null, listener2);

        MenuReplaceOperation operation = new MenuReplaceOperation(internalInterface, fileManager, windowCapability, menuConfiguration, new ArrayList<MenuCell>(), cloneMenuCellsList(Arrays.asList(menuCell1)), true, new MenuManagerCompletionListener() {
            @Override
            public void onComplete(final boolean success, final List<MenuCell> currentMenuCells1) {
                assertOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        assertTrue(success);
                        assertEquals(1, currentMenuCells1.size());
                        assertEquals(listener1, currentMenuCells1.get(0).getMenuSelectionListener());
                        verify(internalInterface, Mockito.times(1)).sendRPCs(any(List.class), any(OnMultipleRequestListener.class));

                        MenuReplaceOperation operation = new MenuReplaceOperation(internalInterface, fileManager, windowCapability, menuConfiguration, currentMenuCells1, cloneMenuCellsList(Arrays.asList(menuCell2)), true, new MenuManagerCompletionListener() {
                            @Override
                            public void onComplete(final boolean success, final List<MenuCell> currentMenuCells2) {
                                assertOnMainThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        assertTrue(success);
                                        assertEquals(1, currentMenuCells2.size());
                                        assertEquals(listener2, currentMenuCells2.get(0).getMenuSelectionListener());
                                        verify(internalInterface, Mockito.times(1)).sendRPCs(any(List.class), any(OnMultipleRequestListener.class));
                                    }
                                });
                            }
                        });
                        transactionQueue.add(operation, false);
                    }
                });
            }
        });
        transactionQueue.add(operation, false);
    }

    private ISdl createISdlMock() {
        final ISdl internalInterface = mock(ISdl.class);

        // When internalInterface.sendRPCs() is called, call listener.onFinished() to fake the response
        final Answer<Void> answer = new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                List<RPCMessage> rpcs = (List<RPCMessage>) args[0];
                OnMultipleRequestListener listener = (OnMultipleRequestListener) args[1];

                for (RPCMessage rpcMessage : rpcs) {
                    RPCRequest request = (RPCRequest) rpcMessage;
                    RPCResponse response = new RPCResponse(request.getFunctionID().toString());
                    response.setCorrelationID(request.getCorrelationID());
                    response.setSuccess(true);
                    listener.onResponse(request.getCorrelationID(), response);
                }

                listener.onFinished();
                return null;
            }
        };
        doAnswer(answer).when(internalInterface).sendRPCs(any(List.class), any(OnMultipleRequestListener.class));
        when(internalInterface.getSdlMsgVersion()).thenReturn(new SdlMsgVersion(new Version(7, 1, 0)));

        return internalInterface;
    }

    private FileManager createFileManagerMock() {
        FileManager fileManager = mock(FileManager.class);

        when(fileManager.hasUploadedFile(any(SdlArtwork.class))).thenReturn(true);

        Answer<Void> onFileManagerUploadAnswer = new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                MultipleFileCompletionListener multipleFileCompletionListener = (MultipleFileCompletionListener) args[1];
                multipleFileCompletionListener.onComplete(null);
                return null;
            }
        };
        doAnswer(onFileManagerUploadAnswer).when(fileManager).uploadArtworks(any(List.class), any(MultipleFileCompletionListener.class));
        return fileManager;
    }

    private WindowCapability createWindowCapability(boolean supportsList, boolean supportsTile, ArrayList<TextField> supportedTextFields) {
        WindowCapability windowCapability = new WindowCapability();
        windowCapability.setTextFields(supportedTextFields);
        windowCapability.setMenuLayoutsAvailable(new ArrayList<MenuLayout>());
        if (supportsList) {
            windowCapability.getMenuLayoutsAvailable().add(MenuLayout.LIST);
        }
        if (supportsTile) {
            windowCapability.getMenuLayoutsAvailable().add(MenuLayout.TILES);
        }
        return windowCapability;
    }

    // Asserts on Taskmaster threads will fail silently so we need to do the assertions on main thread if the code is triggered from Taskmaster
    private void assertOnMainThread(Runnable runnable) {
        mainHandler.post(runnable);
    }
}
