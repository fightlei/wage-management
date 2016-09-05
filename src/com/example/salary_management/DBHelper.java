package com.example.salary_management;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
	
	//���ݿ�汾
	private static final int DB_VERSION    = 3;
	//���ݿ��ļ�Ŀ����·��ΪϵͳĬ��λ��
	   private static String DB_PATH        = "/data/data/com.example.salary_management/databases/";
	 //���徲̬������ʾassets�ļ����µ��ļ���
	   private static String DB_NAME         = "Salary_management.db";
	   
	   private final Context myContext;
	   
	   
	   public Context getMyContext(){
		   return myContext;
	   }
	   
	   
	   

	public DBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		this.myContext=context;
	}
	
	 public DBHelper(Context context, String name, int version){
	       this(context,name,null,version);
	   }

	   public DBHelper(Context context, String name){
	       this(context,name,DB_VERSION);
	   }
	   
	   public DBHelper (Context context) {
	       this(context, DB_PATH + DB_NAME);
	   }
	
	
	


	@Override
	public void onCreate(SQLiteDatabase db) {

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}
	
	/*
	 * ������ݿ��Ƿ���ڣ����������򴴽��µ����ݿ�
	 * ����������
	 */
	
	//������ݿ��Ƿ����
	   private boolean checkDataBase(){
	       SQLiteDatabase checkDB = null;
	       String myPath = DB_PATH + DB_NAME;
	       try{            
	           checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
	       }catch(SQLiteException e){
	    	   e.printStackTrace();
	       }
	       if(checkDB != null){
	           checkDB.close();
	       }
	        return checkDB != null ? true : false;
	   }
	   
	   //��assets�ļ����µ����ݿ��ļ����Ƶ�DB_PATH��
	   private void copyDataBase() throws IOException{
	       
	       InputStream myInput = myContext.getAssets().open(DB_NAME);
	       String outFileName = DB_PATH + DB_NAME;
	       OutputStream myOutput = new FileOutputStream(outFileName);
	       byte[] buffer = new byte[1024];
	       int length;
	       while ((length = myInput.read(buffer))>0){
	           myOutput.write(buffer, 0, length);
	       }
	       myOutput.flush();
	       myOutput.close();
	       myInput.close();
	   }
	
	
	public void createDataBase() throws IOException{
	       boolean dbExist = checkDataBase();
	       if(dbExist)
	       {
	    	   Log.i("******************", "���ݿ��Ѵ��ڣ������κβ���");
	           //���ݿ��Ѵ��ڣ������κβ���
	       }
	       else
	       {
	           //�������ݿ�
	           try {
	               File dir = new File(DB_PATH);
	               if(!dir.exists()){
	                   dir.mkdirs();
	               }
	               File dbf = new File(DB_PATH + DB_NAME);
	               if(dbf.exists()){
	                   dbf.delete();
	               }
	               SQLiteDatabase.openOrCreateDatabase(dbf, null);
	               // ����asseets�е����ݿ��ļ���DB_PATH��
	               copyDataBase();
	           } catch (IOException e) {
	               throw new Error("���ݿⴴ��ʧ��");
	           }
	       }
	   }
	
	
	
	

}
