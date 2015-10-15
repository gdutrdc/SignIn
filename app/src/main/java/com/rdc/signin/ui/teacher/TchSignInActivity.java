package com.rdc.signin.ui.teacher;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;
import android.widget.Toast;

import com.rdc.signin.R;
import com.rdc.signin.constant.TchClass;
import com.rdc.signin.ui.common.ToolbarActivity;
import com.rdc.signin.ui.widget.FloatingActionButton;
import com.rdc.signin.ui.widget.HotspotView;
import com.rdc.signin.utils.DialogUtils;
import com.rdc.signin.utils.UIUtils;
import com.rdc.signin.utils.WifiController;

/**
 * Created by seasonyuu on 15/10/12.
 */
public class TchSignInActivity extends ToolbarActivity implements View.OnClickListener {
	private HotspotView mHotspotView;
	private TextView mTvSignInTips;
	private FloatingActionButton mFAB;

	private AnimationSet mTipsAnimation;

	private static final int CHECK_AP_STATE = 0;
	private static final int CHANGE_AP_STATE_SUCCEED = 1;
	private static final int CHECK_COMMIT_STATE = 2;
	private static final int RESTORE_WIFI_STATE = 3;

	private WifiController mWifiController;

	private boolean isCommited;
	private boolean targetApState;

	private TchClass mClass;

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			if (msg.what == CHECK_AP_STATE) {
				if (mWifiController.isWifiApEnabled() != targetApState) {
					handler.sendEmptyMessageDelayed(CHECK_AP_STATE, 200);
				} else {
					DialogUtils.dismissAllDialog();
					handler.sendEmptyMessage(CHANGE_AP_STATE_SUCCEED);
				}
			} else if (msg.what == CHANGE_AP_STATE_SUCCEED) {
				if (targetApState) {
					mHotspotView.setAnimating(true);
					if (mTipsAnimation == null) {
						mTipsAnimation = new AnimationSet(true);

						ScaleAnimation scaleAnimation = new ScaleAnimation(1, 1.5f, 1, 1.5f,
								mTvSignInTips.getWidth() / 2, mTvSignInTips.getHeight() / 2);
						scaleAnimation.setRepeatCount(-1);
						mTipsAnimation.addAnimation(scaleAnimation);
						AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0.5f);
						alphaAnimation.setRepeatCount(-1);
						mTipsAnimation.addAnimation(alphaAnimation);

						mTipsAnimation.setDuration(1500);
						mTipsAnimation.setRepeatMode(Animation.REVERSE);
					}
				}
				mTvSignInTips.setVisibility(View.VISIBLE);
				mTvSignInTips.startAnimation(mTipsAnimation);
				mFAB.setImageDrawable(getResources().
						getDrawable(R.drawable.ic_portable_wifi_off_white_24dp));
			} else if (msg.what == CHECK_COMMIT_STATE) {
				if (isCommited) {
					DialogUtils.dismissAllDialog();
					new AlertDialog.Builder(TchSignInActivity.this)
							.setMessage("提交成功")
							.setPositiveButton("确定并退出",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											finish();

										}
									}).show();
				} else
					handler.sendEmptyMessageDelayed(CHECK_COMMIT_STATE, 200);
			} else if (msg.what == RESTORE_WIFI_STATE) {
				if (mWifiController.getWifiBack()) {
					if (!mWifiController.isWifiEnabled())
						handler.sendEmptyMessageDelayed(RESTORE_WIFI_STATE, 200);
					else {
						DialogUtils.dismissAllDialog();
						Toast.makeText(TchSignInActivity.this, "Wifi已恢复",
								Toast.LENGTH_SHORT).show();
						mHotspotView.setAnimating(false);
						mTvSignInTips.setVisibility(View.GONE);
						mTvSignInTips.clearAnimation();
					}
					mFAB.setImageDrawable(getResources().
							getDrawable(R.drawable.ic_wifi_tethering_white_24dp));
				}
			}
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tch_sign_in);

		mClass = getIntent().getParcelableExtra("class");

		mHotspotView = UIUtils.findView(this, R.id.tch_sign_in_hotspot);
		mTvSignInTips = UIUtils.findView(this, R.id.tch_sign_in_tips);
		mFAB = UIUtils.findView(this, R.id.tch_sign_in_fab);

		mFAB.setOnClickListener(this);

		mWifiController = new WifiController(this);
		if (mWifiController.isWifiApEnabled()) {
			mFAB.setImageDrawable(getResources()
					.getDrawable(R.drawable.ic_portable_wifi_off_white_24dp));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_tch_sign_in, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.tch_sign_in_by_barcode:
				Intent intent = new Intent(this, TchScanActivity.class);
				intent.putExtra("classID", mClass.getId());
				startActivity(intent);
				break;
			case R.id.tch_sign_in_had_sign_in_list:
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.tch_sign_in_fab:
				targetApState = !mWifiController.isWifiApEnabled();
				mWifiController.setWifiApEnabled(targetApState);
				if (targetApState) {
					handler.sendEmptyMessage(CHECK_AP_STATE);
					DialogUtils.showProgressDialog(this, "请稍候");
				} else {
					handler.sendEmptyMessage(RESTORE_WIFI_STATE);
					DialogUtils.showProgressDialog(TchSignInActivity.this, "正在重新开启wifi...");
				}
				break;
		}
	}
}
