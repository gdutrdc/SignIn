package com.rdc.signin.ui.student;

import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.rdc.signin.R;
import com.rdc.signin.constant.StdClass;
import com.rdc.signin.ui.common.AbsClassActivity;
import com.rdc.signin.ui.widget.FloatingActionButton;
import com.rdc.signin.utils.UIUtils;

/**
 * Created by seasonyuu on 15/9/27.
 */
public class StdClassActivity extends AbsClassActivity {
	private StdClass mClass;

	private TextView tvClassHour;
	private TextView tvClassAbout;
	private TextView tvClassLoc;
	private TextView tvClassTime;
	private TextView tvTeacherName;
	private TextView tvClassSum;
	private TextView tvSignTimes;
	private TextView tvRest;
	private TextView tvAbsence;

	@Override
	protected void onFABClick(FloatingActionButton fab) {
		Intent intent = new Intent(this, StdSignInActivity.class);
		intent.putExtra("mac", mClass.getMac());
		intent.putExtra("id",mClass.getClassId());
		startActivity(intent);
	}

	@Override
	protected View createContentView() {
		View view = LayoutInflater.from(this).inflate(R.layout.class_info_std, null);
		tvAbsence = UIUtils.findView(view, R.id.std_class_absence);
		tvClassAbout = UIUtils.findView(view, R.id.std_class_about);
		tvClassHour = UIUtils.findView(view, R.id.std_class_hour);
		tvClassLoc = UIUtils.findView(view, R.id.std_class_loc);
		tvTeacherName = UIUtils.findView(view, R.id.std_class_teacher);
		tvClassTime = UIUtils.findView(view, R.id.std_class_time);
		tvRest = UIUtils.findView(view, R.id.std_class_rest);
		tvSignTimes = UIUtils.findView(view, R.id.std_class_signtimes);
		tvClassSum = UIUtils.findView(view, R.id.std_class_sum);
		return view;
	}

	@Override
	protected void handlerClassInfo(Parcelable clazz) {
		mClass = (StdClass) clazz;
		setToolbarTitle(mClass.getClassName());
		tvClassSum.setText(mClass.getSum());
		tvRest.setText(mClass.getRest());
		tvTeacherName.setText(mClass.getTeacherName());
		tvClassTime.setText(mClass.getTime());
		tvSignTimes.setText(mClass.getSignTimes());
		tvClassLoc.setText(mClass.getLoc());
		tvClassHour.setText(mClass.getHour());
		tvClassAbout.setText(mClass.getAbout());
		tvAbsence.setText(Integer.parseInt(mClass.getSum()) - Integer.parseInt(mClass.getSignTimes()) + "");
	}
}
