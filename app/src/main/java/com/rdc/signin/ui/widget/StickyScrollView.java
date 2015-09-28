package com.rdc.signin.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by seasonyuu on 15/9/28.
 */
public class StickyScrollView extends ScrollView {
	public interface OnScrollChangeListener {
		void onScrollChange(int l, int t, int oldl, int oldt);
	}

	private OnScrollChangeListener listener;

	public StickyScrollView(Context context) {
		super(context);
	}

	public StickyScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public StickyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public void setOnScrollChangeListener(OnScrollChangeListener l) {
		listener = l;
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
		if (listener != null) {
			listener.onScrollChange(l, t, oldl, oldt);
		}
	}
}
