package com.rdc.signin.ui.common;

import android.os.Build;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

import com.rdc.signin.R;
import com.rdc.signin.utils.UIUtils;

/**
 * Created by seasonyuu on 15/9/17.
 */
public class ToolbarActivity extends AbsActivity {
	private Toolbar toolbar;
	private View statusBar;

	@Override
	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
		setupTopBar();
	}

	@Override
	public void setContentView(View view) {
		super.setContentView(view);
		setupTopBar();
	}


	private void setupTopBar() {
		toolbar = UIUtils.findView(this, R.id.toolbar);
		statusBar = UIUtils.findView(this, R.id.status_bar);

		if (toolbar != null) {
			setSupportActionBar(toolbar);
		}

		if (Build.VERSION.SDK_INT >= 19 && statusBar != null) {
			ViewGroup.LayoutParams params = statusBar.getLayoutParams();
			params.height = UIUtils.getStatusHeight(this);
			statusBar.setLayoutParams(params);
		}
	}
}
