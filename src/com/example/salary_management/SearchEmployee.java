package com.example.salary_management;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SearchEmployee extends Activity {
	
	private EditText et_search;
	private Button bt_search_message;
	private Button bt_search_salary;
	private boolean tag=false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.search_employee_view);
		SysApplication.getInstance().addActivity(this); 
		et_search=(EditText) findViewById(R.id.et_search_eno);
		bt_search_message=(Button) findViewById(R.id.bt_search_message);
		bt_search_salary=(Button) findViewById(R.id.bt_search_salary);

		bt_search_message.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				tag=false;
				Cursor cursor=MainActivity.getDB().rawQuery("SELECT Eno_id FROM Employee", null);
				int resultsCounts=cursor.getCount();
				if(resultsCounts==0|!cursor.moveToFirst()){
					return;
				}
				for(int i=0;i<resultsCounts;i++){
					if(cursor.getString(0).toString().equals(et_search.getText().toString())){
						tag=true;
						break;
					}
					cursor.moveToNext();
				}
				if(tag){
				Admin.setAdmin_employee_message(et_search.getText().toString());
				Intent intent=new Intent(SearchEmployee.this,AdminMessageAlter.class);
				startActivity(intent);
				SearchEmployee.this.finish();}
				else{
					Toast.makeText(SearchEmployee.this, "员工编号不存在，请重新输入。", 0).show();
				}
			}
			
		});
		
		bt_search_salary.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				tag=false;
				Cursor cursor=MainActivity.getDB().rawQuery("SELECT Eno_id FROM Employee", null);
				int resultsCounts=cursor.getCount();
				if(resultsCounts==0|!cursor.moveToFirst()){
					return;
				}
				for(int i=0;i<resultsCounts;i++){
					if(cursor.getString(0).toString().equals(et_search.getText().toString())){
						tag=true;
						break;
					}
					cursor.moveToNext();
				}
				if(tag){
				Admin.setAdmin_employee_message(et_search.getText().toString());
				Intent intent=new Intent(SearchEmployee.this,AdminSalaryAlter.class);
				startActivity(intent);
				SearchEmployee.this.finish();}
				else{
					Toast.makeText(SearchEmployee.this, "员工编号不存在，请重新输入。", 0).show();
				}
			}
			
		});
		
	}
	
	

}
