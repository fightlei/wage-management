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

public class AdminAddEmployee extends Activity {
	
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
		setContentView(R.layout.admin_add_emp);
		SysApplication.getInstance().addActivity(this); 
		initialize();
		bt_message_cancel.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				
				Intent intent =new Intent(AdminAddEmployee.this,Admin.class);
				startActivity(intent);
				AdminAddEmployee.this.finish();
			}
			
		});
		bt_message_save.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				
				Cursor cursor=MainActivity.getDB().rawQuery("SELECT Eno_id FROM Employee", null);
				cursor.moveToLast();
				Integer i=new Integer(cursor.getString(0));
				i=i+1;
				
				MainActivity.getDB().execSQL("INSERT INTO Employee (Eno_id,Ename,Esex,Ebirthday," +
						"Enative_place,Eeducation,Ehiredata,Dname,Eposition," +
						"Eaddress,Etel) VALUES(?,?,?,?,?,?,?,?,?,?,?)", 
						new String[]{i.toString(),et_2.getText().toString(),et_3.getText().toString(),
						et_4.getText().toString(),et_5.getText().toString(),et_6.getText().toString(),
						et_7.getText().toString(),et_8.getText().toString(),et_9.getText().toString(),
						et_10.getText().toString(),et_11.getText().toString()});
				
				
				
				Log.i("***$$$$$$$$$$$$***i.toString()************", i.toString());
				Cursor c=MainActivity.getDB().rawQuery("SELECT Uname_id FROM Emp_message", null);
				int counts=c.getCount();
				c.moveToFirst();
				for(int g=0;g<counts;g++){
				Log.i("**********Cursor************", c.getString(0));
				c.moveToNext();
				}
				
				
				MainActivity.getDB().execSQL("INSERT INTO Salary (Eno_id,Dname,Sbasicsalary) VALUES(?,?,?)", 
						new String[]{i.toString(),et_8.getText().toString(),"5000"});
				
				MainActivity.getDB().execSQL("INSERT INTO Emp_message (Uname_id,Uemployee_password) VALUES(?,?)", 
						new String[]{i.toString(),i.toString()});
				
				Log.i("**$$$$$$$$$$$**后面的i.toString()************", i.toString());
				
				Intent intent =new Intent(AdminAddEmployee.this,Admin.class);
				startActivity(intent);
				AdminAddEmployee.this.finish();
				Toast.makeText(AdminAddEmployee.this, "添加员工成功", 0).show();
			}
			
		});
	}

	private void initialize() {
		et_2=(EditText) findViewById(R.id.et_2_add);
		et_3=(EditText) findViewById(R.id.et_3_add);
		et_4=(EditText) findViewById(R.id.et_4_add);
		et_5=(EditText) findViewById(R.id.et_5_add);
		et_6=(EditText) findViewById(R.id.et_6_add);
		et_7=(EditText) findViewById(R.id.et_7_add);
		et_8=(EditText) findViewById(R.id.et_8_add);
		et_9=(EditText) findViewById(R.id.et_9_add);
		et_10=(EditText) findViewById(R.id.et_10_add);
		et_11=(EditText) findViewById(R.id.et_11_add);
		bt_message_save=(Button) findViewById(R.id.bt_message_save_add_emp);
		bt_message_cancel=(Button) findViewById(R.id.bt_message_cancel_add_emp);
		
	}

}
