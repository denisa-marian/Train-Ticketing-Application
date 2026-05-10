package com.example.trainticketingapp.service;

import com.example.trainticketingapp.model.Booking;
import com.example.trainticketingapp.model.Customer;
import com.example.trainticketingapp.model.Train;

import java.util.ArrayList;
import java.util.List;

public class BookingManager {
    private List<Booking> bookingList;
    private NotificationService notificationService;

    public BookingManager() {
        this.bookingList = new ArrayList<>();
        this.notificationService = new NotificationService();
    }

    public List<Booking> getBookingList() {
        return bookingList;
    }

    public boolean bookTicket(Customer customer, Train train, int nrOfTickets) {
        int bookedSeats = getBookedSeatsForTrain(train);
        int availableSeats = train.getTotalCapacity() - bookedSeats;

        if (availableSeats >= nrOfTickets) {
            Booking booking = new Booking(customer, train, nrOfTickets);
            bookingList.add(booking);
            System.out.println("Booking successful for " + customer.getName() + " on train " + train.getId() + " for " + nrOfTickets + " tickets.");
            notificationService.sendBookingConfirmation(customer, train, nrOfTickets);
            return true;
        } else {
            System.out.println("Booking failed for " + customer.getName() + " on train " + train.getId() + ". Not enough available seats.");
            return false;
        }
    }

    public int getBookedSeatsForTrain(Train train) {
        int bookedSeats = 0;

        for (Booking booking : bookingList) {
            if (booking.getTrain().equals(train)) {
                bookedSeats += booking.getNrOfTickets();
            }
        }
        return bookedSeats;
    }
}
