package com.example.salary_management;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ExpandableListActivity;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.Toast;

public class Admin extends TabActivity {
	
	private ExpandableListView admin_main_view;
	private List<String> group;           
    private List<List<String>> child; 
    private static Cursor cursor;
    private static String admin_employee_message;
    private ListView lv_department_admin;
    private ArrayList<HashMap<String,String>> department_list;
    private ListView lv_settings_admin;
    private ArrayList<HashMap<String,String>> settings_list;
    private static String department_name;
    
    
	
	public static String getDepartment_name() {
		return department_name;
	}



	public static String getAdmin_employee_message() {
		return admin_employee_message;
	}
	
	



	public static void setAdmin_employee_message(String admin_employee_message) {
		Admin.admin_employee_message = admin_employee_message;
	}



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.admin_view);
		SysApplication.getInstance().addActivity(this); 
		initializeData();
		loadTabHost();
		admin_department_show();
		admin_settings_show();
		ContactsInfoAdapter contactsInfoAdapter =new ContactsInfoAdapter();
		contactsInfoAdapter.setChild(child);
		contactsInfoAdapter.setGroup(group);
		contactsInfoAdapter.setContext(this);
		admin_main_view=(ExpandableListView) findViewById(android.R.id.list);
		admin_main_view.setAdapter(contactsInfoAdapter);
		admin_main_view.setOnChildClickListener(new OnChildClickListener(){

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				if(groupPosition==0){
					if(childPosition==0){
						Intent intent=new Intent(Admin.this,SearchEmployee.class);
						startActivity(intent);
					}else{
						Intent intent=new Intent(Admin.this,AdminAddEmployee.class);
						startActivity(intent);
					}
				}else{
				switch(childPosition){
				case 0:
					cursor.moveToPosition(groupPosition-1);
					admin_employee_message=cursor.getString(0);
					Intent intent=new Intent(Admin.this,AdminMessageAlter.class);
					startActivity(intent);
					break;
				case 1:
					cursor.moveToPosition(groupPosition-1);
					admin_employee_message=cursor.getString(0);
					Intent intent_salary=new Intent(Admin.this,AdminSalaryAlter.class);
					startActivity(intent_salary);
					break;
				case 2:
					cursor.moveToPosition(groupPosition-1);
					admin_employee_message=cursor.getString(0);
					new AlertDialog.Builder(Admin.this)
			        .setTitle("删除")
			        .setMessage("确定删除员工"+admin_employee_message+"？")
			        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int whichButton) {
			        	
			        	MainActivity.getDB().execSQL
			        	("DELETE FROM Salary WHERE Eno_id='"+admin_employee_message+"'");
			        	MainActivity.getDB().execSQL
			        	("DELETE FROM Emp_message WHERE Uname_id='"+admin_employee_message+"'");
			        	MainActivity.getDB().execSQL
			        	("DELETE FROM Employee WHERE Eno_id='"+admin_employee_message+"'");
			        	
			        	
			        	initializeData();
			        	ContactsInfoAdapter contactsInfoAdapter =new ContactsInfoAdapter();
			    		contactsInfoAdapter.setChild(child);
			    		contactsInfoAdapter.setGroup(group);
			    		contactsInfoAdapter.setContext(Admin.this);
			    		admin_main_view=(ExpandableListView) findViewById(android.R.id.list);
			    		admin_main_view.setAdapter(contactsInfoAdapter);
			        	
			        	
			        	
			        	
			        	Toast.makeText(Admin.this, "删除员工成功", 0).show();
			        }
			        })
			        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int whichButton) {
			         //取消按钮事件
			        }
			        })
			        .show();
					break;
				}
				}
				return false;
			}
			
		});
		
	}
	




	private void admin_department_show() {
		lv_department_admin=(ListView) findViewById(R.id.lv_admin_department);
		department_list=new ArrayList<HashMap<String,String>>();
		String [] mFrom=new String[]{"name"};
		int [] mTo=new int[]{R.id.tv_display_name_settings};
		final HashMap<String,String> map1=new HashMap<String, String>();
		map1.put("name", "添加部门");
		department_list.add(map1);
		Cursor cursor=MainActivity.getDB().rawQuery("SELECT Dname FROM Department",null);
		int resultsCount=cursor.getCount();
		if(resultsCount==0|!cursor.moveToFirst()){
			return;
		}
		for(int i=0;i<resultsCount;i++){
			HashMap<String,String> map=new HashMap<String, String>();
			map.put("name", cursor.getString(0));
			department_list.add(map);
			cursor.moveToNext();
		}
		
		lv_department_admin.setAdapter(new SimpleAdapter(this,department_list,R.layout.item_settings,mFrom,mTo));
		lv_department_admin.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if(department_list.get(position).equals(map1)){
					Intent intent=new Intent(Admin.this,AdminAddDepartment.class);
					startActivity(intent);
				}else{
					department_name=department_list.get(position).get("name");
					Intent intent=new Intent(Admin.this,AdminDepartmentAlter.class);
					startActivity(intent);
				}
			}
			
		});
		
		
	}
	

	private void admin_settings_show() {
		lv_settings_admin=(ListView) findViewById(R.id.lv_admin_settings);
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
		lv_settings_admin.setAdapter(new SimpleAdapter(this,settings_list,R.layout.item_settings,mFrom,mTo));
		lv_settings_admin.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if(settings_list.get(position).equals(map)){
					AlterPassword.setMARKER(2);
					Intent intent=new Intent(Admin.this,AlterPassword.class);
					startActivity(intent);
				}
				if(settings_list.get(position).equals(map5)){
					new AlertDialog.Builder(Admin.this)
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




	private void addInfo(String g,String[] c){  
        group.add(g);  
        List<String> childitem = new ArrayList<String>();  
        for(int i=0;i<c.length;i++){  
            childitem.add(c[i]);  
        }  
        child.add(childitem);  
    }  
	 private void initializeData(){  
	        group = new ArrayList<String>();  
	        child = new ArrayList<List<String>>();  
	        addInfo("搜索·添加",new String[]{"搜索员工","添加员工"});
	        cursor=MainActivity.getDB().rawQuery("SELECT Eno_id,Ename FROM Employee", null);
	        int resultsCount=cursor.getCount();
	        if(resultsCount==0|!cursor.moveToFirst()){
	        	return;
	        }
	        for(int i=0;i<resultsCount;i++){
	        	addInfo(cursor.getString(0)+"～"+cursor.getString(1),
	        			new String[]{"修改信息","工资设定","删除员工"});
	        	cursor.moveToNext();
	        }
	          
	    }  
		private void loadTabHost() {
			TabHost tabHost=getTabHost();
			TabSpec page1=tabHost.newTabSpec("tab1").setIndicator("员工信息").setContent(R.id.admin_employee_message);
			tabHost.addTab(page1);
			TabSpec page2=tabHost.newTabSpec("tab2").setIndicator("部门信息").setContent(R.id.admin_department_message);
			tabHost.addTab(page2);
			TabSpec page4=tabHost.newTabSpec("tab3").setIndicator("设置").setContent(R.id.admin_settings);
			tabHost.addTab(page4);
		}	


}