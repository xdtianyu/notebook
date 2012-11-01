package org.xdty.notebook;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class Notebook extends Activity {
	public SQLiteDatabase mydb = null;
	public final static String DATABASE_NAME = "mydb.db";
	public final static String TABLE_NAME = "myTable";
	public final static String ID="_id";
	public final static String NOTEID="noteid";
	public final static String TITLE="title";
	public final static String POST="post";
	public final static String DATE="date";
	public final static String DATECOUNT="datecount";
	public final static String 	DATEDETAIL="datedetail";
	public final static String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+"("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+NOTEID+" INTEGER,"+TITLE+" TEXT,"+POST+" TEXT,"+DATE+" TEXT, "+DATEDETAIL+" TEXT,"+DATECOUNT+" INTEGER)";
	private ListView list = null;
	private String[] titlelist;
	static int prime,del_id;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button btn1=(Button)findViewById(R.id.button1);
        Button btn2=(Button)findViewById(R.id.button2);
        Button btn3=(Button)findViewById(R.id.button3);
        Button btn4=(Button)findViewById(R.id.button4);
        
        //数据库
        mydb=openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
        try 
        {
        mydb.execSQL(CREATE_TABLE);
        }
       catch (Exception e)
       {
        }
        Cursor cur=mydb.query(TABLE_NAME, new String[]{ID,NOTEID,TITLE,POST,DATE,DATEDETAIL,DATECOUNT}, null, null, null, null, null);
        //列表
        list=(ListView)findViewById(R.id.listView1);
        //列表字符串数组
        String title,date;
        int i;
        int count=cur.getCount();
        titlelist=new String[count];
        cur.moveToFirst();
        for (i=0;i<count;i++)
        {
        	title=cur.getString(1);
        	date=cur.getString(3);
        	titlelist[count-i-1]=date+"  "+title;
        	cur.moveToNext();
        }
        cur.close();
        mydb.close();
        //显示列表
        list.setAdapter(new ArrayAdapter<String>(Notebook.this, android.R.layout.simple_list_item_1,titlelist));
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				prime=arg2;
				Intent intent=new Intent();
				intent.setClass(Notebook.this,Read.class);
				startActivity(intent);
			}
		});
        list.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				prime=position;
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
			}
        	
		});
        btn1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(Notebook.this, Create.class);
				startActivity(intent);
			}
		});
        btn2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(Notebook.this, Edit.class);
				startActivity(intent);
			}
		});
        btn3.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog();
			}
		});
        btn4.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
    }
    public void onResume() {
        super.onResume();    	
        mydb=openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

      Cursor cur=mydb.query(TABLE_NAME, new String[]{ID,TITLE,POST,DATE,DATEDETAIL}, null, null, null, null, null);
      //列表
      list=(ListView)findViewById(R.id.listView1);
      String title,date;
      int i;
      int count=cur.getCount();
      titlelist=new String[count];
      cur.moveToFirst();
      for (i=0;i<count;i++)
      {
      	title=cur.getString(1);
      	date=cur.getString(3);
      	titlelist[count-i-1]=date+"  "+title;
      	cur.moveToNext();
      }
      cur.close();
      mydb.close();
      //显示列表
      list.setAdapter(new ArrayAdapter<String>(Notebook.this, android.R.layout.simple_list_item_1,titlelist));
    }
	protected void dialog(){
		AlertDialog.Builder builder = new Builder(Notebook.this);


		builder.setMessage("确定要删除吗？");
		builder.setTitle("提示");
		builder.setPositiveButton("确认", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				mydb=openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
				Cursor cur=mydb.query(TABLE_NAME, new String[]{ID,NOTEID,TITLE,POST,DATE,DATEDETAIL,DATECOUNT}, null, null, null, null, null);
				int count=cur.getCount();
				del_id=count-prime-1;
				String DELETE="DELETE FROM "+TABLE_NAME+" WHERE "+NOTEID+"="+del_id;
				mydb.execSQL(DELETE);
				cur.moveToLast();
		       
				int max=Integer.parseInt(cur.getString(0));

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
				cur.close();
				mydb.close();
				Notebook.this.finish();
				Intent intent= new Intent();
				intent.setClass(Notebook.this, Notebook.class);
				startActivity(intent);
				Toast.makeText(getApplicationContext(), "删除成功！", Toast.LENGTH_LONG).show();
				
			}
		});
			builder.setNegativeButton("取消", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.dismiss();
				}
			});
			builder.create().show();
		}

}