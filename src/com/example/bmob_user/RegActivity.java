package com.example.bmob_user;

import java.io.File;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;
import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegActivity extends Activity{

	private EditText et_name,et_password,et_email;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reg_layout);
		et_name = (EditText) findViewById(R.id.et_name);
		et_password = (EditText) findViewById(R.id.et_password);
		et_email = (EditText) findViewById(R.id.et_email);
	}
	//注册
	public void registClick(View v){
		final String name = et_name.getText().toString();
		final String pass = et_password.getText().toString();
		final String email = et_email.getText().toString();
		//获得图片文件的路径
		String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/3.jpg";
		Log.d("RegActivity", path);
		final BmobFile bf = new BmobFile(new File(path));
		
		bf.upload(this, new UploadFileListener() {
			
			@Override
			public void onSuccess() {
				User u = new User();
				u.setUsername(name);
				u.setPassword(pass);
				u.setEmail(email);
				u.setIcon(bf);
				//注册
				u.signUp(RegActivity.this, new SaveListener() {
					
					@Override
					public void onSuccess() {
						Toast.makeText(RegActivity.this, "注册完成", Toast.LENGTH_SHORT).show();
						RegActivity.this.finish();
					}
					
					@Override
					public void onFailure(int i, String s) {
						Toast.makeText(RegActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
						
					}
				});
			}
			
			@Override
			public void onFailure(int i, String s) {
				
			}
		});
	}
}
