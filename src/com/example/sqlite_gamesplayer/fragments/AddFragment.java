package com.example.sqlite_gamesplayer.fragments;

import com.example.bmob_user.R;
import com.example.sqlite_gamesplayer.entity.Player;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddFragment extends DialogFragment{

	private AddFragmentListener addFragmentListener;
	
	public static interface AddFragmentListener{
		public void add(Player player);
	}
	
	public AddFragment(){
		
	}
	
	public static AddFragment newInstance(){
		AddFragment frag = new AddFragment();
		return frag;
		
	}
	
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			 addFragmentListener = (AddFragmentListener) activity;
			} catch (ClassCastException e) {
				e.printStackTrace();
			}
	}
	
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		final View view = LayoutInflater.from(getActivity()).inflate(R.layout.create_gameplayer_dialog,null);
		
		return new AlertDialog.Builder(getActivity())
		        .setIcon(android.R.drawable.ic_input_add)
		        .setView(view)
		        .setTitle("新增游戏玩家")
		        .setPositiveButton("保存", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int arg1) {
						Log.d("AddFragment", "保存0");
						 
						EditText et_player = (EditText) view.findViewById(R.id.editText_player);
						EditText et_score = (EditText) view.findViewById(R.id.editText_score);
						EditText et_level = (EditText) view.findViewById(R.id.editText_level);
						Player player = new Player();
						if(!TextUtils.isEmpty(et_player.getText().toString())
								&&!TextUtils.isEmpty(et_score.getText().toString())
								&&!TextUtils.isEmpty(et_level.getText().toString())){
						
						player.setMyPlayer(et_player.getText().toString());
						player.setScore(Integer.parseInt(et_score.getText().toString()));
						player.setLevel(Integer.parseInt(et_level.getText().toString()));
						addFragmentListener.add(player);
						
						}else{
							Toast.makeText(getActivity(), "输入信息不完全", Toast.LENGTH_SHORT).show();
						
						}
						dialog.dismiss();
						Log.d("AddFragment", "保存");
						
					}
				})
				.setNegativeButton("取消", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int arg1) {
						dialog.dismiss();
					}
				}).create();
	}

}
