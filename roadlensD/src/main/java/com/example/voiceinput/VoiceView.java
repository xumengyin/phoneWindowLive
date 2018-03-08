package com.example.voiceinput;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.util.EventLog;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

/**
 * Created by Administrator on 2017/6/26.
 */
public class VoiceView extends View implements View.OnClickListener
{
	int defaultSubwidth = 10;
	int defaultMainwidth = 15;
	int innerCricleColor = Color.WHITE;
	int outerCricleColor = Color.GREEN;
	int drableId = R.drawable.kkk2;
	int defaultText=23;
	Paint drablePaint, criclePaint, animatorPaint,textPaint;
	//BitmapShader shader;
	Callback callBack;
	//seconds
	int duration = 5;
	boolean init = false;

	public void setCallBack(Callback callback)
	{
		this.callBack=callback;
	}
	public VoiceView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		//init();
		initValue();
	}
	private void initValue()
	{
	}

	private Bitmap getBitmap()
	{
//		Drawable drable = getResources().getDrawable(drableId);
//		if (drable instanceof BitmapDrawable)
//		{
//			return ((BitmapDrawable) drable).getBitmap();
//		}
		return BitmapFactory.decodeResource(getResources(), drableId);
		//return null;
	}

	int width, height;

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		if (getMeasuredHeight() != 0 && getMeasuredWidth() != 0)
		{
			width = getMeasuredWidth();
			height = getMeasuredHeight();
			float rangeX=width/2-getRadius()-defaultSubwidth-defaultMainwidth;
			rect.set(rangeX, rangeX, getMeasuredWidth()-rangeX, getMeasuredHeight()-rangeX);
			init();
		}
	}

	private void releaseAni()
	{
		removeCallbacks(runnable);
		if (animator != null)
		{
			animator.cancel();
			animator=null;
			curSwep=0;
			invalidate();
			if(callBack!=null)
			{
				callBack.onVoiceEnd();
			}
		}
	}
	Runnable runnable =new Runnable()
	{
		@Override
		public void run()
		{
			initAnimator();
		}
	};

	private void addRun()
	{
		removeCallbacks(runnable);
		postDelayed(runnable,1000);
	}
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		if(event.getAction()==MotionEvent.ACTION_DOWN)
		{
			if(animator==null)
			{
				addRun();
			}
		}else if(event.getAction()==MotionEvent.ACTION_UP||event.getAction()==MotionEvent.ACTION_CANCEL)
		{
			releaseAni();
		}else
		{
			//do nothing
		}
		return super.onTouchEvent(event);
	}
	Bitmap drawBitmap;
	private void init()
	{
		if (init)
			return;
		drablePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		drablePaint.setColor(Color.WHITE);
		//drablePaint.setStyle(Paint.Style.STROKE);
		Bitmap bitmap = getBitmap();

		Matrix mat = new Matrix();
		//int tempSize = Math.min(bitmap.getWidth(), bitmap.getHeight());
		int tempSize = Math.min(bitmap.getWidth(), bitmap.getHeight());
		float scale = getRadius() * 2 / tempSize;
		//float scale = (float)getWidth()/ tempSize;
		//mat.setTranslate(bitmap.getWidth()/2,bitmap.getHeight()/2);
		mat.setScale(scale, scale);
		//mat.postTranslate(-100,0);
		//shader.setLocalMatrix(mat);
		drawBitmap=Bitmap.createScaledBitmap(bitmap,(int)(bitmap.getWidth()*scale),(int)(bitmap.getHeight()*scale),false);

		criclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		criclePaint.setColor(Color.WHITE);
		criclePaint.setStyle(Paint.Style.STROKE);
		criclePaint.setStrokeWidth(defaultSubwidth);


		animatorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		animatorPaint.setColor(Color.GREEN);
		animatorPaint.setStyle(Paint.Style.STROKE);
		animatorPaint.setStrokeWidth(defaultMainwidth);

		textPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
		textPaint.setColor(Color.WHITE);
		textPaint.setTextSize(defaultText);
		textPaint.setTypeface(Typeface.DEFAULT_BOLD);
		textWidth=textPaint.measureText(text);

		this.setOnClickListener(this);
		init = true;
	}

	ValueAnimator animator;
	int curSwep;

	private void initAnimator()
	{
		animator = ValueAnimator.ofInt(0, 360);
		animator.setDuration(duration * 1000);
		animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
		{
			@Override
			public void onAnimationUpdate(ValueAnimator animation)
			{
				curSwep = (int) animation.getAnimatedValue();
				invalidate();
			}
		});
		animator.addListener(new AnimatorListenerAdapter()
		{
			@Override
			public void onAnimationEnd(Animator animation)
			{
				releaseAni();
			}
		});
		animator.setInterpolator(new LinearInterpolator());
		animator.start();
		if(callBack!=null)
		{
			callBack.onVoiceStart();
		}
	}

	private float getRadius()
	{
		return (float) (width*0.25);
	}

	private void drawDrable(Canvas canvas)
	{
		canvas.drawBitmap(createCricleDrawable(),getRadius(),getRadius(),drablePaint);
	}
	private Bitmap createCricleDrawable()
	{
		final Paint paint = new Paint();
		paint.setAntiAlias(true);
		Bitmap target = Bitmap.createBitmap((int) getRadius()*2,(int) getRadius()*2, Bitmap.Config.ARGB_8888);
		Canvas canvas =new Canvas(target);
		canvas.drawCircle(getRadius(),getRadius(),getRadius(),paint);
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		canvas.drawBitmap(drawBitmap, 0, 0, paint);
		paint.setXfermode(null);
		return target;
	}
	@Override
	protected void onDraw(Canvas canvas)
	{
		drawDrable(canvas);
		//canvas.drawCircle(getWidth() / 2, getWidth() / 2, getRadius(), drablePaint);
		draw1(canvas);
		draw2(canvas);
		drawText(canvas);
	}

	String text="您好，请说话...";
	float textWidth;
	private float textHeightPadding()
	{
		float textHeight= (int) (textPaint.getFontMetrics().descent-textPaint.getFontMetrics().leading);
		return rect.bottom+(height-rect.bottom-textHeight)/2;
	}
	private void drawText(Canvas canvas)
	{
		canvas.drawText(text,(width-textWidth)/2,textHeightPadding(),textPaint);
	}
	private void draw1(Canvas canvas)
	{
		canvas.drawCircle(getWidth() / 2, getWidth() / 2, getRadius() + defaultSubwidth, criclePaint);
	}

	RectF rect = new RectF();

	private void draw2(Canvas canvas)
	{
		canvas.drawArc(rect, -90, curSwep, false, animatorPaint);
	}

	@Override
	public void onClick(View v)
	{
		//initAnimator();
	}

	public interface Callback
	{
		void onVoiceStart();

		void onVoiceEnd();

		void onVoiceRlease();
	}
}
