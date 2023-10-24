package com.smartdevicelink.transport;

import android.os.Message;

import java.util.LinkedList;
import java.util.Queue;

public class RouterServiceMessageEmitter extends Thread {

    protected final Object QUEUE_LOCK = new Object();
    private boolean isHalted = false, isWaiting = false;
    private final Callback callback;
    private final Queue<Message> queue = new LinkedList<>();

    public RouterServiceMessageEmitter(Callback callback) {
        this.setName("RouterServiceMessageEmitter");
        this.setDaemon(true);
        this.callback = callback;
    }

    @Override
    public void run() {
        while (!isHalted) {
            try {
                Message message;
                synchronized (QUEUE_LOCK) {
                    message = getNextTask();
                    if (message != null && callback != null) {
                        callback.onMessageToSend(message);
                    } else {
                        isWaiting = true;
                        QUEUE_LOCK.wait();
                        isWaiting = false;
                    }
                }
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    protected void alert() {
        if (isWaiting) {
            synchronized (QUEUE_LOCK) {
                QUEUE_LOCK.notify();
            }
        }
    }

    protected void close() {
        this.isHalted = true;
        if (queue != null) {
            queue.clear();
        }
    }

    /**
     * Insert the task in the queue where it belongs
     *
     * @param message the new Message that needs to be added to the queue to be handled
     */
    public void add(Message message) {
        synchronized (this) {
            if (message != null && queue != null) {
                queue.add(message);
            }
        }
    }

    /**
     * Remove the head of the queue
     *
     * @return the old head of the queue
     */
    private Message getNextTask() {
        synchronized (this) {
            if (queue != null) {
                return queue.poll();
            } else {
                return null;
            }
        }
    }

    protected interface Callback {
        boolean onMessageToSend(Message message);
    }
}
