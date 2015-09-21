package com.rdc.signin.net.control;

/**
 * Created by seasonyuu on 15/9/17.
 */
public interface ConnectListener {
	void onConnect(boolean isConnect, String reason, String response);
}
