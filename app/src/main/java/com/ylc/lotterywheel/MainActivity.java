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
        MyLotteryWheel lotterywheel = findViewById(R.id.lotterywheel);
        lotterywheel.setGiftImgs(giftImgs);
        lotterywheel.setGiftNames(giftNames);
        lotterywheel.setSpeed(5);
        lotterywheel.init();
        lotterywheel.setOnSelectListener(new SelectListener() {
            @Override
            public void onSelect(MyGift myGift) {
                Toast.makeText(getApplicationContext(), "恭喜您,中到了" + myGift.getName(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
