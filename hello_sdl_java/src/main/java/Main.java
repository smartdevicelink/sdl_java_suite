import com.smartdevicelink.transport.WebSocketServerConfig;
import com.smartdevicelink.util.DebugTool;

public class Main {

    static Thread thread = null, mainThread;
    static Object LOCK;

    static SdlService sdlService;

    public static void main(String[] args) {
        mainThread = Thread.currentThread();
        LOCK = new Object();
        startSdlService();

        while(!mainThread.isInterrupted()) {
            try {
                synchronized (LOCK) {
                    LOCK.wait();
                }
                System.gc();
                Thread.sleep(500);
                DebugTool.logInfo( "Attempting to start SDL Service again");
                startSdlService();
                DebugTool.logInfo("SdlService started");

            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    static SdlService.SdlServiceCallback serviceCallback = new SdlService.SdlServiceCallback() {
        @Override
        public void onEnd() {
            thread.interrupt();
            thread = null;
            synchronized (LOCK) {
                LOCK.notify();
            }

        }
    };

    private static void startSdlService() {

        thread = new Thread(new Runnable() {

            @Override
            public void run() {
                DebugTool.logInfo("Starting SDL Service");
                sdlService  = new SdlService(new WebSocketServerConfig(5432, -1), serviceCallback);
                sdlService.start();

                System.gc();

            }
        });
        thread.start();
    }
}
