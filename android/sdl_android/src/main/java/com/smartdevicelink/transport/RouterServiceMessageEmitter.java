package com.smartdevicelink.transport;

import android.os.Message;

public class RouterServiceMessageEmitter extends Thread {

    protected final Object QUEUE_LOCK = new Object();
    private boolean isHalted = false, isWaiting = false;

    private Node<SendToRouterServiceTask> head;
    private Node<SendToRouterServiceTask> tail;

    public RouterServiceMessageEmitter() {
        this.setName("RouterServiceMessageEmitter");
        this.setDaemon(true);
    }

    @Override
    public void run() {
        while (!isHalted) {
            try {
                SendToRouterServiceTask task;
                synchronized (QUEUE_LOCK) {
                    task = getNextTask();
                    if (task != null) {
                        task.run();
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

    protected SendToRouterServiceTask getNextTask() {
        return poll();
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
        clear();
    }


    /**
     * This will take the given task and insert it at the tail of the queue
     *
     * @param task the task to be inserted at the tail of the queue
     */
    private void insertAtTail(SendToRouterServiceTask task) {
        if (task == null) {
            throw new NullPointerException();
        }
        Node<SendToRouterServiceTask> oldTail = tail;
        Node<SendToRouterServiceTask> newTail = new Node<>(task, oldTail, null);
        tail = newTail;
        if (head == null) {
            head = newTail;
        } else {
            oldTail.next = newTail;
        }
    }

    /**
     * Insert the task in the queue where it belongs
     *
     * @param task the new SendToRouterServiceTask that needs to be added to the queue to be
     *             handled
     */
    public void add(SendToRouterServiceTask task) {
        synchronized (this) {
            if (task == null) {
                throw new NullPointerException();
            }
            //If we currently don't have anything in our queue
            if (head == null || tail == null) {
                Node<SendToRouterServiceTask> taskNode = new Node<>(task, head, tail);
                head = taskNode;
                tail = taskNode;
            } else {
                insertAtTail(task);
            }
        }
    }

    /**
     * Remove the head of the queue
     *
     * @return the old head of the queue
     */
    public SendToRouterServiceTask poll() {
        synchronized (this) {
            if (head == null) {
                return null;
            } else {
                Node<SendToRouterServiceTask> retValNode = head;
                Node<SendToRouterServiceTask> newHead = head.next;
                if (newHead == null) {
                    tail = null;
                }
                head = newHead;

                return retValNode.item;
            }
        }
    }

    /**
     * Currently only clears the head and the tail of the queue.
     */
    private void clear() {
        head = null;
        tail = null;
    }

    /**
     * A runnable task for sending messages to the SdlRouterService
     */
    public static class SendToRouterServiceTask implements Runnable {
        private Message message;

        private Callback callback;

        public SendToRouterServiceTask(Message message, Callback callback) {
            this.message = message;
            this.callback = callback;
        }

        @Override
        public void run() {
            if (callback == null) {
                return;
            }
            callback.sendMessage(message, 0);
        }
    }


    final class Node<E> {
        final E item;
        Node<E> prev;
        Node<E> next;

        Node(E item, Node<E> previous,
             Node<E> next) {
            this.item = item;
            this.prev = previous;
            this.next = next;
        }
    }

    interface Callback {
        boolean sendMessage(Message message, int retry);
    }
}
