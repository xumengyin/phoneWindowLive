package com.example.voiceinput.Http;

import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Administrator on 2017/7/24.
 */
public class HttpHelper<T extends BaseBean>
{
	private static final String TAG = "HttpHelper";
	public static final String BASE_URL = "";
	static Retrofit retrofit;
	static InterfaceConstants service;
	Callback<String> callback;
	private final Gson gson = new Gson();
	List<Call> list = new ArrayList<>();

	static
	{
		retrofit = new Retrofit.Builder()
				.baseUrl(BASE_URL)
				.build();
		service = retrofit.create(InterfaceConstants.class);
	}

	public HttpHelper()
	{

	}

	public void call(final NetParams<T> netParams)
	{
		final Call<String> caller = service.listRepos(netParams.cmd, netParams.params);
		list.add(caller);
		netParams.callBack.onStart();
		caller.enqueue(new Callback<String>()
		{
			@Override
			public void onResponse(Call<String> call, Response<String> response)
			{
				list.remove(caller);
				String result = response.body();
				Log.e(TAG, "onResponse: " + result);
				T resultInfo = gson.fromJson(result, netParams.mClazz);
				netParams.callBack.onResponse(resultInfo);
			}

			@Override
			public void onFailure(Call<String> call, Throwable t)
			{
				Log.e(TAG, "onFailure: " + t);
				list.remove(caller);
				if (!call.isCanceled())
				{
					netParams.callBack.onFailue(new Exception(t));
				}

			}
		});
	}

	public void cancel()
	{
		for (Call caller : list)
			caller.cancel();
	}
}
