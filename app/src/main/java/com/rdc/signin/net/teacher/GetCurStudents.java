package com.rdc.signin.net.teacher;

import com.android.volley.Request;
import com.rdc.signin.R;
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
 * Created by seasonyuu on 15/10/29.
 */
public class GetCurStudents extends BaseConnect {
	private static final int RESULT_SUCCESS = 1;
	private static final int RESULT_UN_LOGIN = 0;
	private static final int RESULT_UN_SIGN_IN = -2;
	private static final int RESULT_FAILED = -1;

	private String classId;

	public GetCurStudents(String classId, ConnectListener listener) {
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
		params.put(ConnectConfig.GetStudentList.REQUEST_CLASS_ID, classId);
		params.put(ConnectConfig.GetStudentList.REQUEST_DATE,
				new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(new Date()));
		params.put(ConnectConfig.TOKEN, SignInApp.user.getToken());
		return params;
	}

	@Override
	protected String getUrl() {
		return ConnectConfig.GetStudentList.URL_CURRENT;
	}

	@Override
	protected String getTag() {
		return GetCurStudents.class.getSimpleName();
	}

	@Override
	protected void onResult(boolean isConnected, String reason, String response) {
		if (isConnected) {
			int result = JSONUtils.getResult(response);
			if (listener != null)
				switch (result) {
					case RESULT_SUCCESS:
						listener.onConnect(true, reason, response);
						break;
					case RESULT_FAILED:
						listener.onConnect(false,
								SignInApp.getInstance().getString(R.string.failed_to_get), null);
						break;
					case RESULT_UN_LOGIN:
						listener.onConnect(false,
								SignInApp.getInstance().getString(R.string.un_login), null);
						break;
					case RESULT_UN_SIGN_IN:
						listener.onConnect(false,
								SignInApp.getInstance().getString(R.string.un_sign_in), null);
						break;
				}
		} else {
			if (listener != null)
				listener.onConnect(false, reason, null);
		}
	}
}
