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
import android.widget.Toast;

public class AdminMessageAlter extends Activity {
	
	private Cursor cursor;
	private EditText et_2;
	private EditText et_3;
	private EditText et_4;
	private EditText et_5;
	private EditText et_6;
	private EditText et_7;
	private EditText et_8;
	private EditText et_9;
	private EditText et_10;
	private EditText et_11;
	private Button bt_message_save;
	private Button bt_message_cancel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.admin_message_alter);
		SysApplication.getInstance().addActivity(this); 
		initialize();
		cursor=MainActivity.getDB().rawQuery("SELECT * FROM Employee WHERE Eno_id='"+
		Admin.getAdmin_employee_message()+"'", null);
		int resultsCount=cursor.getCount();
        if(resultsCount==0|!cursor.moveToFirst()){
        	return;
        }
		showdata();
		bt_message_cancel.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				
				Intent intent =new Intent(AdminMessageAlter.this,Admin.class);
				startActivity(intent);
				AdminMessageAlter.this.finish();
			}
			
		});
		bt_message_save.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				alterdata();
				
				Intent intent =new Intent(AdminMessageAlter.this,Admin.class);
				startActivity(intent);
				AdminMessageAlter.this.finish();
				Toast.makeText(AdminMessageAlter.this, "员工信息修改成功", 0).show();
			}
			
		});

		
		
	}



	private void initialize() {
		et_2=(EditText) findViewById(R.id.et_2);
		et_3=(EditText) findViewById(R.id.et_3);
		et_4=(EditText) findViewById(R.id.et_4);
		et_5=(EditText) findViewById(R.id.et_5);
		et_6=(EditText) findViewById(R.id.et_6);
		et_7=(EditText) findViewById(R.id.et_7);
		et_8=(EditText) findViewById(R.id.et_8);
		et_9=(EditText) findViewById(R.id.et_9);
		et_10=(EditText) findViewById(R.id.et_10);
		et_11=(EditText) findViewById(R.id.et_11);
		bt_message_save=(Button) findViewById(R.id.bt_message_save);
		bt_message_cancel=(Button) findViewById(R.id.bt_message_cancel);
		
	}

	private void showdata() {
		et_2.setText(cursor.getString(1));
		et_3.setText(cursor.getString(2));
		et_4.setText(cursor.getString(3));
		et_5.setText(cursor.getString(4));
		et_6.setText(cursor.getString(5));
		et_7.setText(cursor.getString(6));
		et_8.setText(cursor.getString(7));
		et_9.setText(cursor.getString(8));
		et_10.setText(cursor.getString(9));
		et_11.setText(cursor.getString(10));
		
	}
	private void alterdata() {

		MainActivity.getDB().execSQL("UPDATE Employee SET Ename=?,Esex=?,Ebirthday=?," +
				"Enative_place=?,Eeducation=?,Ehiredata=?,Dname=?,Eposition=?," +
				"Eaddress=?,Etel=? WHERE Eno_id=?", new String[]{et_2.getText().toString(),et_3.getText().toString(),
				et_4.getText().toString(),et_5.getText().toString(),et_6.getText().toString(),
				et_7.getText().toString(),et_8.getText().toString(),et_9.getText().toString(),
				et_10.getText().toString(),et_11.getText().toString(),Admin.getAdmin_employee_message()});
	}

	
}
