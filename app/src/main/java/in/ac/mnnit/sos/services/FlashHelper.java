package in.ac.mnnit.sos.services;

import android.content.Context;
import android.hardware.Camera;

import java.io.IOException;

/**
 * Created by prashanth on 29/3/17.
 */

public class FlashHelper {
    private Context context;
    private boolean on = false;
    int count=0;
    private Camera mCamera;
    Camera.Parameters mParams;
    int delay = 100; // in ms



    public FlashHelper(Context context) {
        this.context = context;
    }

    public void startOn(){
        count++;

        if (count % 3==2) {
            Thread t = new Thread() {
                public void run() {
                    try {
                        // Switch on the cam for app's life
                        if (mCamera == null) {
                            // Turn on Cam
                            mCamera = Camera.open();
                            try {
                                mCamera.setPreviewDisplay(null);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            mCamera.startPreview();
                        }
                        int times = 10;

                        for (int i = 0;; i++) {
                            toggleFlashLight();
                            sleep(delay);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };

            t.start();
        }
        else if(count%3==0)
        {
            turnOff();
            if (mCamera != null) {
                mCamera.stopPreview();
                mCamera.release();
                mCamera = null;
            }
            count=0;
        }
        else if(count%3==1) {
            mCamera = Camera.open();
            on = false;
            toggleFlashLight();
        }

    }
    public void turnOn() {
        if (mCamera != null) {
            mParams = mCamera.getParameters();
            mParams.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            mCamera.setParameters(mParams);

            on = true;
        }
    }
    /** Turn the devices FlashLight off */
    public void turnOff() {
        // Turn off flashlight
        if (mCamera != null) {
            mParams = mCamera.getParameters();
            if (mParams.getFlashMode().equals(Camera.Parameters.FLASH_MODE_TORCH)) {
                mParams.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                mCamera.setParameters(mParams);
            }
        }
        on = false;
    }

    /** Toggle the flashlight on/off status */
    public void toggleFlashLight() {
        if (!on) { // Off, turn it on
            turnOn();
        } else { // On, turn it off
            turnOff();
        }
    }


}
