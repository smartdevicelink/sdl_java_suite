/*
 * Copyright (c) 2017 - 2019, SmartDeviceLink Consortium, Inc.
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
 * Neither the name of the SmartDeviceLink Consortium, Inc. nor the names of its
 * contributors may be used to endorse or promote products derived from this
 * software without specific prior written permission.
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
package com.smartdevicelink.streaming.video;

import com.smartdevicelink.proxy.rpc.ImageResolution;
import com.smartdevicelink.proxy.rpc.VideoStreamingCapability;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class VideoStreamingParametersTest {
    private VideoStreamingParameters params;
    private VideoStreamingCapability capability;
    private ImageResolution preferredResolution;

    @Before
    public void setUp() {
        params = new VideoStreamingParameters();
        capability = new VideoStreamingCapability();
    }

    @Test
    public void update_NullScale() {
        preferredResolution = new ImageResolution(800, 354);

        capability.setScale(null);
        capability.setPreferredResolution(preferredResolution);

        params.update(capability);

        int width = params.getResolution().getResolutionWidth();
        int height = params.getResolution().getResolutionHeight();

        assertEquals(800, width);
        assertEquals(354, height);
    }

    @Test
    public void update_Scale_1_Resolution_800_354() {
        preferredResolution = new ImageResolution(800, 354);

        capability.setScale(1.0);
        capability.setPreferredResolution(preferredResolution);

        params.update(capability);

        int width = params.getResolution().getResolutionWidth();
        int height = params.getResolution().getResolutionHeight();

        assertEquals(800, width);
        assertEquals(354, height);
    }

    @Test
    public void update_Scale_1_25_Resolution_1280_569() {
        preferredResolution = new ImageResolution(1280, 569);

        capability.setScale(1.25);
        capability.setPreferredResolution(preferredResolution);

        params.update(capability);

        int width = params.getResolution().getResolutionWidth();
        int height = params.getResolution().getResolutionHeight();

        assertEquals(1024, width);
        assertEquals(456, height);
    }

    @Test
    public void update_Scale_1_5_Resolution_1280_569() {
        preferredResolution = new ImageResolution(1280, 569);

        capability.setScale(1.5);
        capability.setPreferredResolution(preferredResolution);

        params.update(capability);

        int width = params.getResolution().getResolutionWidth();
        int height = params.getResolution().getResolutionHeight();

        assertEquals(854, width);
        assertEquals(380, height);
    }
}