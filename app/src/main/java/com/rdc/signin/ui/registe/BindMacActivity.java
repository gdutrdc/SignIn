package com.rdc.signin.ui.registe;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.rdc.signin.R;
import com.rdc.signin.app.SignInApp;
import com.rdc.signin.constant.User;
import com.rdc.signin.net.control.ConnectListener;
import com.rdc.signin.net.registe.BindMac;
import com.rdc.signin.ui.common.ToolbarActivity;
import com.rdc.signin.utils.DialogUtils;
import com.rdc.signin.utils.WifiController;

import java.io.IOException;

/**
 * Created by seasonyuu on 15/9/20.
 */
public class BindMacActivity extends ToolbarActivity {
	private User user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bind_mac);

		try {
			user = User.deSerialization(getIntent().getStringExtra("user"));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	public void onClick(View view) {
		DialogUtils.showProgressDialog(this, "正在绑定");
		bindMac();
	}

	private void bindMac() {
		new BindMac(new ConnectListener() {
			@Override
			public void onConnect(boolean isConnect, String reason, String response) {
				if (isConnect) {
					if (user != null) {
						user.setMac(new WifiController(BindMacActivity.this).getLocalMacAddress());
						SignInApp.getInstance().rememberUser(user);
						SignInApp.user = user;
					}
				} else {
					DialogUtils.showWaringDialog(BindMacActivity.this, reason);
				}
			}
		}).connect();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			startActivity(new Intent(this, LoginActivity.class));
			onBackPressed();
		}
		return super.onOptionsItemSelected(item);
	}
}
