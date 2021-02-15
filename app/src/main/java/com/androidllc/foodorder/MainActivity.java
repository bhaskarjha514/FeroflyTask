package com.androidllc.foodorder;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.vinay.stepview.HorizontalStepView;
import com.vinay.stepview.models.Step;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private HorizontalStepView horizontalStepView;
    private List<Step> stepList;
    private int size;
    private int i=2;
    private Handler handler = new Handler();
    private Runnable runnable;
    private int delay = 5000;
    private RelativeLayout trackOrder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindID();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            handleNavigation();
        }

        handler.postDelayed(runnable= () -> {
            handler.postDelayed(runnable, delay);
            if (i<=size-1){
                horizontalStepView.setStepState(Step.State.COMPLETED,i);
                i+=1;
            }else{
                handler.removeCallbacks(runnable);
            }
        },delay);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void handleNavigation() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        Window window = getWindow();
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        window.setNavigationBarColor(getResources().getColor(R.color.colorbg2));
    }

    private void bindID() {
        horizontalStepView = findViewById(R.id.horizontal_step_view);
        trackOrder = findViewById(R.id.trackRl);
        String map = "http://maps.google.co.in/maps?q=" + "MGS Hospital, West Punjabi Bagh, Delhi, 110026, india";
        trackOrder.setOnClickListener(v->{
            try {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("geo:" + "28.673590"
                                + "," + "77.135560"
                                + "?q=" + "28.673590"
                                + "," + "77.135560"
                                + "(" + "Office Address" + ")"));
                intent.setComponent(new ComponentName(
                        "com.google.android.apps.maps",
                        "com.google.android.maps.MapsActivity"));
                startActivity(intent);
            } catch (ActivityNotFoundException e) {

                try {
                    startActivity(new Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("market://details?id=com.google.android.apps.maps")));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=com.google.android.apps.maps")));
                }

                e.printStackTrace();
            }
        });

        stepList = new ArrayList<>();
        stepList.add(new Step("Cooking", Step.State.COMPLETED));
        stepList.add(new Step("Picked",Step.State.COMPLETED));
        stepList.add(new Step("On way"));
        stepList.add(new Step("Delivered"));
        stepList.add(new Step("Done"));

        horizontalStepView.setSteps(stepList);
        size = stepList.size();

        horizontalStepView
                .setCompletedStepIcon(AppCompatResources.getDrawable(this, R.drawable.ic_check))
                .setNotCompletedStepIcon(AppCompatResources.getDrawable(this, R.drawable.ic_current))
                .setCurrentStepIcon(AppCompatResources.getDrawable(this, R.drawable.ic_current))
                .setCompletedStepTextColor(Color.DKGRAY)
                .setNotCompletedStepTextColor(Color.DKGRAY)
                .setCurrentStepTextColor(Color.BLACK)
                .setCompletedLineColor(Color.parseColor("#ffff1c6f"))
                .setNotCompletedLineColor(Color.parseColor("#01b399"))
                .setTextSize(13)
                .setCircleRadius(13)
                .setReverse(false);
    }
}
