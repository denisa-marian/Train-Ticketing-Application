package com.example.trainticketingapp.service;

import com.example.trainticketingapp.model.Booking;
import com.example.trainticketingapp.model.Customer;
import com.example.trainticketingapp.model.Train;

public class NotificationService {
    public void sendEmail(String toAddress, String subject, String body) {
        System.out.println("AUTOMATED EMAIL");
        System.out.println("Sending email to " + toAddress);
        System.out.println("Subject: " + subject);
        System.out.println(body);
    }

    public void sendBookingConfirmation(Customer customer, Train train, int tickets) {
        String subject = "Booking Confirmation for Train " + train.getId();
        String body = "Dear " + customer.getName() + ",\n" +
                "Your booking has been successfully processed!\n" +
                "You have purchased " + tickets + " seats for train " + train.getId() + ".\n" +
                "Have a great journey!";
        sendEmail(customer.getEmail(), subject, body);
    }

    public void sendDelayNotification(Customer customer, Train train, int delayMinutes) {
        String subject = "Delay Notification for Train " + train.getId();
        String body = "Dear " + customer.getName() + ",\n" +
                "We regret to inform you that train " + train.getId() + " is delayed by " + delayMinutes + " minutes.\n" +
                "We apologize for the inconvenience and thank you for your understanding.";
        sendEmail(customer.getEmail(), subject, body);
    }
}
