package com.lpf.demo;

import android.content.ContentResolver;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static com.lpf.demo.R.id.btn1;

public class MainActivity extends AppCompatActivity {

//    @butterknife.BindView(R.id.btn1)
//    Button btn1;
//    @butterknife.BindView(R.id.btn2)
//    Button btn2;
//    @butterknife.BindView(R.id.btn3)
//    Button btn3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "current->" + getScreenBrightness(), Toast.LENGTH_SHORT).show();
            }
        });
        findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setWindowBrightness(250);
                Toast.makeText(MainActivity.this, "current->" + getScreenBrightness(), Toast.LENGTH_SHORT).show();

            }
        });
        findViewById(R.id.btn3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setWindowBrightness(50);
                Toast.makeText(MainActivity.this, "current->" + getScreenBrightness(), Toast.LENGTH_SHORT).show();

            }
        });
    }


    private float getScreenBrightness() {
//        ContentResolver contentResolver = getContentResolver();
//        int defVal = 125;
//        return Settings.System.getInt(contentResolver,
//                Settings.System.SCREEN_BRIGHTNESS, defVal);

        return getWindow().getAttributes().screenBrightness * 255;
    }

    private void setWindowBrightness(int brightness) {
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.screenBrightness = brightness / 255.0f;
        window.setAttributes(lp);

    }
}
