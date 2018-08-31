package com.taoge.myrecoderdemo.view;

import android.media.MediaRecorder;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * created by：TangTao on 2018/8/30 10:15
 * <p>
 * email：xxx@163.com
 */
public class MyAudioManager {

    private MediaRecorder mMediaRecorder;
    private String mDir;
    private String mCurrentFilePath;

    private static MyAudioManager mInstance;

    private boolean isPrepared;

    private MyAudioManager(String dir) {
        mDir=dir;
    }

    public String getCurrentFilePath() {
        return mCurrentFilePath;
    }

    /**
     * 回调准备完毕
     */
    public interface MyAudioStateListener {
        void wellPrepared();
    }


    public MyAudioStateListener mListener;

    public void setOnAudioStateListener(MyAudioStateListener listener) {
        this.mListener = listener;
    }

    public static MyAudioManager getInstance(String dir) {

        if (mInstance == null) {
            synchronized (MyAudioManager.class) {
                if (mInstance == null) {
                    mInstance = new MyAudioManager(dir);
                }
            }
        }
        return mInstance;
    }


    //准备
    public void prepareAudio() {

        try {

            isPrepared=false;

            File dir = new File(mDir);
            if (!dir.exists())
                dir.mkdir();

            String fileName = generateFileName();
            File file = new File(dir, fileName);

            mCurrentFilePath=file.getAbsolutePath();
            mMediaRecorder = new MediaRecorder();
            //设置输出文件
            mMediaRecorder.setOutputFile(file.getAbsolutePath());
            //设置MediaRecorder的音频源为麦克风
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            //设置音频格式
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR);
            //设置音频编码
            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

            mMediaRecorder.prepare();
            mMediaRecorder.start();
            //准备结束
            isPrepared = true;

            if (mListener != null) {
                mListener.wellPrepared();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 随机生成文件的名称
     *
     * @return
     */
    private String generateFileName() {

        return UUID.randomUUID().toString() + ".amr";
    }


    //获取音量等级
    public int getVoiceLevel(int maxLevel) {

        if (isPrepared) {

            try { //有可能获取不到振幅，抛异常，在这里捕获异常
                //mMediaRecorder.getMaxAmplitude()  1-32767
                return maxLevel * mMediaRecorder.getMaxAmplitude() / 32768 + 1;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return 1;
    }


    //释放资源
    public void release() {

        mMediaRecorder.stop();
        mMediaRecorder.release();
        mMediaRecorder = null;
    }


    //取消
    public void cancel() {

        release();
        if (mCurrentFilePath != null) {

            File file = new File(mCurrentFilePath);
            file.delete();
            mCurrentFilePath=null;
        }
    }
}
