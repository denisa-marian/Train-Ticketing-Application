package com.example.trainticketingapp.model;

import java.time.LocalDateTime;

public class RouteSegment {
    private Station departureStation;
    private Station arrivalStation;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private Train train;

    public RouteSegment(Station departureStation, Station arrivalStation, LocalDateTime departureTime, LocalDateTime arrivalTime, Train train) {
        this.departureStation = departureStation;
        this.arrivalStation = arrivalStation;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.train = train;
    }

    public Station getDepartureStation() {
        return departureStation;
    }

    public Station getArrivalStation() {
        return arrivalStation;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public Train getTrain() {
        return train;
    }
}
