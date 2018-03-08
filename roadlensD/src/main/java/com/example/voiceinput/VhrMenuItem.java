package com.example.voiceinput;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import me.itangqi.waveloadingview.WaveLoadingView;


/**
 * Created by Administrator on 2017/7/12.
 */
public class VhrMenuItem extends BaseCardView implements View.OnClickListener
{
	WaveLoadingView waveView;
	public VhrMenuItem(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		init();
	}

	private void init()
	{
		LayoutInflater.from(getContext()).inflate(R.layout.vhr_menu__layout, this, true);
		waveView= (WaveLoadingView) findViewById(R.id.waveLoadingView);
		this.setOnClickListener(this);

	}

	@Override
	public void onClick(View v)
	{
		//Toast.makeText("vhr");
	}
}
