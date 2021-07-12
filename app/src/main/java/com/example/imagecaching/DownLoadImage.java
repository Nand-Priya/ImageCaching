package com.example.imagecaching;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

public class DownLoadImage extends AsyncTask<Void,Void, Bitmap> {
    String TAG = "priya";
    private Context context;
    private DownloadStatus dnStatus;
    ImageObject imageObject;
    private int position;
    public DownLoadImage(Context context,int pos,ImageObject imageObject ,DownloadStatus ds)
    {
        this.context=context;
        this.imageObject=imageObject;
        dnStatus=ds;
        position=pos;
    }


    protected void onProgressUpdate(Void v) {
        Log.d(TAG,"Progress ");

    }

    @Override
    protected Bitmap doInBackground(Void... voids) {
        Log.d(TAG,"download start ");
        Bitmap bm=getBitmapFromURL(imageObject.getUrl());
        return bm;
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        Log.d(TAG,"download cancelled");
        dnStatus=null;
    }

    protected void onPostExecute(Bitmap v) {
        Log.d(TAG,"Downloaded "  + " bytes ...");
        if(v!=null) {
            dnStatus.downLoadCompleted(position,imageObject.getResId(),v);
            //Toast.makeText(context, "Download complete " + imageObject.getUrl(), Toast.LENGTH_SHORT).show();
        }
    }

    public Bitmap getBitmapFromURL(String src) {
        try {
            java.net.URL url = new java.net.URL(src);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            Log.d(TAG,"download end now ");
            return myBitmap;
        } catch (IOException e) {
            if(isCancelled()) {
                dnStatus=null;
            }
            dnStatus.downLoadFailed(e.toString());
            e.printStackTrace();
            return null;
        }
    }




}
