package com.gamecodeschool.greatwar;

import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class Renderer {
    private Canvas mCanvas;
    private SurfaceHolder mSurfaceHolder;
    private Paint mPaint;

    private Camera mCamera;

    Renderer(SurfaceView sh, Point screenSize){
        mSurfaceHolder = sh.getHolder();
        mPaint = new Paint();
    }

    void draw( PlayerSpec ps, GameState gs){
        if( mSurfaceHolder.getSurface().isValid()){
            mCanvas = mSurfaceHolder.lockCanvas();
            mCanvas.drawColor(Color.argb(255, 222,16,16));
            Log.e("Метка", " Метод Renderer.draw() выполняется");
            ps.draw(mCanvas, mPaint);

            if(gs.getDrawing()){
             ps.draw(mCanvas, mPaint);
            }
            mSurfaceHolder.unlockCanvasAndPost(mCanvas);
        }
    }

}
