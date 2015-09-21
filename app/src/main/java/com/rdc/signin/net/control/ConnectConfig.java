package com.rdc.signin.net.control;

/**
 * Created by seasonyuu on 15/9/17.
 */
public class ConnectConfig {
	public static final String HOST = "http://rdcjim.duapp.com/Signin_Project/index.php/";

	public static final String TOKEN = "token";
	public static final String RESULT = "result";
	public static final String ACT = "act";

	public static class Login {
		public static final String URL_STD = HOST + "Student/Login/doLoginAction";
		public static final String URL_TCH = HOST + "Teacher/Login/doLoginAction";

		public static final String REQUEST_ACCOUNT = "account";
		public static final String REQUEST_PASSWORD = "password";

		public static final String RESPONSE_USER = "user";
		public static final String RESPONSE_NAME = "name";
		public static final String RESPONSE_COLLEGE = "college";
		public static final String RESPONSE_SEX = "sex";
		public static final String RESPONSE_MAJOR = "major";
		public static final String RESPONSE_EMAIL = "email";
		public static final String RESPONSE_TEL = "tel";
		public static final String RESPONSE_MAC = "mac";

	}

	public static class BindMac {
		public static final String URL_STD = HOST + "Student/Registe/registeMac";
		public static final String URL_TCH = HOST + "Teacher/Registe/registeMac";

		public static final String REQUEST_MAC = "mac";
		public static final String REQUEST_CHANNEL = "channel";
	}

	public static class GetClassList {
		public static final String URL_TCH = HOST + "Teacher/Class/GetClassListAction";
		public static final String URL_STD = HOST + "Student/Class/GetClassListAction";

		public static final String RESPONSE_CLASSES = "classes";
		public static final String RESPONSE_ID = "id";
		public static final String RESPONSE_NAME = "name";
		public static final String RESPONSE_TIME = "time";
		public static final String RESPONSE_SUM = "sum";
		public static final String RESPONSE_LOC = "loc";
		public static final String RESPONSE_ABOUT = "about";
		public static final String RESPONSE_HOUR = "hour";
		public static final String RESPONSE_WEEKS = "weeks";
	}
}
