package com.lpf.playlight;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

//import static com.lpf.playlight.App.camera;
//import static com.lpf.playlight.App.isCameraEnabled;

/**
 * Created by liupengfei on 2017/7/4 18:10.
 */

public class CameraUtil {

//
//    public static void openCamera(Context context) {
//        isCameraEnabled = context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
//        if ((Build.VERSION.SDK_INT >= 23) && ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//            return;
//        }
//        String manufactures = android.os.Build.MANUFACTURER;
//        if (isCameraEnabled) {
//            try {
////                closeCamera();
//                if (camera == null) {
//                    camera = Camera.open();
//                    if (manufactures != null && manufactures.toLowerCase().contains("huawei")) {
//                    } else {
//                        camera.setPreviewTexture(new SurfaceTexture(0));
//                    }
//                    camera.startPreview();
//                }
//                if (camera == null)
//                    isCameraEnabled = false;
//            } catch (Exception e) {
////                Crashlytics.getInstance().core.logException(e);
//                isCameraEnabled = false;
//                try {
//                    new AlertDialog.Builder(context)
//                            .setTitle("Attention")
//                            .setMessage("Your camera flashlight is occupied by another app, please close that app and try again.")
//                            .setPositiveButton("OK", null)
//                            .show();
//                } catch (Exception e1) {
////                    Crashlytics.getInstance().core.logException(e1);
//                }
//            }
//        } else {
//            SharedPreferences prefs = context.getSharedPreferences("camera_info", 0);
//            if (!prefs.getBoolean("nocamera", false)) {
//                new AlertDialog.Builder(context)
//                        .setTitle("Attention")
//                        .setMessage("Your phone doesn't have camera flashlight. We'll light up the screen instead.")
//                        .setPositiveButton("OK", null)
//                        .show();
//
//                SharedPreferences.Editor editor = prefs.edit();
//                editor.putBoolean("nocamera", true);
//                editor.commit();
//            }
//        }
//    }
}
