package com.example.imagecaching;

import android.graphics.Bitmap;

public interface DownloadStatus {
    void downLoadCompleted(int position,String rsid,Bitmap bm);
    void downLoadFailed(String reason);
}
