package com.smartdevicelink.managers.screen;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.livio.taskmaster.Taskmaster;
import com.smartdevicelink.managers.AlertCompletionListener;
import com.smartdevicelink.managers.ISdl;
import com.smartdevicelink.managers.file.FileManager;
import com.smartdevicelink.managers.lifecycle.OnSystemCapabilityListener;
import com.smartdevicelink.managers.lifecycle.SystemCapabilityManager;
import com.smartdevicelink.managers.permission.PermissionManager;
import com.smartdevicelink.proxy.rpc.DisplayCapability;
import com.smartdevicelink.proxy.rpc.ImageField;
import com.smartdevicelink.proxy.rpc.SoftButtonCapabilities;
import com.smartdevicelink.proxy.rpc.TextField;
import com.smartdevicelink.proxy.rpc.WindowCapability;
import com.smartdevicelink.proxy.rpc.enums.ImageFieldName;
import com.smartdevicelink.proxy.rpc.enums.SystemCapabilityType;
import com.smartdevicelink.proxy.rpc.enums.TextFieldName;
import com.smartdevicelink.test.TestValues;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class AlertManagerTest {
    AlertManager alertManager;

    @Before
    public void setUp() throws Exception {
        Context mTestContext = getInstrumentation().getContext();

        // mock things
        ISdl internalInterface = mock(ISdl.class);
        FileManager fileManager = mock(FileManager.class);
        PermissionManager permissionManager = mock(PermissionManager.class);

        when(internalInterface.getPermissionManager()).thenReturn(permissionManager);

        Taskmaster taskmaster = new Taskmaster.Builder().build();
        taskmaster.start();
        when(internalInterface.getTaskmaster()).thenReturn(taskmaster);

        Answer<Void> onSystemCapabilityAnswer = new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                OnSystemCapabilityListener onSystemCapabilityListener = (OnSystemCapabilityListener) args[1];
                WindowCapability windowCapability = getWindowCapability(3);
                DisplayCapability displayCapability = new DisplayCapability();
                displayCapability.setWindowCapabilities(Collections.singletonList(windowCapability));
                List<DisplayCapability> capabilities = Collections.singletonList(displayCapability);
                onSystemCapabilityListener.onCapabilityRetrieved(capabilities);
                return null;
            }
        };

        SystemCapabilityManager systemCapabilityManager = mock(SystemCapabilityManager.class);
        doAnswer(onSystemCapabilityAnswer).when(systemCapabilityManager).addOnSystemCapabilityListener(eq(SystemCapabilityType.DISPLAYS), any(OnSystemCapabilityListener.class));
        doReturn(systemCapabilityManager).when(internalInterface).getSystemCapabilityManager();

        alertManager = new AlertManager(internalInterface, fileManager);
    }

    @Test
    public void testInstantiation() {
        assertNotNull(alertManager.defaultMainWindowCapability);
        assertNotNull(alertManager.nextCancelId);
    }

    @Test
    public void testPresentAlert() {
        AlertView.Builder builder = new AlertView.Builder();
        AlertView alertView = builder.build();
        alertManager.presentAlert(alertView, new AlertCompletionListener() {
            @Override
            public void onComplete(boolean success, Integer tryAgainTime) {

            }
        });
        assertTrue(alertManager.transactionQueue.getTasksAsList().size() == 1);
    }

    private WindowCapability getWindowCapability(int numberOfAlertFields) {

        TextField alertText1 = new TextField();
        alertText1.setName(TextFieldName.alertText1);
        TextField alertText2 = new TextField();
        alertText2.setName(TextFieldName.alertText2);
        TextField alertText3 = new TextField();
        alertText3.setName(TextFieldName.alertText3);
        TextField mainField4 = new TextField();
        mainField4.setName(TextFieldName.mainField4);

        List<TextField> textFieldList = new ArrayList<>();

        textFieldList.add(alertText1);
        textFieldList.add(alertText2);
        textFieldList.add(alertText3);

        List<TextField> returnList = new ArrayList<>();

        if (numberOfAlertFields > 0) {
            for (int i = 0; i < numberOfAlertFields; i++) {
                returnList.add(textFieldList.get(i));
            }
        }

        WindowCapability windowCapability = new WindowCapability();
        windowCapability.setTextFields(returnList);

        ImageField imageField = new ImageField();
        imageField.setName(ImageFieldName.alertIcon);
        List<ImageField> imageFieldList = new ArrayList<>();
        imageFieldList.add(imageField);
        windowCapability.setImageFields(imageFieldList);

        windowCapability.setImageFields(imageFieldList);

        SoftButtonCapabilities softButtonCapabilities = new SoftButtonCapabilities();
        softButtonCapabilities.setImageSupported(TestValues.GENERAL_BOOLEAN);
        softButtonCapabilities.setShortPressAvailable(TestValues.GENERAL_BOOLEAN);
        softButtonCapabilities.setLongPressAvailable(TestValues.GENERAL_BOOLEAN);
        softButtonCapabilities.setUpDownAvailable(TestValues.GENERAL_BOOLEAN);
        softButtonCapabilities.setTextSupported(TestValues.GENERAL_BOOLEAN);

        windowCapability.setSoftButtonCapabilities(Collections.singletonList(softButtonCapabilities));
        return windowCapability;
    }

}
