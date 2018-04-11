package com.erze.yqj.moudle.shortvideo.recordclip;

import android.os.Handler;
import android.view.View;

import com.erze.yqj.R;
import com.erze.yqj.moudle.shortvideo.RecordActivity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * record progress controller
 */

public class RecordProgressController {
    private static final String TAG = "RecordProgressController";
    private Handler mHandler;
    private RecordProgressView mProgressView;
    private ChangeProgressRunnable mProgressRunnable;
    private RecordProgressTimer mProgressTimer;
    private long mStartRecordingTime;
    private boolean mIsRecording;
    private LinkedList<RecordClipModel> mProgressClipList;

    private List<RecordingStateChanged> mRecordStateChangedListeners;
    private RecordingLengthChangedListener mRecordingLengthChangedListener;

    /**
     *
     * @param rootView  拍摄进度显示的父控件
     */
    public RecordProgressController(View rootView) {
        mHandler = new Handler();
        mProgressView = (RecordProgressView) rootView.findViewById(R.id.record_progress);
        mProgressRunnable = new ChangeProgressRunnable();
        mRecordStateChangedListeners = new ArrayList<>();
        mProgressTimer = new RecordProgressTimer();
        mProgressTimer.setProgressUpdateListener(mProgressUpdateListener);
        mRecordStateChangedListeners.add(mProgressTimer);

        mStartRecordingTime = 0;
        mIsRecording = false;
        mProgressClipList = new LinkedList<>();

        mProgressView.setProgressClipList(mProgressClipList);
        mRecordStateChangedListeners.add(mProgressView);
    }

    private class ChangeProgressRunnable implements Runnable {
        @Override
        public void run() {
            if (mProgressView.mTotalWidth >= mProgressView.mScreenWidth) {
                mProgressView.invalidate();
                if (mIsRecording && mRecordingLengthChangedListener != null) {
                    mRecordingLengthChangedListener.passMaxPoint();
                }
                mIsRecording = false;
            }
            mRecordingLengthChangedListener.passMinPoint(isPassMinPoint());

            mProgressView.invalidate();
        }
    }

    /**
     * 是否到达了最小录制时长
     *
     * @return
     */
    public boolean isPassMinPoint() {
        long recordedTime = 0;
        for (RecordClipModel clip : mProgressClipList) {
            recordedTime += clip.timeInterval;
        }
        return recordedTime >= RecordActivity.MIN_DURATION;
    }

    /**
     * 是否到达了最大录制时长
     *
     * @return
     */
    public boolean isPassMaxPoint() {
        return mProgressView.isPassMaxPoint();
    }

    /**
     * 进入录制页面Timer即可启动，用于随时更新录制的进度
     */
    public void start() {
        mProgressTimer.start();
    }

    public void stop() {
        mProgressTimer.stop();
    }

    /**
     * startRecord
     */
    public void startRecording() {
        if (mIsRecording) return;
        mStartRecordingTime = System.currentTimeMillis();
        mIsRecording = true;
        RecordClipModel clip = new RecordClipModel();
        clip.timeInterval = 0;
        clip.state = 0;
        mProgressClipList.add(clip);

        for (RecordingStateChanged listener : mRecordStateChangedListeners) {
            listener.recordingStart(mStartRecordingTime);
        }
    }

    public void release() {
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }

        mProgressTimer.stop();
        mProgressTimer.setProgressUpdateListener(null);
        mRecordStateChangedListeners.clear();
        mProgressClipList.clear();
        mProgressView.release();
    }

    /**
     * stop record
     */
    public void stopRecording() {
        mIsRecording = false;
        if (!mProgressClipList.isEmpty()) {
            mProgressClipList.getLast().state = 1;
            mProgressClipList.getLast().timeInterval += 20;
            mHandler.post(mProgressRunnable);
        }

        for (RecordingStateChanged listener : mRecordStateChangedListeners) {
            listener.recordingStop();
        }
    }

    /**
     * 删除最后一个录制的视频
     */
    public void rollback() {
        mIsRecording = false;
        if (!mProgressClipList.isEmpty()) {
            mProgressClipList.removeLast();
            mHandler.post(mProgressRunnable);
        }
    }

    /**
     * 设置最后一个file为待删除文件
     */
    public void setLastClipPending() {
        if (!mProgressClipList.isEmpty()) {
            mProgressClipList.getLast().state = 2;
            mHandler.post(mProgressRunnable);
        }
    }

    /**
     * 设置最后一个file为普通文件
     */
    public void setLastClipNormal() {
        if (!mProgressClipList.isEmpty()) {
            mProgressClipList.getLast().state = 1;
            mHandler.post(mProgressRunnable);
        }
    }

    public int getClipListSize() {
        return mProgressClipList.size();
    }

    /**
     * 只是预估时间，实际录制时长已视频为准
     *
     * @return
     */
    public int getRecordedTime() {
        return mProgressView.mTotalWidth * RecordActivity.MAX_DURATION / mProgressView
                .mScreenWidth;
    }

    public boolean getIsRecording() {
        return mIsRecording;
    }

    public long getStartRecordingTime() {
        return mStartRecordingTime;
    }

    /**
     * 用于通知最短拍摄时长和最长拍摄时长
     * @param listener
     */
    public void setRecordingLengthChangedListener(RecordingLengthChangedListener listener) {
        mRecordingLengthChangedListener = listener;
    }

    private RecordProgressTimer.ProgressUpdateListener mProgressUpdateListener = new RecordProgressTimer.ProgressUpdateListener() {
        @Override
        public void updateProgress(long interval) {
            if (!mProgressClipList.isEmpty()) {
                RecordClipModel clip = mProgressClipList.getLast();
                clip.timeInterval = interval;
                mHandler.post(mProgressRunnable);
            }
        }
    };

    public interface RecordingStateChanged {
        void recordingStart(long startTime);

        void recordingStop();
    }

    public interface RecordingLengthChangedListener {
        void passMinPoint(boolean pass);  //拍摄超过了最短时长

        void passMaxPoint();  //拍摄超过了最长时长
    }
}
