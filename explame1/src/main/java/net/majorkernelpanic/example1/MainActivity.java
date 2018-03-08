package net.majorkernelpanic.example1;

import net.majorkernelpanic.streaming.Session;
import net.majorkernelpanic.streaming.SessionBuilder;
import net.majorkernelpanic.streaming.gl.SurfaceView;
import net.majorkernelpanic.streaming.rtsp.RtspServer;
import net.majorkernelpanic.streaming.video.VideoQuality;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.format.Formatter;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * A straightforward example of how to use the RTSP server included in
 * libstreaming.
 */
public class MainActivity extends Activity implements Session.Callback
{

	private final static String TAG = "MainActivity";

	private SurfaceView mSurfaceView;
	private MediaProjectionManager mMediaProjectionManager;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.activity_main);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		mSurfaceView = (SurfaceView) findViewById(R.id.surface);

		// Sets the port of the RTSP server to 1234
		Editor editor = PreferenceManager.getDefaultSharedPreferences(this)
				.edit();
		editor.putString(RtspServer.KEY_PORT, String.valueOf(1234));
		editor.commit();


//		SessionBuilder.getInstance().setSurfaceView(mSurfaceView)
//				.setPreviewOrientation(90).setContext(getApplicationContext())
//				.setAudioEncoder(SessionBuilder.AUDIO_NONE)
//				.setVideoEncoder(SessionBuilder.VIDEO_H264)
//				.setMediaProjection(mediaProjection);
//
//		// Starts the RTSP server
//		this.startService(new Intent(this, RtspServer.class));
		Log.d(TAG, "IP:" + IP());
		startMediaProject();
	}

	static MediaProjection mediaProjection;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		mediaProjection = mMediaProjectionManager.getMediaProjection(resultCode, data);
		// Configures the SessionBuilder
		SessionBuilder.getInstance()
				.setSurfaceView(mSurfaceView)
				.setContext(getApplicationContext())
				.setAudioEncoder(SessionBuilder.AUDIO_NONE)
				.setVideoEncoder(SessionBuilder.VIDEO_H264)
				.setVideoQuality(new VideoQuality(352,640,15,500000))
				.setMediaProjection(mediaProjection)
				.setCallback(this);

		// Starts the RTSP server
		this.startService(new Intent(this, RtspServer.class));

	}

	int REQUEST_CODE = 0x8888;

	@SuppressLint("NewApi")
	private void startMediaProject()
	{
		mMediaProjectionManager = (MediaProjectionManager) getSystemService(MEDIA_PROJECTION_SERVICE);
		Intent captureIntent = mMediaProjectionManager
				.createScreenCaptureIntent();
		startActivityForResult(captureIntent, REQUEST_CODE);
	}

	private String IP()
	{
		String ipAddress = null;
		WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		int address = wifiInfo.getIpAddress();
		ipAddress = "rtsp://" + Formatter.formatIpAddress(address) + ":1234";
		Toast.makeText(this, ipAddress, Toast.LENGTH_LONG).show();
		return ipAddress;
	}

	@Override
	public void onBitrateUpdate(long bitrate)
	{
		Log.e(TAG, "onBitrateUpdate: ");
	}

	@Override
	public void onSessionError(int reason, int streamType, Exception e)
	{
		Log.e(TAG, "onSessionError: ");
	}

	@Override
	public void onPreviewStarted()
	{
		Log.e(TAG, "onPreviewStarted: ");
	}

	@Override
	public void onSessionConfigured()
	{
		Log.e(TAG, "onSessionConfigured: ");
	}

	@Override
	public void onSessionStarted()
	{
		Log.e(TAG, "onSessionStarted: ");
		//Toast.makeText(Myapp.APP,"开始推屏!!",Toast.LENGTH_SHORT).show();
		final ProgressDialog dialog;
				dialog=new ProgressDialog(MainActivity.this);
				dialog.setMessage("...");
				dialog.show();

		new Handler().postDelayed(new Runnable()
		{
			@Override
			public void run()
			{
				if(dialog!=null)
					dialog.dismiss();
				moveTaskToBack(true);
			}
		},3000);
	}

	@Override
	public void onSessionStopped()
	{
		Log.e(TAG, "onSessionStopped: ");
	}
}
