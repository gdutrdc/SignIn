package com.rdc.signin.net.student;

import com.android.volley.Request;
import com.rdc.signin.app.SignInApp;
import com.rdc.signin.constant.User;
import com.rdc.signin.net.control.BaseConnect;
import com.rdc.signin.net.control.ConnectConfig;
import com.rdc.signin.net.control.ConnectListener;
import com.rdc.signin.utils.JSONUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by seasonyuu on 15/11/1.
 */
public class GetMsgList extends BaseConnect {
	public GetMsgList(ConnectListener listener) {
		super(listener);
	}

	@Override
	protected int getMethod() {
		return Request.Method.POST;
	}

	@Override
	protected Map<String, String> getRequestParams() {
		HashMap<String, String> params = new HashMap<>();
		params.put(ConnectConfig.TOKEN, SignInApp.user.getToken());
		return params;
	}

	@Override
	protected String getUrl() {
		if (SignInApp.user.getIdentity() == User.IDENTITY_STUDENT)
			return ConnectConfig.GetMsgList.URL_STD;
		return ConnectConfig.GetMsgList.URL_TCH;
	}

	@Override
	protected String getTag() {
		return GetMsgList.class.getSimpleName();
	}

	@Override
	protected void onResult(boolean isConnected, String reason, String response) {
		if (isConnected) {
			if (listener != null) {
				int result = JSONUtils.getResult(response);
				switch (result) {
					case 1:
						listener.onConnect(true, null, response);
						break;
					case -1:
						listener.onConnect(false, "列表为空", null);
						break;
				}
			}
		} else {
			if (listener != null)
				listener.onConnect(false, reason, response);
		}
	}
}
