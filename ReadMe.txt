BUILT AN HOTEL RESERVATION PROJECT USING JDBC CONNECTOR (mysql-connector-j-8.0.33), MYSQL, AND INTELLIJ IDE.
This Hotel Reservation project contains the following features 
- Reserve a Room 
	You can simply reserve any particular room you want just by imputing your name, room number and contact number.
	then your room will be reserved as expected
- View Reservation
	you can view all the reservations that has been imputed on the database, to impose clarity for new clients to book a new reservation
- Get Room Number
	you can retrieve your room number from the database simply by inputing your name, and reservation id.
- Update Reservation
	this option contains new room number, new guest name, and new contact number. you can simply update your name, room number and contact number 
	on the database to get a frseh reservation id
- Delete Reservatioon 
	you can delete your reservation id from the database along with your name, room number and contact number. just by simply inputing your reservation 
	id. 

NB - the reservation ID also contains the date and time of each reservation that has been inputed in the database.

- Getting Started 🚀
- Prerequisites
Java Development Kit (JDK)
MySQL Database
MySQL Connector/J (Java)
Setup
Clone this repository to your local machine:

Configure your MySQL database settings in the HotelReservationSystem.java file:

private static final String DB_URL = "jdbc:mysql://localhost:3306/hotel_db";
private static final String DB_USER = "your_username";
private static final String DB_PASSWORD = "your_password";

Compile and run the application

Follow the on-screen menu options to use the system.

Usage 📋
Upon running the application, you'll be presented with a menu to choose your desired operation (reservation, viewing, editing, or exiting).

Follow the prompts to input reservation details, view current reservations, edit existing bookings, and more.

Contributing 🤝
Contributions are welcome! Feel free to open issues and pull requests for bug fixes, enhancements, or new features.
Acknowledgments 🙏
Special thanks to all contributors and supporters of the Hotel Reservation System project.
Happy booking! 🌆
