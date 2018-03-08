package com.example.voiceinput.Http;

/**
 * Created by Administrator on 2017/7/24.
 */
public class NetParams<T extends BaseBean>
{
	public String url;
	public String cmd;
	public String params;
	public Class<T> mClazz;
	public NetCallBackImpl callBack;

	public NetParams(NetParamBuilder builder)
	{
		this.url = builder.url;
		this.cmd = builder.cmd;
		this.params = builder.params;
		this.mClazz = builder.mClazz;
		this.callBack = builder.callBack;
	}

	static class NetParamBuilder<T extends BaseBean>
	{
		public String url;
		public String cmd;
		public String params;
		public Class mClazz;
		NetCallBackImpl callBack;

		public NetParamBuilder setUrl(String url)
		{
			this.url = url;
			return this;
		}

		public NetParamBuilder setCmd(String cmd)
		{
			this.cmd = cmd;
			return this;
		}

		public NetParamBuilder setParams(String params)
		{
			this.params = params;
			return this;
		}

		public NetParamBuilder setClazz(Class<T> clazz)
		{
			this.mClazz = clazz;
			return this;
		}

		public NetParamBuilder setCallBack(NetCallBackImpl callBack)
		{
			this.callBack = callBack;
			return this;
		}

		public NetParams build()
		{
			return new NetParams(this);
		}
	}
}
