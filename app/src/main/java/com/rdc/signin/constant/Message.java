package com.rdc.signin.constant;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by seasonyuu on 15/11/3.
 */
public class Message implements Parcelable {
	public static final int TYPE_SYSTEM = 0;
	public static final int TYPE_ASK_FOR_LEAVE = 1;

	public static final int HANDLED = 1;
	public static final int UNHANDLED = 0;

	private String senderAccount;
	private String senderName;
	private String msgId;
	private String time;
	private String detail;
	private int handle;
	private String className;
	private int type;

	public String getSenderAccount() {
		return senderAccount;
	}

	public void setSenderAccount(String senderAccount) {
		this.senderAccount = senderAccount;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public String getTime() {
		return time;
	}

	public String getFormatTime() {
		return shortCutTime(time);
	}

	public void setTime(String time) {
		this.time = time;
	}

	public void setTime(long time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.CHINA);
		this.time = sdf.format(Long.parseLong(time + "000"));
	}

	private String shortCutTime(String time) {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.CHINA);

		try {
			Date date = fmt.parse(time);
			Date now = new Date();
			if (date.getYear() == now.getYear()) {
				if (date.getDate() == now.getDate()) {
					return new SimpleDateFormat("HH:mm", Locale.CHINA).format(date);
				} else if (date.getDate() == now.getDate() - 1)
					return "昨天 " + new SimpleDateFormat("HH:mm", Locale.CHINA).format(date);
				else
					return new SimpleDateFormat("MM/dd", Locale.CHINA).format(date);
			} else
				return new SimpleDateFormat("yyyy/MM/dd", Locale.CHINA).format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "null";
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public int getHandle() {
		return handle;
	}

	public void setHandle(int handle) {
		this.handle = handle;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.senderAccount);
		dest.writeString(this.senderName);
		dest.writeString(this.msgId);
		dest.writeString(this.time);
		dest.writeString(this.detail);
		dest.writeInt(this.handle);
		dest.writeString(this.className);
		dest.writeInt(this.type);
	}

	public Message() {
	}

	protected Message(Parcel in) {
		this.senderAccount = in.readString();
		this.senderName = in.readString();
		this.msgId = in.readString();
		this.time = in.readString();
		this.detail = in.readString();
		this.handle = in.readInt();
		this.className = in.readString();
		this.type = in.readInt();
	}

	public static final Parcelable.Creator<Message> CREATOR = new Parcelable.Creator<Message>() {
		public Message createFromParcel(Parcel source) {
			return new Message(source);
		}

		public Message[] newArray(int size) {
			return new Message[size];
		}
	};
}
