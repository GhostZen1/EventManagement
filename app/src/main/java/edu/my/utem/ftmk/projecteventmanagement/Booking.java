package edu.my.utem.ftmk.projecteventmanagement;

public class Booking {
    private int bookingId;
    private String eventName;
    private String eventCategory;
    private double eventPrice;
    private String bookingDate;
    private double bookingPrice;
    private int bookingSlot;
    private String eventImage;

    public Booking(int bookingId, String eventName, String eventCategory, double eventPrice, String bookingDate, double bookingPrice, int bookingSlot, String eventImage) {
        this.bookingId = bookingId;
        this.eventName = eventName;
        this.eventCategory = eventCategory;
        this.eventPrice = eventPrice;
        this.bookingDate = bookingDate;
        this.bookingPrice = bookingPrice;
        this.bookingSlot = bookingSlot;
        this.eventImage = eventImage;
    }

    public int getBookingId() {
        return bookingId;
    }

    public String getEventName() {
        return eventName;
    }

    public String getEventCategory() {
        return eventCategory;
    }

    public double getEventPrice() {
        return eventPrice;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public double getBookingPrice() {
        return bookingPrice;
    }

    public int getBookingSlot() {
        return bookingSlot;
    }

    public String getEventImage() {
        return eventImage;
    }
}

