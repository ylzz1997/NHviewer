package com.lhy.nhviwer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class PictureContent {
    /**
     * 名字
     */
    private String name;
    /**
     * 图片id
     */
    private int imageId;
    private Bitmap imageBitmap;
    private String imageURL;
    private String URL;

    public PictureContent() {
    }

    public PictureContent(String name, int imageId) {
        this.name = name;
        this.imageId = imageId;
    }

    public PictureContent(String name, int imageId, String URL) {
        this.name = name;
        this.imageId = imageId;
        this.URL = URL;
    }

    public String getName() {
        return name;
    }

    public int getImageId() {
        return imageId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public Bitmap getImageBitmap() {
        return imageBitmap;
    }

    public void setImageBitmap(Bitmap imageBitmap) {
        this.imageBitmap = imageBitmap;
    }

    public Exception urlToBitmap(){
        try {
            java.net.URL imageurl = new URL(imageURL);
            HttpURLConnection conn = (HttpURLConnection)imageurl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            imageBitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
            return e;
        }
        return null;
    }
}
