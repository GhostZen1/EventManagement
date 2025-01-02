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
                "username TEXT NOT NULL);";

        String createEventTypeTable = "CREATE TABLE eventType (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "eventtypename TEXT NOT NULL);";

        String createEventTable = "CREATE TABLE event (" +
                "eventId INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "eventtypeId INTEGER, " +
                "eventtypename TEXT, " +
                "eventdate DATE, " +
                "FOREIGN KEY (eventtypeId) REFERENCES eventType(id));";

        String createBookingTable = "CREATE TABLE booking (" +
                "bookingId INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "BookingName TEXT NOT NULL, " +
                "eventtypeId INTEGER, " +
                "userId INTEGER, " +
                "FOREIGN KEY (eventtypeId) REFERENCES eventType(id), " +
                "FOREIGN KEY (userId) REFERENCES user(UserId));";

        db.execSQL(createUser);
        db.execSQL(createEventTypeTable);
        db.execSQL(createEventTable);
        db.execSQL(createBookingTable);

        //region default value
        //user
        db.execSQL("INSERT INTO user (email, password, username) VALUES ('test@example.com', '1234', 'testuser');");

        //main event type
        db.execSQL("INSERT INTO eventType (eventtypename) VALUES ('Concerts');");
        db.execSQL("INSERT INTO eventType (eventtypename) VALUES ('Sports');");
        db.execSQL("INSERT INTO eventType (eventtypename) VALUES ('Theater');");
        db.execSQL("INSERT INTO eventType (eventtypename) VALUES ('Other Events');");

        //concerts
        db.execSQL("INSERT INTO event (eventtypeId,eventtypename,eventdate) VALUES ('1','Concert at Madison Square Garden','2024-01-15');");
        db.execSQL("INSERT INTO event (eventtypeId,eventtypename,eventdate) VALUES ('1','Coldplay World Tour 2024','2024-03-15');");
        db.execSQL("INSERT INTO event (eventtypeId,eventtypename,eventdate) VALUES ('1','Adele Live in Concert','2024-03-15');");
        db.execSQL("INSERT INTO event (eventtypeId,eventtypename,eventdate) VALUES ('1','BTS Comeback Tour','2024-06-05');");
        db.execSQL("INSERT INTO event (eventtypeId,eventtypename,eventdate) VALUES ('1','Taylor Swift Eras Tour','2024-07-10');");
        //endregion
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

        long id = db.insert("user",null, val);
        db.close();

        return id;
    }

    public long login(String email, String Password){
        long id = 0;
        String query = "SELECT * FROM user WHERE email = '"+email+"' AND password = '"+Password+"'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);

        if(cursor.moveToFirst()){
            id = cursor.getInt(cursor.getColumnIndexOrThrow("UserId"));
        }

        return id;
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

    public List<Event> fetchEventsDetail() {
        List<Event> events = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT e.eventtypename, e.eventdate, et.eventtypename AS category " +
                "FROM event e " +
                "INNER JOIN eventType et ON e.eventtypeId = et.id";

        Cursor cursor = db.rawQuery(query, null);

        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault());

        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndexOrThrow("eventtypename"));
                String date = cursor.getString(cursor.getColumnIndexOrThrow("eventdate"));
                String category = cursor.getString(cursor.getColumnIndexOrThrow("category"));

                // Convert the date to the required format
                try {
                    Date parsedDate = inputFormat.parse(date);  // Parse the date
                    String formattedDate = outputFormat.format(parsedDate);  // Format the date

                    // Add event to the list
                    events.add(new Event(name, formattedDate, category));
                } catch (ParseException e) {
                    e.printStackTrace();  // Handle any date parsing errors
                }
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return events;
    }
}
