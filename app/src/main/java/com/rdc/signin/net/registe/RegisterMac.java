package com.rdc.signin.net.registe;

import com.android.volley.Request;
import com.rdc.signin.app.SignInApp;
import com.rdc.signin.constant.User;
import com.rdc.signin.net.control.BaseConnect;
import com.rdc.signin.net.control.ConnectConfig;
import com.rdc.signin.net.control.ConnectListener;
import com.rdc.signin.utils.JSONUtils;
import com.rdc.signin.utils.WifiController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by seasonyuu on 15/9/19.
 */
public class RegisterMac extends BaseConnect {
	private User user;

	private final int RESULT_SUCCESS = 1;
	private final int RESULT_UN_LOGIN = 0;
	private final int RESULT_FAILED = -1;
	private final int RESULT_MAC_NULL = -2;
	private final int RESULT_ALREADY = -3;

	public RegisterMac(ConnectListener listener) {
		super(listener);

		user = SignInApp.user;
	}

	@Override
	protected int getMethod() {
		return Request.Method.POST;
	}

	@Override
	protected Map<String, String> getRequestParams() {
		HashMap<String, String> params = new HashMap<>();
		params.put(ConnectConfig.TOKEN, user.getToken());
		params.put(ConnectConfig.RegisteMac.REQUEST_MAC,
				new WifiController(SignInApp.getInstance().getApplicationContext()).getLocalMacAddress());
		params.put(ConnectConfig.RegisteMac.REQUEST_CHANNEL, SignInApp.getInstance().getChannelId());
		return params;
	}

	@Override
	protected String getUrl() {
		if (user.getIdentity() == User.IDENTITY_STUDENT)
			return ConnectConfig.RegisteMac.URL_STD;
		else return ConnectConfig.RegisteMac.URL_TCH;
	}

	@Override
	protected String getTag() {
		return RegisterMac.class.getSimpleName();
	}

	@Override
	protected void onResult(boolean isConnected, String reason, String response) {
		switch (JSONUtils.getResult(response)) {
			case RESULT_SUCCESS:
				if (listener != null)
					listener.onConnect(true, null, response);
				break;
			case RESULT_FAILED:
				if (listener != null)
					listener.onConnect(false, "绑定失败", null);
				break;
			case RESULT_MAC_NULL:
				if (listener != null)
					listener.onConnect(false, "Mac为空 或 获取不到设备Mac，请确认App拥有获取您的设备Mac地址的权限", null);
				break;
			case RESULT_UN_LOGIN:
				if (listener != null)
					listener.onConnect(false, "未登录 或 登录超时", null);
				break;
			case RESULT_ALREADY:
				if(listener!=null)
					listener.onConnect(false,"该设备已绑定了其他账号",null);
				break;
		}
	}
}
