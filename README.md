# phoneWindowLive
运行example1，将手机屏幕推送到电脑播放或者其他手机播放，使用了libstreaming开源库实现了rtsp协议
需要在同一网段，电脑播放使用vlc，输入手机采集端的ip和端口，操作手机开始播放;其他手机端直接可以使用mediaplayer直接播放手机采集端ip和端口


原理：
内部实现了rtsp服务器，以及编码和推送流。实现原理见[libstream](https://github.com/fyhertz/libstreaming)
