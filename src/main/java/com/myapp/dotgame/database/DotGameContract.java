package com.myapp.dotgame.database;

import android.provider.BaseColumns;

public class DotGameContract {
    private DotGameContract() {};

    /* Inner class that defines the table contents */
    public static class DotGameEntry implements BaseColumns {
        public static final String TABLE_NAME = "records";
        public static final String COLUMN_NAME_PLAYERNAME = "name";
        public static final String COLUMN_NAME_SCORE = "score";
    }
}
