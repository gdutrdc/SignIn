package com.rdc.signin.net.control;

/**
 * Created by seasonyuu on 15/9/17.
 */
public class ConnectConfig {
	public static final String HOST = "http://rdcsignintest.duapp.com/Signin_Project/index.php/";

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
		public static final String RESPONSE_VALUE = "value";

	}

	public static class RegisteMac {
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

		public static final String RESPONSE_CLASS_ID = "classid";
		public static final String RESPONSE_CLASS_NAME = "classname";
		public static final String RESPONSE_MAC = "mac";
		public static final String RESPONSE_TEACHER_NAME = "teachername";
		public static final String RESPONSE_REST = "rest";
		public static final String RESPONSE_SIGN_TIMES = "signtimes";
	}

	public static class GetStudentList {
		public static final String URL_ALL = HOST + "Teacher/Student/GetStudentList";
		public static final String URL_CURRENT = HOST + "Teacher/Class/getCurStudents";

		public static final String REQUEST_CLASS_ID = "classId";
		public static final String REQUEST_DATE = "date";

		public static final String RESPONSE_ACCOUNT = "account";
		public static final String RESPONSE_NAME = "name";
		public static final String RESPONSE_REST = "rest";
		public static final String RESPONSE_TIME = "time";
		public static final String RESPONSE_SIGN_TIMES = "signtimes";
	}

	public static class SignIn {
		public static final String URL = HOST + "Student/Class/doSignInAction";

		public static final String REQUEST_CLASS_ID = "classId";
		public static final String REQUEST_STUDENT_ID = "studentId";
		public static final String REQUEST_VALUE = "value";
		public static final String REQUEST_TIME = "time";
	}

	public static class AddClassRecord {
		public static final String URL = HOST + "Teacher/Class/addClassRecord";

		public static final String REQUEST_CLASS_ID = "classId";
		public static final String REQUEST_DATE = "date";
	}

	public static class DoSign {
		public static final String URL = HOST + "Teacher/Class/doSignAction";

		public static final String REQUEST_CLASS_ID = "classId";
		public static final String REQUEST_STUDENTS = "students";
		public static final String REQUEST_TIMES = "times";
		public static final String REQUEST_DATE = "date";
	}

}
