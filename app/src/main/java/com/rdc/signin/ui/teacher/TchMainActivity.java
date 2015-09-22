package com.rdc.signin.ui.teacher;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.rdc.signin.R;
import com.rdc.signin.net.common.GetClassList;
import com.rdc.signin.net.control.ConnectListener;
import com.rdc.signin.ui.common.AbsMainActivity;
import com.rdc.signin.ui.teacher.adapter.ClassListAdapter;
import com.rdc.signin.utils.JSONUtils;

/**
 * Created by seasonyuu on 15/9/20.
 */
public class TchMainActivity extends AbsMainActivity {
	private ClassListAdapter adapter;

	@Override
	protected void onRefresh() {
		new GetClassList(new ConnectListener(){
			@Override
			public void onConnect(boolean isConnect, String reason, String response) {
				if(isConnect){
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
		return View.inflate(this, R.layout.drawer_tch, null);
	}
}
