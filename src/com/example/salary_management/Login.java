package com.example.salary_management;

import com.example.salary_management.R;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TabWidget;
import android.widget.Toast;


public class Login extends TabActivity {
	
	private EditText et_user_employee;
	private EditText et_password_employee;
	private Button bt_employee;
	
	private EditText et_user_manage;
	private EditText et_password_manage;
	private Button bt_manage;
	//记录登录用户账号密码
	private static String user_id;
	private static String user_password;
	

    public static String getUser_id() {
		return user_id;
	}
	public static String getUser_password() {
		return user_password;
	}

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login_view);
        SysApplication.getInstance().addActivity(this); 
        loadTabHost();
        
        
     		
        		initialize();
        		bt_employee.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						verify_employee();
					}	
        		});
        		bt_manage.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						verify_manage();
					}	
        		});
        		
		
    }
//加载TabHost组件
    private void loadTabHost() {
    	TabHost tabHost=getTabHost();
		TabSpec page1=tabHost.newTabSpec("tab1").setIndicator("用户").setContent(R.id.employee_login);
		tabHost.addTab(page1);
		TabSpec page2=tabHost.newTabSpec("tab2").setIndicator("管理员").setContent(R.id.admin_login);
		tabHost.addTab(page2);
		
	}
	private void initialize() {
    	et_user_employee=(EditText) findViewById(R.id.ET_user_employee);
    	et_password_employee=(EditText) findViewById(R.id.ET_password_employee);
    	bt_employee=(Button) findViewById(R.id.BT_login_employee);
    	
    	et_user_manage=(EditText) findViewById(R.id.ET_user_admin);
    	et_password_manage=(EditText) findViewById(R.id.ET_password_admin);
    	bt_manage=(Button) findViewById(R.id.BT_login_manage);	
	}
    private void verify_employee() {
    	
    	if(et_user_employee.getText().toString().equals("")){
    		Toast toast =Toast.makeText(this, "账号不能为空。", 1);
			toast.setGravity(Gravity.BOTTOM,0, 0);
			toast.show();
    		return;
    	}
    	if(et_password_employee.getText().toString().equals("")){
    		Toast toast =Toast.makeText(this, "密码不能为空。", 1);
			toast.setGravity(Gravity.BOTTOM,0, 0);
			toast.show();
    		return;
    	}
		Cursor results =MainActivity.getDB().rawQuery("SELECT * FROM Emp_message", null);
		int resultCounts = results.getCount();
		if(resultCounts==0||!results.moveToFirst()){
			return;
		}
		boolean checkMessage=false;
		for(int i=0;i<resultCounts;i++){
			
			if(et_user_employee.getText().toString().equals(results.getString(0))&&
					et_password_employee.getText().toString().equals(results.getString(1))){
				checkMessage=true;
				user_id=et_user_employee.getText().toString();
				user_password=et_password_employee.getText().toString();
				
				Intent intent =new Intent(Login.this,Employee.class);
				startActivity(intent);
				Login.this.finish();
				return;
			}
			results.moveToNext();
		}
		//对信息错误的情况进行处理
		if(checkMessage==false){
			Toast toast =Toast.makeText(this, "账号或密码有误，请重新输入。", 1);
			toast.setGravity(Gravity.BOTTOM,0, 0);
			toast.show();
		}
		
	}	
    private void verify_manage() {
    	if(et_user_manage.getText().toString().equals("")){
    		Toast toast =Toast.makeText(this, "账号不能为空。", 1);
			toast.setGravity(Gravity.BOTTOM,0, 0);
			toast.show();
    		return;
    	}
    	if(et_password_manage.getText().toString().equals("")){
    		Toast toast =Toast.makeText(this, "密码不能为空。", 1);
			toast.setGravity(Gravity.BOTTOM,0, 0);
			toast.show();
    		return;
    	}
    	Cursor results =MainActivity.getDB().rawQuery("SELECT * FROM Admin_message", null);
		int resultCounts = results.getCount();
		if(resultCounts==0||!results.moveToFirst()){
			return;
		}
		boolean checkMessage=false;
		for(int i=0;i<resultCounts;i++){
			
			if(et_user_manage.getText().toString().equals(results.getString(0))&&
					et_password_manage.getText().toString().equals(results.getString(1))){
				checkMessage=true;
				user_id=et_user_manage.getText().toString();
				user_password=et_password_manage.getText().toString();
				
				Intent intent =new Intent(Login.this,Admin.class);
				startActivity(intent);
				Login.this.finish();
				return;
			}
			results.moveToNext();
		}
		//对信息错误的情况进行处理
		if(checkMessage==false){
			Toast toast =Toast.makeText(this, "账号或密码有误，请重新输入。", 1);
			toast.setGravity(Gravity.BOTTOM,0, 0);
			toast.show();
		}
		
	}


	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
