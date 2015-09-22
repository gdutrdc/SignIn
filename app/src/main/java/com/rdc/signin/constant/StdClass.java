package com.rdc.signin.constant;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by seasonyuu on 15/9/22.
 */
public class StdClass implements Parcelable {
	private String classId;
	private String className;
	private String time;
	private String hour;
	private String about;
	private String weeks;
	private String teacherName;
	private String rest;
	private String mac;
	private String loc;
	private String signTimes;
	private String sum;

	public String getLoc() {
		return loc;
	}

	public void setLoc(String loc) {
		this.loc = loc;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public String getWeeks() {
		return weeks;
	}

	public void setWeeks(String weeks) {
		this.weeks = weeks;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getRest() {
		return rest;
	}

	public void setRest(String rest) {
		this.rest = rest;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getSignTimes() {
		return signTimes;
	}

	public void setSignTimes(String signTimes) {
		this.signTimes = signTimes;
	}

	public String getSum() {
		return sum;
	}

	public void setSum(String sum) {
		this.sum = sum;
	}

	@Override
	public int describeContents() {
		return 1;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.classId);
		dest.writeString(this.className);
		dest.writeString(this.time);
		dest.writeString(this.hour);
		dest.writeString(this.about);
		dest.writeString(this.weeks);
		dest.writeString(this.teacherName);
		dest.writeString(this.rest);
		dest.writeString(this.mac);
		dest.writeString(this.signTimes);
		dest.writeString(this.sum);
	}

	public StdClass() {
	}

	protected StdClass(Parcel in) {
		this.classId = in.readString();
		this.className = in.readString();
		this.time = in.readString();
		this.hour = in.readString();
		this.about = in.readString();
		this.weeks = in.readString();
		this.teacherName = in.readString();
		this.rest = in.readString();
		this.mac = in.readString();
		this.signTimes = in.readString();
		this.sum = in.readString();
	}

	public static final Parcelable.Creator<StdClass> CREATOR = new Parcelable.Creator<StdClass>() {
		public StdClass createFromParcel(Parcel source) {
			return new StdClass(source);
		}

		public StdClass[] newArray(int size) {
			return new StdClass[size];
		}
	};
}
