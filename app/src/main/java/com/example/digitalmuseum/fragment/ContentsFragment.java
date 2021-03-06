package com.example.digitalmuseum.fragment;

import android.annotation.SuppressLint;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.example.digitalmuseum.MainActivity;
import com.example.digitalmuseum.R;
import com.example.digitalmuseum.adapter.ContentRecyclerAdapter1;
import com.example.digitalmuseum.adapter.ContentRecyclerAdapter2;
import com.example.digitalmuseum.databinding.FragmentContentBinding;
import com.example.digitalmuseum.network.vo.DataVO;
import com.example.digitalmuseum.util.CenterLayoutManager;
import com.example.digitalmuseum.util.VisiblePositionChangeListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;

public class ContentsFragment extends Fragment implements View.OnClickListener {
    FragmentContentBinding binding;
    Bundle bundle;
    int centerPosition=0;
    boolean isSeekbar = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(binding == null) {
            if(container!=null) {
                container.removeAllViews();
            }
            try {
                binding = DataBindingUtil.inflate(
                        inflater, R.layout.fragment_content, container, false);
            } catch (InflateException e) {
                e.printStackTrace();
            }
        }

        bundle = getArguments();

        setTitle();

        setContent1();

        if(bundle.getString("MCode").equals("M4")){
            binding.contentText.setText("교동 역대 행사 사진 모음");
        }

        binding.backPage.setOnClickListener(this);
        binding.firstPage.setOnClickListener(this);
        binding.contentView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                if(e.getAction()==MotionEvent.ACTION_DOWN){
                    ((MainActivity) Objects.requireNonNull(getActivity())).Restart_Period();
                }
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
            }
        });

        return binding.getRoot();
    }

    public void setTitle(){
        binding.contentTitle.setText(bundle.getString("ContentTitle"));
    }

    public void setContent1() {
        ArrayList<DataVO> dataVOS = bundle.getParcelableArrayList("dataVOS");



        if (!bundle.getString("type").equals("album2040")&&!bundle.getString("type").equals("albumSebu")){
            Collections.sort(dataVOS, (dataVO, t1) -> dataVO.getProductedAt().compareTo(t1.getProductedAt()));
        }
        if(dataVOS.size()>90){
            ArrayList<DataVO> dataVOS1 = new ArrayList<>();
            ArrayList<DataVO> dataVOS2 = new ArrayList<>();
            for(int i = 0 ;i < dataVOS.size();i++){
                if(i%2==0){
                    dataVOS1.add(dataVOS.get(i));
                }else if(i%2==1){
                    dataVOS2.add(dataVOS.get(i));
                }
            }

            ContentRecyclerAdapter2 adapter;
            CenterLayoutManager layoutManager = new CenterLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);



            adapter = new ContentRecyclerAdapter2((MainActivity) getActivity(), bundle,getContext());

            SnapHelper snapHelper = new LinearSnapHelper();
            snapHelper.attachToRecyclerView(binding.contentView);

            if (bundle.getString("type").equals("album2040")||bundle.getString("type").equals("albumSebu")){
                binding.seekLayout.setVisibility(View.GONE);
            }else {
                binding.rvSeekBar.setMax(dataVOS1.size() - 6);
                binding.rvSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                        if (isSeekbar) {
                            binding.contentView.scrollToPosition(i + 1);
                        }
                        if (i == 0) {
                            binding.contentView.scrollToPosition(0);
                            binding.startSeek.setVisibility(View.GONE);
                            binding.startSeekRed.setVisibility(View.VISIBLE);
                        } else if (i == seekBar.getMax()) {
                            binding.rvSeekBar.setProgress(centerPosition + 2);
                            Log.d("getMax", " ");
                            binding.endSeek.setVisibility(View.GONE);
                            binding.endSeekRed.setVisibility(View.VISIBLE);
                        } else {
                            binding.endSeek.setVisibility(View.VISIBLE);
                            binding.endSeekRed.setVisibility(View.GONE);
                            binding.startSeek.setVisibility(View.VISIBLE);
                            binding.startSeekRed.setVisibility(View.GONE);

                        }
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        isSeekbar = true;
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        isSeekbar = false;
                    }
                });

                binding.contentView.addOnScrollListener(new VisiblePositionChangeListener(layoutManager, new VisiblePositionChangeListener.OnChangeListener() {
                    @Override
                    public void onFirstVisiblePositionChanged(int position) {
                        Log.d("현재보이는 포지션", " " + position);
                        centerPosition = position;
                        if(!isSeekbar){
                            binding.rvSeekBar.setProgress(centerPosition);
                        }
                    }

                    @Override
                    public void onLastVisiblePositionChanged(int position) {
                        Log.d("현재보이는 포지션", " " + position);
                        Log.d("getChildCount()", binding.contentView.getChildCount() + " ");
                        centerPosition = position-5;
                        if(!isSeekbar){
                            binding.rvSeekBar.setProgress(centerPosition);
                        }
                    }
                }));
            }
            binding.contentView.setLayoutManager(layoutManager);
            binding.contentView.setAdapter(adapter);
            binding.contentView.setHasFixedSize(true);
            adapter.updateData(dataVOS1, dataVOS2);
        } else{
            binding.endSeek.setOnClickListener(this);

            dataVOS.add(0,dataVOS.get(0));
            dataVOS.add(0,dataVOS.get(0));
            dataVOS.add(dataVOS.size(),dataVOS.get(0));
            dataVOS.add(dataVOS.size(),dataVOS.get(0));

            for(int i = 0 ; i<dataVOS.size() ; i++){
                Log.d("datavos",dataVOS.get(i).getTitle()+" ");
            }

            if (bundle.getString("type").equals("album2040")||bundle.getString("type").equals("albumSebu")){
                binding.seekLayout.setVisibility(View.GONE);
            }else{
                binding.rvSeekBar.setMax(dataVOS.size()-5);
                binding.rvSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                        Log.d("i", i +" ");
                        Log.d("getMax", seekBar.getMax() +" ");
                        if(isSeekbar){
                            binding.contentView.scrollToPosition(i+4);
                        }
                        if(i==0){
                            binding.contentView.scrollToPosition(0);
                            binding.startSeek.setVisibility(View.GONE);
                            binding.startSeekRed.setVisibility(View.VISIBLE);
                            binding.endSeek.setVisibility(View.VISIBLE);
                            binding.endSeekRed.setVisibility(View.GONE);
                        }else if(i==seekBar.getMax()){
                            seekBar.setProgress(seekBar.getMax());
                            binding.contentView.scrollToPosition(i+4);
                            binding.endSeek.setVisibility(View.GONE);
                            binding.endSeekRed.setVisibility(View.VISIBLE);
                            binding.startSeek.setVisibility(View.VISIBLE);
                            binding.startSeekRed.setVisibility(View.GONE);
                        }else{
                            binding.endSeek.setVisibility(View.VISIBLE);
                            binding.endSeekRed.setVisibility(View.GONE);
                            binding.startSeek.setVisibility(View.VISIBLE);
                            binding.startSeekRed.setVisibility(View.GONE);

                        }
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        isSeekbar=true;
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        isSeekbar=false;
                    }
                });
            }

            ContentRecyclerAdapter1 adapter;
            CenterLayoutManager layoutManager = new CenterLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

            SnapHelper snapHelper = new LinearSnapHelper();

            adapter = new ContentRecyclerAdapter1((MainActivity) getActivity(), bundle,getContext());

            binding.contentView.setLayoutManager(layoutManager);
            binding.contentView.setAdapter(adapter);
            binding.contentView.setHasFixedSize(true);
            binding.contentView.addOnScrollListener(new VisiblePositionChangeListener(layoutManager, new VisiblePositionChangeListener.OnChangeListener() {
                @Override
                public void onFirstVisiblePositionChanged(int position) {
                    Log.d("현재보이는 포지션", " " + position);
                    centerPosition = position+2;
                    if (!bundle.getString("type").equals("album2040")&&!bundle.getString("type").equals("albumSebu")){
                        if(!isSeekbar){
                            binding.rvSeekBar.setProgress(centerPosition-2);
                        }
                    }
                    onImageClicked(centerPosition);
                }

                @Override
                public void onLastVisiblePositionChanged(int position) {
                    Log.d("현재보이는 포지션", " " + position);
                    Log.d("getChildCount()", binding.contentView.getChildCount() + " ");
                    centerPosition = position-2;
                    if (!bundle.getString("type").equals("album2040")&&!bundle.getString("type").equals("albumSebu")){
                        if(!isSeekbar){
                            binding.rvSeekBar.setProgress(centerPosition-2);
                        }
                    }
                    onImageClicked(centerPosition);
                }
            }));

            snapHelper.attachToRecyclerView(binding.contentView);
            adapter.updateData(dataVOS);

        }
        binding.contentView.smoothScrollToPosition(2);

    }

    //scroll
    private void onImageClicked(int position) {/*
        Transition transition = new AutoTransition();
        TransitionManager.beginDelayedTransition(binding.contentView, transition);*/

        for (int i = 0; i < binding.contentView.getChildCount(); i++) {
            View childAt = binding.contentView.getChildAt(i);
            ImageView contentImg = childAt.findViewById(R.id.contentItemImage);


            int width = binding.getSize.getWidth();
            int height = binding.getSize.getHeight();
            Log.d("width", width + " " );
            if (binding.contentView.getChildAdapterPosition(childAt) == position) {
                contentImg.setLayoutParams(new ConstraintLayout.LayoutParams((int) (width * 1.55), (int) (height * 1.55)));
                //binding.contentView.smoothScrollToPosition(position);
            } else {
                contentImg.setLayoutParams(new ConstraintLayout.LayoutParams(width, height));
            }
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backPage:
                if(bundle.getString("type").equals("museum")){
                    ((MainActivity) Objects.requireNonNull(getActivity())).setStartFragment(new MuseumFragment(), true, bundle);
                }else if(bundle.getString("type").equals("album")){
                    ((MainActivity) Objects.requireNonNull(getActivity())).setStartFragment(new AlbumFragment(), true, bundle);
                }else if(bundle.getString("type").equals("great")){
                    ((MainActivity) Objects.requireNonNull(getActivity())).setStartFragment(new GreatFragment(), true, bundle);
                }else if(bundle.getString("type").equals("small")){
                    ((MainActivity) Objects.requireNonNull(getActivity())).setStartFragment(new SmallFragment(), true, bundle);
                }else if(bundle.getString("type").equals("album2040")){
                    ((MainActivity) Objects.requireNonNull(getActivity())).setStartFragment(new Album2040Fragment(), true, bundle);
                }else if(bundle.getString("type").equals("albumSebu")){
                    ((MainActivity) Objects.requireNonNull(getActivity())).setStartFragment(new AlbumSebuFragment(), true, bundle);
                }
                break;
            case R.id.end_seek:
                binding.rvSeekBar.setProgress(binding.rvSeekBar.getMax());
                break;
            case R.id.firstPage:
                ((MainActivity) Objects.requireNonNull(getActivity())).setStartFragment(new SubFragment(), true, bundle);
                break;
        }
    }

    /*final Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            Transition transition = new AutoTransition();
            TransitionManager.beginDelayedTransition(binding.contentView, transition);

            for (int i = 0; i < binding.contentView.getChildCount(); i++) {
                View childAt = binding.contentView.getChildAt(i);
                ImageView contentImg = childAt.findViewById(R.id.contentItemImage);
                if (binding.contentView.getChildAdapterPosition(childAt) == centerPosition) {
                    contentImg.setLayoutParams(new ConstraintLayout.LayoutParams((int) (400 * 1.55), (int) (360 * 1.55)));
                    //binding.contentView.smoothScrollToPosition(position);
                } else {
                    contentImg.setLayoutParams(new ConstraintLayout.LayoutParams(400, 360));
                }
            }
            return false;
        }
    });*/
}