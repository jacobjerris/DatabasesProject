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

            //closing userInput bc it yells at me for mem issues
            input.close();
        }
    
    public static void UserLoop(Scanner input) throws SQLException, ClassNotFoundException {
        //while (true) {
            System.out.println("What would you like to do?");
            System.out.println("1. Search\n2. Checkout\n3. Return\n4. Check Balance, Pay Balance and late fees\n5. Log Out\n");
            int userInput = input.nextInt();
            if (userInput != 5) {
                if (userInput == 1) {
                    System.out.println("Enter your Book Title: ");
                    String authorName = input.next();

                    authorLookup("root", "root", authorName);
                    //query based on user input for a title, could use same query as in the "locate title" in admin panel

                } else if (userInput == 2) {
                    System.out.println("Checkout: ");
                    //checkout could link to two options, rent or buy


                } else if (userInput == 3) {
                    System.out.println("Books to return: ");
                    //query books that user has out as "rented", and give them price for each book?

                } else if (userInput == 4) {
                    System.out.println("What would you like to do?");
                    System.out.println("1. Check Balance\n2. Pay Balance and late fees\n");

                    userInput = input.nextInt();

                    switch (userInput) {
                        case 1:
                            System.out.println("Enter your customer number to view your balance here: ");
                        case 2:
                            System.out.println("Enter your customer number to view the balance to be paid here: ");
                            //balance query
                            System.out.println("Enter your credit card credentials here: ");
                            //once entered, the info should be cleared in their profile and display 0.00 when they submit
                    }
                } else {
                    System.out.println("Command not recognised");
                }
            //} //else {
                //System.out.println("Good Bye!");
                //break;
            }
        }
    //}
    
    public static void AdminLoop(Scanner input) {
        while (true) {
            System.out.println("What would you like to do?");
            System.out.println("1. Locate Title\n2. Update inventory\n3. Check Balance\n4. Generate Reports\n5. Log Out\n");
            int userInput = input.nextInt();
            if (userInput != 5) {
                if (userInput == 1) {
                    System.out.println("Please enter the name of the book you're trying to locate: ");
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

                } else {
                    System.out.println("Command not recognised");
                }
            } else {
                System.out.println("Good Bye!");
                break;
            }
        }
    }

    public static void testQuery(String username, String password) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306", username, password);
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM local.author ");

        while(rs.next()) {
            String s = rs.getString("FName");
            String p = rs.getString("LName");
            System.out.print(s + " " + p + "\n");
        }
    }

    public static void authorLookup(String username, String password, String FName) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306", username, password);

        PreparedStatement ps = con.prepareStatement("SELECT * FROM local.author WHERE FName =?");
        ps.setString(1, FName);

        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            String s = rs.getString("FName");
            String r = rs.getString("LName");
            String q = rs.getString("MInitial");
            System.out.print(s + " " + r + " " + q + " " + "\n");
        }
    }
}

