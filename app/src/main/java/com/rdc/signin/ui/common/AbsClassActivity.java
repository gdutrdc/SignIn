package com.rdc.signin.ui.common;

import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rdc.signin.R;
import com.rdc.signin.ui.widget.FloatingActionButton;
import com.rdc.signin.ui.widget.StickyScrollView;
import com.rdc.signin.utils.UIUtils;

/**
 * Created by seasonyuu on 15/9/27.
 */
public abstract class AbsClassActivity extends ToolbarActivity implements StickyScrollView.OnScrollChangeListener {
	private FloatingActionButton fab;
	private StickyScrollView scrollView;

	private TextView tvTitle;

	private int appBarMaxHeight;
	private int toolbarHeight;
	private int statusBarHeight = 0;
	private float defaultTextSize;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_class);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setTitle("");

		initView();

		Parcelable clazz = getIntent().getParcelableExtra("class");
		handlerClassInfo(clazz);
	}

	private void initView() {
		fab = UIUtils.findView(this, R.id.class_fab);
		scrollView = UIUtils.findView(this, R.id.class_scroll_view);
		tvTitle = UIUtils.findView(this, R.id.class_title);

		if (Build.VERSION.SDK_INT >= 21)
			tvTitle.setElevation(UIUtils.convertDpToPixel(4, this));

		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onFABClick(fab);
			}
		});

		//初始化各项用于计算高度的变量
		appBarMaxHeight = (int) UIUtils.convertDpToPixel(150, this);
		getToolbar().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				toolbarHeight = getToolbar().getHeight();
				statusBarHeight = findViewById(R.id.status_bar).getHeight();

				//初始化ScrollView
				int padding = (int) UIUtils.convertDpToPixel(16, AbsClassActivity.this);
				scrollView.getChildAt(0).setPadding(padding,
						padding / 2 + appBarMaxHeight - toolbarHeight + fab.getSizeDimension() / 2, padding, padding);

				//初始化标题
				defaultTextSize = tvTitle.getTextSize();
				defaultTextSize = UIUtils.convertPixelsToDp(defaultTextSize, AbsClassActivity.this);
				tvTitle.setTextSize(defaultTextSize * 1.5f);

				getToolbar().getViewTreeObserver().removeGlobalOnLayoutListener(this);
			}
		});

		View contentView = createContentView();
		if (contentView != null)
			((ViewGroup) scrollView.getChildAt(0)).addView(contentView);
		scrollView.setVerticalScrollBarEnabled(false);
		scrollView.setOnScrollChangeListener(this);
	}

	public void setToolbarTitle(String title) {
		tvTitle.setText(title);
	}

	@Override
	public void onScrollChange(int l, int t, int oldl, int oldt) {
		int changeHeight = appBarMaxHeight - t;
		RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) tvTitle.getLayoutParams();

		if (changeHeight > appBarMaxHeight) {
			if (tvTitle.getHeight() != appBarMaxHeight) {
				params.height = appBarMaxHeight;
				tvTitle.setLayoutParams(params);
			}
		} else if (changeHeight < toolbarHeight) {
			if (tvTitle.getHeight() != toolbarHeight) {
				params.height = toolbarHeight;
				tvTitle.setLayoutParams(params);
				if (fab.isShowing())
					fab.hide();
			}
		} else {
			if (t >= 0 && t <=
					appBarMaxHeight - toolbarHeight) {
				if (changeHeight <= 1.5 * toolbarHeight && fab.isShowing())
					fab.hide();
				else if (changeHeight > 1.5 * toolbarHeight && !fab.isShowing())
					fab.show();
				params.height = changeHeight;
				tvTitle.setTextSize(
						defaultTextSize * (1 + 0.5f * (changeHeight - toolbarHeight) / (appBarMaxHeight - toolbarHeight)));
				tvTitle.setLayoutParams(params);
			}
		}
	}

	protected abstract void onFABClick(FloatingActionButton fab);

	protected abstract View createContentView();

	protected abstract void handlerClassInfo(Parcelable clazz);
}
