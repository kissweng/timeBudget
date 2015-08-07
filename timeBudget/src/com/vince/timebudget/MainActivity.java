package com.vince.timebudget;

import java.util.Calendar;

import com.vince.timebudget.R;
import com.vince.timebudget.utility.MyProcessBar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewDebug.FlagToString;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {

	private Calendar calendar = Calendar.getInstance();
	private String year = null;
	private String month = null;
	private String day = null;
	private String hour = null;
	private String minute = null;

	private String budget_balance_hour = null;

	private TextView month_tv = null;
	private TextView day_tv = null;
	private TextView income_amount_tv = null;
	private TextView expense_amount_tv = null;
	private TextView budget_balance_amount_tv = null;

	private MyProcessBar myProcessBar = null;

	
	private Float income_amount = 0f;
	private Float expense_amount = 0f;
	private Button add_expense_quickly_btn = null;
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK) {
			initInfo();
		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		// 去掉窗口标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		LayoutInflater inflater = this.getLayoutInflater();
		View view1 = inflater.inflate(R.layout.main_activity, null);

		// 加载布局
		setContentView(R.layout.main_activity);

		initView(view1);

		initDate();
		loadingFormation();
		initInfo();

	}

	private void initView(View v) {
		// TODO Auto-generated method stub
		int w = View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED);
		int h = View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED);

		ImageView imageView = (ImageView) v.findViewById(R.id.battery_bg);

		imageView.measure(w, h);
		int height = imageView.getMeasuredHeight();
		int width = imageView.getMeasuredWidth();

		myProcessBar = (MyProcessBar) v.findViewById(R.id.budget_pb);

		
		
		// LinearLayout.LayoutParams linearParams;
//		FrameLayout.LayoutParams linearParams;
//		linearParams = (FrameLayout.LayoutParams) myProcessBar
//				.getLayoutParams();
//
//		// LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams)
//		// myProcessBar.getLayoutParams(); // 取控件mGrid当前的布局参数
//		linearParams.height = height;// 当控件的高强制设成75象素
//		linearParams.width = width;// 当控件的高强制设成75象素
//		myProcessBar.setLayoutParams(linearParams); // 使设置好的布局参数应用到控件mGrid2

	}

	private void initDate() {
		year = String.valueOf(calendar.get(Calendar.YEAR));
		month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
		if (Integer.parseInt(month) < 10)
			month = "0" + month;
		day = String.valueOf(calendar.get(Calendar.DATE));

		hour = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
		minute = String.valueOf(calendar.get(Calendar.MINUTE));

		Float f_minute = Float.valueOf(minute);
		Float f_hour = Float.valueOf(hour);

		// 得出总的时间支出
		Float a = f_hour + f_minute / 60;

		// 转化为两位小数
		float b = (float) (Math.round(a * 100)) / 100;
		// (这里的100就是2位小数点,如果要其它位,如4位,这里两个100改成10000)

		hour = String.valueOf(b);
		
		expense_amount = b;
		
		income_amount = 24f;
		
		//得出余额，并转换为两位数
		a = 24 - b;
		b = (float) (Math.round(a * 100)) / 100;

		budget_balance_hour = String.valueOf(b);
		;

		if (Integer.parseInt(day) < 10)
			day = "0" + day;
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
	}

	private void loadingFormation() {

		myProcessBar = (MyProcessBar) findViewById(R.id.budget_pb);

		add_expense_quickly_btn = (Button) findViewById(R.id.add_expense_quickly_btn);
		add_expense_quickly_btn.setOnClickListener(this);
		
		month_tv = (TextView) findViewById(R.id.month_tv);
		day_tv = (TextView) findViewById(R.id.day_tv);
		
		income_amount_tv = (TextView) findViewById(R.id.income_amount_tv);
		expense_amount_tv = (TextView) findViewById(R.id.expense_amount_tv);
		budget_balance_amount_tv = (TextView) findViewById(R.id.budget_balance_amount_tv);

//		 myProcessBar.setProgress(expense_amount / income_amount);
		myProcessBar.reflashPorcess(expense_amount / income_amount);
		

	}

	private void initInfo() {
		month_tv.setText(month);
		day_tv.setText(day);
		// income_amount_tv.setText("T ： 24h" );
		//
		// expense_amount_tv.setText("¥ " + cursor.getDouble(0));
		//
		// budget_balance_amount_tv.setText("-¥ " + cursor.getDouble(0));

		// date_of_month_tv.setText(day);

		income_amount_tv.setText("T ：24.00h");

		expense_amount_tv.setText("T ：" + hour + "h");

		budget_balance_amount_tv.setText("T ： " + budget_balance_hour + "h");

		
//		expense_amount = (int) cursor.getDouble(0);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == add_expense_quickly_btn) 
		{
			LayoutInflater inflater = this.getLayoutInflater();
			View view1 = inflater.inflate(R.layout.main_activity, null);

			initView(view1);

			initDate();
			loadingFormation();
			initInfo();
		}
	}

}
