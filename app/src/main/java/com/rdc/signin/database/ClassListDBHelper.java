package com.rdc.signin.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Parcelable;

import com.rdc.signin.app.SignInApp;
import com.rdc.signin.constant.StdClass;
import com.rdc.signin.constant.TchClass;
import com.rdc.signin.constant.User;

import java.util.ArrayList;

/**
 * Created by seasonyuu on 15/9/22.
 */
public class ClassListDBHelper extends SQLiteOpenHelper {

	public static final String TAG = ClassListDBHelper.class.getSimpleName();
	/**
	 * 数据库名称
	 **/
	public static String DATABASE_NAME = "class_list.db";

	/**
	 * 数据库版本
	 **/
	public static int DATABASE_VERSION = 1;

	private static final String TABLE_NAME = "classes";

	private static final String ACCOUNT = "account";
	private static final String CLASS_ID = "classId";
	private static final String CLASS_NAME = "className";
	private static final String TIME = "time";
	private static final String HOUR = "hour";
	private static final String ABOUT = "about";
	private static final String WEEKS = "weeks";
	private static final String TEACHER_NAME = "teacherName";
	private static final String REST = "rest";
	private static final String MAC = "mac";
	private static final String LOC = "loc";
	private static final String SIGN_TIMES = "signTimes";
	private static final String SUM = "sum";

	public ClassListDBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}


	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table " + TABLE_NAME + "("
						+ "_id integer primary key autoincrement,"
						+ CLASS_ID + " text,"
						+ CLASS_NAME + " text,"
						+ ACCOUNT + " varchar(10),"
						+ TIME + " text,"
						+ HOUR + " varchar(4),"
						+ ABOUT + " text,"
						+ LOC + " text,"
						+ WEEKS + " text,"
						+ TEACHER_NAME + " text,"
						+ REST + " varchar(4),"
						+ MAC + " text,"
						+ SIGN_TIMES + " varchar(4),"
						+ SUM + " varchar(4))"
		);

	}

	public void writeClassList(SQLiteDatabase db, ArrayList<Parcelable> list) {
		if (SignInApp.user.getIdentity() == User.IDENTITY_STUDENT) {
			for (Parcelable data : list) {
				StdClass stdClass = (StdClass) data;

				db.delete(TABLE_NAME,CLASS_ID +" = ?",new String[]{stdClass.getClassId()});

				ContentValues values = new ContentValues();
				values.put(ACCOUNT, SignInApp.user.getAccount());
				values.put(CLASS_ID, stdClass.getClassId());
				values.put(CLASS_NAME, stdClass.getClassName());
				values.put(TIME, stdClass.getTime());
				values.put(HOUR, stdClass.getHour());
				values.put(ABOUT, stdClass.getAbout());
				values.put(WEEKS, stdClass.getWeeks());
				values.put(TEACHER_NAME, stdClass.getTeacherName());
				values.put(MAC, stdClass.getMac());
				values.put(REST, stdClass.getRest());
				values.put(SIGN_TIMES, stdClass.getSignTimes());
				values.put(SUM, stdClass.getSum());
				db.insert(TABLE_NAME, null, values);
			}
		} else {
			for (Parcelable data : list) {
				TchClass tchClass = (TchClass) data;

				db.delete(TABLE_NAME,CLASS_ID+" = ?",new String[]{tchClass.getId()});

				ContentValues values = new ContentValues();
				values.put(ACCOUNT, SignInApp.user.getAccount());
				values.put(CLASS_ID, tchClass.getId());
				values.put(CLASS_NAME, tchClass.getName());
				values.put(TIME, tchClass.getTime());
				values.put(HOUR, tchClass.getHour());
				values.put(ABOUT, tchClass.getAbout());
				values.put(WEEKS, tchClass.getWeeks());
				values.put(SUM, tchClass.getSum());
				values.put(LOC, tchClass.getLoc());
				db.insert(TABLE_NAME, null, values);
			}
		}

	}

	public ArrayList<Parcelable> readClassList(SQLiteDatabase db) {
		ArrayList<Parcelable> list = new ArrayList<>();
		Cursor cursor = db.query(
				TABLE_NAME, null, "account = ?", new String[]{SignInApp.user.getAccount()}, null, null, null);
		while (cursor.moveToNext()) {
			if (SignInApp.user.getIdentity() == User.IDENTITY_STUDENT) {
				StdClass stdClass = new StdClass();
				stdClass.setClassId(cursor.getString(cursor.getColumnIndex(CLASS_ID)));
				stdClass.setClassName(cursor.getString(cursor.getColumnIndex(CLASS_NAME)));
				stdClass.setTime(cursor.getString(cursor.getColumnIndex(TIME)));
				stdClass.setHour(cursor.getString(cursor.getColumnIndex(HOUR)));
				stdClass.setAbout(cursor.getString(cursor.getColumnIndex(ABOUT)));
				stdClass.setWeeks(cursor.getString(cursor.getColumnIndex(WEEKS)));
				stdClass.setTeacherName(cursor.getString(cursor.getColumnIndex(TEACHER_NAME)));
				stdClass.setMac(cursor.getString(cursor.getColumnIndex(MAC)));
				stdClass.setRest(cursor.getString(cursor.getColumnIndex(REST)));
				stdClass.setSignTimes(cursor.getString(cursor.getColumnIndex(SIGN_TIMES)));
				stdClass.setSum(cursor.getString(cursor.getColumnIndex(SUM)));

				list.add(stdClass);
			} else {
				TchClass tchClass = new TchClass();
				tchClass.setName(cursor.getString(cursor.getColumnIndex(CLASS_NAME)));
				tchClass.setId(cursor.getString(cursor.getColumnIndex(CLASS_ID)));
				tchClass.setTime(cursor.getString(cursor.getColumnIndex(TIME)));
				tchClass.setHour(cursor.getString(cursor.getColumnIndex(HOUR)));
				tchClass.setAbout(cursor.getString(cursor.getColumnIndex(ABOUT)));
				tchClass.setSum(cursor.getString(cursor.getColumnIndex(SUM)));
				tchClass.setLoc(cursor.getString(cursor.getColumnIndex(LOC)));
				tchClass.setWeeks(cursor.getString(cursor.getColumnIndex(WEEKS)));

				list.add(tchClass);
			}
		}
		return list;
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}
}
