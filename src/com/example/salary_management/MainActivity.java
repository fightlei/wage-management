package com.example.salary_management;

import java.io.IOException;

import com.example.salary_management.DBHelper;
import com.example.salary_management.MainActivity;
import com.example.salary_management.R;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends Activity {
	
	private DBHelper helper=new DBHelper(this);
	private static SQLiteDatabase DB;
	private Button bt1;
	
	public static SQLiteDatabase getDB(){
		return DB;
	}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SysApplication.getInstance().addActivity(this); 
        getDatabase();
        initalize();
    }


    private void initalize() {
        bt1=(Button) findViewById(R.id.Button1);
        bt1.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent(MainActivity.this ,Login.class);
				startActivity(intent);
				MainActivity.this.finish();
			}
        	
        });
		
	}

	private void getDatabase() {
    	try {
            helper.createDataBase();
        } catch (IOException e) {
        	Log.i("******************", "createbatabaseexcet");
            e.printStackTrace();
        }
        
        try{
        DB=helper.getWritableDatabase();}
        catch(Exception e){Log.i("******************", "nullpointer");}
		
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
