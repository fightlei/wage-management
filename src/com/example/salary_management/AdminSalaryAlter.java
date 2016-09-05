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

public class AdminSalaryAlter extends Activity {
	
	private Cursor cursor;
	private EditText et_basic_wage;
	private EditText et_overtime_days;
	private EditText et_overtime_wage;
	private EditText et_checking_in_days;
	private EditText et_checking_in_wage;
	private EditText et_absenteeism_days;
	private EditText et_decdcut_money;
	private EditText et_other_wage;
	private Button bt_salary_save;
	private Button bt_salary_cancel;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.admin_salary_alter);
		SysApplication.getInstance().addActivity(this); 
		initialize();
		cursor=MainActivity.getDB().rawQuery("SELECT * FROM Salary WHERE Eno_id='"+
		Admin.getAdmin_employee_message()+"'", null);
		int resultsCount=cursor.getCount();
        if(resultsCount==0|!cursor.moveToFirst()){
        	return;
        }
		showdata();
		bt_salary_cancel.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				
				Intent intent =new Intent(AdminSalaryAlter.this,Admin.class);
				startActivity(intent);
				AdminSalaryAlter.this.finish();
			}
			
		});
		bt_salary_save.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				alterdata();
				
				Intent intent =new Intent(AdminSalaryAlter.this,Admin.class);
				startActivity(intent);
				AdminSalaryAlter.this.finish();
				Toast.makeText(AdminSalaryAlter.this, "员工工资设定成功", 0).show();
				
			}
			
		});
	}

	private void initialize() {
		et_basic_wage=(EditText) findViewById(R.id.et_basic_wage);
		et_overtime_days=(EditText) findViewById(R.id.et_overtime_days);
		et_overtime_wage=(EditText) findViewById(R.id.et_overtime_wage);
		et_checking_in_days=(EditText) findViewById(R.id.et_checking_in_days);
		et_checking_in_wage=(EditText) findViewById(R.id.et_checking_in_wage);
		et_absenteeism_days=(EditText) findViewById(R.id.et_absenteeism_days);
		et_decdcut_money=(EditText) findViewById(R.id.et_decduct_money);
		et_other_wage=(EditText) findViewById(R.id.et_other_wage);
		bt_salary_save=(Button) findViewById(R.id.bt_salary_save);
		bt_salary_cancel=(Button) findViewById(R.id.bt_salary_cancel);
		
	}

	private void showdata() {

		et_basic_wage.setText(cursor.getString(2));
		et_overtime_days.setText(cursor.getString(7));
		et_overtime_wage.setText(cursor.getString(3));
		et_checking_in_days.setText(cursor.getString(8));
		et_checking_in_wage.setText(cursor.getString(4));
		et_absenteeism_days.setText(cursor.getString(9));
		et_decdcut_money.setText(cursor.getString(5));
		et_other_wage.setText(cursor.getString(6));
		
	}

	private void alterdata() {
		MainActivity.getDB().execSQL("UPDATE Salary SET Sbasicsalary=?,Sovertime_wage=?, Schecking_in_wage=?," +
				"Sdecduct_money=?,Sother_wage=?," +
				"Sovertime_days=?,Schecking_in_days=?,Sabsenteeism_days=? WHERE Eno_id=?", 
				new String[]{et_basic_wage.getText().toString(),et_overtime_wage.getText().toString(),
				et_checking_in_wage.getText().toString(),et_decdcut_money.getText().toString(),
				et_other_wage.getText().toString(),
				et_overtime_days.getText().toString(),et_checking_in_days.getText().toString(),
				et_absenteeism_days.getText().toString(),Admin.getAdmin_employee_message()});
		   
		 
	}

	
}
