package com.gamecodeschool.greatwar;
import android.content.Context;
import android.content.SharedPreferences;

// Необходимо изменить под проект

// Содержит состяния игры, состояние игрового потока
// Может начинать игру с метода, описанного в GameEngine
// Содержит лучшие результаты времени прохождения уровней
// Ведет подсчет текущих монет и текущего времени
// Сореждит состояние текущего уровня
// Умеет останавливать игровой поток и имеет еще кучу геттеров типа текущего времени игры

// Состояние mDrawing
// Используется в Render.draw() прорисовывает только когда обьекты будут загружены
// .
public class GameState {

    private static volatile boolean mThreadRunning = false;
    private static volatile boolean mPaused = true;
    private static volatile boolean mGameOver = true;
    private static volatile boolean mDrawing = false;
    private EngineController mEngineController;
    private int mFastestUnderground;
    private int mFastestMountains;
    private int mFastestCity;
    private long mStartTimeMilis;
    private int mCoinsAvailable;
    private int mCoinsCollected;
    private SharedPreferences.Editor mEditor;
    private String mCurrentLevel;

    GameState(EngineController gs, Context context){
        mEngineController = gs;
        SharedPreferences prefs = context.getSharedPreferences("HiScore", Context.MODE_PRIVATE);
        mEditor = prefs.edit();
        mFastestUnderground = prefs.getInt("fastest_underground_time", 1000);
        mFastestMountains = prefs.getInt("fastest_mountains_time", 1000);
        mFastestCity = prefs.getInt("fastest_city_time", 1000);
    }

    void coinCollected(){ mCoinsCollected++; }
    int getCoinsRemaing(){ return mCoinsAvailable - mCoinsCollected; }
    void coinAddedToLevel(){ mCoinsAvailable++; }
    void resetCoins(){ mCoinsAvailable = 0; mCoinsCollected = 0; }
    void setCurrentLevel(String level){ mCurrentLevel = level; }
    String getCurrentLevel(){ return mCurrentLevel; }
    void objectiveReached(){ endGame();}
    int getFastestUnderground(){ return mFastestUnderground; }
    int getFastestMountains(){ return mFastestMountains; }
    int getFastestCity(){ return mFastestCity; }
    void stopThread(){ mThreadRunning = false; }
    boolean getThreadRunning(){return mThreadRunning; }
    void startThread(){ mThreadRunning = true; }
    boolean getDrawing(){ return mDrawing; }
    boolean getPaused(){ return mPaused; }
    boolean getGameOver(){ return mGameOver; }

    void death(){
        stopEverything();
        SoundEngine.playPlayerBurn();
    }

    private void endGame() {
        stopEverything();
        int totalTime = getCurrentTime();

        switch (mCurrentLevel){
            case "underground":
                if (totalTime < mFastestUnderground){
                    mFastestUnderground = totalTime;
                    mEditor.putInt("fastest_underground_time", mFastestUnderground);
                }
                break;
            case "city":
                if(totalTime < mFastestCity){
                    mFastestCity = totalTime;
                    mEditor.putInt("fastest_city_time", mFastestCity);
                    mEditor.commit();
                }
                break;
            case "montains":
                if( totalTime < mFastestMountains){
                    mFastestMountains = totalTime;
                    mEditor.putInt("fastest_mountains_time", mFastestMountains);
                    mEditor.commit();
                }
                break;
        }
    }

    private int getCurrentTime() {
        long MILLIS_IN_SECOND = 1000;
        return (int)((System.currentTimeMillis() - mStartTimeMilis) / MILLIS_IN_SECOND);
    }

    void startNewGame(){
        stopEverything();
        mEngineController.startNewLevel();
        startEverything();
        mStartTimeMilis = System.currentTimeMillis();
    }

    private void startEverything() {
        mPaused = false;
        mGameOver = false;
        mDrawing = true;
    }

    void stopEverything() {
        mPaused = true;
        mGameOver = true;
        mDrawing = false;
    }

}
