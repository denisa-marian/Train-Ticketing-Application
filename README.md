# Train Ticketing Application

## Description

Train Ticketing Application is a Java application that simulates a train ticket reservation system. The application works with trains, stations, routes, and customers, allowing users to book tickets, search for routes between stations, and manage trains and routes.

The purpose of the application is to demonstrate the main functionalities of a railway ticketing system using separate Java classes for data modeling and application logic.

The application runs in the console, and confirmation messages, error messages, and notifications are displayed directly in the terminal. Email sending is simulated by printing the email content to the console.

## Main Functionalities

The application implements the following features:

- booking one or multiple tickets for a train;
- sending a booking confirmation to the customer;
<img width="1482" height="1244" alt="Screenshot2" src="https://github.com/user-attachments/assets/41e74635-3487-436d-be68-ac2edc11b1b4" />
<img width="1190" height="618" alt="Screenshot3" src="https://github.com/user-attachments/assets/296a9176-9238-4c52-966e-d32e37028a75" />


- checking train capacity in order to prevent overbooking;
 <img width="1650" height="912" alt="Screenshot10" src="https://github.com/user-attachments/assets/16efee61-1676-4a19-9541-cb648a42afc8" />

- searching for possible routes between two stations;
- identifying direct routes or routes that require changing trains;
-  <img width="1730" height="1354" alt="Screenshot1" src="https://github.com/user-attachments/assets/f1230b4e-2961-46ee-8705-df202bd8f6f8" />

- displaying an appropriate message when no route is available;
- <img width="896" height="766" alt="Screenshot18" src="https://github.com/user-attachments/assets/18bf2459-faad-4bd2-85a8-cd5d9ce269f6" />

- administrator menu
- <img width="790" height="1208" alt="Screenshot4" src="https://github.com/user-attachments/assets/2f417c24-5123-4d1b-8608-602b7e465e86" />

- adding, removing, and modifying trains by an administrator;
- <img width="788" height="748" alt="Screenshot5" src="https://github.com/user-attachments/assets/0331ab3f-a2f8-44e9-a8b2-b48bf613101e" />
<img width="1378" height="936" alt="Screenshot6" src="https://github.com/user-attachments/assets/43600b22-d8b2-4824-9ebf-e9dec00f04b7" />

<img width="766" height="752" alt="Screenshot7" src="https://github.com/user-attachments/assets/e431f3a6-434d-46fc-ba00-ed3f928b15b8" />
<img width="1412" height="936" alt="Screenshot8" src="https://github.com/user-attachments/assets/5e485f05-bc1c-41c7-ac27-790b9fd6e1f4" />


<img width="730" height="714" alt="Screenshot9" src="https://github.com/user-attachments/assets/bb6d8d14-b674-44da-ad32-a1152b872773" />
<img width="1650" height="912" alt="Screenshot10" src="https://github.com/user-attachments/assets/540ab705-0e32-40e6-9d6f-04ce12763b5e" />


- adding, removing, and modifying routes;
- <img width="1422" height="1312" alt="Screenshot13" src="https://github.com/user-attachments/assets/bf0746cb-6995-4098-bf62-9677d92f401a" />


<img width="1442" height="1392" alt="Screenshot14" src="https://github.com/user-attachments/assets/5b2837b1-f41a-4d6d-9b2b-9beec5fbb55b" />
<img width="1386" height="906" alt="Screenshot15" src="https://github.com/user-attachments/assets/cde4faee-750c-4dee-8e86-767371a939c5" />


<img width="1432" height="1276" alt="Screenshot16" src="https://github.com/user-attachments/assets/0a9da03a-9b2b-4a92-adf4-8567bf7be1a5" />
<img width="1422" height="864" alt="Screenshot17" src="https://github.com/user-attachments/assets/c0bc2f02-683f-49a0-8dfb-b6c1617a4f60" />

- displaying the bookings made for a specific train;
- <img width="722" height="760" alt="Screenshot11" src="https://github.com/user-attachments/assets/80c638fd-a8e7-42b9-a406-7c15fa7138e9" />

- reporting train delays and notifying affected customers.
- <img width="1278" height="1082" alt="Screenshot12" src="https://github.com/user-attachments/assets/16276f29-f3fc-4bf1-825d-438b4c0f033a" />


## Project Structure

The project is divided into two main packages: `model` and `service`.

# Class Descriptions

# ConsoleApplication

This is the main class of the application. In this class, the objects required for testing the application functionalities are created, such as stations, trains, customers, routes, and services.

This class also calls the methods for booking tickets, searching for routes, managing trains, and reporting delays.

## Model package

# Booking

The Booking class represents a reservation made by a customer for a specific train. It contains information about the customer, the selected train, and the number of booked tickets.

This class is used by BookingManager to store all completed bookings.

# Customer

The Customer class represents the customer who makes a booking. It contains the customer’s name and email address.

The email address is used to simulate sending booking confirmations and delay notifications.

# RouteSegment

The RouteSegment class represents a route segment between two stations. A segment contains the departure station, arrival station, departure time, arrival time, and the train that operates on that segment.

This class makes it possible to build both direct routes and routes made up of multiple segments.

# Station

The Station class represents a train station. Each station has a name.

The class overrides the equals() and hashCode() methods so that two stations with the same name are considered equal. This is important when the application searches for routes between stations.

# Train

The Train class represents a train. It contains the train ID and the total seating capacity.

The capacity is used during ticket booking to check whether there are enough available seats.

## Model package

# BookingManager

The BookingManager class manages ticket bookings. It stores a list of all bookings and checks the number of available seats before accepting a new booking.

If the train has enough available seats, the booking is added and a confirmation message is sent to the customer. If there are not enough available seats, the booking is rejected.

# ScheduleManager

The ScheduleManager class manages route segments. It stores the list of all available route segments and allows searching for possible routes between two stations.

Route searching is implemented using a DFS traversal, which allows the application to find both direct routes and routes that require changing trains.

# NotificationService

The NotificationService class simulates sending emails. Instead of sending real emails, the application prints the email message to the console.

This class is used for booking confirmations and delay notifications.

# AdminService

The AdminService class contains the functionalities available to the administrator. Using this class, the administrator can add, remove, and modify trains or routes.

The administrator can also display the bookings made for a specific train and report delays. When a delay is reported, customers who have bookings for that train are notified.
