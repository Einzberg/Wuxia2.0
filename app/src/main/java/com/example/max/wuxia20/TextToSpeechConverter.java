package com.example.max.wuxia20;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import java.util.Locale;


class TextToSpeechConverter {

    private TextToSpeech textToSpeech;
    private Context context;

    TextToSpeechConverter(Context context){
        this.context = context;
        setupTextToSpeech();
    }

    private void setupTextToSpeech(){
        textToSpeech = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS){
                    int result = textToSpeech.setLanguage(Locale.US);
                    if(result==TextToSpeech.LANG_MISSING_DATA ||
                            result==TextToSpeech.LANG_NOT_SUPPORTED){
                        Log.e("error", "This Language is not supported");
                    }
                }
                else
                    Log.e("error", "Initilization Failed!");
            }
        });
    }

    void onPause() {
        if(textToSpeech != null){
            textToSpeech.stop();
        }
    }

    void convertTextToSpeech(String text){
        Log.d("speech", "Trying To Speech: " + text);
        textToSpeech.speak(text, TextToSpeech.QUEUE_ADD, null);
    }

    void terminate(){
        if(textToSpeech != null){
            textToSpeech.stop();
            textToSpeech.shutdown();

        }
    }
}
