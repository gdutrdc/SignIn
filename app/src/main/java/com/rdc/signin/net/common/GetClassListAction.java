package com.rdc.signin.net.common;

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
 * Created by seasonyuu on 15/9/21.
 */
public class GetClassListAction extends BaseConnect {
	public final int RESULT_SUCCESS = 1;
	public final int RESULT_UNLOGIN = 0;

	public GetClassListAction(ConnectListener listener) {
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
		return SignInApp.user.getIdentity() == User.IDENTITY_STUDENT ? ConnectConfig.GetClassList.URL_STD : ConnectConfig.GetClassList.URL_TCH;
	}

	@Override
	protected String getTag() {
		return GetClassListAction.class.getSimpleName();
	}

	@Override
	protected void onResult(boolean isConnected, String reason, String response) {
		if (isConnected) {
			if (listener != null) {
				switch (JSONUtils.getResult(response)) {
					case RESULT_SUCCESS:
						listener.onConnect(true, null, response);
						break;
					case RESULT_UNLOGIN:
						listener.onConnect(false, "登录过期,请重新登录", null);
						break;
					case -1:
						listener.onConnect(false, "列表为空", null);
					default:
						listener.onConnect(false, "连接错误", null);
				}
			}
		} else if (listener != null) {
			listener.onConnect(false, reason, null);
		}
	}
}