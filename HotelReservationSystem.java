import java.util.ArrayList;
import java.util.Scanner;

class Room {
    int roomNumber;
    String roomType;
    double price;
    boolean isAvailable;

    public Room(int roomNumber, String roomType, double price) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.price = price;
        this.isAvailable = true;
    }

    public void bookRoom() {
        isAvailable = false;
    }

    public void makeAvailable() {
        isAvailable = true;
    }

    public String toString() {
        return "Room " + roomNumber + " (" + roomType + ") - $" + price + " per night. Available: " + isAvailable;
    }
}

class Reservation {
    Room room;
    String customerName;
    int nights;
    double totalPrice;

    public Reservation(Room room, String customerName, int nights) {
        this.room = room;
        this.customerName = customerName;
        this.nights = nights;
        this.totalPrice = room.price * nights;
        room.bookRoom();  // Mark the room as booked
    }

    public void displayReservation() {
        System.out.println("\nReservation Details:");
        System.out.println("Customer Name: " + customerName);
        System.out.println("Room Number: " + room.roomNumber + " (" + room.roomType + ")");
        System.out.println("Number of Nights: " + nights);
        System.out.println("Total Price: $" + totalPrice);
    }

    // Generate payment receipt
    public void displayPaymentReceipt() {
        System.out.println("\n--- Payment Receipt ---");
        System.out.println("Customer Name: " + customerName);
        System.out.println("Room Number: " + room.roomNumber + " (" + room.roomType + ")");
        System.out.println("Price per Night: $" + room.price);
        System.out.println("Number of Nights: " + nights);
        System.out.println("Total Amount: $" + totalPrice);
        System.out.println("Payment Status: Paid");
        System.out.println("Payment Confirmation Number: " + Math.random() * 100000); // Mock confirmation number
        System.out.println("------------------------");
    }
}

public class HotelReservationSystem {
    static ArrayList<Room> rooms = new ArrayList<>();
    static ArrayList<Reservation> reservations = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        initializeRooms();

        while (true) {
            System.out.println("\n--- Hotel Reservation System ---");
            System.out.println("1. Search Available Rooms");
            System.out.println("2. Make a Reservation");
            System.out.println("3. View Reservation");
            System.out.println("4. Exit");
            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    searchAvailableRooms();
                    break;
                case 2:
                    makeReservation(scanner);
                    break;
                case 3:
                    viewReservation(scanner);
                    break;
                case 4:
                    System.out.println("Thank you for using the Hotel Reservation System.");
                    System.exit(0);
                default:
                    System.out.println("Invalid option! Please try again.");
            }
        }
    }

    // Initialize some rooms
    public static void initializeRooms() {
        rooms.add(new Room(101, "Single", 50.0));
        rooms.add(new Room(102, "Single", 50.0));
        rooms.add(new Room(201, "Double", 80.0));
        rooms.add(new Room(202, "Double", 80.0));
        rooms.add(new Room(301, "Suite", 150.0));
    }

    // Search for available rooms
    public static void searchAvailableRooms() {
        System.out.println("\nAvailable Rooms:");
        for (Room room : rooms) {
            if (room.isAvailable) {
                System.out.println(room);
            }
        }
    }

    // Make a reservation
    public static void makeReservation(Scanner scanner) {
        System.out.println("\nEnter your name: ");
        String customerName = scanner.nextLine();

        searchAvailableRooms();
        System.out.print("Enter room number to reserve: ");
        int roomNumber = scanner.nextInt();
        System.out.print("Enter number of nights: ");
        int nights = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        Room selectedRoom = findRoomByNumber(roomNumber);
        if (selectedRoom != null && selectedRoom.isAvailable) {
            Reservation reservation = new Reservation(selectedRoom, customerName, nights);
            reservations.add(reservation);
            System.out.println("Room reserved successfully!");
            reservation.displayReservation();

            // Simulate payment processing and display the receipt
            processPayment(scanner, reservation);
        } else {
            System.out.println("Room is either not available or doesn't exist.");
        }
    }

    // Payment processing
    public static void processPayment(Scanner scanner, Reservation reservation) {
        System.out.println("\nProceed to Payment?");
        System.out.println("1. Yes");
        System.out.println("2. No");
        int paymentChoice = scanner.nextInt();

        if (paymentChoice == 1) {
            System.out.println("Processing payment...");
            // Assume the payment is successful and show a receipt
            reservation.displayPaymentReceipt();
        } else {
            System.out.println("Payment cancelled. Reservation not completed.");
        }
    }

    // View reservation details
    public static void viewReservation(Scanner scanner) {
        System.out.println("\nEnter your name to view reservation: ");
        String customerName = scanner.nextLine();

        for (Reservation reservation : reservations) {
            if (reservation.customerName.equalsIgnoreCase(customerName)) {
                reservation.displayReservation();
                return;
            }
        }
        System.out.println("No reservation found for this name.");
    }

    // Helper method to find a room by its number
    public static Room findRoomByNumber(int roomNumber) {
        for (Room room : rooms) {
            if (room.roomNumber == roomNumber) {
                return room;
            }
        }
        return null;
    }
}
