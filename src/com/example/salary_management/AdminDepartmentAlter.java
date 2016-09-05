package com.example.salary_management;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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

public class AdminDepartmentAlter extends Activity {
	
	private Cursor cursor;
	private EditText et_dno;
	private EditText et_dname;
	private EditText et_dhead;
	private EditText et_daddress;
	private EditText et_dtel;
	private Button bt_department_save;
	private Button bt_department_cancel;
	private Button bt_department_delete;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.admin_department_alter);
		SysApplication.getInstance().addActivity(this); 
		initialize();
		cursor=MainActivity.getDB().rawQuery("SELECT * FROM Department WHERE Dname='"+
		Admin.getDepartment_name()+"'", null);
		int resultsCount=cursor.getCount();
        if(resultsCount==0|!cursor.moveToFirst()){
        	return;
        }
		showdata();
		bt_department_cancel.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
					
					Intent intent =new Intent(AdminDepartmentAlter.this,Admin.class);
					startActivity(intent);
					AdminDepartmentAlter.this.finish();
				}
					
			});
		bt_department_save.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				alterdata();
				
				Intent intent =new Intent(AdminDepartmentAlter.this,Admin.class);
				startActivity(intent);
				AdminDepartmentAlter.this.finish();
				Toast.makeText(AdminDepartmentAlter.this, "部门信息修改成功", 0).show();
				}
					
		});
		bt_department_delete.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(AdminDepartmentAlter.this)
		        .setTitle("删除")
		        .setMessage("确定删除该部门？  该部门的所有员工也将删除。")
		        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int whichButton) {
		        	MainActivity.getDB().execSQL
		        	("DELETE FROM Employee WHERE Dname='"+Admin.getDepartment_name()+"'");
		        	MainActivity.getDB().execSQL
		        	("DELETE FROM Department WHERE Dname='"+Admin.getDepartment_name()+"'");
		        	
					Intent intent =new Intent(AdminDepartmentAlter.this,Admin.class);
					startActivity(intent);
					AdminDepartmentAlter.this.finish();
		        }
		        })
		        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int whichButton) {
		         //取消按钮事件
		        }
		        })
		        .show();
				
			}
			
		});
	}




	private void initialize() {
		
		et_dno=(EditText) findViewById(R.id.et_dno);
		et_dname=(EditText) findViewById(R.id.et_dname);
		et_dhead=(EditText) findViewById(R.id.et_dhead);
		et_daddress=(EditText) findViewById(R.id.et_daddress);
		et_dtel=(EditText) findViewById(R.id.et_dtel);
		bt_department_save=(Button) findViewById(R.id.bt_department_save);
		bt_department_cancel=(Button) findViewById(R.id.bt_department_cancel);
		bt_department_delete=(Button) findViewById(R.id.bt_department_delete);
	}
	
	private void showdata() {
		et_dno.setText(cursor.getString(0));
		et_dname.setText(cursor.getString(1));
		et_dhead.setText(cursor.getString(2));
		et_daddress.setText(cursor.getString(3));
		et_dtel.setText(cursor.getString(4));
		
	}
	

	private void alterdata() {

		Log.i("*******************", et_dno.getText().toString()+"*****"+et_dname.getText().toString());
		MainActivity.getDB().execSQL("UPDATE Department SET Dno_id=?,Dname=?, Dhead=?," +
				"Daddress=?,Dtel=? WHERE Dname=?", new String[]{et_dno.getText().toString(),et_dname.getText().toString(),
				et_dhead.getText().toString(),et_daddress.getText().toString(),
				et_dtel.getText().toString(),Admin.getDepartment_name()});
		
	}

	
}
