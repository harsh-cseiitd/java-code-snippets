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

public class Movie {
	private String movieName;
	private String directorName;
	private String budget;
	private List<Song> movieSongs = null ;
	
	/* Constructors */
	public Movie() {

	}
	
	public Movie(List<Song> songs) {
		setMovieSongs(songs);
	}
	
	public Movie(String movieName, String directorName, String budget) {
		setMovieName(movieName);
		setDirectorName(directorName);
		setBudget(budget);
	}
	
	public Movie(String movieName, String directorName, String budget, List<Song> songs) {
		setMovieName(movieName);
		setDirectorName(directorName);
		setBudget(budget);
		setMovieSongs(songs);
	}
	
	/* Getters and Setters */
	
	public String getMovieName() {
		return this.movieName;
	}
	
	public String getDirectorName() {
		return this.directorName;
	}
	
	public String getBudget() {
		return this.budget;
	}
	
	public List<Song> getMovieSongs() {
		return this.movieSongs;
	}
	
	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}
	
	public void setDirectorName(String dd) {
		this.directorName = dd;
	}
	
	public void setBudget(String bb) {
		this.budget = bb;
	}
	
	public void setMovieSongs(List<Song> songs) {
		this.movieSongs = new ArrayList<Song> (songs.size());
		this.movieSongs.addAll(songs);
	}
	
	public void showDetails() {
		System.out.println("Movie Name: " + this.movieName);
        System.out.println("Movie directed by: " + this.directorName);
        System.out.println("Movie budget: " + this.budget );
        System.out.println("Movie Songs:");
        for(Song asong: this.movieSongs) {
        	asong.showDetails();
        }
	}
}
