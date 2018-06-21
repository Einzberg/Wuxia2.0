package com.example.max.wuxia20;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class NovelsList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("onCreate", "novelListOnCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novels_list);

        Button againstTheGodsButton = (Button) findViewById(R.id.atgButton);
        againstTheGodsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(), ChaptersList.class);
                startIntent.putExtra("novelName", "Against The Gods");
                startActivity(startIntent);
            }
        });

        Button spiritRealmButton = (Button) findViewById(R.id.spiritButton);
        spiritRealmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(), ChaptersList.class);
                startIntent.putExtra("novelName", "Battle Through The Heavens");
                startActivity(startIntent);
            }
        });
    }
}