package com.example.sqlite_gamesplayer.fragments;

import com.example.bmob_user.R;
import com.example.sqlite_gamesplayer.entity.Player;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateFragment extends Fragment implements android.view.View.OnClickListener{
	private EditText et_player,et_score,et_level;
	private UpdateFragmentListener updateFragmentListener;
	private Player player;
	public  static interface UpdateFragmentListener{
        public void update(Player player);
        public Player findById(int id);
	}
	public UpdateFragment() {
				
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
        try {
			updateFragmentListener = (UpdateFragmentListener) activity;
		} catch (ClassCastException e) {
			e.printStackTrace();
		}	
    }
	//不要在构造方法进行传参，所以自己写
	public static UpdateFragment newInstance(int id){
		UpdateFragment frag = new UpdateFragment();
		Bundle b = new Bundle();
		b.putInt("id", id);
		frag.setArguments(b);
		return frag;		
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		int id = getArguments().getInt("id");
		
		player = updateFragmentListener.findById(id);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_update, container,false);
		et_player = (EditText) view.findViewById(R.id.et_player);
		et_level = (EditText) view.findViewById(R.id.et_level);
		et_score = (EditText) view.findViewById(R.id.et_score);
		TextView tv_id = (TextView) view.findViewById(R.id.id);
		Button cancel = (Button) view.findViewById(R.id.cancel);
		Button save = (Button) view.findViewById(R.id.save);
		
		tv_id.setText(String.valueOf(player.getId()));		
		et_player.setText(player.getMyPlayer());
		et_score.setText(String.valueOf(player.getScore()));
		et_level.setText(String.valueOf(player.getLevel()));
		cancel.setOnClickListener(this);
		save.setOnClickListener(this);
		return view;
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.save:
			if(!TextUtils.isEmpty(et_player.getText().toString())
					&&!TextUtils.isEmpty(et_score.getText().toString())
					&&!TextUtils.isEmpty(et_level.getText().toString())){
				    save();
				    getActivity().getFragmentManager().popBackStack();
			}else{
				Toast.makeText(getActivity(), "输入信息不完全", Toast.LENGTH_SHORT).show();
			}

				
				
				break;

		case R.id.cancel:
			getActivity().getFragmentManager().popBackStack();
			break;
		}
	}
	private void save(){
		Player p = new Player();
		p.setId(player.getId());
		
		p.setMyPlayer(et_player.getText().toString());
		p.setLevel(Integer.parseInt(et_level.getText().toString()));
		p.setScore(Integer.parseInt(et_score.getText().toString()));
		updateFragmentListener.update(p);
		Log.d("UpdateFragment", "save");
		
	}
	public void onDetach() {
		super.onDetach();
		updateFragmentListener =null;
	}
	
}
