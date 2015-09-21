package com.rdc.signin.ui.teacher.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rdc.signin.R;
import com.rdc.signin.app.SignInApp;
import com.rdc.signin.constant.Clazz;
import com.rdc.signin.constant.User;
import com.rdc.signin.utils.UIUtils;

import java.util.ArrayList;

/**
 * Created by seasonyuu on 15/9/21.
 */
public class ClassListAdapter extends RecyclerView.Adapter<ClassListAdapter.ClassViewHolder> {
	private Context context;
	private ArrayList<Clazz> list;

	public ClassListAdapter(Context context) {
		this.context = context;
		list = new ArrayList<>();
	}

	public void setClassList(ArrayList<Clazz> list) {
		this.list = list;
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
		Clazz clazz = list.get(position);
		holder.tvName.setText(clazz.getName());
		holder.tvTime.setText(clazz.getTime());
		if (holder.tvTeacher != null)
			holder.tvTeacher.setText(clazz.getTeacher());
	}

	@Override
	public int getItemCount() {
		return list.size();
	}

	public class ClassViewHolder extends RecyclerView.ViewHolder {
		public TextView tvName;
		public TextView tvTime;
		public TextView tvTeacher;

		public ClassViewHolder(View itemView) {
			super(itemView);
			tvName = UIUtils.findView(itemView, R.id.tv_first_line);
			tvTime = UIUtils.findView(itemView, R.id.tv_second_line);
			if (SignInApp.user.getIdentity() == User.IDENTITY_STUDENT)
				tvTeacher = UIUtils.findView(itemView, R.id.tv_third_line);
		}
	}
}
