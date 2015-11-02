package com.rdc.signin.net.control;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.rdc.signin.app.SignInApp;

import java.util.Map;

/**
 * Created by seasonyuu on 15/9/17.
 */
public abstract class BaseConnect {
	protected ConnectListener listener;

	protected BaseConnect(ConnectListener listener) {
		this.listener = listener;
	}

	/**
	 * 定义该连接的类型
	 *
	 * @return * Method.GET <br/>
	 * * Method.POST
	 */
	protected abstract int getMethod();

	/**
	 * 获得该连接的参数
	 *
	 * @return 包含参数的键值对Map
	 */
	protected abstract Map<String, String> getRequestParams();

	protected abstract String getUrl();

	/**
	 * 获得该连接的标签
	 *
	 * @return 连接的Tag
	 */
	protected abstract String getTag();

	/**
	 * 作为连接类内的回调，在数据传到Activity层多加一层判断处理
	 *
	 * @param isConnected 是否连接成功
	 * @param reason      连接失败原因(若连接成功则为空)
	 * @param response    连接成功返回数据(若连接失败则为空)
	 */
	protected abstract void onResult(boolean isConnected, String reason, String response);


	public void connect() {
		StringRequest request = new StringRequest(getMethod(), getUrl(), new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				if (SignInApp.DEBUG)
					Log.d(getTag(), response);
				onResult(true, null, response);
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Log.e("Volley", "onError true");
				onResult(false, error.getMessage(), null);
				error.printStackTrace();
			}
		}) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				return getRequestParams();
			}
		};
		NetworkControl.getInstance().addRequest(request, getTag());
	}

}
