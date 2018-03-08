package com.example.voiceinput.Http;

/**
 * Created by Administrator on 2017/7/24.
 */

public interface NetCallBack<T extends BaseBean>
{

	void onStart();

	void onResponse(T response);

	void onFailue(Exception e);

	void onFinish();
}

