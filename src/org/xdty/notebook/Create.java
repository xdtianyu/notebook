package org.xdty.notebook;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class Create extends Activity{
	
	public SQLiteDatabase mydb = null;
	public Calendar c=null;
	public final static String DATABASE_NAME = "mydb.db";
	public final static String TABLE_NAME = "myTable";
	public final static String ID="_id";
	public final static String NOTEID="noteid";
	public final static String TITLE="title";
	public final static String POST="post";
	public final static String DATE="date";
	public final static String DATEDETAIL="datedetail";
	public final static String DATECOUNT="datecount";
	public static String mydate,mydatedetail;
	public static int mydatecount;
	public static int year,month,day,hour,minute;
	public static int amorpm=0;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create);
    	final EditText edit1=(EditText)findViewById(R.id.c_editText1);
    	final EditText edit2=(EditText)findViewById(R.id.c_editText2);
        Button btn1=(Button)findViewById(R.id.c_button1);
        Button btn2=(Button)findViewById(R.id.c_button2);
        btn1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//生成时间
				c=Calendar.getInstance();
				year=c.get(Calendar.YEAR);
				month=c.get(Calendar.MONTH)+1;
				day=c.get(Calendar.DAY_OF_MONTH);
				amorpm=c.get(Calendar.AM_PM);
				hour=c.get(Calendar.HOUR);
				if(amorpm==Calendar.PM)
					hour+=12;
				minute=c.get(Calendar.MINUTE);
				mydate=""+year+"/"+month+"/"+day;
				mydatedetail=""+year+"年"+month+"月"+day+"日-"+hour+"时"+minute+"分";
				mydatecount=Integer.parseInt(""+year+month+day+hour+minute);
				mydb=openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
		        
				ContentValues cv=new ContentValues();
				cv.put(TITLE, edit1.getText().toString());
//				String test=edit1.getText().toString();
//				if (TextUtils.isEmpty(edit1.getText().toString()) )
//				Log.i("rw", "titile为"+test);
				cv.put(POST, edit2.getText().toString());
				cv.put(DATE, mydate);
				cv.put(DATEDETAIL, mydatedetail);
				cv.put(DATECOUNT, mydatecount);
				mydb.insert(TABLE_NAME, null, cv);
		        Cursor cur=mydb.query(TABLE_NAME, new String[]{ID,NOTEID,TITLE,POST,DATE,DATEDETAIL,DATECOUNT}, null, null, null, null, null);
		        cur.moveToLast();
		        
		        int max=Integer.parseInt(cur.getString(0));
		        int count=cur.getCount();
		        for (int i=0; i<count; i++)
		        {	
		        	cur.moveToPosition(i);
		        	for (int j=i;j<=max;j++)
		        	{
		        		if (cur.getString(0) != null)
		        		{
		        			if (j==Integer.parseInt(cur.getString(0)))
		        				mydb.execSQL("UPDATE "+TABLE_NAME+" SET "+NOTEID+"="+i+" WHERE _id="+j);
		        		}
		        	}
		        }
				mydb.close();
				Toast.makeText(getApplicationContext(), "保存成功！", Toast.LENGTH_LONG).show();
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