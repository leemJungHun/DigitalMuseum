package com.example.digitalmuseum.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.digitalmuseum.MainActivity;
import com.example.digitalmuseum.R;
import com.example.digitalmuseum.adapter.MediumRecyclerAdapter;
import com.example.digitalmuseum.adapter.MediumRecyclerAdapter2;
import com.example.digitalmuseum.databinding.FragmentMediumBinding;
import com.example.digitalmuseum.network.vo.MediumVO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;

public class MediumFragment extends Fragment implements View.OnClickListener {
    FragmentMediumBinding binding;
    MediumRecyclerAdapter adapter;
    MediumRecyclerAdapter2 adapter2;
    ArrayList<MediumVO> mediumVOS;
    Bundle bundle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_medium, container, false);

        bundle = getArguments();
        assert bundle != null;
        mediumVOS = bundle.getParcelableArrayList("mediumVOS");

        Collections.sort(mediumVOS, (mediumVO, t1) -> mediumVO.getCode().compareTo(t1.getCode()));

        adapter = new MediumRecyclerAdapter((MainActivity) getActivity(), bundle);
        adapter2 = new MediumRecyclerAdapter2((MainActivity) getActivity(), bundle);

        binding.mediumView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
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
        binding.mediumView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));


        setText(bundle.getString("LCode"));

        binding.backPage.setOnClickListener(this);

        return binding.getRoot();
    }

    public void setText(String LCode){
        switch (LCode){
            case "L1":
                binding.mediumView.setAdapter(adapter);
                adapter.updateData(mediumVOS);
                binding.mediumTitle.setText(R.string.museumTitle);
                binding.mediumText.setText(R.string.museumText);
                break;
            case "L2":
                binding.mediumView.setAdapter(adapter2);
                adapter.updateData(mediumVOS);
                binding.mediumTitle.setText(R.string.albumTitle);
                binding.mediumText.setText(R.string.albumText);
                break;
            case "L3":
                binding.mediumView.setAdapter(adapter2);
                adapter2.updateData(mediumVOS);
                binding.mediumTitle.setText(R.string.greatManTitle);
                binding.mediumText.setText(R.string.greatManText);
                break;
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backPage:
                ((MainActivity) Objects.requireNonNull(getActivity())).setStartFragment(new SubFragment(),true, null);
                break;
        }
    }
}