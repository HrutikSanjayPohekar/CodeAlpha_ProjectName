package CodeAlpha_ProjectName;
import java.io.*;
import java.util.*;

// Room class
class Room implements Serializable {
    private int roomNumber;
    private String type;
    private double price;
    private boolean isBooked;

    public Room(int roomNumber, String type, double price) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.price = price;
        this.isBooked = false;
    }

    public int getRoomNumber() { return roomNumber; }
    public String getType() { return type; }
    public double getPrice() { return price; }
    public boolean isBooked() { return isBooked; }

    public void book() { isBooked = true; }
    public void cancel() { isBooked = false; }

    @Override
    public String toString() {
        return "Room " + roomNumber + " (" + type + ") - Rs. " + price + " - " + (isBooked ? "Booked" : "Available");
    }
}

// Booking class
class Booking implements Serializable {
    private String customerName;
    int roomNumber;
    private String roomType;

    public Booking(String customerName, int roomNumber, String roomType) {
        this.customerName = customerName;
        this.roomNumber = roomNumber;
        this.roomType = roomType;
    }

    public String getCustomerName() { return customerName; }

    @Override
    public String toString() {
        return customerName + " booked Room " + roomNumber + " (" + roomType + ")";
    }
}

// Hotel class
class Hotel {
    private List<Room> rooms;
    private List<Booking> bookings;

    private static final String ROOM_FILE = "rooms.dat";
    private static final String BOOKING_FILE = "bookings.dat";

    public Hotel() {
        rooms = new ArrayList<>();
        bookings = new ArrayList<>();
        loadData();
    }

    public void showAvailableRooms() {
        boolean found = false;
        for (Room room : rooms) {
            if (!room.isBooked()) {
                System.out.println(room);
                found = true;
            }
        }
        if (!found) System.out.println("No rooms available.");
    }

    public void makeBooking(String customerName, String type) {
        for (Room room : rooms) {
            if (!room.isBooked() && room.getType().equalsIgnoreCase(type)) {
                room.book();
                Booking booking = new Booking(customerName, room.getRoomNumber(), room.getType());
                bookings.add(booking);
                System.out.println("✅ Booking successful: " + booking);
                simulatePayment(room.getPrice());
                saveData();
                return;
            }
        }
        System.out.println("❌ No " + type + " room available.");
    }

    public void cancelBooking(String customerName) {
        Iterator<Booking> it = bookings.iterator();
        while (it.hasNext()) {
            Booking b = it.next();
            if (b.getCustomerName().equalsIgnoreCase(customerName)) {
                for (Room r : rooms) {
                    if (r.getRoomNumber() == b.roomNumber) {
                        r.cancel();
                    }
                }
                it.remove();
                System.out.println("❌ Booking cancelled for " + customerName);
                saveData();
                return;
            }
        }
        System.out.println("❌ Booking not found.");
    }

    public void viewBookings() {
        if (bookings.isEmpty()) {
            System.out.println("📭 No bookings.");
            return;
        }
        for (Booking b : bookings) {
            System.out.println(b);
        }
    }

    private void simulatePayment(double amount) {
        System.out.println("💳 Processing payment of Rs. " + amount + "...");
        try { Thread.sleep(1000); } catch (InterruptedException e) {}
        System.out.println("✅ Payment successful!");
    }

    private void loadData() {
        try (ObjectInputStream ois1 = new ObjectInputStream(new FileInputStream(ROOM_FILE));
             ObjectInputStream ois2 = new ObjectInputStream(new FileInputStream(BOOKING_FILE))) {
            rooms = (List<Room>) ois1.readObject();
            bookings = (List<Booking>) ois2.readObject();
        } catch (Exception e) {
            // First time setup
            rooms.add(new Room(101, "Standard", 1000));
            rooms.add(new Room(102, "Standard", 1000));
            rooms.add(new Room(201, "Deluxe", 2000));
            rooms.add(new Room(202, "Deluxe", 2000));
            rooms.add(new Room(301, "Suite", 3500));
        }
    }

    private void saveData() {
        try (ObjectOutputStream oos1 = new ObjectOutputStream(new FileOutputStream(ROOM_FILE));
             ObjectOutputStream oos2 = new ObjectOutputStream(new FileOutputStream(BOOKING_FILE))) {
            oos1.writeObject(rooms);
            oos2.writeObject(bookings);
        } catch (Exception e) {
            System.out.println("❌ Error saving data.");
        }
    }
}

// Main class
public class HoteleReservationSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Hotel hotel = new Hotel();

        while (true) {
            System.out.println("\n🏨 HOTEL RESERVATION SYSTEM");
            System.out.println("1. Show available rooms");
            System.out.println("2. Book a room");
            System.out.println("3. Cancel a booking");
            System.out.println("4. View all bookings");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt(); sc.nextLine();

            switch (choice) {
                case 1:
                    hotel.showAvailableRooms();
                    break;
                case 2:
                    System.out.print("Enter your name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter room type (Standard/Deluxe/Suite): ");
                    String type = sc.nextLine();
                    hotel.makeBooking(name, type);
                    break;
                case 3:
                    System.out.print("Enter your name: ");
                    String cname = sc.nextLine();
                    hotel.cancelBooking(cname);
                    break;
                case 4:
                    hotel.viewBookings();
                    break;
                case 5:
                    System.out.println("Exiting. Thank you!");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
