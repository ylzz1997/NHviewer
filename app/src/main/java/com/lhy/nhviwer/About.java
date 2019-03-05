package com.lhy.nhviwer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class About extends AppCompatActivity {
    RecyclerView aboutList;
    AboutListAdapter aboutListAdapter;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        toolbar = findViewById(R.id.about_toolbar);
        setSupportActionBar(toolbar);
        aboutList = findViewById(R.id.about_list);
        aboutList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        aboutList.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        List<StandarSecondItemContent> listContent= new ArrayList<StandarSecondItemContent>(){{
            this.add(new StandarSecondItemContent(getString(R.string.about_author),getString(R.string.about_author_content)));
            this.add(new StandarSecondItemContent(getString(R.string.about_qq),getString(R.string.about_qq_content)));
            this.add(new StandarSecondItemContent(getString(R.string.about_email),getString(R.string.about_email_content)));
        }};
        aboutListAdapter=new AboutListAdapter(listContent);
        aboutListAdapter.setOnItemClickListener((v,i)->{
            Toast.makeText(About.this,"点击了"+i,Toast.LENGTH_SHORT).show();
        });
        aboutList.setItemAnimator(new DefaultItemAnimator());
        aboutList.setAdapter(aboutListAdapter);

        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_material);
        toolbar.setNavigationOnClickListener((v -> {
            finish();
        } ));
}
}
