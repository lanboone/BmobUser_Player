package com.example.bmob_user;

import com.example.sqlite_gamesplayer.PlayerActivity;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private EditText et_name,et_pass;
	private TextView button_reg,button_set;
	private SharedPreferences pref;
	private SharedPreferences.Editor editor;
	private CheckBox rememberPass;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mainn);
		Bmob.initialize(this, "0451bd6a27fd29528132c95c25269fb5");
		et_name = (EditText) findViewById(R.id.editText_user);
		et_pass = (EditText) findViewById(R.id.editText_password);
		button_reg = (TextView) findViewById(R.id.button_reg);
		button_reg.setClickable(true);
		button_set = (TextView) findViewById(R.id.button_reset);
		button_set.setClickable(true);
		pref = PreferenceManager.getDefaultSharedPreferences(this);
		rememberPass = (CheckBox) findViewById(R.id.checkBox2);
		boolean isRemember = pref.getBoolean("remember_password", false);
		if(isRemember){
			//将账号和密码都设置到文本框中
			String account = pref.getString("account", "");
			String password = pref.getString("password", "");
			et_name.setText(account);
			et_name.setSelection(account.length());
			et_pass.setText(password);
			rememberPass.setChecked(true);
		}
		
	}
	public void regClick(View v){
		Intent intent = new Intent(this,RegActivity.class);
		startActivity(intent);
	}
	public void resetClick(View v){
		Intent intent = new Intent(this,ResetPassword.class);
		startActivity(intent);
	}
	public void loginClick(View v){
		final String name = et_name.getText().toString();
		final String pass = et_pass.getText().toString();
		final BmobUser bmobUser = new BmobUser();
		bmobUser.setUsername(name);
		bmobUser.setPassword(pass);
		bmobUser.login(this, new SaveListener() {
			
			@Override
			public void onSuccess() {
				User user = BmobUser.getCurrentUser(MainActivity.this, User.class);
				if(user.getEmailVerified()){
				Toast.makeText(MainActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
				editor = pref.edit();
				if(rememberPass.isChecked()){
					editor.putBoolean("remember_password", true);
					editor.putString("account", name);
					editor.putString("password", pass);
					et_name.setSelection(name.length());
				}else{
					editor.clear();
				}
				editor.commit();
				Intent intent = new Intent(MainActivity.this,PlayerActivity.class);
				intent.putExtra("user", user);
				startActivity(intent);
				}else{
				Toast.makeText(MainActivity.this, "用户未激活", Toast.LENGTH_SHORT).show();
				}
			}
			@Override
			public void onFailure(int arg0, String arg1) {
				Toast.makeText(MainActivity.this, "账号或密码不正确", Toast.LENGTH_SHORT).show();
			}
		});

	}


}
