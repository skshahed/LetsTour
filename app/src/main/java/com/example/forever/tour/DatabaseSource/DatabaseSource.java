package com.example.forever.tour.DatabaseSource;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.forever.tour.CRUDClass.Event;
import com.example.forever.tour.CRUDClass.Expense;
import com.example.forever.tour.CRUDClass.Moment;
import com.example.forever.tour.CRUDClass.User;
import com.example.forever.tour.DatabaseHelper.TourDatabaseHelper;

import java.util.ArrayList;

/**
 * Created by Ashif Rahman on 4/25/2017.
 */

public class DatabaseSource {
    private TourDatabaseHelper tourDatabaseHelper;
    private SQLiteDatabase sqLiteDatabase;

    private User user;
    private Event event;
    private Expense expense;
    private Moment moment;

    private int totalExpense ;

    public DatabaseSource(Context context) {
        tourDatabaseHelper = new TourDatabaseHelper(context);

    }

    public void open()
    {
        sqLiteDatabase = tourDatabaseHelper.getWritableDatabase();
    }

    public void close(){
        sqLiteDatabase.close();

    }


    public int addSignUp(User user){
        this.open();

        Cursor cursor = sqLiteDatabase.rawQuery("select * from "+TourDatabaseHelper.TOUR_USER_INFO+" where "+TourDatabaseHelper.USER_NAME+" = '"+user.getUserName()+"'",null);
        if(cursor != null && cursor.getCount() > 0){
            return 00;
        }
        else {

            ContentValues values = new ContentValues();
            values.put(TourDatabaseHelper.USER_PHOTO_PATH, user.getImagePath());
            values.put(TourDatabaseHelper.USER_FULL_NAME, user.getFullName());
            values.put(TourDatabaseHelper.USER_NAME, user.getUserName());
            values.put(TourDatabaseHelper.USER_PASSWORD, user.getPassword());
            values.put(TourDatabaseHelper.USER_EMERGENCY_CONTACT, user.getContactNumber());
            long id = sqLiteDatabase.insert(TourDatabaseHelper.TOUR_USER_INFO, null, values);

            this.close();
            if (id > 0) {
                return 1;
            } else {
                return 0;
            }
        }

    }



    public ArrayList<User> getSingleUsers(String userN){
        ArrayList<User> users = new ArrayList<>();
        this.open();
        // Cursor cursor = sqLiteDatabase.query(TourDatabaseHelper.TOUR_USER_INFO,null,null,null,null,null,null);
        Cursor cursor = sqLiteDatabase.rawQuery("select * from "+TourDatabaseHelper.TOUR_USER_INFO+" where "+TourDatabaseHelper.USER_NAME+" = '"+userN+"'  or  "+TourDatabaseHelper.USER_ID+" = '"+userN+"'",null);
        cursor.moveToFirst();
        if (cursor != null && cursor.getCount() > 0){
            for (int i = 0;i < cursor.getCount();i++){
                int id = cursor.getInt(cursor.getColumnIndex(TourDatabaseHelper.USER_ID));
                String userName = cursor.getString(cursor.getColumnIndex(TourDatabaseHelper.USER_NAME));
                String userPasswprd = cursor.getString(cursor.getColumnIndex(TourDatabaseHelper.USER_PASSWORD));
                String fullname=cursor.getString(cursor.getColumnIndex(TourDatabaseHelper.USER_FULL_NAME));
                String userPhone = cursor.getString(cursor.getColumnIndex(TourDatabaseHelper.USER_EMERGENCY_CONTACT));
                String userPhoto = cursor.getString(cursor.getColumnIndex(TourDatabaseHelper.USER_PHOTO_PATH));

                user = new User(id,userPhoto,fullname,userName,userPasswprd,userPhone);
                users.add(user);
                cursor.moveToNext();
            }
        }
        cursor.close();
        this.close();
        return users;
    }

    public int updateUserInfo(User user, int userId){
        this.open();
        ContentValues values = new ContentValues();
        values.put(TourDatabaseHelper.USER_PHOTO_PATH, user.getImagePath());
        values.put(TourDatabaseHelper.USER_FULL_NAME, user.getFullName());
        values.put(TourDatabaseHelper.USER_NAME, user.getUserName());
        values.put(TourDatabaseHelper.USER_PASSWORD, user.getPassword());
        values.put(TourDatabaseHelper.USER_EMERGENCY_CONTACT, user.getContactNumber());

        Cursor cursor = sqLiteDatabase.rawQuery("select * from "+TourDatabaseHelper.TOUR_USER_INFO+" " +
                "where "+TourDatabaseHelper.USER_NAME+" = '"+user.getUserName()+
                "' and "+TourDatabaseHelper.USER_ID+" != '"+userId+"' ",null);
        if(cursor != null && cursor.getCount() > 0){
            return 00;
        }else{

            int  updateId = sqLiteDatabase.update(TourDatabaseHelper.TOUR_USER_INFO,
                    values,TourDatabaseHelper.USER_ID+" = ?",new String[]{Integer.toString(userId)});

            if(updateId > 0){
                return  1;
            }else{
                return 0;
            }
        }

    }


    public  boolean loginUser(User user){
        this.open();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from "+TourDatabaseHelper.TOUR_USER_INFO+" where "+TourDatabaseHelper.USER_NAME+" = '"+user.getUserName()+"'"+" and  "+TourDatabaseHelper.USER_PASSWORD+" = '"+user.getPassword()+"'",null);
        //cursor.moveToFirst();
        if (cursor != null && cursor.getCount() > 0){
            return true;
        }else{
            return false;
        }
    }


    public boolean addEventInfo(Event event, String userName){
        this.open();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from "+TourDatabaseHelper.TOUR_USER_INFO+" where "+TourDatabaseHelper.USER_NAME+" = '"+event.getUserName()+"' or "+TourDatabaseHelper.USER_ID+" = '"+userName+"' ",null);
        cursor.moveToFirst();
        int uId = cursor.getInt(cursor.getColumnIndex(TourDatabaseHelper.USER_ID));


        ContentValues values = new ContentValues();
        values.put(TourDatabaseHelper.EVENT_USER_ID, uId);
        //values.put(TourDatabaseHelper.EVENT_USER_ID, event.getUserName());
        values.put(TourDatabaseHelper.EVENT_TRAVEL_DESTINATION, event.getTraDestination());
        values.put(TourDatabaseHelper.EVENT_ESTIMATE_BUDGET, event.getTraBudget());
        values.put(TourDatabaseHelper.EVENT_FROM_DATE, event.getTraFromDate());
        values.put(TourDatabaseHelper.EVENT_TO_DATE, event.getTraToDate());
        long id = sqLiteDatabase.insert(TourDatabaseHelper.TOUR_TRAVEL_EVENT,null,values);
        this.close();

        if (id > 0){
            return true;
        }
        else {
            return false;
        }

    }



    public ArrayList<Event> getAllEvents(String userName){
        ArrayList<Event> events = new ArrayList<>();
        this.open();
        // find for user integer ID
            Cursor cursor = sqLiteDatabase.rawQuery("select * from "+TourDatabaseHelper.TOUR_USER_INFO+" where "+TourDatabaseHelper.USER_NAME+" = '"+userName+"' or "+TourDatabaseHelper.USER_ID+" = '"+userName+"' ",null);
            cursor.moveToFirst();
            int uId = cursor.getInt(cursor.getColumnIndex(TourDatabaseHelper.USER_ID));
        // end find for user integer ID

        //Cursor cursor = sqLiteDatabase.query(TourDatabaseHelper.TOUR_TRAVEL_EVENT,null,null,null,null,null,null);
        Cursor cursor2 = sqLiteDatabase.rawQuery("select * from "+TourDatabaseHelper.TOUR_TRAVEL_EVENT+" where "+TourDatabaseHelper.EVENT_USER_ID+" = "+uId+" order by "+TourDatabaseHelper.EVENT_ID+" DESC ",null);
        cursor2.moveToFirst();
        if (cursor2 != null && cursor2.getCount() > 0){
            for (int i = 0;i < cursor2.getCount();i++){
                int id = cursor2.getInt(cursor2.getColumnIndex(TourDatabaseHelper.EVENT_ID));
                String userIdn= cursor2.getString(cursor2.getColumnIndex(TourDatabaseHelper.EVENT_USER_ID));
                String destination= cursor2.getString(cursor2.getColumnIndex(TourDatabaseHelper.EVENT_TRAVEL_DESTINATION));
                String budjget= cursor2.getString(cursor2.getColumnIndex(TourDatabaseHelper.EVENT_ESTIMATE_BUDGET));
                String fromdate=cursor2.getString(cursor2.getColumnIndex(TourDatabaseHelper.EVENT_FROM_DATE));
                String todate = cursor2.getString(cursor2.getColumnIndex(TourDatabaseHelper.EVENT_TO_DATE));


                // start nested loop for calculate per event expense amt
                Cursor cursor3 = sqLiteDatabase.rawQuery("select * from "+TourDatabaseHelper.TOUR_TRAVEL_EXPENSE_TRACKER+" where "+TourDatabaseHelper.EXPENSE_EVENT_ID+" = '"+id+"' ",null);
                cursor3.moveToFirst();
                if (cursor3 != null && cursor3.getCount() > 0) {
                    totalExpense = 0;
                    for (int i3 = 0; i3 < cursor3.getCount(); i3++) {
                        int exDeAmount=cursor3.getInt(cursor3.getColumnIndex(TourDatabaseHelper.EXPENSE_AMOUNT));
                        totalExpense = totalExpense + exDeAmount;
                        //events.add(event);
                        cursor3.moveToNext();
                    }

                }
                /*totalExpense = 0;
                for(int k= 1;k<=3;k++){
                    totalExpense = totalExpense +k;
                }*/
                // end start nested loop for calculate per event expense amt//else {
                if(totalExpense>0){
                    event = new Event(id,userIdn,destination,budjget,fromdate,todate,String.valueOf(totalExpense));
                }else{
                    event = new Event(id,userIdn,destination,budjget,fromdate,todate);
                }
                     events.add(event);
                     cursor2.moveToNext();
            }
        }
        cursor2.close();
        this.close();
        return events;
    }



    public boolean updateEvent(Event event,int eventID){
        this.open();
        ContentValues values = new ContentValues();
        values.put(TourDatabaseHelper.EVENT_ESTIMATE_BUDGET, event.getTraBudget());
        values.put(TourDatabaseHelper.EVENT_TRAVEL_DESTINATION, event.getTraDestination());
        values.put(TourDatabaseHelper.EVENT_FROM_DATE, event.getTraFromDate());
        values.put(TourDatabaseHelper.EVENT_TO_DATE, event.getTraToDate());

        int  updateId = sqLiteDatabase.update(TourDatabaseHelper.TOUR_TRAVEL_EVENT,
                values,TourDatabaseHelper.EVENT_ID+" = ?",new String[]{Integer.toString(eventID)});
        if(updateId > 0){
            return  true;
        }else{
            return false;
        }
    }




    public boolean addExpenseInfo(Expense expense){
        this.open();
        ContentValues values = new ContentValues();
        values.put(TourDatabaseHelper.EXPENSE_EVENT_ID, expense.getEventId());
        values.put(TourDatabaseHelper.EXPENSE_EVENT_ID, expense.getEventId());
        values.put(TourDatabaseHelper.EXPENSE_DATE_TIME, expense.getDateTime());
        values.put(TourDatabaseHelper.EXPENSE_DETAILS, expense.getExpDetails());
        values.put(TourDatabaseHelper.EXPENSE_AMOUNT, expense.getExpAmt());
        long id = sqLiteDatabase.insert(TourDatabaseHelper.TOUR_TRAVEL_EXPENSE_TRACKER,null,values);
        this.close();

        if (id > 0){
            return true;
        }
        else {
            return false;
        }

    }


    public ArrayList<Expense> getAllExpense(String eventId, String userName){
        ArrayList<Expense> expenses = new ArrayList<>();
        this.open();
        //Cursor cursor = sqLiteDatabase.query(TourDatabaseHelper.TOUR_TRAVEL_EVENT,null,null,null,null,null,null);
        Cursor cursor = sqLiteDatabase.rawQuery("select * from "+TourDatabaseHelper.TOUR_TRAVEL_EXPENSE_TRACKER+" where "+TourDatabaseHelper.EXPENSE_EVENT_ID+" = "+eventId+" order by "+TourDatabaseHelper.EXPENSE_ID+" DESC ",null);
        cursor.moveToFirst();
        if (cursor != null && cursor.getCount() > 0){
            for (int i = 0;i < cursor.getCount();i++){
                int id = cursor.getInt(cursor.getColumnIndex(TourDatabaseHelper.EXPENSE_ID));
                String exEventID= cursor.getString(cursor.getColumnIndex(TourDatabaseHelper.EXPENSE_EVENT_ID));
                String exDateTime= cursor.getString(cursor.getColumnIndex(TourDatabaseHelper.EXPENSE_DATE_TIME));
                String exDetails= cursor.getString(cursor.getColumnIndex(TourDatabaseHelper.EXPENSE_DETAILS));
                String exAmount=cursor.getString(cursor.getColumnIndex(TourDatabaseHelper.EXPENSE_AMOUNT));

                expense = new Expense(id,exEventID,exDateTime,exDetails,exAmount,userName);
                expenses.add(expense);
                cursor.moveToNext();
            }
        }
        cursor.close();
        this.close();
        return expenses;
    }






    public boolean addMomentInfo(Moment moment){
        this.open();
        ContentValues values = new ContentValues();
        values.put(TourDatabaseHelper.MOMENT_EVENT_ID, moment.getEventId());
        values.put(TourDatabaseHelper.MOMENT_DATE_TIME, moment.getmDateTime());
        values.put(TourDatabaseHelper.MOMENT_DETAILS, moment.getMomentDetails());
        values.put(TourDatabaseHelper.MOMENT_PHOTO, moment.getMomentPhotopath());
        long id = sqLiteDatabase.insert(TourDatabaseHelper.TOUR_TRAVEL_MOMENT,null,values);
        this.close();
        if (id > 0){
            return true;
        }
        else {
            return false;
        }
    }

    public ArrayList<Moment> getAllMoment(int eventId, String userName){
        ArrayList<Moment> moments = new ArrayList<>();
        this.open();
        //Cursor cursor = sqLiteDatabase.query(TourDatabaseHelper.TOUR_TRAVEL_EVENT,null,null,null,null,null,null);
        Cursor cursor = sqLiteDatabase.rawQuery("select * from "+TourDatabaseHelper.TOUR_TRAVEL_MOMENT+" where "+TourDatabaseHelper.MOMENT_EVENT_ID+" = "+eventId+" order by "+TourDatabaseHelper.MOMENT_ID+" DESC ",null);
        cursor.moveToFirst();
        if (cursor != null && cursor.getCount() > 0){
            for (int i = 0;i < cursor.getCount();i++){
                int id = cursor.getInt(cursor.getColumnIndex(TourDatabaseHelper.MOMENT_ID));
                String mEventID= cursor.getString(cursor.getColumnIndex(TourDatabaseHelper.MOMENT_EVENT_ID));
                String momentDateTime= cursor.getString(cursor.getColumnIndex(TourDatabaseHelper.MOMENT_DATE_TIME));
                String mPhoto= cursor.getString(cursor.getColumnIndex(TourDatabaseHelper.MOMENT_PHOTO));
                String mDetails=cursor.getString(cursor.getColumnIndex(TourDatabaseHelper.MOMENT_DETAILS));

                moment = new Moment(id,userName,mEventID,momentDateTime,mPhoto,mDetails);
                moments.add(moment);
                cursor.moveToNext();
            }
        }
        cursor.close();
        this.close();
        return moments;
    }


    public boolean editMoment(Moment moment,int momentId){
        this.open();
        ContentValues values = new ContentValues();
        values.put(TourDatabaseHelper.MOMENT_DETAILS, moment.getMomentDetails());
        values.put(TourDatabaseHelper.MOMENT_PHOTO, moment.getMomentPhotopath());
        int  updateId = sqLiteDatabase.update(TourDatabaseHelper.TOUR_TRAVEL_MOMENT,
                values,tourDatabaseHelper.MOMENT_ID+" = ?",new String[]{Integer.toString(momentId)});
        if(updateId > 0){
            return  true;
        }else{
            return false;
        }
    }


    public boolean deleteMoment(int momentId){
        this.open();
        int deleteId    =   sqLiteDatabase.delete(TourDatabaseHelper.TOUR_TRAVEL_MOMENT,
                TourDatabaseHelper.MOMENT_ID+"=?",new String[]{Integer.toString(momentId)});
        this.close();
        if(deleteId>0){
            return true;
        }else{
            return false;
        }
    }



    public boolean deleteExpense(int expenseID){
        this.open();
        int deleteId    =   sqLiteDatabase.delete(TourDatabaseHelper.TOUR_TRAVEL_EXPENSE_TRACKER,
                TourDatabaseHelper.EXPENSE_ID+"=?",new String[]{Integer.toString(expenseID)});
        this.close();
        if(deleteId>0){
            return true;
        }else{
            return false;
        }
    }

    public boolean editExpense(Expense expense,int expId){
        this.open();
        ContentValues values = new ContentValues();
        values.put(TourDatabaseHelper.EXPENSE_DETAILS, expense.getExpDetails());
        values.put(TourDatabaseHelper.EXPENSE_AMOUNT, expense.getExpAmt());
        int  updateId = sqLiteDatabase.update(TourDatabaseHelper.TOUR_TRAVEL_EXPENSE_TRACKER,
                values,tourDatabaseHelper.EXPENSE_ID+" = ?",new String[]{Integer.toString(expId)});
        if(updateId > 0){
            return  true;
        }else{
            return false;
        }
    }


    public boolean deleteEvent(int eventID){
        this.open();
        int deleteId    =   sqLiteDatabase.delete(TourDatabaseHelper.TOUR_TRAVEL_EVENT,
                TourDatabaseHelper.EVENT_ID+"=?",new String[]{Integer.toString(eventID)});

        int deleteIdEx    =   sqLiteDatabase.delete(TourDatabaseHelper.TOUR_TRAVEL_EXPENSE_TRACKER,
                TourDatabaseHelper.EXPENSE_EVENT_ID+"=?",new String[]{Integer.toString(eventID)});

        int deleteMo    =   sqLiteDatabase.delete(TourDatabaseHelper.TOUR_TRAVEL_MOMENT,
                TourDatabaseHelper.MOMENT_EVENT_ID+"=?",new String[]{Integer.toString(eventID)});
        this.close();
        //int deleteId = 1;
        if(deleteId>0){
            return true;
        }else{
            return false;
        }
    }


}