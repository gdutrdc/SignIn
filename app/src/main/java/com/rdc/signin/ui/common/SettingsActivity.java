package com.rdc.signin.ui.common;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewGroup;

import com.rdc.signin.R;
import com.rdc.signin.app.SignInApp;


public abstract class SettingsActivity extends ToolbarActivity {

	protected SharedPreferences preferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);

		preferences = SignInApp.getInstance().getPreferences();

		View view = createPreferenceView();
		if (view != null) {
			((ViewGroup) findViewById(R.id.settings_content)).addView(view);
		}
		onReadPreferences();

	}

	protected abstract View createPreferenceView();

	protected abstract void onReadPreferences();

	protected abstract void onSavePreferences();

	protected void setPreferenceEnabled(View v, boolean enabled) {
		if (Build.VERSION.SDK_INT >= 11)
			ViewCompat.setAlpha(v, enabled ? 1 : 0.3f);
		else
			v.setBackgroundResource(enabled ? R.drawable.selectable_item_background : R.color.gray);
		v.setClickable(enabled);

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		onSavePreferences();
	}
}
