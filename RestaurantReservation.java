import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class RestaurantReservation {
   static Scanner input;

   static {
      input = new Scanner(System.in);
   }

   public RestaurantReservation() {
   }

   public static void main(String[] args) {
      ArrayList<Reservation> reservationList = new ArrayList();
      ArrayList<Reservation> allReservations = new ArrayList();
      int resNumber = 1;

      while(true) {

         // loops menu
         while(true) {
            System.out.println("\n--- RESTAURANT RESERVATION SYSTEM ---");
            System.out.println("1 - View All Reservations");
            System.out.println("2 - Make A Reservation");
            System.out.println("3 - Delete A Reservation");
            System.out.println("4 - Generate Report");
            System.out.println("5 - Exit");
            System.out.print("\n- ");
            String choice = input.nextLine();
            System.out.println();

            // validated user choice
            if (choice.equals("1")) {
               showReservations(reservationList);
               pause();
            } else if (choice.equals("2")) {
               System.out.println("Making new reservation...\n");
               Reservation newRes = makeReservation(resNumber);

               // saves to list array the added data
               reservationList.add(newRes);
               allReservations.add(newRes);
               ++resNumber; // iterate # to one
               System.out.println("\nReservation added successfully!");
               pause();
            } else if (choice.equals("3")) {
               reservationList = deleteReservation(reservationList); // validation for reservation deletion
               pause();
            } else if (choice.equals("4")) {

               // print format table
               System.out.println("---------- REPORT ----------\n");
               generateReport(allReservations);
               pause();
            } else {

               // condition to check if user choice is valid
               if (choice.equals("5")) {
                  System.out.println("Thank you for using the system!");
                  return;
               }

               System.out.println("Invalid option, please try again.");
               pause();
            }
         }
      }
   }

   public static void showReservations(ArrayList<Reservation> list) { // created new array list with variable list

      // condition to check if list array is empty
      if (list.isEmpty()) {
         System.out.println("No reservations found.");
      } else {

         // prints table format
         System.out.printf("%-5s %-14s %-12s %-15s %-10s %-10s%n", "#", "Date", "Time", "Name", "Adults", "Children");
         Iterator var2 = list.iterator();

         // fills out table format using object calling of "r"
         while(var2.hasNext()) {
            Reservation r = (Reservation)var2.next();
            System.out.printf("%-5d %-14s %-12s %-20s %-10d %-10d%n", r.number, r.date, r.time, r.name, r.adults, r.children);
         }

      }
   }

   // method for making reservation
   public static Reservation makeReservation(int num) {
      Reservation res = new Reservation();
      res.number = num;
      res.date = getDate();
      res.time = getTime();
      res.name = getInput("Enter name: ");
      res.adults = getInt("How many adults?: ", 1);
      res.children = getInt("How many children?: ", 0);
      return res;
   }

   // method for date entry
   public static String getDate() {
      DateTimeFormatter df = DateTimeFormatter.ofPattern("MM dd, yyyy"); // formatting for date
      return LocalDate.now().format(df); // enters current date
   }

   // method for time entry
   public static String getTime() {
      DateTimeFormatter tf = DateTimeFormatter.ofPattern("hh:mm a"); // formatting of time
      return LocalTime.now().format(tf); // enters current time
   }

   // method for int calling
   public static int getInt(String msg, int min) {
      while(true) {
         System.out.print(msg);
         String data = input.nextLine();

         // condition to check if data doesnt match
         if (!data.matches("\\d+")) {
            System.out.println("Invalid input. Please enter a number.\n");
         } else {
            int value = Integer.parseInt(data); // converst to int and sends it to data
            if (value >= min) {
               return value;
            }

            System.out.println("Number must be at least " + min + ".\n");
         }
      }
   }

   // method for deleting reservation
   public static ArrayList<Reservation> deleteReservation(ArrayList<Reservation> list) {

      // condition if list array is empty
      if (list.isEmpty()) {
         System.out.println("No reservations to delete.");
         return list;
      }
      
      else {
         showReservations(list); // calls method

         // asks for reservation number
         System.out.print("\nEnter reservation number to delete (0 to cancel): ");
         String inputNum = input.nextLine();

         // check if input matches
         if (!inputNum.matches("\\d+")) {
            System.out.println("Invalid input.");
            return list;
         }
         
         else {
            int num = Integer.parseInt(inputNum); // converts int and sends it to inptnum
            if (num == 0) {
               System.out.println("Cancelled.\n");
               return list;
            } else {
               Iterator var4 = list.iterator();

               // if var4 is !void then continue to delete reservation
               while(var4.hasNext()) {
                  Reservation r = (Reservation)var4.next();
                  if (r.number == num) {
                     list.remove(r);
                     System.out.println("Reservation #" + num + " deleted.\n");
                     return list;
                  }
               }

               System.out.println("Reservation not found.\n");
               return list;
            }
         }
      }
   }

   // method to generate report
   public static void generateReport(ArrayList<Reservation> list) {

      // check if list array is empty
      if (list.isEmpty()) {
         System.out.println("No reservations yet.");
      }
      
      // else follows default format
      else {
         int totalAdults = 0;
         int totalChildren = 0;
         int grandTotal = 0;

         // print table format
         System.out.printf("%-5s %-14s %-12s %-20s %-10s %-10s %-10s%n", "#", "Date", "Time", "Name", "Adults", "Children", "Subtotal");
         Iterator var5 = list.iterator(); // handles # numbering

         // if var5 has value then proceeds to loop until iteration is over
         while(var5.hasNext()) {
            Reservation r = (Reservation)var5.next();
            int subtotal = r.adults * 500 + r.children * 300;
            totalAdults += r.adults;
            totalChildren += r.children;
            grandTotal += subtotal;
            System.out.printf("%-5d %-14s %-12s %-20s %-10d %-10d %-10d%n", r.number, r.date, r.time, r.name, r.adults, r.children, subtotal);
         }

         System.out.println("\nTotal Adults: " + totalAdults);
         System.out.println("Total Children: " + totalChildren);
         System.out.println("Grand Total: " + grandTotal);
      }
   }

   // case handling
   public static String getInput(String msg) {
      while(true) {
         System.out.print(msg);
         String val = input.nextLine().trim();
         if (!val.isEmpty()) {
            return val;
         }

         System.out.println("Please enter something.\n");
      }
   }

   // method pause for sligh delay
   public static void pause() {
      System.out.print("\nPress Enter to continue...");
      input.nextLine();
   }
}

// created class object
class Reservation {
   int number;
   String date;
   String time;
   String name;
   int adults;
   int children;

   Reservation() {
   }
}
