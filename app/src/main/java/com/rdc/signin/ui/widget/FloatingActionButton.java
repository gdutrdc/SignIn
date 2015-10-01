package com.rdc.signin.ui.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

import com.rdc.signin.R;
import com.rdc.signin.utils.UIUtils;

/**
 * Created by seasonyuu on 15/9/27.
 */
public class FloatingActionButton extends View {
	private int mBackgroundColor;
	private Drawable mDrawable;
	private int mSize;
	private Drawable mRipple;

	private float mShadowSize;
	private Paint shadowPaint;
	private int mShadowPadding;

	private final float SHADOW_SIZE_NORMAL = UIUtils.convertDpToPixel(3, getContext());
	private final float SHADOW_SIZE_PRESSED = UIUtils.convertDpToPixel(6, getContext());

	private static final int SIZE_MINI = 1;
	private static final int SIZE_NORMAL = 0;

	public FloatingActionButton(Context context) {
		this(context, null);
	}

	public FloatingActionButton(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public FloatingActionButton(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		setWillNotDraw(false);
		setClickable(true);

		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FloatingActionButton, defStyleAttr, 0);

		TypedValue typedValue = new TypedValue();
		context.getTheme().resolveAttribute(R.attr.colorAccent, typedValue, true);
		mBackgroundColor = a.getColor(R.styleable.FloatingActionButton_backgroundColor, typedValue.data);

		mSize = a.getInt(R.styleable.FloatingActionButton_fab_size, SIZE_NORMAL);

		final Drawable d = a.getDrawable(R.styleable.FloatingActionButton_src);
		if (d != null) {
			setImageDrawable(d);
		}

		a.recycle();

		mShadowPadding = (int) getRealSize(16);

		if (Build.VERSION.SDK_INT >= 21) {
			setLayerType(View.LAYER_TYPE_HARDWARE, null);

			mRipple = new RippleDrawable(new ColorStateList(new int[][]{
					{}
			}, new int[]{
					Color.WHITE
			}), new ColorDrawable(mBackgroundColor), null);


			setOutlineProvider(new ViewOutlineProvider() {
				@Override
				public void getOutline(View view, Outline outline) {
					if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
						outline.setOval(getPaddingLeft() + mShadowPadding,
								getPaddingTop() + mShadowPadding,
								getPaddingLeft() + getSizeDimension() + mShadowPadding,
								getPaddingTop() + getSizeDimension() + mShadowPadding);
					}
				}
			});
			setClipToOutline(true);
			setBackgroundDrawable(mRipple);
			setElevation(16f);
			setTranslationZ(6f);
		} else {
			if (Build.VERSION.SDK_INT >= 11)
				setLayerType(LAYER_TYPE_SOFTWARE, null);

			shadowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
			shadowPaint.setAntiAlias(true);
			shadowPaint.setColor(mBackgroundColor);

			mShadowSize = SHADOW_SIZE_NORMAL;
		}

	}

	public void setImageDrawable(Drawable drawable) {
		mDrawable = drawable;
		invalidate();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int preferredSize = this.getSizeDimension();
		int w = resolveAdjustedSize(preferredSize, widthMeasureSpec);
		int h = resolveAdjustedSize(preferredSize, heightMeasureSpec);
		int d = Math.min(w, h);
		this.setMeasuredDimension(d + this.mShadowPadding + this.mShadowPadding, d + this.mShadowPadding + this.mShadowPadding);
	}

	public final int getSizeDimension() {
		switch (this.mSize) {
			case SIZE_NORMAL:
			default:
				return (int) getRealSize(56);
			case SIZE_MINI:
				return (int) getRealSize(40);
		}
	}

	private float getRealSize(int dp) {
		Resources resources = getContext().getResources();
		DisplayMetrics metrics = resources.getDisplayMetrics();
		return dp * (metrics.densityDpi / 148f);
	}

	private static int resolveAdjustedSize(int desiredSize, int measureSpec) {
		int result = desiredSize;
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);
		switch (specMode) {
			case MeasureSpec.AT_MOST:
				result = Math.min(desiredSize, specSize);
				break;
			case MeasureSpec.UNSPECIFIED:
				result = desiredSize;
				break;
			case MeasureSpec.EXACTLY:
				result = specSize;
		}

		return result;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		int height = getMeasuredHeight();
		int width = getMeasuredWidth();


		Paint paint = new Paint();
		if (shadowPaint != null) {
			shadowPaint.setShadowLayer(10.0f, 2.0f, mShadowSize, Color.GRAY);
			canvas.drawCircle(width / 2, height / 2, getSizeDimension() / 2, shadowPaint);
		} else {
			mRipple.setBounds(0, 0, getWidth(), getHeight());
			mRipple.draw(canvas);
		}
		if (mDrawable != null) {
			Bitmap bitmap = ((BitmapDrawable) mDrawable).getBitmap();
			float scaled = 1.0f * getSizeDimension() *
					(mSize == SIZE_NORMAL ? 3.0f / 7 : 3.0f / 5) / Math.min(bitmap.getWidth(), bitmap.getHeight());
			bitmap = Bitmap.createScaledBitmap(bitmap,
					(int) (bitmap.getWidth() * scaled),
					(int) (bitmap.getHeight() * scaled), false);
			canvas.drawBitmap(bitmap,
					(width - bitmap.getWidth()) / 2,
					(height - bitmap.getHeight()) / 2,
					paint);
		}


	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		boolean ret = super.onTouchEvent(event);
		if (mRipple == null) {
			if (event.getAction() == MotionEvent.ACTION_UP ||
					((Math.sqrt(Math.pow(event.getX() - getWidth() / 2, 2) + Math.pow(event.getY() - getHeight() / 2, 2))) > getSizeDimension())) {
				mShadowSize = SHADOW_SIZE_NORMAL;
				shadowPaint.setColor(mBackgroundColor);
				invalidate();
			} else if (event.getAction() == MotionEvent.ACTION_DOWN) {
				mShadowSize = SHADOW_SIZE_PRESSED;
				int color = mBackgroundColor;
				int red = Color.red(mBackgroundColor);
				int green = Color.green(mBackgroundColor);
				int blue = Color.blue(mBackgroundColor);
				if (Color.WHITE - color < color - Color.BLACK)
					color = Color.argb(0xff,
							red - 48 < 0 ? 0 : red - 48,
							green - 48 < 0 ? 0 : green - 48,
							blue - 48 < 0 ? 0 : blue - 48);
				else
					color = Color.argb(0xff,
							(red + 48 > 0xff ? 0xff : red + 48),
							(green + 48 > 0xff ? 0xff : green + 48),
							(blue + 48 > 0xff ? 0xff : blue + 48));
				shadowPaint.setColor(color);
				invalidate();
			}
		}
		return ret;
	}

	public void hide() {
		post(new Runnable() {
			@Override
			public void run() {
				AnimationSet animationSet = new AnimationSet(true);
				ScaleAnimation scaleAnimation = new ScaleAnimation(1, 0, 1, 0);
				animationSet.addAnimation(scaleAnimation);
				TranslateAnimation translateAnimation = new TranslateAnimation(0, getWidth() / 2, 0, getHeight() / 2);
				animationSet.addAnimation(translateAnimation);
				animationSet.setDuration(200L);

				startAnimation(animationSet);
				setVisibility(GONE);
			}
		});
	}

	public void show() {
		post(new Runnable() {
			@Override
			public void run() {
				AnimationSet animationSet = new AnimationSet(true);
				ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1, 0, 1);
				animationSet.addAnimation(scaleAnimation);
				TranslateAnimation translateAnimation = new TranslateAnimation(getWidth() / 2, 0, getHeight() / 2, 0);
				animationSet.addAnimation(translateAnimation);
				animationSet.setDuration(200L);

				startAnimation(animationSet);
				setVisibility(VISIBLE);
			}
		});
	}

	public boolean isShowing() {
		return getVisibility() == VISIBLE;
	}
}
