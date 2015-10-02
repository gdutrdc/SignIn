package com.rdc.signin.ui.student;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;

import com.rdc.signin.R;


public class StdPreferenceFragment extends PreferenceFragment {
	private CheckBoxPreference mCbpMsgSwitch;
	private CheckBoxPreference mCbpEmailPrompt;
	private CheckBoxPreference mCbpAbsencePrompt;
	private ListPreference mLpAbsenceLimit;
	private CheckBoxPreference mCbpSoundPrompt;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.std_preference);
		getSharePrefrence();

		mLpAbsenceLimit
				.setOnPreferenceChangeListener(new ListPreference.OnPreferenceChangeListener() {

					@Override
					public boolean onPreferenceChange(Preference preference,
					                                  Object newValue) {
						mLpAbsenceLimit.setValue(newValue.toString());
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
			mLpAbsenceLimit.setEnabled(mCbpMsgSwitch.isChecked());
		} else
			mLpAbsenceLimit.setEnabled(mCbpAbsencePrompt.isChecked());
	}

	private void findAllPreferences() {
		mCbpMsgSwitch = (CheckBoxPreference) findPreference("std_message_switch");
		mCbpEmailPrompt = (CheckBoxPreference) findPreference("std_email_prompt");
		mCbpAbsencePrompt = (CheckBoxPreference) findPreference("std_absence_prompt");
		mCbpSoundPrompt = (CheckBoxPreference) findPreference("std_sound_prompt");
		mLpAbsenceLimit = (ListPreference) findPreference("std_absence_limit");
	}

	@Override
	public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen,
	                                     Preference preference) {
		switch (preference.getKey()) {
			case "std_message_switch":
				mCbpEmailPrompt.setEnabled(mCbpMsgSwitch.isChecked());
				mCbpAbsencePrompt.setEnabled(mCbpMsgSwitch.isChecked());
				mCbpSoundPrompt.setEnabled(mCbpMsgSwitch.isChecked());
				mLpAbsenceLimit
						.setEnabled(mCbpMsgSwitch.isChecked() ? mCbpAbsencePrompt
								.isChecked() : false);
				break;
			case "std_absence_prompt":
				mLpAbsenceLimit.setEnabled(mCbpAbsencePrompt.isChecked());
				break;
			default:
				break;
		}
		return super.onPreferenceTreeClick(preferenceScreen, preference);
	}
}
