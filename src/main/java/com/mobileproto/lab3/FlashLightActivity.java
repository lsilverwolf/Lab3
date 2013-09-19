package com.mobileproto.lab3;

import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import java.io.IOException;

/**
 * Created by evan on 9/18/13.
 */
public class FlashLightActivity extends Activity implements SurfaceHolder.Callback{


    public Camera mCamera;
    public SurfaceHolder mHolder;

    @Override
    public void onCreate(Bundle savedIntanceState){
        super.onCreate(savedIntanceState);
        setContentView(R.layout.activity_flashlight);

        SurfaceView preview = (SurfaceView) findViewById(R.id.surfaceView);
        mHolder = preview.getHolder();
        mHolder.addCallback(this);
        mCamera = Camera.open();
        try{
            mCamera.setPreviewDisplay(mHolder);
        }catch (IOException e){
            Log.e("IOException", e.getMessage());
        }


        ToggleButton onOff = (ToggleButton) findViewById(R.id.flashlightToggle);

        onOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (!b){
                    // Turn off LED
                    Camera.Parameters params = mCamera.getParameters();
                    params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                    mCamera.setParameters(params);
                    mCamera.stopPreview();
                }
                else {
                    // Turn on LED
                    Camera.Parameters params = mCamera.getParameters();
                    params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                    mCamera.setParameters(params);
                    mCamera.startPreview();
                }
            }
        });

    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {}

    public void surfaceCreated(SurfaceHolder holder) {
        mHolder = holder;
        try{
            mCamera.setPreviewDisplay(mHolder);
        }catch (IOException e){
            Log.e("IOException", e.getMessage());
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        mCamera.stopPreview();
        mCamera.release();
        mHolder = null;
    }


}
