package com.myapp.dotgame;

import androidx.appcompat.app.AppCompatActivity;

import com.myapp.dotgame.database.DotGameContract;
import com.myapp.dotgame.database.DotGameContract.*;
import com.myapp.dotgame.database.RecordDBHelper;
import com.myapp.dotgame.database.Record;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class RecordView extends AppCompatActivity {
    private GridView gridView;
    public static ArrayList<String> recordList = new ArrayList<String>();
    final String[] from = new String[] {DotGameEntry.COLUMN_NAME_PLAYERNAME,
            DotGameEntry.COLUMN_NAME_SCORE};
    final int[] to = new int[] { R.id.name, R.id.score};
    List<Record> records;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_view);

        //ListView lv;
        RecordDBHelper rdb = new RecordDBHelper(this);

        records = rdb.getTop10Scores();
        //Reading all records
        if(recordList.size() > 0) {
            recordList.clear();
        }
        for (Record cn : records) {
            String log = "Player Name: "+cn.getPlayerName()+" \nScore: " + cn.getScore() + "\n";
            recordList.add(log);
            //String log can be used to print the records in logs.
        }

        gridView = (GridView) findViewById(R.id.gridView1);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, recordList);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(getApplicationContext(),
                        ((TextView) v).getText(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}