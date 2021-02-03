package com.myapp.dotgame;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.myapp.dotgame.database.Record;
import com.myapp.dotgame.database.RecordDBHelper;

import java.io.IOException;
import java.util.Random;

import static java.lang.Math.abs;

public class DotGameView extends AppCompatActivity {
    int score = 0;
    Button button;
    String username;
    TextView remainingTime;
    CountDownTimer cdt;
    Boolean isClicked = false;
    Boolean isGameOver = false;
    int pause = 3000;
    AlertDialog.Builder builder;
    View gameView02;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dot_game_view);
        gameView02 = findViewById(R.id.gamelayout02);
        button = gameView02.findViewById(R.id.my_button);
        try {
            Runtime.getRuntime().exec("logcat -c");
        } catch (IOException e) {
            e.printStackTrace();
        }
        username = getIntent().getStringExtra("username");
        if (pause >= 0)
            showButton();
    }

    public void stopTimerTask() {
        if (cdt != null) {
            cdt.cancel();
            cdt = null;
        }
    }

    public void countDown() {
        gameView02 = findViewById(R.id.gamelayout02);
        remainingTime = gameView02.findViewById(R.id.remainingTime);
        cdt = new CountDownTimer(pause, 1000) {
            public void onTick(long millisUntilFinished) {
                remainingTime.setText("Remaining time to click: " + pause + " ms. \n Current score is: " + score + "\n");
            }

            public void onFinish() {
                if (isClicked == false) {
                    gameOver();
                }
            }
        };
        cdt.start();
    }

    public void countIN(View view) {
        if (isGameOver==false) {
            isClicked = true;
            score++;
            button.setVisibility(View.INVISIBLE);
            Log.i("INFO", username + "'s score is " + score);
            stopTimerTask();
            showButton();
        }
    }


    private void gameOver() {
        runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(getApplicationContext(), "GAME OVER!! You scored " + score, Toast.LENGTH_LONG).show();
            }
        });
        addToDB();
        stopTimerTask();
        Log.i("INFO", "Game Over!!. Final score is " + score);
        showPopup();
        score = 0;
        isGameOver=true;
    }

    private void addToDB() {
        RecordDBHelper rdb = new RecordDBHelper(this);
        rdb.addHandler(new Record(username, score));
    }

    public void showButton() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                isClicked = false;
                //button.setVisibility(View.INVISIBLE);
                LinearLayout gameLayout = (LinearLayout) findViewById(R.id.gamelayout02);
                int gameLayoutHeight = gameLayout.getHeight();
                int gameLayoutWidth = gameLayout.getWidth();
                Random R = new Random();
                float dx = (R.nextFloat() * abs(gameLayoutWidth - 500));
                float dy = (R.nextFloat() * abs(gameLayoutHeight - 500));
                button.setX(dx);
                button.setY(dy);
                button.setVisibility(View.VISIBLE);
                pause -= 150;
                countDown();
            }
        });
    }

    public void showPopup() {
        builder = new AlertDialog.Builder(this);
        builder.setMessage("Final score of " + username + " is " + score + ".")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        AlertDialog alert = builder.create();
        alert.setTitle("GAME OVER !!");
        alert.show();
    }

}