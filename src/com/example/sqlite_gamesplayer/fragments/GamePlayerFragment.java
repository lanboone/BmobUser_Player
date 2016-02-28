package com.example.sqlite_gamesplayer.fragments;

import java.util.ArrayList;

import com.example.bmob_user.R;
import com.example.sqlite_gamesplayer.entity.Player;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class GamePlayerFragment extends Fragment{

	private GamePlayerFragmentListener gamePlayerFragmentListener;
	private GamePlayerAdapter gamePlayerAdapter;
	
	public static interface GamePlayerFragmentListener{
		public void showGamePlayerFragment();//显示界面
		public void showUpdateFragment(int id);
		public void delete(int id);
		public ArrayList<Player> findAll();
		
	}
	public GamePlayerFragment(){
		
	}
	public static GamePlayerFragment newInstance(){
		GamePlayerFragment frag = new GamePlayerFragment();
		return frag;		
	}
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		 try {
			 gamePlayerFragmentListener = (GamePlayerFragmentListener) activity;
			} catch (ClassCastException e) {
				e.printStackTrace();
			}
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		ArrayList<Player> players = gamePlayerFragmentListener.findAll();
		gamePlayerAdapter = new GamePlayerAdapter(getActivity(), players);
		
		super.onCreate(savedInstanceState);
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_game_player, container, false);
		ListView listView = (ListView) view.findViewById(R.id.listView);

		registerForContextMenu(listView);//注册上下文菜单，可以长按点击
		listView.setAdapter(gamePlayerAdapter);
		return view;
	}
	
	private static class GamePlayerAdapter extends BaseAdapter{
		private Context context;
		private ArrayList<Player> players;
		public GamePlayerAdapter(Context context,ArrayList<Player> players){
			this.context = context;
			this.players = players;
		}
		public void setGamePlayers(ArrayList<Player> players){
			this.players =players;			
		}
		@Override
		public int getCount() {
			return players.size();
		}
		@Override
		public Object getItem(int position) {
			return players.get(position);
		}
		@Override
		public long getItemId(int position) {
			return position;
		}
		@Override
		public View getView(int position, View convertView, ViewGroup arg2) {
			ViewHolder vh=null;
			if(convertView == null){
				convertView = LayoutInflater.from(context).inflate(R.layout.list_item_layout, null);
				vh = new ViewHolder();
				vh.tv_id = (TextView) convertView.findViewById(R.id.textView_id);
				vh.tv_player = (TextView) convertView.findViewById(R.id.textView_player);
				vh.tv_score = (TextView) convertView.findViewById(R.id.textView_score);
				vh.tv_level = (TextView) convertView.findViewById(R.id.textView_level);
				convertView.setTag(vh);
			}else{
				vh = (ViewHolder) convertView.getTag();
			}
			Player p = players.get(position);
			vh.tv_id.setText(String.valueOf(p.getId()));
			vh.tv_player.setText(p.getMyPlayer());
			vh.tv_score.setText(String.valueOf(p.getScore()));
			vh.tv_level.setText(String.valueOf(p.getLevel()));
			return convertView;
		}	
	class ViewHolder{
		TextView tv_id;
		TextView tv_player;
		TextView tv_score;
		TextView tv_level;
	    }
    }
	
	public void changedData(){
		gamePlayerAdapter.setGamePlayers(gamePlayerFragmentListener.findAll());
		gamePlayerAdapter.notifyDataSetChanged();
	}
	//长按菜单项
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.setHeaderTitle("修改/删除");
		menu.setHeaderIcon(android.R.drawable.ic_menu_edit);
		getActivity().getMenuInflater().inflate(R.menu.listview_context_menu, menu);;
	}
	//菜单的选择触发事件
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.delete_menu:
			AdapterView.AdapterContextMenuInfo info= (AdapterContextMenuInfo) item.getMenuInfo();
			TextView textView_id = (TextView)info.targetView.findViewById(R.id.textView_id);
			int id = Integer.parseInt(textView_id.getText().toString());
			gamePlayerFragmentListener.delete(id);
			changedData();
			break;
		case R.id.update_menu:
		    info= (AdapterContextMenuInfo) item.getMenuInfo();
			textView_id = (TextView)info.targetView.findViewById(R.id.textView_id);
			id = Integer.parseInt(textView_id.getText().toString());
			gamePlayerFragmentListener.showUpdateFragment(id);
			break;
		}
		return super.onContextItemSelected(item);
	}
	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
		gamePlayerFragmentListener =null;
	}
	
	
	
}
