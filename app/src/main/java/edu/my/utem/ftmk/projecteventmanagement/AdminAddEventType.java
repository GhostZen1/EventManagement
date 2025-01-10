package edu.my.utem.ftmk.projecteventmanagement;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

public class AdminAddEventType extends AppCompatActivity {
    private EditText etEventTypeName;
    private Button btnSubmit, btnUploadImage;
    private TextView tvFileName;
    private Uri selectedImageUri;
    private SqLite dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_add_event_type);

        etEventTypeName = findViewById(R.id.etEventTypeName);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnUploadImage = findViewById(R.id.btnUploadImage);
        tvFileName = findViewById(R.id.tvFileName); // Initialize TextView for file name display
        dbHelper = new SqLite(this);


        // Set up Submit button
        btnSubmit.setOnClickListener(v -> {
            String eventTypeName = etEventTypeName.getText().toString().trim();

            if (eventTypeName.isEmpty() || selectedImageUri == null) {
                Toast.makeText(AdminAddEventType.this, "Please fill in all fields and upload an image", Toast.LENGTH_SHORT).show();
            } else {
                // Insert the event into the database
                addEventType(eventTypeName, getFileNameFromUri(selectedImageUri));
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

    private void addEventType(String eventTypeName, String imageName) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            String insertQuery = "INSERT INTO eventType (eventtypename, eventtypeImage) VALUES (?, ?)";
            db.execSQL(insertQuery, new Object[]{
                    eventTypeName,
                    imageName
            });
            db.setTransactionSuccessful();
            Toast.makeText(this, "Event type added successfully", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(AdminAddEventType.this, AdminManageEventType.class));
        } catch (Exception e) {
            Toast.makeText(this, "Error adding event type: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            db.endTransaction();
            db.close();
        }
    }
}
