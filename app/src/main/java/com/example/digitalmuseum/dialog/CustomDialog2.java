package com.example.digitalmuseum.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.example.digitalmuseum.R;
import com.example.digitalmuseum.network.vo.DetailsVO;

public class CustomDialog2 extends Dialog {
    private ConstraintLayout custom_back;
    private ImageView itemImage;
    String imgUrl;
    String URL = "http://10.20.170.240:8080"; //

    private View.OnClickListener mBtnClickListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_dialog2);

        itemImage = findViewById(R.id.contentImage);
        custom_back = findViewById(R.id.custom_back);
        Glide.with(getContext()).load(URL + imgUrl).thumbnail(0.01f).into(itemImage);

        if (mBtnClickListener != null) {
            itemImage.setOnClickListener(mBtnClickListener);
            custom_back.setOnClickListener(mBtnClickListener);
        }
    }

    // 클릭버튼이 하나일때 생성자 함수로 클릭이벤트를 받는다.
    public CustomDialog2(Context context, String imgUrl,
                         View.OnClickListener singleListener) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.imgUrl = imgUrl;
        this.mBtnClickListener = singleListener;
    }

}
