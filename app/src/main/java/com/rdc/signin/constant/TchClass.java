package com.rdc.signin.constant;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by seasonyuu on 15/9/21.
 */
public class TchClass implements Parcelable {
	private String id;
	private String name;
	private String time;
	private String sum;
	private String loc;
	private String about;
	private String weeks;
	private String hour;
	private String teacher;

	public String getTeacher() {
		return teacher;
	}

	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getSum() {
		return sum;
	}

	public void setSum(String sum) {
		this.sum = sum;
	}

	public String getLoc() {
		return loc;
	}

	public void setLoc(String loc) {
		this.loc = loc;
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

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	@Override
	public int describeContents() {
		return 1;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.id);
		dest.writeString(this.name);
		dest.writeString(this.time);
		dest.writeString(this.sum);
		dest.writeString(this.loc);
		dest.writeString(this.about);
		dest.writeString(this.weeks);
		dest.writeString(this.hour);
		dest.writeString(this.teacher);
	}

	public TchClass() {
	}

	protected TchClass(Parcel in) {
		this.id = in.readString();
		this.name = in.readString();
		this.time = in.readString();
		this.sum = in.readString();
		this.loc = in.readString();
		this.about = in.readString();
		this.weeks = in.readString();
		this.hour = in.readString();
		this.teacher = in.readString();
	}

	public static final Parcelable.Creator<TchClass> CREATOR = new Parcelable.Creator<TchClass>() {
		public TchClass createFromParcel(Parcel source) {
			return new TchClass(source);
		}

		public TchClass[] newArray(int size) {
			return new TchClass[size];
		}
	};
}
