package com.rdc.signin.ui.student;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.rdc.signin.R;
import com.rdc.signin.app.SignInApp;
import com.rdc.signin.net.control.ConnectListener;
import com.rdc.signin.net.student.SignIn;
import com.rdc.signin.ui.common.ToolbarActivity;
import com.rdc.signin.ui.widget.ScanView;
import com.rdc.signin.utils.DialogUtils;
import com.rdc.signin.utils.JniMethods;
import com.rdc.signin.utils.UIUtils;
import com.rdc.signin.utils.WifiController;

/**
 * Created by seasonyuu on 15/10/12.
 */
public class StdSignInActivity extends ToolbarActivity implements View.OnClickListener {
	private static final int CHECK_WIFI_STATE = 0;
	private static final int START_ANIMATION = 1;
	private static final int STOP_ANIMATION = 2;
	private static final int SEARCH_TEACHER = 3;
	private static final int FOUND_TEACHER = 4;
	private static final int SEARCH_TIME_OUT = 5;
	private static final int DEFAULT_TIME_OUT = 5 * 1000;

	private ScanView mScanView;

	private String teacherMac;
	private String classId;

	private Button mBtnStartSignIn;

	private int markNum = 0;
	private boolean isSearching = false;

	private WifiController mWifiController;

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case START_ANIMATION:
					mScanView.setAnimating(true);
					break;
				case CHECK_WIFI_STATE:
					if (mWifiController.isWifiEnabled()) {
						DialogUtils.dismissAllDialog();
						startSignIn();
					} else {
						sendEmptyMessageDelayed(CHECK_WIFI_STATE, 200);
					}
					break;
				case SEARCH_TEACHER:
					if (!isSearching)
						break;
					if (!mScanView.isAnimating()) {
						sendEmptyMessage(START_ANIMATION);
					}
					if (mWifiController.searchMacInScanResult(teacherMac)) {
						markNum++;
						if (markNum < 2)
							sendEmptyMessageDelayed(SEARCH_TEACHER, 500);
						else {
							Toast.makeText(StdSignInActivity.this, "签到成功", Toast.LENGTH_SHORT).show();
							sendEmptyMessage(FOUND_TEACHER);
							stopSignIn();
						}
					} else {
						sendEmptyMessageDelayed(SEARCH_TEACHER, 500);
					}
					break;
				case FOUND_TEACHER:
					DialogUtils.showProgressDialog(StdSignInActivity.this, "正在发送数据");
					doSignIn();
					break;
				case SEARCH_TIME_OUT:
					if (markNum < 2) {
						stopSignIn();
						new AlertDialog.Builder(StdSignInActivity.this)
								.setTitle("提示")
								.setMessage("搜索不到老师的信号，请确定Wifi可用。否则点击右上角菜单请进行二维码签到")
								.setPositiveButton("确定", null)
								.setNegativeButton("重试", new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog, int which) {
										startSignIn();
									}
								}).show();
					}
					break;
				case STOP_ANIMATION:
					mScanView.setAnimating(false);
					break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_std_sign_in);

		Intent intent = getIntent();
		teacherMac = intent.getStringExtra("mac");
		classId = intent.getStringExtra("id");

		JniMethods jniMethods = JniMethods.getInstance();
		teacherMac = jniMethods.decrypt(teacherMac, jniMethods.getValueKey());

		mScanView = UIUtils.findView(this, R.id.std_sign_in_scan);
		mBtnStartSignIn = UIUtils.findView(this, R.id.std_sign_in_start);
		mBtnStartSignIn.setOnClickListener(this);

		mWifiController = new WifiController(this);

		if (!mWifiController.isWifiEnabled()) {
			DialogUtils.showProgressDialog(this, "正在开启Wifi");
			mWifiController.setWifiEnabled(true);
			handler.sendEmptyMessage(CHECK_WIFI_STATE);
		} else {
			startSignIn();
		}

	}

	private void doSignIn() {
		new SignIn(classId, SignInApp.user.getAccount(), teacherMac, new ConnectListener() {
			@Override
			public void onConnect(boolean isConnect, String reason, String response) {
				if (isConnect) {
					stopSignIn();
					Toast.makeText(StdSignInActivity.this, "签到成功", Toast.LENGTH_SHORT).show();
				} else {
					new AlertDialog.Builder(StdSignInActivity.this)
							.setTitle("警告")
							.setMessage("上传数据失败" + "\n\n" + reason)
							.setNegativeButton("确定", null)
							.setPositiveButton("重试", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
									handler.sendEmptyMessage(FOUND_TEACHER);
								}
							}).show();
					stopSignIn();
				}
			}
		}).connect();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_std_sign_in, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.std_sign_in_barcode:
				//TODO 启动二维码界面
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.std_sign_in_start:
				startSignIn();
				break;
		}
	}

	private void startSignIn() {
		mBtnStartSignIn.setEnabled(false);
		mBtnStartSignIn.setText("正在签到");
		markNum = 0;
		isSearching = true;
		handler.sendEmptyMessageDelayed(SEARCH_TIME_OUT, DEFAULT_TIME_OUT);
		handler.sendEmptyMessage(SEARCH_TEACHER);
	}

	private void stopSignIn() {
		mBtnStartSignIn.setEnabled(true);
		mBtnStartSignIn.setText("开始签到");
		isSearching = false;
		handler.sendEmptyMessage(STOP_ANIMATION);
	}
}
