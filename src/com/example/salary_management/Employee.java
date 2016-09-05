package com.example.salary_management;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;

public class Employee extends TabActivity {
	
	private ListView lv_message;
	private ListView lv_salary;
	private ListView lv_department;
	private ListView lv_settings;
	private String user_id;
	private ArrayList<HashMap<String, String>> message_list;
	private ArrayList<HashMap<String, String>> salary_list;
	private ArrayList<HashMap<String, String>> department_list;
	private ArrayList<HashMap<String, String>> settings_list;
	private Cursor results;
	private Cursor salary_results;
	private Cursor department_results;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.employee_view);
		SysApplication.getInstance().addActivity(this); 
		user_id=Login.getUser_id();
		loadTabHost();
		//显示员工个人信息界面
		employee_msg_show();
		//显示员工工资详情界面
		salary_msg_show();
		//显示员工所在部门信息界面
		department_msg_show();
		//显示员工个人信息界面
		employee_setting_show();
	}

	private void employee_setting_show() {
		
		lv_settings=(ListView) findViewById(R.id.lv_settings_employee);
		settings_list=new ArrayList<HashMap<String,String>>();
		String [] mFrom=new String[]{"name"};
		int [] mTo=new int[]{R.id.tv_display_name_settings};
		final HashMap<String,String> map=new HashMap<String, String>();
		map.put("name", "修改密码");
		settings_list.add(map);
		HashMap<String,String> map1=new HashMap<String, String>();
		map1.put("name", "软件评分");
		settings_list.add(map1);
		HashMap<String,String> map2=new HashMap<String, String>();
		map2.put("name", "意见反馈");
		settings_list.add(map2);
		HashMap<String,String> map3=new HashMap<String, String>();
		map3.put("name", "检查更新");
		settings_list.add(map3);
		HashMap<String,String> map4=new HashMap<String, String>();
		map4.put("name", "关于我们");
		settings_list.add(map4);
		final HashMap<String,String> map5=new HashMap<String, String>();
		map5.put("name", "退出");
		settings_list.add(map5);
		lv_settings.setAdapter(new SimpleAdapter(this,settings_list,R.layout.item_settings,mFrom,mTo));
		lv_settings.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if(settings_list.get(position).equals(map)){
					AlterPassword.setMARKER(1);
					Intent intent=new Intent(Employee.this,AlterPassword.class);
					startActivity(intent);
				}
				if(settings_list.get(position).equals(map5)){
					new AlertDialog.Builder(Employee.this)
			        .setTitle("退出")
			        .setMessage("真的要离开工资管理吗？")
			        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int whichButton) {
			        	SysApplication.getInstance().exit();
			        }
			        })
			        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int whichButton) {
			         //取消按钮事件
			        }
			        })
			        .show();
				}
			}
			
		});
		
		
		
		
	}

	private void employee_msg_show() {
		lv_message=(ListView) findViewById(R.id.lv_message);
		results=MainActivity.getDB().rawQuery("SELECT * FROM Employee WHERE Eno_id="+user_id+"",null);
		int resultCounts=results.getCount();
		if(resultCounts==0||!results.moveToFirst()){
			return;
		}
		/*
		 * listview设置SimpleAdapter
		 */
		String [] mFrom=new String[]{"name","value"};
		int[] mTo=new int[]{R.id.tv_display_name,R.id.tv_display_value};
		message_list=new ArrayList<HashMap<String, String>>();
		get_message_Data();
		lv_message.setAdapter(new SimpleAdapter(this, message_list, R.layout.item, mFrom, mTo));
		
	}

	private void salary_msg_show() {
		lv_salary=(ListView) findViewById(R.id.lv_salary);
		salary_results=MainActivity.getDB().rawQuery("SELECT * FROM Salary WHERE Eno_id="+user_id+"",null);
		int resultCounts=salary_results.getCount();
		if(resultCounts==0||!salary_results.moveToFirst()){
			return;
		}
		String [] mFrom=new String[]{"name","value"};
		int[] mTo=new int[]{R.id.tv_display_name,R.id.tv_display_value};
		salary_list=new ArrayList<HashMap<String, String>>();
		get_salary_data();
		lv_salary.setAdapter(new SimpleAdapter(this,salary_list,R.layout.item,mFrom,mTo));
	}

	private void get_salary_data() {
		HashMap<String,String> map=new HashMap<String, String>();
		map.put("name", "基本工资");
		map.put("value", salary_results.getString(2));
		salary_list.add(map);
		
		HashMap<String,String> map_day1=new HashMap<String, String>();
		map_day1.put("name", "加班天数");
		map_day1.put("value", salary_results.getString(7));
		salary_list.add(map_day1);
		
		HashMap<String,String> map1=new HashMap<String, String>();
		map1.put("name", "加班补贴");
		map1.put("value", salary_results.getString(3));
		salary_list.add(map1);
		
		HashMap<String,String> map_day2=new HashMap<String, String>();
		map_day2.put("name", "考勤天数");
		map_day2.put("value", salary_results.getString(8));
		salary_list.add(map_day2);
		
		HashMap<String,String> map2=new HashMap<String, String>();
		map2.put("name", "考勤工资");
		map2.put("value", salary_results.getString(4));
		salary_list.add(map2);
		
		HashMap<String,String> map_day3=new HashMap<String, String>();
		map_day3.put("name", "缺勤天数");
		map_day3.put("value", salary_results.getString(9));
		salary_list.add(map_day3);
		
		HashMap<String,String> map3=new HashMap<String, String>();
		map3.put("name", "实扣金额");
		map3.put("value", salary_results.getString(5));
		salary_list.add(map3);
		
		HashMap<String,String> map4=new HashMap<String, String>();
		map4.put("name", "其他津贴");
		map4.put("value", salary_results.getString(6));
		salary_list.add(map4);
		
		Double total_money=salary_results.getDouble(2)+salary_results.getDouble(3)+
				salary_results.getDouble(4)-salary_results.getDouble(5)+salary_results.getDouble(6);
		
		HashMap<String,String> map5=new HashMap<String, String>();
		map5.put("name", "总工资");
		map5.put("value", total_money.toString());
		salary_list.add(map5);
		
	}

	private void department_msg_show() {
		lv_department=(ListView) findViewById(R.id.lv_department);
		department_results=MainActivity.getDB().
				rawQuery("SELECT * FROM Department WHERE Dname=?",new String[]{results.getString(7)});
		int resultCounts=department_results.getCount();
		if(resultCounts==0||!department_results.moveToFirst()){
			return;
		}
		String [] mFrom=new String[]{"name","value"};
		int[] mTo=new int[]{R.id.tv_display_name,R.id.tv_display_value};
		department_list=new ArrayList<HashMap<String, String>>();
		get_department_data();
		lv_department.setAdapter(new SimpleAdapter(this,department_list,R.layout.item,mFrom,mTo));
	}

	private void get_department_data() {
		HashMap<String,String> map=new HashMap<String, String>();
		map.put("name", "部门编号");
		map.put("value", department_results.getString(0));
		department_list.add(map);
		
		HashMap<String,String> map1=new HashMap<String, String>();
		map1.put("name", "部门名称");
		map1.put("value", department_results.getString(1));
		department_list.add(map1);
		
		HashMap<String,String> map2=new HashMap<String, String>();
		map2.put("name", "部门经理");
		map2.put("value", department_results.getString(2));
		department_list.add(map2);
		//计算该员工所在部门的总人数，从department_person_quantity视图中提取
		Cursor cursor=MainActivity.getDB().
				rawQuery("SELECT * FROM department_person_quantity WHERE Dname='"+results.getString(7)+"'",null);
		int resultCounts=cursor.getCount();
		if(resultCounts==0||!cursor.moveToFirst()){
			return;
		}
		HashMap<String,String> map_new1=new HashMap<String, String>();
		map_new1.put("name", "部门人数");
		map_new1.put("value", cursor.getString(1));
		department_list.add(map_new1);
		
		HashMap<String,String> map3=new HashMap<String, String>();
		map3.put("name", "部门地址");
		map3.put("value", department_results.getString(3));
		department_list.add(map3);
		
		HashMap<String,String> map4=new HashMap<String, String>();
		map4.put("name", "部门电话");
		map4.put("value", department_results.getString(4));
		department_list.add(map4);
		
		//计算该员工所在部门的总工资，从department_salary视图中提取
		String emp_depart_msg="SELECT * FROM department_salary WHERE Dname='"+results.getString(7)+"'";
		Cursor cursor1=MainActivity.getDB().
				rawQuery(emp_depart_msg,null);
		int resultCounts1=cursor1.getCount();
		if(resultCounts1==0||!cursor1.moveToFirst()){
			return;
		}
		Double department_total_salary=cursor1.getDouble(1)+cursor1.getDouble(2)
							+cursor1.getDouble(3)-cursor1.getDouble(4);
		HashMap<String,String> map_new2=new HashMap<String, String>();
		map_new2.put("name", "部门总工资");
		map_new2.put("value", department_total_salary.toString());
		department_list.add(map_new2);
		Double average_salary=department_total_salary/cursor.getDouble(1);
		HashMap<String,String> map_new3=new HashMap<String, String>();
		map_new3.put("name", "部门人均工资");
		map_new3.put("value", average_salary.toString());
		department_list.add(map_new3);
		
		
		
	}

	private void get_message_Data() {
		HashMap<String,String> map=new HashMap<String, String>();
		map.put("name", "ID号");
		map.put("value", results.getString(0));
		message_list.add(map);
		HashMap<String,String> map1=new HashMap<String, String>();
		map1.put("name", "姓名");
		map1.put("value", results.getString(1));
		message_list.add(map1);
		HashMap<String,String> map2=new HashMap<String, String>();
		map2.put("name", "性别");
		map2.put("value", results.getString(2));
		message_list.add(map2);
		
		HashMap<String,String> map3=new HashMap<String, String>();
		map3.put("name", "出生年月");
		map3.put("value", results.getString(3));
		message_list.add(map3);
		
		HashMap<String,String> map4=new HashMap<String, String>();
		map4.put("name", "籍贯");
		map4.put("value", results.getString(4));
		message_list.add(map4);
		
		HashMap<String,String> map5=new HashMap<String, String>();
		map5.put("name", "学历");
		map5.put("value", results.getString(5));
		message_list.add(map5);
		
		HashMap<String,String> map6=new HashMap<String, String>();
		map6.put("name", "入职时间");
		map6.put("value", results.getString(6));
		message_list.add(map6);
		
		HashMap<String,String> map7=new HashMap<String, String>();
		map7.put("name", "部门");
		map7.put("value", results.getString(7));
		message_list.add(map7);
		
		HashMap<String,String> map8=new HashMap<String, String>();
		map8.put("name", "职位");
		map8.put("value", results.getString(8));
		message_list.add(map8);
		
		HashMap<String,String> map9=new HashMap<String, String>();
		map9.put("name", "家庭住址");
		map9.put("value", results.getString(9));
		message_list.add(map9);
		
		HashMap<String,String> map10=new HashMap<String, String>();
		map10.put("name", "电话号码");
		map10.put("value", results.getString(10));
		message_list.add(map10);
		
	}

	private void loadTabHost() {
		TabHost tabHost=getTabHost();
		TabSpec page1=tabHost.newTabSpec("tab1").setIndicator("个人").setContent(R.id.message);
		tabHost.addTab(page1);
		TabSpec page2=tabHost.newTabSpec("tab2").setIndicator("工资").setContent(R.id.salary);
		tabHost.addTab(page2);
		TabSpec page3=tabHost.newTabSpec("tab3").setIndicator("部门").setContent(R.id.department);
		tabHost.addTab(page3);
		TabSpec page4=tabHost.newTabSpec("tab4").setIndicator("设置").setContent(R.id.settint_employee);
		tabHost.addTab(page4);
		
	}
	
}
