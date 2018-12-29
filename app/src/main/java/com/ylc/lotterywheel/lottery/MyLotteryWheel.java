
package com.ylc.lotterywheel.lottery;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Toast;

import com.ylc.lotterywheel.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 摇奖转盘
 *
 * @author Administrator
 */
public class MyLotteryWheel extends View {

    public MyLotteryWheel(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    private int measureWidth(int measureSpec) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = suggestW + getPaddingLeft() + getPaddingRight();
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    /**
     * Determines the height of this view
     *
     * @param measureSpec A measureSpec packed into an int
     * @return The height of the view, honoring constraints from measureSpec
     */
    private int measureHeight(int measureSpec) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = suggestH + getPaddingTop()
                    + getPaddingBottom();
            if (specMode == MeasureSpec.AT_MOST) {
                // Respect AT_MOST value if that was what is called for by
                // measureSpec
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    /**
     * default
     */
    private int suggestW = 200;
    /**
     * default
     */
    private int suggestH = 200;
    private Paint paint;
    public ObjectAnimator animator;

    public Bitmap bgBitmap;
    public Bitmap centerBitmap;

    public int screenW = 0;
    public int screenH = 0;

    public int startAngle = 50;
    public int distanceR = 50;

    public boolean mRunning = false;
    /**
     * 速度
     */
    private int mSpeed = 5;

    /**
     * 奖品图片
     */
    private int[] mGiftImgs;
    /**
     * 奖品名称
     */
    private String[] mGiftNames;


    /**
     * 360 分成了n份
     */
    private static int SWEEP_ANGLE;

    /**
     * 奖品列表
     */
    private List<MyGift> myGifts = new ArrayList<>();

    public void setGiftImgs(int[] mGiftImgs) {
        this.mGiftImgs = mGiftImgs;
    }

    public void setGiftNames(String[] mGiftNames) {
        this.mGiftNames = mGiftNames;
    }

    public void setSpeed(int mSpeed) {
        this.mSpeed = mSpeed;
    }

    /**
     * 初始化
     */
    public void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Style.FILL);
        paint.setColor(Color.YELLOW);
        paint.setStrokeWidth(3);
        //背景图片
        bgBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bg2);
        //指针图片
        centerBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.arrow);
        if (mGiftImgs == null || mGiftNames == null) {
            throw new IllegalArgumentException("奖品图片或者奖品名称不能为空");
        }
        SWEEP_ANGLE = 360 / mGiftImgs.length;
        //奖品初始化
        for (int i = 0; i < mGiftImgs.length; i++) {
            myGifts.add(new MyGift(BitmapFactory.decodeResource(getResources(), mGiftImgs[i]), mGiftNames[i]));
        }
        initAnimation();
    }

    /**
     * 初始化动画
     */
    @SuppressLint("ObjectAnimatorBinding")
    private void initAnimation() {
        animator = ObjectAnimator.ofInt(new Object(), "nimi", 0, 360);
        animator.addUpdateListener(new AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                startAngle = (Integer) animation.getAnimatedValue();
                long duration = animation.getDuration();
                if (duration > mSpeed * 100 * 5) {
                    animation.cancel();
                    stopRunning();
                } else {
                    animation.setDuration(duration + 2);
                    invalidate();
                }
            }
        });
        animator.setInterpolator(new LinearInterpolator());
        //animator.setInterpolator(new BounceInterpolator());
        //animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(mSpeed * 100 + (new Random().nextInt(100)));
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.setRepeatCount(ValueAnimator.INFINITE);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            startOrStopRunning();
        }
        return true;
    }

    /**
     * 开始或者停止转动
     */
    private void startOrStopRunning() {
        if (mRunning) {
            animator.cancel();
            stopRunning();
        } else {
            initAnimation();
            animator.start();
        }
        mRunning = !mRunning;
    }

    /**
     * 停止运行
     */
    private void stopRunning() {
        for (MyGift myGift : myGifts) {
            if ((myGift.getEndAngle() % 360) >= 270 && (myGift.getEndAngle() % 360) < 330) {
                if (listener != null) {
                    listener.onSelect(myGift);
                } else {
                    Toast.makeText(getContext(), myGift.getName(), Toast.LENGTH_LONG).show();
                }
                break;
            }
        }
        mRunning = false;
    }

    /**
     * 摇奖结果监听
     */
    public interface SelectListener {
        void onSelect(MyGift myGift);
    }

    private SelectListener listener = null;

    public void setOnSelectListener(SelectListener listener) {
        this.listener = listener;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        //设置画布颜色
        canvas.drawColor(Color.TRANSPARENT);

        screenW = getMeasuredWidth();
        screenH = getMeasuredHeight();

        int centerx = screenH / 2;
        int centery = screenH / 2;

        float dis = screenH / 3f;
        paint.setStyle(Style.FILL);
        RectF dst = new RectF(0, 0, screenW, screenH);

        RectF arcRect = new RectF(distanceR, distanceR, screenW - distanceR, screenH - distanceR);

        //绘制背景图片
        canvas.drawBitmap(bgBitmap, null, dst, paint);
        //绘制奖品图片
        for (int index = 0; index < myGifts.size(); index++) {
            drawGiftBitmap(index % 2 == 0 ? 0xFFFFC300 : 0xFFF17E01, canvas, arcRect, centerx, centery, dis, index);
        }
        //绘制指针图片
        canvas.drawBitmap(centerBitmap, centerx - centerBitmap.getWidth() / 2, centery - centerBitmap.getHeight() / 2, paint);
    }

    /**
     * 绘制奖品图片
     *
     * @param color   背景颜色
     * @param canvas  画布
     * @param arcRect 扇形
     * @param centerx x轴中心
     * @param centery y轴中心
     * @param dis     距离
     * @param index   角标
     */
    private void drawGiftBitmap(int color, Canvas canvas, RectF arcRect, int centerx, int centery, float dis, int index) {
        paint.setColor(color);
        canvas.drawArc(arcRect, startAngle + index * SWEEP_ANGLE, SWEEP_ANGLE, true, paint);
        double radians = Math.toRadians(startAngle + SWEEP_ANGLE / 2 + index * SWEEP_ANGLE);
        int x = (int) (centerx + dis * Math.cos(radians)) - myGifts.get(index).getBmp().getWidth() / 2;
        int y = (int) (centery + dis * Math.sin(radians)) - myGifts.get(index).getBmp().getHeight() / 2;

        canvas.drawBitmap(myGifts.get(index).getBmp(), x, y, paint);
        myGifts.get(index).setStartAngle(startAngle + index * SWEEP_ANGLE);
        myGifts.get(index).setEndAngle(myGifts.get(index).getStartAngle() + SWEEP_ANGLE);
    }
}
