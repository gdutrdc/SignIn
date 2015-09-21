package com.rdc.signin.ui.registe;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.rdc.signin.R;
import com.rdc.signin.app.SignInApp;
import com.rdc.signin.constant.User;
import com.rdc.signin.net.control.ConnectListener;
import com.rdc.signin.net.registe.Login;
import com.rdc.signin.ui.common.ToolbarActivity;
import com.rdc.signin.ui.student.StdMainActivity;
import com.rdc.signin.ui.teacher.TchMainActivity;
import com.rdc.signin.utils.DialogUtils;
import com.rdc.signin.utils.JSONUtils;
import com.rdc.signin.utils.KeyboardUtils;
import com.rdc.signin.utils.UIUtils;
import com.rdc.signin.utils.WifiControler;

/**
 * Created by seasonyuu on 15/9/17.
 */
public class LoginActivity extends ToolbarActivity {
	private TextInputLayout mLoginAccount;
	private TextInputLayout mLoginPassword;
	private RadioGroup mLoginIdentityWrapper;
	private KeyboardUtils keyboardUtils;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		getSupportActionBar().setTitle(R.string.login);

		setupView();

		keyboardUtils = new KeyboardUtils(this, findViewById(R.id.login_tips));

		PushManager.startWork(getApplicationContext(), PushConstants.LOGIN_TYPE_API_KEY, "7aVEaZs5vUDRHuETxHRkgUxa");

	}

	private void setupView() {
		mLoginAccount = UIUtils.findView(this, R.id.login_account);
		mLoginPassword = UIUtils.findView(this, R.id.login_password);
		mLoginIdentityWrapper = UIUtils.findView(this, R.id.login_identity_wrapper);

		mLoginAccount.setHint(getString(R.string.account_std));
		mLoginPassword.setHint(getString(R.string.password));

		User user = SignInApp.getInstance().getRememberUser();
		if (mLoginAccount.getEditText() != null)
			mLoginAccount.getEditText().setText(user.getAccount());
		if (mLoginPassword.getEditText() != null)
			mLoginPassword.getEditText().setText(user.getPassword());
		if (user.getIdentity() == User.IDENTITY_STUDENT)
			((RadioButton) mLoginIdentityWrapper.getChildAt(1)).setChecked(true);
		else
			((RadioButton) mLoginIdentityWrapper.getChildAt(0)).setChecked(true);

		mLoginPassword.getEditText().setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_DONE) {
					keyboardUtils.hideKeyboard(LoginActivity.this);
					onClick(v);
					return true;
				}
				return false;
			}
		});

		mLoginIdentityWrapper.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if (checkedId == R.id.login_radio_std)
					mLoginAccount.setHint(getString(R.string.account_std));
				else
					mLoginAccount.setHint(getString(R.string.account_tch));
			}
		});
	}

	public void onClick(View v) {
		String account = null;
		String password = null;
		if (mLoginAccount.getEditText() != null)
			account = mLoginAccount.getEditText().getText().toString();
		if (mLoginPassword.getEditText() != null)
			password = mLoginPassword.getEditText().getText().toString();

		DialogUtils.showProgressDialog(this, "正在登录");
		login(account, password);
	}

	private void login(String account, String password) {
		new Login(account, password,
				((RadioButton) findViewById(R.id.login_radio_std)).isChecked() ?
						User.IDENTITY_STUDENT : User.IDENTITY_TEACHER, new ConnectListener() {
			@Override
			public void onConnect(boolean isConnect, String reason, String response) {
				if (isConnect) {
					Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
					DialogUtils.dismissAllDialog();

					User user = JSONUtils.getUser(response);
					user.setPassword(mLoginPassword.getEditText().getText().toString());

					SignInApp.getInstance().rememberUser(user);
					SignInApp.user = user;

					readUser(user);
				} else {
					DialogUtils.showWaringDialog(LoginActivity.this, reason);
				}
			}
		}).connect();
	}

	private void readUser(User user) {
		String mac = new WifiControler(this).getLocalMacAddress();
		if (user.getMac() == null || user.getMac().equals("null")) {
			startActivity(new Intent(this, BindMacActivity.class));
			finish();
		} else if (!user.getMac().equals(mac)) {
			DialogUtils.showWaringDialog(this, "该设备不是您所绑定的设备，如果有疑问请与管理员联系");
		} else {
			if (user.getIdentity() == User.IDENTITY_STUDENT)
				startActivity(new Intent(this, StdMainActivity.class));
			else
				startActivity(new Intent(this, TchMainActivity.class));
			finish();
		}

	}
}
