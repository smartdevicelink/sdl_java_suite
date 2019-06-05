/*
 * Copyright (C) 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 * Note: This file has been modified from its original form.
 * Site with example code: https://developer.android.com/reference/java/util/concurrent/ThreadPoolExecutor
 */

package com.smartdevicelink.managers.screen.choiceset;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class PausableThreadPoolExecutor extends ThreadPoolExecutor {

	private boolean isPaused;
	private ReentrantLock threadLock;
	private Condition condition;

	PausableThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
		threadLock = new ReentrantLock();
		condition = threadLock.newCondition();
	}

	protected void beforeExecute(Thread t, Runnable r) {
		super.beforeExecute(t, r);
		threadLock.lock();
		try {
			while (isPaused) condition.await();
		} catch (InterruptedException ie) {
			t.interrupt();
		} finally {
			threadLock.unlock();
		}
	}

	void pause() {
		threadLock.lock();
		try {
			isPaused = true;
		} finally {
			threadLock.unlock();
		}
	}

	void resume() {
		threadLock.lock();
		try {
			isPaused = false;
			condition.signalAll();
		} finally {
			threadLock.unlock();
		}
	}

}
