package com.rdc.signin.ui.student;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.rdc.signin.R;
import com.rdc.signin.constant.StdClass;
import com.rdc.signin.net.control.ConnectListener;
import com.rdc.signin.net.student.AskForLeaveAction;
import com.rdc.signin.ui.common.ToolbarActivity;
import com.rdc.signin.utils.DialogUtils;
import com.rdc.signin.utils.UIUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by seasonyuu on 15/11/1.
 */
public class StdAskForLeaveActivity extends ToolbarActivity implements View.OnClickListener {
	private StdClass mClass;
	private TextView mDatePicker;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ask_for_leave);

		mClass = getIntent().getParcelableExtra("class");

		setTitle(mClass.getClassName() + " - 请假");

		mDatePicker = UIUtils.findView(this, R.id.std_ask_for_leave_date);
		mDatePicker.setOnClickListener(this);
		mDatePicker.setText(new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(new Date()));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_std_ask_for_leave, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.std_ask_for_leave_send:
				DialogUtils.showProgressDialog(this, R.string.sending);
				askForLeave();
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void askForLeave() {
		new AskForLeaveAction(mClass.getClassId(), new ConnectListener() {
			@Override
			public void onConnect(boolean isConnect, String reason, String response) {
				if (isConnect) {
					DialogUtils.showTipsDialog(StdAskForLeaveActivity.this, "发送成功", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							finish();
						}
					});
				} else {
					DialogUtils.showWaringDialog(StdAskForLeaveActivity.this, reason);
				}
			}
		}).connect();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.std_ask_for_leave_date:
				Calendar calendar = Calendar.getInstance();
				DatePickerDialog dialog = new DatePickerDialog(this, R.style.AppTheme, new DatePickerDialog.OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
						mDatePicker.setText(String.format("%4d-%2d-%2d", year, monthOfYear + 1, dayOfMonth));
					}
				}, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
				dialog.show();
		}
	}
}
