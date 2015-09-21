package com.rdc.signin.ui.student;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.rdc.signin.R;
import com.rdc.signin.ui.common.AbsMainActivity;

/**
 * Created by seasonyuu on 15/9/20.
 */
public class StdMainActivity extends AbsMainActivity {
	@Override
	protected void onRefresh() {

	}

	@Override
	protected void initRecyclerView(RecyclerView recyclerView) {

	}

	@Override
	protected View createNavigationView() {
		View view = View.inflate(this, R.layout.drawer_std, null); // just for test
		return view;
	}
}
