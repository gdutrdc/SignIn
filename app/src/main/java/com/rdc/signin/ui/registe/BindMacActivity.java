package com.rdc.signin.ui.registe;

import android.content.DialogInterface;
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
import com.rdc.signin.ui.student.StdMainActivity;
import com.rdc.signin.ui.teacher.TchMainActivity;
import com.rdc.signin.utils.DialogUtils;
import com.rdc.signin.utils.JniMethods;
import com.rdc.signin.utils.WifiController;

/**
 * Created by seasonyuu on 15/9/20.
 */
public class BindMacActivity extends ToolbarActivity {
	private User user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bind_mac);

		user = (User) getIntent().getSerializableExtra("user");

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
						DialogUtils.showTipsDialog(BindMacActivity.this, "绑定成功", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								if (user.getIdentity() == User.IDENTITY_STUDENT) {
									String value = user.getValue();
									JniMethods methods = JniMethods.getInstance();
									value = methods.decrypt(value,
											JniMethods.getJniKey());
									methods.setValueKey(value);
									startActivity(new Intent(BindMacActivity.this, StdMainActivity.class));
								} else {
									startActivity(new Intent(BindMacActivity.this, TchMainActivity.class));
								}
								finish();
							}
						});
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

	@Override
	public void onBackPressed() {
		startActivity(new Intent(this, LoginActivity.class));
		super.onBackPressed();
	}
}
