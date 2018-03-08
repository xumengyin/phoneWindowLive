package com.example.voiceinput;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2017/7/12.
 */
public abstract class BaseCardView extends CardView
{
	//protected abstract void initView();
	public BaseCardView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	public interface ItemClickCallBack
	{
		void itemClick(View view);
	}
}
