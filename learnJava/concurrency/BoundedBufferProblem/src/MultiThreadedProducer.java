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

public class MultiThreadedProducer<T> implements Producer<T>, Runnable {
	private SharedBuffer<T> sb;
	private String name;
	private T[] items;
	
	public MultiThreadedProducer(String nm, SharedBuffer<T> buffer, T[] objs) {
		this.items = objs;
		this.name = nm;
		this.sb = buffer;
	}

	public void put(T item){
		try {
			if (sb.add(item)) {
				System.out.println(name + " added " + item + " in Buffer");
			} else {
				System.out.println(name + " couldn't add " + item + " in Buffer because it is full.");
			}
		} catch (InterruptedException e) {
			System.out.println(name + ": Exception occured in adding " + item + " " + e);
		}	
	}

	public void startProducing() {
		System.out.println("Starting : " + name);
		new Thread(this).start();
	}

	public void run() {
		for (int i = 0; i < items.length; i++) {
			put(items[i]);
		}
	}
}
