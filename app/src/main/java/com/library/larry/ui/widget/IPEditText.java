package com.library.larry.ui.widget;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.library.larry.R;
import com.socks.library.KLog;

/**
 * 自定义IP地址输入框
 */

public class IPEditText extends LinearLayout
{
	private EditText mFirstIP;
	private EditText mSecondIP;
	private EditText mThirdIP;
	private EditText mFourthIP;

	public IPEditText(Context context, AttributeSet attrs)
	{
		super(context, attrs);

		/**
		 * 初始化控件
		 */
		View view = LayoutInflater.from(context)
				.inflate(R.layout.custom_my_edittext, this);
		mFirstIP = (EditText) findViewById(R.id.ip_first);
		mSecondIP = (EditText) findViewById(R.id.ip_second);
		mThirdIP = (EditText) findViewById(R.id.ip_third);
		mFourthIP = (EditText) findViewById(R.id.ip_fourth);
        MyDeleteListener listener = new MyDeleteListener();
        mSecondIP.setOnKeyListener(listener);
        mThirdIP.setOnKeyListener(listener);
        mFourthIP.setOnKeyListener(listener);
		OperatingEditText(context);
	}

	private class MyDeleteListener implements OnKeyListener
    {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event)
        {
            if(keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent
                    .ACTION_DOWN)
            {
                if( v == mSecondIP )
                {
                    if( 0 == mSecondIP.getText().toString().length() )
                    {
                        mFirstIP.setFocusable(true);
                        mFirstIP.requestFocus();
                        mFirstIP.setSelection(mFirstIP.getText().length());
                        return true;
                    }
                }
                else if( v == mThirdIP )
                {
                    if( 0 == mThirdIP.getText().toString().length() )
                    {
                        mSecondIP.setFocusable(true);
                        mSecondIP.requestFocus();
                        mSecondIP.setSelection(mSecondIP.getText().length());
                        return true;
                    }
                }
                else if( v == mFourthIP )
                {
                    if( 0 == mFourthIP.getText().toString().length() )
                    {
                        mThirdIP.setFocusable(true);
                        mThirdIP.requestFocus();
                        mThirdIP.setSelection(mThirdIP.getText().length());
                        return true;
                    }
                }
            }
            return false;
        }
    }

	/**
	 * 获得EditText中的内容,当每个Edittext的字符达到三位时,自动跳转到下一个EditText,当用户点击.时,
	 * 下一个EditText获得焦点
	 */
	private void OperatingEditText(final Context context)
	{
		mFirstIP.addTextChangedListener(new TextWatcher()
		{

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
                                      int count)
			{
				/**
				 * 获得EditTe输入内容,做判断,如果大于255,提示不合法,当数字为合法的三位数下一个EditText获得焦点,
				 * 用户点击啊.时,下一个EditText获得焦点
				 */
                KLog.e("first ip changed");
                refreshText(start,s.toString(),mFirstIP,mSecondIP,null);
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after)
			{

			}

			@Override
			public void afterTextChanged(Editable s)
			{

			}
		});

		mSecondIP.addTextChangedListener(new TextWatcher()
		{

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
                                      int count)
			{
                KLog.e("second ip changed");
                refreshText(start,s.toString(),mSecondIP,mThirdIP,mFirstIP);
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after)
			{

			}

			@Override
			public void afterTextChanged(Editable s)
			{

			}
		});

		mThirdIP.addTextChangedListener(new TextWatcher()
		{

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
                                      int count)
			{
                KLog.e("third ip changed");
                refreshText(start,s.toString(),mThirdIP,mFourthIP,mSecondIP);
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after)
			{

			}

			@Override
			public void afterTextChanged(Editable s)
			{

			}
		});

		mFourthIP.addTextChangedListener(new TextWatcher()
		{

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
                                      int count)
			{
                KLog.e("fourth ip changed");
                refreshText(start,s.toString(),mFourthIP,null,mThirdIP);
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after)
			{

			}

			@Override
			public void afterTextChanged(Editable s)
			{

			}
		});
	}

	public String getText(Context context)
	{
		String text1 = mFirstIP.getText().toString();
		String text2 = mSecondIP.getText().toString();
		String text3 = mThirdIP.getText().toString();
		String text4 = mFourthIP.getText().toString();
        if( TextUtils.isEmpty(text1) || TextUtils.isEmpty(text2)
                || TextUtils.isEmpty(text3) || TextUtils.isEmpty(text4))
        {
            return "";
        }
        else
        {
            return text1 + "." + text2 + "." + text3 + "." + text4;
        }
	}

	public void setIp(String ip)
	{
		String[] strings = ip.split("\\.");
		try
		{
			mFirstIP.setText(strings[0]);
			mSecondIP.setText(strings[1]);
			mThirdIP.setText(strings[2]);
			mFourthIP.setText(strings[3]);
			mFourthIP.setFocusable(true);
			mFourthIP.requestFocus();
		} catch( Exception e )
		{
			e.printStackTrace();
		}
	}

	private void refreshText(int start, String s, EditText et, EditText nextEt, EditText preEt)
    {
        KLog.e("refreshText--->string:"+s);
        if( s != null && s.length() > 0 )
        {
            String current = null;
            et.setSelection(et.getText().length());
            if( s.length() > 2 || s.trim().contains(".") )
            {
                if( s.trim().contains(".") )
                {
                    current = s.substring(0, s.length() - 1);
                    et.setText(current);
                }
                else
                {
                    current = s.trim();
                }

                if( current.startsWith("0") && 1 < current.length() )
                {
                    current = current.substring(1);
                }
                try
                {
                    if( Integer.parseInt(current) > 255 )
                    {
                        current = String.valueOf(255);
                        et.setText(current);
                    }
                }catch (Exception e)
                {
                    current = String.valueOf("");
                    et.setText(current);
                }
                if( null != nextEt )
                {
                    nextEt.setFocusable(true);
                    nextEt.requestFocus();
                }
            }
            else
            {
                if( 1 < s.length() && s.startsWith("0") )
                {
                    s = s.substring(1);
                    et.setText(s);
                }
            }
        }

        /**
         * 当用户需要删除时,此时的EditText为空时,上一个EditText获得焦点
         */
//        KLog.e("step1");
//        if( null != preEt )
//        {
//            KLog.e("step2,start:"+start+" length:"+s.length());
//            if( start == 0 && s.length() == 0 )
//            {
//                KLog.e("step3:  "+start+"   s:"+s);
//                et.setOnKeyListener(new OnKeyListener()
//                {
//                    @Override
//                    public boolean onKey(View v, int keyCode, KeyEvent event)
//                    {
//                        KLog.e("step4");
//                        if(keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent
//                                .ACTION_DOWN)
//                        {
//                            KLog.e("step5");
//                            preEt.setFocusable(true);
//                            preEt.requestFocus();
//                            preEt.setSelection(preEt.getText().length());
//                            return true;
//                        }
//                        KLog.e("step6");
//                        return false;
//                    }
//                });
//            }
//            else
//            {
//                KLog.e("step7");
//                et.setOnKeyListener(null);
//            }
//        }
    }
}
