package com.lpf.quickandroid.gif;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.UUID;

public class PreferencesUtil {

    private static PreferencesUtil instance;

    private static SharedPreferences sharedPreferences;

    private PreferencesUtil(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static PreferencesUtil getInstance(Context context) {
        if (instance == null)
            instance = new PreferencesUtil(context);
        return instance;
    }

    public boolean isLoggedIn() {
        if (sharedPreferences != null && sharedPreferences.getString("user_name", null) != null
                && !"".equals(sharedPreferences.getString("user_name", ""))) {
            return true;
        }
        return false;
    }

    public String getUserName() {
        if (sharedPreferences != null) {
            return sharedPreferences.getString("user_name", "");
        }
        return "";
    }

    public boolean saveUserName(String name) {
        if (sharedPreferences != null) {
            return sharedPreferences.edit().putString("user_name", name)
                    .commit();
        }
        return false;
    }

    public String getUserId() {
        if (sharedPreferences != null) {
            String userId = sharedPreferences.getString("userId", "");
            if (userId.equals("")) {
                userId = getUuid();
            }
            return userId;
        }
        return "";
    }

    public boolean saveUserId(String userId) {
        if (sharedPreferences != null) {
            return sharedPreferences.edit().putString("userId", userId)
                    .commit();
        }
        return false;
    }

    public String getUuid() {
        if (sharedPreferences != null) {
            String uuid = sharedPreferences.getString("uuid", "");
            if (uuid.equals("")) {
                uuid = UUID.randomUUID().toString();
                saveUuid(uuid);
            }
            return uuid;
        }
        return "";
    }

    public boolean saveUuid(String uuid) {
        if (sharedPreferences != null) {
            return sharedPreferences.edit().putString("uuid", uuid)
                    .commit();
        }
        return false;
    }

    public String getXLogId() {
        if (sharedPreferences != null) {
            String uuid = sharedPreferences.getString("xLogId", "");
            if (uuid.equals("")) {
                uuid = UUID.randomUUID().toString();
                saveXLogId(uuid);
            }
            return uuid;
        }
        return "";
    }

    public boolean saveXLogId(String uuid) {
        if (sharedPreferences != null) {
            return sharedPreferences.edit().putString("xLogId", uuid).commit();
        }
        return false;
    }

    public boolean saveLastExitTime(long lastExitTime) {
        if (sharedPreferences != null) {
            return sharedPreferences.edit().putLong("lastExitTime", lastExitTime).commit();
        }
        return false;
    }

    public long getLastExitTime() {
        if (sharedPreferences != null) {
            return sharedPreferences.getLong("lastExitTime", -1);
        }
        return -1;
    }

    public boolean saveLastResetTime(long lastResetTime) {
        if (sharedPreferences != null) {
            return sharedPreferences.edit().putLong("lastResetTime", lastResetTime).commit();
        }
        return false;
    }

    public long getLastResetTime() {
        if (sharedPreferences != null) {
            return sharedPreferences.getLong("lastResetTime", -1);
        }
        return -1;
    }

    public boolean saveAutoPlaySetting(int autoPlaySetting) {
        if (sharedPreferences != null) {
            return sharedPreferences.edit().putInt("autoPlay", autoPlaySetting).commit();
        }
        return false;
    }

    public int getAutoPlaySetting() {
        if (sharedPreferences != null) {
            return sharedPreferences.getInt("autoPlay", 1);
        }
        return 1;
    }

    public String getSessionId() {
        if (sharedPreferences != null) {
            return sharedPreferences.getString("sid", "");
        }
        return "";
    }

    public boolean saveSessionId(String sid) {
        if (sharedPreferences != null) {
            return sharedPreferences.edit().putString("sid", sid)
                    .commit();
        }
        return false;
    }

    public String getUserPhoto() {
        if (sharedPreferences != null) {
            return sharedPreferences.getString("user_photo", "");
        }
        return "";
    }

    public boolean saveUserPhoto(String uri) {
        if (sharedPreferences != null) {
            return sharedPreferences.edit().putString("user_photo", uri)
                    .commit();
        }
        return false;
    }


    public String getCurrentChannel() {
        String userId = getUserId();
        if (sharedPreferences != null) {
            return sharedPreferences.getString("channel_" + userId, null);
        }
        return null;
    }

    // just save current channel id list
    public boolean saveCurrentChannel(String currentChannel) {
        String userId = getUserId();
        if (sharedPreferences != null) {
            return sharedPreferences.edit().putString("channel_" + userId, currentChannel).commit();
        }
        return false;
    }

    public String getAllChannel() {
        if (sharedPreferences != null) {
            return sharedPreferences.getString("allChannel", null);
        }
        return null;
    }

    public boolean saveAllChannel(String allChannel) {
        if (sharedPreferences != null) {
            return sharedPreferences.edit().putString("allChannel", allChannel).commit();
        }
        return false;
    }

    public String getChannelSample() {
        if (sharedPreferences != null) {
            return sharedPreferences.getString("channelSample", null);
        }
        return null;
    }

    public boolean saveChannelSample(String channelSample) {
        if (sharedPreferences != null) {
            return sharedPreferences.edit().putString("channelSample", channelSample).commit();
        }
        return false;
    }

    public String getLocal() {
        if (sharedPreferences != null) {
            return sharedPreferences.getString("local", null);
        }
        return null;
    }

    public boolean saveLocal(String local) {
        if (sharedPreferences != null) {
            return sharedPreferences.edit().putString("local", local).commit();
        }
        return false;
    }

    public int getScreenHeight() {
        if (sharedPreferences != null) {
            return sharedPreferences.getInt("screenHeight", -1);
        }
        return -1;
    }

    public boolean saveScreenHeight(int screenHeight) {
        if (sharedPreferences != null) {
            return sharedPreferences.edit().putInt("screenHeight", screenHeight).commit();
        }
        return false;
    }

    public boolean getStarRated() {
        if (sharedPreferences != null) {
            return sharedPreferences.getBoolean("star", false);
        }
        return false;
    }

    public boolean saveStarRated(boolean star) {
        if (sharedPreferences != null) {
            return sharedPreferences.edit().putBoolean("star", star).commit();
        }
        return false;
    }

    // get star show times
    public int getStarShowTimes() {
        if (sharedPreferences != null) {
            return sharedPreferences.getInt("starShowedTime", 0);
        }
        return 0;
    }

    public boolean saveStarShowTimes(int starShowedTime) {
        if (sharedPreferences != null) {
            return sharedPreferences.edit().putInt("starShowedTime", starShowedTime).commit();
        }
        return false;
    }

    // get last time star showed
    public long getStarLastShowTime() {
        if (sharedPreferences != null) {
            return sharedPreferences.getLong("starLastShowTime", -1);
        }
        return -1;
    }

    public boolean saveStarLastShowTime(long lastStarShowTime) {
        if (sharedPreferences != null) {
            return sharedPreferences.edit().putLong("starLastShowTime", lastStarShowTime).commit();
        }
        return false;
    }

    // app is the first time to start
    public boolean getIsFirstToStart() {
        if (sharedPreferences != null) {
            return sharedPreferences.getBoolean("isFirstToStart", true);
        }
        return true;
    }

    public boolean saveIsFirstToStart(boolean isFirstToStart) {
        if (sharedPreferences != null) {
            return sharedPreferences.edit().putBoolean("isFirstToStart", isFirstToStart).commit();
        }
        return false;
    }

    public boolean getIsFirstTime() {
        if (sharedPreferences != null) {
            return sharedPreferences.getBoolean("isFristTimeToRun", true);
        }
        return true;
    }

    public boolean saveIsFirstTime(boolean isFirstTimeToRun) {
        if (sharedPreferences != null) {
            return sharedPreferences.edit().putBoolean("isFristTimeToRun", isFirstTimeToRun).commit();
        }
        return false;
    }

    // channel tip is showed or not
    public boolean getIsChannelTipShowed() {
        if (sharedPreferences != null) {
            return sharedPreferences.getBoolean("isChannelTipShowed", false);
        }
        return false;
    }

    public boolean saveIsChannelTipShowed(boolean isChannelTipShowed) {
        if (sharedPreferences != null) {
            return sharedPreferences.edit().putBoolean("isChannelTipShowed", isChannelTipShowed).commit();
        }
        return false;
    }
}
