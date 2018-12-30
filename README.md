###摇奖转盘

调用代码:

        int[] giftImgs = {R.drawable.danfan, R.drawable.f040, R.drawable.ipad, R.drawable.f040, R.drawable.iphone, R.drawable.f040, R.drawable.meizi, R.drawable.f040};
        String[] giftNames = {"单反", "笑脸1", "iPad", "笑脸2", "iPhone", "笑脸3", "妹子", "笑脸4"};
        MyLotteryWheel lotterywheel = findViewById(R.id.lotterywheel);
        lotterywheel.setGiftImgs(giftImgs);
        lotterywheel.setGiftNames(giftNames);
        //设置转动速度,可选配置
        lotterywheel.setSpeed(5);
        //初始化,必须设置项
        lotterywheel.init();
        lotterywheel.setOnSelectListener(new SelectListener() {
            @Override
            public void onSelect(MyGift myGift) {
                Toast.makeText(getApplicationContext(), "恭喜您,中到了" + myGift.getName(), Toast.LENGTH_LONG).show();
            }
        });
        
 效果图:
 
 ![效果图](https://upload-images.jianshu.io/upload_images/8903781-c0c07208616286a4.gif?imageMogr2/auto-orient/strip)