package com.example.max.wuxia20;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;


public class ChaptersList extends AppCompatActivity {
    public Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        Log.d("onCreate", "chaptersListOnCreateCalled");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapters_list);
        context = this;

        if (getIntent().hasExtra("novelName")){
            String novelName = getIntent().getExtras().getString("novelName");
            TextView novelNameText = (TextView) findViewById(R.id.novelName);
            novelNameText.setText(novelName);

            ChapterListTask chapterListTask = new ChapterListTask(context, this.findViewById(android.R.id.content), novelName);
            chapterListTask.execute(novelName);
        }
    }

    @Override
    public void onBackPressed(){
        Intent startIntent = new Intent(context, NovelsList.class);
        startActivity(startIntent);
    }
}

