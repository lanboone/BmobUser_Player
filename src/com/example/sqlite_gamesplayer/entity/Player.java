package com.example.sqlite_gamesplayer.entity;

public class Player {

	private int id;
	private String myplayer;
	private int score;
	private int level;
	public Player(){
		
	}
	public Player(String myplayer,int score,int level){
		this.myplayer = myplayer;
		this.score = score;
	    this.level = level;
	}
	public Player(int id,String myplayer,int score,int level){
		this.id = id;
		this.myplayer = myplayer;
		this.score = score;
	    this.level = level;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMyPlayer() {
		return myplayer;
	}
	public void setMyPlayer(String myplayer) {
		this.myplayer = myplayer;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	@Override
	public String toString() {
		return "Player [id=" + id + ", myplayer=" + myplayer + ", score=" + score
				+ ", level=" + level + "]";
	}
	
	
}
