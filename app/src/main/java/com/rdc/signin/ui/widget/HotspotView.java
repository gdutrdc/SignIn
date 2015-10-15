package com.rdc.signin.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by seasonyuu on 15/10/12.
 */
public class HotspotView extends View {
	private final int DEFAULT_PADDING = 54;

	private Context context;

	private Paint backgroundPaint;
	private Paint linePaint;

	private int size = -1;
	private int circleRadius = -1;

	private boolean isAnimating = false;

	private Runnable runnable = new Runnable() {
		@Override
		public void run() {
			if (isAnimating) {
				if (circleRadius != -1) {
					circleRadius += 2;
					if (circleRadius > size)
						circleRadius = 0;
					postInvalidate();
				}
				postDelayed(this, 15);
			}
		}
	};

	public HotspotView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public HotspotView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		isAnimating = true;
		post(runnable);
		this.context = context;
	}

	public void setAnimating(boolean isAnimating) {
		this.isAnimating = isAnimating;
		post(runnable);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if (size == -1) {
			size = Math.min(getMeasuredWidth(), getMeasuredHeight()) / 2;
			circleRadius = size;
		}

		if (backgroundPaint == null) {
			backgroundPaint = new Paint();
			backgroundPaint.setColor(Color.GRAY);
			backgroundPaint.setAlpha(128);
		}
		canvas.drawCircle(getMeasuredWidth() / 2, getMeasuredHeight() / 2,
				size - DEFAULT_PADDING, backgroundPaint);

		if (linePaint == null) {
			linePaint = new Paint();
			linePaint.setColor(Color.WHITE);
			linePaint.setStyle(Paint.Style.STROKE);
			linePaint.setAntiAlias(true);
		}
		canvas.drawCircle(getMeasuredWidth() / 2, getMeasuredHeight() / 2,
				(circleRadius + size) % size - DEFAULT_PADDING, linePaint);
		canvas.drawCircle(getMeasuredWidth() / 2, getMeasuredHeight() / 2,
				(circleRadius + size / 3) % size - DEFAULT_PADDING, linePaint);
		canvas.drawCircle(getMeasuredWidth() / 2, getMeasuredHeight() / 2,
				(circleRadius + size / 3 * 2) % size - DEFAULT_PADDING, linePaint);

	}
}
