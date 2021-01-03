package com.gamecodeschool.greatwar;

import android.content.Context;
import android.graphics.Point;
import android.graphics.PointF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;



public class GameEngine extends SurfaceView
    implements Runnable, EngineController{


    private Thread mThread = null;
    // Изменить на private
    public static long mFPS = 0;

    private GameState mGameState;
    Renderer mRenderer;
    PlayerSpec mPlayerSpec;
    Point mScreenSize;




    public GameEngine(Context context, Point size){
        super(context);

        mScreenSize = size;

        mGameState = new GameState(this, context);
        mRenderer = new Renderer(this, size);
        mPlayerSpec = new PlayerSpec(context, new Point(100,500), mScreenSize);
    }



    @Override
    public void run() {
        while (mGameState.getThreadRunning()){
            long frameStartTime = System.currentTimeMillis();

            if(!mGameState.getPaused()){
                // Перемещаем
            }

            mRenderer.draw(mPlayerSpec, mGameState);

            long timeThisFrame = System.currentTimeMillis() - frameStartTime;

            if( timeThisFrame >= 1){
                final int MILLIS_IN_SECOND = 1000;


            }
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    public void stopThread(){

        mGameState.stopEverything();
        mGameState.stopThread();

        try {
            mThread.join();
        } catch (InterruptedException e){
            Log.e("Exception ", "stopThread()" + e.getMessage());
        }

    }

    public void startThread(){
        mGameState.startThread();
        mThread = new Thread(this);
        mThread.start();

    }

    @Override
    public void startNewLevel() {

    }
}
