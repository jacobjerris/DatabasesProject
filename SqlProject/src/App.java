import java.util.Scanner;
import java.sql.*;
/*
    TODO:
    1. generate queries for each option
    2. setup some type of login?
    3. make it more "user friendly" and test queries before presentation
 */

public class App {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        //creating database connection
        //testQuery("root", "root");
        Scanner input = new Scanner(System.in);
            System.out.print("Are you a User or an Admin?\n");
            String user = input.nextLine();
            if(user.equalsIgnoreCase("User")) {

                UserLoop(input);

            } else if (user.equalsIgnoreCase("Admin")) {

                AdminLoop(input);

            }
            input.close(); //closing userInput bc it yells at me for mem issues
        }
    
    public static void UserLoop(Scanner input) throws SQLException, ClassNotFoundException {
        int userInput = 0;
        while (userInput != 5) { //loops through prompts until user enters 5.
            System.out.println("\nWhat would you like to do?");
            System.out.print("\t1. Search\n\t2. Checkout\n\t3. Return\n\t4. Check Balance, Pay Balance and late fees\n\t5. Log Out\nEnter Choice: ");
            userInput = input.nextInt(); //collect user choice
            if (userInput == 1) {
                System.out.print("Enter your Book Title: ");
                input.nextLine();
                String bookTitle = input.nextLine();
                authorLookup("root", "root", bookTitle);
                //query based on user input for a title, could use same query as in the "locate title" in admin panel

            } else if (userInput == 2) {
                System.out.println("Checkout: ");
                //checkout could link to two options, rent or buy


            } else if (userInput == 3) {
                System.out.println("Books to return: ");
                //query books that user has out as "rented", and give them price for each book?

            } else if (userInput == 4) {
                System.out.println("What would you like to do?");
                System.out.print("\t1. Check Balance\n\t2. Pay Balance and late fees\nEnter Choice: ");

                userInput = input.nextInt();

                switch (userInput) {
                    case 1:
                        System.out.print("Enter your customer number to view your balance here: ");
                    case 2:
                        System.out.print("Enter your customer number to view the balance to be paid here: ");
                        //balance query
                        System.out.print("Enter your credit card credentials here: ");
                        //once entered, the info should be cleared in their profile and display 0.00 when they submit
                }
            }else if (userInput == 5){
             System.out.println("Goodbye!");

            } else {
                System.out.println("Command not recognised");
            }
        }
    }

    
    public static void AdminLoop(Scanner input) throws SQLException, ClassNotFoundException {
        int userInput = 0;
        while (userInput != 5) {
            System.out.println("\nWhat would you like to do?");
            System.out.print("\t1. Locate Title\n\t2. Update inventory\n\t3. Check Balance\n\t4. Generate Reports\n\t5. Log Out\nEnter Choice: ");
            userInput = input.nextInt();
            if (userInput == 1) {
                System.out.print("Please enter the name of the book you're trying to locate: ");
                input.nextLine();
                String bookTitle = input.nextLine();
                authorLookup("root", "root", bookTitle);
                //query based on book name, show all details about book

            } else if (userInput == 2) {
                System.out.println("What would you like to do?\n1. Add to inventory\n2. Delete title\n3. Update title");
                //run query based on user input, prob a switch statement is needed, etc.

            } else if (userInput == 3) {
                System.out.println("What would you like to do?\n1. Check user balance\n2. Apply late fee to user");
                //depending on the option, could prob accomplish both tasks with one query

            } else if (userInput == 4) {
                System.out.println("Generating Reports..");
                //doesn't state for dates, but could ask for dates. or just print every record that we have, shouldn't
                // be too much though

            } else if (userInput == 5){
                System.out.println("Goodbye!");

            } else {
                System.out.println("Command not recognised");
            }
        }
    }


    public static void authorLookup(String username, String password, String bookTitle) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306", username, password);

        PreparedStatement ps = con.prepareStatement("SELECT * FROM local.books WHERE BookTitle =?");
        ps.setString(1, bookTitle);

        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            String b = rs.getString("BookTitle");
            String g = rs.getString("Genre");
            String c = rs.getString("Condition");
            String f = rs.getString("Format");
            String p = rs.getString("Price");
            String count = rs.getString("Count");
            System.out.print("Book Title: " + b + "\n" + "Genre: " + g + "\n" + "Condition: " + c + "\n" + "Format: " + f + "\n" + "Price: " + p + "\n" + "Count: " + count + "\n");
        }
    }
}
