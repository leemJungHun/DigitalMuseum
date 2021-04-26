package com.example.digitalmuseum.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.example.digitalmuseum.MainActivity;
import com.example.digitalmuseum.R;
import com.example.digitalmuseum.adapter.Album2040RecyclerAdapter;
import com.example.digitalmuseum.adapter.SmallRecyclerAdapter1;
import com.example.digitalmuseum.adapter.SmallRecyclerAdapter2;
import com.example.digitalmuseum.databinding.FragmentAlbum2040Binding;
import com.example.digitalmuseum.databinding.FragmentSmallBinding;
import com.example.digitalmuseum.network.vo.SmallVO;
import com.example.digitalmuseum.util.CenterLayoutManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class Album2040Fragment extends Fragment implements View.OnClickListener {
    FragmentAlbum2040Binding binding;
    Bundle bundle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        try {
            binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_album2040, container, false);
        } catch (OutOfMemoryError e) {
            ((MainActivity) Objects.requireNonNull(getActivity())).setStartFragment(new AlbumFragment(), true, bundle);
        }

        binding.smallView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                if (e.getAction() == MotionEvent.ACTION_DOWN) {
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

        setText();
        binding.smallView.setHasFixedSize(true);

        binding.backPage.setOnClickListener(this);
        binding.firstPage.setOnClickListener(this);

        return binding.getRoot();
    }

    public void setText() {
        ArrayList<SmallVO> smallVOS = bundle.getParcelableArrayList("smallVOS");

        Collections.sort(smallVOS, (smallVO, t1) -> smallVO.getTitle().compareTo(t1.getTitle()));

        Album2040RecyclerAdapter adapter;
        adapter = new Album2040RecyclerAdapter((MainActivity) getActivity(), bundle, getContext());
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),4);
        binding.smallView.setLayoutManager(layoutManager);
        binding.smallView.setAdapter(adapter);
        binding.smallView.setHasFixedSize(true);
        adapter.updateData(smallVOS);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backPage:
                ((MainActivity) Objects.requireNonNull(getActivity())).setStartFragment(new AlbumFragment(), true, bundle);
                break;
            case R.id.firstPage:
                ((MainActivity) Objects.requireNonNull(getActivity())).setStartFragment(new SubFragment(), true, bundle);
                break;
        }
    }
}