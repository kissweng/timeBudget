package com.vince.timebudget.utility;

import com.vince.timebudget.R;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ImageView.ScaleType;

public class MyProcessBar extends RelativeLayout {
	protected static final int STOP = 0x108;
	protected static final int NEXT = 0x109;

	private int m_max = 100;
	private int m_process = 0;
	private Float m_processf = 0f;
	
	private ImageView mImageView = null;
	private LayoutParams params;
	private Handler mHandler;
	private boolean isModified = false;
	private Thread mThread = new Thread(new Runnable() {
		@Override
		public void run() 
		{
				try {
					mThread.sleep(20);
					m_process++;
//					m_process = 50;
					setProgress(m_process);
					reflashPorcess(m_process);// 界面的修改，交由线程来处理
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	});

	public MyProcessBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public MyProcessBar(Context context) {
		super(context);
		init();
	}

	private void init() {
//		setBackgroundResource(R.drawable.widget_battery_bg);
		mHandler = new Handler(getContext().getMainLooper());
		params = new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT);
		params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//		mThread.start();
	}

	public void setMax(int max) {
		m_max = max;
	}

	public int getMax() {
		return m_max;
	}

	public void setProgress(int process) {
		if (process <= m_max) 
		{
			m_process = process;
			mHandler.post(mThread);
		}
	}

	public void setProgress(Float process) {
		if (process <= m_max) {
			m_processf = process;
			mHandler.post(mThread);
//			reflashPorcess(process);
			
		}
	}
	
	public int getProgress() {
		return m_process;
	}

	private int getCountLength() {
		int j = getHeight();
		System.out.println(j+"jjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj");
		return (getHeight() - 16) * m_process / m_max;
	}
	
	private float getCountLengthf() {
		int j = getHeight();
		Float float1 = (getHeight() - 16) * m_processf / m_max;
		Float m_maxf = 100f;
		Float f = m_processf / m_maxf;
		j = (int)(211 * (1-f));
		return j;
	}

	private void reflashPorcess(int process) {
		if (mImageView != null)
			removeView(mImageView);
		mImageView = null;
		mImageView = new ImageView(getContext());
		mImageView.setAdjustViewBounds(true);
		mImageView.setScaleType(ScaleType.FIT_XY);
		
		mImageView.setImageResource(R.drawable.widget_battery_bg1);
		params.height = getCountLength();
		addView(mImageView, params);
	}
	
	public void reflashPorcess(Float process) {
		m_processf = process * 100;
//		m_processf = 60.2f;
		if (mImageView != null)
			removeView(mImageView);
		mImageView = null;
		mImageView = new ImageView(getContext());
		mImageView.setAdjustViewBounds(true);
		mImageView.setScaleType(ScaleType.FIT_XY);
		
		mImageView.setImageResource(R.drawable.widget_battery_bg1);
		int j = (int) getCountLengthf();
		params.height = j;
		addView(mImageView, params);
	}
	
}