package com.example.digitalmuseum.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.digitalmuseum.MainActivity;
import com.example.digitalmuseum.R;
import com.example.digitalmuseum.databinding.FragmentSubBinding;
import com.example.digitalmuseum.network.HttpRequestService;
import com.example.digitalmuseum.network.vo.MediumVO;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SubFragment extends Fragment implements View.OnClickListener {
    FragmentSubBinding binding;
    private HttpRequestService httpRequestService;
    private ArrayList<MediumVO> mediumVOS = new ArrayList<>();
    String LCode;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_sub, container, false);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HttpRequestService.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        httpRequestService = retrofit.create(HttpRequestService.class);

        binding.museum.setOnClickListener(this);
        binding.album.setOnClickListener(this);
        binding.greatMan.setOnClickListener(this);
        binding.history.setOnClickListener(view -> ((MainActivity) Objects.requireNonNull(getActivity())).setStartFragment(new HistoryFragment(),false, null));

        return binding.getRoot();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        Bundle result = new Bundle();
        switch (view.getId()) {
            case R.id.museum:
                LCode = "L1";
                result.putString("LCode", LCode);
                ((MainActivity) Objects.requireNonNull(getActivity())).setStartFragment(new MuseumFragment(),false, result);
                break;
            case R.id.album:
                LCode = "L2";
                ArrayList<MediumVO> mediumVOS = new ArrayList<>();
                String[] year = {"1927-1946", "1947-1966","1967-1986", "1987-2006", "2007-2016", "2017-현재"};
                for(int i = 0 ; i<year.length;i++){
                    MediumVO mediumVO = new MediumVO();
                }
                result.putString("LCode", LCode);
                ((MainActivity) Objects.requireNonNull(getActivity())).setStartFragment(new AlbumFragment(),false, result);
                break;
            case R.id.great_man:
                LCode = "L3";
                result.putString("LCode", LCode);
                ((MainActivity) Objects.requireNonNull(getActivity())).setStartFragment(new GreatFragment(),false, result);
                break;
        }
        /*httpRequestService.getMediumList(LCode).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.isSuccessful() && response.body() != null){
                    Gson gson = new Gson();
                    if(!response.body().get("data").toString().equals("null")){
                        JsonObject jsonObject = response.body().getAsJsonObject("data");
                        JsonArray jsonArray = jsonObject.getAsJsonArray("mediums");
                        for (JsonElement element : jsonArray) {
                            MediumVO mediumVO = gson.fromJson(element, MediumVO.class);
                            Log.d("getTitle", " " + mediumVO.getTitle());
                            Log.d("getImage", " " + mediumVO.getImage());
                            Log.d("getCode", " " + mediumVO.getCode());
                            mediumVOS.add(mediumVO);
                        }

                        Bundle result = new Bundle();
                        result.putParcelableArrayList("mediumVOS", mediumVOS);
                        result.putString("LCode", LCode);

                        ((MainActivity) Objects.requireNonNull(getActivity())).setStartFragment(new MediumFragment(),false, result);
                    }
                    else{
                        Log.d("data","null");
                    }

                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });*/
    }
}
