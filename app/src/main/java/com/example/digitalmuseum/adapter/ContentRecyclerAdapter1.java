package com.example.digitalmuseum.adapter;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.digitalmuseum.dialog.CustomDialog;
import com.example.digitalmuseum.MainActivity;
import com.example.digitalmuseum.R;
import com.example.digitalmuseum.dialog.CustomDialog2;
import com.example.digitalmuseum.network.HttpRequestService;
import com.example.digitalmuseum.network.vo.DataVO;
import com.example.digitalmuseum.network.vo.DetailsVO;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ContentRecyclerAdapter1 extends RecyclerView.Adapter<ContentRecyclerAdapter1.ViewHolder> implements View.OnClickListener {
    private ArrayList<DataVO> mData = null;
    private MainActivity activity;
    private Bundle result;
    private boolean isFirst = true;
    private Context context;
    private boolean isClick = false;
    String URL = "http://10.20.170.240:8080"; //
    CustomDialog dialog;
    CustomDialog2 dialog2;


    // 생성자에서 데이터 리스트 객체를 전달받음.
    public ContentRecyclerAdapter1(MainActivity activity, Bundle result, Context context) {
        this.activity = activity;
        this.result = result;
        this.context = context;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    @NonNull
    public ContentRecyclerAdapter1.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_content1, parent, false);
        return new ViewHolder(view);
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(ContentRecyclerAdapter1.ViewHolder holder, int position) {

        DataVO item = mData.get(position);

        Log.d("아이템 크기", " " + mData.size());
        Log.d("아이템 셋", " " + position);
        Log.d("text", " " + item.getTitle());
        //holder.scheduleIcon.setImageDrawable(item.getIcon()) ;

        if (position == 2) {
            holder.contentImage.setLayoutParams(new ConstraintLayout.LayoutParams((int) (400 * 1.55), (int) (360 * 1.55)));
        } else {
            holder.contentImage.setLayoutParams(new ConstraintLayout.LayoutParams(400, 360));
        }


        GradientDrawable drawable =
                (GradientDrawable) context.getDrawable(R.drawable.radius_line_5);

        holder.contentImage.setBackground(drawable);
        holder.contentImage.setClipToOutline(true);

        String product = null;
        if (item.getProductedAt() != null && !item.getProductedAt().equals("")) {
            product = item.getProductedAt().substring(0, 4);
        }


        if (product == null || product.equals("3000")) {
            product = " ";
        } else {
            product = product + "년";
        }

        String title = item.getTitle().replaceAll("\\.jpg", "");
        title = title.replaceAll("\\?", "");
        holder.pattern1.setVisibility(View.VISIBLE);
        holder.startMargin.setVisibility(View.GONE);
        holder.contentTitle.setText(title);
        holder.contentProduct.setText(product);
        holder.contentTitle.setTag(R.string.code,item.getCode());
        holder.contentImage.setTag(R.string.code,item.getCode());
        holder.contentProduct.setTag(R.string.code,item.getCode());
        holder.contentTitle.setTag(R.string.imgUrl,item.getImage());
        holder.contentImage.setTag(R.string.imgUrl,item.getImage());
        holder.contentProduct.setTag(R.string.imgUrl,item.getImage());
        if (item.getImage() != null) {
            Log.d("getImage", " " + URL + item.getImage());
            Glide.with(activity).load(URL + item.getImage()).thumbnail(0.01f).into(holder.contentImage);
        }

        holder.contentTitle.setOnClickListener(this);
        holder.contentImage.setOnClickListener(this);
        holder.contentProduct.setOnClickListener(this);


        if (position < 2 || position > mData.size() - 3) {
            holder.startMargin.setVisibility(View.GONE);
            holder.pattern1.setVisibility(View.INVISIBLE);
        }
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void updateData(ArrayList<DataVO> dataVOS) {
        if (mData != null) {
            mData.clear();
        } else {
            mData = new ArrayList<>();
        }
        mData.addAll(dataVOS);
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        if(!isClick){
            isClick=true;
            if(!result.getString("type").equals("album")&&!result.getString("type").equals("great")){
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(HttpRequestService.URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                HttpRequestService httpRequestService = retrofit.create(HttpRequestService.class);

                httpRequestService.getData(view.getTag(R.string.code).toString()).enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            Gson gson = new Gson();
                            if (!response.body().get("data").toString().equals("null")) {
                                DetailsVO detailsVO = gson.fromJson(response.body().get("data").toString(), DetailsVO.class);
                                Log.d("detailsVo", " " + detailsVO.getCode());
                                Dialog(detailsVO);
                            } else {
                                Log.d("data", "null");
                                isClick=false;
                            }

                        }else{
                            isClick=false;
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        isClick=false;
                    }
                });
            }else if(result.getString("type").equals("album")){
                Dialog2(view.getTag(R.string.imgUrl).toString());
            }
        }
    }

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView contentImage;
        TextView contentTitle;
        TextView contentProduct;
        ConstraintLayout startMargin;
        ConstraintLayout pattern1;

        ViewHolder(View itemView) {
            super(itemView);

            // 뷰 객체에 대한 참조. (hold strong reference)
            contentProduct = itemView.findViewById(R.id.contentItemProducted);
            contentTitle = itemView.findViewById(R.id.contentItemTitle);
            contentImage = itemView.findViewById(R.id.contentItemImage);
            startMargin = itemView.findViewById(R.id.start_margin);
            pattern1 = itemView.findViewById(R.id.pattern_01);

        }
    }

    public void Dialog(DetailsVO detailsVO) {
        dialog = new CustomDialog(context,
                detailsVO,// 내용
                OkListener);
        ((MainActivity) Objects.requireNonNull(activity)).Restart_Period(dialog);

        //요청 이 다이어로그를 종료할 수 있게 지정함
        dialog.setCancelable(true);
        Objects.requireNonNull(dialog.getWindow()).setGravity(Gravity.CENTER);
        dialog.show();
    }

    //다이얼로그 클릭이벤트
    private final View.OnClickListener OkListener = new View.OnClickListener() {
        public void onClick(View v) {
            if (v.getId() == R.id.close_btn) {
                dialog.dismiss();
                isClick=false;
            } else {
                ((MainActivity) Objects.requireNonNull(activity)).Restart_Period(dialog);
            }
        }
    };

    public void Dialog2(String imgUrl) {
        dialog2 = new CustomDialog2(context,
                imgUrl,// 내용
                OkListener2); //
        ((MainActivity) Objects.requireNonNull(activity)).Restart_Period(dialog2);

        //요청 이 다이어로그를 종료할 수 있게 지정함
        dialog2.setCancelable(true);
        Objects.requireNonNull(dialog2.getWindow()).setGravity(Gravity.CENTER);
        dialog2.show();
    }

    //다이얼로그 클릭이벤트
    private final View.OnClickListener OkListener2 = new View.OnClickListener() {
        public void onClick(View v) {
            dialog2.dismiss();
            isClick=false;
            ((MainActivity) Objects.requireNonNull(activity)).Restart_Period(dialog2);
        }
    };
}
