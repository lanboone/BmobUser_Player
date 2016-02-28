package com.example.sqlite_gamesplayer.db;

import android.provider.BaseColumns;

public final class GameMetaData {
	private GameMetaData(){
		
	}
	public static abstract class GamePlayer implements BaseColumns{
		public static final String TABLE_NAME = "player_table";
		public static final String MYPLAYER = "myplayer";
		public static final String SCORE = "score";
		public static final String LEVEL = "level";
	}

	
}
