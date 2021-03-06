package com.rdc.signin.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.rdc.signin.constant.PreferenceKeys;
import com.rdc.signin.constant.User;
import com.rdc.signin.net.control.NetworkControl;

import java.io.IOException;

/**
 * Created by seasonyuu on 15/9/16.
 */
public class SignInApp extends MultiDexApplication {
	private final String TAG = SignInApp.class.getSimpleName();

	private static SharedPreferences _preferences;
	private static SignInApp application;
	public static User user;

	public static final boolean DEBUG = true;

	@Override
	public void onCreate() {
		super.onCreate();
		application = this;
		_preferences = getSharedPreferences(getPackageName() + "_preferences", MODE_PRIVATE);
		NetworkControl.init(this);
	}

	@Override
	protected void attachBaseContext(Context base) {
		super.attachBaseContext(base);
		MultiDex.install(this);
	}

	public static SignInApp getInstance() {
		return application;
	}

	public SharedPreferences getPreferences() {
		return _preferences;
	}

	public User getRememberUser() {
		String s = _preferences.getString(PreferenceKeys.USER, "");
		User user = null;
		try {
			user = User.deSerialization(s);
		} catch (IOException e) {
			if (DEBUG) {
				Log.d(TAG, "user cannot serialize");
			}
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			if (DEBUG)
				Log.d(TAG, "user cannot serialize");
			e.printStackTrace();
		}
		return user;
	}

	public void rememberUser(User user) {
		SharedPreferences.Editor editor = _preferences.edit();
		try {
			editor.putString(PreferenceKeys.USER, User.serialize(user));
		} catch (IOException e) {
			e.printStackTrace();
			if (DEBUG)
				Log.d(TAG, "user cannot serialize");
		}
		editor.commit();
	}

	public void setChannelId(String channelId) {
		SharedPreferences.Editor editor = _preferences.edit();
		editor.putString(PreferenceKeys.CHANNEL_ID, channelId);
		editor.commit();
	}

	public String getChannelId() {
		return _preferences.getString(PreferenceKeys.CHANNEL_ID, "");
	}
}
