/*
 * Copyright (C) 2015 Harsh Vardhan
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

package org.learnSpring.DI;

import java.util.ArrayList;
import java.util.List;

public class Song {
	private String songName;
	private ArrayList<String> singers;
		
	/* Constructors */
	public Song() {
		
	}

	public Song(String songName) {
		setSongName(songName);
	}

	public Song(List<String> singers) {
		setSingers(singers);
	}
	
	public Song(String songName, List<String> singers) {
		setSongName(songName);
		setSingers(singers);
	}
	
	/* Getters and Setters */
	public String getSongName() {
		return this.songName;
	}
	
	public List<String> getSingersName() {
		return (List<String>) this.singers.clone();
	}
	
	public void setSongName(String songName) {
		this.songName = songName;
	}
	
	public void setSingers(List<String> singers) {
		this.singers = (ArrayList<String>) singers;
	}
	
	public void setSingers(String[] singers) {
		this.singers = new ArrayList(singers.length);
		for(String singer: singers) {
			this.singers.add(singer);
		}
	}
	
	/* Display Function */
	public void showDetails() {
		System.out.println("Song Name:" + songName );
		System.out.print("Singers:'");
		for(String singer: singers) {
			 System.out.print(singer + " ");
		} 
		System.out.println("");
	}
}
