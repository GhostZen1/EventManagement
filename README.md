# Event Management Mobile Application

## Introduction
This **Event Management App** is a mobile application designed to simplify online event booking. Users can browse, filter, and book tickets for various events such as concerts, fun runs, charity programs, and educational seminars. The application provides seamless booking experiences, generates receipts upon successful booking, and allows users to view their booking history. Administrators can manage event categories, add new events, and monitor user bookings.

## Features
### User Features:
- **Browse & Filter Events** – Users can view and filter events based on different categories.
- **Book Event Tickets** – Users can book tickets for events via an **API Gateway**.
- **Receive Booking Receipt** – After successful booking, users receive an automated receipt generated through the **ToyyibPay API**.
- **Booking History** – Users can view their past event bookings.

### Admin Features:
- **Manage Event Categories** – Admins can add, edit, or delete event categories.
- **Create & Manage Events** – Admins can add new events and update event details.
- **Monitor User Bookings** – Admins can view a list of bookings made by users.

## Functionalities
- **Shared Preferences** – Used to store user login details for a seamless experience.
- **SQLite Database** – Stores event details, categories, and user booking history locally.
- **Threads** – Implements background processing to display available events smoothly.
- **Async Background Task** – Generates a **receipt** for bookings via **ToyyibPay API** asynchronously.

## Installation Dependencies
Installation dependencies are the necessary tools, libraries, and SDKs required to run the application. Below are the dependencies needed for this project:

### Android Development Environment:
1. **Android Studio** – The official IDE for Android development.
2. **Java/Kotlin SDK** – Required to compile and run Android applications.
3. **Gradle** – A build automation tool used in Android development.

### Required Dependencies:
Include the following dependencies in the `build.gradle` file:
```gradle
dependencies {
    implementation 'androidx.appcompat:appcompat:1.3.1'
    implementation 'com.google.android.material:material:1.4.0'
    implementation 'androidx.recyclerview:recyclerview:1.2.1'
    implementation 'androidx.lifecycle:lifecycle-extensions:2.2.0'
    implementation 'com.squareup.retrofit2:retrofit:2.9.0' // API communication
    implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
    implementation 'com.squareup.okhttp3:okhttp:4.9.1'
    implementation 'com.github.toyyibPay:android-sdk:1.0.0' // Payment Gateway
}
```

## How to Run the App
1. Clone the repository:
   ```sh
   git clone https://github.com/GhostZen1/EventManagement.git
   ```
2. Open the project in **Android Studio**.
3. Sync Gradle to install dependencies.
4. Configure an **Android Virtual Device (AVD)** or connect a physical Android device.
5. Build and run the application.

## API Integration
The application integrates **ToyyibPay API** for payment processing. The API is used to generate receipts for users after booking. The integration follows this workflow:
1. The user selects an event and confirms booking.
2. The app sends a request to ToyyibPay API to process the payment.
3. Once payment is successful, a receipt is generated asynchronously.
4. The receipt is displayed in the user’s booking history.

## Database Structure (SQLite)
| Table | Columns |
|--------|---------|
| `users` | UserId, email, password, username,ic,role |
| `eventType` | id, eventtypename, eventtypeImage |
| `event` | eventId, eventtypeId, eventtypename, eventname,eventdate,eventprice,eventImage |
|`booking` | userId,eventId,bookingdate,bookingprice,slotNumber|

## Future Enhancements
- Implement **Firebase Authentication** for user login.
- Add **push notifications** for event updates.
- Introduce **QR code tickets** for event entry validation.

## Images
![image](https://github.com/user-attachments/assets/89eb900c-1f1e-4661-85e7-a6e167595119)
![image](https://github.com/user-attachments/assets/3cb88858-d18b-4aa8-a011-aba2731f2475)
![image](https://github.com/user-attachments/assets/2219696f-8f23-4cf1-a36d-ac49c0525870)
![image](https://github.com/user-attachments/assets/1047fff9-200d-46b6-9773-12e1171605c0)


