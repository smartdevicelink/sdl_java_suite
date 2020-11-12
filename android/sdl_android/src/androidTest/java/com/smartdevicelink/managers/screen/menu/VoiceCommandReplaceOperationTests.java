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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Arrays;
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
public class VoiceCommandReplaceOperationTests {

    private static final String TAG = "VoiceCommandReplaceOperationTests";
    VoiceCommandReplaceOperation voiceCommandReplaceOperation;
    ISdl internalInterface;
    VoiceCommandReplaceOperation.VoiceCommandChangesListener voiceCommandChangesListener;
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

    @Test
    public void verifyCanceledTaskDoesNotSendAnyRPCs() {
        internalInterface = mock(ISdl.class);

        voiceCommandChangesListener = new VoiceCommandReplaceOperation.VoiceCommandChangesListener() {
            @Override
            public void updatedVoiceCommands(List<VoiceCommand> voiceCommands, HashMap<RPCRequest, String> errorObject) {

            }
        };

        voiceCommandReplaceOperation = new VoiceCommandReplaceOperation(internalInterface, Arrays.asList(voiceCommand1, voiceCommand2), Arrays.asList(voiceCommand3, voiceCommand4), voiceCommandChangesListener);
        voiceCommandReplaceOperation.cancelTask();
        voiceCommandReplaceOperation.onExecute();
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
                    deleteCommand = (DeleteCommand) ((List<Object>)invocation.getArguments()[0]).get(0);
                } catch (Exception e) {
                    DebugTool.logInfo(TAG, "not DeleteCommands: " + e);
                }

                try {
                    addCommand = (AddCommand) ((List<Object>)invocation.getArguments()[0]).get(0);
                } catch (Exception e) {
                    DebugTool.logInfo(TAG, "not AddCommands: " + e);
                }

                if (deleteCommand != null) {
                    DeleteCommandResponse badResponse = new DeleteCommandResponse();
                    badResponse.setSuccess(false);
                    List<DeleteCommand> deleteCommands = ((List<DeleteCommand>)invocation.getArguments()[0]);
                    for (DeleteCommand command : deleteCommands) {
                        badResponse.setCorrelationID(command.getCorrelationID());
                        ((OnMultipleRequestListener)invocation.getArguments()[1]).onResponse(command.getCorrelationID(), badResponse);
                    }
                } else if (addCommand != null) {
                    AddCommandResponse badResponse = new AddCommandResponse();
                    badResponse.setSuccess(false);
                    List<AddCommand> addCommands = ((List<AddCommand>)invocation.getArguments()[0]);
                    for (AddCommand command : addCommands) {
                        badResponse.setCorrelationID(command.getCorrelationID());
                        ((OnMultipleRequestListener)invocation.getArguments()[1]).onResponse(command.getCorrelationID(), badResponse);
                    }
                } else {
                    DebugTool.logInfo(TAG, "CallBacks failed");
                    return null;
                }
                ((OnMultipleRequestListener)invocation.getArguments()[1]).onFinished();
                return null;
            }
        }).when(internalInterface).sendRPCs(any(List.class), any(OnMultipleRequestListener.class));

        voiceCommandChangesListener = new VoiceCommandReplaceOperation.VoiceCommandChangesListener() {
            @Override
            public void updatedVoiceCommands(List<VoiceCommand> voiceCommands, HashMap<RPCRequest, String> errorObject) {
                assertEquals(4, errorObject.size());
                assertEquals(2, voiceCommands.size());
            }
        };

        voiceCommandReplaceOperation = new VoiceCommandReplaceOperation(internalInterface, Arrays.asList(voiceCommand1, voiceCommand2), Arrays.asList(voiceCommand3, voiceCommand4), voiceCommandChangesListener);
        voiceCommandReplaceOperation.onExecute();
    }
}
