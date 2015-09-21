package com.rdc.signin.ui.teacher;

import android.view.View;

import com.rdc.signin.R;
import com.rdc.signin.net.common.GetClassList;
import com.rdc.signin.net.control.ConnectListener;
import com.rdc.signin.ui.common.AbsMainActivity;

/**
 * Created by seasonyuu on 15/9/20.
 */
public class TchMainActivity extends AbsMainActivity {
	@Override
	protected void onRefresh() {
		new GetClassList(new ConnectListener(){
			@Override
			public void onConnect(boolean isConnect, String reason, String response) {

			}
		}).connect();
	}

	@Override
	protected View createNavigationView() {
		return View.inflate(this, R.layout.drawer_tch, null);
	}
}
