package in.ac.mnnit.sos.services;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
/**
 * Created by walktoremember on 30/3/17.
 */

public class FlashLightHelper {

    private Context context;
    private CameraManager mCameraManager;
    private String mCameraId;
    private Boolean isFlashAvailable;
    private Boolean isTorchOn;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)

    public FlashLightHelper(Context context){
        this.context=context;
        isTorchOn=false;
        isFlashAvailable = context.getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        if (!isFlashAvailable){
            AlertDialog alert = new AlertDialog.Builder(context)
                    .create();
            alert.setTitle("Error !!");
            alert.setMessage("Your device doesn't support flash light!");
            alert.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // closing the application
                    //System.exit(0);
                }
            });
        }
        mCameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
        try {
            mCameraId = mCameraManager.getCameraIdList()[0];
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

    }

    public void startFlash(){
        try {
            if (isTorchOn) {
                turnOffFlashLight();
                isTorchOn = false;
            } else {
                turnOnFlashLight();
                isTorchOn = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void turnOnFlashLight() {

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                mCameraManager.setTorchMode(mCameraId, true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void turnOffFlashLight() {

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                mCameraManager.setTorchMode(mCameraId, false);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
