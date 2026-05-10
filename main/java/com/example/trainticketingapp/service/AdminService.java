package com.example.trainticketingapp.service;

import com.example.trainticketingapp.model.Booking;
import com.example.trainticketingapp.model.RouteSegment;
import com.example.trainticketingapp.model.Train;

import java.util.ArrayList;
import java.util.List;

public class AdminService {
    private List<Train> trains;
    private BookingManager booking;
    private NotificationService notification;
    private ScheduleManager schedule;

    public AdminService(BookingManager booking, ScheduleManager schedule) {
        this.trains = new ArrayList<>();
        this.booking = booking;
        this.schedule = schedule;
        this.notification = new NotificationService();
    }

    public List<Train> getTrains() {
        return trains;
    }

    public void addTrain(Train train) {
        for (Train t : trains) {
            if (t.getId().equals(train.getId())) {
                System.out.println("Train already exists");
                return;
            }
        }
        trains.add(train);
        System.out.println("Train added successfully");
    }

    public void removeTrain(String trainId) {
        Train removedTrain = null;
        for (Train t : trains) {
            if (t.getId().equals(trainId)) {
                removedTrain = t;
                break;
            }
        }
        if (removedTrain != null) {
            trains.remove(removedTrain);
            System.out.println("Train removed successfully");
        }
        else {
            System.out.println("Train not found");
        }
    }

    public void modifyTrain(String trainId, int newCapacity) {
        for (Train t : trains) {
            if (t.getId().equals(trainId)) {
                t.setTotalCapacity(newCapacity);
                System.out.println("Train modified successfully");
                return;
            }
        }
        System.out.println("Train not found");
    }

    public void addRoute(RouteSegment segment) {
        schedule.addSegments(segment);
        System.out.println("Route added successfully");
    }

    public void removeRoute(RouteSegment segment) {
        boolean removed = schedule.getAllSegments().remove(segment);
        if (removed) {
            System.out.println("Route removed successfully");
        } else {
            System.out.println("Route not found");
        }
    }

    public void modifyRoute(RouteSegment oldSegment, RouteSegment newSegment) {
        if (schedule.getAllSegments().contains(oldSegment)) {
            schedule.getAllSegments().remove(oldSegment);
            schedule.addSegments(newSegment);
            System.out.println("Route modified successfully");
        } else {
            System.out.println("Route not found");
        }
    }

    public void showBookingsForTrain(String trainId) {
        System.out.println("Train ID: " + trainId);
        boolean hasBookings = false;
        for (Booking b : booking.getBookingList()) {
            if (b.getTrain().getId().equals(trainId)) {
                System.out.println("Customer: " + b.getCustomer().getName() + ", Tickets: " + b.getNrOfTickets());
                hasBookings = true;
            }
        }
        if (!hasBookings) {
            System.out.println("No bookings found");
        }
    }

    public boolean reportDelay(String trainId, int delayMinutes) {
        Train delayedTrain = null;
        for (Train t : trains) {
            if (t.getId().equals(trainId)) {
                delayedTrain = t;
                break;
            }
        }

        if (delayedTrain == null) {
            System.out.println("Train not found");
            return false;
        }

        System.out.println("\nDelay of " + delayMinutes + " minutes reported for train " + trainId);

        boolean notifiedCustomers = false;
        for (Booking b : booking.getBookingList()) {
            if (b.getTrain().getId().equals(trainId)) {
                notification.sendDelayNotification(b.getCustomer(), b.getTrain(), delayMinutes);
                notifiedCustomers = true;
            }
        }

        if (!notifiedCustomers) {
            System.out.println("No customers to notify for this train.");
        }
        return true;
    }
}
