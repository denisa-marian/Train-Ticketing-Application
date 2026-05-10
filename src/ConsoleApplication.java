package com.example.trainticketingapp;

import com.example.trainticketingapp.model.Customer;
import com.example.trainticketingapp.model.RouteSegment;
import com.example.trainticketingapp.model.Station;
import com.example.trainticketingapp.model.Train;
import com.example.trainticketingapp.service.AdminService;
import com.example.trainticketingapp.service.BookingManager;
import com.example.trainticketingapp.service.ScheduleManager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class ConsoleApplication {

    private static final Scanner scanner = new Scanner(System.in);

    private static final BookingManager bookingManager = new BookingManager();
    private static final ScheduleManager scheduleManager = new ScheduleManager();
    private static final AdminService adminService = new AdminService(bookingManager, scheduleManager);

    public static void main(String[] args) {
        setupDemoData();

        boolean running = true;

        while (running) {
            printMainMenu();

            String option = scanner.nextLine();

            switch (option) {
                case "1" -> searchRoutes();
                case "2" -> bookTickets();
                case "3" -> adminMenu();
                case "0" -> {
                    running = false;
                    System.out.println("Application closed.");
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void printMainMenu() {;
        System.out.println("\n        TRAIN TICKETING APP");
        printAvailableStations();

        System.out.println("\n1. Find routes between stations");
        System.out.println("2. Book tickets");
        System.out.println("3. Administrator menu");
        System.out.println("0. Exit");
        System.out.print("Choose option: ");
    }

    private static void printAvailableStations() {
        java.util.Set<String> stations = new java.util.TreeSet<>();

        for (RouteSegment segment : scheduleManager.getAllSegments()) {
            stations.add(segment.getDepartureStation().getName());
            stations.add(segment.getArrivalStation().getName());
        }

        System.out.println("Available stations:");
        for (String station : stations) {
            System.out.println("- " + station);
        }
    }

    private static void adminMenu() {
        boolean adminRunning = true;

        while (adminRunning) {
            System.out.println("\n        ADMIN MENU ");
            System.out.println("1. Add train");
            System.out.println("2. Remove train");
            System.out.println("3. Modify train capacity");
            System.out.println("4. Add route");
            System.out.println("5. Remove route");
            System.out.println("6. Modify route");
            System.out.println("7. Show bookings for train");
            System.out.println("8. Report train delay");
            System.out.println("9. Show all trains");
            System.out.println("10. Show all routes");
            System.out.println("0. Back");
            System.out.print("Choose option: ");

            String option = scanner.nextLine();

            switch (option) {
                case "1" -> addTrain();
                case "2" -> removeTrain();
                case "3" -> modifyTrain();
                case "4" -> addRoute();
                case "5" -> removeRoute();
                case "6" -> modifyRoute();
                case "7" -> showBookingsForTrain();
                case "8" -> reportDelay();
                case "9" -> showAllTrains();
                case "10" -> showAllRoutes();
                case "0" -> adminRunning = false;
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private static void searchRoutes() {
        System.out.print("Departure station: ");
        String departure = scanner.nextLine();

        System.out.print("Arrival station: ");
        String arrival = scanner.nextLine();

        List<List<RouteSegment>> routes = scheduleManager.findRoutes(
                new Station(departure),
                new Station(arrival)
        );

        if (routes.isEmpty()) {
            System.out.println("No possible route found between " + departure + " and " + arrival + ".");
            return;
        }

        System.out.println("\nAvailable routes:");
        printRoutes(routes);
    }

    private static void bookTickets() {
        System.out.print("Departure station: ");
        String departure = scanner.nextLine();

        System.out.print("Arrival station: ");
        String arrival = scanner.nextLine();

        List<List<RouteSegment>> routes = scheduleManager.findRoutes(
                new Station(departure),
                new Station(arrival)
        );

        if (routes.isEmpty()) {
            System.out.println("No possible route found. Booking cannot be made.");
            return;
        }

        System.out.println("\nAvailable routes:");
        printRoutes(routes);

        System.out.print("Choose route number: ");
        int routeIndex = readInt() - 1;

        if (routeIndex < 0 || routeIndex >= routes.size()) {
            System.out.println("Invalid route number.");
            return;
        }

        RouteSegment selectedSegment = routes.get(routeIndex).get(0);
        Train selectedTrain = selectedSegment.getTrain();

        System.out.print("Customer name: ");
        String customerName = scanner.nextLine();

        System.out.print("Customer email: ");
        String customerEmail = scanner.nextLine();

        System.out.print("Number of tickets: ");
        int tickets = readInt();

        Customer customer = new Customer(customerName, customerEmail);

        boolean success = bookingManager.bookTicket(customer, selectedTrain, tickets);

        if (success) {
            System.out.println("Booking completed successfully.");
        } else {
            System.out.println("Booking failed. Not enough available seats.");
        }
    }

    private static void addTrain() {
        System.out.print("Train ID: ");
        String trainId = scanner.nextLine();

        System.out.print("Total capacity: ");
        int capacity = readInt();

        adminService.addTrain(new Train(trainId, capacity));
    }

    private static void removeTrain() {
        System.out.print("Train ID to remove: ");
        String trainId = scanner.nextLine();

        adminService.removeTrain(trainId);
    }

    private static void modifyTrain() {
        System.out.print("Train ID to modify: ");
        String trainId = scanner.nextLine();

        System.out.print("New capacity: ");
        int capacity = readInt();

        adminService.modifyTrain(trainId, capacity);
    }

    private static void addRoute() {
        System.out.print("Departure station: ");
        String departure = scanner.nextLine();

        System.out.print("Arrival station: ");
        String arrival = scanner.nextLine();

        System.out.print("Departure time yyyy-MM-dd HH:mm: ");
        LocalDateTime departureTime = readDateTime();

        System.out.print("Arrival time yyyy-MM-dd HH:mm: ");
        LocalDateTime arrivalTime = readDateTime();

        System.out.print("Train ID: ");
        String trainId = scanner.nextLine();

        System.out.print("Train capacity: ");
        int capacity = readInt();

        Train train = new Train(trainId, capacity);

        RouteSegment segment = new RouteSegment(
                new Station(departure),
                new Station(arrival),
                departureTime,
                arrivalTime,
                train
        );

        adminService.addTrain(train);
        adminService.addRoute(segment);
    }

    private static void removeRoute() {
        System.out.println("Existing routes:");
        showAllRoutes();

        System.out.println("\nTo remove a route, enter its exact details.");

        System.out.print("Departure station: ");
        String departure = scanner.nextLine();

        System.out.print("Arrival station: ");
        String arrival = scanner.nextLine();

        System.out.print("Departure time yyyy-MM-dd HH:mm: ");
        LocalDateTime departureTime = readDateTime();

        System.out.print("Arrival time yyyy-MM-dd HH:mm: ");
        LocalDateTime arrivalTime = readDateTime();

        System.out.print("Train ID: ");
        String trainId = scanner.nextLine();

        RouteSegment foundSegment = findRouteSegment(
                departure,
                arrival,
                departureTime,
                arrivalTime,
                trainId
        );

        if (foundSegment == null) {
            System.out.println("Route not found.");
        } else {
            adminService.removeRoute(foundSegment);
        }
    }

    private static void modifyRoute() {
        System.out.println("Existing routes:");
        showAllRoutes();

        System.out.println("\nEnter the details of the route you want to modify.");

        System.out.print("Current departure station: ");
        String oldDeparture = scanner.nextLine();

        System.out.print("Current arrival station: ");
        String oldArrival = scanner.nextLine();

        System.out.print("Current departure time yyyy-MM-dd HH:mm: ");
        LocalDateTime oldDepartureTime = readDateTime();

        System.out.print("Current arrival time yyyy-MM-dd HH:mm: ");
        LocalDateTime oldArrivalTime = readDateTime();

        System.out.print("Current train ID: ");
        String oldTrainId = scanner.nextLine();

        RouteSegment oldSegment = findRouteSegment(
                oldDeparture,
                oldArrival,
                oldDepartureTime,
                oldArrivalTime,
                oldTrainId
        );

        if (oldSegment == null) {
            System.out.println("Route not found.");
            return;
        }

        System.out.println("\nEnter the new route details.");

        System.out.print("New departure station: ");
        String newDeparture = scanner.nextLine();

        System.out.print("New arrival station: ");
        String newArrival = scanner.nextLine();

        System.out.print("New departure time yyyy-MM-dd HH:mm: ");
        LocalDateTime newDepartureTime = readDateTime();

        System.out.print("New arrival time yyyy-MM-dd HH:mm: ");
        LocalDateTime newArrivalTime = readDateTime();

        System.out.print("New train ID: ");
        String newTrainId = scanner.nextLine();

        System.out.print("New train capacity: ");
        int newCapacity = readInt();

        Train newTrain = new Train(newTrainId, newCapacity);
        adminService.addTrain(newTrain);

        RouteSegment newSegment = new RouteSegment(
                new Station(newDeparture),
                new Station(newArrival),
                newDepartureTime,
                newArrivalTime,
                newTrain
        );

        adminService.modifyRoute(oldSegment, newSegment);
    }

    private static RouteSegment findRouteSegment(
            String departure,
            String arrival,
            LocalDateTime departureTime,
            LocalDateTime arrivalTime,
            String trainId
    ) {
        for (RouteSegment segment : scheduleManager.getAllSegments()) {
            boolean sameRoute =
                    segment.getDepartureStation().equals(new Station(departure))
                            && segment.getArrivalStation().equals(new Station(arrival))
                            && segment.getDepartureTime().equals(departureTime)
                            && segment.getArrivalTime().equals(arrivalTime)
                            && segment.getTrain().getId().equalsIgnoreCase(trainId);

            if (sameRoute) {
                return segment;
            }
        }

        return null;
    }

    private static void showBookingsForTrain() {
        System.out.print("Train ID: ");
        String trainId = scanner.nextLine();

        adminService.showBookingsForTrain(trainId);
    }

    private static void reportDelay() {
        System.out.print("Train ID: ");
        String trainId = scanner.nextLine();

        System.out.print("Delay in minutes: ");
        int delayMinutes = readInt();

        adminService.reportDelay(trainId, delayMinutes);
    }

    private static void showAllTrains() {
        if (adminService.getTrains().isEmpty()) {
            System.out.println("No trains available.");
            return;
        }

        System.out.println("\nAvailable trains:");

        for (Train train : adminService.getTrains()) {
            System.out.println("- Train ID: " + train.getId()
                    + " | Capacity: " + train.getTotalCapacity()
                    + " | Booked seats: " + bookingManager.getBookedSeatsForTrain(train)
                    + " | Available seats: " + (train.getTotalCapacity() - bookingManager.getBookedSeatsForTrain(train)));
        }
    }

    private static void showAllRoutes() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        if (scheduleManager.getAllSegments().isEmpty()) {
            System.out.println("No routes available.");
            return;
        }

        int index = 1;

        for (RouteSegment segment : scheduleManager.getAllSegments()) {
            System.out.println(index + ". "
                    + segment.getDepartureStation().getName()
                    + " -> "
                    + segment.getArrivalStation().getName()
                    + " | Train: "
                    + segment.getTrain().getId()
                    + " | "
                    + segment.getDepartureTime().format(formatter)
                    + " - "
                    + segment.getArrivalTime().format(formatter));
            index++;
        }
    }

    private static void printRoutes(List<List<RouteSegment>> routes) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        for (int i = 0; i < routes.size(); i++) {
            List<RouteSegment> route = routes.get(i);

            System.out.println("\nRoute " + (i + 1) + ":");

            for (RouteSegment segment : route) {
                System.out.println("  "
                        + segment.getDepartureStation().getName()
                        + " -> "
                        + segment.getArrivalStation().getName()
                        + " | Train: "
                        + segment.getTrain().getId()
                        + " | "
                        + segment.getDepartureTime().format(formatter)
                        + " - "
                        + segment.getArrivalTime().format(formatter));
            }

            if (route.size() == 1) {
                System.out.println("  Type: Direct route");
            } else {
                System.out.println("  Type: Route with " + (route.size() - 1) + " change(s)");
            }
        }
    }

    private static int readInt() {
        while (true) {
            try {
                int value = Integer.parseInt(scanner.nextLine());
                return value;
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid number: ");
            }
        }
    }

    private static LocalDateTime readDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        while (true) {
            try {
                return LocalDateTime.parse(scanner.nextLine(), formatter);
            } catch (Exception e) {
                System.out.print("Invalid date format. Use yyyy-MM-dd HH:mm: ");
            }
        }
    }

    private static void setupDemoData() {
        Train t1 = new Train("IR-1621", 120);
        Train t2 = new Train("R-3005", 80);
        Train t3 = new Train("IC-531", 140);
        Train t4 = new Train("IR-1745", 100);

        adminService.addTrain(t1);
        adminService.addTrain(t2);
        adminService.addTrain(t3);
        adminService.addTrain(t4);

        Station bucharest = new Station("Bucharest");
        Station ploiesti = new Station("Ploiesti");
        Station brasov = new Station("Brasov");
        Station sibiu = new Station("Sibiu");
        Station cluj = new Station("Cluj");

        scheduleManager.addSegments(new RouteSegment(
                bucharest,
                ploiesti,
                LocalDateTime.of(2026, 5, 12, 8, 30),
                LocalDateTime.of(2026, 5, 12, 9, 20),
                t1
        ));

        scheduleManager.addSegments(new RouteSegment(
                ploiesti,
                brasov,
                LocalDateTime.of(2026, 5, 12, 9, 45),
                LocalDateTime.of(2026, 5, 12, 12, 10),
                t1
        ));

        scheduleManager.addSegments(new RouteSegment(
                bucharest,
                brasov,
                LocalDateTime.of(2026, 5, 12, 10, 15),
                LocalDateTime.of(2026, 5, 12, 13, 35),
                t2
        ));

        scheduleManager.addSegments(new RouteSegment(
                brasov,
                sibiu,
                LocalDateTime.of(2026, 5, 12, 14, 10),
                LocalDateTime.of(2026, 5, 12, 16, 45),
                t3
        ));

        scheduleManager.addSegments(new RouteSegment(
                sibiu,
                cluj,
                LocalDateTime.of(2026, 5, 12, 17, 10),
                LocalDateTime.of(2026, 5, 12, 20, 30),
                t4
        ));
    }
}