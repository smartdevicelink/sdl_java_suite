/***************************************************************************************************
 * Copyright © 2017 Xevo Inc.
 * Redistribution and use in source and binary forms, with or without modification, are permitted
 * provided that the following conditions are met:
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions
 *    and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of
 *    conditions and the following disclaimer in the documentation and/or other materials provided
 *    with the distribution.
 * 3. Neither the name of the copyright holder nor the names of its contributors may be used to
 *    endorse or promote products derived from this software without specific prior written
 *    permission.
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
 * WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 **************************************************************************************************/
package com.smartdevicelink.managers.video;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.test.platform.app.InstrumentationRegistry;

import com.smartdevicelink.managers.ISdl;
import com.smartdevicelink.managers.lifecycle.OnSystemCapabilityListener;
import com.smartdevicelink.managers.lifecycle.SystemCapabilityManager;
import com.smartdevicelink.proxy.rpc.HapticRect;
import com.smartdevicelink.proxy.rpc.Rectangle;
import com.smartdevicelink.proxy.rpc.SendHapticData;
import com.smartdevicelink.proxy.rpc.VideoStreamingCapability;
import com.smartdevicelink.proxy.rpc.enums.SystemCapabilityType;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created on 9/26/2017.
 */
@RunWith(MockitoJUnitRunner.Strict.class)
public class HapticInterfaceManagerTest extends TestCase {
    @Mock
    private ISdl interalInterface;

    @Captor
    private ArgumentCaptor<SendHapticData> captor;

    private HapticInterfaceManager hapticMgr;

    @Before
    public void setUp() throws Exception {
        hapticMgr = new HapticInterfaceManager(interalInterface);
    }

    @After
    public void tearDown() throws Exception {
        hapticMgr = null;
    }

    @Test
    public void testSetHapticData() throws Exception {
        List<HapticRect> rects = new ArrayList<>();
        Rectangle rect = new Rectangle();
        rect.setX(10f);
        rect.setY(10f);
        rect.setWidth(50f);
        rect.setHeight(20f);
        HapticRect hRect = new HapticRect();
        hRect.setRect(rect);
        rects.add(hRect);
        hapticMgr.setHapticData(rects);
        verify(interalInterface).sendRPC(any(SendHapticData.class));
    }

    @Test
    public void testRefreshHapticData() throws Exception {
        View root = createViews();
        hapticMgr.refreshHapticData(root);
        verify(interalInterface).sendRPC(captor.capture());
        SendHapticData data = captor.getValue();
        assertNotNull("SendHapticData RPC", data);
        List<HapticRect> list = data.getHapticRectData();
        assertNotNull("List", list);
        assertEquals("Haptic Rects", 4, list.size());
    }

    @Test
    public void testRefreshHapticDataNull() throws Exception {
        hapticMgr.refreshHapticData(null);
        verify(interalInterface).sendRPC(captor.capture());
        SendHapticData data = captor.getValue();
        assertNotNull("SendHapticData RPC", data);
        List<HapticRect> list = data.getHapticRectData();
        assertTrue("List", list.isEmpty());
    }

    @Test
    public void testRefreshHapticData_Scale_2_0() {
        double scale = 2.0;

        final int buttonX = 60;
        final int buttonY = 60;
        final int buttonWidth = 150;
        final int buttonHeight = 70;

        Rectangle expected = new Rectangle();
        expected.setX(120.0f);
        expected.setY(120.0f);
        expected.setWidth(300.0f);
        expected.setHeight(140.0f);

        VideoStreamingCapability capability = new VideoStreamingCapability();
        capability.setScale(scale);

        assertViewWithCapability(buttonX, buttonY, buttonWidth, buttonHeight, expected, capability);
    }


    @Test
    public void testRefreshHapticData_Scale_0_5() {
        double scale = 0.5;

        final int buttonX = 60;
        final int buttonY = 60;
        final int buttonWidth = 150;
        final int buttonHeight = 70;

        Rectangle expected = new Rectangle();
        expected.setX(30.0f);
        expected.setY(30.0f);
        expected.setWidth(75.0f);
        expected.setHeight(35.0f);

        VideoStreamingCapability capability = new VideoStreamingCapability();
        capability.setScale(scale);

        assertViewWithCapability(buttonX, buttonY, buttonWidth, buttonHeight, expected, capability);
    }



    @Test
    public void testRefreshHapticData_NullCapability() {
        final int buttonX = 60;
        final int buttonY = 60;
        final int buttonWidth = 150;
        final int buttonHeight = 70;

        Rectangle expected = new Rectangle();
        expected.setX(60.0f);
        expected.setY(60.0f);
        expected.setWidth(150.0f);
        expected.setHeight(70.0f);

        assertViewWithCapability(buttonX, buttonY, buttonWidth, buttonHeight, expected, null);
    }

    @Test
    public void testRefreshHapticData_NullScale() {
        final int buttonX = 60;
        final int buttonY = 60;
        final int buttonWidth = 150;
        final int buttonHeight = 70;

        Rectangle expected = new Rectangle();
        expected.setX(60.0f);
        expected.setY(60.0f);
        expected.setWidth(150.0f);
        expected.setHeight(70.0f);

        VideoStreamingCapability capability = new VideoStreamingCapability();
        capability.setScale(null);

        assertViewWithCapability(buttonX, buttonY, buttonWidth, buttonHeight, expected, capability);
    }

    @Test
    public void testRefreshWithUserData() throws Exception {
        List<HapticRect> rects = new ArrayList<>();
        Rectangle rect = new Rectangle();
        rect.setX(10f);
        rect.setY(10f);
        rect.setWidth(50f);
        rect.setHeight(20f);
        HapticRect hRect = new HapticRect();
        hRect.setRect(rect);
        rects.add(hRect);
        hapticMgr.setHapticData(rects);
        verify(interalInterface).sendRPC(any(SendHapticData.class));

        View root = createViews();
        hapticMgr.refreshHapticData(root);
        verify(interalInterface, times(1)).sendRPC(any(SendHapticData.class));
    }

    private View createViews() {

        View view = mock(View.class);

        ViewGroup parent1 = mock(ViewGroup.class);
        ViewGroup parent2 = mock(ViewGroup.class);

        when(parent1.getChildCount()).thenReturn(5);

        when(parent1.getChildAt(0)).thenReturn(view);
        when(parent1.getChildAt(1)).thenReturn(view);
        when(parent1.getChildAt(2)).thenReturn(view);
        when(parent1.getChildAt(3)).thenReturn(parent2);
        when(parent1.getChildAt(4)).thenReturn(view);

        when(parent2.getChildCount()).thenReturn(2);
        when(parent2.getChildAt(0)).thenReturn(view);
        when(parent2.getChildAt(1)).thenReturn(view);


        doAnswer(new Answer<Boolean>() {
            private int count = 0;

            @Override
            public Boolean answer(InvocationOnMock invocation) throws Throwable {
                int curCount = count++;
                return (curCount >= 1) && (curCount <= 4);
            }
        }).when(view).isClickable();

        return parent1;
    }


    private View createView(final int x, final int y, int w, int h) {
        View button = new Button(InstrumentationRegistry.getInstrumentation().getContext()) {
            @Override
            public void getLocationOnScreen(int[] outLocation) {
                super.getLocationOnScreen(outLocation);
                outLocation[0] = x;
                outLocation[1] = y;
            }
        };
        button.setClickable(true);
        button.setRight(w);
        button.setBottom(h);
        return button;
    }

    private void assertViewWithCapability(int x, int y, int w, int h, Rectangle expected, VideoStreamingCapability capability) {
        SystemCapabilityManager systemCapabilityManager = mock(SystemCapabilityManager.class);
        when(systemCapabilityManager.getCapability(eq(SystemCapabilityType.VIDEO_STREAMING), (OnSystemCapabilityListener) isNull(), anyBoolean())).thenReturn(capability);
        doReturn(systemCapabilityManager).when(interalInterface).getSystemCapabilityManager();

        View button = createView(x, y, w, h);

        hapticMgr.refreshHapticData(button);
        verify(interalInterface).sendRPC(captor.capture());

        SendHapticData data = captor.getValue();
        List<HapticRect> list = data.getHapticRectData();
        assertEquals(1, list.size());
        Rectangle current = list.get(0).getRect();

        assertEquals(expected.getX(), current.getX(), 0.0005);
        assertEquals(expected.getY(), current.getY(), 0.0005);
        assertEquals(expected.getWidth(), current.getWidth(), 0.0005);
        assertEquals(expected.getHeight(), current.getHeight(), 0.0005);
    }
}
