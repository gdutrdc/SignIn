package com.rdc.signin.ui.common;

import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.rdc.signin.R;
import com.rdc.signin.utils.UIUtils;

/**
 * Created by seasonyuu on 15/9/27.
 */
public abstract class AbsClassActivity extends ToolbarActivity {
	private FloatingActionButton fab;
	private NestedScrollView scrollView;
	private CollapsingToolbarLayout collapsingToolbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_class);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);

		collapsingToolbar = UIUtils.findView(this, R.id.collapsing_toolbar);

		initView();

		Parcelable clazz = getIntent().getParcelableExtra("class");
		handlerClassInfo(clazz);
	}

	private void initView() {
		fab = UIUtils.findView(this, R.id.class_fab);
		scrollView = UIUtils.findView(this, R.id.class_scroll_view);

		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onFABClick(fab);
			}
		});

		View contentView = createContentView();
		if (contentView != null)
			((ViewGroup) scrollView.getChildAt(0)).addView(contentView);
		scrollView.setVerticalScrollBarEnabled(false);

		if (Build.VERSION.SDK_INT == 19) {
			FrameLayout.LayoutParams params = ((FrameLayout.LayoutParams) findViewById(R.id.class_main_content).getLayoutParams());
			params.topMargin = UIUtils.getStatusHeight(this);
			findViewById(R.id.class_main_content).setLayoutParams(params);

			View view = new View(this);
			FrameLayout.LayoutParams params1 = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, UIUtils.getStatusHeight(this));
			view.setLayoutParams(params);
			TypedValue typedValue = new TypedValue();
			getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValue, true);
			view.setBackgroundColor(typedValue.data);
			addContentView(view, params1);
		}
	}

	public void setToolbarTitle(String title) {
		collapsingToolbar.setTitle(title);
	}

	protected abstract void onFABClick(FloatingActionButton fab);

	protected abstract View createContentView();

	protected abstract void handlerClassInfo(Parcelable clazz);
}
