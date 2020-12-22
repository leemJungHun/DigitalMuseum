package com.example.digitalmuseum.adapter;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.digitalmuseum.MainActivity;
import com.example.digitalmuseum.R;
import com.example.digitalmuseum.fragment.ContentsFragment;
import com.example.digitalmuseum.network.HttpRequestService;
import com.example.digitalmuseum.network.vo.DataVO;
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

public class SmallRecyclerAdapter2 extends RecyclerView.Adapter<SmallRecyclerAdapter2.ViewHolder> implements View.OnClickListener {
    private ArrayList<SmallVO> mData = null ;
    private ArrayList<SmallVO> mData2 = null ;
    private ArrayList<SmallVO> mData3 = null ;
    private ArrayList<DataVO> dataVOS = new ArrayList<>();
    private MainActivity activity;
    private Bundle result;
    private Context context;
    private int count=0;
    private boolean isClick = false;
    String URL = "http://10.20.170.240:8080"; //

    // 생성자에서 데이터 리스트 객체를 전달받음.
    public SmallRecyclerAdapter2(MainActivity activity, Bundle result, Context context) {
        this.activity = activity;
        this.result = result;
        this.context = context;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    @NonNull
    public SmallRecyclerAdapter2.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        View view = LayoutInflater.from(context).inflate(R.layout.item_small2, parent, false) ;
        return new ViewHolder(view);
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(SmallRecyclerAdapter2.ViewHolder holder, int position) {

        count = position/2;
        SmallVO item = mData.get(count);
        SmallVO item2 = null;
        SmallVO item3 = null;

        if(count<mData2.size()){
            item2 = mData2.get(count);
        }

        if(count<mData3.size()){
            item3 = mData3.get(count);
        }

        Log.d("아이템 크기", " " + getItemCount());
        Log.d("아이템 셋", " " + position);
        Log.d("case", " " + position%2);
        //holder.scheduleIcon.setImageDrawable(item.getIcon()) ;

        GradientDrawable drawable=
                (GradientDrawable) context.getDrawable(R.drawable.radius_line);
        if (position==0){
            holder.startMargin.setVisibility(View.VISIBLE);
        }else {
            holder.startMargin.setVisibility(View.GONE);
        }

        if(position==getItemCount()-1){
            holder.endMargin.setVisibility(View.VISIBLE);
            holder.endMargin2.setVisibility(View.VISIBLE);
        }else {
            holder.endMargin.setVisibility(View.GONE);
            holder.endMargin2.setVisibility(View.GONE);
        }


        switch (position%2){
            case 0:
                holder.pattern1.setVisibility(View.VISIBLE);
                holder.pattern2.setVisibility(View.GONE);
                holder.pattern3.setVisibility(View.GONE);
                holder.smallText.setText(item.getTitle());
                holder.smallText.setTag(R.string.code,item.getCode());
                holder.smallImage.setTag(R.string.code,item.getCode());

                holder.smallText.setTag(R.string.title,item.getTitle());
                holder.smallImage.setTag(R.string.title,item.getTitle());
                holder.smallImage.setImageResource(R.drawable.kyodong_img_004_01);
                holder.smallText.setOnClickListener(this);
                holder.smallImage.setOnClickListener(this);
                holder.smallImage.setBackground(drawable);
                holder.smallImage.setClipToOutline(true);
                if(item.getImage()!=null){
                    Log.d("getImage"," " + URL+item.getImage());
                    Glide.with(activity).load(URL + item.getImage()).thumbnail(0.01f).into(holder.smallImage);
                }
                break;
            case 1:
                if(item2!=null){
                    holder.pattern1.setVisibility(View.GONE);
                    holder.pattern2.setVisibility(View.VISIBLE);
                    holder.smallText2.setText(item2.getTitle());
                    holder.smallText2.setTag(R.string.code,item2.getCode());
                    holder.smallImage2.setTag(R.string.code,item2.getCode());
                    holder.smallText2.setTag(R.string.title,item2.getTitle());
                    holder.smallImage2.setTag(R.string.title,item2.getTitle());
                    holder.smallImage2.setImageResource(R.drawable.kyodong_img_004_01);
                    holder.smallText2.setOnClickListener(this);
                    holder.smallImage2.setOnClickListener(this);
                    holder.smallImage2.setBackground(drawable);
                    holder.smallImage2.setClipToOutline(true);
                    if(item2.getImage()!=null){
                        Log.d("getImage"," " + URL+item2.getImage());
                        Glide.with(activity).load(URL + item2.getImage()).thumbnail(0.01f).into(holder.smallImage2);
                    }
                }
                if(item3!=null){
                    holder.pattern3.setVisibility(View.VISIBLE);
                    holder.smallText3.setText(item3.getTitle());
                    holder.smallText3.setTag(R.string.code,item3.getCode());
                    holder.smallImage3.setTag(R.string.code,item3.getCode());
                    holder.smallText3.setTag(R.string.title,item3.getTitle());
                    holder.smallImage3.setTag(R.string.title,item3.getTitle());
                    holder.smallImage3.setImageResource(R.drawable.kyodong_img_004_01);
                    holder.smallText3.setOnClickListener(this);
                    holder.smallImage3.setOnClickListener(this);
                    holder.smallImage3.setBackground(drawable);
                    holder.smallImage3.setClipToOutline(true);
                    if(item3.getImage()!=null){
                        Log.d("getImage"," " + URL+item3.getImage());
                        Glide.with(activity).load(URL + item3.getImage()).thumbnail(0.01f).into(holder.smallImage3);
                    }
                }else{
                    holder.pattern3.setVisibility(View.GONE);
                }

                break;
        }


    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return mData.size() + mData2.size();
    }

    public void updateData(ArrayList<SmallVO> smallVOS1, ArrayList<SmallVO> smallVOS2, ArrayList<SmallVO> smallVOS3) {
        if(mData!=null) {
            mData.clear();
        }else{
            mData = new ArrayList<>();
        }
        mData.addAll(smallVOS1);
        if(mData2!=null) {
            mData2.clear();
        }else{
            mData2 = new ArrayList<>();
        }
        mData2.addAll(smallVOS2);
        if(mData3!=null) {
            mData3.clear();
        }else{
            mData3 = new ArrayList<>();
        }
        mData3.addAll(smallVOS3);
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        if(!isClick){
            isClick=true;
            Log.d("클릭"," "+view.getTag(R.string.code).toString());
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(HttpRequestService.URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            HttpRequestService httpRequestService = retrofit.create(HttpRequestService.class);

            httpRequestService.getContentsList(view.getTag(R.string.code).toString()).enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if(response.isSuccessful() && response.body() != null){
                        Gson gson = new Gson();
                        if(!response.body().get("data").toString().equals("null")){
                            JsonObject jsonObject = response.body().getAsJsonObject("data");
                            JsonArray jsonArray;
                            if(jsonObject.get("datas")!=null){
                                jsonArray = jsonObject.getAsJsonArray("datas");
                                for (JsonElement element : jsonArray) {
                                    DataVO dataVO = gson.fromJson(element, DataVO.class);
                                    Log.d("getTitle", " " + dataVO.getTitle());
                                    Log.d("getProductedAt", " " + dataVO.getProductedAt());
                                    Log.d("getCode", " " + dataVO.getCode());
                                    dataVOS.add(dataVO);
                                }
                                result.putParcelableArrayList("dataVOS", dataVOS);
                                result.putString("type", "small");
                            }
                            result.putString("SCode", view.getTag(R.string.code).toString());
                            result.putString("ContentTitle", view.getTag(R.string.title).toString());

                            ((MainActivity) Objects.requireNonNull(activity)).setStartFragment(new ContentsFragment(),false, result);
                        }
                        else{
                            Log.d("data","null");
                            isClick=false;
                        }
                        isClick=false;
                    }

                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    isClick=false;
                }
            });
        }

    }
    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView smallImage ;
        TextView smallText ;
        ImageView smallImage2 ;
        TextView smallText2 ;
        ImageView smallImage3 ;
        TextView smallText3 ;
        ConstraintLayout pattern1;
        ConstraintLayout pattern2;
        ConstraintLayout pattern3;
        ConstraintLayout startMargin;
        ConstraintLayout endMargin;
        ConstraintLayout endMargin2;

        ViewHolder(View itemView) {
            super(itemView) ;

            // 뷰 객체에 대한 참조. (hold strong reference)
            smallText = itemView.findViewById(R.id.patternText1) ;
            smallText2 = itemView.findViewById(R.id.patternText2) ;
            smallText3 = itemView.findViewById(R.id.patternText3) ;
            smallImage = itemView.findViewById(R.id.patternImage1) ;
            smallImage2 = itemView.findViewById(R.id.patternImage2) ;
            smallImage3 = itemView.findViewById(R.id.patternImage3) ;
            pattern1 = itemView.findViewById(R.id.pattern_01);
            pattern2 = itemView.findViewById(R.id.pattern_02);
            pattern3 = itemView.findViewById(R.id.pattern_03);


            startMargin = itemView.findViewById(R.id.start_margin);
            endMargin = itemView.findViewById(R.id.end_margin);
            endMargin2 = itemView.findViewById(R.id.end_margin2);

        }
    }
}
