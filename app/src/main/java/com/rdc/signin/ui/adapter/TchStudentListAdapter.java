package com.rdc.signin.ui.adapter;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rdc.signin.R;
import com.rdc.signin.constant.Student;
import com.rdc.signin.utils.UIUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;

/**
 * Created by seasonyuu on 15/10/28.
 */
public class TchStudentListAdapter extends RecyclerView.Adapter<TchStudentListAdapter.StudentViewHolder> {
	private Context context;
	private ArrayList<Student> list;

	public static final int TYPE_ALL = 0; // 显示该课程所有学生
	public static final int TYPE_CURRENT = 1; // 显示该课程当前已签到的学生
	private int type;

	@IntDef({TYPE_ALL, TYPE_CURRENT})
	@Retention(RetentionPolicy.SOURCE)
	public @interface Type {
	}

	public TchStudentListAdapter(Context context) {
		this.context = context;
		list = new ArrayList<>();
	}

	@Override
	public StudentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		StudentViewHolder holder = new StudentViewHolder(
				LayoutInflater.from(context).inflate(R.layout.item_tch_student_list, parent, false));
		return holder;
	}

	public int getType() {
		return type;
	}

	/**
	 * 设置列表要显示的学生类型
	 *
	 * @param type 必须为{@link #TYPE_ALL},{@link #TYPE_CURRENT}中的一个
	 */
	public void setType(@Type int type) {
		this.type = type;
	}

	@Override
	public void onBindViewHolder(StudentViewHolder holder, int position) {
		holder.show(list.get(position));
	}

	public void setList(ArrayList<Student> students) {
		list = students;
	}

	@Override
	public int getItemCount() {
		return list.size();
	}

	public class StudentViewHolder extends RecyclerView.ViewHolder {
		private TextView tvAccount;
		private TextView tvName;
		private TextView tvRest;
		private TextView tvSignTimes;

		public StudentViewHolder(View itemView) {
			super(itemView);
			tvAccount = UIUtils.findView(itemView, R.id.tv_second_line);
			tvName = UIUtils.findView(itemView, R.id.tv_first_line);
			tvRest = UIUtils.findView(itemView, R.id.tch_student_rest);
			tvSignTimes = UIUtils.findView(itemView, R.id.tch_student_sign_times);
		}

		public void show(Student student) {
			if (type == TYPE_ALL) {
				tvAccount.setText(student.getAccount());
				tvName.setText(student.getName());
				tvSignTimes.setText("签到次数: " + student.getSigntimes());
				tvRest.setText("请假次数: " + student.getRest());
			} else if(type == TYPE_CURRENT){
				tvAccount.setText(student.getAccount());
				tvName.setText(student.getName());
				tvSignTimes.setText("签到时间: " + student.getSignInTime());
				if (tvRest.getVisibility() == View.VISIBLE)
					tvRest.setVisibility(View.GONE);
			}
		}
	}
}
