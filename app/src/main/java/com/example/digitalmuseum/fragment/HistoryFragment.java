package com.example.digitalmuseum.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.example.digitalmuseum.MainActivity;
import com.example.digitalmuseum.R;
import com.example.digitalmuseum.adapter.HistoryRecyclerAdapter;
import com.example.digitalmuseum.adapter.MediumRecyclerAdapter;
import com.example.digitalmuseum.databinding.FragmentHistoryBinding;
import com.example.digitalmuseum.util.CenterLayoutManager;
import com.example.digitalmuseum.util.VisiblePositionChangeListener;

import java.util.ArrayList;
import java.util.Objects;

public class HistoryFragment extends Fragment implements View.OnClickListener {
    FragmentHistoryBinding binding;
    HistoryRecyclerAdapter adapter;
    ArrayList<ImageView> selectImgs = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_history, container, false);

        adapter = new HistoryRecyclerAdapter((MainActivity)getActivity(),getContext());

        CenterLayoutManager layoutManager = new CenterLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        SnapHelper snapHelper = new PagerSnapHelper();

        binding.historyView.setLayoutManager(layoutManager);
        binding.historyView.setAdapter(adapter);
        binding.historyView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
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
        binding.historyView.addOnScrollListener(new VisiblePositionChangeListener(layoutManager, new VisiblePositionChangeListener.OnChangeListener() {
            @Override
            public void onFirstVisiblePositionChanged(int position) {
                Log.d("현재보이는 포지션", " " + position);
                switch (position){
                    case 0:
                        setSelectImgs(0);
                        break;
                    case 2:
                        setSelectImgs(1);
                        break;
                    case 4:
                        setSelectImgs(2);
                        break;
                    case 6:
                        setSelectImgs(3);
                        break;
                    case 10:
                        setSelectImgs(4);
                        break;
                }

            }

            @Override
            public void onLastVisiblePositionChanged(int position) {
                Log.d("현재보이는 포지션", " " + position);
                switch (position){
                    case 1:
                        setSelectImgs(1);
                        break;
                    case 3:
                        setSelectImgs(2);
                        break;
                    case 5:
                        setSelectImgs(3);
                        break;
                    case 7:
                        setSelectImgs(4);
                        break;
                    case 11:
                        setSelectImgs(5);
                        break;
                }
            }
        }));
        snapHelper.attachToRecyclerView(binding.historyView);
        adapter.updateData();

        selectImgs.add(binding.selectImg1);
        selectImgs.add(binding.selectImg2);
        selectImgs.add(binding.selectImg3);
        selectImgs.add(binding.selectImg4);
        selectImgs.add(binding.selectImg5);
        selectImgs.add(binding.selectImg6);

        binding.backPage.setOnClickListener(this);
        binding.select1.setOnClickListener(this);
        binding.select2.setOnClickListener(this);
        binding.select3.setOnClickListener(this);
        binding.select4.setOnClickListener(this);
        binding.select5.setOnClickListener(this);
        binding.select6.setOnClickListener(this);

        return binding.getRoot();
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backPage:
                ((MainActivity) Objects.requireNonNull(getActivity())).setStartFragment(new SubFragment(),true, null);
                break;
            case R.id.select_1:
                binding.historyView.scrollToPosition(0);
                setSelectImgs(0);
                break;
            case R.id.select_2:
                binding.historyView.scrollToPosition(1);
                setSelectImgs(1);
                break;
            case R.id.select_3:
                binding.historyView.scrollToPosition(3);
                setSelectImgs(2);
                break;
            case R.id.select_4:
                binding.historyView.scrollToPosition(5);
                setSelectImgs(3);
                break;
            case R.id.select_5:
                binding.historyView.scrollToPosition(7);
                setSelectImgs(4);
                break;
            case R.id.select_6:
                binding.historyView.scrollToPosition(11);
                setSelectImgs(5);
                break;
        }
    }

    public void setSelectImgs(int position){
        for(int i = 0 ; i<selectImgs.size();i++){
            if(i==position){
                selectImgs.get(i).setImageResource(R.drawable.seekbar_thumb);
            }else {
                selectImgs.get(i).setImageResource(R.drawable.history_bar_gray);
            }
        }
    }
}