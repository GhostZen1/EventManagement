package edu.my.utem.ftmk.projecteventmanagement;

public class Booking2 {
    private int bookingId;
    private int userId;
    private int eventId;
    private String bookingDate;
    private double bookingPrice;
    private int slotNumber;

    public Booking2(int bookingId, int userId, int eventId, String bookingDate, double bookingPrice, int slotNumber) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.eventId = eventId;
        this.bookingDate = bookingDate;
        this.bookingPrice = bookingPrice;
        this.slotNumber = slotNumber;
    }

    public int getBookingId() {
        return bookingId;
    }

    public int getUserId() {
        return userId;
    }

    public int getEventId() {
        return eventId;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public double getBookingPrice() {
        return bookingPrice;
    }

    public int getSlotNumber() {
        return slotNumber;
    }
}
