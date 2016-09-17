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

import core.ConcurrentBufffer;
import core.Consumer;

public class SimpleConsumerImpl<T> implements Consumer<T> {
	private String name;
	private ConcurrentBufffer<T> buffer;
	
	public SimpleConsumerImpl(String name, ConcurrentBufffer<T> buffer) {
		this.name   = name;
		this.buffer = buffer;
	}

	@Override
	public T get() {
		T item = null;
		try {
			item = buffer.get();
			if (item == null) {
				System.out.println(name + ": couldn't get item from buffer because it is empty.");	
			} else {
				System.out.println(name + ": got " + item + " from buffer");
			}
		} catch (Exception e) {
			System.out.println(name + ": Exception occured in getting item from buffer: " + e);
		}
		return item;	
	}
	
	public void start() {
		System.out.println("Consumer " + name + " starts.");
	}
	
	public void end() {
		System.out.println("Consumer " + name + " ends.");
	}
}
