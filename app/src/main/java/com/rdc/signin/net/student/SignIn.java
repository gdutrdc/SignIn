package com.rdc.signin.net.student;

import com.android.volley.Request;
import com.rdc.signin.app.SignInApp;
import com.rdc.signin.net.control.BaseConnect;
import com.rdc.signin.net.control.ConnectConfig;
import com.rdc.signin.net.control.ConnectListener;
import com.rdc.signin.utils.JniMethods;

import org.apache.commons.codec.binary.RDCBase64;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by seasonyuu on 15/10/28.
 */
public class SignIn extends BaseConnect {
	private String classId;
	private String studentId;
	private String mac;

	public SignIn(String classId, String studentId, String mac, ConnectListener listener) {
		super(listener);
		this.classId = classId;
		this.studentId = studentId;
		this.mac = mac;
	}

	@Override
	protected int getMethod() {
		return Request.Method.POST;
	}

	@Override
	protected Map<String, String> getRequestParams() {
		HashMap<String, String> params = new HashMap<>();
		params.put(ConnectConfig.TOKEN, SignInApp.user.getToken());
		params.put(ConnectConfig.SignIn.RESPONSE_CLASS_ID, classId);
		params.put(ConnectConfig.SignIn.RESPONSE_STUDENT_ID, studentId);
		Date date = new Date();
		params.put(ConnectConfig.SignIn.RESPONSE_TIME, new SimpleDateFormat("HH:mm:ss", Locale.CHINA).format(date));

		JniMethods jniMethods = JniMethods.getInstance();
		String value = mac + "|" + new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(date);
		value = jniMethods.encrypt(value);
		value = new String(RDCBase64.encodeBase64(value.getBytes()));
		params.put(ConnectConfig.SignIn.RESPONSE_VALUE, value);
		return params;
	}

	@Override
	protected String getUrl() {
		return ConnectConfig.SignIn.URL_STD;
	}

	@Override
	protected String getTag() {
		return SignIn.class.getSimpleName();
	}

	@Override
	protected void onResult(boolean isConnected, String reason, String response) {

	}
}
