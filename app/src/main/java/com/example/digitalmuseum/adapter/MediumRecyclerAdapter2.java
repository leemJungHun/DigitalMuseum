package com.example.digitalmuseum.adapter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

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

public class MediumRecyclerAdapter2 extends RecyclerView.Adapter<MediumRecyclerAdapter2.ViewHolder> implements View.OnClickListener {
    private ArrayList<MediumVO> mData = null ;
    private ArrayList<SmallVO> smallVOS = new ArrayList<>();
    private ArrayList<DataVO> dataVOS = new ArrayList<>();
    private MainActivity activity;
    private Bundle result;
    private int itemCount = 0;

    // 생성자에서 데이터 리스트 객체를 전달받음.
    public MediumRecyclerAdapter2(MainActivity activity, Bundle result) {
        this.activity = activity;
        this.result = result;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    @NonNull
    public MediumRecyclerAdapter2.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        View view = LayoutInflater.from(context).inflate(R.layout.item_medium2, parent, false) ;
        return new ViewHolder(view);
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(MediumRecyclerAdapter2.ViewHolder holder, int position) {

        MediumVO item = mData.get(position) ;

        Log.d("아이템 크기", " " + mData.size());
        Log.d("아이템 셋", " " + position);


        //holder.scheduleIcon.setImageDrawable(item.getIcon()) ;
        holder.mediumText.setText(item.getTitle());
        holder.mediumText.setTag(item.getCode());
        holder.mediumImg.setTag(item.getCode());
        holder.mediumText.setOnClickListener(this);
        holder.mediumImg.setOnClickListener(this);
        switch (item.getCode()){
            case "M1":
                holder.mediumImg.setImageResource(R.drawable.kyodong_img_004_01);
                break;
            case "M2":
                holder.mediumImg.setImageResource(R.drawable.kyodong_img_004_02);
                break;
            case "M3":
                holder.mediumImg.setImageResource(R.drawable.kyodong_img_004_03);
                break;
            case "M4":
                holder.mediumImg.setImageResource(R.drawable.kyodong_img_004_04);
                break;
            case "M5":
                holder.mediumImg.setImageResource(R.drawable.kyodong_img_006_01);
                break;
            case "M6":
                holder.mediumImg.setImageResource(R.drawable.kyodong_img_006_02);
                break;
        }
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return itemCount ;
    }

    public void updateData(ArrayList<MediumVO> mediumList) {
        if(mData!=null) {
            mData.clear();
        }else{
            mData = new ArrayList<>();
        }
        mData.addAll(mediumList);
        itemCount = mData.size();
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HttpRequestService.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        HttpRequestService httpRequestService = retrofit.create(HttpRequestService.class);

        httpRequestService.getSmallList(view.getTag().toString()).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.isSuccessful() && response.body() != null){
                    Gson gson = new Gson();
                    if(!response.body().get("data").toString().equals("null")){
                        Fragment fragment;
                        JsonObject jsonObject = response.body().getAsJsonObject("data");
                        JsonArray jsonArray;
                        if(jsonObject.get("smalls")!=null){
                            jsonArray = jsonObject.getAsJsonArray("smalls");
                            for (JsonElement element : jsonArray) {
                                SmallVO smallVO = gson.fromJson(element, SmallVO.class);
                                Log.d("getTitle", " " + smallVO.getTitle());
                                Log.d("getImage", " " + smallVO.getImage());
                                Log.d("getCode", " " + smallVO.getCode());
                                smallVOS.add(smallVO);
                            }
                            fragment = new SmallFragment();
                            result.putParcelableArrayList("smallVOS", smallVOS);
                        }else{
                            jsonArray = jsonObject.getAsJsonArray("datas");
                            for (JsonElement element : jsonArray) {
                                DataVO dataVO = gson.fromJson(element, DataVO.class);
                                Log.d("getTitle", " " + dataVO.getTitle());
                                Log.d("getProductedAt", " " + dataVO.getProductedAt());
                                Log.d("getCode", " " + dataVO.getCode());
                                dataVOS.add(dataVO);
                            }
                            fragment = new ContentsFragment();
                            result.putParcelableArrayList("dataVOS", dataVOS);
                            result.putString("type", "medium");
                        }


                        result.putString("MCode", view.getTag().toString());

                        ((MainActivity) Objects.requireNonNull(activity)).setStartFragment(fragment,false, result);
                    }
                    else{
                        Log.d("data","null");
                    }

                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mediumImg ;
        TextView mediumText ;

        ViewHolder(View itemView) {
            super(itemView) ;

            // 뷰 객체에 대한 참조. (hold strong reference)
            mediumText = itemView.findViewById(R.id.mediumItemText) ;
            mediumImg = itemView.findViewById(R.id.mediumItemImage) ;

        }
    }
}
