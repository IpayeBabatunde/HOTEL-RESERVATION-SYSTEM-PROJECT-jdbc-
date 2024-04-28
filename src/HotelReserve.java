import java.sql.*;
import java.util.Scanner;

public class HotelReserve {
    private static final String url = "jdbc:mysql://localhost:3306/hotel_db?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String username = "root";
    private static final String password = "1234567";

    public static void main(String[] args) throws ClassNotFoundException, SQLException{
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            while (true) {
                System.out.println("HOTEL MANAGEMENT SYSTEM");
                Scanner scanner = new Scanner(System.in);
                System.out.println("1. Reserve a room");
                System.out.println("2. View Reservations");
                System.out.println("3. Get Room Number");
                System.out.println("4. Update Reservations");
                System.out.println("5. Delete Reservations");
                System.out.println("0. Exit");
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        reserveRoom(connection, scanner);
                        break;

                    case 2:
                        viewReservations(connection);
                        break;

                    case 3:
                        getRoomNumber(connection, scanner);
                        break;

                    case 4:
                        updateReservation(connection, scanner);
                        break;

                    case 5:
                        deleteReservation(connection, scanner);
                        break;

                    case 0:
                        exit();
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid choice. Try again.");
                }

            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    private static void reserveRoom(Connection connection, Scanner scanner) {
        try {
            System.out.println("Enter guest name: ");
            String guestName = scanner.next();
            scanner.nextLine();
            System.out.println("Enter room number: ");
            int roomNumber = scanner.nextInt();
            System.out.println("Enter contact number: ");
            String contactNumber = scanner.next();

            String sql = "INSERT INTO Reservations (guest_name, room_number, contact_number)" +
                    "VALUES ('" + guestName + "', " + roomNumber + ", '" + contactNumber + "')";

            try (Statement statement = connection.createStatement()) {
                int affectedRows = statement.executeUpdate(sql);

                if (affectedRows > 0) {
                    System.out.println("Reservation Successful !!!");
                }else {
                    System.out.println("Reservation Failed. Please try again ");
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    private static void viewReservations(Connection connection) throws SQLException {
        String sql = "SELECT reservations.Reservation_ID, Reservations.Guest_Name, Reservations.Room_Number, Reservations.Contact_Number, Reservations.Reservation_Date FROM Reservations;";

        try (Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql)){
            System.out.println("Current Reservation: ");
            System.out.println("+----------------+------------------+---------------+--------------------+-------------------------+");
            System.out.println("| Reservation ID | Guest            | Room Number   | Contact Number     | Reservation Date        |");
            System.out.println("+----------------+------------------+---------------+--------------------+-------------------------+");

            while (resultSet.next()) {
                int reservationId = resultSet.getInt("reservation_id");
                String guestName = resultSet.getString("guest_Name");
                int roomNumber = resultSet.getInt("room_Number");
                String contactNumber = resultSet.getString("Contact_number");
                String reservationDate = resultSet.getTimestamp("reservation_date").toString();

                // format and display the reservation date in a table format
                System.out.printf("| %-14d | %-15s | %-13d | %-20s | %-19s  |\n",
                        reservationId, guestName, roomNumber, contactNumber, reservationDate);
            }
            System.out.println("+---------------+--------------+------------+-------------+------------------------------+");
        }
    }
    private static void getRoomNumber(Connection connection, Scanner scanner){
        try {
            System.out.println("Enter Reservation ID: ");
            int reservationId = scanner.nextInt();
            System.out.println("Enter Guest Name: ");
            String guestName = scanner.next();

            String sql = "SELECT room_number FROM reservations " +
                    "WHERE reservations.Reservation_ID = " + reservationId +
                    " AND Guest_Name = '" + guestName + "'";

            try (Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql)) {

                if (resultSet.next()) {
                    int roomNumber = resultSet.getInt("room_Number");
                    System.out.println("Room number for Reservation ID " + reservationId +
                            " and Guest " + guestName + " is: " + roomNumber);
                }else {
                    System.out.println("Reservation not found for the given ID and guest name");
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static void updateReservation(Connection connection, Scanner scanner){
        try {
            System.out.println("Enter reservation ID to update: ");
            int reservationId = scanner.nextInt();
            scanner.nextLine();

            if (!reservationExists(connection, reservationId)) {
                System.out.println("Reservation not found for the given ID.");
                return;
            }
            System.out.print("Enter new guest name: ");
            String newGuestName = scanner.nextLine();
            System.out.print("Enter new room number: ");
            int newRoomNumber = scanner.nextInt();
            System.out.print("Enter new contact number: ");
            String newContactNumber = scanner.next();

            String sql = "UPDATE reservations SET Guest_Name = '" + newGuestName + "'," +
                    "reservations.Room_Number = " + newRoomNumber + ", " +
                    "Contact_Number = '" + newContactNumber + "' " +
                    " WHERE Reservation_ID = " + reservationId;

            try (Statement statement = connection.createStatement()) {
                int affectedRows = statement.executeUpdate(sql);

                if (affectedRows > 0 ) {
                    System.out.println("Reservation updated successfully!");
                }else {
                    System.out.println("Reservation update failed.");
                }
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
private static void deleteReservation(Connection connection, Scanner scanner) {
        try {
            System.out.println("Enter reservation ID to delete: ");
            int reservationId = scanner.nextInt();

            if (!reservationExists(connection, reservationId)){
                System.out.println("Reservation not found for the given ID.");
                return;
            }
            String sql = "DELETE FROM reservations WHERE Reservation_ID = " + reservationId;

            try (Statement statement = connection.createStatement()) {
                int affectedRows = statement.executeUpdate(sql);

                if (affectedRows > 0) {
                    System.out.println("Reservation deleted successfully!");
                }else {
                    System.out.println("Reservation deletion failed.");
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
}
private static boolean reservationExists(Connection connection, int reservationId) {
        try {
            String sql = "SELECT Reservation_id FROM reservations WHERE reservation_id = " + reservationId;

            try (Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql)) {
                return resultSet.next();
            }
        }catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
}
public static void exit() throws  InterruptedException{
    System.out.println("Existing System");
    int i = 5;
    while (i!=0){
        System.out.println(".");
        Thread.sleep(1000);
        i--;
    }
    System.out.println();
    System.out.println("Thank you for Using Hotel Reservation System !!! ");
}
}