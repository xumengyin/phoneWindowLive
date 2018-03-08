package com.example.testcontentprivader;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity
{

	ContentResolver mResolver;
	String au = "com.example.voiceinput.DataContentPrivider";
	Uri mUri = Uri.parse("content://" + au);
	private static final String TAG = "SecondAPP";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mResolver = getContentResolver();
		mResolver.registerContentObserver(mUri, true, new ContentObserver(new Handler())
		{
			@Override
			public boolean deliverSelfNotifications()
			{
				return super.deliverSelfNotifications();
			}

			@Override
			public void onChange(boolean selfChange)
			{
				Log.e(TAG, "onChange: " + selfChange);
				super.onChange(selfChange);
			}

			@Override
			public void onChange(boolean selfChange, Uri uri)
			{
				Log.e(TAG, "onChange: uri" + uri + "--selfchange:" + selfChange);
				super.onChange(selfChange, uri);
			}
		});
	}

	public void add(View view)
	{
		mResolver.insert(mUri, new ContentValues());
	}

	public void delete(View view)
	{
		mResolver.delete(mUri, "a", null);
	}

	public void update(View view)
	{
		mResolver.update(mUri, new ContentValues(), "a", null);
	}

	public void query(View view)
	{
		mResolver.query(mUri, null, null, null, null);
	}

	public void applyBath(View view)
	{
		ArrayList<ContentProviderOperation> list = new ArrayList<>();
		///list.add(ContentProviderOperation.newUpdate(mUri).withValues(new ContentValues()).build());
		list.add(ContentProviderOperation.newInsert(mUri).withValues(new ContentValues()).build());
		list.add(ContentProviderOperation.newDelete(mUri).build());
		try
		{
			mResolver.applyBatch(au, list);
		} catch (RemoteException e)
		{
			e.printStackTrace();
		} catch (OperationApplicationException e)
		{
			e.printStackTrace();
		}
	}

	private void toast(String str)
	{
		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
	}

	public void getShareperence(View view)
	{
		int value = mResolver.call(mUri, method1, null, null).getInt("test");
		Log.e(TAG, "getShareperence: " + value);
		toast("getShareperence: " + value);
	}

	public void setSharePerence(View view)
	{
		Bundle bundle = new Bundle();
		bundle.putInt("test", new Random().nextInt(200));
		int value = mResolver.call(mUri, method2, null, bundle).getInt("test");
		toast("getShareperence: " + value);
	}

	final String method1 = "getTestName";
	final String method2 = "setTestName";
}
