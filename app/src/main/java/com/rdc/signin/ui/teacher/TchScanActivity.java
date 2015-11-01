package com.rdc.signin.ui.teacher;

import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.Toast;

import com.google.zxing.AmbientLightManager;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BeepManager;
import com.google.zxing.DecodeHintType;
import com.google.zxing.Result;
import com.google.zxing.camera.CameraManager;
import com.google.zxing.decode.CaptureActivityHandler;
import com.google.zxing.decode.FinishListener;
import com.google.zxing.decode.InactivityTimer;
import com.google.zxing.widget.ViewfinderView;
import com.rdc.signin.R;
import com.rdc.signin.constant.Student;
import com.rdc.signin.database.TchRegSQLiteHelper;
import com.rdc.signin.net.control.ConnectListener;
import com.rdc.signin.net.teacher.DoSignAction;
import com.rdc.signin.ui.adapter.TchScanListAdapter;
import com.rdc.signin.utils.DialogUtils;
import com.rdc.signin.utils.QRCodeUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

public final class TchScanActivity extends AppCompatActivity implements
		SurfaceHolder.Callback {
	private boolean isTorchOn = false;
	private CameraManager cameraManager;
	private Toolbar mToolbar;
	private CaptureActivityHandler handler;
	private Result savedResultToShow;
	private ViewfinderView viewfinderView;
	private boolean hasSurface;
	private ListView mLvHadIn;
	private Collection<BarcodeFormat> decodeFormats;
	private Map<DecodeHintType, ?> decodeHints;
	private String characterSet;
	private InactivityTimer inactivityTimer;
	private BeepManager beepManager;
	private AmbientLightManager ambientLightManager;
	private TchScanListAdapter mListAdapter;
	private String mClassId;
	private SQLiteDatabase mDatabase;
	private TchRegSQLiteHelper mHelper;
	private String mDate = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(new Date());

	public ViewfinderView getViewfinderView() {
		return viewfinderView;
	}

	public Handler getHandler() {
		return handler;
	}

	public CameraManager getCameraManager() {
		return cameraManager;
	}

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);

		setTheme(android.support.v7.appcompat.R.style.Theme_AppCompat_NoActionBar);
		Window window = getWindow();
		window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.activity_tch_scan);

		mClassId = getIntent().getStringExtra("classId");

		findAllViewById();

		setupActionBar();

		hasSurface = false;
		inactivityTimer = new InactivityTimer(this);
		beepManager = new BeepManager(this);
		ambientLightManager = new AmbientLightManager(this);

		mHelper = new TchRegSQLiteHelper(this);
		mListAdapter = new TchScanListAdapter(this);
		mListAdapter.setDataList(mHelper.getDataFromLocal(mClassId, mDate));

		mLvHadIn.setAdapter(mListAdapter);
	}

	private void findAllViewById() {
		viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
		mLvHadIn = (ListView) findViewById(R.id.lv_had_in_tch_scan);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_tch_scan, menu);
		return super.onCreateOptionsMenu(menu);
	}

	private void setupActionBar() {
		mToolbar = (Toolbar) findViewById(R.id.tch_scan_toolbar);
		mToolbar.setTitle("开始签到");
		mToolbar.setBackgroundColor(Color.parseColor("#00000000"));
		setSupportActionBar(mToolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			safeFinish();
		} else if (item.getItemId() == R.id.mi_light_tch_check_message) {
			if (isTorchOn) {
				isTorchOn = false;
				cameraManager.setTorch(false);
				item.setIcon(R.drawable.ic_flash_off_white_24dp);
			} else {
				isTorchOn = true;
				cameraManager.setTorch(true);
				item.setIcon(R.drawable.ic_flash_on_white_24dp);
			}
		} else if (item.getItemId() == R.id.mi_tch_sign_in_commit_qrcode) {
			DialogUtils.showProgressDialog(this, "提交中");
			doSign();
		}
		return super.onOptionsItemSelected(item);
	}

	private void doSign() {
		String[] students = new String[mListAdapter.getCount()];
		String[] times = new String[mListAdapter.getCount()];
		ArrayList<Student> list = mListAdapter.getDataList();
		for (int i = 0; i < list.size(); i++) {
			students[i] = list.get(i).getAccount();
			times[i] = list.get(i).getSignInTime();
		}
		new DoSignAction(mClassId, students, times, new ConnectListener() {
			@Override
			public void onConnect(boolean isConnect, String reason, String response) {
				if (isConnect) {
					DialogUtils.showTipsDialog(TchScanActivity.this, "提交成功");
					mHelper.deleteData(mClassId, mDate);
					mListAdapter.setDataList(mHelper.getDataFromLocal(mClassId, mDate));
					mListAdapter.notifyDataSetChanged();
				} else {
					DialogUtils.showWaringDialog(TchScanActivity.this, reason);
				}
			}
		}).connect();
	}

	private void safeFinish() {
		new AlertDialog.Builder(this).setMessage("确定退出扫描？(本次扫描的结果已保存到本地)")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						finish();
					}
				}).setNegativeButton("取消", null).show();

	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onResume() {
		super.onResume();

		cameraManager = new CameraManager(getApplication());

		viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
		viewfinderView.setCameraManager(cameraManager);

		handler = null;
		resetStatusView();

		SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
		SurfaceHolder surfaceHolder = surfaceView.getHolder();
		if (hasSurface) {
			initCamera(surfaceHolder);
		} else {
			surfaceHolder.addCallback(this);
			surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		}

		beepManager.updatePrefs();
		ambientLightManager.start(cameraManager);

		inactivityTimer.onResume();

		decodeFormats = null;
		characterSet = null;
	}

	@Override
	protected void onPause() {
		if (handler != null) {
			handler.quitSynchronously();
			handler = null;
		}
		inactivityTimer.onPause();
		ambientLightManager.stop();
		cameraManager.closeDriver();
		if (!hasSurface) {
			SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
			SurfaceHolder surfaceHolder = surfaceView.getHolder();
			surfaceHolder.removeCallback(this);
		}
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		inactivityTimer.shutdown();
		viewfinderView.recycleLineDrawable();
		super.onDestroy();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
			case KeyEvent.KEYCODE_CAMERA:// 禁用相机拍照按键（如果有）
				return true;
			case KeyEvent.KEYCODE_BACK:
				safeFinish();
				return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void decodeOrStoreSavedBitmap(Bitmap bitmap, Result result) {
		if (handler == null) {
			savedResultToShow = result;
		} else {
			if (result != null) {
				savedResultToShow = result;
			}
			if (savedResultToShow != null) {
				Message message = Message.obtain(handler,
						R.id.decode_succeeded, savedResultToShow);
				handler.sendMessage(message);
			}
			savedResultToShow = null;
		}
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if (!hasSurface) {
			hasSurface = true;
			initCamera(holder);
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		hasSurface = false;
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
	                           int height) {

	}

	/**
	 * 处理扫描到的结果
	 */
	public void handleDecode(final Result rawResult, Bitmap barcode,
	                         float scaleFactor) {
		inactivityTimer.onActivity();
		beepManager.playBeepSoundAndVibrate();

		inactivityTimer.onActivity();

		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle("签到学生");
		final Student student = new Student();
		try {
			String s = QRCodeUtils.readQRCode(rawResult.getText());
			String[] datas = s.split("/", 2);
			for (String data : datas)
				Log.e("Tag", s + " - " + data);
			student.setAccount(datas[0]);
			student.setName(datas[1]);
			student.setSignInTime(new SimpleDateFormat("HH:mm:ss", Locale.CHINA).format(new Date()));
		} catch (Exception e) {
			e.printStackTrace();
			displayDialog("这不是一个学生的二维码！");
			return;
		}

		dialog.setMessage(student.getName() + "\n" + student.getAccount() + "\n签到时间:" + student.getSignInTime());
		dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

				handler.sendEmptyMessage(R.id.restart_preview);
			}
		});
		dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				addData(student);
				handler.sendEmptyMessage(R.id.restart_preview);
			}
		});
		dialog.create().show();
	}

	private void initCamera(SurfaceHolder surfaceHolder) {
		if (surfaceHolder == null) {
			return;
		}
		if (cameraManager.isOpen()) {
			return;
		}
		try {
			cameraManager.openDriver(surfaceHolder);
			if (handler == null) {
				handler = new CaptureActivityHandler(this, decodeFormats,
						decodeHints, characterSet, cameraManager);
			}
			decodeOrStoreSavedBitmap(null, null);
		} catch (IOException ioe) {
			displayFrameworkBugMessageAndExit();
		} catch (RuntimeException e) {
			displayFrameworkBugMessageAndExit();
		}
	}

	private void displayFrameworkBugMessageAndExit() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("警告");
		builder.setMessage("抱歉，相机出现问题，您可能需要重启设备");
		builder.setPositiveButton("确定", new FinishListener(this));
		builder.setOnCancelListener(new FinishListener(this));
		builder.show();
	}

	public void restartPreviewAfterDelay(long delayMS) {
		if (handler != null) {
			handler.sendEmptyMessageDelayed(R.id.restart_preview, delayMS);
		}
		resetStatusView();
	}

	private void resetStatusView() {
		viewfinderView.setVisibility(View.VISIBLE);
	}

	public void drawViewfinder() {
		viewfinderView.drawViewfinder();
	}

	public void makeSucceedToast() {
		Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
	}

	public void displayDialog(String message) {
		new AlertDialog.Builder(this).setMessage(message)
				.setNegativeButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						restartPreviewAfterDelay(1000);

					}
				}).show();

	}

	public void addData(Student student) {
		for (Student s : mListAdapter.getDataList()) {
			if (s.getAccount().equals(student.getAccount())) {
				Toast.makeText(this, "该学生的二维码已扫描过", Toast.LENGTH_SHORT).show();
				return;
			}
		}
		addItem(student);
	}

	private void addItem(final Student student) {
		ArrayList<Student> list = mListAdapter.getDataList();
		list.add(student);
		mListAdapter.setDataList(list);
		mListAdapter.notifyDataSetChanged();

		if (mHelper == null) {
			mHelper = new TchRegSQLiteHelper(this);
		}
		try {
			mHelper.insertData(new String[]{mClassId, mDate,
					student.getAccount(), student.getName(),
					student.getSignInTime()});
			makeSucceedToast();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
