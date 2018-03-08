package com.example.voiceinput.Http;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import org.apache.http.conn.ConnectTimeoutException;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/**
 * Created by Administrator on 2017/7/24.
 */
public abstract class NetCallBackImpl<T extends BaseBean> implements NetCallBack
{
	ProgressDialog dialog;
	Context ctx;
	Handler mHandler;

	public NetCallBackImpl(Context context)
	{
		ctx = context;
		mHandler = new Handler(Looper.getMainLooper());
	}

	@Override
	public void onStart()
	{
		this.dialog = new ProgressDialog(ctx);
		dialog.show();
	}

	abstract void uiSucess(T response);

	abstract void uiError(T response);

	@Override
	public void onFailue(Exception e)
	{
		onFinish();
		String error = "请求异常";

		if (e instanceof ConnectException)
		{// 不能在指定的ip和端口上建立连接
			error = "无法和服务器建立连接";
		} else if (e instanceof ConnectTimeoutException)
		{// 建立连接超时
			error = "与服务器建立连接超时";
		} else if (e instanceof SocketTimeoutException)
		{// 读取数据超时
			error = "服务器读取数据超时";
		} else if (e instanceof UnknownHostException)
		{
			error = "网络异常";
		} else if (e instanceof InterruptedIOException)
		{
			error = "网络连接失败";
		} else if (e instanceof IOException)
		{
			if (e.getLocalizedMessage().equals("Socket Closed") || e.getLocalizedMessage().equals("Canceled"))
			{
				return;
			}
			error = "网络异常";
		}

		uiFailue(error);
	}

	@Override
	public void onResponse(final BaseBean response)
	{
		onFinish();
		if (response.result == 0)
		{
			mHandler.post(new Runnable()
			{
				@Override
				public void run()
				{
					uiSucess((T) response);
				}
			});
		} else
		{
			mHandler.post(new Runnable()
			{
				@Override
				public void run()
				{
					uiError((T) response);
				}
			});
		}
	}

	@Override
	public void onFinish()
	{
		if (dialog != null)
		{
			dialog.dismiss();
			dialog=null;
		}
	}

	public void uiFailue(String error)
	{
		Toast.makeText(ctx, error, Toast.LENGTH_LONG).show();
	}
}
