package com.example.trainticketingapp.service;

import com.example.trainticketingapp.model.RouteSegment;
import com.example.trainticketingapp.model.Station;

import java.util.ArrayList;
import java.util.List;

public class ScheduleManager {
    private List<RouteSegment> allSegments;

    public ScheduleManager() {
        this.allSegments = new ArrayList<>();
    }

    public List<RouteSegment> getAllSegments() {
        return allSegments;
    }

    public void addSegments(RouteSegment segment) {
        allSegments.add(segment);
    }

    public List<List<RouteSegment>> findRoutes(Station departure, Station arrival) {
        List<List<RouteSegment>> possibleRoutes = new ArrayList<>();
        List<RouteSegment> currentPath = new ArrayList<>();

        findRoutessDFS(departure, arrival, currentPath, possibleRoutes);
        return possibleRoutes;
    }

    public void findRoutessDFS(Station current, Station arrival, List<RouteSegment> currentPath, List<List<RouteSegment>> allPaths) {
        if (current.equals(arrival) && !currentPath.isEmpty()) {
            allPaths.add(new ArrayList<>(currentPath));
            return;
        }

        for (RouteSegment segment : allSegments) {
            if (segment.getDepartureStation().equals(current)) {
                boolean isValidTime = true;

                if (!currentPath.isEmpty()){
                    RouteSegment previousSegment = currentPath.get(currentPath.size() - 1);
                    if (segment.getDepartureTime().isBefore(previousSegment.getArrivalTime())) {
                        isValidTime = false;
                    }
                }

                if (isValidTime && !currentPath.contains(segment)) {
                    currentPath.add(segment);
                    findRoutessDFS(segment.getArrivalStation(), arrival, currentPath, allPaths);
                    currentPath.remove(currentPath.size() - 1);
                }
            }
        }
    }
}
