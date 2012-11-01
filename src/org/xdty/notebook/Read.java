package org.xdty.notebook;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Read extends Activity{
	public SQLiteDatabase mydb = null;
	public final static String DATABASE_NAME = "mydb.db";
	public final static String TABLE_NAME = "myTable";
	public final static String ID="_id";
	public final static String TITLE="title";
	public final static String POST="post";
	public final static String DATE="date";
	public final static String DATEDETAIL="datedetail";
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.read);
		Button btn1=(Button)findViewById(R.id.r_button1);
		Button btn2=(Button)findViewById(R.id.r_button2);
		TextView text1=(TextView)findViewById(R.id.r_textView1);
		TextView text2=(TextView)findViewById(R.id.r_textView2);
		TextView text3=(TextView)findViewById(R.id.r_textView3);
		mydb=openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
		Cursor cur=mydb.query(TABLE_NAME, new String []{ID,TITLE,POST,DATE,DATEDETAIL}, null, null, null, null, null);
		String title,post,date;
		int count = cur.getCount();
		cur.moveToPosition(count-Notebook.prime-1);
		title=cur.getString(1);
		post=cur.getString(2);
		date=cur.getString(4);
		text1.setText(title);
		text2.setText(date);
		text3.setText(""+post);
		cur.close();
		mydb.close();
		
		btn1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(Read.this, Edit.class);
				startActivity(intent);
			}
		});
		btn2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
	public void onResume(){
		super.onResume();
		mydb=openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
		Cursor cur=mydb.query(TABLE_NAME, new String []{ID,TITLE,POST,DATE,DATEDETAIL}, null, null, null, null, null);
		String title,post,date;
		int count = cur.getCount();
		cur.moveToPosition(count-Notebook.prime-1);
		title=cur.getString(1);
		post=cur.getString(2);
		date=cur.getString(4);
		TextView text1=(TextView)findViewById(R.id.r_textView1);
		TextView text2=(TextView)findViewById(R.id.r_textView2);
		TextView text3=(TextView)findViewById(R.id.r_textView3);
		text1.setText(title);
		text2.setText(date);
		text3.setText(post);
		cur.close();
		mydb.close();
	}
}