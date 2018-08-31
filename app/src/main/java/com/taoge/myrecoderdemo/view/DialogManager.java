package com.taoge.myrecoderdemo.view;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.taoge.myrecoderdemo.R;

/**
 * created by：TangTao on 2018/8/30 09:36
 * <p>
 * email：xxx@163.com
 */
public class DialogManager {

    private Dialog mDialog;

    private ImageView mIcon;
    private ImageView mVoice;
    private TextView mLabel;
    private Context mContext;

    public DialogManager(Context mContext) {
        this.mContext = mContext;
    }

    public void showRecordingDialog() {

        mDialog = new Dialog(mContext, R.style.Theme_AudioDialog);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.dialog_recorder, null);
        mDialog.setContentView(view);

        mIcon = mDialog.findViewById(R.id.id_recorder_dialog_icon);
        mVoice = mDialog.findViewById(R.id.id_recorder_dialog_voice);
        mLabel = mDialog.findViewById(R.id.id_recorder_dialog_label);

        mDialog.show();
    }


    public void recording() {
        if (mDialog != null && mDialog.isShowing()) {
            mIcon.setVisibility(View.VISIBLE);
            mVoice.setVisibility(View.VISIBLE);
            mLabel.setVisibility(View.VISIBLE);

            mIcon.setImageResource(R.drawable.recorder);
            mLabel.setText(R.string.up_to_cancel);

        }

    }


    public void wantToCancel() {
        if (mDialog != null && mDialog.isShowing()) {
            mIcon.setVisibility(View.VISIBLE);
            mVoice.setVisibility(View.GONE);
            mLabel.setVisibility(View.VISIBLE);

            mIcon.setImageResource(R.drawable.cancel);
            mLabel.setText(R.string.up_to_cancel);

        }

    }


    public void tooShort() {
        if (mDialog != null && mDialog.isShowing()) {
            mIcon.setVisibility(View.VISIBLE);
            mVoice.setVisibility(View.GONE);
            mLabel.setVisibility(View.VISIBLE);

            mIcon.setImageResource(R.drawable.voice_to_short);
            mLabel.setText("录音时间过短");

        }
    }


    public void dismissDialog() {

        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
            mDialog = null;
        }
    }



    //更新音量显示
    public void updateVoiceLevel(int level) {

        if (mDialog != null && mDialog.isShowing()) {
            //mIcon.setVisibility(View.VISIBLE);
            //mVoice.setVisibility(View.VISIBLE);
            //mLabel.setVisibility(View.VISIBLE);

            int resId=mContext.getResources().getIdentifier("v"+level,"drawable",mContext.getPackageName());
            mVoice.setImageResource(resId);
        }
    }
}
