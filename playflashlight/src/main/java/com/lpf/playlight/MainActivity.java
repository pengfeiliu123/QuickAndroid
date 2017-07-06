package com.lpf.playlight;

import android.Manifest;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    public static final int REQUEST_CODE_CAMERA = 1;
    public static final int REQUEST_CODE_CAMEAR_SWITCH = 2;
    public static MainActivity instance;

    public static boolean isAppActive = false;

    private HomeWatcher mHomeWatcher;

    private FrameLayout mFLWhite;
    private ToggleButton mSoundSwitch;
    private ToggleButton mSwitchButton;

    private SurfaceHolder surfaceHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        setContentView(R.layout.activity_main);

        initViews();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //评分
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkPermission(REQUEST_CODE_CAMERA);
        App.instance.openCamera();

        if (!isAppActive) {
            isAppActive = true;

            mHomeWatcher.startWatch();
            initUIComponents();

            mFLWhite.setKeepScreenOn(true);
            App.instance.startLightHandler();

            App.instance.blinkOn = true;
            turnOnCamera();
        }

        onUpdateUI();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }


    private void initViews() {
        mSwitchButton = (ToggleButton) findViewById(R.id.switchBtn);
        final String stateText = mSwitchButton.getText().toString();
        mSwitchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
//                    App.instance.openCamera();
                    turnOnCamera();
//                    mSwitchButton.setChecked(true);
                }else{
//                    App.instance.closeCamera();
                    turnOffCamera();
//                    mSwitchButton.setChecked(false);
                }
            }
        });
//        mSwitchButton.setOnCheckedChangeListener(this);
//        mSwitchButton.setChecked(true);
        mHomeWatcher = new HomeWatcher(this);
        mHomeWatcher.setOnHomePressedListener(new HomeWatcher.OnHomePressedListener() {
            @Override
            public void onHomePressed() {

            }

            @Override
            public void onHomeLongPressed() {

            }
        });
    }



//    @Override
//    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//        switch(buttonView.getId()){
//            case R.id.switchBtn:
//                if(isChecked){
//                    App.instance.closeCamera();
//                    mSwitchButton.setChecked(false);
//                }else{
//                    App.instance.openCamera();
//                    mSwitchButton.setChecked(true);
//                }
//                break;
//        }
//    }

    private void initUIComponents() {
        mFLWhite = (FrameLayout) findViewById(R.id.main_fl_white);
    }

    @Override
    public void onClick(View v) {

    }

    private boolean checkPermission(int requestCode) {
        if ((Build.VERSION.SDK_INT >= 23) && ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                Toast.makeText(getApplicationContext(), "Please allow Camera permission", Toast.LENGTH_LONG).show();
            }
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, requestCode);
            return true;
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_CAMERA: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    App.instance.openCamera();

                    turnOnCamera();
                    onUpdateUI();
                } else {
                    onUpdateUI();
                }
                return;
            }
            case REQUEST_CODE_CAMEAR_SWITCH: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    App.instance.openCamera();
//                    switchClicked();
                }
                return;
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (!AppTool.isAppOnForeground(this)) {

            mHomeWatcher.stopWatch();

            isAppActive = false;

            App.instance.stopLightHandler();
            turnOffCamera();
            App.instance.closeCamera();

            if (mFLWhite != null) {
                mFLWhite.setKeepScreenOn(false);
            }
        }
    }

    private void switchClicked(boolean isSwitchOn) {
        if (isSwitchOn) {
            App.instance.blinkOn = true;
            turnOnCamera();
        } else {
            turnOffCamera();
        }
        onUpdateUI();
    }

    private void onUpdateUI() {

//        mSwitchButton.setSwitchState();

    }

    private synchronized void turnOnCamera() {
        Thread.yield();
        if (App.instance.isCameraEnabled) {
            App.instance.openCamera();
            Camera camera = App.instance.camera;
            if (Build.VERSION.SDK_INT >= 23) {
                try {
                    if (surfaceHolder == null) {
                        surfaceHolder = ((SurfaceView) findViewById(R.id.preview)).getHolder();
                        surfaceHolder.addCallback(new SurfaceHolder.Callback() {
                            @Override
                            public void surfaceCreated(SurfaceHolder holder) {

                            }

                            @Override
                            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

                            }

                            @Override
                            public void surfaceDestroyed(SurfaceHolder holder) {

                            }
                        });
                        camera.setPreviewDisplay(surfaceHolder);
                        camera.startPreview();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            App.instance.turnOnCamera();
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mFLWhite != null) {
                        mFLWhite.setVisibility(View.VISIBLE);
                    }
                }
            });
        }
    }

    private synchronized void turnOffCamera() {
        if (App.instance.isCameraEnabled) {
            App.instance.turnOffCamera();
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mFLWhite != null) {
                        mFLWhite.setVisibility(View.GONE);
                    }
                }
            });
        }
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        onUpdateUI();
    }


}
































