import java.util.Scanner;
import java.sql.*;
/*
    TODO:
    1. generate queries for each option
    2. setup some type of login?
    3. make it more "user friendly" and test queries before presentation
 */
public class App {
    static String username = "root";
    static String password = "root";
    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        //creating database connection
        //testQuery(username, password);
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
                authorLookup(username, password, bookTitle);
                //query based on user input for a title, could use same query as in the "locate title" in admin panel

            } else if (userInput == 2) {
                System.out.println("What would you like to do?\n1. Purchase book\n2. Rent book");

                int userSelection = input.nextInt();

                if(userSelection == 1) {
                    System.out.println("Please provide your customer number: ");
                    int cusNumber = input.nextInt();
                    purchaseBook(username, password, cusNumber);
                }
                else if(userSelection == 2) {
                    System.out.println("Please provide your customer number: ");
                    int cusNumber = input.nextInt();

                    if(rentBookCheck(username, password, cusNumber)) {
                        rentBook(username, password, cusNumber);
                    }
                    else {
                        System.out.println("Please clear or return your already rented books before renting another one, thank you!");
                    }
                }
                //checkout could link to two options, rent or buy
            } else if (userInput == 3) {
                System.out.println("Return a Book");
                    returnBook(username, password);

            } else if (userInput == 4) {
                System.out.println("What would you like to do?");
                System.out.print("\t1. Check Balance\n\t2. Pay Balance\n\t3. Pay Late Fees\n\tEnter Choice: ");

                userInput = input.nextInt();
                int CusNumb;
                String ccard;
                float balance, payment, finalBalance, lateFees, finalLateFees;

                switch (userInput) {
                    case 1:
                        System.out.print("Enter your customer number to view your balance here: ");
                        CusNumb = input.nextInt();
                        balance = viewBalance(CusNumb);
                        System.out.printf("Your Balance is %.2f", balance);
                        break;
                    case 2:
                        System.out.print("Enter your customer number to view the balance to be paid here: ");
                        CusNumb = input.nextInt();
                        balance = viewBalance(CusNumb);
                        System.out.printf("Your Balance is $%.2f\n", balance);
                        Scanner in = new Scanner(System.in);
                        if(balance > 0) {
                            while (true) {
                                System.out.print("Enter your credit card credentials here: ");
                                ccard = in.nextLine();
                                if (ccard.length() == 16) {
                                    System.out.print("Your credit card is valid!\n");
                                    break;
                                }
                                System.out.print("Invalid credit card number, credit card number must be 16 digits long\n");
                            }
                            while (true) {
                                System.out.printf("How much would you like to pay? Must be less than or equal to $%.2f\n", balance);
                                payment = in.nextFloat();
                                if (payment <= balance && payment >= 0) {
                                    finalBalance = balance - payment;
                                    changeBalance(CusNumb, finalBalance);
                                    break;
                                }
                                System.out.println("That was an invalid payment amount!");
                            }
                        } else {System.out.println("You Have no Balance to pay");}

                        break;
                    case 3:
                        System.out.print("Enter your customer number to view the Late Fees to be paid here: ");
                        CusNumb = input.nextInt();
                        lateFees = viewLateFees(CusNumb);
                        System.out.printf("Your Late Fees Balance is $%.2f\n", lateFees);
                        Scanner inp = new Scanner(System.in);
                        if(lateFees > 0) {
                            while (true) {
                                System.out.print("Enter your credit card credentials here: ");
                                ccard = inp.nextLine();
                                if (ccard.length() == 16) {
                                    System.out.print("Your credit card is valid!\n");
                                    break;
                                }
                                System.out.print("Invalid credit card number, credit card number must be 16 digits long\n");
                            }
                            while (true) {
                                System.out.printf("How much would you like to pay? Must be less than or equal to $%.2f\n", lateFees);
                                payment = inp.nextFloat();
                                if (payment <= lateFees && payment >= 0) {
                                    finalLateFees = lateFees - payment;
                                    changeLateFees(CusNumb, finalLateFees);
                                    break;
                                }
                                System.out.println("That was an invalid payment amount!");
                            }
                        } else {System.out.println("You Have no Late Fees to pay");}
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
                authorLookup(username, password, bookTitle);
                //query based on book name, show all details about book

            } else if (userInput == 2) {
                System.out.println("What would you like to do?");
                System.out.print("\t1. Add to inventory\n\t2. Delete title\n\t3. Update title\nEnter Choice: ");
                //run query based on user input
                userInput = input.nextInt();
                switch (userInput) {
                    case 1:
                        System.out.print("*Add to inventory*\n");
                        addInventory(username,password);
                        break;
                    case 2:
                        System.out.print("*Delete title*\n");
                        deleteInventory(username,password);
                        break;
                    case 3:
                        System.out.print("*Update title*\n");
                        updateInventory(username,password);
                        break;
                    default:
                        System.out.println("Command not recognised");
                }

            } else if (userInput == 3) {
                checkUserBalanceAndLateFees(username, password);
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


    public static boolean rentBookCheck(String username, String password, int cusNumber) throws ClassNotFoundException, SQLException {
        boolean test = false;

        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306", username, password);
        PreparedStatement checkBalance = con.prepareStatement("SELECT customer.Balance FROM local.customer WHERE CustomerID =?");
        PreparedStatement checkRentals = con.prepareStatement("SELECT COUNT(local.transaction.Rental) FROM local.transaction WHERE Rental = 1");
        checkBalance.setInt(1, cusNumber);

        ResultSet rs = checkBalance.executeQuery();
        ResultSet cs = checkRentals.executeQuery();
        rs.next();
        cs.next();
        int b = rs.getInt("Balance");
        int c = cs.getInt(1);


        if (b == 0 && c < 2) {
            test = true;
        }
        return test;
    }


    public static void rentBook(String username, String password, int cusNumber) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306", username, password);

        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);

        PreparedStatement rentNumber = con.prepareStatement("SELECT max(RentalID) FROM local.rent_status");
        ResultSet rn = rentNumber.executeQuery();
        rn.next();
        int rentCount = rn.getInt(1);



        PreparedStatement rentStatus = con.prepareStatement("INSERT INTO local.rent_status VALUES (?, Day_Rented, 1, Date_Due)");
        rentStatus.setInt(1, rentCount + 1);
        rentStatus.executeUpdate();


        //Query that gets the biggest transaction ID and makes it into a value called "count"
        PreparedStatement transaction = con.prepareStatement("SELECT max(TransactionID) FROM local.transaction");
        ResultSet rs = transaction.executeQuery();
        rs.next();
        int count = rs.getInt(1);

        Scanner input = new Scanner(System.in) ;
        System.out.print("Please enter your book title you'd like to rent:  ");
        String bookTitle = input.nextLine();

        PreparedStatement ps = con.prepareStatement("SELECT * FROM local.books WHERE BookTitle = ?");
        ps.setString(1, bookTitle);
        ResultSet is = ps.executeQuery();

        int b = 0;
        int balance = 0;

        while(is.next()) {
            b = is.getInt("ISBN");
            balance = is.getInt("Price");
        }

        //This inserts the transaction when the user finished their purchase
        PreparedStatement purchaseBook = con.prepareStatement("INSERT INTO local.transaction (TransactionID, CustomerID,Transaction_Date, ISBN,Transaction_Price, Rental, RentalID) VALUES (?, ?, ?, ? , ?, 1, ?)");
        purchaseBook.setInt(1, count + 1);
        purchaseBook.setInt(2, cusNumber);
        purchaseBook.setDate(3, date);
        purchaseBook.setInt(4, b);
        purchaseBook.setInt(5, balance);
        purchaseBook.setInt(6, rentCount);
        purchaseBook.executeUpdate();


        PreparedStatement currentBalance = con.prepareStatement("SELECT Balance FROM local.customer WHERE CustomerID = ?");
        currentBalance.setInt(1, cusNumber);
        ResultSet cb = transaction.executeQuery();
        cb.next();
        int currBalance = cb.getInt(1);

        PreparedStatement customerBalance = con.prepareStatement("UPDATE local.customer SET Balance = ? + customer.Balance WHERE CustomerID = ?");
        customerBalance.setInt(1, currBalance);
        customerBalance.setInt(2, cusNumber);
        customerBalance.executeUpdate();

        System.out.println("Your balance has been updated.");
    }


    public static void purchaseBook(String username, String password, int cusNumber) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306", username, password);

        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);
        System.out.println(date);


        //Query that gets the biggest transaction ID and makes it into a value called "count"
        PreparedStatement transaction = con.prepareStatement("SELECT max(TransactionID) FROM local.transaction");
        ResultSet rs = transaction.executeQuery();
        rs.next();
        int count = rs.getInt(1);



        Scanner input = new Scanner(System.in) ;
        System.out.print("Please enter your book title you'd like to purchase:  ");
        String bookTitle = input.nextLine();

        PreparedStatement ps = con.prepareStatement("SELECT * FROM local.books WHERE BookTitle = ?");
        ps.setString(1, bookTitle);
        ResultSet is = ps.executeQuery();

        String b = null;
        int balance = 0;

        while(is.next()) {
            b = is.getString("ISBN");
            balance = is.getInt("Price");
        }

        //This inserts the transaction when the user finished their purchase
        PreparedStatement purchaseBook = con.prepareStatement("INSERT INTO local.transaction (TransactionID, CustomerID,Transaction_Date, ISBN,Transaction_Price, Rental, RentalID) VALUES (?, ?, ?, ? , ?, 0, 1)");
        purchaseBook.setInt(1, count + 1);
        purchaseBook.setInt(2, cusNumber);
        purchaseBook.setDate(3, date);
        purchaseBook.setString(4, b);
        purchaseBook.setInt(5, balance);
        purchaseBook.executeUpdate();


        PreparedStatement currentBalance = con.prepareStatement("SELECT Balance FROM local.customer WHERE CustomerID = ?");
        currentBalance.setInt(1, cusNumber);
        ResultSet cb = transaction.executeQuery();
        cb.next();
        int currBalance = cb.getInt(1);

        PreparedStatement customerBalance = con.prepareStatement("UPDATE local.customer SET Balance = ? + customer.Balance WHERE CustomerID = ?");
        customerBalance.setInt(2, cusNumber);
        customerBalance.setInt(1, currBalance);
        customerBalance.executeUpdate();

        System.out.println("Your balance has been updated.");
    }


    public static void returnBook(String username, String password) throws ClassNotFoundException, SQLException {
        //Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306", username, password);

        //Prompt User for Book Information
        Scanner input = new Scanner(System.in);
        System.out.print("Enter your CustomerID: ");
        String customerID = input.next();

        PreparedStatement bookID = con.prepareStatement("SELECT local.transaction.RentalID, local.transaction.ISBN, local.rent_status.Date_Due, local.rent_status.Date_Returned " +
                "FROM local.transaction JOIN local.rent_status ON local.transaction.RentalID = local.rent_status.RentalID" +
                " WHERE CustomerID = ? AND Rental = 1");

        bookID.setInt(1, Integer.parseInt(customerID));
        ResultSet rs = bookID.executeQuery();

        rs.next();
        String b = rs.getString("RentalID");
        int i = rs.getInt("ISBN");
        Date cd = rs.getDate("Date_Due");
        Date rd = rs.getDate("Date_Returned");

        PreparedStatement bookTitle = con.prepareStatement("SELECT BookTitle FROM local.books WHERE ISBN = ?");
        bookTitle.setInt(1, i);
        ResultSet ns = bookTitle.executeQuery();

        ns.next();
        String t = ns.getString("BookTitle");

        System.out.print("\n" + "Rental ID: " + b + "\n" + "ISBN: " + i + "\nBook Title: " + t + "\n" + "Due Date: " + cd + "\n");

        long millis = System.currentTimeMillis();
        java.sql.Date currentDate = new java.sql.Date(millis);

        if (rd == null) {
            System.out.printf("Would you like to return \"%s\" to the Book store?\n", t);
            System.out.printf("Yes/No: ", t);
            char user = input.next().charAt(0);
            if(user == 'y' || user == 'Y') {
                PreparedStatement prs = con.prepareStatement("UPDATE local.rent_status SET Date_Returned = ? WHERE RentalID = ?");
                prs.setDate(1, currentDate);
                prs.setString(2, b);
                prs.executeUpdate();
                prs.close();
            }

            else if (user == 'n' || user == 'N')
                System.out.printf("\'%s\' has NOT been returned.", t);

            else
                System.out.println("Command not found. Book NOT returned.");
        }

        else if (rd != null)
            System.out.println("There is No book to return.");

        else
            System.out.println("No Customer Information Found");
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


    public static float viewBalance(int CusNumb) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306", username, password);

        PreparedStatement ps = con.prepareStatement("SELECT CustomerID, Balance FROM local.customer WHERE CustomerID =?");
        ps.setInt(1, CusNumb);

        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getFloat("Balance");
    }


    public static void changeBalance(int CusNumb, float finalBalance) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306", username, password);

        PreparedStatement ps = con.prepareStatement("UPDATE local.customer SET Balance = ? WHERE CustomerID = ?");
        ps.setFloat(1, finalBalance);
        ps.setInt(2, CusNumb);

        ps.executeUpdate();

        float balance = viewBalance(CusNumb);
        System.out.printf("Your New Balance is $%.2f", balance);
    }


    public static float viewLateFees(int CusNumb) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306", username, password);

        PreparedStatement ps = con.prepareStatement("SELECT CustomerID, LateFees FROM local.customer WHERE CustomerID =?");
        ps.setInt(1, CusNumb);

        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getFloat("LateFees");
    }


    public static void changeLateFees(int CusNumb, float finalLateFees) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306", username, password);

        PreparedStatement ps = con.prepareStatement("UPDATE local.customer SET LateFees = ? WHERE CustomerID = ?");
        ps.setFloat(1, finalLateFees);
        ps.setInt(2, CusNumb);

        ps.executeUpdate();

        float lateFees = viewLateFees(CusNumb);
        System.out.printf("Your New Late Fees Balance is $%.2f", lateFees);
    }


    public static void checkUserBalanceAndLateFees(String username, String password) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306", username, password);
        Scanner input = new Scanner(System.in);


        System.out.println("What would you like to do?\n1. Check Customer Balance\n2. Apply late fees");
        int userInput = input.nextInt();

        if(userInput == 1) {
            System.out.print("Enter Customer Number to view Balance: ");
            int cusNum = input.nextInt();
            PreparedStatement ps = con.prepareStatement("SELECT Balance FROM local.customer WHERE CustomerID =?");
            ps.setInt(1, cusNum);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                String b = rs.getString("Balance");
                System.out.print("User balance: " + b);
            }

        } else if(userInput == 2) {
            System.out.print("Enter Customer Number to apply a late fee: ");
            int cusNum = input.nextInt();
            System.out.println("Enter Late Fee Price: ");
            int LateFee = input.nextInt();
            PreparedStatement ps = con.prepareStatement("INSERT INTO local.customer VALUE (?, ?)");
            ps.setInt(1, cusNum);
            ps.setInt(2, LateFee);
        }
    }
}

/*Users
        •    Search (Done)
        •    Checkout (rent) / buy (Jacob)
        •    Return (Luke)
        •    Check balance, pay balance and late fees (Nathan)
        Admins
        •    Locate title (Done)
        •    Update inventory (add/delete/update titles) (Luke)
        •    Check user balance and apply late fees (Jacob)
        •    Generate Reports (Nathan)
*/
