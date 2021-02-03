package com.myapp.dotgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainMenuActivity extends AppCompatActivity {
    Button play;
    Button resume;
    Button record;
    Button logs;
    TextView welcomeBanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        String username = getIntent().getStringExtra("username");
        welcomeBanner = (TextView)findViewById(R.id.welcome);
        welcomeBanner.append(" " + username);

        play = (Button)findViewById(R.id.playButton);
        record = (Button)findViewById(R.id.recordsButton);
        logs = (Button)findViewById(R.id.logsButton);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, DotGameView.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });

        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, RecordView.class);
                startActivity(intent);
            }
        });

        logs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, LogsView1.class);
                startActivity(intent);
            }
        });
    }
}