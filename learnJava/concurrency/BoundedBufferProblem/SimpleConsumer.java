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

public class SimpleConsumer<T> implements Consumer<T> {
	SharedBuffer<T> sb;
	private String name;
	
	public SimpleConsumer(String nm, SharedBuffer<T> buffer) {
		this.sb = buffer;
		this.name = nm;
	}

	public T get() {
		T item = null;
		try {
			item = sb.get();
			if (item == null) {
				System.out.println(name + ": couldn't get item from Buffer because it is Empty.");	
			} else {
				System.out.println(name + ": got " + item + " from Buffer");
			}
		} catch (InterruptedException e) {
			System.out.println(name + ": Exception occured in getting item from buffer: " + e);
		}
		return item;	
	}
}
