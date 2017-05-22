package com.example.forever.tour.DatabaseHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Ashif Rahman on 4/25/2017.
 */

public class TourDatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "TourCholoGhuri";
    public static final int DATABASE_VERSION = 5;

    public static final String TOUR_USER_INFO = "tour_user_Info";
        public static final String USER_ID = "user_id";
        public static final String USER_PHOTO_PATH =  "photo_path";
        public static final String USER_FULL_NAME = "full_name";
        public static final String USER_NAME = "user_name";
        public static final String USER_PASSWORD = "password";
        public static final String USER_EMERGENCY_CONTACT = "emergency_contact";
        public static final String USER_ADDRESS = "address";
        public static final String CREATE_SYSTEM_USER_TABLE = "create table " +TOUR_USER_INFO+"("+
            USER_ID+" integer primary key, "+
            USER_PHOTO_PATH+" text, "+
            USER_FULL_NAME+" text, "+
            USER_NAME+" text, "+
            USER_PASSWORD+" text, "+
            USER_EMERGENCY_CONTACT+" text, "+
            USER_ADDRESS+" text);";

    public static final String TOUR_TRAVEL_EVENT = "tour_travel_event_info";
        public static final String EVENT_ID = "event_id";
        public static final String EVENT_USER_ID = "user_id";
        public static final String EVENT_TRAVEL_DESTINATION = "travel_destination";
        public static final String EVENT_ESTIMATE_BUDGET = "estimate_budget";
        public static final String EVENT_FROM_DATE = "from_date";
        public static final String EVENT_TO_DATE = "to_date";
        public static final String CREATE_TRAVEL_EVENT_TABLE = "create table " +TOUR_TRAVEL_EVENT+"("+
            EVENT_ID+" integer primary key, "+
                EVENT_USER_ID+" text, "+
            EVENT_TRAVEL_DESTINATION+" text, "+
            EVENT_ESTIMATE_BUDGET+" text, "+
            EVENT_FROM_DATE+" text, "+
            EVENT_TO_DATE+" text);";

        public static final String TOUR_TRAVEL_EXPENSE_TRACKER = "tour_travel_expense_tracker";  //Relational table
            public static final String EXPENSE_ID = "expense_id";
            public static final String EXPENSE_EVENT_ID = "expense_event_id"; //Relation with TOUR_TRAVEL_EVENT
            public static final String EXPENSE_USER_ID = "user_id";
            public static final String EXPENSE_DATE_TIME = "expense_date_time";
            public static final String EXPENSE_DETAILS = "expense_details";
            public static final String EXPENSE_AMOUNT = "expense_amount";
            public static final String CREATE_TRAVEL_EXPENSE_TRACKER_TABLE = "create  table " +TOUR_TRAVEL_EXPENSE_TRACKER+"("+
                    EXPENSE_ID+" integer primary key, "+
                    EXPENSE_USER_ID+" text, "+
                    EXPENSE_DATE_TIME+" text, "+
                    EXPENSE_EVENT_ID+" text, "+
                    EXPENSE_DETAILS+" text, "+
                    EXPENSE_AMOUNT+" text);";

        public static final String TOUR_TRAVEL_MOMENT = "tour_travel_moment";  //Relational table
        public static final String MOMENT_ID = "moment_id";
        public static final String MOMENT_EVENT_ID = "moment_event_id"; //Relation with TOUR_TRAVEL_EVENT
        public static final String MOMENT_DATE_TIME = "moment_date_time";
        public static final String MOMENT_DETAILS = "moment_details";
        public static final String MOMENT_PHOTO = "moment_photo";
        public static final String CREATE_TRAVEL_MOMENT_TABLE = "create  table " +TOUR_TRAVEL_MOMENT+"("+
                MOMENT_ID+" integer primary key, "+
                MOMENT_EVENT_ID+" text, "+
                MOMENT_DATE_TIME+" text, "+
                MOMENT_DETAILS+" text, "+
                MOMENT_PHOTO+" text);";

    public TourDatabaseHelper(Context context) {
        super(context, DATABASE_NAME,  null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_SYSTEM_USER_TABLE);
        db.execSQL(CREATE_TRAVEL_EVENT_TABLE);
        db.execSQL(CREATE_TRAVEL_EXPENSE_TRACKER_TABLE);
        db.execSQL(CREATE_TRAVEL_MOMENT_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+TOUR_USER_INFO);
        db.execSQL("drop table if exists "+TOUR_TRAVEL_EVENT);
        db.execSQL("drop table if exists "+TOUR_TRAVEL_EXPENSE_TRACKER);
        db.execSQL("drop table if exists "+TOUR_TRAVEL_MOMENT);
        onCreate(db);

    }
    public void clearDatabase(){

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TOUR_USER_INFO,null,null);
        db.delete(TOUR_TRAVEL_EVENT,null,null);
        db.delete(TOUR_TRAVEL_EXPENSE_TRACKER,null,null);
        db.delete(TOUR_TRAVEL_MOMENT,null,null);
        db.close();
    }
}
