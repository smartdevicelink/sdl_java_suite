/*
 *  Copyright (c) 2019. Livio, Inc.
 *  All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *  Redistributions of source code must retain the above copyright notice, this
 *  list of conditions and the following disclaimer.
 *
 *  Redistributions in binary form must reproduce the above copyright notice,
 *  this list of conditions and the following
 *  disclaimer in the documentation and/or other materials provided with the
 *  distribution.
 *
 *  Neither the name of the Livio Inc. nor the names of its contributors
 *  may be used to endorse or promote products derived from this software
 *  without specific prior written permission.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 *  AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 *  ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 *  LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 *  SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 *  INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 *  CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 *  ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 *  POSSIBILITY OF SUCH DAMAGE.
 */

package com.smartdevicelink.java;

import com.smartdevicelink.transport.WebSocketServerConfig;
import com.smartdevicelink.util.DebugTool;

public class Main {
    private static final String TAG = "Main";
    private static Thread thread = null;
    private static Thread mainThread;
    private static final Object LOCK = new Object();

    private static SdlService sdlService;

    public static void main(String[] args) {
        //Enable DebugTool
        DebugTool.enableDebugTool();

        mainThread = Thread.currentThread();
        startSdlService();

        while(!mainThread.isInterrupted()) {
            try {
                synchronized (LOCK) {
                    LOCK.wait();
                }
                System.gc();
                Thread.sleep(500);
                DebugTool.logInfo(TAG,  "Attempting to start SDL Service again");
                startSdlService();
                DebugTool.logInfo(TAG, "SdlService started");

            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    private static final SdlService.SdlServiceCallback serviceCallback = new SdlService.SdlServiceCallback() {
        @Override
        public void onEnd() {
            if (thread != null) {
                thread.interrupt();
                thread = null;
            }
            synchronized (LOCK) {
                LOCK.notify();
            }

        }
    };

    private static void startSdlService() {

        thread = new Thread(new Runnable() {

            @Override
            public void run() {
                DebugTool.logInfo(TAG, "Starting SDL Service");
                sdlService  = new SdlService(new WebSocketServerConfig(5432, -1), serviceCallback);
                sdlService.start();

                System.gc();

            }
        });
        thread.start();
    }
}
