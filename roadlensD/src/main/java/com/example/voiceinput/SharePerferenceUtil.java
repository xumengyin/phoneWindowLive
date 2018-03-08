package com.example.voiceinput;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2017/7/21.
 */
public class SharePerferenceUtil
{
	final static String PERFERENCE_KRY = "test contentprocider";
	final static String KEY_NAME = "name";
	static SharePerferenceUtil instance;
	SharedPreferences sharedPreferences;

	private SharePerferenceUtil(Context ctx)
	{
		sharedPreferences = ctx.getSharedPreferences(PERFERENCE_KRY, Context.MODE_PRIVATE);
	}

	public static SharePerferenceUtil getInstance(Context ctx)
	{
		if (instance == null)
		{
			instance = new SharePerferenceUtil(ctx);
		}
		return instance;
	}

	public int getTestName(int value)
	{
		return sharedPreferences.getInt(KEY_NAME, value);
	}

	public void setTestName(int name)
	{
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putInt(KEY_NAME, name).commit();
	}
}
