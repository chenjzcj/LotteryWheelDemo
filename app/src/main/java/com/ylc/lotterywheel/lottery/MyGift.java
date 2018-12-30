package com.ylc.lotterywheel.lottery;

import android.graphics.Bitmap;

/**
 * 摇奖的礼品
 * Created by Felix.Zhong on 2018/12/29 15:57
 */
public class MyGift {
    private Bitmap bmp;
    private String name;
    private int startAngle;
    private int endAngle;
    /**
     * 0代表没有中到,1代表中到
     */
    private int state;

    MyGift(Bitmap bmp, String name, int state) {
        this.bmp = bmp;
        this.name = name;
        this.state = state;
    }

    @Override
    public String toString() {
        return "MyGift{" +
                "bmp=" + bmp +
                ", name='" + name + '\'' +
                ", startAngle=" + startAngle +
                ", endAngle=" + endAngle +
                ", state=" + state +
                '}';
    }

    public Bitmap getBmp() {
        return bmp;
    }

    public void setBmp(Bitmap bmp) {
        this.bmp = bmp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStartAngle() {
        return startAngle;
    }

    public void setStartAngle(int startAngle) {
        this.startAngle = startAngle;
    }

    public int getEndAngle() {
        return endAngle;
    }

    public void setEndAngle(int endAngle) {
        this.endAngle = endAngle;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
