/**
 * This is a sample of how to get Java EE Bean to work with an SDL application
 * The code can be uncommented out with the proper gradle dependencies added
 */


//    @ServerEndpoint("/")
 //   @Stateful(name = "SDLSessionEJB")
    public class Main {

  /*      Session session;
        static Thread thread = null, mainThread;
        static Object LOCK;

        static SdlService sdlService;
        CustomTransport websocket;

        @OnOpen
        public void onOpen (Session session, EndpointConfig config) {
            websocket = new CustomTransport("http://localhost") {
                @Override
                public void onWrite(byte[] bytes) {
                    try {
                        session.getBasicRemote().sendBinary(ByteBuffer.wrap(bytes));
                    }
                    catch (IOException e) {

                    }
                }
            };
            this.session = session;
            SdlService sdlMain = new SdlMain(websocket, sdlServiceCallback);
            sdlMain.start();
        }

        @OnMessage
        public void onMessage (ByteBuffer message, Session session) {
            websocket.onByteBufferReceived(message);
        }


        static final SdlService.SdlServiceCallback sdlServiceCallback = new SdlService.SdlServiceCallback() {
            @Override
            public void onEnd() {

            }
        };
        */
    }
