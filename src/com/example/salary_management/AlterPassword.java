package com.example.salary_management;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AlterPassword extends Activity {
	
	private EditText et_accept_password;
	private EditText et_accept_new_password;
	private EditText et_confirm_new_password;
	private TextView tv_confirm_password;
	private TextView tv_confirm_new_password;
	private Button bt_commit;
	private Button bt_cancel;
	private Boolean tag1=false;
	private Boolean tag2=false;
	private static int MARKER=0;
	
	

	public static void setMARKER(int mARKER) {
		MARKER = mARKER;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.password_alter);
		SysApplication.getInstance().addActivity(this); 
		initialize();
		bt_cancel.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				if(MARKER==1){
				Intent intent=new Intent(AlterPassword.this,Employee.class);
				startActivity(intent);
				AlterPassword.this.finish();}
				if(MARKER==2){
					Intent intent=new Intent(AlterPassword.this,Admin.class);
					startActivity(intent);
					AlterPassword.this.finish();}
			}
			
		});
		bt_commit.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				tag1=false;
				tag2=false;
				checkmessage();
				if(tag1&&tag2){
					if(et_confirm_new_password
							.getText().toString().equals("")){
						Toast.makeText(AlterPassword.this, "密码不能为空", 1).show();
						return;
					}
					if(MARKER==1){
					MainActivity.getDB().execSQL("UPDATE Emp_message " +
							"SET Uemployee_password='"+et_confirm_new_password
							.getText().toString()+"' WHERE Uname_id='"+Login.getUser_id()+"'");
					
					Intent intent=new Intent(AlterPassword.this,Employee.class);
					startActivity(intent);
					AlterPassword.this.finish();
					Toast.makeText(AlterPassword.this, "密码设置成功", 1).show();
					}
					if(MARKER==2){
						MainActivity.getDB().execSQL("UPDATE Admin_message " +
								"SET Apassword='"+et_confirm_new_password
								.getText().toString()+"' WHERE Aname_id='admin'");
						Intent intent=new Intent(AlterPassword.this,Admin.class);
						startActivity(intent);
						AlterPassword.this.finish();
						Toast.makeText(AlterPassword.this, "密码设定成功", 1).show();
					}
				}
			}
			
		});
		
	}

	private void checkmessage() {
		if(MARKER==1){
		if(et_accept_password.getText().toString().equals(Login.getUser_password())){
			tv_confirm_password.setText("原密码正确");
			tv_confirm_password.setTextColor(android.graphics.Color.GREEN);
			tag1=true;
		}
		else{
			tv_confirm_password.setText("原密码错误");
			tv_confirm_password.setTextColor(android.graphics.Color.RED);
			tag1=false;
		}
		if(tag1){
		if(et_accept_new_password.getText().toString().equals(et_confirm_new_password.getText().toString())){
			tv_confirm_new_password.setText("重复正确");
			tv_confirm_new_password.setTextColor(android.graphics.Color.GREEN);
			tag2=true;
		}
		else{
			tv_confirm_new_password.setText("重复错误");
			tv_confirm_new_password.setTextColor(android.graphics.Color.RED);
			tag2=false;
		}}}
		if(MARKER==2){
			Cursor cursor=MainActivity.getDB().rawQuery("SELECT Apassword FROM Admin_message", null);
			if(!cursor.moveToFirst()){
				return;
			}
			if(et_accept_password.getText().toString().equals(cursor.getString(0))){
				tv_confirm_password.setText("原密码正确");
				tv_confirm_password.setTextColor(android.graphics.Color.GREEN);
				tag1=true;
			}
			else{
				tv_confirm_password.setText("原密码错误");
				tv_confirm_password.setTextColor(android.graphics.Color.RED);
				tag1=false;
			}
			if(tag1){
			if(et_accept_new_password.getText().toString().equals(et_confirm_new_password.getText().toString())){
				tv_confirm_new_password.setText("重复正确");
				tv_confirm_new_password.setTextColor(android.graphics.Color.GREEN);
				tag2=true;
			}
			else{
				tv_confirm_new_password.setText("重复错误");
				tv_confirm_new_password.setTextColor(android.graphics.Color.RED);
				tag2=false;
			}}
		}
		
	}

	private void initialize() {
		bt_commit=(Button) findViewById(R.id.bt_commit);
		bt_cancel=(Button) findViewById(R.id.bt_cancel);
		et_accept_password=(EditText) findViewById(R.id.et_accept_password);
		et_accept_new_password=(EditText) findViewById(R.id.et_accept_new_password);
		et_confirm_new_password=(EditText) findViewById(R.id.et_confirm_new_password);
		tv_confirm_password=(TextView) findViewById(R.id.tv_confirm_password);
		tv_confirm_new_password=(TextView) findViewById(R.id.tv_confirm_new_password);
		
	}

}
