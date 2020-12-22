package com.example.digitalmuseum.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.example.digitalmuseum.R;
import com.example.digitalmuseum.network.vo.DetailsVO;

import java.util.Objects;

public class CustomDialog extends Dialog {
    private ConstraintLayout mOkButton;
    private ConstraintLayout custom_back;
    private ImageView itemImage;
    private DetailsVO detailsVO;
    private TextView classified;
    private TextView producted;
    private TextView title;
    private TextView amountValue;
    private TextView sizeValue;
    private TextView materialValue;
    private TextView productionValue;
    private TextView providerValue;
    private TextView sourceValue;
    private TextView originValue;
    private TextView storageValue;
    private TextView store_numValue;
    private TextView contentText;
    String URL = "http://10.20.170.240:8080"; //



    private View.OnClickListener mBtnClickListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_dialog);

        itemImage = findViewById(R.id.contentImage);
        mOkButton = findViewById(R.id.close_btn);
        classified = findViewById(R.id.classified);
        producted = findViewById(R.id.producted);
        title = findViewById(R.id.title);
        amountValue = findViewById(R.id.amountValue);
        sizeValue = findViewById(R.id.sizeValue);
        materialValue = findViewById(R.id.materialValue);
        productionValue = findViewById(R.id.productionValue);
        providerValue = findViewById(R.id.providerValue);
        sourceValue = findViewById(R.id.sourceValue);
        originValue = findViewById(R.id.originValue);
        storageValue = findViewById(R.id.storageValue);
        store_numValue = findViewById(R.id.store_numValue);
        contentText = findViewById(R.id.contentText);
        custom_back = findViewById(R.id.custom_back);

        String amount = detailsVO.getAmount()+"개";
        classified.setText(detailsVO.getCode());
        producted.setText(detailsVO.getProductedAt().substring(0,4));
        title.setText(detailsVO.getTitle());
        amountValue.setText(amount);
        sizeValue.setText(detailsVO.getSize());
        materialValue.setText(detailsVO.getMaterial());
        productionValue.setText(detailsVO.getProduction());
        providerValue.setText(detailsVO.getProduction());
        sourceValue.setText(detailsVO.getSource());
        originValue.setText(detailsVO.getOrigin());
        storageValue.setText(detailsVO.getStorage());
        store_numValue.setText(detailsVO.getStoreNum());
        contentText.setText(detailsVO.getContent());

        Glide.with(getContext()).load(URL + detailsVO.getImgUrl()).thumbnail(0.01f).into(itemImage);

        if (mBtnClickListener != null) {
            mOkButton.setOnClickListener(mBtnClickListener);
            itemImage.setOnClickListener(mBtnClickListener);
            classified.setOnClickListener(mBtnClickListener);
            producted.setOnClickListener(mBtnClickListener);
            amountValue.setOnClickListener(mBtnClickListener);
            sizeValue.setOnClickListener(mBtnClickListener);
            materialValue.setOnClickListener(mBtnClickListener);
            productionValue.setOnClickListener(mBtnClickListener);
            providerValue.setOnClickListener(mBtnClickListener);
            sourceValue.setOnClickListener(mBtnClickListener);
            originValue.setOnClickListener(mBtnClickListener);
            storageValue.setOnClickListener(mBtnClickListener);
            store_numValue.setOnClickListener(mBtnClickListener);
            contentText.setOnClickListener(mBtnClickListener);
            custom_back.setOnClickListener(mBtnClickListener);
        }
    }

    // 클릭버튼이 하나일때 생성자 함수로 클릭이벤트를 받는다.
    public CustomDialog(Context context, DetailsVO detailsVO,
                        View.OnClickListener singleListener) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.detailsVO = detailsVO;
        this.mBtnClickListener = singleListener;
    }

}
