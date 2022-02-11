package com.smartdevicelink.managers.screen.menu;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.smartdevicelink.managers.ISdl;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.rpc.AddCommand;
import com.smartdevicelink.proxy.rpc.AddCommandResponse;
import com.smartdevicelink.proxy.rpc.DeleteCommand;
import com.smartdevicelink.proxy.rpc.DeleteCommandResponse;
import com.smartdevicelink.proxy.rpc.listeners.OnMultipleRequestListener;
import com.smartdevicelink.util.DebugTool;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
public class VoiceCommandUpdateOperationTests {

    private static final String TAG = "VoiceCommandReplaceOperationTests";
    VoiceCommandUpdateOperation voiceCommandUpdateOperation;
    ISdl internalInterface;
    VoiceCommandUpdateOperation.VoiceCommandChangesListener voiceCommandChangesListener;
    List<String> list1 = Collections.singletonList("Command one");
    List<String> list2 = Collections.singletonList("Command two");
    List<String> list3 = Collections.singletonList("Command three");
    List<String> list4 = Collections.singletonList("Command four");


    VoiceCommand voiceCommand1 = new VoiceCommand(list1, new VoiceCommandSelectionListener() {
        @Override
        public void onVoiceCommandSelected() {

        }
    });

    VoiceCommand voiceCommand2 = new VoiceCommand(list2, new VoiceCommandSelectionListener() {
        @Override
        public void onVoiceCommandSelected() {

        }
    });

    VoiceCommand voiceCommand3 = new VoiceCommand(list3, new VoiceCommandSelectionListener() {
        @Override
        public void onVoiceCommandSelected() {

        }
    });

    VoiceCommand voiceCommand4 = new VoiceCommand(list4, new VoiceCommandSelectionListener() {
        @Override
        public void onVoiceCommandSelected() {

        }
    });

    List<VoiceCommand> deleteList = new ArrayList<>();
    List<VoiceCommand> addList = new ArrayList<>();


    @Before
    public void setup() {
        deleteList.clear();
        addList.clear();
        voiceCommand1.setCommandId(1);
        voiceCommand2.setCommandId(2);
        voiceCommand3.setCommandId(3);
        voiceCommand4.setCommandId(4);
        deleteList.add(voiceCommand1);
        deleteList.add(voiceCommand2);
        addList.add(voiceCommand3);
        addList.add(voiceCommand4);
    }

    @Test
    public void verifyCanceledTaskDoesNotSendAnyRPCs() {
        internalInterface = mock(ISdl.class);

        voiceCommandChangesListener = new VoiceCommandUpdateOperation.VoiceCommandChangesListener() {
            @Override
            public void updateVoiceCommands(List<VoiceCommand> newCurrentVoiceCommands, HashMap<RPCRequest, String> errorObject) {

            }
        };

        voiceCommandUpdateOperation = new VoiceCommandUpdateOperation(internalInterface, deleteList, addList, voiceCommandChangesListener);
        voiceCommandUpdateOperation.cancelTask();
        voiceCommandUpdateOperation.onExecute();
        verify(internalInterface, times(0)).sendRPCs(any(List.class), any(OnMultipleRequestListener.class));
    }

    @Test
    public void verifyErrorObjectIsSetCorrectly() {
        internalInterface = mock(ISdl.class);

        doAnswer(new Answer<Void>() {
            public Void answer(InvocationOnMock invocation) throws Throwable {
                DeleteCommand deleteCommand = null;
                AddCommand addCommand = null;

                try {
                    deleteCommand = (DeleteCommand) ((List<Object>) invocation.getArguments()[0]).get(0);
                } catch (Exception e) {
                    DebugTool.logInfo(TAG, "not DeleteCommands: " + e);
                }

                try {
                    addCommand = (AddCommand) ((List<Object>) invocation.getArguments()[0]).get(0);
                } catch (Exception e) {
                    DebugTool.logInfo(TAG, "not AddCommands: " + e);
                }

                if (deleteCommand != null) {
                    DeleteCommandResponse badResponse = new DeleteCommandResponse();
                    badResponse.setSuccess(false);
                    List<DeleteCommand> deleteCommands = ((List<DeleteCommand>) invocation.getArguments()[0]);
                    for (DeleteCommand command : deleteCommands) {
                        badResponse.setCorrelationID(command.getCorrelationID());
                        ((OnMultipleRequestListener) invocation.getArguments()[1]).onResponse(command.getCorrelationID(), badResponse);
                    }
                } else if (addCommand != null) {
                    AddCommandResponse badResponse = new AddCommandResponse();
                    badResponse.setSuccess(false);
                    List<AddCommand> addCommands = ((List<AddCommand>) invocation.getArguments()[0]);
                    for (AddCommand command : addCommands) {
                        badResponse.setCorrelationID(command.getCorrelationID());
                        ((OnMultipleRequestListener) invocation.getArguments()[1]).onResponse(command.getCorrelationID(), badResponse);
                    }
                } else {
                    DebugTool.logInfo(TAG, "CallBacks failed");
                    return null;
                }
                ((OnMultipleRequestListener) invocation.getArguments()[1]).onFinished();
                return null;
            }
        }).when(internalInterface).sendRPCs(any(List.class), any(OnMultipleRequestListener.class));

        voiceCommandChangesListener = new VoiceCommandUpdateOperation.VoiceCommandChangesListener() {
            @Override
            public void updateVoiceCommands(List<VoiceCommand> newCurrentVoiceCommands, HashMap<RPCRequest, String> errorObject) {
                assertEquals(4, errorObject.size());
                assertEquals(2, newCurrentVoiceCommands.size());
                assertEquals(newCurrentVoiceCommands.get(0).getVoiceCommands().get(0), voiceCommand1.getVoiceCommands().get(0));
                assertEquals(newCurrentVoiceCommands.get(1).getVoiceCommands().get(0), voiceCommand2.getVoiceCommands().get(0));
            }
        };

        VoiceCommandUpdateOperation.VoiceCommandChangesListener listenerSpy = Mockito.spy(voiceCommandChangesListener);

        voiceCommandUpdateOperation = new VoiceCommandUpdateOperation(internalInterface, deleteList, addList, listenerSpy);
        voiceCommandUpdateOperation.onExecute();

        verify(listenerSpy, times(1)).updateVoiceCommands(any(List.class), any(HashMap.class));
    }

    @Test
    public void verifySuccessIsSetCorrectly() {
        internalInterface = mock(ISdl.class);

        doAnswer(new Answer<Void>() {
            public Void answer(InvocationOnMock invocation) throws Throwable {
                DeleteCommand deleteCommand = null;
                AddCommand addCommand = null;

                try {
                    deleteCommand = (DeleteCommand) ((List<Object>) invocation.getArguments()[0]).get(0);
                } catch (Exception e) {
                    DebugTool.logInfo(TAG, "not DeleteCommands: " + e);
                }

                try {
                    addCommand = (AddCommand) ((List<Object>) invocation.getArguments()[0]).get(0);
                } catch (Exception e) {
                    DebugTool.logInfo(TAG, "not AddCommands: " + e);
                }

                if (deleteCommand != null) {
                    DeleteCommandResponse successResponse = new DeleteCommandResponse();
                    successResponse.setSuccess(true);
                    List<DeleteCommand> deleteCommands = ((List<DeleteCommand>) invocation.getArguments()[0]);
                    for (DeleteCommand command : deleteCommands) {
                        successResponse.setCorrelationID(command.getCorrelationID());
                        ((OnMultipleRequestListener) invocation.getArguments()[1]).onResponse(command.getCorrelationID(), successResponse);
                    }
                } else if (addCommand != null) {
                    AddCommandResponse successResponse = new AddCommandResponse();
                    successResponse.setSuccess(true);
                    List<AddCommand> addCommands = ((List<AddCommand>) invocation.getArguments()[0]);
                    for (AddCommand command : addCommands) {
                        successResponse.setCorrelationID(command.getCorrelationID());
                        ((OnMultipleRequestListener) invocation.getArguments()[1]).onResponse(command.getCorrelationID(), successResponse);
                    }
                } else {
                    DebugTool.logInfo(TAG, "CallBacks failed");
                    return null;
                }
                ((OnMultipleRequestListener) invocation.getArguments()[1]).onFinished();
                return null;
            }
        }).when(internalInterface).sendRPCs(any(List.class), any(OnMultipleRequestListener.class));

        voiceCommandChangesListener = new VoiceCommandUpdateOperation.VoiceCommandChangesListener() {
            @Override
            public void updateVoiceCommands(List<VoiceCommand> newCurrentVoiceCommands, HashMap<RPCRequest, String> errorObject) {
                assertEquals(0, errorObject.size());
                assertEquals(2, newCurrentVoiceCommands.size());
                assertEquals(newCurrentVoiceCommands.get(0).getVoiceCommands().get(0), voiceCommand3.getVoiceCommands().get(0));
                assertEquals(newCurrentVoiceCommands.get(1).getVoiceCommands().get(0), voiceCommand4.getVoiceCommands().get(0));
            }
        };

        VoiceCommandUpdateOperation.VoiceCommandChangesListener listenerSpy = Mockito.spy(voiceCommandChangesListener);

        voiceCommandUpdateOperation = new VoiceCommandUpdateOperation(internalInterface, deleteList, addList, listenerSpy);
        voiceCommandUpdateOperation.onExecute();

        verify(listenerSpy, times(1)).updateVoiceCommands(any(List.class), any(HashMap.class));
    }

    @Test
    public void verifySendingAnEmptyListWillClearVoiceCommands() {
        internalInterface = mock(ISdl.class);

        doAnswer(new Answer<Void>() {
            public Void answer(InvocationOnMock invocation) throws Throwable {
                DeleteCommand deleteCommand = null;
                AddCommand addCommand = null;

                try {
                    deleteCommand = (DeleteCommand) ((List<Object>) invocation.getArguments()[0]).get(0);
                } catch (Exception e) {
                    DebugTool.logInfo(TAG, "not DeleteCommands: " + e);
                }

                try {
                    addCommand = (AddCommand) ((List<Object>) invocation.getArguments()[0]).get(0);
                } catch (Exception e) {
                    DebugTool.logInfo(TAG, "not AddCommands: " + e);
                }

                if (deleteCommand != null) {
                    DeleteCommandResponse successResponse = new DeleteCommandResponse();
                    successResponse.setSuccess(true);
                    List<DeleteCommand> deleteCommands = ((List<DeleteCommand>) invocation.getArguments()[0]);
                    for (DeleteCommand command : deleteCommands) {
                        successResponse.setCorrelationID(command.getCorrelationID());
                        ((OnMultipleRequestListener) invocation.getArguments()[1]).onResponse(command.getCorrelationID(), successResponse);
                    }
                } else if (addCommand != null) {
                    AddCommandResponse successResponse = new AddCommandResponse();
                    successResponse.setSuccess(true);
                    List<AddCommand> addCommands = ((List<AddCommand>) invocation.getArguments()[0]);
                    for (AddCommand command : addCommands) {
                        successResponse.setCorrelationID(command.getCorrelationID());
                        ((OnMultipleRequestListener) invocation.getArguments()[1]).onResponse(command.getCorrelationID(), successResponse);
                    }
                } else {
                    DebugTool.logInfo(TAG, "CallBacks failed");
                    return null;
                }
                ((OnMultipleRequestListener) invocation.getArguments()[1]).onFinished();
                return null;
            }
        }).when(internalInterface).sendRPCs(any(List.class), any(OnMultipleRequestListener.class));

        voiceCommandChangesListener = new VoiceCommandUpdateOperation.VoiceCommandChangesListener() {
            @Override
            public void updateVoiceCommands(List<VoiceCommand> newCurrentVoiceCommands, HashMap<RPCRequest, String> errorObject) {
                assertEquals(0, errorObject.size());
                assertEquals(0, newCurrentVoiceCommands.size());
            }
        };

        VoiceCommandUpdateOperation.VoiceCommandChangesListener listenerSpy = Mockito.spy(voiceCommandChangesListener);

        voiceCommandUpdateOperation = new VoiceCommandUpdateOperation(internalInterface, deleteList, new ArrayList<VoiceCommand>(), listenerSpy);
        voiceCommandUpdateOperation.onExecute();

        verify(listenerSpy, times(1)).updateVoiceCommands(any(List.class), any(HashMap.class));
    }


    @Test
    public void testVoiceCommandsInListNotInSecondList() {
        VoiceCommand command1 = new VoiceCommand(Collections.singletonList("Command 1"), null);
        VoiceCommand command2 = new VoiceCommand(Collections.singletonList("Command 2"), null);
        VoiceCommand command3 = new VoiceCommand(Collections.singletonList("Command 3"), null);

        VoiceCommand command1Clone = new VoiceCommand(Collections.singletonList("Command 1"), null);

        List<VoiceCommand> voiceCommandList = new ArrayList<>();
        voiceCommandList.add(command1);
        voiceCommandList.add(command2);

        List<VoiceCommand> voiceCommandList2 = new ArrayList<>();
        voiceCommandList2.add(command1Clone);
        voiceCommandList2.add(command3);
        VoiceCommandUpdateOperation voiceCommandUpdateOperation = new VoiceCommandUpdateOperation(internalInterface, null, null, null);

        List<VoiceCommand> differencesList = voiceCommandUpdateOperation.voiceCommandsInListNotInSecondList(voiceCommandList, voiceCommandList2);
        assertEquals(differencesList.size(), 1);
    }

    @Test
    public void testDelete() {
        internalInterface = mock(ISdl.class);

        VoiceCommand command1 = new VoiceCommand(Collections.singletonList("Command 1"), null);
        VoiceCommand command2 = new VoiceCommand(Collections.singletonList("Command 2"), null);

        VoiceCommand command1Clone = new VoiceCommand(Collections.singletonList("Command 1"), null);
        VoiceCommand command3 = new VoiceCommand(Collections.singletonList("Command 3"), null);

        List<VoiceCommand> voiceCommandList = new ArrayList<>();
        voiceCommandList.add(command1);
        voiceCommandList.add(command2);

        List<VoiceCommand> voiceCommandList2 = new ArrayList<>();
        voiceCommandList2.add(command1Clone);
        voiceCommandList2.add(command3);
        VoiceCommandUpdateOperation voiceCommandUpdateOperation = new VoiceCommandUpdateOperation(internalInterface, voiceCommandList, voiceCommandList2, null);
        voiceCommandUpdateOperation.onExecute();
        verify(internalInterface, times(1)).sendRPCs(ArgumentMatchers.<DeleteCommand>anyList(), any(OnMultipleRequestListener.class));
    }

}
