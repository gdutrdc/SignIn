package com.rdc.signin.ui.teacher;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.rdc.signin.R;
import com.rdc.signin.constant.Student;
import com.rdc.signin.net.control.ConnectListener;
import com.rdc.signin.net.teacher.GetCurStudents;
import com.rdc.signin.net.teacher.GetStudentList;
import com.rdc.signin.ui.adapter.TchStudentListAdapter;
import com.rdc.signin.ui.common.ToolbarActivity;
import com.rdc.signin.utils.DialogUtils;
import com.rdc.signin.utils.JSONUtils;
import com.rdc.signin.utils.UIUtils;

import java.util.ArrayList;

/**
 * Created by seasonyuu on 15/10/27.
 */
public class TchStudentListActivity extends ToolbarActivity {
	private RecyclerView mRecyclerView;
	private TchStudentListAdapter adapter;
	private SwipeRefreshLayout mSwipeRefreshLayout;
	private String classId;
	private int mType;

	public static final int TYPE_ALL = 0; // 显示该课程所有学生
	public static final int TYPE_CURRENT = 1; // 显示该课程当前已签到的学生

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tch_student_list);

		Intent intent = getIntent();
		classId = intent.getStringExtra("classId");
		mType = intent.getIntExtra("type", TYPE_ALL);

		mRecyclerView = UIUtils.findView(this, R.id.tch_student_list);
		mSwipeRefreshLayout = UIUtils.findView(this, R.id.tch_student_list_refresh);

		mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
		adapter = new TchStudentListAdapter(this);
		switch (mType) {
			case TYPE_ALL:
				adapter.setType(TchStudentListAdapter.TYPE_ALL);
				setTitle(R.string.student_list);
				break;
			case TYPE_CURRENT:
				adapter.setType(TchStudentListAdapter.TYPE_CURRENT);
				setTitle(R.string.list_had_sign_in);
				break;
		}
		mRecyclerView.setAdapter(adapter);

		mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				DialogUtils.showProgressDialog(TchStudentListActivity.this, "正在加载");
				getStudentList();
			}
		});
		mSwipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.RED, Color.GREEN);

		DialogUtils.showProgressDialog(this, "正在加载");
		getStudentList();
	}

	public void getStudentList() {
		switch (mType) {
			case TYPE_ALL:
				new GetStudentList(classId, new ConnectListener() {

					@Override
					public void onConnect(boolean isConnect, String reason, String response) {
						mSwipeRefreshLayout.setRefreshing(false);
						DialogUtils.dismissAllDialog();
						if (isConnect) {
							ArrayList<Student> students = JSONUtils.getStudentList(response);
							adapter.setList(students);
							adapter.notifyDataSetChanged();
						} else {
							DialogUtils.showWaringDialog(TchStudentListActivity.this,
									reason == null ? "Error" : reason);
						}
					}
				}).connect();
				break;
			case TYPE_CURRENT:
				new GetCurStudents(classId, new ConnectListener() {
					@Override
					public void onConnect(boolean isConnect, String reason, String response) {
						mSwipeRefreshLayout.setRefreshing(false);
						DialogUtils.dismissAllDialog();
						if (isConnect) {
							ArrayList<Student> students = JSONUtils.getStudentList(response);
							adapter.setList(students);
							adapter.notifyDataSetChanged();
						} else {
							DialogUtils.showWaringDialog(TchStudentListActivity.this, reason);
						}
					}
				}).connect();
				break;
		}
	}
}
