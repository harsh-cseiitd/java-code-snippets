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

public class SimpleProducer<T> implements Producer<T>  {
	private SharedBuffer<T> sb;
	private String name;
	
	public SimpleProducer(String nm, SharedBuffer<T> buffer) {
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
}
