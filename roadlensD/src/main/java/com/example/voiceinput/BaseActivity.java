package com.example.voiceinput;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.voiceinput.Http.HttpHelper;

public class BaseActivity extends AppCompatActivity
{
	HttpHelper mHttphelper;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		mHttphelper=new HttpHelper();

	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		mHttphelper.cancel();
	}
}
