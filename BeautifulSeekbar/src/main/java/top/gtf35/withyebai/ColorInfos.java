package top.gtf35.withyebai;

import android.graphics.Color;

/*色彩信息处理类*/
public class ColorInfos{
    public final static int defaultOutlineColor = 0xffB5CCFF;
    public final static int defaultInlineColor = 0xffB5CCFF;
    public final static int defaultIndicatorColor = 0x704680FF;
    public final static int defaultIndicatorCircleColor = Color.WHITE;

    private int outlineColor = defaultOutlineColor;//进度条边框颜色
    private int inlineColor = defaultInlineColor;//进度条进度颜色
    private int indicatorCircleColor = defaultIndicatorCircleColor;//进度条手柄内部填充颜色
    private int indicatorColor = defaultIndicatorColor;//进度条手柄外圈颜色

    public void setInlineColor(int color){
        inlineColor = color;
    }

    public void setOutlineColor(int color){
        outlineColor = color;
    }

    public void setIndicatorColor(int color){
        indicatorColor = color;
    }

    public void setIndicatorCircleColor(int color){
        indicatorCircleColor = color;
    }

    public int getInlineColor(){
        return inlineColor;
    }

    public int getOutlineColor(){
        return outlineColor;
    }

    public int getIndicatorColor(){
        return indicatorColor;
    }

    public int getIndicatorCircleColor(){
        return indicatorCircleColor;
    }
}