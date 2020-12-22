package com.example.digitalmuseum.adapter;

import android.app.Activity;
import android.content.Context;
import android.opengl.EGL14;
import android.opengl.EGLConfig;
import android.opengl.EGLContext;
import android.opengl.EGLDisplay;
import android.opengl.EGLSurface;
import android.opengl.GLES20;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.digitalmuseum.MainActivity;
import com.example.digitalmuseum.R;
import com.example.digitalmuseum.fragment.ContentsFragment;
import com.example.digitalmuseum.fragment.SmallFragment;
import com.example.digitalmuseum.network.HttpRequestService;
import com.example.digitalmuseum.network.vo.DataVO;
import com.example.digitalmuseum.network.vo.MediumVO;
import com.example.digitalmuseum.network.vo.SmallVO;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HistoryRecyclerAdapter extends RecyclerView.Adapter<HistoryRecyclerAdapter.ViewHolder> {
    MainActivity activity;
    Context context;
    public HistoryRecyclerAdapter(MainActivity activity,Context context) {
        this.activity = activity;
        this.context = context;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    @NonNull
    public HistoryRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        View view = LayoutInflater.from(context).inflate(R.layout.item_history, parent, false) ;
        return new ViewHolder(view);
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(HistoryRecyclerAdapter.ViewHolder holder, int position) {

        Log.d("아이템 셋", " " + position);
        ((MainActivity) Objects.requireNonNull(activity)).Restart_Period();
        //holder.scheduleIcon.setImageDrawable(item.getIcon()) ;
        switch (position){
            case 0:
                holder.historyImg.setImageResource(R.drawable.kyodong_003_01);
                break;
            case 1:
                holder.historyImg.setImageResource(R.drawable.kyodong_003_02_01);
                break;
            case 2:
                holder.historyImg.setImageResource(R.drawable.kyodong_003_02_02);
                break;
            case 3:
                holder.historyImg.setImageResource(R.drawable.kyodong_003_03_01);
                break;
            case 4:
                holder.historyImg.setImageResource(R.drawable.kyodong_003_03_02);
                break;
            case 5:
                holder.historyImg.setImageResource(R.drawable.kyodong_003_04_01);
                break;
            case 6:
                holder.historyImg.setImageResource(R.drawable.kyodong_003_04_02);
                break;
            case 7:
                holder.historyImg.setImageResource(R.drawable.kyodong_003_05_01);
                break;
            case 8:
                holder.historyImg.setImageResource(R.drawable.kyodong_003_05_02);
                break;
            case 9:
                holder.historyImg.setImageResource(R.drawable.kyodong_003_05_03);
                break;
            case 10:
                holder.historyImg.setImageResource(R.drawable.kyodong_003_05_04);
                break;
            case 11:
                holder.historyImg.setImageResource(R.drawable.kyodong_003_06_01);
                break;
            case 12:
                holder.historyImg.setImageResource(R.drawable.kyodong_003_06_02);
                break;
            case 13:
                holder.historyImg.setImageResource(R.drawable.kyodong_003_06_03);
                break;

        }

    }


    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return 14 ;
    }

    public void updateData() {
        notifyDataSetChanged();
    }

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView historyImg ;

        ViewHolder(View itemView) {
            super(itemView) ;

            // 뷰 객체에 대한 참조. (hold strong reference)
            historyImg = itemView.findViewById(R.id.historyItemImage);

        }
    }
}
