package edu.my.utem.ftmk.projecteventmanagement;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class UserReceipt extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_receipt);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent); // Update intent
        handleIncomingIntent(intent); // Handle new intent
    }

    private void handleIncomingIntent(Intent intent) {
        Uri data = intent.getData();
        if (data != null && data.getScheme().equals("yourapp") && data.getHost().equals("payment-complete")) {
            // Process the payment result (e.g., confirm the payment, show receipt)
            String paymentStatus = data.getQueryParameter("status"); // Retrieve the payment status
            Log.d("FPX PAYMENT STATUS", "FPX PAYMENT STATUS: " + paymentStatus);

        }
    }
}
