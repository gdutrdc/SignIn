package com.rdc.signin.net.control;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.lang.reflect.Field;
import java.util.Set;

/**
 * Created by seasonyuu on 15/9/16.
 */
public class NetworkControl {
	private static RequestQueue mRequestQueue;
	private static NetworkControl controller;

	private NetworkControl() {
	}

	public static NetworkControl getInstance() {
		if (controller == null)
			controller = new NetworkControl();
		return controller;
	}

	public static void init(Context context) {
		mRequestQueue = Volley.newRequestQueue(context);
	}

	public RequestQueue getRequestQueue() {
		if (mRequestQueue != null)
			return mRequestQueue;
		else
			throw new IllegalStateException("RequestQueue not initialized");
	}

	public void addRequest(Request<?> request, Object tag) {
		if (tag != null) {
			request.setTag(tag);
		}
		mRequestQueue.add(request);
	}

	public void cancelAll(Object tag) {
		mRequestQueue.cancelAll(tag);
	}

	/**
	 * 判断队列中是否还有标签为tag的请求还未完成
	 *
	 * @param tag 要查询的请求，如果为空，则为判断是否还有请求留在队列中
	 * @return 若有则返回true，否则为false
	 */
	public boolean isPendingToRequest(final Object tag) {
		try {
			Class clazz = mRequestQueue.getClass();
			Field mObject = clazz.getDeclaredField("mCurrentRequests");
			mObject.setAccessible(true);
			final Set<Request<?>> mCurrentRequests = (Set<Request<?>>) mObject.get(mRequestQueue);
			if (tag == null) {
				if (mCurrentRequests.size() != 0)
					return true;
			}
			for (final Request<?> request : mCurrentRequests) {

				if (request.getTag() != null && request.getTag().equals(tag)) {

					Log.d("tagIsPendingToRequest ", "PendingTag: " + request.getTag() + " tag:" + tag);
					return true;
				}
			}
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		return false;
	}

}
