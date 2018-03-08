package com.example.voiceinput;

import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.transition.Scene;
import android.transition.TransitionManager;
import android.util.Log;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity implements VoiceView.Callback
{
	VoiceView mVoiceView;
	FrameLayout mRootView;
	private static final String TAG = "xu";
	@RequiresApi(api = Build.VERSION_CODES.KITKAT)
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//findViewById(android.R.id.content).setBackground(new BitmapDrawable(Utils.getWallpaper(this)));
//		mVoiceView = (VoiceView) findViewById(R.id.voiceView);
//		mVoiceView.setCallBack(this);
		//CircleImageView
	}

	
	@Override
	public void onVoiceStart()
	{
		Log.e(TAG, "onVoiceStart:" );
	}

	@Override
	public void onVoiceEnd()
	{
		Log.e(TAG, "onVoiceEnd: " );
	}

	@Override
	public void onVoiceRlease()
	{
		Log.e(TAG, "onVoiceRlease: " );
	}
}
