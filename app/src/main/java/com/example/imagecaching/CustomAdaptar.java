package com.example.imagecaching;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdaptar extends RecyclerView.Adapter<CustomAdaptar.ViewHolder> {
    private ArrayList<ImageObject> mImgArrayRes;
    private Context mContext;
    private ImageCaching imageCaching;
    private String TAG="CustomAdaptar";
    private int imgPos=0;
    private ImageView imgV;

    public CustomAdaptar(ArrayList<ImageObject> res,ImageCaching imgcach)
    {
        mImgArrayRes=res;
        imageCaching=imgcach;

    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView img;
        private EditText edtxt;
        private Button btn;
        public ViewHolder(View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.imageView);
            edtxt=itemView.findViewById(R.id.editText);
            btn=itemView.findViewById(R.id.button);
        }

        public ImageView getImgView()
        {
            return img;
        }

        public EditText getEdtxtView()
        {
            return edtxt;
        }

        public Button getBtnView()
        {
            return btn;
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG,"ViewholderCreated");
        mContext= parent.getContext();
        LayoutInflater inflater =LayoutInflater.from(mContext);
        View view=inflater.inflate(R.layout.item_layout,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        LocalBroadcastManager.getInstance(mContext).registerReceiver(imagereceiver,new IntentFilter("image_download_complete"));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.d(TAG,"position of holder: "+position);
        imgPos=position;
        Bitmap bitmap=imageCaching.getImage(position,mImgArrayRes.get(position));
        String textValue=mImgArrayRes.get(position).getTxt();
        ImageView imgview=holder.getImgView();
        imgV=imgview;
        EditText editTextView=holder.getEdtxtView();
        Button btnview=holder.getBtnView();
        if(bitmap!=null) {
            Log.d(TAG,"image set at position: "+position);
            imgview.setImageBitmap(bitmap);
        }
        else {
            imgview.setImageResource(R.drawable.download);
        }
        editTextView.setEnabled(true);
        editTextView.setText(textValue);
        btnview.setEnabled(true);
        if(!textValue.isEmpty()){
            editTextView.setEnabled(false);
            btnview.setEnabled(false);
        }


        btnview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Txt=editTextView.getText().toString();
                if(!Txt.isEmpty()) {
                    mImgArrayRes.get(position).setTxt(Txt);
                    editTextView.setEnabled(false);
                    btnview.setEnabled(false);
                }
                else {
                    Toast.makeText(mContext,"Please Enter Text",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return mImgArrayRes.size();
    }

    BroadcastReceiver imagereceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
                if(intent.getIntExtra("Position",-1)==imgPos)
                {
                    imgV.setImageBitmap(imageCaching.getImage(imgPos,mImgArrayRes.get(imgPos)));
                }
        }
    };

}
