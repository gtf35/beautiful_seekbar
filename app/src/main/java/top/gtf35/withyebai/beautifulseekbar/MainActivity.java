package top.gtf35.withyebai.beautifulseekbar;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import top.gtf35.withyebai.BeautifulSeekbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /*
         * 进度监听
         * */
        final TextView progressTV = findViewById(R.id.tv_progress);
        final TextView fingerTV = findViewById(R.id.tv_finger);
        final BeautifulSeekbar seekbar = findViewById(R.id.beautifulSeekbar);
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



        /*
        * 获取进度
        * */
        Button getProgressBtn = findViewById(R.id.btn_getprogress);
        getProgressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*获取进度（0-100）*/
                Toast.makeText(MainActivity.this, "进度：" + seekbar.getProgress() + "%", Toast.LENGTH_SHORT).show();
            }
        });



        /*
        * 代码设置进度(0-100)
        * */
        final EditText inputProgress = findViewById(R.id.et_progress);
        Button setProgressBtn = findViewById(R.id.btn_setprogess);
        setProgressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    int progress = Integer.parseInt(inputProgress.getText().toString());
                    /*设置进度*/
                    seekbar.setProgress(progress);
                } catch (Exception e){
                    seekbar.setProgress(0);
                }
            }
        });




        /*
        * 代码禁用，此刻用户无法滑动
        * */
        Button disableButton = findViewById(R.id.btn_disable);
        disableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*禁用*/
                seekbar.setEnabled(false);
            }
        });



        /*
        * 代码启用，现在用户可以正常滑动了
        * */
        Button enableButton = findViewById(R.id.btn_enable);
        enableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*启用*/
                seekbar.setEnabled(true);
            }
        });


        /*
        * 代码动态设置颜色，立刻生效
        * */
        Button changeColorButton = findViewById(R.id.btn_change_color);
        changeColorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seekbar.setInlineColor(Color.GREEN);//进度条进度颜色
                seekbar.setOutlineColor(Color.RED);//进度条边框颜色
                seekbar.setIndicatorColor(Color.BLUE);//进度条手柄外圈颜色
                seekbar.setIndicatorCircleColor(Color.BLACK);//进度条手柄内部填充颜色
            }
        });
    }
}
