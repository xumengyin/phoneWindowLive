package net.majorkernelpanic.example1;

import android.app.Application;

/**
 * Created by Administrator on 2017/7/6.
 */
public class Myapp extends Application
{
	public static Myapp APP;
	@Override
	public void onCreate()
	{
		super.onCreate();
		APP=this;
	}
}
