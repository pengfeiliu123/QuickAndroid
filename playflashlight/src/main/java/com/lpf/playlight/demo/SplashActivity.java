package com.lpf.playlight.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ToggleButton;

import com.lpf.playlight.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SplashActivity extends AppCompatActivity {


    @BindView(R.id.switchBtn)
    ToggleButton switchBtn;
    @BindView(R.id.turnOn)
    ImageView turnOn;
    @BindView(R.id.turnOff)
    ImageView turnOff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.switchBtn, R.id.turnOn, R.id.turnOff})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.switchBtn:
                break;
            case R.id.turnOn:

                break;
            case R.id.turnOff:
                break;
        }
    }
}
