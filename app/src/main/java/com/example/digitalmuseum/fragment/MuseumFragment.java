package com.example.digitalmuseum.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.digitalmuseum.MainActivity;
import com.example.digitalmuseum.R;
import com.example.digitalmuseum.adapter.MediumRecyclerAdapter;
import com.example.digitalmuseum.adapter.MediumRecyclerAdapter2;
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

public class MuseumFragment extends Fragment implements View.OnClickListener {
    FragmentMuseumBinding binding;
    MediumRecyclerAdapter adapter;
    MediumRecyclerAdapter2 adapter2;
    ArrayList<MediumVO> mediumVOS;
    private ArrayList<SmallVO> smallVOS = new ArrayList<>();
    private ArrayList<DataVO> dataVOS = new ArrayList<>();
    private Bundle result;
    String LCode;
    private boolean isClick = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(binding == null) {
            if(container!=null) {
                container.removeAllViews();
            }
            try {
                binding = DataBindingUtil.inflate(
                        inflater, R.layout.fragment_museum, container, false);
            } catch (InflateException e) {
                e.printStackTrace();
            }
        }
        result = getArguments();
        assert result != null;

        LCode = result.getString("LCode");

        binding.document.setTag("M1");
        binding.book.setTag("M2");
        binding.museum.setTag("M3");
        binding.photo.setTag("M4");
        binding.document.setOnClickListener(this);
        binding.book.setOnClickListener(this);
        binding.museum.setOnClickListener(this);
        binding.photo.setOnClickListener(this);

        binding.backPage.setOnClickListener(this);

        return binding.getRoot();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backPage:
                ((MainActivity) Objects.requireNonNull(getActivity())).setStartFragment(new SubFragment(),true, null);
                break;
        }

        if(!isClick){
            isClick=true;
            if(view.getId()!=R.id.backPage){
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(HttpRequestService.URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                HttpRequestService httpRequestService = retrofit.create(HttpRequestService.class);

                httpRequestService.getSmallList(view.getTag().toString()).enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if(response.isSuccessful() && response.body() != null){
                            Gson gson = new Gson();
                            if(response.body().get("data")!=null){
                                Fragment fragment;
                                JsonArray jsonArray = response.body().getAsJsonArray("data");
                                if(!view.getTag().toString().equals("M4")){
                                    for (JsonElement element : jsonArray) {
                                        SmallVO smallVO = gson.fromJson(element, SmallVO.class);
                                        Log.d("getTitle", " " + smallVO.getTitle());
                                        Log.d("getImage", " " + smallVO.getImage());
                                        Log.d("getCode", " " + smallVO.getCode());
                                        smallVOS.add(smallVO);
                                    }
                                    fragment = new SmallFragment();
                                    result.putParcelableArrayList("smallVOS", smallVOS);
                                }else{
                                    for (JsonElement element : jsonArray) {
                                        DataVO dataVO = gson.fromJson(element, DataVO.class);
                                        Log.d("getTitle", " " + dataVO.getTitle());
                                        Log.d("getProductedAt", " " + dataVO.getProductedAt());
                                        Log.d("getCode", " " + dataVO.getCode());
                                        dataVOS.add(dataVO);
                                    }
                                    fragment = new ContentsFragment();
                                    result.putParcelableArrayList("dataVOS", dataVOS);
                                    result.putString("type", "museum");
                                }

                                if(view.getId()==R.id.photo){
                                    result.putString("ContentTitle", "사진류");
                                }


                                result.putString("MCode", view.getTag().toString());

                                ((MainActivity) Objects.requireNonNull(getActivity())).setStartFragment(fragment,false, result);
                            }
                            else{
                                Log.d("data","null");
                                isClick=false;
                            }
                            isClick=false;
                        }

                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        isClick=false;
                    }
                });
            }
        }


    }
}