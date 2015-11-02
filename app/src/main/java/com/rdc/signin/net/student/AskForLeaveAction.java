package com.rdc.signin.net.student;

import com.android.volley.Request;
import com.rdc.signin.app.SignInApp;
import com.rdc.signin.net.control.BaseConnect;
import com.rdc.signin.net.control.ConnectConfig;
import com.rdc.signin.net.control.ConnectListener;
import com.rdc.signin.utils.JSONUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by seasonyuu on 15/11/1.
 */
public class AskForLeaveAction extends BaseConnect {
	private String classId;

	public AskForLeaveAction(String classId, ConnectListener listener) {
		super(listener);
		this.classId = classId;
	}

	@Override
	protected int getMethod() {
		return Request.Method.POST;
	}

	@Override
	protected Map<String, String> getRequestParams() {
		HashMap<String, String> params = new HashMap<>();
		params.put(ConnectConfig.AskForLeave.REQUEST_CLASS_ID, classId);
		params.put(ConnectConfig.TOKEN, SignInApp.user.getToken());
		return params;
	}

	@Override
	protected String getUrl() {
		return ConnectConfig.AskForLeave.URL;
	}

	@Override
	protected String getTag() {
		return AskForLeaveAction.class.getSimpleName();
	}

	@Override
	protected void onResult(boolean isConnected, String reason, String response) {
		if (isConnected) {
			if (listener == null)
				return;
			int result = JSONUtils.getResult(response);
			switch (result) {
				case 1:
					listener.onConnect(true, null, response);
					break;
			}
		} else {
			if (listener != null)
				listener.onConnect(false, reason, response);
		}
	}
}
