package com.library.larry.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringSystem;
import com.facebook.rebound.SpringUtil;
import com.library.larry.R;
import com.library.larry.ui.util.FontUtil;

/**
 * 自定义MyTextView,可使用fontAwesome图标库和FontFz字体
 */

public class MyTextView extends android.support.v7.widget.AppCompatTextView
{
	// 是否可以触摸回弹
	private boolean mEnableTouchSpring = false;
	private boolean mRemovePadding = false;
	private final boolean mEnableCircleAnimation;

	private Spring mScaleSpring;
	private static final SpringConfig ORIGAMI_SPRING_CONFIG = SpringConfig
			.fromOrigamiTensionAndFriction(80, 3);
	private MySpringListener mScaleSpringListener;

	/*************** public method ********************/

	// 是否开启facebook动画
	public void setTouchSprint(boolean opened)
	{
		mEnableTouchSpring = opened;
		setClickable(mEnableTouchSpring);
	}

	/*************** public method ********************/

	public MyTextView(Context context)
	{
		this(context, null);
	}

	public MyTextView(Context context, AttributeSet attrs)
	{
		this(context, attrs, 0);
	}

	public MyTextView(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);

		initSpring();

		TypedArray a = context
				.obtainStyledAttributes(attrs, R.styleable.MyTextView);

		float textSize = a.getDimension(R.styleable.MyTextView_textSize, 0);
		if( 0 != textSize )
		{
			getPaint().setTextSize(textSize);
		}
		mEnableTouchSpring = a
				.getBoolean(R.styleable.MyTextView_enableTouchSpring, false);
        mRemovePadding = a
				.getBoolean(R.styleable.MyTextView_removePadding, false);
		mEnableCircleAnimation = a
				.getBoolean(R.styleable.MyTextView_enableCircleAnimation, false);

		String mFontTemp  = a.getString(R.styleable.MyTextView_myFontFamily);
		int mFont = 0;
		try {
			mFont = Integer.parseInt(mFontTemp);
		}catch (Exception e){
			mFont = 0;
		}
		if( mFont == 1 )
		{
			FontUtil.setFontFamilyFontawesome(this);
		}
		else if( mFont == 2 )
		{
			FontUtil.setFontFamilyFzlt(this);
		}

		if( mEnableTouchSpring )
		{
			this.setClickable(true);
		}
		if( mEnableCircleAnimation )
		{
			this.setClickable(true);
		}

		a.recycle();
	}

	@Override
	protected void onAttachedToWindow()
	{
		super.onAttachedToWindow();
		mScaleSpring.addListener(mScaleSpringListener);
	}

	@Override
	protected void onDetachedFromWindow()
	{
		super.onDetachedFromWindow();
		mScaleSpring.removeListener(mScaleSpringListener);
	}

	private void initSpring()
	{
		mScaleSpringListener = new MySpringListener(this);
		mScaleSpring = SpringSystem.create().createSpring()
				.setSpringConfig(ORIGAMI_SPRING_CONFIG);
		setClickable(mEnableTouchSpring);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		if( mEnableTouchSpring && isClickable() )
		{
			switch( event.getAction() )
			{
				case MotionEvent.ACTION_DOWN:
					mScaleSpring.setEndValue(1);
					break;
				case MotionEvent.ACTION_UP:
					mScaleSpring.setEndValue(0);
					break;
				default:
					break;
			}
		}
		return super.onTouchEvent(event);
	}

	private class MySpringListener extends SimpleSpringListener
	{
		View view;

		MySpringListener(View view)
		{
			this.view = view;
		}

		@Override
		public void onSpringUpdate(Spring spring)
		{
			float mappedValue = (float) SpringUtil
					.mapValueFromRangeToRange(spring
							.getCurrentValue(), 0, 1, 1, 0.8);
			float alphaVaue = (float) SpringUtil.mapValueFromRangeToRange(spring
					.getCurrentValue(), 0, 1, 1, 0.5);

			view.setScaleX(mappedValue);
			view.setScaleY(mappedValue);
			view.setAlpha(alphaVaue);
		}
	}

	Paint.FontMetricsInt fontMetricsInt;

	@Override
	protected void onDraw(Canvas canvas)
	{
		if( mRemovePadding )
		{// 设置是否remove间距，true为remove
			if( fontMetricsInt == null )
			{
				fontMetricsInt = new Paint.FontMetricsInt();
				getPaint().getFontMetricsInt(fontMetricsInt);
			}
			canvas.translate(0, fontMetricsInt.top - fontMetricsInt.ascent);
		}
		super.onDraw(canvas);
	}
}
