package com.aier.speech.recognizer.mvp.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.aier.speech.recognizer.R;
import com.aier.speech.recognizer.bean.FaceCheckBean;
import com.aier.speech.recognizer.bean.QuestionRankResult;
import com.aier.speech.recognizer.bean.SimilarFaceResult;
import com.aier.speech.recognizer.bean.UniqidResult;
import com.aier.speech.recognizer.model.MessageWrap;
import com.aier.speech.recognizer.mvp.contract.CameraContract;
import com.aier.speech.recognizer.mvp.presenter.CameraPresenter;
import com.aier.speech.recognizer.util.ImageUtils;
import com.aier.speech.recognizer.util.SharedPreferencesUtil;

import org.greenrobot.eventbus.EventBus;
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

public class CameraActivity extends BaseActivity implements CameraContract.View {
    private static final String TAG = "CameraActivity";
    @BindView(R.id.camera)
    JavaCameraView mCameraView;


    @BindView(R.id.iv_left_pic_1)
    ImageView iv_left_pic_1;
    @BindView(R.id.iv_left_pic_2)
    ImageView iv_left_pic_2;
    @BindView(R.id.iv_left_pic_3)
    ImageView iv_left_pic_3;
    @BindView(R.id.iv_left_pic_4)
    ImageView iv_left_pic_4;
    @BindView(R.id.iv_right_pic_1)
    ImageView iv_right_pic_1;
    @BindView(R.id.iv_right_pic_2)
    ImageView iv_right_pic_2;
    @BindView(R.id.iv_right_pic_3)
    ImageView iv_right_pic_3;
    @BindView(R.id.iv_right_pic_4)
    ImageView iv_right_pic_4;

    @BindView(R.id.left_score_1)
    TextView left_score_1;
    @BindView(R.id.left_score_2)
    TextView left_score_2;
    @BindView(R.id.left_score_3)
    TextView left_score_3;
    @BindView(R.id.left_score_4)
    TextView left_score_4;

    @BindView(R.id.right_score_1)
    TextView right_score_1;
    @BindView(R.id.right_score_2)
    TextView right_score_2;
    @BindView(R.id.right_score_3)
    TextView right_score_3;
    @BindView(R.id.right_score_4)
    TextView right_score_4;


    CascadeClassifier cascadeClassifier;

    Mat grayscaleImage;
    private int absoluteFaceSize;
    private String PATH = "/sdcard/face2/";
    String fileName;
    private CameraPresenter presenter;
    private boolean isPhoto = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new CameraPresenter(this);

      //  mCameraView.setCameraIndex(CameraBridgeViewBase.CAMERA_ID_BACK);//后置摄像头
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

                        File folder = new File(PATH);
                        if (!folder.exists()) {
                            folder.mkdirs();
                        }
                        if(!isPhoto){
                            isPhoto =true;
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
    protected void onResume() {
        super.onResume();
        isPhoto = false;
        presenter.getQuestionRank();
    }

    @Override
    protected void onPause() {
        super.onPause();
        isPhoto = true;
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
        List<SimilarFaceResult.ResultBean> resultBeans = bean.getResult();
        if (resultBeans.size() > 0) {
            presenter.upLoadPicGetUseIdFile(fileName);
            SimilarFaceResult.ResultBean bean1 = resultBeans.get(0);
            String score = (bean1.getScore() * 100 + 30 + "").substring(0, 2);
            Log.i("ccc", "  getScore " + score + " bean1.getScore()" + bean1.getScore());
            Bundle bundle = new Bundle();
            Intent intent = new Intent(this, DetailActivity.class);
            bundle.putString("name", bean1.getName());
            bundle.putString("duty", bean1.getDuty());
            bundle.putString("description", bean1.getDescription());
            bundle.putString("score", score);
            String image = bean1.getDraw_image();
            //  Log.i("ccc", "  getDraw_image " + image);
            if (TextUtils.isEmpty(image)) {
                bundle.putString("img", bean1.getImage());
            } else {
                bundle.putString("img", image);
            }
            intent.putExtras(bundle);
            startActivity(intent);
        } else {
            Log.i("ccc", "服务器没有返回信息》》》》》》 ");
            isPhoto = false;
            deletePic();
        }
    }

    @Override
    public void getUniqidDataSuccess(UniqidResult value) {
        String id = value.getData().getUniqid();
        if(!TextUtils.isEmpty(id)){
            SharedPreferencesUtil.putString(this,"uniqid",value.getData().getUniqid());
        }else{
            SharedPreferencesUtil.putString(this,"uniqid","");
        }
        deletePic();
    }

    public void getDataFail() {
        isPhoto = false;
        deletePic();
    }

    @Override
    public void getUniqidDataFail() {
        SharedPreferencesUtil.putString(this,"uniqid","");
        deletePic();
    }

    @Override
    public void getQuestionRankDataSuccess(QuestionRankResult value) {
        QuestionRankResult.DataBean dataBean = value.getData();
        List<QuestionRankResult.DataBean.LeftBean> leftBeans =dataBean.getLeft();
        if(leftBeans.size()>0){
            QuestionRankResult.DataBean.LeftBean leftBean;
            for(int i=0;i<leftBeans.size();i++){
                leftBean = leftBeans.get(i);
                if(i==0){
                    //  ImageUtils.imageRound2(this,leftBean.getImage(),iv_left_pic_1);
                    ImageUtils.imageCorners(this,leftBean.getImage(),iv_left_pic_1);
                    left_score_1.setText("总得分："+leftBean.getScore()+"分");
                }else if(i==1){
                    ImageUtils.imageCorners(this,leftBean.getImage(),iv_left_pic_2);
                    left_score_2.setText("总得分："+leftBean.getScore()+"分");
                }else if (i==2){
                    ImageUtils.imageCorners(this,leftBean.getImage(),iv_left_pic_3);
                    left_score_3.setText("总得分："+leftBean.getScore()+"分");
                }else if(i==3){
                    ImageUtils.imageCorners(this,leftBean.getImage(),iv_left_pic_4);
                    left_score_4.setText("总得分："+leftBean.getScore()+"分");
                }
            }
        }
        List<QuestionRankResult.DataBean.RightBean> rightBeans =dataBean.getRight();
        if(rightBeans.size()>0){
            QuestionRankResult.DataBean.RightBean rightBean;
            for(int i=0;i<rightBeans.size();i++){
                rightBean = rightBeans.get(i);
                if(i==0){
                    ImageUtils.imageCorners(this,rightBean.getImage(),iv_right_pic_1);
                    right_score_1.setText("总得分："+rightBean.getScore()+"分");
                }else if(i==1){
                    ImageUtils.imageCorners(this,rightBean.getImage(),iv_right_pic_2);
                    right_score_2.setText("总得分："+rightBean.getScore()+"分");
                }else if (i==2){
                    ImageUtils.imageCorners(this,rightBean.getImage(),iv_right_pic_3);
                    right_score_3.setText("总得分："+rightBean.getScore()+"分");
                }else if(i==3){
                    ImageUtils.imageCorners(this,rightBean.getImage(),iv_right_pic_4);
                    right_score_4.setText("总得分："+rightBean.getScore()+"分");
                }
            }
        }
    }

    private void deletePic() {
        File file = new File(fileName);
        if (file.exists()) {
            file.delete();
            Log.i(TAG, "deletePic+++");
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mCameraView) {
            mCameraView.disableView();
        }
        presenter.dispose();
    }

}
