package com.smartdevicelink.transport;

import android.os.Message;

public class RouterServiceMessageEmitter extends Thread {
        protected final Object QUEUE_LOCK = new Object();
        private boolean isHalted = false, isWaiting = false;
        private SendToRouterServiceTaskQueue queue;

        public RouterServiceMessageEmitter(SendToRouterServiceTaskQueue queue) {
            this.setName("PacketWriteTaskMaster");
            this.setDaemon(true);
            this.queue = queue;
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
            SendToRouterServiceTaskQueue queue = this.queue;
            if (queue != null) {
                return queue.poll();
            }
            return null;
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

    /**
     * Queue that will hold SendToRouterServiceTask in the order that they are received
     */
    public static class SendToRouterServiceTaskQueue extends Thread {
        final class Node<E> {
            final E item;
            SendToRouterServiceTaskQueue.Node<E> prev;
            SendToRouterServiceTaskQueue.Node<E> next;

            Node(E item, SendToRouterServiceTaskQueue.Node<E> previous,
                 SendToRouterServiceTaskQueue.Node<E> next) {
                this.item = item;
                this.prev = previous;
                this.next = next;
            }
        }

        private SendToRouterServiceTaskQueue.Node<SendToRouterServiceTask> head;
        private SendToRouterServiceTaskQueue.Node<SendToRouterServiceTask> tail;

        /**
         * This will take the given task and insert it at the tail of the queue
         *
         * @param task the task to be inserted at the tail of the queue
         */
        private void insertAtTail(SendToRouterServiceTask task) {
            if (task == null) {
                throw new NullPointerException();
            }
            SendToRouterServiceTaskQueue.Node<SendToRouterServiceTask> oldTail = tail;
            SendToRouterServiceTaskQueue.Node<SendToRouterServiceTask> newTail =
                    new SendToRouterServiceTaskQueue.Node<>(task, oldTail, null);
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
                    SendToRouterServiceTaskQueue.Node<SendToRouterServiceTask> taskNode =
                            new SendToRouterServiceTaskQueue.Node<>(task, head, tail);
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
        public void clear() {
            head = null;
            tail = null;
        }
    }

    interface Callback {
        boolean sendMessage(Message message, int retry);
    }
}
