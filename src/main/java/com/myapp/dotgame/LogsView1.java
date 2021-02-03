package com.myapp.dotgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LogsView1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logs_view1);
        try {
            TextView tv = (TextView)findViewById(R.id.logsID);
            tv.setText("");
            Process process = Runtime.getRuntime().exec("logcat -d INFO:I *:S");
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            StringBuilder log = new StringBuilder();
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                log.append(line + "\n");
            }
            tv.setMovementMethod(new ScrollingMovementMethod());
            tv.setText(log.toString());
            Runtime.getRuntime().exec("logcat -c");
        } catch (IOException e) {
        }
    }
}