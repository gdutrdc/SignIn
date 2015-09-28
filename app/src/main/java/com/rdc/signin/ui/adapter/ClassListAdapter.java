package com.rdc.signin.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rdc.signin.R;
import com.rdc.signin.app.SignInApp;
import com.rdc.signin.constant.StdClass;
import com.rdc.signin.constant.TchClass;
import com.rdc.signin.constant.User;
import com.rdc.signin.ui.student.StdClassActivity;
import com.rdc.signin.utils.UIUtils;

import java.util.ArrayList;

/**
 * Created by seasonyuu on 15/9/21.
 */
public class ClassListAdapter extends RecyclerView.Adapter<ClassListAdapter.ClassViewHolder> {
	private Context context;
	private ArrayList<Parcelable> list;

	public ClassListAdapter(Context context) {
		this.context = context;
		list = new ArrayList<>();
	}

	public void setClassList(ArrayList<Parcelable> list) {
		this.list = list;
	}

	public ArrayList<Parcelable> getClassList() {
		return list;
	}

	@Override
	public ClassListAdapter.ClassViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		ClassViewHolder holder;
		if (SignInApp.user.getIdentity() == User.IDENTITY_STUDENT)
			holder = new ClassViewHolder(
					LayoutInflater.from(context).inflate(R.layout.item_three_line, parent, false));
		else
			holder = new ClassViewHolder(
					LayoutInflater.from(context).inflate(R.layout.item_two_line, parent, false));
		return holder;
	}

	@Override
	public void onBindViewHolder(ClassViewHolder holder, int position) {
		holder.position = position;

		if (SignInApp.user.getIdentity() == User.IDENTITY_STUDENT) {
			StdClass stdClass = (StdClass) list.get(position);
			holder.tvName.setText(stdClass.getClassName());
			holder.tvTeacher.setText(stdClass.getTeacherName() + " 老师");
			holder.tvTime.setText(stdClass.getTime());
		} else {
			TchClass tchClass = (TchClass) list.get(position);
			holder.tvName.setText(tchClass.getName());
			holder.tvTime.setText(tchClass.getTime());
		}
	}

	@Override
	public int getItemCount() {
		return list.size();
	}

	public class ClassViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
		public TextView tvName;
		public TextView tvTime;
		public TextView tvTeacher;

		public int position;

		public ClassViewHolder(View itemView) {
			super(itemView);
			tvName = UIUtils.findView(itemView, R.id.tv_first_line);
			tvTime = UIUtils.findView(itemView, R.id.tv_second_line);
			if (SignInApp.user.getIdentity() == User.IDENTITY_STUDENT)
				tvTeacher = UIUtils.findView(itemView, R.id.tv_third_line);
			itemView.setOnClickListener(this);
		}


		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			if (SignInApp.user.getIdentity() == User.IDENTITY_STUDENT)
				intent.setClass(context, StdClassActivity.class);
			else {

			}
			intent.putExtra("class", list.get(position));
			context.startActivity(intent);
		}
	}
}
