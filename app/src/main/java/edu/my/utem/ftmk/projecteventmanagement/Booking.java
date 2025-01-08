package edu.my.utem.ftmk.projecteventmanagement;

public class Booking {
    public String EventName, EventCategory, EventPrice, BookingDate, BookingPrice;

    public Booking(String eventName, String eventCategory, String eventPrice, String bookingDate, String bookingPrice) {
        EventName = eventName;
        EventCategory = eventCategory;
        EventPrice = eventPrice;
        BookingDate = bookingDate;
        BookingPrice = bookingPrice;
    }

    public String getEventName() {
        return EventName;
    }

    public String getEventCategory() {
        return EventCategory;
    }

    public String getEventPrice() {
        return EventPrice;
    }

    public String getBookingDate() {
        return BookingDate;
    }

    public String getBookingPrice() {
        return BookingPrice;
    }
}
