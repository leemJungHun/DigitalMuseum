package com.example.digitalmuseum.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
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
    Bundle bundle;
    private ArrayList<SmallVO> smallVOS = new ArrayList<>();
    boolean itemClick = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_album, container, false);

        bundle = getArguments();
        assert bundle != null;

        //addMediumVO


        //Collections.sort(mediumVOS, (mediumVO, t1) -> mediumVO.getCode().compareTo(t1.getCode()));

        binding.backPage.setOnClickListener(this);
        binding.year2040.setOnClickListener(this);
        binding.year5070.setOnClickListener(this);
        binding.year8000.setOnClickListener(this);
        binding.year0020.setOnClickListener(this);

        return binding.getRoot();
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.backPage) {
            ((MainActivity) Objects.requireNonNull(getActivity())).setStartFragment(new SubFragment(), true, null);
        } else {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(HttpRequestService.URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            HttpRequestService httpRequestService = retrofit.create(HttpRequestService.class);

            if (!itemClick) {
                itemClick = true;
                httpRequestService.getSmallList(view.getTag().toString()).enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        itemClick = false;
                        if (response.isSuccessful() && response.body() != null) {
                            Gson gson = new Gson();
                            String contentTitle = "";
                            Fragment fragment;
                            fragment = new AlbumSebuFragment();
                            switch (view.getTag().toString()) {
                                case "M7":
                                    contentTitle = "졸업앨범 - 1920 ~ 1949";
                                    fragment = new Album2040Fragment();
                                    break;
                                case "M8":
                                    contentTitle = "졸업앨범 - 1950 ~ 1979";
                                    break;
                                case "M9":
                                    contentTitle = "졸업앨범 - 1980 ~ 1999";
                                    break;
                                case "M10":
                                    contentTitle = "졸업앨범 - 2000 ~ 현재";
                                    break;

                            }
                            if (!response.body().get("data").toString().equals("null")) {
                                JsonArray jsonArray = response.body().getAsJsonArray("data");

                                for (JsonElement element : jsonArray) {
                                    SmallVO dataVO = gson.fromJson(element, SmallVO.class);
                                    Log.d("getTitle", " " + dataVO.getTitle());
                                    Log.d("getImage", " " + dataVO.getImage());
                                    Log.d("getCode", " " + dataVO.getCode());
                                    smallVOS.add(dataVO);
                                }
                                bundle.putParcelableArrayList("smallVOS", smallVOS);
                                bundle.putString("type", "album");

                                bundle.putString("MCode", view.getTag().toString());
                                bundle.putString("ContentTitle", contentTitle);

                                ((MainActivity) Objects.requireNonNull(getActivity())).setStartFragment(fragment, false, bundle);
                            } else {
                                bundle.putString("type", "album");
                                bundle.putString("ContentTitle", contentTitle);
                                bundle.putString("MCode", view.getTag().toString());

                                ((MainActivity) Objects.requireNonNull(getActivity())).setStartFragment(new NoContentsFragment(), false, bundle);
                                Log.d("data", "null");
                            }

                        }

                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        itemClick = false;
                    }
                });
            }
        }
    }
}
