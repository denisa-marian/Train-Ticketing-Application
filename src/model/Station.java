package com.example.trainticketingapp.model;

import java.util.Objects;

public class Station {
    private String name;

    public Station(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Station station = (Station) obj;
        return Objects.equals(name, station.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
