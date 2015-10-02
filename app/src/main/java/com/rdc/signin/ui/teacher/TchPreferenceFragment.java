package com.rdc.signin.ui.teacher;


import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;

import com.rdc.signin.R;

public class TchPreferenceFragment extends PreferenceFragment {
	private CheckBoxPreference mCbpMsgSwitch;
	private CheckBoxPreference mCbpEmailPrompt;
	private CheckBoxPreference mCbpAbsencePrompt;
	private ListPreference mLpAbsencePercent;
	private CheckBoxPreference mCbpSoundPrompt;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.tch_preference);
		getSharePrefrence();

		mLpAbsencePercent
				.setOnPreferenceChangeListener(new ListPreference.OnPreferenceChangeListener() {

					@Override
					public boolean onPreferenceChange(Preference preference,
					                                  Object newValue) {
						mLpAbsencePercent.setValue(newValue.toString());
						return false;
					}
				});
	}

	private void getSharePrefrence() {
		findAllPreferences();

		// TODO 读取SharePreferenced信息
		if (!mCbpMsgSwitch.isChecked()) {
			mCbpEmailPrompt.setEnabled(mCbpMsgSwitch.isChecked());
			mCbpAbsencePrompt.setEnabled(mCbpMsgSwitch.isChecked());
			mCbpSoundPrompt.setEnabled(mCbpMsgSwitch.isChecked());
			mLpAbsencePercent.setEnabled(mCbpMsgSwitch.isChecked());
		} else
			mLpAbsencePercent.setEnabled(mCbpAbsencePrompt.isChecked());
	}

	private void findAllPreferences() {
		mCbpMsgSwitch = (CheckBoxPreference) findPreference("tch_message_switch");
		mCbpEmailPrompt = (CheckBoxPreference) findPreference("tch_email_prompt");
		mCbpAbsencePrompt = (CheckBoxPreference) findPreference("tch_absence_prompt");
		mCbpSoundPrompt = (CheckBoxPreference) findPreference("tch_sound_prompt");
		mLpAbsencePercent = (ListPreference) findPreference("tch_absence_percent");
	}

	@Override
	public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen,
	                                     Preference preference) {
		switch (preference.getKey()) {
			case "tch_message_switch":
				mCbpEmailPrompt.setEnabled(mCbpMsgSwitch.isChecked());
				mCbpAbsencePrompt.setEnabled(mCbpMsgSwitch.isChecked());
				mCbpSoundPrompt.setEnabled(mCbpMsgSwitch.isChecked());
				mLpAbsencePercent
						.setEnabled(mCbpMsgSwitch.isChecked() ? mCbpAbsencePrompt
								.isChecked() : false);
				break;
			case "tch_absence_prompt":
				mLpAbsencePercent.setEnabled(mCbpAbsencePrompt.isChecked());
				break;
			default:
				break;
		}
		return super.onPreferenceTreeClick(preferenceScreen, preference);
	}
}
