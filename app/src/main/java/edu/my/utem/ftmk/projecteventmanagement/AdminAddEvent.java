package edu.my.utem.ftmk.projecteventmanagement;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

public class AdminAddEvent extends AppCompatActivity {

    private EditText etEventName, etDate, etEventPrice;
    private Spinner spinnerEventType;
    private Button btnSubmit, btnUploadImage;
    private TextView tvFileName; // TextView to display the selected file name
    private Uri selectedImageUri;
    private SqLite dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_add_event);

        etEventName = findViewById(R.id.etEventName);
        etDate = findViewById(R.id.etDate);
        etEventPrice = findViewById(R.id.txtIc);
        spinnerEventType = findViewById(R.id.spinnerEventType);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnUploadImage = findViewById(R.id.btnUploadImage);
        tvFileName = findViewById(R.id.tvFileName); // Initialize TextView for file name display
        dbHelper = new SqLite(this);

        // Set DatePicker on the Event Date EditText
        etDate.setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(AdminAddEvent.this,
                    (view, selectedYear, selectedMonth, selectedDay) -> {
                        String date = selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDay;
                        etDate.setText(date);
                    }, year, month, dayOfMonth);
            datePickerDialog.show();
        });

        // Fetch event types and populate the Spinner
        List<EventType> eventTypes = dbHelper.getEventCategories();
        ArrayAdapter<EventType> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, eventTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEventType.setAdapter(adapter);

        // Set up Submit button
        btnSubmit.setOnClickListener(v -> {
            String eventName = etEventName.getText().toString().trim();
            String eventDate = etDate.getText().toString().trim();
            String eventPrice = etEventPrice.getText().toString().trim();
            EventType selectedEventType = (EventType) spinnerEventType.getSelectedItem();

            if (eventName.isEmpty() || eventDate.isEmpty() || eventPrice.isEmpty() || selectedEventType == null || selectedImageUri == null) {
                Toast.makeText(AdminAddEvent.this, "Please fill in all fields and upload an image", Toast.LENGTH_SHORT).show();
            } else {
                // Insert the event into the database
                addEvent(eventName, eventDate, eventPrice, selectedEventType);
            }
        });

        // Set up Image upload button
        btnUploadImage.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*"); // Filter for images only
            startActivityForResult(intent, 1); // Request code 1
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            String fileName = getFileNameFromUri(selectedImageUri); // Get file name
            String filePath = getPathFromUri(selectedImageUri);
            File selectedFile = new File(filePath);

            if (selectedFile.exists() && selectedFile.isFile()) {
                String extension = getFileExtension(selectedFile);
                if (extension.equals("jpg") || extension.equals("jpeg") || extension.equals("png")) {
                    tvFileName.setText("Selected File: " + fileName); // Display the file name
                    Toast.makeText(this, "Image selected: " + fileName, Toast.LENGTH_SHORT).show();
                    saveImageToInternalStorage(selectedFile, fileName); // Save image with correct file name
                } else {
                    Toast.makeText(this, "Please select an image file (jpg, jpeg, png)", Toast.LENGTH_SHORT).show();
                    selectedImageUri = null; // Reset image selection
                    tvFileName.setText("Selected File: None"); // Clear file name display
                }
            }
        }
    }

    private String getPathFromUri(Uri uri) {
        String path = null;
        String[] projection = {MediaStore.Images.Media.DATA};
        try (Cursor cursor = getContentResolver().query(uri, projection, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                path = cursor.getString(columnIndex);
            }
        }
        return path;
    }

    private String getFileExtension(File file) {
        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex > 0) {
            return fileName.substring(dotIndex + 1).toLowerCase();
        }
        return "";
    }

    private String getFileNameFromUri(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            try (Cursor cursor = getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    int nameIndex = cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME);
                    if (nameIndex != -1) {
                        result = cursor.getString(nameIndex);
                    }
                }
            }
        }
        if (result == null) {
            result = uri.getLastPathSegment();
        }
        return result;
    }

    private void saveImageToInternalStorage(File sourceFile, String fileName) {
        try {
            File imagesDirectory = new File(getApplicationContext().getFilesDir(), "images");
            if (!imagesDirectory.exists()) {
                imagesDirectory.mkdirs();
            }

            File destinationFile = new File(imagesDirectory, fileName);

            try (FileInputStream fis = new FileInputStream(sourceFile);
                 FileOutputStream fos = new FileOutputStream(destinationFile)) {
                byte[] buffer = new byte[1024];
                int length;
                while ((length = fis.read(buffer)) != -1) {
                    fos.write(buffer, 0, length);
                }
                fos.flush();
                Log.d("Image", "Image saved as " + destinationFile.getAbsolutePath());
                Toast.makeText(this, "Image saved to: " + destinationFile.getAbsolutePath(), Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error saving image: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void addEvent(String eventName, String eventDate, String eventPrice, EventType eventType) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            double price = Double.parseDouble(eventPrice);
            String imageName = getFileNameFromUri(selectedImageUri);

            String insertQuery = "INSERT INTO event (eventtypeId, eventtypename, eventname, eventdate, eventprice, eventImage) VALUES (?, ?, ?, ?, ?, ?)";
            db.execSQL(insertQuery, new Object[]{
                    eventType.getId(),
                    eventType.getName(),
                    eventName,
                    eventDate,
                    price,
                    imageName
            });
            db.setTransactionSuccessful();
            Toast.makeText(this, "Event added successfully", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(AdminAddEvent.this, AdminManageEvent.class));
        } catch (Exception e) {
            Toast.makeText(this, "Error adding event: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            db.endTransaction();
            db.close();
        }
    }
}
