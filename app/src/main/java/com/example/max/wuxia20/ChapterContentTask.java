package com.example.max.wuxia20;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Arrays;


public class ChapterContentTask extends AsyncTask<String, Integer, ArrayList> {
    private Context context;
    private View view;
    private TextToSpeechConverter ttsc;

    ChapterContentTask(Context context, View view){
        Log.d("onCreate", "chapterContentTaskCalled");
        this.context = context;
        this.view = view;
    }
    @Override
    protected ArrayList doInBackground(String... strings) {
        ChapterListExtractor chapterExtractor = new ChapterListExtractor();
        return chapterExtractor.GetArticleFromHtml(strings[0]);
    }

    @Override
    protected void onPostExecute(ArrayList chapterContent){
        if (chapterContent == null){
            TextView novelNameText = (TextView) view.findViewById(R.id.novelContentId);
            novelNameText.setText("noContentTextView");
        } else {
            TextView novelNameText = (TextView) view.findViewById(R.id.novelContentId);
            //chapterContent = chapterContent.replaceAll("Previous Chapter|Next Chapter", "");
//            for (int i = 0; i < 4; i++){
//                chapterContent = chapterContent.replaceFirst("\n", "");
//            }

            final String[] paragraphs = (String[]) chapterContent.toArray(new String[0]); //.split("\n");
            novelNameText.setText(String.join("\n\n", chapterContent));

            ttsc = new TextToSpeechConverter(context);
            ToggleButton readingButton = (ToggleButton) view.findViewById(R.id.readButton);
            readingButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton toggleButton, boolean b) {
                    int paragraphIndex = 0;

                    if (b){
                        while(b){
                            if (paragraphIndex > paragraphs.length - 1){
                                break;
                            }
                            String notReadParagraphs = paragraphs[paragraphIndex];
                            ttsc.convertTextToSpeech(notReadParagraphs);
                            paragraphIndex++;
                        }

                    } else {
                        ttsc.onPause();
                    }
                }
            });
        }
    }

    void terminate(){
        if (ttsc != null){
            ttsc.terminate();
        }
    }
}
