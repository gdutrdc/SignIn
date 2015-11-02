package com.rdc.signin.net.registe;

import com.android.volley.Request;
import com.rdc.signin.constant.User;
import com.rdc.signin.net.control.BaseConnect;
import com.rdc.signin.net.control.ConnectConfig;
import com.rdc.signin.net.control.ConnectListener;
import com.rdc.signin.utils.JSONUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by seasonyuu on 15/9/17.
 */
public class DoLoginAction extends BaseConnect {
	private String account;
	private String password;
	private int identity;

	private final int RESULT_SUCCESS = 1;
	private final int RESULT_ACCOUNT_NOT_EXIST = 2;
	private final int RESULT_PASSWORD_WRONG = 3;

	public DoLoginAction(String account, String password, int identity, ConnectListener listener) {
		super(listener);
		this.identity = identity;
		this.account = account;
		this.password = password;
	}

	@Override
	protected int getMethod() {
		return Request.Method.POST;
	}

	@Override
	protected Map<String, String> getRequestParams() {
		HashMap<String, String> map = new HashMap<>();
		map.put(ConnectConfig.Login.REQUEST_ACCOUNT, account);
		map.put(ConnectConfig.Login.REQUEST_PASSWORD, password);
		return map;
	}

	@Override
	protected String getUrl() {
		String url = null;
		if (identity == User.IDENTITY_STUDENT)
			url = ConnectConfig.Login.URL_STD;
		else if (identity == User.IDENTITY_TEACHER)
			url = ConnectConfig.Login.URL_TCH;
		return url;
	}

	@Override
	protected String getTag() {
		return DoLoginAction.class.getSimpleName();
	}

	@Override
	protected void onResult(boolean isConnected, String reason, String response) {
		if (isConnected)
			switch (JSONUtils.getResult(response)) {
				case RESULT_SUCCESS:
					if (listener != null)
						listener.onConnect(true, reason, response);
					break;
				case RESULT_ACCOUNT_NOT_EXIST:
					if (listener != null)
						listener.onConnect(false, "账号不存在", response);
					break;
				case RESULT_PASSWORD_WRONG:
					if (listener != null)
						listener.onConnect(false, "密码不正确", response);
					break;
			}
		else if (listener != null)
			listener.onConnect(false, reason, null);
	}
}
