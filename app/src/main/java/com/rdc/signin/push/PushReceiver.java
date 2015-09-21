package com.rdc.signin.push;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.media.RingtoneManager;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.baidu.android.pushservice.PushMessageReceiver;
import com.rdc.signin.R;
import com.rdc.signin.app.SignInApp;
import com.rdc.signin.constant.User;
import com.rdc.signin.ui.student.StdMainActivity;
import com.rdc.signin.ui.teacher.TchMainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by seasonyuu on 15/9/20.
 */
public class PushReceiver extends PushMessageReceiver {
	public static final String TAG = PushReceiver.class.getSimpleName();
	private PendingIntent intent;

	@Override
	public void onBind(Context context, int errorCode, String appid,
	                   String userId, String channelId, String requestId) {
		String responseString = "onBind errorCode=" + errorCode + " appid="
				+ appid + " userId=" + userId + " channelId=" + channelId
				+ " requestId=" + requestId;
		if (SignInApp.DEBUG)
			Log.d(TAG, responseString);
		SignInApp.getInstance().setChannelId(channelId);
	}

	@Override
	public void onUnbind(Context context, int i, String s) {

	}

	@Override
	public void onSetTags(Context context, int i, List<String> list, List<String> list1, String s) {

	}

	@Override
	public void onDelTags(Context context, int i, List<String> list, List<String> list1, String s) {

	}

	@Override
	public void onListTags(Context context, int i, List<String> list, String s) {

	}

	@Override
	public void onMessage(Context context, String message, String s1) {
		try {
			JSONObject json = new JSONObject(message);
			String title = json.getString("title");
			String description = json.getString("description");
			int type = json.getInt("type");
			NotificationManager nm = (NotificationManager) context
					.getSystemService(Context.NOTIFICATION_SERVICE);
			// TODO 区分开type
			// TODO 判断应用是否已经打开 如果已经打开 跳转到指定的界面，如果未打开，启动登录界面
			int identify = SignInApp.getInstance().getRememberUser().getIdentity();

			Intent open = new Intent();
			if (identify == User.IDENTITY_STUDENT)
				open.setClass(context, StdMainActivity.class);
			else
				open.setClass(context, TchMainActivity.class);
			open.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent = PendingIntent.getActivity(context, 0, open, 0);

			Notification notification = new NotificationCompat.Builder(context)
					.setSmallIcon(R.drawable.small_icon)
					.setLargeIcon(
							((BitmapDrawable) context.getResources()
									.getDrawable(R.mipmap.ic_launcher))
									.getBitmap())
					.setContentText(description)
					.setAutoCancel(true)
					.setContentTitle(title)
					.setSound(
							RingtoneManager.getActualDefaultRingtoneUri(
									context, RingtoneManager.TYPE_NOTIFICATION))
					.setContentIntent(intent).build();
			nm.notify(0, notification);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onNotificationClicked(Context context, String s, String s1, String s2) {

	}

	@Override
	public void onNotificationArrived(Context context, String s, String s1, String s2) {

	}
}
