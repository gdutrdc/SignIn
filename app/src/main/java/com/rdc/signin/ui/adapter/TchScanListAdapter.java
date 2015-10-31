package com.rdc.signin.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rdc.signin.R;
import com.rdc.signin.constant.Student;

import java.util.ArrayList;

public class TchScanListAdapter extends BaseAdapter {
	private ArrayList<Student> mStudentList;
	private Context mContext;

	public TchScanListAdapter(Context context) {
		mContext = context;
		mStudentList = new ArrayList<>();
	}

	@Override
	public int getCount() {
		return mStudentList.size();
	}

	@Override
	public Student getItem(int position) {
		return mStudentList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public void setDataList(ArrayList<Student> list) {
		mStudentList = list;
	}

	public ArrayList<Student> getDataList() {
		return mStudentList;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder vh = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.item_tch_student_list, parent, false);
			vh = new ViewHolder();
			vh.account = (TextView) convertView
					.findViewById(R.id.tv_second_line);
			vh.name = (TextView) convertView.findViewById(R.id.tv_first_line);
			vh.signInTime = (TextView) convertView.findViewById(R.id.tch_student_sign_times);
			convertView.findViewById(R.id.tch_student_rest).setVisibility(View.GONE);
			convertView.setTag(vh);
		} else
			vh = (ViewHolder) convertView.getTag();
		vh.account.setText(mStudentList.get(position).getAccount());
		vh.name.setText(mStudentList.get(position).getName());
		vh.signInTime.setText(mStudentList.get(position).getSignInTime());
		return convertView;
	}


	private class ViewHolder {
		private TextView account;
		private TextView name;
		private TextView signInTime;
	}


}
