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
import core.Producer;

public class SimpleProducerImpl<T> implements Producer<T> {
	private String name;
	private ConcurrentBufffer<T> buffer;
	
	public SimpleProducerImpl(String name, ConcurrentBufffer<T> buffer) {
		this.name   = name;
		this.buffer = buffer;
	}

	@Override
	public void put(T item) {
		try {
			boolean result = buffer.put(item);
			if (result) {
				System.out.println(name + " added " + item + " in buffer");
			} else {
				System.out.println(name + " couldn't add " + item + " in buffer because it is full.");
			}
		} catch (Exception e) {
			System.out.println(name + ": Exception occured in adding " + item + " " + e);
		}
	}
	
	public void start() {
		System.out.println("Producer " + name + " starts");
	}
	
	public void end() {
		System.out.println("Producer " + name + " ends.");
	}
}
