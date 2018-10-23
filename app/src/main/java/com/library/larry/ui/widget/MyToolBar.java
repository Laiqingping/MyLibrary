package com.library.larry.ui.widget;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.zeninfor.operator.R;
import com.zeninfor.operator.common.FontUtil;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MyToolBar extends FrameLayout
{
	@BindView(R.id.tv_left_icon)
	MyTextView mTvLeftIcon;
	@BindView(R.id.tv_center_icon)
	MyTextView mTvCenterIcon;
	@BindView(R.id.tv_right_icon)
	MyTextView mTvRightIcon;
	@BindView(R.id.rl_root)
    RelativeLayout mRlRoot;

	private OnPositionClickListener onPositionClickListener = null;

	/************************** public method ******************************/

	public void setOnPositionClickListener(
			OnPositionClickListener onPositionClickListener)
	{
		this.onPositionClickListener = onPositionClickListener;
	}

	// 设置成普通的toolbar
	public void setCommonWithTitle(String title)
	{
		FontUtil.setFontFamilyFzlt(mTvLeftIcon);
		FontUtil.setFontFamilyFzlt(mTvRightIcon);
		mTvLeftIcon.setTextSize(16);
		mTvRightIcon.setTextSize(16);
		mTvLeftIcon.setText(R.string.action_cancel);
		mTvRightIcon.setText(R.string.action_ensure);
		mTvCenterIcon.setText(title);
	}

	public View getViewByPosition(int position)
	{
		switch( position )
		{
			case 0:
				return mTvLeftIcon;
			case 1:
				return mTvCenterIcon;
			case 2:
				return mTvRightIcon;
			default:
				return null;
		}
	}

	public void setTvCenter(String title)
	{
		mTvCenterIcon.setText(title);
	}

	public String getTitle()
	{
		return mTvCenterIcon.getText().toString();
	}

	/**
	 * 设置背景颜色
	 * 
	 * @param color
	 */
	public void setToolBarBackgroundColor(@ColorInt int color)
	{
		mRlRoot.setBackgroundColor(color);
	}

	/**
	 * 设置文本颜色
	 * 
	 * @param position
	 * @param color
	 */
	public void setTvColor(int position, @ColorInt int color)
	{
		if( position == 1 )
		{
			mTvCenterIcon.setTextColor(color);
		}
	}

	/************************** public method ******************************/

	public MyToolBar(Context context)
	{
		this(context, null);
	}

	public MyToolBar(Context context, AttributeSet attrs)
	{
		this(context, attrs, 0);
	}

	public MyToolBar(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);

		View view = LayoutInflater.from(context)
				.inflate(R.layout.view_toolbar, this);
		ButterKnife.bind(view);

		mTvLeftIcon.setOnClickListener(view1 -> {
			if( null != onPositionClickListener )
			{
				onPositionClickListener.onPositionClick(mTvLeftIcon, 0);
			}
		});
		mTvCenterIcon.setOnClickListener(view12 -> {
			if( null != onPositionClickListener )
			{
				onPositionClickListener.onPositionClick(mTvCenterIcon, 1);
			}
		});
		mTvRightIcon.setOnClickListener(view1 -> {
			if( null != onPositionClickListener )
			{
				onPositionClickListener.onPositionClick(mTvRightIcon, 2);
			}
		});

	}

	public MyTextView getTvCenterIcon()
	{
		return mTvCenterIcon;
	}

	public interface OnPositionClickListener
	{
		void onPositionClick(View view, int position);
	}

	public MyTextView getTvRightIcon()
	{
		return mTvRightIcon;
	}

	public MyTextView getTvLeftIcon()
	{
		return mTvLeftIcon;
	}

	public void setLeftBackIcon()
	{
		setLeftIcon(getContext().getString(R.string.fa_angle_left));
		return;
	}

	public void setLeftIcon(String iconText)
	{
		FontUtil.setFontFamilyFontawesome(mTvLeftIcon);
		mTvLeftIcon.setText(iconText);
		mTvLeftIcon.getPaint().setTextSize(getResources()
				.getDimensionPixelSize(R.dimen.font_size_middle));
		return;
	}

	public void setCenterIcon(String iconText)
	{
		FontUtil.setFontFamilyFontawesome(mTvCenterIcon);
		mTvCenterIcon.setText(iconText);
		mTvCenterIcon.getPaint().setTextSize(getResources()
				.getDimensionPixelSize(R.dimen.font_size_middle));
		return;
	}

	public void setRightIcon(String iconText)
	{
		FontUtil.setFontFamilyFontawesome(mTvRightIcon);
		mTvRightIcon.setText(iconText);
		mTvRightIcon.getPaint().setTextSize(getResources()
				.getDimensionPixelSize(R.dimen.font_size_middle));
		return;
	}

	public void setRightText(String text)
	{
		mTvRightIcon.setText(text);
		mTvRightIcon.getPaint().setTextSize(getResources()
				.getDimensionPixelSize(R.dimen.font_size_default));
		return;
	}

	public void setLeftText(String text)
	{
		mTvLeftIcon.setText(text);
		mTvLeftIcon.getPaint().setTextSize(getResources()
				.getDimensionPixelSize(R.dimen.font_size_default));
		return;
	}
}
