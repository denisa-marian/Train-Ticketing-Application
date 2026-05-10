package com.example.trainticketingapp.model;

import java.util.Objects;

public class Train {
    private String id;
    private int totalCapacity;

    public Train(String id, int totalCapacity) {
        this.id = id;
        this.totalCapacity = totalCapacity;
    }

    public String getId() {
        return id;
    }

    public int getTotalCapacity() {
        return totalCapacity;
    }

    public void setTotalCapacity(int totalCapacity) {
        this.totalCapacity = totalCapacity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Train train = (Train) o;
        return Objects.equals(id, train.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
