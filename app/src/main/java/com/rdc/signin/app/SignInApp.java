package com.rdc.signin.app;

import android.app.Application;
import android.content.SharedPreferences;

import com.rdc.signin.constant.User;
import com.rdc.signin.net.control.NetworkControl;

/**
 * Created by seasonyuu on 15/9/16.
 */
public class SignInApp extends Application {
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

	public static SignInApp getInstance() {
		return application;
	}

	public User getRememberUser() {
		User user = new User();
		user.setAccount(_preferences.getString("account", ""));
		user.setPassword(_preferences.getString("password", ""));
		user.setIdentity(_preferences.getInt("identity", User.IDENTITY_STUDENT));
		return user;
	}

	public void rememberUser(User user) {
		SharedPreferences.Editor editor = _preferences.edit();
		editor.putString("account", user.getAccount());
		editor.putString("password", user.getPassword());
		editor.putInt("identity", user.getIdentity());
		editor.commit();
	}

	public void setChannelId(String channelId) {
		SharedPreferences.Editor editor = _preferences.edit();
		editor.putString("channelId", channelId);
		editor.commit();
	}

	public String getChannelId() {
		return _preferences.getString("channelId", "");
	}
}
