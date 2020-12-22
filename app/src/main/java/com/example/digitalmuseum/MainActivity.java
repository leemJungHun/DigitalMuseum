package com.example.digitalmuseum;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.example.digitalmuseum.databinding.ActivityMainBinding;
import com.example.digitalmuseum.dialog.CustomDialog;
import com.example.digitalmuseum.dialog.CustomDialog2;
import com.example.digitalmuseum.fragment.MainFragment;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityMainBinding binding;
    FragmentManager fragmentManager;
    Fragment mainFragment;
    Fragment nowFragment;
    boolean back = false;
    CustomDialog dialog;
    CustomDialog2 dialog2;

    TimerTask addTask;
    Timer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();

        binding.mainContainer.setOnClickListener(this);

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        mainFragment = new MainFragment();
        nowFragment = new MainFragment();

        transaction
                .setReorderingAllowed(true)
                .addToBackStack(null)
                .replace(R.id.main_container, new MainFragment()).commitAllowingStateLoss();

    }

    public void setStartFragment(Fragment fragment,boolean isBack, Bundle result) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();


        nowFragment = fragment;

        if(!isBack){
            transaction.setCustomAnimations(R.anim.pull_in_right, R.anim.push_out_left, R.anim.pull_in_right, R.anim.push_out_left);
        }else{
            transaction.setCustomAnimations(R.anim.pull_in_left, R.anim.push_out_right, R.anim.pull_in_left, R.anim.push_out_right);
        }

        if(result!=null){
            fragment.setArguments(result);
        }

        if(nowFragment!=mainFragment){
            Restart_Period();
        }

        transaction
                .setReorderingAllowed(true)
                .addToBackStack(null)
                .replace(R.id.main_container, fragment).commitAllowingStateLoss();
    }


    @Override
    public void onBackPressed() {
        if(back){

        }
    }

    public void Restart_Period(){
        Log.d("Restart","Task");
        Stop_Period();
        Start_Period();
    }

    public void Restart_Period(CustomDialog dialog){
        Log.d("Restart","Task");
        this.dialog = dialog;
        Stop_Period();
        Start_Period();
    }

    public void Restart_Period(CustomDialog2 dialog2){
        Log.d("Restart","Task");
        this.dialog2 = dialog2;
        Stop_Period();
        Start_Period();
    }

    public void Start_Period(){
        if(nowFragment!=mainFragment){
            SetAddTask();
            timer = new Timer();
            timer.schedule(addTask, 210*1000);
        }
    }

    public void Stop_Period(){
        if(timer !=null) {
            timer.cancel();
            timer = null;
        }
    }

    private void SetAddTask(){
        try {
            addTask = new TimerTask() {
                @Override
                public void run() {
                    Message msg = handler.obtainMessage();
                    handler.sendMessage(msg);
                }
            };
        }catch (Exception ignored){

        }
    }


    final Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            // 원래 하려던 동작 (UI변경 작업 등)
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            nowFragment = new MainFragment();

            if(dialog!=null){
                if(dialog.isShowing()){
                    dialog.dismiss();
                }
            }else if(dialog2!=null){
                if(dialog2.isShowing()){
                    dialog2.dismiss();
                }
            }

            transaction
                    .setReorderingAllowed(true)
                    .addToBackStack(null)
                    .replace(R.id.main_container, new MainFragment()).commitAllowingStateLoss();
            return false;
        }
    });

    @Override
    public void onClick(View view) {
        Restart_Period();
    }
}