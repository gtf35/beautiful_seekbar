# BeautifulSeekbar

一个在 Android 平台上美观的跟手进度条

[![](https://img.shields.io/github/stars/gtf35/beautiful_seekbar?style=for-the-badge)]()  [![](https://img.shields.io/github/forks/gtf35/beautiful_seekbar?style=for-the-badge)]()  [![](https://img.shields.io/github/release/gtf35/beautiful_seekbar?style=for-the-badge)](https://github.com/gtf35/beautiful_seekbar/releases) 

## 0 效果图
![效果图](https://github.com/gtf35/beautiful_seekbar/blob/master/static/seekbar.gif)

## 1 设计图
![设计图](https://github.com/gtf35/beautiful_seekbar/blob/master/static/yuanpic.png)

## 2 特性
   - 美观，ui设计师 夜白 设计
   - 支持颜色自定义
   - 支持禁用/启用
   - 支持代码设置进度
   - 支持滑动/手指按下/手指抬起事件监听
   - 完全支持 wrap_content/match_parent/固定大小等尺寸
   - 支持 AndroidStudio 预览(请先 build 一次即可预览)
   - 支持 API Level 18 (Android 4.3)
   - 小巧，没有使用任何图片资源
   - 支持被系统回收后重建

## 3 使用
### 	3x1 依赖：在 app 级别的 build.gradle 添加

```Gradle
dependencies {
   ....
   implementation 'top.gtf35.lib.withyebai:SeekBar:1.0'
   ....
}
```

### 	3x2 引入：在 layout 布局文件中添加
```xml
<top.gtf35.withyebai.BeautifulSeekbar
        android:layout_width="match_parent"
        android:layout_height="50dp" />
```
### 	3x3 监听：在调用实例的 setOnSeekBarChangeListener 监听滑动/手指按下/手指抬起事件

```java
/*进度监听*/
seekbar.setOnSeekBarChangeListener(new BeautifulSeekbar.onSeekBarChangeListener() {
            @Override
            public void onProgressChanged(BeautifulSeekbar beautifulSeekbar, int progress) {
                //在进度改变的时候会回调这个接口(包括使用 Java 设置进度)
                //返回0-100之间的数值
                progressTV.setText("进度：" + progress + "%");
            }

            @Override
            public void onStartTrackingTouch(BeautifulSeekbar beautifulSeekbar) {
                //在手指放在拖动条上的时候会回调这个接口，可做进度提示等操作
                fingerTV.setText("手指已按下");
            }

            @Override
            public void onStopTrackingTouch(BeautifulSeekbar beautifulSeekbar) {
                //在手指在拖动条抬起上的时候会回调这个接口，可做进度提示等操作
                fingerTV.setText("手指未按下");
            }
        });
```


## 4 混淆
没有使用任何反射等奇技淫巧，无需关注

## 5 高级使用

### 		5x0 demo 预览

![demo预览](https://github.com/gtf35/beautiful_seekbar/blob/master/static/demopic.png)

可以参考 demo ，在 demo 里有所有方法的演示

看 demo 运行理解，更高效

### 在 xml 布局文件中设置默认状态

| 属性                   | 类型    | 说明                   | 示例数据             |
| ---------------------- | ------- | ---------------------- | -------------------- |
| enable                 | boolean | 启用                   | true                 |
| default_progress       | integer | 默认进度(0-100)        | 30                   |
| outline_color          | color   | 进度条边框颜色         | @color/colorPrimary  |
| inline_color           | color   | 进度条进度颜色         | #00574B              |
| indicator_circle_color | color   | 进度条手柄内部填充颜色 | @android:color/white |
| indicator_color        | color   | 进度条手柄外圈颜色     | #704680FF            |

> 提示：除了 indicator_color 即 进度条手柄外圈颜色 可使用透明色，这样会更漂亮(默认的 indicator_color  即为透明色)，其余部分颜色请勿使用透明色，因为处理开了抗锯齿带来的各部分边缘拼接有瑕疵的问题，各部分有轻微重合的部分，使用透明色会很难看。

### 获取进度

调用 BeautifulSeekbar 实例的 getProgress() 方法可以获取进度，返回 0-100 的整数，代表 0%-100%

```java
int progress = seekbar.getProgress();
```

### 设置进度

调用 BeautifulSeekbar 实例的 setProgress(int progress) 方法可以设置进度，传入 0-100 的整数，代表 0%-100%

```java
int progress = 10;//10%    0-100之间的整数
seekbar.setProgress(progress);//设置进度
```

### 动态禁用/启用

调用 BeautifulSeekbar 实例的 setEnabled(boolean isEnable) 方法可以动态设置禁用/启用

传入 true 为启用，用户可以正常滑动

传入 false 为禁用，用户将不能滑动

```java
seekbar.setEnabled(true);//启用。用户可以滑动了
seekbar.setEnabled(false);//禁用。用户将不能滑动
```

### 动态设置颜色

调用 BeautifulSeekbar 实例的 setInlineColor(int color) 方法可以可动态设置进度条进度颜色

调用 BeautifulSeekbar 实例的 setOutlineColor(int color) 方法可以可动态设置进度条边框颜色

调用 BeautifulSeekbar 实例的 setIndicatorColor(int color) 方法可以可动态设置进度条手柄外圈颜色

调用 BeautifulSeekbar 实例的 setIndicatorCircleColor(int color) 方法可以可动态设置进度条手柄内部填充颜色

```java
seekbar.setInlineColor(Color.GREEN);//进度条进度颜色
seekbar.setOutlineColor(Color.RED);//进度条边框颜色
seekbar.setIndicatorColor(Color.BLUE);//进度条手柄外圈颜色
seekbar.setIndicatorCircleColor(Color.BLACK);//进度条手柄内部填充颜色
```



## 关于

- 程序：gtf35 gtfdeyouxiang@gmail.com
- 美术：夜白 3412436097@qq.com
- 开源协议：MIT
- 文档撰写日期：2019/08/18

## 更新日志

- 2019/08/18 整理开源