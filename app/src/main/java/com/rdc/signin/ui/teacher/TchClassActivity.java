package com.rdc.signin.ui.teacher;

import android.content.Intent;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.rdc.signin.R;
import com.rdc.signin.constant.TchClass;
import com.rdc.signin.ui.common.AbsClassActivity;
import com.rdc.signin.utils.UIUtils;

/**
 * Created by seasonyuu on 15/9/28.
 */
public class TchClassActivity extends AbsClassActivity {
	private TextView tvClassHour;
	private TextView tvClassAbout;
	private TextView tvClassLoc;
	private TextView tvClassTime;

	private TchClass mClass;

	@Override
	protected void onFABClick(FloatingActionButton fab) {
		Intent intent = new Intent(this, TchSignInActivity.class);
		intent.putExtra("class", mClass);
		startActivity(intent);
	}

	@Override
	protected View createContentView() {
		View view = LayoutInflater.from(this).inflate(R.layout.class_info_tch, null);
		tvClassAbout = UIUtils.findView(view, R.id.tch_class_about);
		tvClassHour = UIUtils.findView(view, R.id.tch_class_hour);
		tvClassLoc = UIUtils.findView(view, R.id.tch_class_loc);
		tvClassTime = UIUtils.findView(view, R.id.tch_class_time);
		return view;
	}

	@Override
	protected void handlerClassInfo(Parcelable clazz) {
		mClass = (TchClass) clazz;
		setToolbarTitle(mClass.getName());
		tvClassLoc.setText(mClass.getLoc());
		tvClassTime.setText(mClass.getTime());
		tvClassHour.setText(mClass.getHour());
		if (mClass.getAbout().length() > 0) {
			tvClassAbout.setText(mClass.getAbout());
		} else {
			tvClassAbout.setText("(无)");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_tch_class, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.tch_class_student_list:
				Intent studentList = new Intent(this, TchStudentListActivity.class);
				studentList.putExtra("classId", mClass.getId());
				studentList.putExtra("type",TchStudentListActivity.TYPE_ALL);
				startActivity(studentList);
				break;
		}
		return super.onOptionsItemSelected(item);
	}
}
