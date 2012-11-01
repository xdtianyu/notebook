package org.xdty.notebook;

import java.util.Calendar;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Edit extends Activity{
	
	public SQLiteDatabase mydb = null;
	public final static String DATABASE_NAME = "mydb.db";
	public final static String TABLE_NAME = "myTable";
	public final static String ID="_id";
	public final static String TITLE="title";
	public final static String POST="post";
	public final static String DATE="date";
	public final static String DATEDETAIL="datedetail";
	public static String mydate;
	public static int year,month,day,hour,minute;
	public static int amorpm=0;
	public Calendar c=null;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit);
        Button btn1=(Button)findViewById(R.id.e_button1);
        Button btn2=(Button)findViewById(R.id.e_button2);
		EditText edit1=(EditText)findViewById(R.id.e_editText1);
		EditText edit2=(EditText)findViewById(R.id.e_editText2);
		mydb=openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
		
		Cursor cur=mydb.query(TABLE_NAME, new String []{ID,TITLE,POST,DATEDETAIL}, null, null, null, null, null);
		String title,post;
		int count = cur.getCount();
		cur.moveToPosition(count-Notebook.prime-1);
		title=cur.getString(1);
		post=cur.getString(2);
		edit1.setText(title);
		edit2.setText(post);
		
        btn1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				EditText edit1=(EditText)findViewById(R.id.e_editText1);
				EditText edit2=(EditText)findViewById(R.id.e_editText2);
				c=Calendar.getInstance();
				year=c.get(Calendar.YEAR);
				month=c.get(Calendar.MONTH)+1;
				Log.i("month", ""+month);
				day=c.get(Calendar.DAY_OF_MONTH);
				amorpm=c.get(Calendar.AM_PM);
				hour=c.get(Calendar.HOUR);
				String title2,post2;
				title2=edit1.getText().toString();
				post2=edit2.getText().toString();
				if(amorpm==Calendar.PM)
					hour+=12;
				minute=c.get(Calendar.MINUTE);
				mydate=""+year+"年"+month+"月"+day+"日-"+hour+"时"+minute+"分";
				mydb=openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
				Cursor cur=mydb.query(TABLE_NAME, new String []{ID,TITLE,POST,DATEDETAIL}, null, null, null, null, null);
				int count = cur.getCount();
				int pri=count-Notebook.prime-1;
				String UPDATE_TABLE = "UPDATE "+TABLE_NAME+" SET "+"TITLE=\""+title2+"\",POST=\""+post2+"\",DATEDETAIL=\""+mydate+"\" WHERE noteid="+pri;
				mydb.execSQL(UPDATE_TABLE);
				mydb.close();
				Toast.makeText(getApplicationContext(), "修改成功！", Toast.LENGTH_LONG).show();
				
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
}