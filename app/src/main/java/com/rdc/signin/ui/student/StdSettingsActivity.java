package com.rdc.signin.ui.student;

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
public class StdSettingsActivity extends SettingsActivity implements View.OnClickListener {
	private AppCompatCheckBox cbEmail;
	private AppCompatCheckBox cbAbsence;
	private SwitchCompat switchMsg;
	private TextView tvAbsenceLimit;
	private AppCompatCheckBox cbSound;

	@Override
	protected View createPreferenceView() {
		View v = LayoutInflater.from(this).inflate(R.layout.std_preference, null);
		cbEmail = UIUtils.findView(v, R.id.std_settings_cb_email);
		cbAbsence = UIUtils.findView(v, R.id.std_settings_cb_absence);
		switchMsg = UIUtils.findView(v, R.id.std_settings_switch_msg);
		tvAbsenceLimit = UIUtils.findView(v, R.id.std_settings_tv_absence_limit);
		cbSound = UIUtils.findView(v, R.id.std_settings_cb_sound);

		switchMsg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					setPreferenceEnabled((View) cbEmail.getParent(), true);
					setPreferenceEnabled((View) cbAbsence.getParent(), true);
					setPreferenceEnabled((View) tvAbsenceLimit.getParent(), cbAbsence.isChecked());
				} else {
					setPreferenceEnabled((View) cbEmail.getParent(), false);
					setPreferenceEnabled((View) cbAbsence.getParent(), false);
					setPreferenceEnabled((View) tvAbsenceLimit.getParent(), false);
				}
			}
		});
		cbAbsence.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				setPreferenceEnabled((View) tvAbsenceLimit.getParent(), isChecked);
			}
		});

		v.findViewById(R.id.std_settings_absence).setOnClickListener(this);
		v.findViewById(R.id.std_settings_absence_limit).setOnClickListener(this);
		v.findViewById(R.id.std_settings_about_dev).setOnClickListener(this);
		v.findViewById(R.id.std_settings_fallback).setOnClickListener(this);
		v.findViewById(R.id.std_settings_email).setOnClickListener(this);
		v.findViewById(R.id.std_settings_sound).setOnClickListener(this);
		v.findViewById(R.id.std_settings_contacts).setOnClickListener(this);
		return v;
	}

	@Override
	protected void onReadPreferences() {
		cbAbsence.setChecked(preferences.getBoolean(PreferenceKeys.STUDENT_ABSENCE_PROMPT, true));
		cbEmail.setChecked(preferences.getBoolean(PreferenceKeys.STUDENT_EMAIL_PROMPT, true));
		cbSound.setChecked(preferences.getBoolean(PreferenceKeys.STUDENT_SOUND_PROMPT, true));
		switchMsg.setChecked(preferences.getBoolean(PreferenceKeys.STUDENT_SWITCH_MESSAGE, true));

		tvAbsenceLimit.setText(preferences.getString(PreferenceKeys.STUDENT_ABSENCE_LIMIT, "3"));

		if (switchMsg.isChecked()) {
			setPreferenceEnabled((View) cbEmail.getParent(), true);
			setPreferenceEnabled((View) cbAbsence.getParent(), true);
			setPreferenceEnabled((View) tvAbsenceLimit.getParent(), cbAbsence.isChecked());
		} else {
			setPreferenceEnabled((View) cbEmail.getParent(), false);
			setPreferenceEnabled((View) cbAbsence.getParent(), false);
			setPreferenceEnabled((View) tvAbsenceLimit.getParent(), false);
		}
	}

	@Override
	protected void onSavePreferences() {
		SharedPreferences.Editor editor = preferences.edit();
		editor.putBoolean(PreferenceKeys.STUDENT_ABSENCE_PROMPT, cbAbsence.isChecked());
		editor.putBoolean(PreferenceKeys.STUDENT_SWITCH_MESSAGE, switchMsg.isChecked());
		editor.putString(PreferenceKeys.STUDENT_ABSENCE_LIMIT, tvAbsenceLimit.getText().toString());
		editor.putBoolean(PreferenceKeys.STUDENT_SOUND_PROMPT, cbSound.isChecked());
		editor.putBoolean(PreferenceKeys.STUDENT_EMAIL_PROMPT, cbEmail.isChecked());
		editor.commit();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.std_settings_about_dev:
				break;
			case R.id.std_settings_absence:
				cbAbsence.setChecked(!cbAbsence.isChecked());
				break;
			case R.id.std_settings_absence_limit:
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setTitle(R.string.absence_limit);
				builder.setItems(R.array.std_absence_times, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						String value = getResources().getStringArray(R.array.std_absence_times)[which];
						tvAbsenceLimit.setText(value);
					}
				});
				builder.setNegativeButton(R.string.cancel, null);
				builder.create().show();
				break;
			case R.id.std_settings_sound:
				cbSound.setChecked(!cbSound.isChecked());
				break;
			case R.id.std_settings_email:
				cbEmail.setChecked(!cbEmail.isChecked());
				break;
		}
	}
}
