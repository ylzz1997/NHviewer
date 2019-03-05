package com.lhy.nhviwer;

import android.app.Activity;
import android.view.View;
import android.widget.Toast;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CatchNHBookPictureList {

    String url = "https://nhentai.net/";
    String urll = "https://nhentai.net";
    Document doc;
    Activity context;

    public CatchNHBookPictureList(Activity context) {
        this.context = context;
    }

    public CatchNHBookPictureList() {
    }

    List<PictureContent> catchBookByPage(){
        Connection conn = Jsoup.connect(url);
        conn.header("User-Agent", "Mozilla/5.0 (X11; Linux x86_64; rv:32.0) Gecko/20100101 Firefox/32.0");
        List<PictureContent> pictureContentList = new ArrayList<PictureContent>();
        try {
            doc = conn.get();
            Elements gallery = doc.select(".gallery");
            Iterator it = gallery.iterator();
            while (it.hasNext()){
                Element element = (Element)it.next();
                PictureContent pc = new PictureContent();
                pc.setURL(urll+element.selectFirst("a").attr("href"));
                pc.setName(element.selectFirst(".caption").text());
                pc.setImageURL(element.selectFirst(".lazyload").attr("data-src"));
                Exception pe = pc.urlToBitmap();
                if(pe!=null){
                    Toast.makeText(context,"图片加载失败,错误提示:"+pe.getMessage(),Toast.LENGTH_SHORT).show();
                }
                pictureContentList.add(pc);
                System.out.println("完成：");
                System.out.println(pc.getName());
            }
        } catch (Exception e) {
            context.findViewById(R.id.Main_progressBar).setVisibility(View.INVISIBLE);
            e.printStackTrace();
            return null;
        }
        return pictureContentList;
    }
}
