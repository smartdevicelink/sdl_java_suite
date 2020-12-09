package com.smartdevicelink.managers.file;

/**
 * Created by Bilal Alsharifi on 12/2/20.
 */
class DispatchGroup {
    private int count;
    private Runnable runnable;

    DispatchGroup() {
        count = 0;
    }

    synchronized void enter() {
        count++;
    }

    synchronized void leave() {
        count--;
        run();
    }

    void notify(Runnable runnable) {
        this.runnable = runnable;
        run();
    }

    private void run() {
        if (count <= 0 && runnable != null) {
            runnable.run();
        }
    }
}
