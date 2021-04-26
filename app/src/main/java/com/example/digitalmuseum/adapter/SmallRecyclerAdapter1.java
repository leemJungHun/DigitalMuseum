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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.example.digitalmuseum.MainActivity;
import com.example.digitalmuseum.R;
import com.example.digitalmuseum.fragment.ContentsFragment;
import com.example.digitalmuseum.fragment.MediumFragment;
import com.example.digitalmuseum.fragment.NoContentsFragment;
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

public class SmallRecyclerAdapter1 extends RecyclerView.Adapter<SmallRecyclerAdapter1.ViewHolder> implements View.OnClickListener {
    private ArrayList<SmallVO> mData = null ;
    private ArrayList<DataVO> dataVOS = new ArrayList<>();
    private MainActivity activity;
    private Bundle result;
    private Context context;
    private boolean isClick = false;
    String URL = HttpRequestService.URL + "/kyodong"; //

    // 생성자에서 데이터 리스트 객체를 전달받음.
    public SmallRecyclerAdapter1(MainActivity activity, Bundle result,Context context) {
        this.activity = activity;
        this.result = result;
        this.context =context;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    @NonNull
    public SmallRecyclerAdapter1.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        View view = LayoutInflater.from(context).inflate(R.layout.item_small1, parent, false) ;
        return new ViewHolder(view);
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(SmallRecyclerAdapter1.ViewHolder holder, int position) {

        SmallVO item = mData.get(position) ;

        Log.d("아이템 크기", " " + mData.size());
        Log.d("아이템 셋", " " + position);

        GradientDrawable drawable=
                (GradientDrawable) context.getDrawable(R.drawable.radius_line);

        holder.smallImage.setBackground(drawable);
        holder.smallImage.setClipToOutline(true);


        if(position==0&&mData.size()>4){
            holder.startMargin.setVisibility(View.VISIBLE);
            holder.endMargin.setVisibility(View.GONE);
        }else if(position==mData.size()-1&&mData.size()>4){
            holder.startMargin.setVisibility(View.GONE);
            holder.endMargin.setVisibility(View.VISIBLE);
        }else{
            holder.startMargin.setVisibility(View.GONE);
            holder.endMargin.setVisibility(View.GONE);
        }
        Log.e("Title", item.getTitle());

        holder.smallText.setText(item.getTitle().replaceAll("/n","\n"));
        holder.smallText.setTag(R.string.code,item.getCode());
        holder.smallImage.setTag(R.string.code,item.getCode());
        holder.smallText.setTag(R.string.title,item.getTitle().replaceAll("/n",""));
        holder.smallImage.setTag(R.string.title,item.getTitle().replaceAll("/n",""));
        holder.smallImage.setImageResource(R.drawable.kyodong_img_004_01);
        holder.smallText.setOnClickListener(this);
        holder.smallImage.setOnClickListener(this);
        if(item.getImage()!=null){
            Log.d("getImage"," " + URL+item.getImage());
            Glide.with(activity).load(URL + item.getImage()).override(400,360).thumbnail(0.01f).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.smallImage);
        }


    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return mData.size() ;
    }

    public void updateData(ArrayList<SmallVO> smallVOS) {
        if(mData!=null) {
            mData.clear();
        }else{
            mData = new ArrayList<>();
        }
        mData.addAll(smallVOS);
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
                        if(response.body().get("data")!=null){
                            JsonArray jsonArray = response.body().getAsJsonArray("data");
                            if(jsonArray!=null){
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

                            if(dataVOS.size()!=0) {
                                ((MainActivity) Objects.requireNonNull(activity)).setStartFragment(new ContentsFragment(), false, result);
                            }else{
                                ((MainActivity) Objects.requireNonNull(activity)).setStartFragment(new NoContentsFragment(), false, result);
                            }
                        }
                        else{
                            Log.d("data","null");
                            isClick=false;
                            ((MainActivity) Objects.requireNonNull(activity)).setStartFragment(new NoContentsFragment(), false, result);
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
        ConstraintLayout startMargin;
        ConstraintLayout endMargin;

        ViewHolder(View itemView) {
            super(itemView) ;

            // 뷰 객체에 대한 참조. (hold strong reference)
            smallText = itemView.findViewById(R.id.smallItemText) ;
            smallImage = itemView.findViewById(R.id.smallItemImage) ;

            startMargin = itemView.findViewById(R.id.start_margin);
            endMargin = itemView.findViewById(R.id.end_margin);

        }
    }
}
