package com.baidu.aip.asrwakeup3.mvp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.baidu.aip.asrwakeup3.R;
import com.baidu.aip.asrwakeup3.mvp.contract.OpenCVContract;
import com.baidu.aip.asrwakeup3.mvp.presenter.MainPresenter;
import com.baidu.aip.asrwakeup3.mvp.presenter.OpenCVPresenter;
import com.baidu.aip.asrwakeup3.network.schedulers.SchedulerProvider;

import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Calendar;

public class OpenCVCameraActivity extends BaseActivity implements CameraBridgeViewBase.CvCameraViewListener, JavaCameraView.PhotoSuccessCallback, OpenCVContract.View {

    JavaCameraView openCvCameraView;
    private CascadeClassifier cascadeClassifier;
    private Mat grayscaleImage;
    private int absoluteFaceSize;
    private String fileName = "";
    int faceSerialCount = 0;
    private boolean isPhoteTakingPic = false;
    private String path = "/sdcard/face/";
    private OpenCVPresenter presenter;
    private void initializeOpenCVDependencies() {
        try {
            // Copy the resource into a temp file so OpenCV can load it
            InputStream is = getResources().openRawResource(R.raw.lbpcascade_frontalface);
            File cascadeDir = getDir("cascade", Context.MODE_PRIVATE);
            File mCascadeFile = new File(cascadeDir, "lbpcascade_frontalface.xml");
            FileOutputStream os = new FileOutputStream(mCascadeFile);
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            is.close();
            os.close();
            // Load the cascade classifier
            cascadeClassifier = new CascadeClassifier(mCascadeFile.getAbsolutePath());
        } catch (Exception e) {
            Log.e("OpenCVActivity", "Error loading cascade", e);
        }
        // And we are ready to go
        openCvCameraView.enableView();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        openCvCameraView =  findViewById(R.id.jcv);
        openCvCameraView.setCameraIndex(0);//更换摄像头1前置摄像头
        openCvCameraView.setCvCameraViewListener(this);
        openCvCameraView.setPhotoSuccessCallback(this);
        presenter = new OpenCVPresenter(this, SchedulerProvider.getInstance());
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_open_cv;
    }


    @Override
    public void onResume() {
        super.onResume();
        if (!OpenCVLoader.initDebug()) {
            Log.e("log_wons", "OpenCV init error");
        }
        initializeOpenCVDependencies();
    }

    @Override
    public void onCameraViewStarted(int width, int height) {
        grayscaleImage = new Mat(height, width, CvType.CV_8UC4);
        absoluteFaceSize = (int) (height * 0.2);
    }

    @Override
    public void onCameraViewStopped() {
    }

    @Override
    public Mat onCameraFrame(Mat aInputFrame) {

        Imgproc.cvtColor(aInputFrame, grayscaleImage, Imgproc.COLOR_RGBA2RGB);
        MatOfRect faces = new MatOfRect();
        if (cascadeClassifier != null) {
            cascadeClassifier.detectMultiScale(grayscaleImage, faces, 1.1, 2, 2,
                    new Size(absoluteFaceSize, absoluteFaceSize), new Size());
        }
        Rect[] facesArray = faces.toArray();
        int faceCount = facesArray.length;
        if (faceCount > 0) {
            faceSerialCount++;
        } else {
            faceSerialCount = 0;
        }
        if (faceSerialCount > 5) {
            if(!isPhoteTakingPic){
                File folder = new File(path);
                if (!folder.exists()){
                    folder.mkdirs();
                }
                fileName = path+ File.separator + getTime() + ".jpeg";
                openCvCameraView.takePhoto(fileName);
                Log.i("拍","拍摄照片啦");
            }
            faceSerialCount = -5000;
        }

        for (int i = 0; i < facesArray.length; i++) {
            Imgproc.rectangle(aInputFrame, facesArray[i].tl(), facesArray[i].br(), new Scalar(0, 255, 0, 255), 3);
        }
        return aInputFrame;
    }

    public long getTime() {
        return Calendar.getInstance().getTimeInMillis();
    }


    @Override
    public void doThing() {
        isPhoteTakingPic = true;
        Log.i("拍","拍好了！！！！！！！！！！！！");
        presenter.upLoadPicFile(fileName);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.despose();
    }

    @Override
    public void getDataSuccess() {

    }

    @Override
    public void getDataFail() {

    }
}