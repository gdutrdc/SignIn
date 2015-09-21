package com.rdc.signin.utils;

import com.rdc.signin.constant.Clazz;
import com.rdc.signin.constant.User;
import com.rdc.signin.net.control.ConnectConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by seasonyuu on 15/9/19.
 */
public class JSONUtils {
	public static int FAILED_RESULT = -100;//没什么特殊含义 只是大概标记下

	public static int getResult(String json) {
		int result = FAILED_RESULT;
		try {
			JSONObject jsonObject = new JSONObject(json);
			result = jsonObject.getInt(ConnectConfig.RESULT);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static User getUser(String json) {
		User user = null;
		try {
			boolean isStudent;
			JSONObject wrapper = new JSONObject(json);
			JSONObject jsonObject = wrapper.getJSONObject(ConnectConfig.Login.RESPONSE_USER);
			isStudent = wrapper.getString(ConnectConfig.ACT).equals("student_login");

			user = new User();
			user.setToken(wrapper.getString(ConnectConfig.TOKEN));
			user.setIdentity(isStudent ? User.IDENTITY_STUDENT : User.IDENTITY_TEACHER);
			user.setAccount(jsonObject.getString(ConnectConfig.Login.REQUEST_ACCOUNT));
			user.setName(jsonObject.getString(ConnectConfig.Login.RESPONSE_NAME));
			user.setSex(jsonObject.getString(ConnectConfig.Login.RESPONSE_SEX));
			user.setCollege(jsonObject.getString(ConnectConfig.Login.RESPONSE_COLLEGE));
			user.setTel(jsonObject.getString(ConnectConfig.Login.RESPONSE_TEL));
			user.setEmail(jsonObject.getString(ConnectConfig.Login.RESPONSE_EMAIL));
			user.setMac(jsonObject.getString(ConnectConfig.Login.RESPONSE_MAC));
			if (isStudent)
				user.setMajor(jsonObject.getString(ConnectConfig.Login.RESPONSE_MAJOR));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return user;
	}

	public static ArrayList<Clazz> getClassList(String json) {
		ArrayList<Clazz> list = new ArrayList<>();
		try {
			JSONObject jsonObject = new JSONObject(json);
			JSONArray jsonArray = jsonObject.getJSONArray(ConnectConfig.GetClassList.RESPONSE_CLASSES);
			for (int i = 0; i < jsonArray.length(); i++) {
				list.add(getClass(jsonArray.get(i).toString()));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return list;
	}

	public static Clazz getClass(String json) {
		Clazz clazz = new Clazz();
		try {
			JSONObject jsonObject = new JSONObject(json);
			clazz.setId(jsonObject.getString(ConnectConfig.GetClassList.RESPONSE_ID));
			clazz.setName(jsonObject.getString(ConnectConfig.GetClassList.RESPONSE_NAME));
			clazz.setTime(jsonObject.getString(ConnectConfig.GetClassList.RESPONSE_TIME));
			clazz.setSum(jsonObject.getString(ConnectConfig.GetClassList.RESPONSE_SUM));
			clazz.setLoc(jsonObject.getString(ConnectConfig.GetClassList.RESPONSE_LOC));
			clazz.setAbout(jsonObject.getString(ConnectConfig.GetClassList.RESPONSE_ABOUT));
			clazz.setWeeks(jsonObject.getString(ConnectConfig.GetClassList.RESPONSE_WEEKS));
			clazz.setHour(jsonObject.getString(ConnectConfig.GetClassList.RESPONSE_HOUR));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return clazz;
	}
}
