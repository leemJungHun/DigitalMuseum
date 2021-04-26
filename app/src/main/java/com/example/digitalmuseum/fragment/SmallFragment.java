package com.example.digitalmuseum.fragment;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
import com.example.digitalmuseum.adapter.MediumRecyclerAdapter;
import com.example.digitalmuseum.adapter.SmallRecyclerAdapter1;
import com.example.digitalmuseum.adapter.SmallRecyclerAdapter2;
import com.example.digitalmuseum.databinding.FragmentSmallBinding;
import com.example.digitalmuseum.network.vo.MediumVO;
import com.example.digitalmuseum.network.vo.SmallVO;
import com.example.digitalmuseum.util.CenterLayoutManager;
import com.example.digitalmuseum.util.VisiblePositionChangeListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;

public class SmallFragment extends Fragment implements View.OnClickListener {
    FragmentSmallBinding binding;
    Bundle bundle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(binding == null) {
            if(container!=null) {
                container.removeAllViews();
            }
            try {
                binding = DataBindingUtil.inflate(
                        inflater, R.layout.fragment_small, container, false);
            } catch (OutOfMemoryError|InflateException e) {
                ((MainActivity) Objects.requireNonNull(getActivity())).setStartFragment(new MuseumFragment(), true, bundle);
                e.printStackTrace();
            }
        }


        binding.smallView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
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
        binding.smallView2.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
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
        bundle = getArguments();

        setText(bundle.getString("MCode"));
        //binding.smallView.smoothScrollBy();
        //binding.smallView.addOnScrollListener();
        binding.smallView.setHasFixedSize(true);
        binding.smallView2.setHasFixedSize(true);

        binding.backPage.setOnClickListener(this);
        binding.firstPage.setOnClickListener(this);

        return binding.getRoot();
    }

    public void setText(String MCode) {
        ArrayList<SmallVO> smallVOS = bundle.getParcelableArrayList("smallVOS");


        Collections.sort(smallVOS, (smallVO, t1) -> smallVO.getCode().compareTo(t1.getCode()));

        ArrayList<SmallVO> smallVOS1 = new ArrayList<>();
        ArrayList<SmallVO> smallVOS2 = new ArrayList<>();
        ArrayList<SmallVO> smallVOS3 = new ArrayList<>();

        for(int i = 0 ;i < smallVOS.size();i++){
            if(i%3==0){
                smallVOS1.add(smallVOS.get(i));
            }else if(i%3==1){
                smallVOS2.add(smallVOS.get(i));
            }else {
                smallVOS3.add(smallVOS.get(i));
            }
        }

        Log.d("smallVOS1",smallVOS1.size()+" ");
        Log.d("smallVOS2",smallVOS2.size()+" ");
        Log.d("smallVOS3",smallVOS3.size()+" ");
        RecyclerView rv_small;
        SnapHelper snapHelper;
        if(smallVOS.size()>=10){
            SmallRecyclerAdapter2 adapter2;
            adapter2 = new SmallRecyclerAdapter2((MainActivity) getActivity(), bundle, getContext());
            binding.smallView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
            binding.smallView.setAdapter(adapter2);
            adapter2.updateData(smallVOS1,smallVOS2,smallVOS3);
        }else{
            if (smallVOS.size()>=4){
                rv_small = binding.smallView2;
            }else{
                rv_small = binding.smallView;
            }
            snapHelper = new LinearSnapHelper();
            SmallRecyclerAdapter1 adapter;
            adapter = new SmallRecyclerAdapter1((MainActivity) getActivity(), bundle,getContext());
            CenterLayoutManager layoutManager = new CenterLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
            rv_small.setLayoutManager(layoutManager);
            rv_small.setAdapter(adapter);
            rv_small.setHasFixedSize(true);
            snapHelper.attachToRecyclerView(rv_small);
            adapter.updateData(smallVOS);
        }
        switch (MCode) {
            case "M1":
                binding.smallTitle.setText(R.string.documentation);
                break;
            case "M2":
                binding.smallTitle.setText(R.string.books);
                break;
            case "M3":
                binding.smallTitle.setText(R.string.museums);
                break;
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backPage:
                ((MainActivity) Objects.requireNonNull(getActivity())).setStartFragment(new MuseumFragment(), true, bundle);
                break;
            case R.id.firstPage:
                ((MainActivity) Objects.requireNonNull(getActivity())).setStartFragment(new SubFragment(), true, bundle);
                break;
        }
    }
}