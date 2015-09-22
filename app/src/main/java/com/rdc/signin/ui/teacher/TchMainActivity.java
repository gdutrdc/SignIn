package com.rdc.signin.ui.teacher;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.rdc.signin.R;
import com.rdc.signin.app.SignInApp;
import com.rdc.signin.net.common.GetClassList;
import com.rdc.signin.net.control.ConnectListener;
import com.rdc.signin.ui.common.AbsMainActivity;
import com.rdc.signin.ui.registe.LoginActivity;
import com.rdc.signin.ui.adapter.ClassListAdapter;
import com.rdc.signin.utils.JSONUtils;

/**
 * Created by seasonyuu on 15/9/20.
 */
public class TchMainActivity extends AbsMainActivity implements View.OnClickListener {
	private ClassListAdapter adapter;

	@Override
	protected void onRefresh() {
		new GetClassList(new ConnectListener() {
			@Override
			public void onConnect(boolean isConnect, String reason, String response) {
				if (isConnect) {
					adapter.setClassList(JSONUtils.getTchClassList(response));
					adapter.notifyDataSetChanged();
					swipeRefreshLayout.setRefreshing(false);
				}
			}
		}).connect();
	}

	@Override
	protected void initRecyclerView(RecyclerView recyclerView) {
		adapter = new ClassListAdapter(this);
		recyclerView.setAdapter(adapter);
	}

	@Override
	protected View createNavigationView() {
		View view = View.inflate(this, R.layout.drawer_tch, null);

		((TextView) view.findViewById(R.id.tv_name_tch_main)).setText(SignInApp.user.getName() + " 老师");
		((TextView) view.findViewById(R.id.tv_college_tch_main)).setText(SignInApp.user.getCollege());
		view.findViewById(R.id.nav_exit_tch).setOnClickListener(this);
		view.findViewById(R.id.nav_message_tch).setOnClickListener(this);
		view.findViewById(R.id.nav_result_tch).setOnClickListener(this);
		view.findViewById(R.id.nav_settings_tch).setOnClickListener(this);
		view.findViewById(R.id.nav_switch_account_tch).setOnClickListener(this);
		return view;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.nav_exit_tch:
				onBackPressed();
				break;
			case R.id.nav_message_tch:
			case R.id.nav_result_tch:
			case R.id.nav_settings_tch:
			case R.id.nav_switch_account_tch:
				onBackPressed();
				startActivity(new Intent(this, LoginActivity.class));
				break;
		}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		saveClassList(adapter.getClassList());
	}
}
