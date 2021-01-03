package com.gamecodeschool.greatwar;


import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;



// Готов
//
// Считывает размеры экрана через WindowManager и присваивает size
// через ссылку
// Запускает и останавливает цикл


public class GameActivity extends Activity {

    GameEngine mGameEngine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        mGameEngine = new GameEngine(this, size);
        setContentView(mGameEngine);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mGameEngine.startThread();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mGameEngine.stopThread();
    }
}
