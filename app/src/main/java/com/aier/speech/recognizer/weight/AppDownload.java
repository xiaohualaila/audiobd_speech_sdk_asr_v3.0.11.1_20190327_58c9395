package com.aier.speech.recognizer.weight;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class AppDownload {
    DownloadManager downloadManager;
    Context context;
    private Callback callback;

    public void setProgressInterface(Callback callback){
        this.callback = callback;
    }

    public void downApk(String url, Context context) {
        this.context = context;
        //        seek.setVisibility(View.VISIBLE);
        //创建下载任务,downloadUrl就是下载链接
        DownloadManager.Request request = new DownloadManager.Request( Uri.parse( url ) );
        //指定下载路径和下载文件名

        request.setDestinationInExternalPublicDir( "/yubai/",  "羽白.apk" );
       //获取下载管理器
        downloadManager = (DownloadManager) context.getSystemService( Context.DOWNLOAD_SERVICE );
       //将下载任务加入下载队列，否则不会进行下载
        updateViews( downloadManager.enqueue( request ) );
    }


    private void updateViews(final Long downlaodId) {
        final Timer myTimer = new Timer();
        myTimer.schedule( new TimerTask() {
            @SuppressLint("NewApi")
            @Override
            public void run() {
                DownloadManager.Query query = new DownloadManager.Query().setFilterById( downlaodId );
                Cursor cursor = ((DownloadManager) context.getSystemService( Context.DOWNLOAD_SERVICE )).query( query );
                cursor.moveToFirst();
                float bytes_downloaded = cursor.getInt( cursor.getColumnIndex( DownloadManager
                        .COLUMN_BYTES_DOWNLOADED_SO_FAR ) );
                float bytes_total = cursor.getInt( cursor.getColumnIndex( DownloadManager.COLUMN_TOTAL_SIZE_BYTES ) );
                cursor.close();
                final int dl_progress = (int) (bytes_downloaded * 100 / bytes_total);
                Log.i( "czx", "progress:" + dl_progress );
                if (dl_progress >= 100) {
                    myTimer.cancel();
                    callback.callProgress(dl_progress);
                }else {
                    callback.callProgress(dl_progress);
                }
            }
        }, 0, 800 );
    }

    public interface Callback{
        void callProgress(int progress);
    }

}
