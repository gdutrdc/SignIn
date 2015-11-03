package com.rdc.signin.ui.student;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.rdc.signin.R;
import com.rdc.signin.app.SignInApp;
import com.rdc.signin.database.ClassListDBHelper;
import com.rdc.signin.net.common.GetClassListAction;
import com.rdc.signin.net.control.ConnectListener;
import com.rdc.signin.ui.adapter.ClassListAdapter;
import com.rdc.signin.ui.common.AbsMainActivity;
import com.rdc.signin.ui.common.MessageListActivity;
import com.rdc.signin.ui.register.LoginActivity;
import com.rdc.signin.utils.JSONUtils;

/**
 * Created by seasonyuu on 15/9/20.
 */
public class StdMainActivity extends AbsMainActivity implements View.OnClickListener {
	private ClassListAdapter adapter;

	@Override
	protected void onRefresh() {

		new GetClassListAction(new ConnectListener() {
			@Override
			public void onConnect(boolean isConnect, String reason, String response) {
				if (isConnect) {
					adapter.setClassList(JSONUtils.getStdClassList(response));
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

		ClassListDBHelper helper = new ClassListDBHelper(this);
		SQLiteDatabase db = helper.getReadableDatabase();
		adapter.setClassList(helper.readClassList(db));
		adapter.notifyDataSetChanged();

		if (adapter.getItemCount() == 0) {
			onRefresh();
		}
	}

	@Override
	protected View createNavigationView() {
		View view = View.inflate(this, R.layout.drawer_std, null);

		((TextView) view.findViewById(R.id.tv_std_name)).setText(SignInApp.user.getName());
		((TextView) view.findViewById(R.id.tv_std_department)).setText(SignInApp.user.getCollege());
		((TextView) view.findViewById(R.id.tv_std_major_and_class)).setText(SignInApp.user.getMajor());

		view.findViewById(R.id.nav_exit_std).setOnClickListener(this);
		view.findViewById(R.id.nav_switch_account_std).setOnClickListener(this);
		view.findViewById(R.id.nav_settings_std).setOnClickListener(this);
		view.findViewById(R.id.nav_upload_failed_std).setOnClickListener(this);
		view.findViewById(R.id.nav_email_std).setOnClickListener(this);
		return view;
	}

	@Override
	protected void onSaveData() {
		saveClassList(adapter.getClassList());
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		saveClassList(adapter.getClassList());
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.nav_exit_std:
				finish();
				break;
			case R.id.nav_email_std:
				Intent message = new Intent(this, MessageListActivity.class);
				startActivity(message);
				break;
			case R.id.nav_settings_std:
				startSettingsActivity();
				break;
			case R.id.nav_upload_failed_std:
			case R.id.nav_switch_account_std:
				Intent login = new Intent(this, LoginActivity.class);
				login.putExtra("switch_user", true);
				startActivity(login);
				saveClassList(adapter.getClassList());
				finish();
				break;
		}
	}
}
