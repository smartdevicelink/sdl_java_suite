package com.smartdevicelink;

import com.smartdevicelink.transport.CustomTransport;
import com.smartdevicelink.transport.CustomTransportConfig;

import javax.ejb.Stateful;
import javax.websocket.EndpointConfig;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * This is a sample of how to get Java EE Bean to work with an SDL application
 * The code can be uncommented out with the proper gradle dependencies added
 */


@ServerEndpoint("/")
@Stateful(name = "SDLSessionEJB")
public class Main {

        Session session;
        static Thread thread = null, mainThread;
        static Object LOCK;
        static com.smartdevicelink.SdlService sdlService;
        CustomTransport websocket;
        @OnOpen
        public void onOpen (final Session session, EndpointConfig config) {
            websocket = new CustomTransport("http://localhost") {
                @Override
                public void onWrite(byte[] bytes, int offset, int length) {
                    try {
                        session.getBasicRemote().sendBinary(ByteBuffer.wrap(bytes));
                    }
                    catch (IOException e) {
                    }
                }
            };
            this.session = session;
            sdlService = new com.smartdevicelink.SdlService(new CustomTransportConfig(websocket), sdlServiceCallback);
            sdlService.start();
        }
        @OnMessage
        public void onMessage (ByteBuffer message, Session session) {
            websocket.onByteBufferReceived(message);
        }
        static final com.smartdevicelink.SdlService.SdlServiceCallback sdlServiceCallback = new com.smartdevicelink.SdlService.SdlServiceCallback() {
            @Override
            public void onEnd() {
            }
        };
}