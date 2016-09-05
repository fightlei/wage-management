package com.example.salary_management;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AdminAddDepartment extends Activity {
	
	private EditText et_dno;
	private EditText et_dname;
	private EditText et_dhead;
	private EditText et_daddress;
	private EditText et_dtel;
	private Button bt_department_save;
	private Button bt_department_cancel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.admin_add_depar_view);
		SysApplication.getInstance().addActivity(this); 
		initialize();
		bt_department_save.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				MainActivity.getDB().execSQL("INSERT INTO Department (Dno_id,Dname, Dhead," +
						"Daddress,Dtel) VALUES(?,?,?,?,?)", new String[]{et_dno.getText().toString(),et_dname.getText().toString(),
						et_dhead.getText().toString(),et_daddress.getText().toString(),
						et_dtel.getText().toString()});
				
				Intent intent =new Intent(AdminAddDepartment.this,Admin.class);
				startActivity(intent);
				AdminAddDepartment.this.finish();
				Toast.makeText(AdminAddDepartment.this, "添加部门成功", 0).show();
			}
			
		});
		bt_department_cancel.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				
				Intent intent =new Intent(AdminAddDepartment.this,Admin.class);
				startActivity(intent);
				AdminAddDepartment.this.finish();
				
			}
			
		});
	}

	private void initialize() {
		et_dno=(EditText) findViewById(R.id.et_add_dno);
		et_dname=(EditText) findViewById(R.id.et_add_dname);
		et_dhead=(EditText) findViewById(R.id.et_add_dhead);
		et_daddress=(EditText) findViewById(R.id.et_add_daddress);
		et_dtel=(EditText) findViewById(R.id.et_add_dtel);
		bt_department_save=(Button) findViewById(R.id.bt_department_add_save);
		bt_department_cancel=(Button) findViewById(R.id.bt_department_add_cancel);
		
	}
	
	

}
