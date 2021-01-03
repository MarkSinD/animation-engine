package com.gamecodeschool.greatwar;

import android.graphics.Rect;

public class AnimationEngine {
    private Rect mSourceRect;
    private int mFrameCount;
    private int mCurrentFrame;
    private long mFrameTicker;
    private int mFramePeriod;
    private int mFrameWidth;
    private int mFrameHeight;


    AnimationEngine( float frameWidth, float frameHeight, int frameCount){
        final int ANIM_FPS = 2;
        mCurrentFrame = 0;
        mFrameCount = frameCount;
        mFrameWidth = (int) frameWidth;
        mFrameHeight = (int) frameHeight;


        mSourceRect = new Rect(0,0, this.mFrameWidth, mFrameHeight);
        mFramePeriod = 100/ANIM_FPS;
        mFrameTicker = 0L;
    }

    Rect getCurrentFrame(long time){

        if(time > mFrameTicker + mFramePeriod){

            mFrameTicker = time;
            mCurrentFrame++;

            if( mCurrentFrame >= mFrameCount){
                mCurrentFrame = 0;
            }

        }

        this.mSourceRect.left = mCurrentFrame * mFrameWidth;
        this.mSourceRect.right = this.mSourceRect.left + mFrameWidth;

        return mSourceRect;
    }
}
