package com.rdc.signin.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.rdc.signin.constant.Student;

import java.util.ArrayList;

public class TchRegSQLiteHelper extends SQLiteOpenHelper {
	private static int version = 1;// 数据库名称
	private static final String name = "registration.db";

	private static final String TABLE_RESULT = "result";

	public TchRegSQLiteHelper(Context context) {
		super(context, name, null, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "create table result(_id integer primary key autoincrement,classId varchar(16),date varchar(16),account varchar(16),name text,sign_in_time varchar(16))";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

	public void insertData(String[] params) {
		SQLiteDatabase db = getWritableDatabase();
		String sql = "insert into result(classId,date,account,name,sign_in_time) values(?,?,?,?,?)";
		db.execSQL(sql, params);
		db.close();
	}

	public ArrayList<Student> getDataFromLocal(String classId, String date) {
		SQLiteDatabase db = getReadableDatabase();
		ArrayList<Student> list = new ArrayList<>();
		Cursor cursor = db.query(TABLE_RESULT, null, "classId=? and date=?",
				new String[]{classId, date}, null, null, null);
		while (cursor.moveToNext()) {
			Student student = new Student();
			student.setAccount(cursor.getString(cursor.getColumnIndex("account")));
			student.setName(cursor.getString(cursor.getColumnIndex("name")));
			student.setSignInTime(cursor.getString(cursor.getColumnIndex("sign_in_time")));
			list.add(student);
		}
		db.close();
		cursor.close();
		return list;
	}

	public void deleteData(String classId, String date) {
		SQLiteDatabase db = getWritableDatabase();
		db.delete(TABLE_RESULT, "classId = ? and date =?", new String[]{classId, date});
		db.close();
	}
}
