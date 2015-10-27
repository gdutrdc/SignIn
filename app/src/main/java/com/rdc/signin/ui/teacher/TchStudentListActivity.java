package com.rdc.signin.ui.teacher;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.rdc.signin.R;
import com.rdc.signin.constant.Student;
import com.rdc.signin.net.control.ConnectListener;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tch_student_list);

		mRecyclerView = UIUtils.findView(this, R.id.tch_student_list);
		mSwipeRefreshLayout = UIUtils.findView(this, R.id.tch_student_list_refresh);

		mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
		adapter = new TchStudentListAdapter(this);
		mRecyclerView.setAdapter(adapter);

		mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				DialogUtils.showProgressDialog(TchStudentListActivity.this, "正在加载");
				getStudentList();
			}
		});
		mSwipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.RED, Color.GREEN);

		classId = getIntent().getStringExtra("classId");

		DialogUtils.showProgressDialog(this, "正在加载");
		getStudentList();
	}

	public void getStudentList() {
		new GetStudentList(classId, new ConnectListener() {

			@Override
			public void onConnect(boolean isConnect, String reason, String response) {
				if (isConnect) {
					ArrayList<Student> students = JSONUtils.getStudentList(response);
					adapter.setList(students);
					adapter.notifyDataSetChanged();
				} else {
					Toast.makeText(TchStudentListActivity.this,
							reason == null ? "Error" : reason, Toast.LENGTH_SHORT).show();
				}
				mSwipeRefreshLayout.setRefreshing(false);
				DialogUtils.dismissAllDialog();
			}
		}).connect();
	}
}
