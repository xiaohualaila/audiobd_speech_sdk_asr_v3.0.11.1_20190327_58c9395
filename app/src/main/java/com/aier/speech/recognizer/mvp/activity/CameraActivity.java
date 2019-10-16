package com.aier.speech.recognizer.mvp.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aier.speech.recognizer.R;
import com.aier.speech.recognizer.bean.QuestionRankResult;
import com.aier.speech.recognizer.bean.SimilarFaceResult;
import com.aier.speech.recognizer.bean.UniqidResult;
import com.aier.speech.recognizer.bean.YUBAIBean;
import com.aier.speech.recognizer.model.MessageWrap;
import com.aier.speech.recognizer.mvp.contract.CameraContract;
import com.aier.speech.recognizer.mvp.presenter.CameraPresenter;
import com.aier.speech.recognizer.util.ImageUtils;
import com.aier.speech.recognizer.util.ReplaceHtml;
import com.aier.speech.recognizer.util.SharedPreferencesUtil;
import com.aier.speech.recognizer.util.ToastyUtil;
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
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class CameraActivity extends RobotSpeechActivity implements CameraContract.View{
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
    @BindView(R.id.tip)
    TextView tip;

    CascadeClassifier cascadeClassifier;

    Mat grayscaleImage;
    private int absoluteFaceSize;
    private String PATH = "/sdcard/face2/";
    String fileName;
    private CameraPresenter presenter;
    private boolean isPhoto = true;
    private Disposable mDisposable;
    private MediaPlayer mediaPlayer;

    private AudioManager am;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        am = (AudioManager) getSystemService(AUDIO_SERVICE);
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
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
                    if (!isPhoto) {
                        isPhoto = true;
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
       // heartinterval();
    }


    /**
     * 发送心跳数据
     */
    private void heartinterval() {
        mDisposable = Flowable.interval(0, 5, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    presenter.getMsgData();
                    Log.i("sss",">>>>>>>>>>>>>>>>>>>>>心跳");
                });
    }



    @OnClick({R.id.iv_answer_question, R.id.iv_right_btn, R.id.iv_left_btn, R.id.jj_icon,R.id.take_photo})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_answer_question:
                startActiviys(AnswerQuestionActivity.class);
                break;
            case R.id.iv_right_btn://菜单
                startActiviys(MenuActivity.class);
                break;
            case R.id.iv_left_btn://初心地图
                startActiviys(MapActivity.class);
                break;
            case R.id.jj_icon://简介
                startActiviys(IntroductionActivity.class);
                break;
            case R.id.take_photo://允许拍照
                isPhoto = false;
                ToastyUtil.INSTANCE.showSuccess("准备重回红军时代");
                break;

        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        isPhoto = true;
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
           Bitmap bitmap1 = edgeBitmap(bitmap);
            //int quality 图像压缩率，0-100。 0 压缩100%，100意味着不压缩；
            bitmap1.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);
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

    /**
     * Bitmap 剪切成正方形
     *
     * @param bitmap
     * @return
     */
    public Bitmap edgeBitmap(Bitmap bitmap) {
        int size = bitmap.getWidth() < bitmap.getHeight() ? bitmap.getWidth() : bitmap.getHeight();
        //剪切成正方形
        Bitmap bitmap2 = Bitmap.createBitmap(bitmap, 0, 0, size, size);
        return bitmap2;
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
            speak("您回到红军时代是" + bean1.getName() + "相似度" + score + "%" + bean1.getDuty());
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
        if (!TextUtils.isEmpty(id)) {
            SharedPreferencesUtil.putString(this, "uniqid", value.getData().getUniqid());
        } else {
            SharedPreferencesUtil.putString(this, "uniqid", "");
        }
        deletePic();
    }

    public void getDataFail() {
        isPhoto = false;
        deletePic();
    }

    @Override
    public void getUniqidDataFail() {
        SharedPreferencesUtil.putString(this, "uniqid", "");
        deletePic();
    }

    @Override
    public void getQuestionRankDataSuccess(QuestionRankResult value) {
        QuestionRankResult.DataBean dataBean = value.getData();
        List<QuestionRankResult.DataBean.LeftBean> leftBeans = dataBean.getLeft();
        if (leftBeans.size() > 0) {
            QuestionRankResult.DataBean.LeftBean leftBean;
            for (int i = 0; i < leftBeans.size(); i++) {
                leftBean = leftBeans.get(i);
                String img = leftBean.getImage();
                if (i == 0) {
                    if(!TextUtils.isEmpty(img)){
                        ImageUtils.imageCorners(this, leftBean.getImage(), iv_left_pic_1);
                        left_score_1.setText("总得分：" + leftBean.getScore() + "分");
                        iv_left_pic_1.setVisibility(View.VISIBLE);
                        left_score_1.setVisibility(View.VISIBLE);
                    }else{
                        iv_left_pic_1.setVisibility(View.GONE);
                        left_score_1.setVisibility(View.GONE);
                    }
                } else if (i == 1) {
                    if(!TextUtils.isEmpty(img)){
                        ImageUtils.imageCorners(this, leftBean.getImage(), iv_left_pic_2);
                        left_score_2.setText("总得分：" + leftBean.getScore() + "分");
                        iv_left_pic_2.setVisibility(View.VISIBLE);
                        left_score_2.setVisibility(View.VISIBLE);
                    }else{
                        iv_left_pic_2.setVisibility(View.GONE);
                         left_score_2.setVisibility(View.GONE);
                    }
                } else if (i == 2) {
                    if(!TextUtils.isEmpty(img)){
                        ImageUtils.imageCorners(this, leftBean.getImage(), iv_left_pic_3);
                        left_score_3.setText("总得分：" + leftBean.getScore() + "分");
                        iv_left_pic_3.setVisibility(View.VISIBLE);
                        left_score_3.setVisibility(View.VISIBLE);
                    }else{
                        iv_left_pic_3.setVisibility(View.GONE);
                        left_score_3.setVisibility(View.GONE);
                    }
                } else if (i == 3) {
                    if(!TextUtils.isEmpty(img)){
                        ImageUtils.imageCorners(this, leftBean.getImage(), iv_left_pic_4);
                        left_score_4.setText("总得分：" + leftBean.getScore() + "分");
                        iv_left_pic_4.setVisibility(View.VISIBLE);
                        left_score_4.setVisibility(View.VISIBLE);
                    }else{
                        iv_left_pic_4.setVisibility(View.GONE);
                        left_score_4.setVisibility(View.GONE);
                    }
                }
            }
        }
        List<QuestionRankResult.DataBean.RightBean> rightBeans = dataBean.getRight();
        if (rightBeans.size() > 0) {
            QuestionRankResult.DataBean.RightBean rightBean;
            for (int i = 0; i < rightBeans.size(); i++) {
                rightBean = rightBeans.get(i);
                String img = rightBean.getImage();
                if (i == 0) {
                    if(!TextUtils.isEmpty(img)){
                        ImageUtils.imageCorners(this, rightBean.getImage(), iv_right_pic_1);
                        right_score_1.setText("总得分：" + rightBean.getScore() + "分");
                        iv_right_pic_1.setVisibility(View.VISIBLE);
                        right_score_1.setVisibility(View.VISIBLE);
                    }else{
                        iv_right_pic_1.setVisibility(View.GONE);
                        right_score_1.setVisibility(View.GONE);
                    }
                } else if (i == 1) {
                    if(!TextUtils.isEmpty(img)){
                        ImageUtils.imageCorners(this, rightBean.getImage(), iv_right_pic_2);
                        right_score_2.setText("总得分：" + rightBean.getScore() + "分");
                        iv_right_pic_2.setVisibility(View.VISIBLE);
                        right_score_2.setVisibility(View.VISIBLE);
                    }else{
                        iv_right_pic_2.setVisibility(View.GONE);
                        right_score_2.setVisibility(View.GONE);
                    }

                } else if (i == 2) {
                    if(!TextUtils.isEmpty(img)){
                        ImageUtils.imageCorners(this, rightBean.getImage(), iv_right_pic_3);
                        right_score_3.setText("总得分：" + rightBean.getScore() + "分");
                        iv_right_pic_3.setVisibility(View.VISIBLE);
                        right_score_3.setVisibility(View.VISIBLE);
                    }else{
                        iv_right_pic_3.setVisibility(View.GONE);
                        right_score_3.setVisibility(View.GONE);
                    }

                } else if (i == 3) {
                    if(!TextUtils.isEmpty(img)){
                        ImageUtils.imageCorners(this, rightBean.getImage(), iv_right_pic_4);
                        right_score_4.setText("总得分：" + rightBean.getScore() + "分");
                        iv_right_pic_4.setVisibility(View.VISIBLE);
                        right_score_4.setVisibility(View.VISIBLE);
                    }else{
                        iv_right_pic_4.setVisibility(View.GONE);
                        right_score_4.setVisibility(View.GONE);
                    }
                }
            }
        }
    }

    @Override
    public void getQuestionRankDataFail(String msg) {
        ToastyUtil.INSTANCE.showInfo(msg);
    }

    @Override
    public void getYubaiDataSuccess(YUBAIBean bean) {
        Log.i(TAG, "羽白结果---->  " + bean.toString());
        String result = bean.getResult();
        String label = bean.getLabel();
        //  Log.i("xxxx", "label---->  " + label);
        //判断是否是新闻如果是新闻资讯显示Webview
        String text;
        if (label.equals("新闻资讯")) {

            try {
                String resultNotHtml = ReplaceHtml.delHtmlTag(result);
                int indexLast = resultNotHtml.indexOf("-----------");
                text = resultNotHtml.substring(0, indexLast);
            } catch (Exception e) {
                text = result;
            }
            //显示滚动文字
            tip.setText(text);
            tip.setSelected(true);
            speak(text);
            Log.i("ZZZ", "返回新闻结果----> result  " + result);
            return;
        }else if(label.equals("笑话娱乐")){
            speak(result);
            //显示滚动文字
            tip.setText(result);
            tip.setSelected(true);
            return;
        }else if(label.equals("音乐欣赏")){
            tip.setText(result);
            tip.setSelected(true);
            //判断是否有声音
            String voice = bean.getVoice();
            if (TextUtils.isEmpty(voice)) {
                speak(result);
                Log.i("ZZZ", "speak+++++++++++++++++++++++++++");
            } else {
                playVoice(voice);
            }
            return;
        }else if(label.equals("儿童故事")||label.equals("红色知识")){
            speak(result);
            //显示滚动文字
            tip.setText(result);
            tip.setSelected(true);
            return;
        }else{
            try {
                int index = result.indexOf("你还可以问我");
                text = result.substring(0, index);
            } catch (Exception e) {
                text = result;
            }
            //显示滚动文字
            tip.setText(text);
            tip.setSelected(true);
        }

        //判断是否有声音
        String voice = bean.getVoice();
        if (TextUtils.isEmpty(voice)) {
            speak(text);
            Log.i("ZZZ", "speak+++++++++++++++++++++++++++");
        } else {
            playVoice(voice);
        }
    }

    private void deletePic() {
        if (fileName != null) {
            File file = new File(fileName);
            if (file.exists()) {
                file.delete();
                Log.i(TAG, "deletePic+++");
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mCameraView) {
            mCameraView.disableView();
        }
        presenter.dispose();
        if (mDisposable != null) {
            mDisposable.dispose();
        }
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public void wakup() {
        Log.i(TAG, "唤醒");
        stopTTS();
        speak("在");
        startSpeech();
        stopMediaPlay();
    }

    protected void speechStart() {
        Log.i(TAG, "msg ---->   speechStart  ");
        tip.setText("正在识别");
    }

    protected void speechBackMsg(String msg) {
          Log.i(TAG, "msg ---->     " + msg);
        if (msg.contains("增大音量") || msg.contains("增加声音") || msg.contains("声音变大") || msg.contains("增加音量") || msg.contains("调高音量") || msg.equals("提高音量")) {
            am.adjustStreamVolume(AudioManager.STREAM_SYSTEM, AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);//增大
            return;
        } else if (msg.contains("减小音量") || msg.contains("减小声音") || msg.contains("声音变小") || msg.contains("调低音量")) {
            am.adjustStreamVolume(AudioManager.STREAM_SYSTEM, AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);//增小
            return;
        }
        tip.setText(msg);
        presenter.loadData(msg);
        Log.i(TAG, "msg ---->   speechBackMsg  " + msg);
    }

    protected void speechTemporary(String msg) {
        tip.setText(msg);
    }

    /**
     * 语音识别结束
     */
    protected void speechFinish() {
        //    Log.i(TAG, "msg ---->   speechFinish  ");
        tip.setText("");
    }

    /**
     * 语音合成播放完成
     */
    protected void ttsFinish() {
      tip.setText("");
    }

    //停止播放音乐
    private void stopMediaPlay() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.reset();
        }
    }
    //播放语音
    private void playVoice(String voice) {
        try {
            mediaPlayer.setDataSource(voice);
            // 通过异步的方式装载媒体资源
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(mp -> {
                // 装载完毕 开始播放流媒体
                mediaPlayer.start();
            });
            mediaPlayer.setOnCompletionListener(mp -> {
                 tip.setText("");
            });
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
