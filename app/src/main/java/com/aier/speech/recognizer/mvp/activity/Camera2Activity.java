package com.aier.speech.recognizer.mvp.activity;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.aier.speech.recognizer.R;
import com.aier.speech.recognizer.bean.QuestionRankResult;
import com.aier.speech.recognizer.bean.SimilarFaceResult;
import com.aier.speech.recognizer.bean.UniqidResult;
import com.aier.speech.recognizer.mvp.contract.CameraContract;
import com.aier.speech.recognizer.mvp.presenter.CameraPresenter;
import com.aier.speech.recognizer.util.FileUtil;
import com.aier.speech.recognizer.util.ImageUtils;
import com.aier.speech.recognizer.util.SharedPreferencesUtil;
import com.aier.speech.recognizer.util.ToastyUtil;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

import static org.opencv.imgcodecs.Imgcodecs.imread;


public class Camera2Activity extends BaseActivity implements SurfaceHolder.Callback, CameraContract.View {

    @BindView(R.id.camera_sf)
    SurfaceView camera_sf;

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

    private Camera camera;
    private String filePath;
    private SurfaceHolder holder;
    private boolean isFrontCamera = true;
    private int width = 640;
    private int height = 480;
    private boolean isPhoto = false;

    CascadeClassifier cascadeClassifier;
    Mat grayscaleImage;
    private int absoluteFaceSize;
    private CameraPresenter presenter;
  //  private Disposable mDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new CameraPresenter(this);
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        camera_sf = findViewById(R.id.camera_sf);
        holder = camera_sf.getHolder();
        holder.addCallback(this);
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        grayscaleImage = new Mat(height, width, CvType.CV_8UC4);
        absoluteFaceSize = (int) (height * 0.2);
        initClassifier();
//        heartinterval();
    }

    /**
     * 发送心跳数据
     */
//    private void heartinterval() {
//        mDisposable = Flowable.interval(0, 4, TimeUnit.SECONDS)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(aLong -> {
//                    takePhoto();
//             //       Log.i("sss", ">>>>>>>>>>>>>>>>>>>>>心跳");
//                });
//    }


    @Override
    protected int getLayout() {
        return R.layout.activity_camera2;
    }

    private void initClassifier() {
        InputStream is = getResources().openRawResource(R.raw.lbpcascade_frontalface);
        File cascadeDir = getDir("cascade", MODE_PRIVATE);
        File mCascadeFile = new File(cascadeDir, "lbpcascade_frontalface.xml");
        FileOutputStream os;
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
    }

    @OnClick({R.id.take_photo, R.id.iv_answer_question, R.id.iv_right_btn, R.id.iv_left_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.take_photo:
                takePhoto();
                break;
            case R.id.iv_answer_question:
                startActiviys(AnswerQuestionActivity.class);
                break;
            case R.id.iv_right_btn://菜单
                startActiviys(MenuActivity.class);
                break;
            case R.id.iv_left_btn://初心地图
                startActiviys(MapActivity.class);
                break;
        }
    }

    private void takePhoto() {
        if (!isPhoto) {
            isPhoto = true;
            camera.takePicture(null, null, jpeg);
        }
    }

    private Camera.PictureCallback jpeg = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            filePath = FileUtil.getPath() + File.separator + FileUtil.getTime() + ".jpeg";
            File file = new File(filePath);
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Matrix matrix = new Matrix();
//            matrix.reset();
//            matrix.postRotate(90);//南康那边要注释掉不需要旋转图片
            BitmapFactory.Options factory = new BitmapFactory.Options();
            factory = setOptions(factory);

            Bitmap bm = BitmapFactory.decodeByteArray(data, 0, data.length, factory);
            Bitmap bm1 = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
            BufferedOutputStream bos = null;
            try {
                bos = new BufferedOutputStream(new FileOutputStream(file));
                bm1.compress(Bitmap.CompressFormat.JPEG, 50, bos);
                bos.flush();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (bos != null) {
                    try {
                        bos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                bm.recycle();
                bm1.recycle();
                stopPreview();
                showPic();
            }
        }
    };

    /**
     * 上传信息
     */
    private void showPic() {
        startCameraPreview();

        Mat src = imread(filePath);
        Imgproc.cvtColor(src, grayscaleImage, Imgproc.COLOR_RGBA2RGB);
        MatOfRect faces = new MatOfRect();
        if (cascadeClassifier != null) {
            cascadeClassifier.detectMultiScale(grayscaleImage, faces, 1.1, 2, 2,
                    new Size(absoluteFaceSize, absoluteFaceSize), new Size());
        }
        Rect[] facesArray = faces.toArray();
        int faceCount = facesArray.length;
        if (faceCount > 0) {
            Log.i("ccc", "有人脸的照片");
            ToastyUtil.INSTANCE.showSuccess("正在穿越红军时代");
            presenter.upLoadPicFile(filePath);
        } else {
            deletePic();
            isPhoto = false;
            ToastyUtil.INSTANCE.showError("请重新拍照！");
            Log.i("ccc", "没有人脸的照片");
        }
    }

    public static BitmapFactory.Options setOptions(BitmapFactory.Options opts) {
        opts.inJustDecodeBounds = false;
        opts.inPurgeable = true;
        opts.inInputShareable = true;
        opts.inPreferredConfig = Bitmap.Config.RGB_565;
        opts.inSampleSize = 1;
        return opts;
    }

    @Override
    protected void onResume() {
        super.onResume();
        camera = openCamera();
        isPhoto = false;
        presenter.getQuestionRank();
    }

    @Override
    protected void onPause() {
        super.onPause();
        isPhoto = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeCamera();
        presenter.dispose();
//        if (mDisposable != null) {
//            mDisposable.dispose();
//        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            camera.setPreviewDisplay(holder);
        } catch (IOException exception) {

        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        startPreview();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        stopPreview();
    }

    private Camera openCamera() {
        if (camera == null) {
            try {
                camera = Camera.open();
            } catch (Exception e) {
                camera = null;
                e.printStackTrace();
            }
        }
        return camera;
    }

    private void startPreview() {
        Camera.Parameters para;
        if (null != camera) {
            para = camera.getParameters();
        } else {
            return;
        }

//        List<Camera.Size> supportedPictureSizes = para.getSupportedPictureSizes();
//        for(int i=0;i<supportedPictureSizes.size();i++){
//            Log.i("sss","widget " +supportedPictureSizes.get(i).width +"  height " +supportedPictureSizes.get(i).height );
//        }
//        Log.i("sss","widget ///////");
//        List<Camera.Size> supportedPreviewSizes = para.getSupportedPreviewSizes();
//        for(int i=0;i<supportedPreviewSizes.size();i++){
//            Log.i("sss","widget " +supportedPreviewSizes.get(i).width +"  height " +supportedPreviewSizes.get(i).height);
//        }

        para.setPreviewSize(width, height);
        setPictureSize(para, width, height);
        para.setPictureFormat(ImageFormat.JPEG);//设置图片格式
        setCameraDisplayOrientation(isFrontCamera ? 0 : 1, camera);
        camera.setParameters(para);
        camera.startPreview();
    }

    /* 停止预览 */
    private void stopPreview() {
        if (camera != null) {
            try {
                camera.stopPreview();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /* 开始预览 */
    private void startCameraPreview() {
        if (camera != null) {
            try {
                camera.startPreview();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setCameraDisplayOrientation(int cameraId, Camera camera) {
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(cameraId, info);
        int rotation = this.getWindowManager().getDefaultDisplay().getRotation();
        rotation = 0;
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }
        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;
        } else {
            result = (info.orientation - degrees + 360) % 360;
        }
        camera.setDisplayOrientation(result);
    }

    private void setPictureSize(Camera.Parameters para, int width, int height) {
        int absWidth = 0;
        int absHeight = 0;
        List<Camera.Size> supportedPictureSizes = para.getSupportedPictureSizes();
        for (Camera.Size size : supportedPictureSizes) {
            if (Math.abs(width - size.width) < Math.abs(width - absWidth)) {
                absWidth = size.width;
            }
            if (Math.abs(height - size.height) < Math.abs(height - absHeight)) {
                absHeight = size.height;
            }
        }
        para.setPictureSize(absWidth, absHeight);
    }

    private void closeCamera() {
        if (null != camera) {
            try {
                camera.setPreviewDisplay(null);
                camera.setPreviewCallback(null);
                camera.release();
                camera = null;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void getDataSuccess(SimilarFaceResult bean) {
        List<SimilarFaceResult.ResultBean> resultBeans = bean.getResult();
        if (resultBeans.size() > 0) {
            presenter.upLoadPicGetUseIdFile(filePath);
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

    @Override
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
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
    }
}
