package com.echo.quickindexer;

import java.util.ArrayList;
import java.util.Collections;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

import com.echo.quickindexer.adapter.HaoHanAdapter;
import com.echo.quickindexer.domain.HaoHan;
import com.echo.quickindexer.ui.QuickIndexBar;
import com.echo.quickindexer.ui.QuickIndexBar.OnLetterUpdateListener;

public class MainActivity extends Activity {

	private ListView lv;
	private ArrayList<HaoHan> persons;
	private TextView tv_center;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
		tv_center = (TextView) findViewById(R.id.tv_center);
		
		// View
		lv = (ListView) findViewById(R.id.lv);
		
		// Model
		persons = new ArrayList<HaoHan>();
		fillAndSortData(persons);
		
		// Controller
		lv.setAdapter(new HaoHanAdapter(persons, this));
		
		QuickIndexBar bar = (QuickIndexBar) findViewById(R.id.bar);
		bar.setOnLetterUpdateListener(new OnLetterUpdateListener() {
			
			@Override
			public void onLetterUpdate(String letter) {
//				Utils.showToast(MainActivity.this, letter);		
				showLetter(letter);
				
				for (int i = 0; i < persons.size(); i++) {
					String l = persons.get(i).getPinyin().charAt(0) + "";
					if(TextUtils.equals(letter, l)){
						// 找到第一个首字母是letter条目.
						lv.setSelection(i);
						break;
					}
				}
			}
		});
		

	}
	
	private Handler mHandler = new Handler();

	/**
	 * 在屏幕中间显示一个字母提示
	 * @param letter
	 */
	protected void showLetter(String letter) {
		tv_center.setText(letter);
		tv_center.setVisibility(View.VISIBLE);
		
		// 移除之前的延时操作
		mHandler.removeCallbacksAndMessages(null);
		// 延时两秒消除
		mHandler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				tv_center.setVisibility(View.GONE);
			}
			
		}, 2000);
	}

	/**
	 * 填充并排序数据
	 * @param persons
	 */
	private void fillAndSortData(ArrayList<HaoHan> persons) {
		// 填充
		for (int i = 0; i < Cheeses.NAMES.length; i++) {
			String s = Cheeses.NAMES[i];
			persons.add(new HaoHan(s));
		}
		
		// 排序
		Collections.sort(persons);
	}

}
