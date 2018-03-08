package com.example.voiceinput.Http;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Administrator on 2017/7/24.
 */
public interface InterfaceConstants
{
	@POST()
	Call<String> listRepos(@Field("cmd") String cmd,@Field("params") String params);

}
