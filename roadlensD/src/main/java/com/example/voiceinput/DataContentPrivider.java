package com.example.voiceinput;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/6/27.
 */
public class DataContentPrivider extends ContentProvider
{
	private static final String TAG = "DataContentPrivider";

	@Override
	public boolean onCreate()
	{
		return true;
	}

	@Nullable
	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder)
	{
		Log.e(TAG, "contentprivider query: ");
		getContext().getContentResolver().notifyChange(uri,null);
		return null;
	}

	@Nullable
	@Override
	public String getType(Uri uri)
	{
		Log.e(TAG, "getType: "+uri );
		return null;
	}

	@Nullable
	@Override
	public Uri insert(Uri uri, ContentValues values)
	{
		Log.e(TAG, "contentprivider insert: ");
		return null;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs)
	{
		Log.e(TAG, "contentprivider delete: ");
		return 0;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs)
	{
		Log.e(TAG, "contentprivider update: ");
		return 0;
	}

	@NonNull
	@Override
	public ContentProviderResult[] applyBatch(ArrayList<ContentProviderOperation> operations) throws OperationApplicationException
	{
		Log.e(TAG, "contentprivider applyBatch: ");
		return super.applyBatch(operations);
	}

	@Override
	public int bulkInsert(Uri uri, ContentValues[] values)
	{
		Log.e(TAG, "contentprivider bulkInsert: ");
		return super.bulkInsert(uri, values);
	}

	@Nullable
	@Override
	public Bundle call(String method, String arg, Bundle extras)
	{
		Bundle bundle = new Bundle();
		int value = 0;
		Log.e(TAG, "contentprivider call: " + method + "arg:" + arg);
		switch (method)
		{
			case method1:
				value = SharePerferenceUtil.getInstance(getContext()).getTestName(0);
				break;
			case method2:
				SharePerferenceUtil.getInstance(getContext()).setTestName(extras.getInt("test", 0));
				value = SharePerferenceUtil.getInstance(getContext()).getTestName(0);
				break;
		}
		bundle.putInt("test", value);

		return bundle;
	}

	final String method1 = "getTestName";
	final String method2 = "setTestName";
}
