package com.example.voiceinput;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.Size;
import android.hardware.display.DisplayManager;
import android.media.MediaRecorder;
import android.media.MediaRecorder.OnErrorListener;
import android.media.MediaRecorder.OnInfoListener;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.support.annotation.RequiresApi;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class RecordActivity extends Activity implements SurfaceHolder.Callback,
		OnInfoListener
{

	String tag = "xu";
	Camera camera;
	Camera camera2;
	Parameters parameters;
	Parameters parameters2;
	SurfaceView mSurfaceView;
	// SurfaceView mSurfaceView2;
	SurfaceHolder mHolder;
	// SurfaceHolder mHolder2;
	MediaRecorder mainRecorder, mainRecorder2;
	private static final int PIC_1080_WIDETH = 1920;
	private static final int PIC_1080_HIGHT = 1080;
	private static final int PIC_720_WIDETH = 1280;
	private static final int PIC_720_HIGHT = 720;
	ImageView mImageView;
	private int mScreenDensity;
	private MediaProjectionManager mMediaProjectionManager;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_record);
		//initView();
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		mScreenDensity = metrics.densityDpi;
		initView();
		initSocket();
	}

	MediaProjection mediaProjection;

	@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		mediaProjection = mMediaProjectionManager.getMediaProjection(resultCode, data);
		// Configures the SessionBuilder


		// Starts the RTSP server

		//moveTaskToBack(true);
		//record2();
		//record();
		recorder3();
	}

	int REQUEST_CODE = 0x8888;


	@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
	private void startMediaProject()
	{
		mMediaProjectionManager = (MediaProjectionManager) getSystemService(MEDIA_PROJECTION_SERVICE);
		Intent captureIntent = mMediaProjectionManager
				.createScreenCaptureIntent();
		startActivityForResult(captureIntent, REQUEST_CODE);
	}

	private void stopRecord()
	{
		new Thread(new Runnable()
		{

			@Override
			public void run()
			{
				isRecord = false;
				if (mainRecorder != null)
				{
					Log.d(tag, "stop1");
					mainRecorder.setOnErrorListener(null);
					Log.d(tag, "stop2");
					mainRecorder.stop();
					Log.d(tag, "stop3");
					mainRecorder.release();
					Log.d(tag, "stop4");
					mainRecorder = null;
					Log.d(tag, "stop2");
				}
				if (mainRecorder2 != null)
				{
					Log.d(tag, "stop1");
					mainRecorder2.setOnErrorListener(null);
					Log.d(tag, "stop2");
					mainRecorder2.stop();
					Log.d(tag, "stop3");
					mainRecorder2.release();
					Log.d(tag, "stop4");
					mainRecorder2 = null;
					Log.d(tag, "stop2");
				}
				// camera.lock();
				Log.d(tag, "stop5");
			}
		}).start();
	}

	private boolean isRecord = false;

	private File getoutputFile()
	{
		File mainRecorderFile = new File(Environment
				.getExternalStorageDirectory().getAbsoluteFile() + "/atest");
		if (!mainRecorderFile.exists())
		{
			mainRecorderFile.mkdirs();
		}
		File file = new File(mainRecorderFile, System.currentTimeMillis()
				+ "test111.mp4");
		return file;
	}
	private void recorder3()
	{
		if (isRecord)
		{
			return;
		}
		isRecord = true;
		// camera.stopPreview();
		camera.unlock();
		mainRecorder = new MediaRecorder();
		mainRecorder.setCamera(camera);
		mainRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
		mainRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		mainRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
		mainRecorder.setPreviewDisplay(mSurfaceView.getHolder().getSurface());
		mainRecorder.setVideoSize(1280,720);
		mainRecorder.setVideoFrameRate(20);
		mainRecorder.setVideoEncodingBitRate(2000000);
		mainRecorder.setOutputFile(getoutputFile().getAbsolutePath());
		mainRecorder.setMaxDuration(3000);
		try
		{
			mainRecorder.prepare();
		} catch (IllegalStateException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mainRecorder.start();
	}
	private void record()
	{
		if (isRecord)
		{
			return;
		}
		isRecord = true;
		// camera.stopPreview();
		camera.unlock();
		mainRecorder = new MediaRecorder();
		mainRecorder.setCamera(camera);
		mainRecorder.setOnInfoListener(this);

		mainRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
		mainRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		//mainRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
		//mainRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
		//mainRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		//mainRecorder.setMaxDuration(30 * 1000);
		mainRecorder.setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR);
		mainRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
		//mainRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
		mainRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		mainRecorder.setMaxDuration(0);
		mainRecorder.setMaxFileSize(0);
		// mainRecorder.setOnInfoListener(null);
		// mainRecorder.setOnErrorListener(null);
		// 设置视频录制的分辨率。必须放在设置编码和格式的后面，否则报错
		mainRecorder.setVideoSize(PIC_720_WIDETH, PIC_720_HIGHT);
		// mainRecorder
		// .setOrientationHint(90);
		// 设置录制的视频帧率。必须放在设置编码和格式的后面，否则报错
		// mainRecorder.setVideoFrameRate(30);
		// 设置视频文件输出的路径

		//mainRecorder.setPreviewDisplay(mHolder.getSurface());
		//mainRecorder.setOutputFile(file.getAbsolutePath());
		mainRecorder.setOutputFile(getStreamFd());
		//mainRecorder.setOutputFile(getoutputFile().getAbsolutePath());
		// wb test
		// mainRecorder.setVideoEncodingBitRate(8 * 1024 * 1024);
		mainRecorder.setVideoEncodingBitRate(2 * 1024 * 1024);
		mainRecorder.setOnErrorListener(new OnErrorListener()
		{

			@Override
			public void onError(MediaRecorder mr, int what, int extra)
			{
				Log.d(tag, "what:" + what + "--- extra" + extra);

			}
		});
		try
		{
			mainRecorder.prepare();
		} catch (IllegalStateException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mainRecorder.start();
	}
	private FileDescriptor getStreamFd() {
		ParcelFileDescriptor[] pipe=null;

		try {
			pipe=ParcelFileDescriptor.createPipe();

			new TransferThread(new ParcelFileDescriptor.AutoCloseInputStream(pipe[0]),
					new FileOutputStream(getoutputFile())).start();
		}
		catch (IOException e) {
			Log.e(getClass().getSimpleName(), "Exception opening pipe", e);
		}

		return(pipe[1].getFileDescriptor());
	}
	protected ParcelFileDescriptor[] mParcelFileDescriptors;
	protected ParcelFileDescriptor mParcelRead;
	protected ParcelFileDescriptor mParcelWrite;
	private void initSocket()
	{
		try
		{
			mParcelFileDescriptors = ParcelFileDescriptor.createPipe();
			mParcelRead = new ParcelFileDescriptor(mParcelFileDescriptors[0]);
			mParcelWrite = new ParcelFileDescriptor(mParcelFileDescriptors[1]);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
	private void record2()
	{
		if (isRecord)
		{
			return;
		}
		isRecord = true;
		// camera.stopPreview();
		mainRecorder = new MediaRecorder();
		mainRecorder.setOnInfoListener(this);

		mainRecorder.setVideoSource(MediaRecorder.VideoSource.SURFACE);
		mainRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		// mainRecorder.setMaxDuration(30 * 1000);
		mainRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);

		// 设置视频录制的分辨率。必须放在设置编码和格式的后面，否则报错
		mainRecorder.setVideoSize(PIC_1080_WIDETH, PIC_1080_HIGHT);

		// mainRecorder
		// .setOrientationHint(90);
		// 设置录制的视频帧率。必须放在设置编码和格式的后面，否则报错
		mainRecorder.setVideoFrameRate(30);
		mainRecorder.setVideoEncodingBitRate(1* 1024 * 1024);
		mainRecorder.setMaxDuration(0);
		mainRecorder.setMaxFileSize(0);
		// 设置视频文件输出的路径
		File mainRecorderFile = new File(Environment
				.getExternalStorageDirectory().getAbsoluteFile() + "/atest");
		if (!mainRecorderFile.exists())
		{
			mainRecorderFile.mkdirs();
		}
		File file = new File(mainRecorderFile, System.currentTimeMillis()
				+ "test111.mp4");
		//mainRecorder.setPreviewDisplay(mHolder.getSurface());
		//mainRecorder.setOutputFile(file.getAbsolutePath());
		mainRecorder.setOutputFile(mParcelFileDescriptors[1].getFileDescriptor());
		try
		{
			//mainRecorder.setPreviewDisplay(mSurfaceView.getHolder().getSurface());
			mainRecorder.prepare();
			//mainRecorder.start();
		} catch (IllegalStateException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		mediaProjection.createVirtualDisplay("MainActivity",
//				PIC_1080_WIDETH, PIC_1080_HIGHT, mScreenDensity,
//				DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
//				mainRecorder.getSurface(), null /*Callbacks*/, null);
		mainRecorder.start();
	}

	private void initView()
	{
		mSurfaceView = (SurfaceView) findViewById(R.id.surfaceView1);
		// mSurfaceView2 = (SurfaceView) findViewById(R.id.surfaceView2);
		mHolder = mSurfaceView.getHolder();
		// mSurfaceView.getWindowToken()
		// mHolder2 = mSurfaceView2.getHolder();
		// mHolder2.addCallback(new Callback() {
		//
		// @Override
		// public void surfaceDestroyed(SurfaceHolder holder) {
		// // TODO Auto-generated method stub
		// if (camera2 != null) {
		// //camera.stopPreview();
		// camera2.release();
		// camera2 = null;
		// }
		//
		// }
		//
		// @Override
		// public void surfaceCreated(SurfaceHolder holder) {
		// // TODO Auto-generated method stub
		// // try {
		// // camera2.setPreviewDisplay(mHolder2);
		// // } catch (IOException e) {
		// // // TODO Auto-generated catch block
		// // e.printStackTrace();
		// // }
		// // camera2.startPreview();
		//
		// }
		//
		// @Override
		// public void surfaceChanged(SurfaceHolder holder, int format, int
		// width,
		// int height) {
		// // TODO Auto-generated method stub
		//
		// }
		// });

		mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		mHolder.addCallback(this);
		findViewById(R.id.button1).setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				camera.takePicture(null, null, new PictureCallback()
				{

					@Override
					public void onPictureTaken(byte[] data, Camera camera)
					{
						// TODO Auto-generated method stub
						Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0,
								data.length);
						Log.d(tag, "take pic width :" + bitmap.getWidth()
								+ "height:" + bitmap.getHeight());
						((ImageView) findViewById(R.id.imageView)).setImageBitmap(bitmap);
						camera.startPreview();
					}
				});

			}
		});
		findViewById(R.id.button2).setOnClickListener(new OnClickListener()
		{

			@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
//				new Thread(new Runnable() {
//
//					@Override
//					public void run() {
//						// TODO Auto-generated method stub
//
				//record();
//						// record2();
//					}
//				}).start();
				//mainRecorder.start();
				startMediaProject();
			}
		});
		findViewById(R.id.button3).setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				stopRecord();
			}
		});
		findViewById(R.id.button4).setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				// cas.getMatrix()
				// ani();
				// change();

			}

		});
	}


	private void initCamera()
	{
		try
		{
			camera = CameraHelper.getDefaultBackFacingCameraInstance();
		} catch (RuntimeException e)
		{
			Log.d(tag, "faild");
			finish();
			return;
		}
		camera.setDisplayOrientation(90);
		parameters = camera.getParameters();
		List<String> focusModes = parameters.getSupportedFocusModes();
		if (focusModes.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO))
		{
			parameters
					.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
		}
		List<Size> pict = parameters.getSupportedPictureSizes();
		for (Size size : pict)
		{
			log("width: " + size.width + "--height:" + size.height);
		}
		List<Size> pret = parameters.getSupportedPreviewSizes();
		for (Size size : pret)
		{
			log("prewidth: " + size.width + "--preheight:" + size.height);
		}
		// parameters.setPictureSize(PIC_720_WIDETH, PIC_720_HIGHT);
		parameters.setPictureSize(PIC_1080_WIDETH, PIC_1080_HIGHT);
		parameters.setPreviewSize(PIC_1080_WIDETH, PIC_1080_HIGHT);
		// parameters.setPreviewFormat(ImageFormat.YV12);
		parameters.setPictureFormat(PixelFormat.JPEG);
		camera.setParameters(parameters);
		//camera.startPreview();
	}

	private void log(String log)
	{
		Log.e(tag, log);
	}

	@SuppressWarnings("deprecation")
	private void initCamera2()
	{
		try
		{
			camera2 = CameraHelper.getDefaultFrontFacingCameraInstance();
		} catch (RuntimeException e)
		{
			Log.d(tag, "faild");
			finish();
			return;
		}
		camera2.setDisplayOrientation(90);
		parameters2 = camera2.getParameters();
		List<String> focusModes = parameters2.getSupportedFocusModes();
		if (focusModes.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO))
		{
			parameters2
					.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
		}
		// parameters.setPictureSize(PIC_720_WIDETH, PIC_720_HIGHT);
		parameters2.setPictureSize(PIC_720_WIDETH, PIC_720_HIGHT);
		parameters2.setPreviewSize(PIC_720_WIDETH, PIC_720_HIGHT);
		parameters.setPreviewFormat(ImageFormat.YV12);
		parameters2.setPictureFormat(PixelFormat.JPEG);
		camera2.setParameters(parameters2);
		camera2.setPreviewCallback(new PreviewCallback()
		{

			@Override
			public void onPreviewFrame(byte[] data, Camera camera)
			{
				// TODO Auto-generated method stub
				Log.d(tag, "camera2 onPreviewFrame");
				if (true)
				{
					Camera.Parameters parameters = camera.getParameters();
					int imageFormat = parameters.getPreviewFormat();
					int w = parameters.getPreviewSize().width;
					int h = parameters.getPreviewSize().height;
					Rect rect = new Rect(0, 0, w, h);
					YuvImage yuvImg = new YuvImage(data, imageFormat, w, h,
							null);
					ByteArrayOutputStream outputstream = new ByteArrayOutputStream();
					yuvImg.compressToJpeg(rect, 100, outputstream);
					Bitmap rawbitmap = BitmapFactory.decodeByteArray(
							outputstream.toByteArray(), 0, outputstream.size());
					mImageView.setImageBitmap(rawbitmap);
				}
			}
		});
	}

	@SuppressWarnings("deprecation")
	@Override
	public void surfaceCreated(SurfaceHolder holder)
	{
		// TODO Auto-generated method stub
		initCamera();
		try
		{
			camera.setPreviewDisplay(mHolder);
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		camera.setPreviewCallback(new PreviewCallback()
		{

			@Override
			public void onPreviewFrame(byte[] data, Camera camera)
			{
				//Log.d(tag, "camera onPreviewFrameonPreviewFrame");

			}
		});
		camera.startPreview();

	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
	                           int height)
	{
		//record2();

	}

	@SuppressWarnings("deprecation")
	@Override
	public void surfaceDestroyed(SurfaceHolder holder)
	{
		if (camera != null)
		{
			// camera.stopPreview();
			camera.release();
			camera = null;
		}

	}

	@Override
	public void onInfo(MediaRecorder mr, int what, int extra)
	{
		// if(what=)
		// {
		//
		// }

	}
	static class TransferThread extends Thread {
		InputStream in;
		FileOutputStream out;

		TransferThread(InputStream in, FileOutputStream out) {
			this.in=in;
			this.out=out;
		}

		@Override
		public void run() {
			byte[] buf=new byte[8192];
			int len;

			try {
				while ((len=in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}

				in.close();

				out.flush();
				out.getFD().sync();
				out.close();
			}
			catch (IOException e) {
				Log.e(getClass().getSimpleName(),
						"Exception transferring file", e);
			}
		}
	}
}
