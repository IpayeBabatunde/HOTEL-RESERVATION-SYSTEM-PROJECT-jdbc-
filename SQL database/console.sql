CREATE TABLE Reservations (
    Reservation_ID INT AUTO_INCREMENT PRIMARY KEY,
Guest_Name VARCHAR(255) NOT NULL,
Room_Number INT NOT NULL,
Contact_Number VARCHAR(10),
Reservation_Date TIMESTAMP DEFAULT  CURRENT_TIMESTAMP);

DESCRIBE Reservations;
SELECT * FROM Reservations;
SELECT Reservation_ID, Reservations.Guest_Name, Reservations.Room_Number, Reservations.Contact_Number, Reservations.Reservation_Date FROM Reservations;
insert into reservations (Guest_Name, Room_Number, Contact_Number) values ('ipaye', 001, '1234567');
DELETE FROM reservations WHERE Reservation_ID = 4;
SELECT * FROM reservations;