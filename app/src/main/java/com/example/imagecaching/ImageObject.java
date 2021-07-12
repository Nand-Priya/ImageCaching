package com.example.imagecaching;

public class ImageObject {

    private String resId;
    private String url;
    private String txt="";

    public ImageObject(String resId,String url){
        this.resId=resId;
        this.url=url;
    }

    public String getResId(){
        return resId;
    }

    public String getUrl() {
        return url;
    }

    public void setTxt(String text)
    {
        txt=text;
    }

    public String getTxt(){
        return txt;
    }
}
