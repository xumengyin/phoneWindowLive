package com.example.voiceinput;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.Fade;
import android.transition.Scene;
import android.transition.Slide;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

public class TestActivity extends BaseActivity
{
	ContentResolver mResolver;
	Uri mUri = Uri.parse("content://com.example.voiceinput.DataContentPrivider");
	FrameLayout mRootView;
	private static final String TAG = "TestActivity";
	@RequiresApi(api = Build.VERSION_CODES.KITKAT)
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);
		mResolver = getContentResolver();
		mRootView = (FrameLayout) findViewById(R.id.root_view);


	}

	public void getShare(View view)
	{
		int value = SharePerferenceUtil.getInstance(this).getTestName(0);
		Toast.makeText(this, "value" + value, Toast.LENGTH_SHORT).show();
	}

	@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
	public void add(View view)
	{
		//mResolver.insert(mUri,new ContentValues());
//		Scene scene = Scene.getSceneForLayout(mRootView, R.layout.scece2, this);
//		TransitionManager.go(scene, new Slide());

	}

	@RequiresApi(api = Build.VERSION_CODES.KITKAT)
	public void delete(View view)
	{
		//mResolver.delete(mUri,"a",null);
		Scene scene = Scene.getSceneForLayout(mRootView, R.layout.scece1, this);
		TransitionManager.go(scene, new ChangeBounds());
	}

	public void update(View view)
	{
		mResolver.update(mUri, new ContentValues(), "a", null);
	}

	public void query(View view)
	{
		mResolver.query(mUri, null, null, null, null);
	}
}
