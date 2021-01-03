package com.gamecodeschool.greatwar;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.Log;

public class PlayerSpec {private static final String tag = "Player";
    private static final String bitmapName = "player";
    private static final int mCountOfFrames = 25;
    private static final float speed = 10f;
    private static final PointF size = new PointF(500f, 500f);
    private Bitmap mBitmap;
    private AnimationEngine mAnimationEngine;
    private Context mContext;
    private Rect mSectionToDraw;
    private Point mLocation;
    private Point mScreenSize;


    public PlayerSpec(Context c,  Point startingLocation, Point screenSize ) {
        Log.e("Метка", " был создан обьект PlayerSpec");
        mLocation = startingLocation;
        Log.e("Метка", " mLocation = " + mLocation.x + " " + mLocation.y);


        mScreenSize = screenSize;
        mContext = c;
        initialize();

        mAnimationEngine = new AnimationEngine( size.x , size.y, mCountOfFrames);
        mSectionToDraw = mAnimationEngine.getCurrentFrame(System.currentTimeMillis());
    }


    private void initialize(){
        int resID = mContext.getResources().getIdentifier(bitmapName, "drawable", mContext.getPackageName());
        mBitmap = BitmapFactory.decodeResource(mContext.getResources(),resID);
        mBitmap = Bitmap.createScaledBitmap(mBitmap, (int)size.x*mCountOfFrames, (int)size.y, false);
        Log.e("Метка", " инициализация прошла, картинка загружена");
    }

    public void draw(Canvas canvas, Paint paint){
        Log.e("Метка", " Метод PlayerSpec.draw() выполняется");
        mSectionToDraw = mAnimationEngine.getCurrentFrame(System.currentTimeMillis());
        canvas.drawBitmap(mBitmap, mSectionToDraw, new Rect(500,500,1000,1000), paint);


    }
}