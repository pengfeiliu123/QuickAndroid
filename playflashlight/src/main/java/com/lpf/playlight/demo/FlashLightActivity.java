package com.lpf.playlight.demo;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        theButton = (ToggleButton) findViewById(R.id.flashlightButton);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        background = (LinearLayout)findViewById(R.id.background);
        background.setOnLongClickListener(new LongClickListener());

        theButton.setTextOn("Off");
        theButton.setTextOff("On");

        lightSeekBar = (SeekBar)findViewById(R.id.light_seekbar);
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
        colorPickerDialog.initialize(R.string.title,colors, colors[0], 5, 2);
        colorPickerDialog.setOnColorSelectedListener(new ColorPickerSwatch.OnColorSelectedListener() {

            @Override
            public void onColorSelected(int color) {
                background.setBackgroundColor(color);
            }
        });

        colorChoose = (ImageView)findViewById(R.id.color_choose);
        colorChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                background.setBackgroundColor(Color.parseColor("#FFFFFF"));
                colorPickerDialog.show(getSupportFragmentManager(), "colorpicker");
            }
        });

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
            if (! success) {
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
