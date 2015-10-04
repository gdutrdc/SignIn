package com.rdc.signin.ui.teacher;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.rdc.signin.R;
import com.rdc.signin.constant.PreferenceKeys;
import com.rdc.signin.ui.common.SettingsActivity;
import com.rdc.signin.utils.UIUtils;

/**
 * Created by seasonyuu on 15/10/2.
 */
public class TchSettingsActivity extends SettingsActivity implements View.OnClickListener {
	private AppCompatCheckBox cbEmail;
	private AppCompatCheckBox cbAbsence;
	private SwitchCompat switchMsg;
	private TextView tvAbsencePercent;
	private AppCompatCheckBox cbSound;

	@Override
	protected View createPreferenceView() {
		View view = LayoutInflater.from(this).inflate(R.layout.tch_preference, null);
		cbEmail = UIUtils.findView(view, R.id.tch_settings_cb_email);
		cbAbsence = UIUtils.findView(view, R.id.tch_settings_cb_absence);
		switchMsg = UIUtils.findView(view, R.id.tch_settings_switch_msg);
		tvAbsencePercent = UIUtils.findView(view, R.id.tch_settings_tv_absence_percent);
		cbSound = UIUtils.findView(view, R.id.tch_settings_cb_sound);

		switchMsg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					setPreferenceEnabled((View) cbEmail.getParent(), true);
					setPreferenceEnabled((View) cbAbsence.getParent(), true);
					setPreferenceEnabled((View) tvAbsencePercent.getParent(), cbAbsence.isChecked());
				} else {
					setPreferenceEnabled((View) cbEmail.getParent(), false);
					setPreferenceEnabled((View) cbAbsence.getParent(), false);
					setPreferenceEnabled((View) tvAbsencePercent.getParent(), false);
				}
			}
		});
		cbAbsence.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				setPreferenceEnabled((View) tvAbsencePercent.getParent(), isChecked);
			}
		});

		view.findViewById(R.id.tch_settings_email).setOnClickListener(this);
		view.findViewById(R.id.tch_settings_sound).setOnClickListener(this);
		view.findViewById(R.id.tch_settings_about_dev).setOnClickListener(this);
		view.findViewById(R.id.tch_settings_fallback).setOnClickListener(this);
		view.findViewById(R.id.tch_settings_absence).setOnClickListener(this);
		view.findViewById(R.id.tch_settings_absence_percent).setOnClickListener(this);
		view.findViewById(R.id.tch_settings_contacts).setOnClickListener(this);
		return view;
	}

	@Override
	protected void onReadPreferences() {
		cbAbsence.setChecked(preferences.getBoolean(PreferenceKeys.TEACHER_ABSENCE_PROMPT, true));
		cbEmail.setChecked(preferences.getBoolean(PreferenceKeys.TEACHER_EMAIL_PROMPT, true));
		cbSound.setChecked(preferences.getBoolean(PreferenceKeys.TEACHER_SOUND_PROMPT, true));
		switchMsg.setChecked(preferences.getBoolean(PreferenceKeys.TEACHER_SWITCH_MESSAGE, true));

		tvAbsencePercent.setText(preferences.getString(PreferenceKeys.TEACHER_ABSENCE_PERCENT, "30%"));

		if (switchMsg.isChecked()) {
			setPreferenceEnabled((View) cbEmail.getParent(), true);
			setPreferenceEnabled((View) cbAbsence.getParent(), true);
			setPreferenceEnabled((View) tvAbsencePercent.getParent(), cbAbsence.isChecked());
		} else {
			setPreferenceEnabled((View) cbEmail.getParent(), false);
			setPreferenceEnabled((View) cbAbsence.getParent(), false);
			setPreferenceEnabled((View) tvAbsencePercent.getParent(), false);
		}
	}

	@Override
	protected void onSavePreferences() {
		SharedPreferences.Editor editor = preferences.edit();
		editor.putBoolean(PreferenceKeys.TEACHER_ABSENCE_PROMPT, cbAbsence.isChecked());
		editor.putBoolean(PreferenceKeys.TEACHER_SWITCH_MESSAGE, switchMsg.isChecked());
		editor.putString(PreferenceKeys.TEACHER_ABSENCE_PERCENT, tvAbsencePercent.getText().toString());
		editor.putBoolean(PreferenceKeys.TEACHER_SOUND_PROMPT, cbSound.isChecked());
		editor.putBoolean(PreferenceKeys.TEACHER_EMAIL_PROMPT, cbEmail.isChecked());
		editor.commit();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.tch_settings_about_dev:
				break;
			case R.id.tch_settings_absence:
				cbAbsence.setChecked(!cbAbsence.isChecked());
				break;
			case R.id.tch_settings_absence_percent :
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setTitle(R.string.absence_percent);
				builder.setItems(R.array.tch_absence_percent, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						String value = getResources().getStringArray(R.array.tch_absence_percent)[which];
						tvAbsencePercent.setText(value);
					}
				});
				builder.setNegativeButton(R.string.cancel, null);
				builder.create().show();
				break;
			case R.id.tch_settings_sound:
				cbSound.setChecked(!cbSound.isChecked());
				break;
			case R.id.tch_settings_email:
				cbEmail.setChecked(!cbEmail.isChecked());
				break;
		}
	}
}
