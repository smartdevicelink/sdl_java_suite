/*
 * Copyright (c) 2019, Livio, Inc.
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
package com.smartdevicelink.transport;

import com.smartdevicelink.protocol.SdlPacket;
import com.smartdevicelink.util.DebugTool;

import static com.smartdevicelink.protocol.SdlProtocol.V1_HEADER_SIZE;
import static com.smartdevicelink.protocol.SdlProtocol.V1_V2_MTU_SIZE;
import static com.smartdevicelink.protocol.SdlProtocol.V2_HEADER_SIZE;
import static com.smartdevicelink.protocol.SdlProtocol.V3_V4_MTU_SIZE;


public class SdlPsm {
    private static final String TAG = "Sdl PSM";

    //Each state represents the byte that should be incoming
    public static final int START_STATE = 0x0;
    public static final int SERVICE_TYPE_STATE = 0x02;
    public static final int CONTROL_FRAME_INFO_STATE = 0x03;
    public static final int SESSION_ID_STATE = 0x04;
    public static final int DATA_SIZE_1_STATE = 0x05;
    public static final int DATA_SIZE_2_STATE = 0x06;
    public static final int DATA_SIZE_3_STATE = 0x07;
    public static final int DATA_SIZE_4_STATE = 0x08;
    public static final int MESSAGE_1_STATE = 0x09;
    public static final int MESSAGE_2_STATE = 0x0A;
    public static final int MESSAGE_3_STATE = 0x0B;
    public static final int MESSAGE_4_STATE = 0x0C;
    public static final int DATA_PUMP_STATE = 0x0D;
    public static final int FINISHED_STATE = 0xFF;
    public static final int ERROR_STATE = -1;


    private static final byte FIRST_FRAME_DATA_SIZE = 0x08;

    private static final int VERSION_MASK = 0xF0; //4 highest bits
    private static final int ENCRYPTION_MASK = 0x08; //4th lowest bit
    private static final int FRAME_TYPE_MASK = 0x07; //3 lowest bits


    int state;

    int version;
    boolean encrypted;
    int frameType;
    int serviceType;
    int controlFrameInfo;
    int sessionId;
    int dumpSize, dataLength;
    int messageId = 0;

    byte[] payload;

    public SdlPsm() {
        reset();
    }

    public boolean handleByte(byte data) {
        try {
            state = transitionOnInput(data, state);
        } catch (Exception e) {
            DebugTool.logError(TAG, "Exception thrown while parsing byte - " + data, e);
            state = ERROR_STATE;
            return false;
        }

        return state != ERROR_STATE;
    }

    private int transitionOnInput(byte rawByte, int state) {
        switch (state) {
            case START_STATE:
                version = (rawByte & (byte) VERSION_MASK) >> 4;
                encrypted = (1 == ((rawByte & (byte) ENCRYPTION_MASK) >> 3));
                frameType = rawByte & (byte) FRAME_TYPE_MASK;

                if ((version < 1 || version > 5)) {
                    //These are known versions supported by this library.
                    return ERROR_STATE;
                }

                if (frameType < SdlPacket.FRAME_TYPE_CONTROL || frameType > SdlPacket.FRAME_TYPE_CONSECUTIVE) {
                    return ERROR_STATE;
                }

                return SERVICE_TYPE_STATE;

            case SERVICE_TYPE_STATE:
                serviceType = (int) (rawByte & 0xFF);
                switch (serviceType) {
                    case 0x00: //SessionType.CONTROL:
                    case 0x07: //SessionType.RPC:
                    case 0x0A: //SessionType.PCM (Audio):
                    case 0x0B: //SessionType.NAV (Video):
                    case 0x0F: //SessionType.BULK (Hybrid):
                        return CONTROL_FRAME_INFO_STATE;
                    default:
                        return ERROR_STATE;
                }

            case CONTROL_FRAME_INFO_STATE:
                controlFrameInfo = (int) (rawByte & 0xFF);
                //Log.trace(TAG,"Frame Info: " + controlFrameInfo);
                switch (frameType) {
                    case SdlPacket.FRAME_TYPE_CONTROL:
                        /*if(frameInfo<FRAME_INFO_HEART_BEAT
                                || (frameInfo>FRAME_INFO_END_SERVICE_ACK
                                        && (frameInfo!=FRAME_INFO_SERVICE_DATA_ACK || frameInfo!=FRAME_INFO_HEART_BEAT_ACK))){
                            return ERROR_STATE;
                        }*/ //Although some bits are reserved...whatever
                        break;
                    case SdlPacket.FRAME_TYPE_SINGLE: //Fall through since they are both the same
                    case SdlPacket.FRAME_TYPE_FIRST:
                        if (controlFrameInfo != 0x00) {
                            return ERROR_STATE;
                        }
                        break;
                    case SdlPacket.FRAME_TYPE_CONSECUTIVE:
                        //It might be a good idea to check packet sequence numbers here
                        break;

                    default:
                        return ERROR_STATE;
                }
                return SESSION_ID_STATE;

            case SESSION_ID_STATE:
                sessionId = (int) (rawByte & 0xFF);
                return DATA_SIZE_1_STATE;

            case DATA_SIZE_1_STATE:
                //First data size byte
                //Log.d(TAG, "Data byte 1: " + rawByte);
                dataLength += ((int) (rawByte & 0xFF)) << 24; //3 bytes x 8 bits
                //Log.d(TAG, "Data Size 1 : " + dataLength);
                return DATA_SIZE_2_STATE;

            case DATA_SIZE_2_STATE:
                //Log.d(TAG, "Data byte 2: " + rawByte);
                dataLength += ((int) (rawByte & 0xFF)) << 16; //2 bytes x 8 bits
                //Log.d(TAG, "Data Size 2 : " + dataLength);
                return DATA_SIZE_3_STATE;

            case DATA_SIZE_3_STATE:
                //Log.d(TAG, "Data byte 3: " + rawByte);
                dataLength += ((int) (rawByte & 0xFF)) << 8; //1 byte x 8 bits
                //Log.d(TAG, "Data Size 3 : " + dataLength);
                return DATA_SIZE_4_STATE;

            case DATA_SIZE_4_STATE:
                //Log.d(TAG, "Data byte 4: " + rawByte);
                dataLength += ((int) rawByte) & 0xFF;
                //Log.trace(TAG, "Data Size: " + dataLength);
                //We should have data length now for the pump state
                switch (frameType) { //If all is correct we should break out of this switch statement
                    case SdlPacket.FRAME_TYPE_SINGLE:
                    case SdlPacket.FRAME_TYPE_CONSECUTIVE:
                        break;
                    case SdlPacket.FRAME_TYPE_CONTROL:
                        //Ok, well here's some interesting bit of knowledge. Because the start session request is from the phone with no knowledge of version it sends out
                        //a v1 packet. THEREFORE there is no message id field. **** Now you know and knowing is half the battle ****
                        if (version == 1 && controlFrameInfo == SdlPacket.FRAME_INFO_START_SERVICE) {
                            if (dataLength == 0) {
                                return FINISHED_STATE; //We are done if we don't have any payload
                            }
                            if (dataLength <= V1_V2_MTU_SIZE - V1_HEADER_SIZE) { // sizes from protocol/WiProProtocol.java
                                payload = new byte[dataLength];
                            } else {
                                return ERROR_STATE;
                            }
                            dumpSize = dataLength;
                            return DATA_PUMP_STATE;
                        }
                        break;

                    case SdlPacket.FRAME_TYPE_FIRST:
                        if (dataLength == FIRST_FRAME_DATA_SIZE || this.encrypted) {
                            //In a few production releases of core the first frame could be
                            //encrypted. Therefore it is not an error state if the first frame data
                            //length is greater than 8 in that case alone.
                            break;
                        }
                    default:
                        return ERROR_STATE;
                }
                switch (version) {
                    case 1:
                        //Version 1 packets will not have message id's
                        if (dataLength == 0) {
                            return FINISHED_STATE; //We are done if we don't have any payload
                        }
                        if (dataLength <= V1_V2_MTU_SIZE - V1_HEADER_SIZE) { // sizes from SDL protocol
                            payload = new byte[dataLength];
                        } else {
                            return ERROR_STATE;
                        }
                        dumpSize = dataLength;
                        return DATA_PUMP_STATE;
                    case 2:
                        if (dataLength <= V1_V2_MTU_SIZE - V2_HEADER_SIZE) {
                            return MESSAGE_1_STATE;
                        } else {
                            return ERROR_STATE;
                        }
                    case 3:
                    case 4:
                        if (dataLength <= V3_V4_MTU_SIZE - V2_HEADER_SIZE) {
                            return MESSAGE_1_STATE;
                        } else {
                            return ERROR_STATE;
                        }
                    default:
                        return MESSAGE_1_STATE;
                }

            case MESSAGE_1_STATE:
                messageId += ((int) (rawByte & 0xFF)) << 24; //3 bytes x 8 bits
                return MESSAGE_2_STATE;

            case MESSAGE_2_STATE:
                messageId += ((int) (rawByte & 0xFF)) << 16; //2 bytes x 8 bits
                return MESSAGE_3_STATE;

            case MESSAGE_3_STATE:
                messageId += ((int) (rawByte & 0xFF)) << 8; //1 byte x 8 bits
                return MESSAGE_4_STATE;

            case MESSAGE_4_STATE:
                messageId += ((int) rawByte) & 0xFF;

                if (dataLength == 0) {
                    return FINISHED_STATE; //We are done if we don't have any payload
                }
                try {
                    payload = new byte[dataLength];
                } catch (OutOfMemoryError oom) {
                    return ERROR_STATE;
                }
                dumpSize = dataLength;
                return DATA_PUMP_STATE;

            case DATA_PUMP_STATE:
                payload[dataLength - dumpSize] = rawByte;
                dumpSize--;
                //Do we have any more bytes to read in?
                if (dumpSize > 0) {
                    return DATA_PUMP_STATE;
                } else if (dumpSize == 0) {
                    return FINISHED_STATE;
                } else {
                    return ERROR_STATE;
                }
            case FINISHED_STATE: //We shouldn't be here...Should have been reset
            default:
                return ERROR_STATE;

        }

    }

    public SdlPacket getFormedPacket() {
        if (state == FINISHED_STATE) {
            //Log.trace(TAG, "Finished packet.");
            return new SdlPacket(version, encrypted, frameType,
                    serviceType, controlFrameInfo, sessionId,
                    dataLength, messageId, payload);
        } else {
            return null;
        }
    }

    public int getState() {
        return state;
    }

    public void reset() {
        version = 0;
        state = START_STATE;
        messageId = 0;
        dataLength = 0;
        frameType = 0x00; //Set it to null
        payload = null;
    }

}
