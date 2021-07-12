package com.example.imagecaching;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.util.LruCache;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    ArrayList<ImageObject> resourceId;
    RecyclerView recyclerView;
    private LruCache<String,Bitmap> memcache;
    private ImageCaching imageCaching;
    private String TAG="priya";
    private String imageurl ="https://www.rd.com/wp-content/uploads/2021/03/GettyImages-1133605325-scaled-e1617227898456.jpg";
    private String image1url ="https://wallpaperaccess.com/full/156340.jpg";
    private String image2Url="https://images.unsplash.com/photo-1523302348819-ffd5c0521796?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=750&q=80";
    private String image3url ="https://www.gstatic.com/webp/gallery3/1.png";
    private String image4url ="https://www.gstatic.com/webp/gallery3/2.png";
    private String image5Url="https://www.gstatic.com/webp/gallery3/3.png"  ;
    private String image6url ="https://www.gstatic.com/webp/gallery3/4.png";
    private String image7url ="https://wallpaperaccess.com/full/156340.jpg";
    private String image8Url="https://www.gstatic.com/webp/gallery/2.jpg";
    private String image9url ="https://www.gstatic.com/webp/gallery/1.jpg";
    private String image10url ="https://www.gstatic.com/webp/gallery/3.jpg";
    private String image11Url="https://www.gstatic.com/webp/gallery/5.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int max=(int)(Runtime.getRuntime().maxMemory()/(1024*8));
        memcache=new LruCache<String, Bitmap>(max){
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getByteCount() / 1024;
            }
        };
        resourceId=new ArrayList<>();
        resourceId.add(new ImageObject("pic1",imageurl));
        resourceId.add(new ImageObject("pic2",image1url));
        resourceId.add(new ImageObject("pic3",image2Url));
        resourceId.add(new ImageObject("pic4",image3url));
        resourceId.add(new ImageObject("pic5",image4url));
        resourceId.add(new ImageObject("pic6",image5Url));
        resourceId.add(new ImageObject("pic7",image6url));
        resourceId.add(new ImageObject("pic8",image7url));
        resourceId.add(new ImageObject("pic9",image8Url));
        resourceId.add(new ImageObject("pic10",image9url));
        resourceId.add(new ImageObject("pic11",image10url));
        resourceId.add(new ImageObject("pic12",image11Url));
        imageCaching=new ImageCaching(getApplicationContext(),memcache);
        recyclerView=findViewById(R.id.rvview);
        CustomAdaptar customAdaptar=new CustomAdaptar(resourceId,imageCaching);
        recyclerView.setAdapter(customAdaptar);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }



    @Override
    protected void onPause() {
        super.onPause();
        imageCaching.cancelDownload();
    }
}