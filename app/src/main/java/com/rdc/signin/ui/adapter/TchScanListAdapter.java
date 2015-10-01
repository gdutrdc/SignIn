package com.rdc.signin.ui.adapter;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rdc.signin.R;
import com.rdc.signin.database.TchRegSQLiteHelper;
import com.rdc.signin.ui.teacher.TchScanActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TchScanListAdapter extends BaseAdapter {
	private List<String> mAccountList;
	private Context mContext;
	private String mClassId, mDate;
	private SQLiteDatabase mDatabase;
	private TchRegSQLiteHelper mHelper;

	public TchScanListAdapter(Context context) {
		mContext = context;
		mAccountList = new ArrayList<String>();
		mClassId = ((TchScanActivity) mContext).getClassId();
		mDate = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA)
				.format(new Date());
		mHelper = new TchRegSQLiteHelper(mContext);
		getData();
	}

	@Override
	public int getCount() {
		return mAccountList.size();
	}

	@Override
	public String getItem(int position) {
		return mAccountList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder vh = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.item_single_line, parent, false);
			vh = new ViewHolder();
			vh.account = (TextView) convertView
					.findViewById(R.id.tv_title);
			convertView.setTag(vh);
		} else
			vh = (ViewHolder) convertView.getTag();
		vh.account.setText(mAccountList.get(position));
		return convertView;
	}

	public void addData(String account) {
		if (mAccountList.contains(account)) {
			((TchScanActivity) mContext).displayDialog("该学号已签到过");
			return;
		}
		addItem(account);
		notifyDataSetChanged();
	}

	private void addItem(final String account) {
		mAccountList.add(account);
		if (mHelper == null) {
			mHelper = new TchRegSQLiteHelper(mContext);
			mDatabase = mHelper.getWritableDatabase();
		}
		try {
			insertData(mDatabase, new String[]{mClassId, mDate, account});
			((TchScanActivity) mContext).makeSucceedToast();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void getData() {
		mDatabase = mHelper.getReadableDatabase();
		Cursor cursor = mDatabase.query("result", null, "classId=? and date=?",
				new String[]{mClassId, mDate}, null, null, null);
		int colAccount = cursor.getColumnIndex("account");
		while (cursor.moveToNext())
			mAccountList.add(cursor.getString(colAccount));
	}

	private void insertData(SQLiteDatabase db, String[] params) {
		String sql = "insert into result(classId,date,account) values(?,?,?)";
		db.execSQL(sql, params);
	}

	private class ViewHolder {
		private TextView account;
	}

	public void closeDatabase() {
		if (mDatabase != null && mDatabase.isOpen())
			mDatabase.close();
	}

}
