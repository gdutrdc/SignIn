package com.rdc.signin.net.common;

import com.android.volley.Request;
import com.rdc.signin.app.SignInApp;
import com.rdc.signin.constant.User;
import com.rdc.signin.net.control.BaseConnect;
import com.rdc.signin.net.control.ConnectConfig;
import com.rdc.signin.net.control.ConnectListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by seasonyuu on 15/9/21.
 */
public class GetClassList extends BaseConnect {
	public GetClassList(ConnectListener listener) {
		super(listener);
	}

	@Override
	protected int getMethod() {
		return Request.Method.POST;
	}

	@Override
	protected Map<String, String> getRequestParams() {
		HashMap<String, String> params = new HashMap<>();
		params.put(ConnectConfig.TOKEN,SignInApp.user.getToken());
		return params;
	}

	@Override
	protected String getUrl() {
		return SignInApp.user.getIdentity() == User.IDENTITY_STUDENT?ConnectConfig.GetClassList.URL_STD:ConnectConfig.GetClassList.URL_TCH;
	}

	@Override
	protected String getTag() {
		return GetClassList.class.getSimpleName();
	}

	@Override
	protected void onResult(boolean isConnected, String reason, String response) {

	}
}
