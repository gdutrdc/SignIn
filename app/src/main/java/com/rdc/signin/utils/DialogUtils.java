package com.rdc.signin.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * Created by seasonyuu on 15/9/19.
 */
public class DialogUtils {
	private static ProgressDialog progressDialog;
	private static AlertDialog alertDialog;

	public static void showProgressDialog(Context context, String message) {
		if (progressDialog == null || progressDialog.getContext() != context) {
			progressDialog = new ProgressDialog(context);
		}
		dismissAllDialog();
		progressDialog.setMessage(message);
		progressDialog.show();
	}

	public static void dismissAllDialog() {
		if (progressDialog != null && progressDialog.isShowing())
			progressDialog.dismiss();
		if (alertDialog != null && alertDialog.isShowing())
			alertDialog.dismiss();
	}

	public static void showWaringDialog(Context context, String message, DialogInterface.OnClickListener onClickListener) {
		if (alertDialog == null || alertDialog.getContext() != context) {
			alertDialog = new AlertDialog.Builder(context)
					.create();
		}
		dismissAllDialog();
		alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", onClickListener);
		alertDialog.setTitle("警告");
		alertDialog.setMessage(message);
		alertDialog.show();
	}

	public static void showWaringDialog(Context context, String message) {
		showWaringDialog(context, message, null);
	}

	public static void showTipsDialog(Context context, String message, DialogInterface.OnClickListener onClickListener) {
		if (alertDialog == null || alertDialog.getContext() != context) {
			alertDialog = new AlertDialog.Builder(context)
					.create();
		}
		dismissAllDialog();
		alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", onClickListener);
		alertDialog.setTitle("提示");
		alertDialog.setMessage(message);
		alertDialog.show();
	}

	public static void showTipsDialog(Context context, String message) {
		showTipsDialog(context, message, null);
	}
}
