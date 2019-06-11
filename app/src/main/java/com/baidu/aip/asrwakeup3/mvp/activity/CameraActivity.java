package com.baidu.aip.asrwakeup3.mvp.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import com.baidu.aip.asrwakeup3.R;
import com.baidu.aip.asrwakeup3.bean.FaceCheckBean;
import com.baidu.aip.asrwakeup3.mvp.contract.OpenCVContract;
import com.baidu.aip.asrwakeup3.mvp.presenter.OpenCVPresenter;
import com.baidu.aip.asrwakeup3.network.schedulers.SchedulerProvider;

import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.android.Utils;
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
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;

public class CameraActivity extends RobotTTSActivity implements OpenCVContract.View {
    private static final String TAG = "CameraActivity";
    @BindView(R.id.camera)
    JavaCameraView mCameraView;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.score)
    TextView score;
    CascadeClassifier cascadeClassifier;

    Mat grayscaleImage;
    private int absoluteFaceSize;
    private String PATH = "/sdcard/face2/";
    int faceSerialCount = 0;
    private boolean isPhoteTakingPic = false;
    String fileName;
    private OpenCVPresenter presenter;
    private boolean isCheckFace = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera);
        presenter = new OpenCVPresenter(this, SchedulerProvider.getInstance());
        mCameraView.setCameraIndex(CameraBridgeViewBase.CAMERA_ID_FRONT);//打开前置摄像头
        mCameraView.setCvCameraViewListener(new CameraBridgeViewBase.CvCameraViewListener() {
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
                        File folder = new File(PATH);
                        if (!folder.exists()){
                            folder.mkdirs();
                        }
                        savePicture(aInputFrame);
                        Log.i(TAG,"拍摄照片啦");
                    }
                    faceSerialCount = -5000;
                }

                for (int i = 0; i <facesArray.length; i++){
                    Imgproc.rectangle(aInputFrame, facesArray[i].tl(), facesArray[i].br(), new Scalar(0, 255, 0, 255), 3);
                }

                return aInputFrame;
            }
        });

        initClassifier();
    }

    private void savePicture(Mat frameData){
        isPhoteTakingPic =true;
        Bitmap bitmap = Bitmap.createBitmap(frameData.width(), frameData.height(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(frameData, bitmap);
        fileName = PATH + File.separator + getTime() + ".jpg";
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(fileName);
            //int quality 图像压缩率，0-100。 0 压缩100%，100意味着不压缩；
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(outputStream != null){
                try {
                    outputStream.close();
                    presenter.upLoadPicFile(fileName);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public long getTime() {
        return Calendar.getInstance().getTimeInMillis();
    }

    private void initClassifier() {
        Log.i("xxx","you mei you paizhao ya----------------->");
        InputStream is = getResources().openRawResource(R.raw.lbpcascade_frontalface);
        File cascadeDir = getDir("cascade", Context.MODE_PRIVATE);
        File mCascadeFile = new File(cascadeDir, "lbpcascade_frontalface.xml");
        FileOutputStream os = null;
        try {
            os = new FileOutputStream(mCascadeFile);
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            is.close();
            os.close();
            cascadeClassifier = new CascadeClassifier(mCascadeFile.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mCameraView.enableView();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mCameraView){
            mCameraView.disableView();
        }
    }

    @Override
    public void getDataSuccess(FaceCheckBean bean) {
        List<FaceCheckBean.ResultBean> list =  bean.getResult();
        StringBuilder builder = new StringBuilder();
        builder.delete(0,builder.length());
        if(list.size()>0){
            for (int i =0;i<list.size();i++){
               FaceCheckBean.ResultBean b = list.get(i);
                builder.append("姓名："+b.getLabel());
                float score =(float)b.getScore();
                builder.append("，识别分数："+score);
                if(score>0.55){
                    builder.append( " ,检测成功！"+"\n");
                    speak(b.getLabel()+"识别成功！");
                    isCheckFace = true;
                }else {
                    builder.append( " ,检测失败！"+"\n");
                    speak(b.getLabel()+"识别失败！");
                }
            }
        }else {
            builder.append("检测失败,未识别到人脸！");
            speak("检测失败,未识别到人脸信息！");
        }
        name.setText(builder.toString());
        deletePic();
    }

    @Override
    public void getDataFail() {
        deletePic();

    }

    private void deletePic() {
        File file =  new File(fileName);
        if(file.exists()){
            file.delete();
            Log.i(TAG,"deletePic+++");
        }
        isPhoteTakingPic = false;
    }

    protected void ttsFinish(){
          if(isCheckFace){
              finish();
          }
    }
}
