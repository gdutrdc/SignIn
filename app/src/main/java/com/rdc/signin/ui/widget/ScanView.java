package com.rdc.signin.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by seasonyuu on 15/10/12.
 */
public class ScanView extends View {
	private final int DEFAULT_PADDING = 54;
	private Paint scanPaint;
	private Paint linePaint;

	private boolean isAnimating = false;

	private Matrix matrix;

	private int angle = 0;

	private Runnable runnable = new Runnable() {
		@Override
		public void run() {
			if(!isAnimating)
				return;
			angle += 1;
			matrix = new Matrix();
			matrix.postRotate(angle, getMeasuredWidth() / 2, getMeasuredHeight() / 2);
			postInvalidate();
			postDelayed(this, 15);
		}
	};

	public ScanView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ScanView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		matrix = new Matrix();
		post(runnable);
	}

	public void setAnimating(boolean isAnimating) {
		this.isAnimating = isAnimating;
		post(runnable);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		int size = Math.min(getMeasuredHeight(), getMeasuredWidth()) / 2;

		if (linePaint == null) {
			linePaint = new Paint();
			linePaint.setColor(Color.BLACK);
			linePaint.setStyle(Paint.Style.STROKE);
			linePaint.setAntiAlias(true);
		}
		canvas.drawCircle(getMeasuredWidth() / 2, getMeasuredHeight() / 2,
				size - DEFAULT_PADDING, linePaint);
		canvas.drawCircle(getMeasuredWidth() / 2, getMeasuredHeight() / 2,
				size / 4, linePaint);
		canvas.drawLine(DEFAULT_PADDING, getMeasuredHeight() / 2,
				getMeasuredWidth() - DEFAULT_PADDING, getMeasuredHeight() / 2, linePaint);
		canvas.drawLine(size,
				getMeasuredHeight() / 2 - getMeasuredWidth() / 2 + DEFAULT_PADDING,
				size,
				getMeasuredHeight() / 2 + getMeasuredWidth() / 2 - DEFAULT_PADDING, linePaint);

		if (scanPaint == null) {
			scanPaint = new Paint();
			SweepGradient sweepGradient = new SweepGradient(getMeasuredWidth() / 2,
					getMeasuredHeight() / 2, Color.parseColor("#30000000"), Color.TRANSPARENT);
			scanPaint.setShader(sweepGradient);
		}
		canvas.concat(matrix);
		canvas.drawCircle(getMeasuredWidth() / 2, getMeasuredHeight() / 2,
				size - DEFAULT_PADDING, scanPaint);

	}
}
