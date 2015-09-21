package com.rdc.signin.ui.registe;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.rdc.signin.R;
import com.rdc.signin.net.control.ConnectListener;
import com.rdc.signin.net.registe.BindMac;
import com.rdc.signin.ui.common.ToolbarActivity;
import com.rdc.signin.utils.DialogUtils;

/**
 * Created by seasonyuu on 15/9/20.
 */
public class BindMacActivity extends ToolbarActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bind_mac);

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
