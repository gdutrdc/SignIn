package com.rdc.signin.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TchRegSQLiteHelper extends SQLiteOpenHelper {
	private static int version = 1;// 数据库名称
	private static String name = "registration.db";
	private Context mContext;

	public TchRegSQLiteHelper(Context context) {
		super(context, name, null, version);
		mContext = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "create table result(_id integer primary key autoincrement,classId varchar(16),date varchar(16),account varchar(16))";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
