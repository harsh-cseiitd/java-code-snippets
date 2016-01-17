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

public class MultiThreadedProdConsTest {

	public static void main(String[] args) {
		SharedBuffer<Integer> sb = new SharedBuffer<Integer>(5);
		Integer[] array1 = new Integer[] {10, 20, 30,40,50};
		Integer[] array2 = new Integer[] {60, 70, 80,90,100};
		
		MultiThreadedProducer<Integer> prod1   = new MultiThreadedProducer<Integer>("Producer1", sb, array1);
		MultiThreadedProducer<Integer> prod2   = new MultiThreadedProducer<Integer>("Producer2", sb, array2);
		MultiThreadedConsumer<Integer> con     = new MultiThreadedConsumer<Integer>("Consumer", sb);
		
		prod1.startProducing();
		prod2.startProducing();
		con.startConsuming();
	}

}
