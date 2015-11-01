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
 * Created by seasonyuu on 15/10/31.
 */
public class DoSignAction extends BaseConnect {
	private String[] studentIds;
	private String[] signInTimes;
	private String classId;

	public DoSignAction(String classId, String[] studentIds, String[] signInTimes, ConnectListener listener) {
		super(listener);
		this.classId = classId;
		this.signInTimes = signInTimes;
		this.studentIds = studentIds;
	}

	@Override
	protected int getMethod() {
		return Request.Method.POST;
	}

	@Override
	protected Map<String, String> getRequestParams() {
		HashMap<String, String> params = new HashMap<>();
		params.put(ConnectConfig.DoSign.REQUEST_CLASS_ID, classId);
		params.put(ConnectConfig.TOKEN, SignInApp.user.getToken());
		params.put(ConnectConfig.DoSign.REQUEST_DATE,
				new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(new Date()));
		params.put(ConnectConfig.DoSign.REQUEST_STUDENTS, handlerArray(studentIds));
		params.put(ConnectConfig.DoSign.REQUEST_TIMES, handlerArray(signInTimes));
		return params;
	}

	public String handlerArray(String[] array) {
		String result = "";
		for (int i = 0; i < array.length; i++) {
			result += array[i] + ",";
		}
		return result.substring(0, result.length() - 1);
	}

	@Override
	protected String getUrl() {
		return ConnectConfig.DoSign.URL;
	}

	@Override
	protected String getTag() {
		return DoSignAction.class.getSimpleName();
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
				case 0:
					listener.onConnect(false, "用户未登录，请重新登录", null);
					break;
				case -1:
					listener.onConnect(false, "提交失败，请确定该学生账号等信息无误", null);
			}
		} else {
			if (listener != null)
				listener.onConnect(false, reason, null);
		}

	}
}
