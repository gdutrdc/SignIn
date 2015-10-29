package com.rdc.signin.constant;

/**
 * Created by seasonyuu on 15/10/28.
 */
public class Student {
	private String account;
	private String name;
	private String signInTime;
	private int rest;
	private int signtimes;

	public int getSigntimes() {
		return signtimes;
	}

	public void setSigntimes(int signtimes) {
		this.signtimes = signtimes;
	}

	public String getSignInTime() {
		return signInTime;
	}

	public void setSignInTime(String signInTime) {
		this.signInTime = signInTime;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRest() {
		return rest;
	}

	public void setRest(int rest) {
		this.rest = rest;
	}
}
