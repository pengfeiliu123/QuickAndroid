package com.lpf.playlight.demo;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.lpf.playlight.MainActivity;
import com.lpf.playlight.R;
import com.lpf.playlight.colorpick.ColorPickerDialog;
import com.lpf.playlight.colorpick.ColorPickerSwatch;

public class FlashLightActivity extends FragmentActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
    private static final String TAG = "FlashLightActivity";
    private static final String LONG_PRESS = "long_press";
    public static final String WHITE = "white";
    private final Flash flash = new Flash();
    private LinearLayout background;
    private ToggleButton theButton;
    private Drawable dark;
    private boolean changeColor = false;

    private SharedPreferences sharedPreferences;

    private SeekBar lightSeekBar;
    private ImageView colorChoose;
    private LinearLayout flashLightLayout;

    private float lastDistance = 0;
    private float currentDistance = 0;
    private float scaleSize = 1f;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        theButton = (ToggleButton) findViewById(R.id.flashlightButton);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        background = (LinearLayout) findViewById(R.id.background);
        background.setOnLongClickListener(new LongClickListener());

        theButton.setTextOn("Off");
        theButton.setTextOff("On");

        lightSeekBar = (SeekBar) findViewById(R.id.light_seekbar);
        lightSeekBar.setMax(254);
        lightSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                setWindowBrightness(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        final ColorPickerDialog colorPickerDialog = new ColorPickerDialog();
        int[] colors = getResources().getIntArray(R.array.color_choose);
        colorPickerDialog.initialize(R.string.title, colors, colors[0], 5, 2);
        colorPickerDialog.setOnColorSelectedListener(new ColorPickerSwatch.OnColorSelectedListener() {

            @Override
            public void onColorSelected(int color) {
                background.setBackgroundColor(color);
            }
        });

        colorChoose = (ImageView) findViewById(R.id.color_choose);
        colorChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                background.setBackgroundColor(Color.parseColor("#FFFFFF"));
                colorPickerDialog.show(getSupportFragmentManager(), "colorpicker");
            }
        });

        flashLightLayout = (LinearLayout) findViewById(R.id.flashLightLayout);
        background.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //更兼容的方式
                int actionMasked = MotionEventCompat.getActionMasked(event);

                switch (actionMasked) {

                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        //当触控点多于两个时进行缩放操作
                        if (event.getPointerCount()>=2){
                            //设置当前的两点间的距离
                            setCurrentDistance(event.getX(0),event.getY(0),event.getX(1),event.getY(1));
                            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) flashLightLayout.getLayoutParams();
                            //获取用户屏幕的宽度
                            Point point = new Point();
                            ((WindowManager)getApplicationContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getSize(point);
                            int screenSize = point.x;
                            //当当前距离大于之前距离时，进行放大操作
                            if (lastDistance <= 0 ){
                                lastDistance = currentDistance;
                            } else{
                                if (currentDistance>lastDistance){
                                    if(flashLightLayout.getWidth() < screenSize /2){
                                        scaleSize = 1.05f;
                                    }else{
                                        scaleSize = 1f;
                                    }
                                    //设置完放大参数后，将当前距离赋值给lastDistance
                                    lastDistance=currentDistance;
                                    //当当前距离小于之前距离时，进行缩小操作
                                } else if (currentDistance<lastDistance){
                                    //防止图片被缩小的太小，最小不能超过屏幕宽度的1/4
                                    if (flashLightLayout.getWidth()>screenSize/4){
                                        scaleSize = 0.95f;
                                    } else {
                                        scaleSize = 1f;
                                    }
                                    lastDistance=currentDistance;
                                }
                                //设置layoutParams进行缩放操作
                                layoutParams.height = (int) (flashLightLayout.getHeight()*scaleSize);
                                layoutParams.width = (int) (flashLightLayout.getWidth()*scaleSize);
                                flashLightLayout.setLayoutParams(layoutParams);
                            }
                        }
                        break;

                    //触摸动作抬起时将两个距离初始化
                    case MotionEvent.ACTION_UP:
                        lastDistance = 0;
                        break;
                }
                return true;
            }
        });
    }

    private float setCurrentDistance(float x1, float y1, float x2, float y2){
        currentDistance = (float) Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));
        return currentDistance;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Log.i(TAG, "Changed pref: " + key);
        switch (key) {
            case LONG_PRESS:
                new PressTask().execute();
                break;
            case WHITE:
                new WhiteTask().execute();
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

        if (theButton.isChecked()) {
            theButton.setEnabled(false);
            new FlashTask().execute();
            theButton.setKeepScreenOn(true);
        } else {
            flash.off();
        }

        new PressTask().execute();
        new WhiteTask().execute();
    }

    @Override
    public void onPause() {
        super.onPause();
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
        flash.close();
    }

    public void onToggleClicked(View v) {
        if (theButton.isChecked()) {
            new FlashTask().execute();
            v.setKeepScreenOn(true);
            theButton.setChecked(true);
//            if (changeColor) {
//                background.setBackgroundColor(Color.WHITE);
//            }
        } else {
            flash.off();
            v.setKeepScreenOn(false);
            theButton.setChecked(false);
//            if (background != null) {
//                background.setBackgroundDrawable(dark);
//            }
        }
    }

    public class LongClickListener implements View.OnLongClickListener {
        @Override
        public boolean onLongClick(View view) {
            theButton.setChecked(!theButton.isChecked());
            onToggleClicked(theButton);
            return true;
        }
    }

    public class FlashTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {
            return flash.on();
        }

        @Override
        protected void onPostExecute(Boolean success) {
            theButton.setEnabled(true);
            if (!success) {
                Toast.makeText(FlashLightActivity.this, "Failed to access camera.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class WhiteTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            return sharedPreferences.getBoolean(WHITE, false);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            changeColor = aBoolean;
//            if (changeColor && theButton.isChecked()) {
//                background.setBackgroundColor(Color.WHITE);
//            } else {
//                background.setBackgroundDrawable(dark);
//            }
        }
    }

    public class PressTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            return sharedPreferences.getBoolean(LONG_PRESS, false);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            background.setLongClickable(aBoolean);
        }
    }

    private void setWindowBrightness(int brightness) {
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.screenBrightness = brightness / 255.0f;
        window.setAttributes(lp);
    }
}
