package com.aier.speech.recognizer.mvp.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.aier.speech.recognizer.R;
import com.aier.speech.recognizer.util.FileUtil;
import com.aier.speech.recognizer.util.ImageUtils;
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

import butterknife.BindView;
import butterknife.OnClick;

import static org.opencv.imgcodecs.Imgcodecs.imread;


public class Camera2Activity extends BaseActivity implements SurfaceHolder.Callback {

     @BindView(R.id.camera_sf)
     SurfaceView camera_sf;



//    private ImageView img;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        camera_sf = findViewById(R.id.camera_sf);

     //   img = findViewById(R.id.img);
        holder = camera_sf.getHolder();
        holder.addCallback(this);
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);


        grayscaleImage = new Mat(height, width, CvType.CV_8UC4);
        absoluteFaceSize = (int) (height * 0.2);
        initClassifier();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_camera5;
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
    }

    @OnClick({R.id.take_photo})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.take_photo:
                if (!isPhoto) {
                    takePhoto();
                }
                break;


        }
    }

    private void takePhoto() {
        isPhoto = true;
        camera.takePicture(null, null, jpeg);
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
//            matrix.postRotate(90);
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
//        ImageUtils.image(Camera2Activity.this,filePath,img);
        Log.i("sss", "++++++++++++++++++++++++");
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

            isPhoto = false;
            Log.i("sss", "有人脸的照片");
            ToastyUtil.INSTANCE.showInfo("人脸");
        } else {
            File file = new File(filePath);
            if (file.exists()) {
                file.delete();
            }
            isPhoto = false;
            ToastyUtil.INSTANCE.showInfo("未识别到人脸");
            Log.i("sss", "没有人脸的照片");
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

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeCamera();
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
        para.setPreviewSize(width, height);
        setPictureSize(para, 640, 480);
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


}
