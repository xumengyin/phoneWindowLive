package com.example.voiceinput;

import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2017/6/14.
 */
public class Utils
{
	public static ArrayList<AppInfo> getAppList(Context context)
	{
		ArrayList<AppInfo> mlistAppInfo = new ArrayList<AppInfo>();
		PackageManager pm = context.getPackageManager(); // 获得PackageManager对象
		Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
		mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		// 通过查询，获得所有ResolveInfo对象.
		List<ResolveInfo> resolveInfos = pm
				.queryIntentActivities(mainIntent, 0);
		Collections.sort(resolveInfos, new ResolveInfo.DisplayNameComparator(pm));
		if (mlistAppInfo != null)
		{
			mlistAppInfo.clear();
			for (ResolveInfo reInfo : resolveInfos)
			{
				String activityName = reInfo.activityInfo.name; // 获得该应用程序的启动Activity的name
				String pkgName = reInfo.activityInfo.packageName; // 获得应用程序的包名
				String appLabel = (String) reInfo.loadLabel(pm); // 获得应用程序的Label
				Drawable icon = reInfo.loadIcon(pm); // 获得应用程序图标

				// 为应用程序的启动Activity 准备Intent
				Intent launchIntent = new Intent(Intent.ACTION_MAIN)
						.addCategory(Intent.CATEGORY_LAUNCHER)
						.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
				launchIntent.setComponent(new ComponentName(pkgName,
						activityName));
				// 创建一个AppInfo对象，并赋值
				AppInfo appInfo = new AppInfo();
				appInfo.alabel = appLabel;
				appInfo.packageName = pkgName;
				appInfo.appIcon = drawble2Bitmap(icon);
				appInfo.intent = launchIntent;
				mlistAppInfo.add(appInfo); // 添加至列表中
//				Logger.logd("Utils", appLabel + " activityName---" + activityName
//						+ " pkgName---" + pkgName);
			}
		}
		return mlistAppInfo;
	}

	public static Bitmap getWallpaper(Context ctx)
	{
		WallpaperManager wallpaperManager = WallpaperManager
				.getInstance(ctx);
		// 获取当前壁纸
		Drawable wallpaperDrawable = wallpaperManager.getDrawable();
		// 将Drawable,转成Bitmap
		Bitmap bm = ((BitmapDrawable) wallpaperDrawable).getBitmap();
		//Logger.logd("Utils", "wallpaper width:" + bm.getWidth() + "height:" + bm.getHeight());
		return bm;
	}

	public static Bitmap drawble2Bitmap(Drawable drawable)
	{
		Bitmap bitmap = Bitmap.createBitmap(
				drawable.getIntrinsicWidth(),
				drawable.getIntrinsicHeight(),
				drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
						: Bitmap.Config.RGB_565);
		Canvas canvas = new Canvas(bitmap);
		//canvas.setBitmap(bitmap);
		drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
		drawable.draw(canvas);
		return bitmap;
	}
}
