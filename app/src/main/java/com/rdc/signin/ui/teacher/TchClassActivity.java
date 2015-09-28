package com.rdc.signin.ui.teacher;

import android.widget.Toast;

import com.rdc.signin.ui.common.AbsClassActivity;
import com.rdc.signin.ui.widget.FloatingActionButton;

/**
 * Created by seasonyuu on 15/9/28.
 */
public class TchClassActivity extends AbsClassActivity {

	@Override
	protected void onFABClick(FloatingActionButton fab) {
		Toast.makeText(this, "FAB click", Toast.LENGTH_SHORT).show();
	}
}
