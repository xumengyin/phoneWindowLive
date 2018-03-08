package net.majorkernelpanic.streaming.video;
import java.io.IOException;
import java.nio.ByteBuffer;

import android.media.MediaCodec;
import android.media.MediaCodec.BufferInfo;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.util.Log;
import android.view.Surface;

public class VideoDecoderThread extends Thread {
	private static final String VIDEO = "video/";
	private static final String TAG = "VideoDecoder";
	//private MediaExtractor mExtractor;
	private MediaCodec mDecoder;
	
	private boolean eosReceived;
	
	public boolean init(Surface surface, MediaCodec mDecoder) {
		eosReceived = false;
		this.mDecoder=mDecoder;	
		return true;
	}

	@Override
	public void run() {
		BufferInfo info = new BufferInfo();
		ByteBuffer[] inputBuffers = mDecoder.getInputBuffers();
		ByteBuffer[] outBuffers = mDecoder.getOutputBuffers();
		
		boolean isInput = true;
		boolean first = false;
		long startWhen = 0;
		
		while (!eosReceived) {
			if (isInput) {
				int inputIndex = mDecoder.dequeueInputBuffer(500000);
				if (inputIndex >= 0) {
					// fill inputBuffers[inputBufferIndex] with valid data
					ByteBuffer inputBuffer = inputBuffers[inputIndex];
					//inputBuffer.clear();
					//int sampleSize = mExtractor.readSampleData(inputBuffer, 0);
					
					
					//mMediaCodec.queueInputBuffer(bufferIndex, 0, inputBuffers[bufferIndex].position(), now, 0);
				}
			}
			
			
			}
			
			
		
		
		mDecoder.stop();
		mDecoder.release();
		//mExtractor.release();
	}
	
	public void close() {
		eosReceived = true;
	}
}