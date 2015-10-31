package com.rdc.signin.net.teacher;

import com.android.volley.Request;
import com.rdc.signin.app.SignInApp;
import com.rdc.signin.net.control.BaseConnect;
import com.rdc.signin.net.control.ConnectConfig;
import com.rdc.signin.net.control.ConnectListener;
import com.rdc.signin.utils.JSONUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by seasonyuu on 15/10/28.
 */
public class AddClassRecord extends BaseConnect {
	private String classId;

	public AddClassRecord(String classId, ConnectListener listener) {
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
		params.put(ConnectConfig.AddClassRecord.REQUEST_CLASS_ID, classId);
		params.put(ConnectConfig.AddClassRecord.REQUEST_DATE,
				new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(new Date()));
		params.put(ConnectConfig.TOKEN, SignInApp.user.getToken());
		return params;
	}

	@Override
	protected String getUrl() {
		return ConnectConfig.AddClassRecord.URL;
	}

	@Override
	protected String getTag() {
		return AddClassRecord.class.getSimpleName();
	}

	@Override
	protected void onResult(boolean isConnected, String reason, String response) {
		if (isConnected) {
			int result = JSONUtils.getResult(response);
			switch (result) {
				case 1:
					if (listener != null)
						listener.onConnect(true, reason, response);
					break;
				case -1:
					if (listener != null)
						listener.onConnect(false, "未登录", response);
					break;
			}
		} else {
			if (listener != null)
				listener.onConnect(false, reason, response);
		}
	}
}
