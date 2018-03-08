package com.example.voiceinput;

import android.animation.AnimatorInflater;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/6/26.
 */
public class MainItemView extends BaseCardView implements View.OnClickListener
{
	private static final String TAG = "MainItemView";
	static int defaultBg ;
	Drawable mBgColor;
	String mText;
	Drawable mImageViewId;
	ImageView mImageView;
	TextView mTextView;
	int mTextColor;
	int mTextSize;
	int foregroundColor;
	int[] attr = new int[]{R.attr.colorControlHighlight};
	public MainItemView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		initStyle(context, attrs);
		init();
	}

	private void initStyle(Context context, AttributeSet attrs)
	{
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.main_itemView_style);
		mBgColor = a.getDrawable(R.styleable.main_itemView_style_android_background);
		mText = a.getString(R.styleable.main_itemView_style_android_text);
		mImageViewId = a.getDrawable(R.styleable.main_itemView_style_imageview);
		mTextColor = a.getInt(R.styleable.main_itemView_style_android_textColor, Color.WHITE);
		mTextSize = a.getDimensionPixelSize(R.styleable.main_itemView_style_android_textSize, 12);
		a.recycle();
		defaultBg=getResources().getColor(R.color.colorPrimary);
//		TypedArray typedArray = context.obtainStyledAttributes(attr);
//		foregroundColor = typedArray.getResourceId(0, 0);
//		typedArray.recycle();
	}


	private void init()
	{
		LayoutInflater.from(getContext()).inflate(R.layout.main_item_layout, this, true);
		mImageView = (ImageView) findViewById(R.id.main_item_img);
		mTextView = (TextView) findViewById(R.id.main_item_text);
		mImageView.setImageDrawable(mImageViewId);
		mTextView.setText(mText);
		mTextView.setTextColor(mTextColor);
		//mTextView.setShadowLayer(10,10,10,getResources().getColor(R.color.colorAccent));
		mTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
		this.setCardElevation(10);
		//this.setStateListAnimator(AnimatorInflater.loadStateListAnimator(getContext(), R.animator.main_item_touch));
			//this.setBackgroundResource(R.drawable.main_item_bg);
		//setUseCompatPadding(true);
		//this.setForeground(new ColorDrawable(foregroundColor));
		this.setCardBackgroundColor(defaultBg);
//		LinearLayout.LayoutParams params = (LayoutParams) this.getLayoutParams();
//		params.gravity = Gravity.CENTER;
//		this.setLayoutParams(params);
		this.setOnClickListener(this);
//		setPadding(10,10,10,10);
	}

	ValueAnimator animator;
	float animatorValue = 0.1f;

	private void initAnimator(boolean isEnlarge)
	{
		if (!isEnlarge)
		{
			animator = ValueAnimator.ofFloat(1.0f, 1.0f - animatorValue);
		} else
		{
			animator = ValueAnimator.ofFloat(1.0f - animatorValue, 1.0f);
		}
		animator.setDuration(100);
		animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
		{
			@Override
			public void onAnimationUpdate(ValueAnimator animation)
			{
				MainItemView.this.setScaleX((Float) animation.getAnimatedValue());
				MainItemView.this.setScaleY((Float) animation.getAnimatedValue());
			}
		});
		animator.start();
	}

//	@Override
//	public boolean onTouchEvent(MotionEvent event)
//	{
//		if (event.getAction() == MotionEvent.ACTION_DOWN)
//		{
//			Log.e(TAG, "onTouchEvent: ACTION_DOWN");
//			initAnimator(false);
//		} else if (event.getAction() == MotionEvent.ACTION_UP)
//		{
//			Log.e(TAG, "onTouchEvent: ACTION_UP");
//			initAnimator(true);
//			Log.e(TAG, "onClick: " + mTextView.getText());
//			Toast.makeText(getContext(), mTextView.getText(), Toast.LENGTH_SHORT).show();
//			if (callBack != null)
//				callBack.itemClick(this);
//		} else if (event.getAction() == MotionEvent.ACTION_CANCEL)
//		{
//			Log.e(TAG, "onTouchEvent: ACTION_cancel");
//			initAnimator(true);
//		}
//		return true;
//	}


	ItemClickCallBack callBack;

	public void setItemClick(ItemClickCallBack callBack)
	{
		this.callBack = callBack;
	}

	@Override
	public void onClick(View v)
	{
		Toast.makeText(getContext(), mTextView.getText(), Toast.LENGTH_SHORT).show();
	}


}
