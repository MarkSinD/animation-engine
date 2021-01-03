package com.gamecodeschool.greatwar;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

import java.io.IOException;


// Необходимо изменить под проект

// Поддержка mp3, ogg, wav, aac, 3gp, flac
// Размер файла, который может проиграть SoundPool не должен превышать 1 мегабайт.
// Диапазон скорости воспроизведения составляет от 0,5 до 2,0.
// Можно программно задать количество аудио потоков, проигрываемых одновременно. Если максимальное число потоков будет превышено, автоматически остановится поток с самым низкий приоритетом. При наличии нескольких потоков с тем же низким приоритетом, будет выбран старейший из них. В случае, если приоритет нового потока ниже,
// чем все активные потоки, новый звук не будет проигран.
// Ограничение максимального числа потоков помогает снизить загрузку процессора.

// SoundPool (int maxStreams, int streamType, int srcQuality)
//      аргументы:
// maxStreams - максимальное количество потоков, который могут воспроизводится одновременно
// streamType - тип аудиопотока, это константа из класса AudioManager. Здесь чаще всего используется AudioManager.STREAM_MUSIC.
// srcQuality - качество кодирования. Сейчас этот параметр не используется, поэтому всегда будем передавать 0.
//
// int play(int soundID, float leftVolume, float rightVolume, int priority, int loop, float rate)
// soundID - идентификатор звука (который вернул load())
// leftVolume - уровень громкости для левого канала (от 0.0 до 1.0)
// rightVolume - уровень громкости для правого канала (от 0.0 до 1.0)
// priority - приоритет потока (0 - самый низкий)
// loop - количество повторов (0 - без повторов, (-1) - зациклен)
// rate - скорость воспроизведения (от 0.5 до 2.0)
//
// Метод возвращает целое число int, которое используется в других методах в качестве параметра streamID.
//
// void stop(int streamID)
// void pause(int streamID)
// void resume(int streamID)
// void setLoop(int streamID, int loop)
// void setPriority(int streamID, int priority)
// void setRate(int streamID, float rate)
// void setVolume(int streamID, float leftVolume, float rightVolume)
// void autoPause()
// void autoResume()
// boolean unload(int soundID)
// void release()
//
//
// /


public class SoundEngine {

    private static SoundPool mSP;
    private static int mJump_ID = -1;
    private static int mReach_Objective_ID = 1;
    private static int mCoin_Pickup_ID = -1;
    private static int mPlayer_Burn_ID = -1;
    private static SoundEngine ourInstance;


    public  static SoundEngine getInstance(Context context){
        ourInstance = new SoundEngine(context);
        return ourInstance;
    }

    public SoundEngine( Context c){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            AudioAttributes audioAttributes = new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_MEDIA).setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build();
            mSP = new SoundPool.Builder().setMaxStreams(5).setAudioAttributes(audioAttributes).build() ;}
        else{
            mSP = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        }

        try{
            AssetManager assetManager = c.getAssets();
            AssetFileDescriptor descriptor;
            descriptor = assetManager.openFd("jump.ogg");
            mJump_ID = mSP.load(descriptor, 0);

            descriptor = assetManager.openFd("reach_objective.ogg");
            mReach_Objective_ID = mSP.load(descriptor, 0);

            descriptor = assetManager.openFd("coin_pickup.ogg");
            mCoin_Pickup_ID = mSP.load(descriptor, 0);

            descriptor = assetManager.openFd("player_burn.ogg");
            mPlayer_Burn_ID = mSP.load(descriptor, 0);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void playJump(){ mSP.play(mJump_ID, 1,1,0,0,1); }
    public static void playReachObjective(){ mSP.play(mReach_Objective_ID, 1,1,0,0,1); }
    public static void playCoinPickUp(){ mSP.play(mCoin_Pickup_ID, 1,1,0,0,1); }
    public static void playPlayerBurn() {mSP.play(mPlayer_Burn_ID, 1,1,0,0,1); }



}
