package com.example.max.wuxia20;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import java.util.ArrayList;
import java.util.HashMap;


public class ChapterListTask extends AsyncTask<String, Integer, ArrayList> {
    private Context context;
    private View view;
    private String novelName;
    private ChapterListExtractor chapterExtractor;

    ChapterListTask(Context context, View view, String novelName){
        this.context = context;
        this.view = view;
        this.novelName = novelName;
    }

    @Override
    protected ArrayList doInBackground(String... strings) {
        chapterExtractor = new ChapterListExtractor();
        return chapterExtractor.GetChaptersList(strings[0]);
    }

    @Override
    protected void onPostExecute(ArrayList chapters){
        int startingId = 7777;
        for (Object chapter : chapters){
            CreateChapterButton(startingId, chapter, chapters);
            startingId++;
        }
    }

    private void CreateChapterButton(int id, final Object chapter, final ArrayList chapters) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        Button btn = new Button(context);
        btn.setId(id);
        btn.setText(chapter.toString());
        btn.setBackgroundColor(Color.rgb(198, 226, 255));
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.scrollLinearLayourId);
        linearLayout.addView(btn, layoutParams);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String chapterUrl = chapterExtractor.GetUrlFromChapters(novelName, chapter.toString());
                HashMap chaptersandurls = chapterExtractor.GetChaptersAndUrls(novelName);
                Intent startIntent = new Intent(context, ChapterContent.class);
                startIntent.putExtra("chapterUrl", chapterUrl);
                startIntent.putExtra("chapterArray", chapters);
                startIntent.putExtra("chapter", chapter.toString());
                startIntent.putExtra("allUrls", chaptersandurls);
                startIntent.putExtra("novelName", novelName);
                context.startActivity(startIntent);
            }
            //Chapter URL and chapter Array are too big
        });
    }
}
