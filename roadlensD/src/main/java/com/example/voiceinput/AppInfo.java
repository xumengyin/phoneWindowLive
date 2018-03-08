package com.example.voiceinput;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/6/14.
 */
public class AppInfo implements Parcelable
{
	public String appName="";
	public String alabel;
	public String packageName="";
	public String versionName="";
	public Intent intent;
	public int versionCode=0;
	public Bitmap appIcon=null;

	@Override
	public int describeContents()
	{
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags)
	{
		dest.writeString(this.appName);
		dest.writeString(this.alabel);
		dest.writeString(this.packageName);
		dest.writeString(this.versionName);
		dest.writeParcelable(this.intent, flags);
		dest.writeInt(this.versionCode);
		dest.writeParcelable(this.appIcon, flags);
	}

	public AppInfo()
	{
	}

	protected AppInfo(Parcel in)
	{
		this.appName = in.readString();
		this.alabel = in.readString();
		this.packageName = in.readString();
		this.versionName = in.readString();
		this.intent = in.readParcelable(Intent.class.getClassLoader());
		this.versionCode = in.readInt();
		this.appIcon = in.readParcelable(Bitmap.class.getClassLoader());
	}

	public static final Creator<AppInfo> CREATOR = new Creator<AppInfo>()
	{
		@Override
		public AppInfo createFromParcel(Parcel source)
		{
			return new AppInfo(source);
		}

		@Override
		public AppInfo[] newArray(int size)
		{
			return new AppInfo[size];
		}
	};
}
