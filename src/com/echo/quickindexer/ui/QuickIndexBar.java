package com.echo.quickindexer.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 快速索引栏
 * @author poplar
 *
 */
public class QuickIndexBar extends View {
	
	private OnLetterUpdateListener onLetterUpdateListener;
	
	public interface OnLetterUpdateListener{
		void onLetterUpdate(String letter);
	}
	
	public OnLetterUpdateListener getOnLetterUpdateListener() {
		return onLetterUpdateListener;
	}

	public void setOnLetterUpdateListener(
			OnLetterUpdateListener onLetterUpdateListener) {
		this.onLetterUpdateListener = onLetterUpdateListener;
	}
	private static final String[] LETTERS = new String[]{
		"A", "B", "C", "D", "E", "F",
		"G", "H", "I", "J", "K", "L",
		"M", "N", "O", "P", "Q", "R",
		"S", "T", "U", "V", "W", "X",
		"Y", "Z"
	};
	
	private Paint paint;

	// 单元格宽度
	private int cellWidth;

	// 单元格高度
	private float cellHeight;


	public QuickIndexBar(Context context) {
		this(context, null);
	}

	public QuickIndexBar(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public QuickIndexBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// 创建一个抗锯齿的画笔
		paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		// 画笔文本加粗
		paint.setTypeface(Typeface.DEFAULT_BOLD);
		// 颜色
		paint.setColor(Color.BLACK);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		
		// 遍历26个英文字母, 计算坐标, 进行绘制
		for (int i = 0; i < LETTERS.length; i++) {
			String letter = LETTERS[i];
			
			// 计算x坐标
			float x = cellWidth * 0.5f - paint.measureText(letter) * 0.5f;
			// 计算y坐标
			Rect bounds = new Rect();
			// 获取文本的矩形区域
			paint.getTextBounds(letter, 0, letter.length(), bounds);
			
			float y = cellHeight * 0.5f + bounds.height() * 0.5f + i * cellHeight;
			
			// 把当前被选中的字母索引 置为灰色, 否则白色
			paint.setColor(i == lastIndex ? Color.WHITE : Color.BLACK);
			
			// 绘制文本
			canvas.drawText(letter, x, y, paint);
		}
	}
	private int lastIndex = -1;
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		float y;
		int currentIndex;
		
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// 获取被点击到的字母索引
			y = event.getY();
			// 根据y值, 计算当前按下的字母位置
			currentIndex = (int) (y / cellHeight);
			if(currentIndex != lastIndex){
				if(currentIndex >= 0 && currentIndex < LETTERS.length){
					String letter = LETTERS[currentIndex];
					if(onLetterUpdateListener != null){
						onLetterUpdateListener.onLetterUpdate(letter);
					}
					System.out.println("letter: " + letter);
					// 记录上一次触摸的字母
					lastIndex = currentIndex;
				}
			}
			
			break;
		case MotionEvent.ACTION_MOVE:
			// 获取被点击到的字母索引
			y = event.getY();
			// 根据y值, 计算当前按下的字母位置
			currentIndex = (int) (y / cellHeight);
			if(currentIndex != lastIndex){
				if(currentIndex >= 0 && currentIndex < LETTERS.length){
					String letter = LETTERS[currentIndex];
					if(onLetterUpdateListener != null){
						onLetterUpdateListener.onLetterUpdate(letter);
					}
					System.out.println("letter: " + letter);
					// 记录上一次触摸的字母
					lastIndex = currentIndex;
				}
			}
			
			break;
		case MotionEvent.ACTION_UP:
			lastIndex = -1;
			break;
		default:
			break;
		}
		invalidate();
		
		return true;
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int mHeight = getMeasuredHeight();
		cellWidth = getMeasuredWidth();
		cellHeight = mHeight * 1.0f / LETTERS.length;
	}
	
	

}
