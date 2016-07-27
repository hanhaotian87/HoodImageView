package com.yzfarm.hoodimageview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;

public class HoodImageView extends ImageView {
	
	private int state = 0x000000;
	private int paddingLeft,paddingRight,paddingTop;
	private int hoodHeight;
	private int space;
	
	private int vipResId;
	private int updateResId;
	private int dlcResId;
	private Bitmap vipBitMap;
	private Bitmap updateBitMap;
	private Bitmap dlcBitMap;

	public HoodImageView(Context context) {
		super(context);
		setup(context);
	}
	public HoodImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setup(context);
	}
	public HoodImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setup(context);
	}
	
	private void setup(Context context){
		vipResId = R.drawable.vip;
		updateResId = R.drawable.update;
		dlcResId = R.drawable.dlc;
		vipBitMap = BitmapFactory.decodeResource(getResources(), vipResId);
		updateBitMap = BitmapFactory.decodeResource(getResources(), updateResId);
	    dlcBitMap = BitmapFactory.decodeResource(getResources(), dlcResId);
	}
	
	public int getState() {
		return state;
	}
	
	/**
	 * @param vip 会员免费标记
	 * @param update 有更新标记
	 * @param dlc 有dlc更新标记
	 * 
	 * */
	public void setState(boolean vip,boolean update,boolean dlc) {
		if(vip){
			state = state | 0x100;
		}
		if(update){
			state = state | 0x010;
		}
		if(dlc){
			state  = state | 0x001;
		}
	}
	
	/**
	 * @param hoodHeight 蒙条高度
	 * @param space 两个标记之间间距
	 * @param paddingLeft 蒙条左边距
	 * @param paddingRight 蒙条右边距
	 * @param paddingTop 蒙条上边距
	 * 
	 * */
	public void setHood(int hoodHeight,int space,int paddingLeft,int paddingRight,int paddingTop){
		this.hoodHeight = hoodHeight;
		this.space = space;
		this.paddingLeft = paddingLeft;
		this.paddingTop = paddingTop;
		this.paddingRight = paddingRight;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		switch(state){
		case 0x000000:
			return;
		case 0x000001:
			addLabel(canvas,dlcBitMap);
			break;
		case 0x000010:
			addLabel(canvas, updateBitMap);
			break;
		case 0x000100:
			addLabel(canvas, vipBitMap);
			break;
		case 0x000011:
			addLabel(canvas, updateBitMap, dlcBitMap);
			break;
		case 0x000101:
			addLabel(canvas, vipBitMap, dlcBitMap);
			break;
		case 0x000110:
			addLabel(canvas, vipBitMap, updateBitMap);
			break;
		case 0x000111:
			addLabel(canvas, vipBitMap, updateBitMap, dlcBitMap);
			break;
			default:
				break;
		    
		}
		
	}
	
	private void addHood(Canvas canvas){
		Paint hoodP = new Paint();
		hoodP.setColor(0x80000000);
		canvas.drawRect(paddingLeft, paddingTop, getWidth()-paddingLeft-paddingRight, hoodHeight, hoodP);
	}
	
	private void addLabel(Canvas canvas,Bitmap bitmap){
		//@TODO考虑使用matrix调整图像大小
		addHood(canvas);
		Paint p = new Paint();
		int bWidth = bitmap.getWidth();
		int bHeight = bitmap.getHeight();
		int left = (getWidth() - bWidth)/2;
		int top = (hoodHeight - bHeight)/2;
		canvas.drawBitmap(bitmap, left, top, p);
	}
	
    private void addLabel(Canvas canvas,Bitmap bitmap1,Bitmap bitmap2){
    	addHood(canvas);
    	Paint p = new Paint();
		int bWidth = bitmap1.getWidth();
		int bHeight = bitmap1.getHeight();
		int left1 = (getWidth() - 2*bWidth - space)/2;
		int left2 = (getWidth() - 2*bWidth - space)/2 + bWidth + space;
		int top = (hoodHeight - bHeight)/2;
		canvas.drawBitmap(bitmap1, left1, top, p);
		canvas.drawBitmap(bitmap2, left2, top, p);
	}
    
    private void addLabel(Canvas canvas,Bitmap bitmap1,Bitmap bitmap2,Bitmap bitmap3){
    	addHood(canvas);
    	Paint p = new Paint();
		int bWidth = bitmap1.getWidth();
		int bHeight = bitmap1.getHeight();
		int left1 = (getWidth() - 3*bWidth - 2*space)/2;
		int left2 = (getWidth() - 3*bWidth - 2*space)/2 + bWidth + space;
		int left3 = (getWidth() - 3*bWidth - 2*space)/2 + 2*bWidth + 2*space;
		int top = (hoodHeight - bHeight)/2;
		canvas.drawBitmap(bitmap1, left1, top, p);
		canvas.drawBitmap(bitmap2, left2, top, p);
		canvas.drawBitmap(bitmap3, left3, top, p);
	}

}
