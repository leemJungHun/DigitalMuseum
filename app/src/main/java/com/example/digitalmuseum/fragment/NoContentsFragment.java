package com.example.digitalmuseum.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.SnapHelper;

import com.example.digitalmuseum.MainActivity;
import com.example.digitalmuseum.R;
import com.example.digitalmuseum.adapter.ContentRecyclerAdapter1;
import com.example.digitalmuseum.adapter.ContentRecyclerAdapter2;
import com.example.digitalmuseum.databinding.FragmentContentBinding;
import com.example.digitalmuseum.databinding.FragmentNoContentBinding;
import com.example.digitalmuseum.network.vo.DataVO;
import com.example.digitalmuseum.util.CenterLayoutManager;
import com.example.digitalmuseum.util.VisiblePositionChangeListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class NoContentsFragment extends Fragment implements View.OnClickListener {
    FragmentNoContentBinding binding;
    Bundle bundle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_no_content, container, false);

        bundle = getArguments();

        setTitle();

        binding.backPage.setOnClickListener(this);

        return binding.getRoot();
    }

    public void setTitle(){
        binding.contentTitle.setText(bundle.getString("ContentTitle"));
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
                }

                break;
        }
    }
}