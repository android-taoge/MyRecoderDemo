package com.taoge.myrecoderdemo.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.taoge.myrecoderdemo.MainActivity;
import com.taoge.myrecoderdemo.R;

import java.util.List;

/**
 * created by：TangTao on 2018/8/30 13:58
 * <p>
 * email：xxx@163.com
 */
public class RecorderAdapter extends ArrayAdapter<MainActivity.Recorder> {


    private int mMinItemWidth;
    private int mMaxItemWidth;
    private LayoutInflater mInflater;


    public RecorderAdapter(@NonNull Context context, List<MainActivity.Recorder> datas) {
        super(context, -1, datas);
        mInflater=LayoutInflater.from(context);

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            wm.getDefaultDisplay().getRealMetrics(outMetrics);
        }
        mMaxItemWidth = (int) (outMetrics.widthPixels * 0.7f);
        mMinItemWidth = (int) (outMetrics.widthPixels * 0.15f);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder= null;
        if(convertView==null){

            convertView=mInflater.inflate(R.layout.item_recorder,parent,false);
            holder=new ViewHolder();
            holder.seconds=convertView.findViewById(R.id.id_recorder_time);
            holder.length=convertView.findViewById(R.id.id_recorder_length);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }

        holder.seconds.setText(Math.round(getItem(position).getTime())+"\"");
        ViewGroup.LayoutParams lp=holder.length.getLayoutParams();
        lp.width= (int) (mMinItemWidth+mMaxItemWidth/60f*getItem(position).getTime());

        return convertView;

    }

    private class ViewHolder{
        TextView seconds;
        View length;
    }
}
