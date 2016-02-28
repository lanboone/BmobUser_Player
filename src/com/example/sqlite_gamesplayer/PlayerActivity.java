package com.example.sqlite_gamesplayer;

import java.util.ArrayList;

import com.example.bmob_user.R;
import com.example.sqlite_gamesplayer.db.DatabaseAdatper;
import com.example.sqlite_gamesplayer.entity.Player;
import com.example.sqlite_gamesplayer.fragments.AddFragment;
import com.example.sqlite_gamesplayer.fragments.AddFragment.AddFragmentListener;
import com.example.sqlite_gamesplayer.fragments.GamePlayerFragment;
import com.example.sqlite_gamesplayer.fragments.GamePlayerFragment.GamePlayerFragmentListener;
import com.example.sqlite_gamesplayer.fragments.UpdateFragment;
import com.example.sqlite_gamesplayer.fragments.UpdateFragment.UpdateFragmentListener;

import android.os.Bundle;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

public class PlayerActivity extends Activity implements AddFragmentListener,GamePlayerFragmentListener,UpdateFragmentListener{

	private DatabaseAdatper dbAdatper;
	private GamePlayerFragment gamePlayerFragment;
	private UpdateFragment updateFragment;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		dbAdatper = new DatabaseAdatper(this);
		showGamePlayerFragment();
	}
	public void showGamePlayerFragment() {
		gamePlayerFragment = GamePlayerFragment.newInstance();
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.replace(R.id.main_layout, gamePlayerFragment);
		ft.addToBackStack(null);
		ft.commit();
	}

	@Override
	public void showUpdateFragment(int id) {
		updateFragment = UpdateFragment.newInstance(id);
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.replace(R.id.main_layout, updateFragment);
		ft.addToBackStack(null);
		ft.commit();
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			if(getFragmentManager().getBackStackEntryCount()==1){
				finish();
				return true;
			}else{
				getFragmentManager().popBackStack();
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case R.id.add:
			AddFragment createGamePlayerFragment = AddFragment.newInstance();
			createGamePlayerFragment.show(getFragmentManager(),null);
			break;
			
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void update(Player player) {
		dbAdatper.update(player);
		gamePlayerFragment.changedData();
	}

	@Override
	public Player findById(int id) {
		return dbAdatper.findById(id);
	}
	
	

	@Override
	public void delete(int id) {
		dbAdatper.delete(id);
		gamePlayerFragment.changedData();
	}

	@Override
	public ArrayList<Player> findAll() {
		return dbAdatper.findAll();
	}

	
	public void add(Player player) {
		dbAdatper.add(player);
		gamePlayerFragment.changedData();
		Log.d("AddFragment", "add");
	}

}
