package com.example.trainticketingapp.model;

public class Booking {
    private Customer customer;
    private Train train;
    private int nrOfTickets;

    public Booking(Customer customer, Train train, int nrOfTickets) {
        this.customer = customer;
        this.train = train;
        this.nrOfTickets = nrOfTickets;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Train getTrain() {
        return train;
    }

    public int getNrOfTickets() {
        return nrOfTickets;
    }
}
