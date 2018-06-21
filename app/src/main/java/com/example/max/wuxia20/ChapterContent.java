package com.example.max.wuxia20;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.HashMap;

public class ChapterContent extends AppCompatActivity {
    public Context context;

    String novelName;
    ChapterContentTask chapterContentTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("onCreate", "chapterContentCalled");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_content);
        context = this;

        if (getIntent().hasExtra("chapterUrl")){
            String chapterUrl = getIntent().getExtras().getString("chapterUrl");
            novelName = getIntent().getExtras().getString("novelName");
            String chapterName = getIntent().getExtras().getString("chapter");
            final ArrayList listOfChapters = (ArrayList) getIntent().getExtras().get("chapterArray");
            final HashMap<String, String> chapterURLHashMap = (HashMap) getIntent().getExtras().get("allUrls");

            ArrayList<String> chapters = (ArrayList) listOfChapters;
            int index = chapters.indexOf(chapterName);

            final String nextChapter = chapters.get(index + 1);
            final String previousChapter = chapters.get(index - 1);
            final String nextChapterUrl = chapterURLHashMap.get(nextChapter);
            final String previousChapterUrl = chapterURLHashMap.get(previousChapter);

            chapterContentTask = new ChapterContentTask(context, this.findViewById(android.R.id.content));
            chapterContentTask.execute(chapterUrl);

            Button nextButton = (Button) findViewById(R.id.nextButton);
            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    chapterContentTask.terminate();
                    Intent startIntent = new Intent(context, ChapterContent.class);
                    startIntent.putExtra("chapterUrl", nextChapterUrl);
                    startIntent.putExtra("chapterArray", listOfChapters);
                    startIntent.putExtra("chapter", nextChapter);
                    startIntent.putExtra("allUrls", chapterURLHashMap);
                    startIntent.putExtra("novelName", novelName);
                    context.startActivity(startIntent);
                }
            });


            Button previousButton = (Button) findViewById(R.id.previousButton);
            previousButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    chapterContentTask.terminate();
                    Intent startIntent = new Intent(context, ChapterContent.class);
                    startIntent.putExtra("chapterUrl", previousChapterUrl);
                    startIntent.putExtra("chapterArray", listOfChapters);
                    startIntent.putExtra("chapter", previousChapter);
                    startIntent.putExtra("allUrls", chapterURLHashMap);
                    startIntent.putExtra("novelName", novelName);
                    context.startActivity(startIntent);
                }
            });
        }
    }

    @Override
    public void onBackPressed(){
        chapterContentTask.terminate();
        Intent startIntent = new Intent(context, ChaptersList.class);
        startIntent.putExtra("novelName", novelName);
        startActivity(startIntent);
    }
}
