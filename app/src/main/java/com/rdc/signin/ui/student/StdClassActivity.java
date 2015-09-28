package com.rdc.signin.ui.student;

import android.widget.Toast;

import com.rdc.signin.ui.common.AbsClassActivity;
import com.rdc.signin.ui.widget.FloatingActionButton;

/**
 * Created by seasonyuu on 15/9/27.
 */
public class StdClassActivity extends AbsClassActivity {

	@Override
	protected void onFABClick(FloatingActionButton fab) {

		Toast.makeText(this, "FAB click", Toast.LENGTH_SHORT).show();
	}
}
