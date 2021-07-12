package com.example.imagecaching;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.util.LinkedList;
import java.util.Queue;

public class ImageCaching {

    private LruCache<String,Bitmap> memcache;
    private Context mcontext;
    private String TAG="Imagecaching";
    private Queue<DownLoadImage> queue=new LinkedList<>();
    public ImageCaching(Context context,LruCache<String, Bitmap> memcache){
        mcontext=context;
        this.memcache=memcache;
    }


    public Bitmap getImage(int pos,ImageObject imgObj) {
        Bitmap img=memcache.get(imgObj.getResId());
        if(img!=null)
        {
            Log.d(TAG,"image returned from memory:imageobject"+imgObj);
            return img;
        }
        Log.d(TAG,"Download Image for imageObject"+imgObj);
        downloadimage(pos,imgObj);
        return null;
    }

    private DownloadStatus downloadStatus=new DownloadStatus() {
        @Override
        public void downLoadCompleted(int position,String rsId,Bitmap bm) {
            //Log.d(TAG,"Download Completed for imageObject");
            memcache.put(rsId,bm);
            Intent in=new Intent("image_download_complete");
            in.putExtra("Position",position);
            LocalBroadcastManager.getInstance(mcontext).sendBroadcast(in);
            if(!queue.isEmpty()){
                queue.remove();
                if (!queue.isEmpty()){
                    executePendingAction(queue.peek());
                }

            }
        }

        @Override
        public void downLoadFailed(String reason) {
            Log.d(TAG,"Download Failed");
        }
    };

    private void downloadimage(int pos,ImageObject imgob) {
        if(queue.isEmpty()){
            executePendingAction(new DownLoadImage(mcontext,pos,imgob,downloadStatus));
        }
        queue.add(new DownLoadImage(mcontext,pos,imgob,downloadStatus));
    }

    private void executePendingAction(DownLoadImage downLoadImage){
        downLoadImage.execute();
    }

    public void cancelDownload()
    {
        if(!queue.isEmpty()) {
            queue.peek().cancel(true);
            queue.clear();
        }
    }
}
