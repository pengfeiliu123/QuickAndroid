package com.lpf.playlight;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by liupengfei on 2017/7/5 17:24.
 */

public class GlobalSetting {

    private final static String PREFS_KEY_SWITCH_ON = "switch_on";
    private final static String PREFS_KEY_SOUND_ON = "sound_on";
    private final static String PREFS_KEY_THEME_ON = "theme_on";
    private final static String PREFS_KEY_LIGHT_VOLUME = "sos_volume";

    private Context mContext;

    // switch on / off
    private boolean switchOn = true;
    private boolean soundOn = true;
    private boolean themeOn = true;
    private float lightVolume = 1;

    private static GlobalSetting mSettingInfo = null;

    public static GlobalSetting getInstance(Context context) {
        if (mSettingInfo == null) {
            mSettingInfo = new GlobalSetting(context);
        }
        return mSettingInfo;
    }

    private GlobalSetting(Context context) {
        mContext = context;
        loadPrefs();
    }

    // Get/Set switch option
    public boolean isSwitchOn() {
        return switchOn;
    }

    public void setSwitchOn(boolean on) {
        switchOn = on;
        savePrefs();
    }

    // Get/Set sound option
    public boolean isSoundOn() {
        return soundOn;
    }

    public void setSoundOn(boolean on) {
        soundOn = on;
        savePrefs();
    }

    // Get/Set light volume option
    public float getLightVolume() {
        return lightVolume;
    }

    public void setLightVolume(float volume) {
        lightVolume = volume;
        savePrefs();
    }

    // Get/Set theme option
    public boolean isThemeOn() {
        return themeOn;
    }

    public void setThemeOn(boolean on) {
        themeOn = on;
        savePrefs();
    }


    public void loadPrefs() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        switchOn = prefs.getBoolean(PREFS_KEY_SWITCH_ON, true);
        soundOn = prefs.getBoolean(PREFS_KEY_SOUND_ON, true);
        themeOn = prefs.getBoolean(PREFS_KEY_THEME_ON, false);
        lightVolume = prefs.getFloat(PREFS_KEY_LIGHT_VOLUME, 1);
    }

    public void savePrefs() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putBoolean(PREFS_KEY_SWITCH_ON, switchOn);
        prefsEditor.putBoolean(PREFS_KEY_SOUND_ON, soundOn);
        prefsEditor.putBoolean(PREFS_KEY_THEME_ON, themeOn);
        prefsEditor.putFloat(PREFS_KEY_LIGHT_VOLUME, lightVolume);
        prefsEditor.commit();
    }
}
