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
                System.out.println("What would you like to do?\n1. Purchase book\n2. Rent book");

                int userSelection = input.nextInt();

                if(userSelection == 1) {
                    System.out.println("Please enter the book title you'd like to purchase: ");
                    String bookUser = input.nextLine();
                    purchaseBook("root", "rootuser", bookUser);
                }
                else if(userSelection ==2) {
                    System.out.println("Please provide your customer number: ");
                    int cusNumber = input.nextInt();
                    rentBook("root", "rootuser", cusNumber);
                }
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
                        break;
                    case 2:
                        System.out.print("Enter your customer number to view the balance to be paid here: ");
                        //balance query
                        System.out.print("Enter your credit card credentials here: ");
                        //once entered, the info should be cleared in their profile and display 0.00 when they submit
                        break;
                    default:
                        System.out.println("Command not recognised");
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
                System.out.println("What would you like to do?");
                System.out.print("\t1. Add to inventory\n\t2. Delete title\n\t3. Update title\nEnter Choice: ");
                //run query based on user input
                userInput = input.nextInt();
                switch (userInput) {
                    case 1:
                        System.out.print("*Add to inventory*\n");
                        addInventory("root","root");
                        break;
                    case 2:
                        System.out.print("*Delete title*\n");
                        deleteInventory("root","root");
                        break;
                    case 3:
                        System.out.print("*Update title*\n");
                        updateInventory("root","root");
                        break;
                    default:
                        System.out.println("Command not recognised");
                }

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
            System.out.print("Book Title: " + b + "\n" + "Genre: " + g + "\n" + "Condition: " + c + "\n" + "Format: " + f + "\n" + "Price: " + p + "\n");
        }
    }

    public static void rentBook(String username, String password, int cusNumber) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306", username, password);
        PreparedStatement checkBalance = con.prepareStatement("SELECT customer.Balance FROM local.customer WHERE CustomerID =?");
        checkBalance.setInt(1, cusNumber);


        PreparedStatement countOfRental = con.prepareStatement("SELECT COUNT(local.transaction.Rental) FROM local.transaction WHERE Rental = 1");

        ResultSet count = countOfRental.executeQuery();
        ResultSet rs = checkBalance.executeQuery();


        while(rs.next()) {
            int b = rs.getInt("Balance");

            int rentalCount = count.getInt("Rental");
            System.out.println(rentalCount);

            if (b == 0) {
                if(rentalCount )
                System.out.println("You may rent books!");
            }
            else {
                System.out.println("Please clear your balance before renting books.");
            }
        }
    }

    public static void purchaseBook(String username, String password, String book) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");

        Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306", username, password);

        PreparedStatement checkBalance = con.prepareStatement("");

        checkBalance.setString(1, book);

    }


    public static void addInventory(String username, String password) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306", username, password);

        //Prompt User for Book Information
        Scanner input = new Scanner(System.in);
        System.out.println("Enter Title Information to add Title to Database");
        System.out.print("Book Title: ");
        String bookTitle = input.nextLine();
        System.out.print("Book ISBN: ");
        String bookISBN = input.nextLine();
        System.out.print("Book AuthorID: ");
        String bookAuthorID = input.nextLine();
        System.out.print("Book Author First Name: ");
        String bookFName = input.nextLine();
        System.out.print("Book Author Last Name: ");
        String bookLName = input.nextLine();
        System.out.print("Book Author Middle Initial: ");
        String bookMInitial = input.nextLine();
        System.out.print("Book Genre: ");
        String bookGenre = input.nextLine();
        System.out.print("Book SubGenre: ");
        String bookSubGenre = input.nextLine();
        System.out.print("Book PublisherID: ");
        String bookPublisherID = input.nextLine();
        System.out.print("Book PublisherName: ");
        String bookPublisherName = input.nextLine();
        System.out.print("Book Condition: ");
        String bookCondition = input.nextLine();
        System.out.print("Book format: ");
        String bookFormat = input.nextLine();
        System.out.print("Book Price: ");
        String bookPrice = input.nextLine();

        //Author Insert Statement
        String sqlAuthor = "INSERT INTO local.author (AuthorID, FName, LName, MInitial ) VALUES (?, ?, ?, ?)";

        PreparedStatement authorStatement = con.prepareStatement(sqlAuthor);
        authorStatement.setInt(1, Integer.parseInt(bookAuthorID));
        authorStatement.setString(2, bookFName);
        authorStatement.setString(3, bookLName);
        authorStatement.setString(4, bookMInitial);

        System.out.printf("\n%s\n", authorStatement);
        authorStatement.executeUpdate();

        //Genre Insert Statement
        String sqlGenre = "INSERT INTO local.category (Genre, SubGenre ) VALUES (?, ?)";

        PreparedStatement genreStatement = con.prepareStatement(sqlGenre);
        genreStatement.setString(1, bookGenre);
        genreStatement.setString(2, bookSubGenre);

        System.out.printf("%s\n", genreStatement);
        genreStatement.executeUpdate();

        //Publisher Insert Statement
        String sqlPublisher = "INSERT INTO local.publishers (PublisherID, Name ) VALUES (?, ?)";

        PreparedStatement publisherStatement = con.prepareStatement(sqlPublisher);
        publisherStatement.setString(1, bookPublisherID);
        publisherStatement.setString(2, bookPublisherName);

        System.out.printf("%s\n", publisherStatement);
        publisherStatement.executeUpdate();

        //Book Insert Statement
        String sqlBook = "INSERT INTO local.books (ISBN, AuthorID, BookTitle, Genre, PublisherID, `Condition`, `Format`, Price) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement bookStatement = con.prepareStatement(sqlBook);
        bookStatement.setInt(1, Integer.parseInt(bookISBN));
        bookStatement.setInt(2, Integer.parseInt(bookAuthorID));
        bookStatement.setString(3, bookTitle);
        bookStatement.setString(4, bookGenre);
        bookStatement.setInt(5, Integer.parseInt(bookPublisherID));
        bookStatement.setString(6, bookCondition);
        bookStatement.setString(7, bookFormat);
        bookStatement.setFloat(8, Float.parseFloat(bookPrice));

        System.out.printf("%s\n\n", bookStatement);
        int rowsInserted = bookStatement.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("A new book was inserted successfully!");
        }
    }


    public static void deleteInventory(String username, String password) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306", username, password);

        Scanner input = new Scanner(System.in);
        System.out.print("Enter Book Title to Delete: ");
        String bookTitle = input.nextLine();

        if(bookTitle != null){
            System.out.printf("Would you like to delete the book \"%s\" from inventory?\nYes/No:", bookTitle);
            char user = input.nextLine().charAt(0);
            if(user == 'y' || user == 'Y') {
                PreparedStatement ds = con.prepareStatement("DELETE FROM local.books WHERE BookTitle =?");
                ds.setString(1, bookTitle);
                ds.executeUpdate();
                System.out.printf("Book \"%s\" has been Deleted from inventory.", bookTitle);
            }
            else if(user == 'n' || user == 'N')
                System.out.printf("Book \"%s\" has NOT been Deleted from inventory.", bookTitle);

            else
                System.out.println("Command not found");

        }
        else
            System.out.print("No book with matching title found.");
    }


    public static void updateInventory(String username, String password) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306", username, password);

        Scanner input = new Scanner(System.in);
        System.out.print("Enter Book ISBN to Update: ");
        String bookISBN = input.nextLine();

        PreparedStatement ps = con.prepareStatement("SELECT * FROM local.books WHERE ISBN =?");
        ps.setString(1, bookISBN);

        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            String b = rs.getString("BookTitle");
            String g = rs.getString("Genre");
            String c = rs.getString("Condition");
            String f = rs.getString("Format");
            String p = rs.getString("Price");
            System.out.print("Book Title: " + b + "\n" + "Genre: " + g + "\n" + "Condition: " + c + "\n" + "Format: " + f + "\n" + "Price: " + p + "\n\n");
            System.out.printf("Is %s the book you want to Update?\nYes/No: ", b);
        }

        char user = input.nextLine().charAt(0);
        if(user == 'y' || user == 'Y') {
            System.out.print("New Book Title: ");
            String bookTitle = input.nextLine();
            
            PreparedStatement prs = con.prepareStatement("UPDATE local.books SET BookTitle = ? WHERE ISBN = ?");

            prs.setString(1,bookTitle);
            prs.setString(2,bookISBN);

            prs.executeUpdate();
            prs.close();
            System.out.println("Book Title has been Updated.");
        }
        else if(user == 'n' || user == 'N')
            System.out.println("Book has NOT been Updated.");

        else
            System.out.println("Option not found");
    }
}
