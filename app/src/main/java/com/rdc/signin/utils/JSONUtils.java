package com.rdc.signin.utils;

import android.os.Parcelable;

import com.rdc.signin.constant.StdClass;
import com.rdc.signin.constant.TchClass;
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
			user.setIdentity(isStudent?User.IDENTITY_STUDENT:User.IDENTITY_TEACHER);
			user.setToken(wrapper.getString(ConnectConfig.TOKEN));
			user.setIdentity(isStudent ? User.IDENTITY_STUDENT : User.IDENTITY_TEACHER);
			user.setAccount(jsonObject.getString(ConnectConfig.Login.REQUEST_ACCOUNT));
			user.setName(jsonObject.getString(ConnectConfig.Login.RESPONSE_NAME));
			user.setSex(jsonObject.getString(ConnectConfig.Login.RESPONSE_SEX));
			user.setCollege(jsonObject.getString(ConnectConfig.Login.RESPONSE_COLLEGE));
			user.setTel(jsonObject.getString(ConnectConfig.Login.RESPONSE_TEL));
			user.setEmail(jsonObject.getString(ConnectConfig.Login.RESPONSE_EMAIL));
			user.setMac(jsonObject.getString(ConnectConfig.Login.RESPONSE_MAC));
			if (isStudent) {
				user.setMajor(jsonObject.getString(ConnectConfig.Login.RESPONSE_MAJOR));
				user.setValue(wrapper.getString(ConnectConfig.Login.RESPONSE_VALUE));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return user;
	}

	public static ArrayList<Parcelable> getStdClassList(String json) {
		ArrayList<Parcelable> list = null;
		try {
			JSONObject jsonObject = new JSONObject(json);
			list = new ArrayList<>();
			JSONArray jsonArray = jsonObject.getJSONArray(ConnectConfig.GetClassList.RESPONSE_CLASSES);
			for (int i = 0; i < jsonArray.length(); i++) {
				list.add(getStdClass(jsonArray.get(i).toString()));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return list;
	}

	public static StdClass getStdClass(String json) {
		StdClass stdClass = null;
		try {
			JSONObject jsonObject = new JSONObject(json);
			stdClass = new StdClass();
			stdClass.setClassId(jsonObject.getString(ConnectConfig.GetClassList.RESPONSE_CLASS_ID));
			stdClass.setClassName(jsonObject.getString(ConnectConfig.GetClassList.RESPONSE_CLASS_NAME));
			stdClass.setTime(jsonObject.getString(ConnectConfig.GetClassList.RESPONSE_TIME));
			stdClass.setHour(jsonObject.getString(ConnectConfig.GetClassList.RESPONSE_HOUR));
			stdClass.setAbout(jsonObject.getString(ConnectConfig.GetClassList.RESPONSE_ABOUT));
			stdClass.setWeeks(jsonObject.getString(ConnectConfig.GetClassList.RESPONSE_WEEKS));
			stdClass.setTeacherName(jsonObject.getString(ConnectConfig.GetClassList.RESPONSE_TEACHER_NAME));
			stdClass.setMac(jsonObject.getString(ConnectConfig.GetClassList.RESPONSE_MAC));
			stdClass.setRest(jsonObject.getString(ConnectConfig.GetClassList.RESPONSE_REST));
			stdClass.setSignTimes(jsonObject.getString(ConnectConfig.GetClassList.RESPONSE_SIGNTIMES));
			stdClass.setSum(jsonObject.getString(ConnectConfig.GetClassList.RESPONSE_SUM));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return stdClass;
	}

	public static ArrayList<Parcelable> getTchClassList(String json) {
		ArrayList<Parcelable> list = new ArrayList<>();
		try {
			JSONObject jsonObject = new JSONObject(json);
			JSONArray jsonArray = jsonObject.getJSONArray(ConnectConfig.GetClassList.RESPONSE_CLASSES);
			for (int i = 0; i < jsonArray.length(); i++) {
				list.add(getTchClass(jsonArray.get(i).toString()));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return list;
	}

	public static TchClass getTchClass(String json) {
		TchClass tchClass = new TchClass();
		try {
			JSONObject jsonObject = new JSONObject(json);
			tchClass.setId(jsonObject.getString(ConnectConfig.GetClassList.RESPONSE_ID));
			tchClass.setName(jsonObject.getString(ConnectConfig.GetClassList.RESPONSE_NAME));
			tchClass.setTime(jsonObject.getString(ConnectConfig.GetClassList.RESPONSE_TIME));
			tchClass.setSum(jsonObject.getString(ConnectConfig.GetClassList.RESPONSE_SUM));
			tchClass.setLoc(jsonObject.getString(ConnectConfig.GetClassList.RESPONSE_LOC));
			tchClass.setAbout(jsonObject.getString(ConnectConfig.GetClassList.RESPONSE_ABOUT));
			tchClass.setWeeks(jsonObject.getString(ConnectConfig.GetClassList.RESPONSE_WEEKS));
			tchClass.setHour(jsonObject.getString(ConnectConfig.GetClassList.RESPONSE_HOUR));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return tchClass;
	}
}
