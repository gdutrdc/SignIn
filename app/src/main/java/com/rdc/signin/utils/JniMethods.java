package com.rdc.signin.utils;

import android.util.Log;

import com.rdc.signin.app.SignInApp;

import org.apache.commons.codec.binary.RDCBase64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class JniMethods {
	static {
		System.loadLibrary("methods-jni");
	}

	private static String valueKey = "";
	private static JniMethods instance;
	public final static int GET_KEY = 0;
	public static final int DEFAULT = 1;

	public native static String getJniKey();

	private JniMethods() {

	}

	public static JniMethods getInstance() {
		if (instance == null)
			instance = new JniMethods();
		return instance;
	}

	public String getValueKey() {
		return valueKey;
	}

	public void setValueKey(String valueKey) {
		this.valueKey = valueKey;
	}

	public String encrypt(String input,String valueKey){
		byte[] crypted = null;
		try {
			SecretKeySpec skey = new SecretKeySpec(valueKey.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, skey);
			crypted = cipher.doFinal(input.getBytes());
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		// return new String(Base64.decode(input, Base64.DEFAULT));
		return new String(RDCBase64.encodeBase64(crypted));
	}

	public String encrypt(String input) {
		return encrypt(input,valueKey);
	}

	// public String decrypt(String input, String key) {
	// byte[] output = null;
	// try {
	// SecretKeySpec skey;
	// Log.e("key", key);
	// skey = new SecretKeySpec(key.getBytes(), "AES");
	// Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
	// cipher.init(Cipher.DECRYPT_MODE, skey);
	// // org.apache.commons.codec.binary.Base64.decodeBase64(input)
	// output = cipher.doFinal(Base64.decodeBase64(input));
	// // output = cipher.doFinal(Base64.decode(input, Base64.DEFAULT));
	// } catch (Exception e) {
	// System.out.println(e.toString());
	// }
	// return new String(output);
	// }

	public String decrypt(String input, String key) {
		byte[] output = null;
		try {
			SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "AES");
			input = new String(RDCBase64.decodeBase64(input));
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, skey);
			if (SignInApp.DEBUG)
				Log.d("key", key);
			// output = cipher.doFinal(Base64.decode(input,Base64.URL_SAFE));
			output = cipher.doFinal(RDCBase64.decodeBase64(input));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new String(output);
	}

}
