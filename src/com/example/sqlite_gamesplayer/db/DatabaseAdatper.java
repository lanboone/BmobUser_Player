package com.example.sqlite_gamesplayer.db;

import java.util.ArrayList;

import com.example.sqlite_gamesplayer.entity.Player;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseAdatper {

	private DatabaseHelper dbHelper;
	public DatabaseAdatper(Context context){
		dbHelper = new DatabaseHelper(context);
	}
	
	public void add(Player player){
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(GameMetaData.GamePlayer.MYPLAYER,player.getMyPlayer());
		values.put(GameMetaData.GamePlayer.SCORE, player.getScore());
		values.put(GameMetaData.GamePlayer.LEVEL,player.getLevel());
		db.insert(GameMetaData.GamePlayer.TABLE_NAME, null, values);
	    db.close();
	}
	public void delete(int id){
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		String whereClause = GameMetaData.GamePlayer._ID+"=?";
		String[] whereArgs = {String.valueOf(id)};
	    db.delete(GameMetaData.GamePlayer.TABLE_NAME, whereClause, whereArgs);
	    db.close();
	}
	public void update(Player player){
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(GameMetaData.GamePlayer.MYPLAYER,player.getMyPlayer());
		values.put(GameMetaData.GamePlayer.SCORE, player.getScore());
		values.put(GameMetaData.GamePlayer.LEVEL,player.getLevel());
		String whereClause = GameMetaData.GamePlayer._ID+"=?";
		String[] whereArgs = {String.valueOf(player.getId())};
		db.update(GameMetaData.GamePlayer.TABLE_NAME, values, whereClause, whereArgs);
		db.close();
	}
	public Player findById(int id){
		SQLiteDatabase db = dbHelper.getReadableDatabase();
//		String[] columns = {GameMetaData.GamePlayerTable._ID
//				,GameMetaData.GamePlayerTable.MYPLAYER
//				,GameMetaData.GamePlayerTable.SCORE
//				,GameMetaData.GamePlayerTable.LEVEL};
		//是否去除重复记录，表名，要查询的列，查询条件，查询条件的值，分组条件，分组条件的值，排序，分页条件
		Cursor c = db.query(true, GameMetaData.GamePlayer.TABLE_NAME
				, null,  GameMetaData.GamePlayer._ID+"=?"
				, new String[]{String.valueOf(id)}
		        , null, null, null, null, null);
		Player player = null;
		if(c.moveToNext()){
			player = new Player();
			player.setId(c.getInt(c.getColumnIndexOrThrow(GameMetaData.GamePlayer._ID)));
			player.setMyPlayer(c.getString(c.getColumnIndexOrThrow(GameMetaData.GamePlayer.MYPLAYER)));
			player.setScore(c.getInt(c.getColumnIndexOrThrow(GameMetaData.GamePlayer.SCORE)));
			player.setLevel(c.getInt(c.getColumnIndexOrThrow(GameMetaData.GamePlayer.LEVEL)));
			
		}
		c.close();
	    db.close();
		
		return player;
		
	}
	public ArrayList<Player> findAll(){
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		String sql = "select _id,myplayer,score,level from player_table order by score desc";
		ArrayList<Player> players = new ArrayList<Player>();
//      太少列就不用这样，直接null
//		String[] columns = {GameMetaData.GamePlayerTable._ID
//				,GameMetaData.GamePlayerTable.MYPLAYER
//				,GameMetaData.GamePlayerTable.SCORE
//				,GameMetaData.GamePlayerTable.LEVEL};
		Cursor c = db.rawQuery(sql, null);
				
		Player player = null;
		while(c.moveToNext()){
			player = new Player();
			player.setId(c.getInt(c.getColumnIndexOrThrow(GameMetaData.GamePlayer._ID)));
			player.setMyPlayer(c.getString(c.getColumnIndexOrThrow(GameMetaData.GamePlayer.MYPLAYER)));
			player.setScore(c.getInt(c.getColumnIndexOrThrow(GameMetaData.GamePlayer.SCORE)));
			player.setLevel(c.getInt(c.getColumnIndexOrThrow(GameMetaData.GamePlayer.LEVEL)));
			players.add(player);
		}
		c.close();
	    db.close();
		return players;
		
	}
	public int getCount(){
		int count = 0;
		String sql = "select count(_id) from player_table";
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor c = db.rawQuery(sql, null);
		c.moveToFirst();
		count =c.getInt(0);
		c.close();
		db.close();
		return count;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
