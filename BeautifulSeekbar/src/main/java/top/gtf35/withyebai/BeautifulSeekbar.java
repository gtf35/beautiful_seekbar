package top.gtf35.withyebai;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;


/**
 * 一个漂亮的推动进度条
 * 设计：夜白
 * 程序：gtf35
 * gtfdeyouxiang@gamil.com
 * 2019/08/12
 * */

public class BeautifulSeekbar extends View {

    private SizeInfos mSizeInfos = new SizeInfos();
    private ColorInfos mColorInfos = new ColorInfos();
    private DrawObjects mDrawObjects = new DrawObjects();
    private int mProgress = 0;
    private onSeekBarChangeListener mListener;
    private boolean mEnable = true;

    public interface onSeekBarChangeListener{
        void onProgressChanged(BeautifulSeekbar beautifulSeekbar, int progress);
        void onStartTrackingTouch(BeautifulSeekbar beautifulSeekbar);
        void onStopTrackingTouch(BeautifulSeekbar beautifulSeekbar);
    }

    public void setOnSeekBarChangeListener(onSeekBarChangeListener listener){
        mListener = listener;
    }

    public void setInlineColor(int color){
        mColorInfos.setInlineColor(color);
        invalidate();
    }

    public void setOutlineColor(int color){
        mColorInfos.setOutlineColor(color);
        invalidate();
    }

    public void setIndicatorColor(int color){
        mColorInfos.setIndicatorColor(color);
        invalidate();
    }

    public void setIndicatorCircleColor(int color){
        mColorInfos.setIndicatorCircleColor(color);
        invalidate();
    }

    public int getProgress() {
        return mProgress;
    }

    public void setProgress(int progress) {
        if (progress <= 0) progress = 0;
        if (progress >= 100) progress = 100;
        mProgress = progress;
        if (mListener!= null) mListener.onProgressChanged(this, progress);
        invalidate();
    }

    public void setIndicatorZoomProcess(int progress){
        mSizeInfos.setIndicatorZoomProcess(progress);
        invalidate();
    }

    public int getIndicatorZoomProcess(){
        return  mSizeInfos.getIndicatorZoomProcess();
    }

    public BeautifulSeekbar(Context context) {
        super(context);
    }

    public BeautifulSeekbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);//关闭硬件加速
        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.BeautifulSeekbar);
        mEnable = typedArray.getBoolean(R.styleable.BeautifulSeekbar_enable, true);//启用
        mProgress = typedArray.getInteger(R.styleable.BeautifulSeekbar_default_progress, 0);//默认进度
        mColorInfos.setIndicatorCircleColor(typedArray.getInteger(
                R.styleable.BeautifulSeekbar_indicator_circle_color,
                ColorInfos.defaultIndicatorCircleColor));
        mColorInfos.setIndicatorColor(typedArray.getInteger(
                R.styleable.BeautifulSeekbar_indicator_color,
                ColorInfos.defaultIndicatorColor));
        mColorInfos.setInlineColor(typedArray.getInteger(
                R.styleable.BeautifulSeekbar_inline_color,
                ColorInfos.defaultInlineColor));
        mColorInfos.setOutlineColor(typedArray.getInteger(
                R.styleable.BeautifulSeekbar_outline_color,
                ColorInfos.defaultOutlineColor));
    }

    public BeautifulSeekbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setEnabled(boolean enabled) {
        mEnable = enabled;
    }

    @Override
    public boolean isEnabled() {
        return mEnable;
    }

    /*复用的绘画相关实例*/
    private class DrawObjects{

        Paint outlinePaint;
        Paint inlinePaint;
        Paint indicatorPaint;
        Paint indicatorCirclePaint;
        RectF leftCirCleRect;
        RectF rightCirCleRect;
        ObjectAnimator biggerAnimator;
        ObjectAnimator smallerAnimator;
        public DrawObjects() {
            biggerAnimator = ObjectAnimator.ofInt(BeautifulSeekbar.this, "indicatorZoomProcess", 100);
            biggerAnimator.setDuration(100);
            smallerAnimator = ObjectAnimator.ofInt(BeautifulSeekbar.this, "indicatorZoomProcess", 0);
            smallerAnimator.setDuration(100);

            /*外框线画笔*/
            outlinePaint = new Paint();
            outlinePaint.setAntiAlias(true);//抗锯齿
            outlinePaint.setStyle(Paint.Style.STROKE);

            /*内部进度画笔*/
            inlinePaint = new Paint();
            inlinePaint.setAntiAlias(false);//抗锯齿
            inlinePaint.setStyle(Paint.Style.FILL);

            /*手柄画笔*/
            indicatorPaint = new Paint();
            indicatorPaint.setStyle(Paint.Style.STROKE);
            indicatorPaint.setAntiAlias(true);//抗锯齿

            /*手柄内圈画笔*/
            indicatorCirclePaint = new Paint();
            indicatorCirclePaint.setStyle(Paint.Style.FILL);
            indicatorCirclePaint.setAntiAlias(true);//抗锯齿
        }

        /*需要在调用 mSizeInfos.reSize() 初始化测量之后才能进行画笔颜色重置*/
        public void reSetColor(){
            /*外框线画笔*/
            outlinePaint.setColor(mColorInfos.getOutlineColor());

            /*内部进度画笔*/
            inlinePaint.setColor(mColorInfos.getInlineColor());


            /*手柄画笔*/
            indicatorPaint.setColor(mColorInfos.getIndicatorColor());

            /*手柄内圈画笔*/
            indicatorCirclePaint.setColor(mColorInfos.getIndicatorCircleColor());
        }

        /*需要在调用 mSizeInfos.reSize() 初始化测量之后才能进行尺寸置*/
        public void reSetSize(){

            outlinePaint.setStrokeWidth(mSizeInfos.getLineWidth());
            inlinePaint.setStrokeWidth(mSizeInfos.getPrintInlineLineWidth());
            indicatorPaint.setStrokeWidth(mSizeInfos.getLineWidth());

            leftCirCleRect = new RectF(
                    mSizeInfos.getPrintLeftCircleLeft(),
                    mSizeInfos.getPrintLeftCircleTop(),
                    mSizeInfos.getPrintLeftCircleRight(),
                    mSizeInfos.getPrintLeftCircleBottom());

            rightCirCleRect = new RectF(
                    mSizeInfos.getPrintRightCircleLeft(),
                    mSizeInfos.getPrintRightCircleTop(),
                    mSizeInfos.getPrintRightCircleRight(),
                    mSizeInfos.getPrintRightCircleBottom());
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 获取宽-测量规则的模式和大小
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        // 获取高-测量规则的模式和大小
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (getLayoutParams().width == ViewGroup.LayoutParams.WRAP_CONTENT && getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            /*高宽全是当布局参数设置为wrap_content时*/
            mSizeInfos.reSize((int)mSizeInfos.getDefaultCanvasWidth(), (int)mSizeInfos.getDefaultCanvasHeight());
            setMeasuredDimension((int)mSizeInfos.getDefaultCanvasWidth(), (int)mSizeInfos.getDefaultCanvasHeight());//应用大小
        } else if (getLayoutParams().width == ViewGroup.LayoutParams.WRAP_CONTENT) {
            /*宽是wrap_content，高是match_parent或都指定了大小*/
            mSizeInfos.reSize((int)mSizeInfos.getDefaultCanvasWidth(), heightSize);//高用测量值，宽用默认值
            setMeasuredDimension((int)mSizeInfos.getDefaultCanvasWidth(), heightSize);//应用大小
        } else if (getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            /*高是wrap_content，宽是match_parent或都指定了大小*/
            mSizeInfos.reSize(widthSize, (int)mSizeInfos.getDefaultCanvasHeight());//宽用测量值，高用默认值
            setMeasuredDimension(widthSize, (int)mSizeInfos.getDefaultCanvasHeight());//应用大小
        } else {
            /*高宽全是match_parent或都指定了大小*/
            mSizeInfos.reSize(widthSize, heightSize);//应用的应该是实际的大小，因为这种情况是人家指定画布大小了，所以大小必须等于画布，然后再用画布大小去计算自己
            setMeasuredDimension(widthSize, heightSize);//应用大小
        }

        mDrawObjects.reSetSize();
    }

    /*画水平进度条*/
    private void drawProgress(Canvas canvas){
        mDrawObjects.reSetColor();
        Paint paint = mDrawObjects.outlinePaint;
        canvas.drawArc(mDrawObjects.leftCirCleRect, -90, -180, false, paint);
        canvas.drawArc(mDrawObjects.rightCirCleRect, -93, 186, false, paint);
        canvas.drawLine(
                mSizeInfos.getPrintTopLineStartX(),
                mSizeInfos.getPrintTopLineStartY(),
                mSizeInfos.getPrintTopLineEndX(),
                mSizeInfos.getPrintTopLineEndY(),
                paint);
        canvas.drawLine(
                mSizeInfos.getPrintBottomLineStartX(),
                mSizeInfos.getPrintBottomLineStartY(),
                mSizeInfos.getPrintBottomLineEndX(),
                mSizeInfos.getPrintBottomLineEndY(),
                paint);
        paint = mDrawObjects.inlinePaint;
        canvas.drawCircle(
                mSizeInfos.getPrintInlineCircleLeftX(),
                mSizeInfos.getPrintInlineCircleLeftY(),
                mSizeInfos.getPrintInlineCircleLeftLong(),
                paint);
        canvas.drawCircle(
                mSizeInfos.getPrintInlineCircleRightX(mProgress),
                mSizeInfos.getPrintInlineCircleRightY(),
                mSizeInfos.getPrintInlineCircleRightLong(),
                paint);
        canvas.drawLine(
                mSizeInfos.getPrintInlineLineStartX(),
                mSizeInfos.getPrintInlineLineStartY(),
                mSizeInfos.getPrintInlineLineEndX(mProgress),
                mSizeInfos.getPrintInlineLineEndY(),
                paint);
        paint = mDrawObjects.indicatorPaint;
        canvas.drawCircle(
                mSizeInfos.getPrintIndicatorX(mProgress),
                mSizeInfos.getPrintIndicatorY(),
                mSizeInfos.getPrintIndicatorLong(),
                paint);
        paint = mDrawObjects.indicatorCirclePaint;
        canvas.drawCircle(
                mSizeInfos.getPrintIndicatorInlineCircleX(mProgress),
                mSizeInfos.getPrintIndicatorInlineCircleY(),
                mSizeInfos.getPrintIndicatorInlineCircleLong(),
                paint);
    }

    private boolean checkISTouchIndicator(float x, float y){
        if (!(x >= mSizeInfos.getCalculationIndicatorLeft(mProgress) && x <= mSizeInfos.getCalculationIndicatorRight(mProgress))) return false;
        if (!(y >= mSizeInfos.getCalculationIndicatorTop(mProgress) && y <= mSizeInfos.getCalculationIndicatorBottom(mProgress))) return false;
        return true;
    }

    /*在系统回收时保存状态*/
    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("superState", super.onSaveInstanceState());
        bundle.putBoolean("mEnable", mEnable);
        bundle.putInt("mProgress", mProgress);
        bundle.putInt("IndicatorCircleColor", mColorInfos.getIndicatorCircleColor());
        bundle.putInt("IndicatorColor", mColorInfos.getIndicatorColor());
        bundle.putInt("InlineColor", mColorInfos.getInlineColor());
        bundle.putInt("OutlineColor", mColorInfos.getOutlineColor());
        return bundle;
    }

    /*在系统重建的时候恢复状态*/
    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            mEnable = bundle.getBoolean("mEnable", true);
            mProgress = bundle.getInt("mProgress", 0);
            mColorInfos.setIndicatorCircleColor(bundle.getInt("IndicatorCircleColor", ColorInfos.defaultIndicatorCircleColor));
            mColorInfos.setIndicatorColor(bundle.getInt("IndicatorColor", ColorInfos.defaultIndicatorColor));
            mColorInfos.setInlineColor(bundle.getInt("InlineColor", ColorInfos.defaultInlineColor));
            mColorInfos.setOutlineColor(bundle.getInt("OutlineColor", ColorInfos.defaultOutlineColor));
            state = bundle.getParcelable("superState");
        }
        super.onRestoreInstanceState(state);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawProgress(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        ObjectAnimator smallerAnimator = mDrawObjects.smallerAnimator;
        ObjectAnimator biggerAnimator = mDrawObjects.biggerAnimator;
        float x=event.getX();
        float y = event.getY();
        Log.d("触摸", "x=" + x + "  y=" + y + "  " + (checkISTouchIndicator(x , y)? "在": "不在")  + "指示器上");
        float moveX = x - mSizeInfos.getPrintInlineLineStartX();
        int p = (int)(moveX / mSizeInfos.getLineLong() * 100f);
        //Log.d("触摸", "移动百分比：" + p);
        if (mEnable)setProgress(p);//启用时开可移动
        switch (event.getAction()){
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_DOWN:
                //Log.d("触摸", "放大");
                if (smallerAnimator.isRunning()) smallerAnimator.end();
                if (mListener != null) mListener.onStartTrackingTouch(this);
                biggerAnimator.start();
                break;
            case MotionEvent.ACTION_UP:
                //Log.d("触摸", "缩小");
                if (biggerAnimator.isRunning()) biggerAnimator.end();
                if (mListener != null) mListener.onStopTrackingTouch(this);
                smallerAnimator.start();
                break;
        }
        return true;
    }

}
