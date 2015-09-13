package com.echo.quickindexer.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.echo.quickindexer.R;
import com.echo.quickindexer.domain.HaoHan;


public class HaoHanAdapter extends BaseAdapter {

	private ArrayList<HaoHan> persons = new ArrayList<HaoHan>();
	private final Context context;
	
	public HaoHanAdapter(ArrayList<HaoHan> persons, Context context) {
		super();
		this.persons = persons;
		this.context = context;
	}
	@Override
	public int getCount() {
		return persons.size();
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view;
		if(convertView == null){
			view = View.inflate(context, R.layout.item_person, null);
		}else {
			view = convertView;
		}
		
		TextView tv_index = (TextView) view.findViewById(R.id.tv_index);
		TextView tv_name = (TextView) view.findViewById(R.id.tv_name);

		HaoHan haoHan = persons.get(position);
		
		// 当前首字母
		String currentStr = haoHan.getPinyin().charAt(0) + "";
		
		String indexStr = null;
		// 如果是第一个, 直接显示
		if(position == 0){
			indexStr = currentStr;
		}else {
			// 判断当前首字母和上一个条目的首字母是否一致, 不一致时候显示.
			String lastStr = persons.get(position - 1).getPinyin().charAt(0) + "";
			if(!TextUtils.equals(lastStr, currentStr)){
				// 不一致时候赋值indexStr
				indexStr = currentStr;
			}
			
		}
		
		tv_index.setVisibility(indexStr != null ?  View.VISIBLE : View.GONE);
		tv_index.setText(currentStr);
		tv_name.setText(haoHan.getName());
		
		return view;
	}
	

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

}
