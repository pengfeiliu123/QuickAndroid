package com.lpf.playlight;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.ads.MobileAds;
import com.lpf.playlight.MainActivity;
import com.lpf.playlight.R;

public class App extends Application {

    public static App instance;

    // Power manager
    public PowerManager pm;
    public PowerManager.WakeLock wl;

    // Camera availability
    public boolean isCameraEnabled;
    public Camera camera;

    // Blink Handler
    private Thread mThread;
    private boolean isStopThread = false;

    // Strobe
    public final static float STROBE_SWITCH_MIN = 0;
    public final static float STROBE_SWITCH_MAX = 1;
    public boolean blinkOn = true;

    // Audios
    public MediaPlayer switchOnPlayer;
    public MediaPlayer switchOffPlayer;
    public MediaPlayer shutterUpPlayer;
    public MediaPlayer switchLevelPlayer;
    public MediaPlayer switchLevelEndPlayer;

    // SOS
    public final static float SOS_FREQ_LONG = 1f;
    public final static float SOS_FREQ_SHORT = 0.5f;
    public final static float SOS_FREQ_WAIT_NEXT_CYCLE = 1f;

    public final static int SOS_STATUS_FREQ_LONG = 0;
    public final static int SOS_STATUS_FREQ_SHORT = 1;
    public final static int SOS_STATUS_FREQ_SECOND_LONG = 2;
    public final static int SOS_STATUS_FREQ_WAIT_NEXT_CYCLE = 3;
    public final static int SOS_STATUS_COUNT = SOS_STATUS_FREQ_WAIT_NEXT_CYCLE + 1;
    public int sosStatus = SOS_STATUS_FREQ_LONG;

    private final static int BLINK_COUNT_PER_CYCLE = 3;
    public int sosBlinkCount = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initFabric();
        initApp();
        initFlurry();
        initAdjust();
//        initFacebook();
//        initAd();
    }

    private void initAd() {
//        MobileAds.initialize(this, AdmobNativeAdManager.ADMOB_APP_ID);
    }

//    private void initFacebook() {
//        FacebookSdk.sdkInitialize(this);
//        AppEventsLogger.activateApp(this);
//    }

    protected void initFabric() {
//        CrashlyticsCore core = new CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build();
//        Fabric.with(this, new Crashlytics.Builder().core(core).build(), new Crashlytics());
    }

    private void initFlurry() {
        // configure Flurry
//        FlurryAgent.setLogEnabled(false);
//        // init Flurry
//        FlurryAgent.init(this, NGCommonConfiguration.FLURRY_KEY);
    }

    private void initAdjust() {
//        AdjustConfig config = new AdjustConfig(this, NGCommonConfiguration.ADJUST_TOKEN, AdjustConfig.ENVIRONMENT_PRODUCTION);
//        Adjust.onCreate(config);
//        registerActivityLifecycleCallbacks(new AdjustLifecycleCallbacks());
    }

//    private static final class AdjustLifecycleCallbacks implements ActivityLifecycleCallbacks {
//        @Override
//        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
//
//        }
//
//        @Override
//        public void onActivityStarted(Activity activity) {
//
//        }
//
//        @Override
//        public void onActivityResumed(Activity activity) {
//            Adjust.onResume();
//        }
//
//        @Override
//        public void onActivityPaused(Activity activity) {
//            Adjust.onPause();
//        }
//
//        @Override
//        public void onActivityStopped(Activity activity) {
//
//        }
//
//        @Override
//        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
//
//        }
//
//        @Override
//        public void onActivityDestroyed(Activity activity) {
//
//        }
//    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public void initApp() {

        // Load preferences
//        AppSettingInfo.getInstance(getApplicationContext());
//        AppSettingInfo.getInstance(getApplicationContext()).setLightVolume(STROBE_SWITCH_MIN);

        // init keyguard
        pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wl = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "FlashLight");

        // init media player
        initMediaPlayers();
    }

    private void initMediaPlayers() {
//        try {
//            switchOnPlayer = MediaPlayer.create(getApplicationContext(), R.raw.sound_on);
//            switchOffPlayer = MediaPlayer.create(getApplicationContext(), R.raw.sound_off);
//            shutterUpPlayer = MediaPlayer.create(getApplicationContext(), R.raw.shutter_up);
//            switchLevelPlayer = MediaPlayer.create(getApplicationContext(), R.raw.switch_level);
//            switchLevelEndPlayer = MediaPlayer.create(getApplicationContext(), R.raw.switch_end);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    public void openCamera() {
        isCameraEnabled = getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        if ((Build.VERSION.SDK_INT >= 23) && ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        String manufactures = android.os.Build.MANUFACTURER;
        if (isCameraEnabled) {
            try {
//                closeCamera();
                if(camera == null) {
                    camera = Camera.open();
                    if (manufactures != null && manufactures.toLowerCase().contains("huawei")) {
                    } else {
                        camera.setPreviewTexture(new SurfaceTexture(0));
                    }
                    camera.startPreview();
                }
                if (camera == null)
                    isCameraEnabled = false;
            } catch (Exception e) {
//                Crashlytics.getInstance().core.logException(e);
                isCameraEnabled = false;
                try {
                    new AlertDialog.Builder(MainActivity.instance)
                            .setTitle("Attention")
                            .setMessage("Your camera flashlight is occupied by another app, please close that app and try again.")
                            .setPositiveButton("OK", null)
                            .show();
                } catch (Exception e1) {
//                    Crashlytics.getInstance().core.logException(e1);
                }
            }
        } else {
            SharedPreferences prefs = getApplicationContext().getSharedPreferences("camera_info", 0);
            if (!prefs.getBoolean("nocamera", false)) {
                new AlertDialog.Builder(MainActivity.instance)
                        .setTitle("Attention")
                        .setMessage("Your phone doesn't have camera flashlight. We'll light up the screen instead.")
                        .setPositiveButton("OK", null)
                        .show();

                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("nocamera", true);
                editor.commit();
            }
        }
    }

    public void closeCamera() {
        if (camera != null) {
            camera.stopPreview();
            camera.release();
            camera = null;
        }
    }

    public void turnOnCamera() {
        try {
            if (isCameraEnabled) {
                Parameters p = camera.getParameters();
                p.setFlashMode(Parameters.FLASH_MODE_TORCH);
                camera.setParameters(p);
            }
        } catch (Exception e) {
//            Crashlytics.getInstance().core.logException(e);
        }
    }

    public void turnOffCamera() {
        try {
            if (isCameraEnabled) {
                Parameters params = App.instance.camera.getParameters();
                params.setFlashMode(Parameters.FLASH_MODE_OFF);
                App.instance.camera.setParameters(params);
            }
        } catch (Exception e) {
//            Crashlytics.getInstance().core.logException(e);
        }
    }

    /************************************
     * BLINK HANDLER
     *************************************/
    public void startLightHandler() {
        // start animation thread
        isStopThread = false;
        mThread = new Thread(mEffectionRunnable);
        mThread.start();
    }

    public void stopLightHandler() {
        if (mThread != null) {
            mThread.interrupt();
            try {
                isStopThread = true;
                mThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mThread = null;
        }
    }


    /********************************
     * Effection Handler
     **********************************/
    private Runnable mEffectionRunnable = new Runnable() {
        public void run() {
            android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_LOWEST);


            while (mThread != null && !mThread.isInterrupted() && !isStopThread) {
//                if (AppSettingInfo.getInstance(MainActivity.instance).isSwitchOn()) {
//                    if (MainActivity.instance.isLightOn()) {
//                        MainActivity.instance.lightEffection();
//                        lightEffection();
//                    } else if (MainActivity.instance.isLightStrobe()) {
//                        MainActivity.instance.strobeEffection();
//                        strobeEffection();
//                    } else if (MainActivity.instance.isLightSOS()) {
//                        MainActivity.instance.SOSEffection();
//                        sosEffection();
//                    }
//                } else {
//                    MainActivity.instance.offEffection();
//                    switchOffEffection();
//                }
            }
        }

        private void lightEffection() {
            App.instance.blinkOn = true;

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private void strobeEffection() {
//            float seconds = STROBE_SWITCH_MAX * (1 - AppSettingInfo.getInstance(MainActivity.instance).getLightVolume());
//            try {
//                Thread.sleep((int) (seconds * 1000));
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//            blinkOn = !blinkOn;
        }

        private void sosEffection() {
            try {
                if (App.instance.sosStatus == SOS_STATUS_FREQ_LONG) {
                    Thread.sleep((int) (SOS_FREQ_SHORT * 500));
                } else if (App.instance.sosStatus == SOS_STATUS_FREQ_SHORT) {
                    Thread.sleep((int) (SOS_FREQ_LONG * 500));
                } else if (App.instance.sosStatus == SOS_STATUS_FREQ_SECOND_LONG) {
                    Thread.sleep((int) (SOS_FREQ_SHORT * 500));
                } else if (App.instance.sosStatus == SOS_STATUS_FREQ_WAIT_NEXT_CYCLE) {
                    Thread.sleep((int) (SOS_FREQ_WAIT_NEXT_CYCLE * 500));

                    App.instance.sosStatus = SOS_STATUS_FREQ_LONG;
                    App.instance.blinkOn = true;
                    return;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (!App.instance.blinkOn) {
                App.instance.sosBlinkCount++;
                if (App.instance.sosBlinkCount == BLINK_COUNT_PER_CYCLE) {
                    App.instance.sosBlinkCount = 0;

                    App.instance.sosStatus++;
                    App.instance.sosStatus = App.instance.sosStatus % SOS_STATUS_COUNT;
                }
            }

            App.instance.blinkOn = !App.instance.blinkOn;
            if (App.instance.sosStatus == SOS_STATUS_FREQ_WAIT_NEXT_CYCLE) {
                App.instance.blinkOn = false;
            }
        }

        private void switchOffEffection() {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };
}
