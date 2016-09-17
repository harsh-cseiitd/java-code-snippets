/*
 * Copyright (C) 2016 Harsh Vardhan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package simpleImpl;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

import core.ConcurrentBufffer;

public class ConcurrentBuffferImpl<T> implements  ConcurrentBufffer<T> {
	private static final int DEFAULT_CAPACITY   = 16;
	private static final int START_READ_INDEX   = 0;
	private static final int START_WRITE_INDEX  = -1;
	private int readIndex  = START_READ_INDEX;
	private int writeIndex = START_WRITE_INDEX;
	private static boolean FAIRNESS_IN_LOCKING = true;
	private int capacity = 0;
	private ArrayList<T> buffer;
	private final ReentrantLock readLock;
	private final ReentrantLock writeLock;


	public ConcurrentBuffferImpl() {
		this(DEFAULT_CAPACITY);
	}

	public ConcurrentBuffferImpl(int cap) {
		this.capacity  = cap;
		this.buffer    = new ArrayList<T>(capacity);
		this.readLock  = new ReentrantLock(FAIRNESS_IN_LOCKING);
		this.writeLock = new ReentrantLock(FAIRNESS_IN_LOCKING);
		for (int i = 0; i < this.capacity; i++) {
			this.buffer.add(null);
		}
		System.out.println("Queue: initialized with capacity " + capacity);
	}

	@Override
	public boolean isEmpty() {
		boolean result = false;
		if (acquireBothLocksWithoutBlocking()) {
			if (isEmptyUnsafe()) {
				result = true;
			}
			releaseBothLocksWithoutBlocking();
		}
		return result;
	}

	@Override
	public boolean isFull() {
		boolean result = false;
		if (acquireBothLocksWithoutBlocking()) {
			if (isFullUnsafe()) {
				result = true;
			}
			releaseBothLocksWithoutBlocking();
		}
		return result;
	}

	@Override
	public boolean put(T item) throws Exception {
		if (acquireBothLocksWithoutBlocking() && isFullUnsafe()) {
			releaseBothLocksWithoutBlocking();
			System.out.println("Queue: can't insert item " + item + " queue is full.");
			return false;
		}
		releaseReadLockWithoutBlocking();
		writeIndex = (writeIndex + 1)  % this.capacity;
		this.buffer.set(writeIndex, item);
		System.out.println("Queue: Stored item " + item + " at index:" + writeIndex);
		releaseWriteLockWithoutBlocking();
		return true;
	}

	@Override
	public T get() {
		if (acquireBothLocksWithoutBlocking() && isEmptyUnsafe()) {
			releaseBothLocksWithoutBlocking();
			System.out.println("Queue: can't get item, queue is empty.");
			return null;
		}
		T item = (T) this.buffer.get(readIndex);
		if (readIndex == writeIndex) {
			readIndex  = START_READ_INDEX;
			writeIndex = START_WRITE_INDEX;
		} else {
			readIndex = (readIndex + 1)  % this.capacity;
		}
		releaseBothLocksWithoutBlocking();
		System.out.println("Queue: read item " + item);
		return item;
	}

	@Override
	public int size() {
		return this.capacity;
	}

	private boolean isEmptyUnsafe() {
		boolean result = false;
		if ((readIndex  == START_READ_INDEX) &&
			(writeIndex == START_WRITE_INDEX)) {
			result = true;
		}
		return result;
	}


	private boolean isFullUnsafe() {
		boolean result = false;
		if (((readIndex  == START_READ_INDEX) && (writeIndex == capacity -1)) ||
				((writeIndex == readIndex -1)&& (writeIndex > START_WRITE_INDEX))) {
				result = true;
		}
		return result;
	}
	
	/*private boolean acquireReadLockWithoutBlocking() {
		boolean result = false;
		int timeout    = 10;
		int timeTaken  = 0;
		while (timeTaken <= timeout) {
            boolean gotReadLock  = false;
            try {
            	gotReadLock  = readLock.tryLock();
            } catch (Exception e) {
            	System.out.println("Exception occurs in acquiring read lock: " + e);
            } finally {
                if (gotReadLock) return true;
            }
            timeTaken = timeTaken + 2;
            try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				System.out.println("Exception occurs in thread sleep: " + e);
				return result;
			}
        }
		return result;
	}*/
	
	/*private boolean acquireWriteLockWithoutBlocking() {
		boolean result = false;
		int timeout    = 10;
		int timeTaken  = 0;
		while (timeTaken <= timeout) {
            boolean gotwriteLock  = false;
            try {
            	gotwriteLock  = writeLock.tryLock();
            } catch (Exception e) {
            	System.out.println("Exception occurs in acquiring read lock: " + e);
            } finally {
                if (gotwriteLock) return true;
            }
            timeTaken = timeTaken + 2;
            try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				System.out.println("Exception occurs in thread sleep: " + e);
				return result;
			}
        }
		return result;
	}*/
	
	private boolean acquireBothLocksWithoutBlocking() {
		boolean result = false;
		int timeout    = 10;
		int timeTaken  = 0;
		while (timeTaken <= timeout) {
			boolean gotReadLock  = false;
			boolean gotWriteLock = false;
			try {
				gotReadLock  = readLock.tryLock();
				gotWriteLock = writeLock.tryLock();
			} catch (Exception e) {
				System.out.println("Exception occurs in acquiring both locks: " + e);
			} finally {
				if (gotReadLock && gotWriteLock) return true;
				else if (gotReadLock) readLock.unlock();
				else if (gotWriteLock) writeLock.unlock();
			}
			timeTaken = timeTaken + 2;
			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				System.out.println("Exception occurs in thread sleep: " + e);
				return result;
			}
		}
		return result;
	}

	private boolean releaseReadLockWithoutBlocking() {
		if (readLock.isLocked()) {
			readLock.unlock();
		}
		return true;
	}

	private boolean releaseWriteLockWithoutBlocking() {
		if (writeLock.isLocked()) {
			writeLock.unlock();
		}
		return true;
	}

	private boolean releaseBothLocksWithoutBlocking() {
		boolean gotReadLock  = readLock.isLocked();
		boolean gotWriteLock = writeLock.isLocked();

		if (gotReadLock && gotWriteLock) {
			readLock.unlock();
			writeLock.unlock();
		} else if (gotReadLock) {
			readLock.unlock();
		} else if (gotWriteLock) {
			writeLock.unlock();
		}
		return true;
	}
}
