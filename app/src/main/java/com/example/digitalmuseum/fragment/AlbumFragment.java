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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.digitalmuseum.MainActivity;
import com.example.digitalmuseum.R;
import com.example.digitalmuseum.adapter.AlbumRecyclerAdapter;
import com.example.digitalmuseum.adapter.MediumRecyclerAdapter;
import com.example.digitalmuseum.adapter.MediumRecyclerAdapter2;
import com.example.digitalmuseum.databinding.FragmentAlbumBinding;
import com.example.digitalmuseum.databinding.FragmentMediumBinding;
import com.example.digitalmuseum.databinding.FragmentMuseumBinding;
import com.example.digitalmuseum.network.HttpRequestService;
import com.example.digitalmuseum.network.vo.DataVO;
import com.example.digitalmuseum.network.vo.MediumVO;
import com.example.digitalmuseum.network.vo.SmallVO;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AlbumFragment extends Fragment implements View.OnClickListener {
    FragmentAlbumBinding binding;
    AlbumRecyclerAdapter adapter;
    ArrayList<MediumVO> mediumVOS;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_album, container, false);

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

        Bundle bundle = getArguments();
        assert bundle != null;
        mediumVOS = new ArrayList<>();

        String[] code = {"M8","M9","M10","M11","M12","M13"};
        String[] title = {"1927-1946","1947-1966","1967-1986","1987-2006","2007-2016","2017-현재"};

        for(int i=0;i<code.length;i++){
            MediumVO addMediumVO = new MediumVO();
            addMediumVO.setCode(code[i]);
            addMediumVO.setTitle(title[i]);
            mediumVOS.add(addMediumVO);
        }


        //addMediumVO


        //Collections.sort(mediumVOS, (mediumVO, t1) -> mediumVO.getCode().compareTo(t1.getCode()));

        adapter = new AlbumRecyclerAdapter((MainActivity) getActivity(), bundle);

        binding.mediumView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));


        setText(bundle.getString("LCode"));

        binding.backPage.setOnClickListener(this);

        return binding.getRoot();
    }

    public void setText(String LCode) {
        binding.mediumView.setAdapter(adapter);
        adapter.updateData(mediumVOS);
        binding.mediumTitle.setText(R.string.albumTitle);
        binding.mediumText.setText(R.string.albumText);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backPage:
                ((MainActivity) Objects.requireNonNull(getActivity())).setStartFragment(new SubFragment(), true, null);
                break;
        }
    }
}
