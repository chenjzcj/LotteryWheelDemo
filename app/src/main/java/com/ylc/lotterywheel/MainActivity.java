package com.ylc.lotterywheel;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.ylc.lotterywheel.lottery.MyGift;
import com.ylc.lotterywheel.lottery.MyLotteryWheel;
import com.ylc.lotterywheel.lottery.MyLotteryWheel.SelectListener;

/**
 * @author Administrator
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int[] giftImgs = {R.drawable.danfan, R.drawable.f040, R.drawable.ipad, R.drawable.f040, R.drawable.iphone, R.drawable.f040, R.drawable.meizi, R.drawable.f040};
        String[] giftNames = {"单反", "笑脸1", "iPad", "笑脸2", "iPhone", "笑脸3", "妹子", "笑脸4"};
        int[] giftStates = {1, 0, 1, 0, 1, 0, 1, 0};
        MyLotteryWheel lotterywheel = findViewById(R.id.lotterywheel);
        lotterywheel.setGiftImgs(giftImgs);
        lotterywheel.setGiftNames(giftNames);
        lotterywheel.setGiftStates(giftStates);
        //设置转动速度,可选配置
        lotterywheel.setSpeed(MyLotteryWheel.Speed.SLOW.getSpeed());
        //初始化,必须设置项
        lotterywheel.init();
        lotterywheel.setOnSelectListener(new SelectListener() {
            @Override
            public void onSelect(MyGift myGift) {
                String msg = myGift.getState() == 1 ? "恭喜您,中到了" + myGift.getName() : "很遗憾,没有中到奖!";
                //Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();
                //小米手机Toast含有应用名前缀问题: https://blog.csdn.net/yinxing2008/article/details/82385643
                Toast toast = Toast.makeText(MainActivity.this, null, Toast.LENGTH_LONG);
                toast.setText(msg);
                toast.show();
            }
        });
    }
}
