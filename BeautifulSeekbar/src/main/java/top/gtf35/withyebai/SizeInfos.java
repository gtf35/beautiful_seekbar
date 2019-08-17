package top.gtf35.withyebai;

/*尺寸信息处理类*/
public class SizeInfos{
    final float defaultLineWidth = 20f;//默认线条宽度
    final float defaultPogressHeight = 80f;//默认进度条高度
    final float defaultIndicatorHeight = 120f;//默认指示器的高度
    final float defaultLineLong = 800f;//默认线条长度

    private float lineWidth;
    private float processHeight;
    private float indicatorHeight;
    private float lineLong;
    private int indicatorZoomProcess;
    private int indicatorZoomProcessMin = 30;
    private float offsetY;

    public SizeInfos() {
        lineWidth = defaultLineWidth;
        processHeight = defaultPogressHeight;
        indicatorHeight = defaultIndicatorHeight;
        lineLong = defaultLineLong;
    }

    public void setIndicatorZoomProcess(int progress){
        if (progress >= 100) progress = 100;
        if (progress <= indicatorZoomProcessMin) progress = indicatorZoomProcessMin;
        indicatorZoomProcess = progress;
    }

    public int getIndicatorZoomProcess(){
        if (indicatorZoomProcess <= indicatorZoomProcessMin) indicatorZoomProcess = indicatorZoomProcessMin;
        return indicatorZoomProcess;
    }

    public float getDefaultCanvasWidth(){
        return defaultPogressHeight + lineLong;
    }

    public float getDefaultCanvasHeight(){
        return defaultIndicatorHeight;
    }

    public void reSize(int canvasWidth, int canvasHeight){
        float fix = canvasHeight / getDefaultCanvasHeight();
        lineWidth = defaultLineWidth * fix;
        processHeight = defaultPogressHeight * fix;
        indicatorHeight = processHeight + 2 * lineWidth;
        lineLong = canvasWidth - processHeight;
        indicatorZoomProcessMin = (int)((processHeight / indicatorHeight) * 100f) - 5;//-3是因为抗锯齿
    }

    public float getLineWidth(){
        return lineWidth;
    }

    public float getLineLong(){
        return lineLong;
    }

    /*左圆*/
    public float getPrintLeftCircleTop(){
        return offsetY + getIndicatorExtraHeight() + getHalfLineWidth();
    }

    public float getPrintLeftCircleLeft(){
        return getHalfLineWidth();
    }

    public float getPrintLeftCircleBottom(){
        return  offsetY + getIndicatorExtraHeight() + processHeight - getHalfLineWidth();
    }

    public float getPrintLeftCircleRight(){
        return processHeight - getHalfLineWidth();
    }

    /*右圆*/
    public float getPrintRightCircleTop(){
        return getPrintLeftCircleTop();
    }

    public float getPrintRightCircleLeft(){
        return getPrintLeftCircleLeft() + lineLong;
    }

    public float getPrintRightCircleBottom(){
        return getPrintLeftCircleBottom();
    }

    public float getPrintRightCircleRight(){
        return getPrintLeftCircleRight() + lineLong;
    }

    /*上直线*/
    public float getPrintTopLineStartX(){
        return getHalfProcessHeight() - 3;//不知道为啥这个半圆接不上
    }

    public float getPrintTopLineStartY(){
        return offsetY + getIndicatorExtraHeight() + getHalfLineWidth();
    }

    public float getPrintTopLineEndX(){
        return getPrintTopLineStartX() + lineLong + 3;//不知道为啥这个半圆接不上
    }

    public float getPrintTopLineEndY(){
        return getPrintTopLineStartY();
    }

    /*下直线*/
    public float getPrintBottomLineStartX(){
        return getHalfProcessHeight() - 3;//不知道为啥这个半圆接不上
    }

    public float getPrintBottomLineStartY(){
        return offsetY + getIndicatorExtraHeight() + processHeight - getHalfLineWidth();
    }

    public float getPrintBottomLineEndX(){
        return getPrintBottomLineStartX() + lineLong + 3;//不知道为啥这个半圆接不上
    }

    public float getPrintBottomLineEndY(){
        return getPrintBottomLineStartY();
    }

    /*中间固定圆*/
    public float getPrintInlineCircleLeftX(){
        return processHeight/2f;
    }

    public float getPrintInlineCircleLeftY(){
        return offsetY + getIndicatorExtraHeight() + getPrintInlineCircleLeftX();
    }

    public float getPrintInlineCircleLeftLong(){
        return (processHeight - 2 * lineWidth)/2;
    }

    /*中间移动圆*/
    public float getPrintInlineCircleRightX(int progress){
        return getPrintInlineCircleLeftX() + (progress / 100f) * lineLong;
    }

    public float getPrintInlineCircleRightY(){
        return getPrintInlineCircleLeftY();
    }

    public float getPrintInlineCircleRightLong(){
        return getPrintInlineCircleLeftLong();
    }

    /*中间填充线*/
    public float getPrintInlineLineWidth(){
        return getPrintInlineCircleLeftLong() * 2.2f;//抵消抗锯齿的阴影
    }

    public float getPrintInlineLineStartX(){
        return getPrintTopLineStartX();
    }

    public float getPrintInlineLineStartY(){
        return getPrintInlineCircleLeftY();
    }

    public float getPrintInlineLineEndX(int progress){
        return getPrintInlineCircleRightX(progress);
    }

    public float getPrintInlineLineEndY(){
        return getPrintInlineCircleRightY();
    }

    /*手柄*/
    public float getPrintIndicatorX(int progress){
        return getPrintInlineCircleRightX(progress);
    }

    public float getPrintIndicatorY(){
        return getPrintInlineCircleRightY();
    }

    public float getPrintIndicatorLong(){
        return (getIndicatorZoomProcess() / 100f) * ((indicatorHeight - lineWidth)/2f);
    }

    public float getCalculationIndicatorTop(int progress){
        return 0f;
    }

    public float getCalculationIndicatorBottom(int progress){
        return indicatorHeight;
    }

    public float getCalculationIndicatorLeft(int progress){
        return getPrintIndicatorX(progress) - getPrintIndicatorLong();
    }

    public float getCalculationIndicatorRight(int progress){
        return getPrintIndicatorX(progress) + getPrintIndicatorLong();
    }

    /*手柄内圆*/
    public float getPrintIndicatorInlineCircleX(int progress){
        return getPrintInlineCircleRightX(progress);
    }

    public float getPrintIndicatorInlineCircleY(){
        return getPrintInlineCircleRightY();
    }

    public float getPrintIndicatorInlineCircleLong(){
        return getPrintIndicatorLong() - getHalfLineWidth();
    }

    private float getHalfLineWidth(){
        return lineWidth/2f;
    }

    private float getHalfProcessHeight(){
        return processHeight/2f;
    }

    private float getIndicatorExtraHeight(){
        return (indicatorHeight - processHeight)/2f;
    }
}
