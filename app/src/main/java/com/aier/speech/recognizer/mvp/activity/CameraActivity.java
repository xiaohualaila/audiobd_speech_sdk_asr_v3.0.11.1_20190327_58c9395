package com.aier.speech.recognizer.mvp.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import com.aier.speech.recognizer.R;
import com.aier.speech.recognizer.bean.SimilarFaceResult;
import com.aier.speech.recognizer.mvp.contract.OpenCVContract;
import com.aier.speech.recognizer.mvp.presenter.CameraPresenter;
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

import butterknife.BindView;

public class CameraActivity extends BaseActivity implements OpenCVContract.View {


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

    private boolean isPhoteTakingPic = false;
    String fileName;
    private CameraPresenter presenter;
    private static Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new CameraPresenter(this);
        mCameraView.setCameraIndex(CameraBridgeViewBase.CAMERA_ID_BACK);//后置摄像头
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
                    if (!isPhoteTakingPic) {
                        isPhoteTakingPic = true;
                        File folder = new File(PATH);
                        if (!folder.exists()) {
                            folder.mkdirs();
                        }
                        savePicture(aInputFrame);
                        Log.i(TAG, "拍摄照片啦");
                    }
                }

                for (int i = 0; i < facesArray.length; i++) {
                    Imgproc.rectangle(aInputFrame, facesArray[i].tl(), facesArray[i].br(), new Scalar(0, 255, 0, 255), 3);
                }

                return aInputFrame;
            }
        });

        initClassifier();
    }

    @Override
    protected int getLayout() {
        return R.layout.camera;
    }

    private void savePicture(Mat frameData) {
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
        } finally {
            if (outputStream != null) {
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
        InputStream is = getResources().openRawResource(R.raw.lbpcascade_frontalface);
        File cascadeDir = getDir("cascade", MODE_PRIVATE);
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
    public void getDataSuccess(SimilarFaceResult bean) {
        isPhoteTakingPic = false;
        deletePic();

//        List<SimilarFaceResult.ResultBean> resultBeans = bean.getResult();
//        if (resultBeans.size() > 0) {
//            SimilarFaceResult.ResultBean bean1 = resultBeans.get(0);
//            Bundle bundle = new Bundle();
//            Intent intent = new Intent(this, DetailActivity.class);
//            bundle.putString("my_name", "");
//            bundle.putString("name", bean1.getName());
//            bundle.putString("duty", bean1.getDuty());
//            bundle.putString("description", bean1.getDescription());
//            String score = (bean1.getScore() * 100 + "").substring(0, 2);
//            bundle.putString("score", score);
//            bundle.putString("img", bean1.getDraw_image());
//            intent.putExtras(bundle);
//            startActivity(intent);
//        }
//        finish();
    }

    @Override
    public void getDataFail() {
        isPhoteTakingPic = false;
     //   EventBus.getDefault().post(MessageWrap.getInstance("未识别到人脸信息！"));
        deletePic();
    }

    private void deletePic() {
        File file = new File(fileName);
        if (file.exists()) {
            file.delete();
            Log.i(TAG, "deletePic+++");
        }
        isPhoteTakingPic = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.dispose();
        if (null != mCameraView) {
            mCameraView.disableView();
        }
        handler.removeCallbacksAndMessages(null);
    }

}
