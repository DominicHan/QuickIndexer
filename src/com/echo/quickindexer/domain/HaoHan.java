package com.echo.quickindexer.domain;

import com.echo.quickindexer.util.PinyinUtil;

public class HaoHan implements Comparable<HaoHan>{

	private String name;
	private String pinyin;
	
	public HaoHan(String name) {
		super();
		this.name = name;
		this.pinyin = PinyinUtil.getPinyin(name);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPinyin() {
		return pinyin;
	}
	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	@Override
	public int compareTo(HaoHan another) {
		return this.pinyin.compareTo(another.pinyin);
	}
	
}
