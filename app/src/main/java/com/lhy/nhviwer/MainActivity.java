package com.lhy.nhviwer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ProgressBar mainProgressBar;
    Toolbar toolbar;
    RecyclerView picturList;
    PictureListAdapter pla;
    private SwipeRefreshLayout swiperereshlayout ;
    List<PictureContent> pictureContentList= new ArrayList<PictureContent>();

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            pla.notifyDataSetChanged();
            mainProgressBar.setVisibility(View.INVISIBLE);
            if(msg.what==0){
                if(swiperereshlayout.isRefreshing()){
                    swiperereshlayout.setRefreshing(false);
                }
                System.out.println(pictureContentList.size());
                System.out.println("操作完成");
            }else if(msg.what==1){
                Toast.makeText(MainActivity.this,"网络连接错误",Toast.LENGTH_SHORT).show();
            }

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        mainProgressBar = findViewById(R.id.Main_progressBar);
        setSupportActionBar(toolbar);
        swiperereshlayout = findViewById(R.id.Main_swipeRefreshLayout);
        picturList = findViewById(R.id.picture_list);
        picturList.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        pla = new PictureListAdapter(pictureContentList);
        picturList.setAdapter(pla);

        picturList.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                pla.setLoadState(pla.LOADING);
                Toast.makeText(MainActivity.this,"到底了",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {
                pla.setLoadState(pla.LOADING_COMPLETE);
            }
        });

        pla.setOnItemClickListener((v,i)->{
            Toast.makeText(this,"点击了!",Toast.LENGTH_SHORT).show();
        });

        new Thread(()->{
            List<PictureContent> presentPictureContentList = new CatchNHBookPictureList(MainActivity.this).catchBookByPage();
            if(presentPictureContentList==null){
                handler.sendEmptyMessage(1);
            }else {
                for(PictureContent picture : presentPictureContentList){
                    System.out.println("添加完成");
                    pictureContentList.add(picture);
                }
                handler.sendEmptyMessage(0);
            }
        }).start();

        swiperereshlayout.setOnRefreshListener(()->{
            onRefresh();
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    void onRefresh(){
        pla.claerAllData();
        new Thread(()->{
            List<PictureContent> presentPictureContentList = new CatchNHBookPictureList().catchBookByPage();
            for(PictureContent picture : presentPictureContentList){
                System.out.println("添加完成");
                pictureContentList.add(picture);
            }
            handler.sendEmptyMessage(0);
        }).start();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id){
            case R.id.action_about:
                Intent intent = new Intent(MainActivity.this,About.class);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
