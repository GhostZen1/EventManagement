package edu.my.utem.ftmk.projecteventmanagement;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SqLite extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "EventManagement.db";
    public SqLite(Context context) {super(context,DATABASE_NAME, null, DATABASE_VERSION);}

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUser  = "CREATE TABLE user (" +
                "UserId INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "email TEXT NOT NULL, " +
                "password TEXT NOT NULL, " +
                "username TEXT NOT NULL, " +
                "ic TEXT NOT NULL, " +
                "role TEXT NOT NULL);";

        String createEventTypeTable = "CREATE TABLE eventType (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "eventtypename TEXT NOT NULL);";

        String createEventTable = "CREATE TABLE event (" +
                "eventId INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "eventtypeId INTEGER, " +
                "eventtypename TEXT, " +
                "eventname INTEGER, " +
                "eventdate DATE, " +
                "eventprice DOUBLE, " +
                "FOREIGN KEY (eventtypeId) REFERENCES eventType(id));";

        String createBookingTable = "CREATE TABLE booking (" +
                "bookingId INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "userId INTEGER, " +
                "eventId INTEGER, " +
                "bookingdate DATE, " +
                "bookingprice DOUBLE, " +
                "FOREIGN KEY (eventId) REFERENCES eventType(id), " +
                "FOREIGN KEY (userId) REFERENCES user(UserId));";

        db.execSQL(createUser);
        db.execSQL(createEventTypeTable);
        db.execSQL(createEventTable);
        db.execSQL(createBookingTable);

        //user
        db.execSQL("INSERT INTO user (email, password, username, ic, role) VALUES ('admin', 'admin', 'admin', '01', 'admin');");
        db.execSQL("INSERT INTO user (email, password, username, ic, role) VALUES ('1', '1', '1', '1', 'user');");
        //main event type
        db.execSQL("INSERT INTO eventType (eventtypename) VALUES ('Concerts');");

        db.execSQL("INSERT INTO event (eventtypeId,eventtypename,eventname,eventdate,eventprice) VALUES ('1','Concerts','Concert at Madison Square Garden','2024-01-15',16.00);");
        db.execSQL("INSERT INTO booking (userId,eventId,bookingdate,bookingprice) VALUES ('2','1','2024-01-15',16.00);");
//
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS booking");
        db.execSQL("DROP TABLE IF EXISTS event");
        db.execSQL("DROP TABLE IF EXISTS eventType");
        db.execSQL("DROP TABLE IF EXISTS user");
        onCreate(db);
    }

    public long addUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues val = new ContentValues();
        val.put("username",user.getName());
        val.put("email",user.getEmail());
        val.put("password",user.getPassword());
        val.put("ic", user.getIc());
        val.put("role", "user");

        long id = db.insert("user",null, val);
        db.close();

        return id;
    }

    public UserSession login(String email, String password) {
        UserSession userSession = null;
        String query = "SELECT UserId, role FROM user WHERE email = ? AND password = ?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{email, password});

        if (cursor.moveToFirst()) {
            int userId = cursor.getInt(cursor.getColumnIndexOrThrow("UserId"));
            String role = cursor.getString(cursor.getColumnIndexOrThrow("role"));
            userSession = new UserSession(userId, role);
        }

        cursor.close();
        db.close();
        return userSession; // Return user ID and role
    }


    public List<EventType> getEventCategories() {
        List<EventType> categories = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM  eventType" , null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("eventtypename"));
                categories.add(new EventType(id, name));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return categories;
    }

    public List<Event> fetchEventsByType(int eventTypeId) {
        List<Event> events = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM event WHERE eventtypeId = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(eventTypeId)});

        if (cursor.moveToFirst()) {
            do {
                int id = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("eventId")));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("eventname"));
                String date = cursor.getString(cursor.getColumnIndexOrThrow("eventdate"));
                double price = cursor.getDouble(cursor.getColumnIndexOrThrow("eventprice"));
                String eventtypename = cursor.getString(cursor.getColumnIndexOrThrow("eventtypename"));
                events.add(new Event(id, name, date, eventtypename, price)); // Replace null with the category if needed
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return events;
    }

}