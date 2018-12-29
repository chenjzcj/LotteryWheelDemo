package com.ylc.lotterywheel;

import com.ylc.lotterywheel.lottery.MyLotteryWheel;
import com.ylc.lotterywheel.lottery.MyLotteryWheel.SelectListener;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

/**
 * @author Administrator
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyLotteryWheel lotterywheel = findViewById(R.id.lotterywheel);
        lotterywheel.setOnSelectListener(new SelectListener() {
            @Override
            public void onSelect(String str) {
                Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG).show();
            }
        });
    }
}
