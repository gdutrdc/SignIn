package com.rdc.signin.net.teacher;

import com.android.volley.Request;
import com.rdc.signin.app.SignInApp;
import com.rdc.signin.net.control.BaseConnect;
import com.rdc.signin.net.control.ConnectConfig;
import com.rdc.signin.net.control.ConnectListener;
import com.rdc.signin.utils.JSONUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by seasonyuu on 15/10/27.
 */
public class GetStudentList extends BaseConnect {
	private String classId;

	public static final int RESULT_SUCCESS = 1;
	public static final int RESULT_FAILED = -1;

	public GetStudentList(String classId, ConnectListener listener) {
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
		params.put(ConnectConfig.TOKEN, SignInApp.user.getToken());
		params.put(ConnectConfig.GetStudentList.REQUEST_CLASS_ID, classId);
		return params;
	}

	@Override
	protected String getUrl() {
		return ConnectConfig.GetStudentList.URL;
	}

	@Override
	protected String getTag() {
		return GetStudentList.class.getSimpleName();
	}

	@Override
	protected void onResult(boolean isConnected, String reason, String response) {
		if (isConnected) {
			int result = JSONUtils.getResult(response);
			switch (result) {
				case RESULT_SUCCESS:
					if (listener != null)
						listener.onConnect(true, reason, response);
					break;
				case RESULT_FAILED:
					if (listener != null)
						listener.onConnect(false, "列表为空", response);
					break;

			}
		} else if (listener != null)
			listener.onConnect(false, reason, response);
	}
}
