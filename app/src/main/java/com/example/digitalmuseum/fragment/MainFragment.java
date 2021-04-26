package com.example.digitalmuseum.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.digitalmuseum.MainActivity;
import com.example.digitalmuseum.R;
import com.example.digitalmuseum.databinding.FragmentMainBinding;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.LogRecord;

public class MainFragment extends Fragment {
    FragmentMainBinding binding;
    Timer timer;
    ArrayList<Drawable> forwardDrawable = new ArrayList<>();
    ArrayList<Drawable> backendDrawable = new ArrayList<>();
    int backendNum = 0;
    int forwardNum = 0;
    boolean isForward = true;
    TimerTask addTask;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(binding == null) {
            if(container!=null) {
                container.removeAllViews();
            }
            try {
                binding = DataBindingUtil.inflate(
                        inflater, R.layout.fragment_main, container, false);
            } catch (InflateException e) {
                e.printStackTrace();
            }
        }
        forwardDrawable.add(getResources().getDrawable(R.drawable.kyodong_img_001_02));
        forwardDrawable.add(getResources().getDrawable(R.drawable.kyodong_img_001_04));
        forwardDrawable.add(getResources().getDrawable(R.drawable.kyodong_img_001_06));
        forwardDrawable.add(getResources().getDrawable(R.drawable.kyodong_img_001_08));
        forwardDrawable.add(getResources().getDrawable(R.drawable.kyodong_img_001_10));
        backendDrawable.add(getResources().getDrawable(R.drawable.kyodong_img_001_03));
        backendDrawable.add(getResources().getDrawable(R.drawable.kyodong_img_001_05));
        backendDrawable.add(getResources().getDrawable(R.drawable.kyodong_img_001_07));
        backendDrawable.add(getResources().getDrawable(R.drawable.kyodong_img_001_09));
        backendDrawable.add(getResources().getDrawable(R.drawable.kyodong_img_001_11));

        ((MainActivity) Objects.requireNonNull(getActivity())).Media_Start();

        Stop_Period();

        Start_Period();

        binding.mainScreen.setOnClickListener(view -> {
            Stop_Period();
            ((MainActivity) Objects.requireNonNull(getActivity())).Media_Stop();
            ((MainActivity) Objects.requireNonNull(getActivity())).setStartFragment(new SubFragment(),false, null);
        });

        return binding.getRoot();
    }

    private void fadeOutAndHideImage(final ImageView outImg)
    {
        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator());
        fadeOut.setDuration(1000);

        fadeOut.setAnimationListener(new Animation.AnimationListener()
        {
            public void onAnimationEnd(Animation animation)
            {
                switch (outImg.getId()){
                    case R.id.backend_img:
                        if(backendNum<4){
                            backendNum++;
                        }else {
                            backendNum=0;
                        }
                        Glide.with(Objects.requireNonNull(getActivity())).load(backendDrawable.get(backendNum)).into(outImg);
                        break;
                    case R.id.forward_img:
                        if(forwardNum<4){
                            forwardNum++;
                        }else {
                            forwardNum=0;
                        }
                        Glide.with(Objects.requireNonNull(getActivity())).load(forwardDrawable.get(forwardNum)).into(outImg);
                        break;
                }
                outImg.setVisibility(View.GONE);
            }
            public void onAnimationRepeat(Animation animation) {}
            public void onAnimationStart(Animation animation) {}
        });

        outImg.startAnimation(fadeOut);
    }

    private void fadeInAndShowImage(final ImageView inImg)
    {
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new AccelerateInterpolator());
        fadeIn.setDuration(1000);

        fadeIn.setAnimationListener(new Animation.AnimationListener()
        {
            public void onAnimationEnd(Animation animation)
            {
                inImg.setVisibility(View.VISIBLE);
            }
            public void onAnimationRepeat(Animation animation) {}
            public void onAnimationStart(Animation animation) { }
        });
        inImg.startAnimation(fadeIn);
    }

    public void Start_Period(){
        SetImageTask();
        timer = new Timer();
        timer.schedule(addTask, 0, 3*1000);
    }

    public void Stop_Period(){
        if(timer !=null) {
            timer.cancel();
            timer = null;
        }
    }

    private void SetImageTask(){
        try {
            addTask = new TimerTask() {
                @Override
                public void run() {
                    Message msg = handler.obtainMessage();
                    handler.sendMessage(msg);
                }
            };
        }catch (Exception ignored){

        }
    }


    final Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            // 원래 하려던 동작 (UI변경 작업 등)
            binding.backendImg.clearAnimation();
            binding.forwardImg.clearAnimation();
            if(isForward){
                fadeInAndShowImage(binding.backendImg);
                fadeOutAndHideImage(binding.forwardImg);
                isForward=false;
            }else{
                fadeInAndShowImage(binding.forwardImg);
                fadeOutAndHideImage(binding.backendImg);
                isForward=true;
            }
            return false;
        }
    });

}