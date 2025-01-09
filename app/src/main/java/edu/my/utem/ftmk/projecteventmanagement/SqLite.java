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
                "eventtypename TEXT NOT NULL, " +
                "eventtypeImage TEXT NOT NULL);";

        String createEventTable = "CREATE TABLE event (" +
                "eventId INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "eventtypeId INTEGER, " +
                "eventtypename TEXT, " +
                "eventname INTEGER, " +
                "eventdate DATE, " +
                "eventprice DOUBLE, " +
                "eventImage TEXT NOT NULL, " +
                "FOREIGN KEY (eventtypeId) REFERENCES eventType(id));";


        String createBookingTable = "CREATE TABLE booking (" +
                "bookingId INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "userId INTEGER, " +
                "eventId INTEGER, " +
                "bookingdate DATE, " +
                "bookingprice DOUBLE, " +
                "slotNumber INTEGER," +
                "FOREIGN KEY (eventId) REFERENCES eventType(id), " +
                "FOREIGN KEY (userId) REFERENCES user(UserId));";

        db.execSQL(createUser);
        db.execSQL(createEventTypeTable);
        db.execSQL(createEventTable);
        db.execSQL(createBookingTable);

        //user
        db.execSQL("INSERT INTO user (email, password, username, ic, role) VALUES ('2', '2', 'admin', '01', 'admin');");
        db.execSQL("INSERT INTO user (email, password, username, ic, role) VALUES ('1', '1', '1', '1', 'user');");
        //main event type
        db.execSQL("INSERT INTO eventType (eventtypename, eventtypeImage) VALUES ('Concerts','concert.jpeg');");
        db.execSQL("INSERT INTO eventType (eventtypename, eventtypeImage) VALUES ('Sport','sport.jpg');");

        db.execSQL("INSERT INTO event (eventtypeId,eventtypename,eventname,eventdate,eventprice,eventImage) VALUES ('1','Concerts','Concert at Madison Square Garden','2024-01-15',16.00,'madison.jpg');");
        db.execSQL("INSERT INTO event (eventtypeId,eventtypename,eventname,eventdate,eventprice,eventImage) VALUES ('2','Delete','Concert at Madison Square Garden','2024-01-15',16.00,'madison.jpg');");
        db.execSQL("INSERT INTO event (eventtypeId,eventtypename,eventname,eventdate,eventprice,eventImage) VALUES ('3','delete','Concert at Madison Square Garden','2024-01-15',16.00,'madison.jpg');");
        db.execSQL("INSERT INTO booking (userId,eventId,bookingdate,bookingprice, slotNumber) VALUES ('2','1','2024-01-15',16.00,2);");
        db.execSQL("INSERT INTO booking (userId,eventId,bookingdate,bookingprice, slotNumber) VALUES ('2','1','2024-01-15',16.00,2);");
        db.execSQL("INSERT INTO booking (userId,eventId,bookingdate,bookingprice, slotNumber) VALUES ('2','1','2024-01-15',16.00,2);");
        db.execSQL("INSERT INTO booking (userId,eventId,bookingdate,bookingprice, slotNumber) VALUES ('2','1','2024-01-15',16.00,2);");
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
        Cursor cursor = db.rawQuery("SELECT * FROM eventType", null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("eventtypename"));
                String imageName = cursor.getString(cursor.getColumnIndexOrThrow("eventtypeImage"));
                categories.add(new EventType(id, name, imageName));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return categories;
    }

    public List<Event> getListEvent() {
        List<Event> events = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT event.eventId AS id, event.eventtypename AS eventName, event.eventdate AS date, event.eventprice AS price," +
                "event.eventImage AS img, eventType.eventtypename AS category " +
                "FROM event " +
                "INNER JOIN eventType ON event.eventtypeId = eventType.id", null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("eventName"));
                String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
                String category = cursor.getString(cursor.getColumnIndexOrThrow("category"));
                Double price = cursor.getDouble(cursor.getColumnIndexOrThrow("price"));
                String eventImage = cursor.getString(cursor.getColumnIndexOrThrow("img"));

                // Create a new Event object and add it to the list
                events.add(new Event(id, name, date, category, price, eventImage));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return events;
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
                String eventImage = cursor.getString(cursor.getColumnIndexOrThrow("eventImage"));  // Fetch the event image name

                events.add(new Event(id, name, date, eventtypename, price, eventImage)); // Include the image
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return events;
    }


    public long addBooking(int userId, int eventId, float bookingPrice, int slotNumber) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        // Prepare the booking data
        values.put("userId", userId);
        values.put("eventId", eventId);
        values.put("bookingprice", bookingPrice);
        values.put("slotNumber", slotNumber);

        // Get the current date in the required format
        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        values.put("bookingdate", currentDate);

        // Insert the booking data into the database
        long bookingId = db.insert("booking", null, values);

        db.close(); // Close the database connection
        return bookingId; // Return the ID of the newly inserted booking
    }

    public User getUserDetails(int userId) {
        User user = null;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM user WHERE UserId = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});

        if (cursor.moveToFirst()) {
            String email = cursor.getString(cursor.getColumnIndexOrThrow("email"));
            String password = cursor.getString(cursor.getColumnIndexOrThrow("password"));
            String username = cursor.getString(cursor.getColumnIndexOrThrow("username"));
            String ic = cursor.getString(cursor.getColumnIndexOrThrow("ic"));
            String role = cursor.getString(cursor.getColumnIndexOrThrow("role"));

            // Create a User object and set the values
            user = new User(email, password, username, ic, role);
        }

        cursor.close();
        db.close();
        return user;
    }

    public List<UserAdmin> getAllUsers() {
        List<UserAdmin> userList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM user";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int userId = cursor.getInt(cursor.getColumnIndexOrThrow("UserId"));
                String email = cursor.getString(cursor.getColumnIndexOrThrow("email"));
                String password = cursor.getString(cursor.getColumnIndexOrThrow("password"));
                String username = cursor.getString(cursor.getColumnIndexOrThrow("username"));
                String ic = cursor.getString(cursor.getColumnIndexOrThrow("ic"));
                String role = cursor.getString(cursor.getColumnIndexOrThrow("role"));

                // Add each user to the list
                userList.add(new UserAdmin(userId, username, password, email, ic, role));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return userList;
    }

    public boolean deleteUser(int userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete("user", "UserId = ?", new String[]{String.valueOf(userId)});
        db.close();
        return rowsDeleted > 0;
    }

    public boolean updateUserDetails(int userId, String name, String password, String email, String ic) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        // Insert the updated user data
        contentValues.put("username", name);
        contentValues.put("password", password);
        contentValues.put("email", email);
        contentValues.put("ic", ic);

        // Update the user details where UserId matches
        int rowsUpdated = db.update("user", contentValues, "UserId = ?", new String[]{String.valueOf(userId)});

        db.close();

        // Return true if the update was successful (at least one row updated)
        return rowsUpdated > 0;
    }

    public Event getEventDetailsByBookingId(int bookingId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Event event = null;

        // Query to join the booking, event, and eventType tables to get event details
        String query = "SELECT e.eventId, e.eventname, e.eventdate, e.eventprice, et.eventtypename, e.eventImage " +
                "FROM booking b " +
                "JOIN event e ON b.eventId = e.eventId " +
                "JOIN eventType et ON e.eventtypeId = et.id " +
                "WHERE b.bookingId = ?";


        // Execute the query
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(bookingId)});

        if (cursor.moveToFirst()) {
            int eventId = cursor.getInt(cursor.getColumnIndexOrThrow("eventId"));
            String eventName = cursor.getString(cursor.getColumnIndexOrThrow("eventname"));
            String eventDate = cursor.getString(cursor.getColumnIndexOrThrow("eventdate"));
            double eventPrice = cursor.getDouble(cursor.getColumnIndexOrThrow("eventprice"));
            String eventTypeName = cursor.getString(cursor.getColumnIndexOrThrow("eventtypename"));
            String eventImage = cursor.getString(cursor.getColumnIndexOrThrow("eventImage"));

            // Create the Event object and set the retrieved details
            event = new Event(eventId, eventName, eventDate, eventTypeName, eventPrice, eventImage);
        }

        cursor.close();
        db.close();
        return event; // Return the event details
    }

    public List<Booking> getBookingHistory(int userId) {
        List<Booking> bookings = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT b.bookingId, e.eventname, et.eventtypename, e.eventprice, b.bookingdate, b.bookingprice, b.slotNumber, e.eventImage " +
                "FROM booking b " +
                "JOIN event e ON b.eventId = e.eventId " +
                "JOIN eventType et ON e.eventtypeId = et.id " +
                "WHERE b.userId = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});

        if (cursor.moveToFirst()) {
            do {
                int bookingId = cursor.getInt(cursor.getColumnIndexOrThrow("bookingId"));
                String eventName = cursor.getString(cursor.getColumnIndexOrThrow("eventname"));
                String eventCategory = cursor.getString(cursor.getColumnIndexOrThrow("eventtypename"));
                double eventPrice = cursor.getDouble(cursor.getColumnIndexOrThrow("eventprice"));
                String bookingDate = cursor.getString(cursor.getColumnIndexOrThrow("bookingdate"));
                double bookingPrice = cursor.getDouble(cursor.getColumnIndexOrThrow("bookingprice"));
                int bookingSlot = cursor.getInt(cursor.getColumnIndexOrThrow("slotNumber"));
                String eventImage = cursor.getString(cursor.getColumnIndexOrThrow("eventImage"));

                bookings.add(new Booking(bookingId, eventName, eventCategory, eventPrice, bookingDate, bookingPrice, bookingSlot, eventImage));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return bookings;
    }

    // New method to fetch all events in the database
    public List<Event> fetchAllEvents() {
        List<Event> events = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM event"; // No filter, fetch all events
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int id = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("eventId")));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("eventname"));
                String date = cursor.getString(cursor.getColumnIndexOrThrow("eventdate"));
                double price = cursor.getDouble(cursor.getColumnIndexOrThrow("eventprice"));
                String eventtypename = cursor.getString(cursor.getColumnIndexOrThrow("eventtypename"));
                String eventImage = cursor.getString(cursor.getColumnIndexOrThrow("eventImage"));

                events.add(new Event(id, name, date, eventtypename, price, eventImage));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return events;
    }

    public boolean deleteEvent(int eventId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete("event", "eventId = ?", new String[]{String.valueOf(eventId)});
        db.close();
        return rowsDeleted > 0;
    }






}