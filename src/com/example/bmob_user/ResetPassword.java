package com.example.bmob_user;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.ResetPasswordByEmailListener;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ResetPassword extends Activity{
	private EditText et_email;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reset_layout);
		et_email = (EditText) findViewById(R.id.et_email);
	}
	public void send(View v){
		final String email = et_email.getText().toString();
		BmobUser.resetPasswordByEmail(this, email, new ResetPasswordByEmailListener() {
			
			@Override
			public void onSuccess() {
				 Toast.makeText(ResetPassword.this,"重置密码请求成功，请到" + email + "邮箱进行密码重置操作",Toast.LENGTH_SHORT).show();
				 
			}
			
			@Override
			public void onFailure(int arg0, String arg1) {
				Toast.makeText(ResetPassword.this,"重置密码失败",Toast.LENGTH_SHORT).show();
			}
		});
	}

}
