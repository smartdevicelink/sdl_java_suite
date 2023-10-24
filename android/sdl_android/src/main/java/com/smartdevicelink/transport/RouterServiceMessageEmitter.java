package com.smartdevicelink.transport;

import android.os.Message;

public class RouterServiceMessageEmitter extends Thread {

    protected final Object QUEUE_LOCK = new Object();
    private boolean isHalted = false, isWaiting = false;
    private final Callback callback;

    private Node<Message> head;
    private Node<Message> tail;

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
        clear();
    }

    /**
     * Insert the task in the queue where it belongs
     *
     * @param message the new Message that needs to be added to the queue to be
     *             handled
     */
    public void add(Message message) {
        synchronized (this) {
            if (message == null) {
                throw new NullPointerException();
            }
            //If we currently don't have anything in our queue
            if (head == null || tail == null) {
                Node<Message> taskNode = new Node<>(message, head, tail);
                head = taskNode;
                tail = taskNode;
            } else {
                //Add to tail
                Node<Message> oldTail = tail;
                Node<Message> newTail = new Node<>(message, oldTail, null);
                tail = newTail;
                if (head == null) {
                    head = newTail;
                } else {
                    oldTail.next = newTail;
                }
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
            if (head == null) {
                return null;
            } else {
                Node<Message> retValNode = head;
                Node<Message> newHead = head.next;
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


    private final static class Node<E> {
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

    protected interface Callback {
        boolean onMessageToSend(Message message);
    }
}
