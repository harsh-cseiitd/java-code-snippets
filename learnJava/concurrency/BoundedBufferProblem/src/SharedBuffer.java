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

import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;


public class SharedBuffer<T> {
	ArrayList<T> buffer ;
	Semaphore  produceMutex;
	Semaphore  consumeMutex;
	AtomicInteger nextWriteIndex;
	AtomicInteger currentReadIndex;
	AtomicInteger capacity;

	SharedBuffer(int bufferSize) {
		this.produceMutex = new Semaphore(1);
		this.consumeMutex = new Semaphore(1);
		this.nextWriteIndex    = new AtomicInteger();
		this.currentReadIndex  = new AtomicInteger();
		this.capacity          = new AtomicInteger(bufferSize);
		this.buffer            = new ArrayList<T>(this.capacity.get());
	}

	public boolean isFull() {
		if ((nextWriteIndex.get() - currentReadIndex.get() == capacity.get()-1)||
				(nextWriteIndex.get() == currentReadIndex.get() -1)) {
			return true;
		}
		return false;		
	}
	
	public boolean isEmpty() {
		if (nextWriteIndex.get() == currentReadIndex.get()) {
			return true;
		}
		return false;
	}

	public boolean add(T item) throws InterruptedException {
		if (isFull()) {
			return false;
		}
		this.produceMutex.acquire();
		this.buffer.add(nextWriteIndex.get(), item);
		nextWriteIndex.getAndSet(nextPutIndex()) ;
		this.produceMutex.release();
		return true;
	}
	
	public T get() throws InterruptedException{
		if (isEmpty()) {
			return null;
		} else {
			this.consumeMutex.acquire();
			T obj = this.buffer.get(this.currentReadIndex.get());
			this.buffer.set(this.currentReadIndex.get(), null);
			currentReadIndex.getAndSet(nextGetIndex()) ;
			this.consumeMutex.release();
			return obj;
		}
	}
	
	private int nextPutIndex() {
		return (nextWriteIndex.get() + 1)% this.capacity.get();
	}
	
	private int nextGetIndex() {
		return (currentReadIndex.get() + 1)% this.capacity.get();
	}

}
