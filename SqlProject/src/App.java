import Java.util.Scanner;
/*
    TODO:
    1. generate queries for each option
    2. setup some type of login?
    3. make it more "user friendly" and test queries before presentation
 */

public class App {
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        System.out.print("Are you a User or an Admin?\n");
        String user = input.nextLine();
        if(user.equalsIgnoreCase("User")) {
            System.out.println("What would you like to do?");
            System.out.println("1. Search\n2. Checkout\n3. Return\n4. Check Balance, Pay Balance and late fees\n5. Log Out\n");
            int userInput = input.nextInt();
            if(userInput == 1){
                System.out.println("Enter your Book Title: ");
                //query based on user input for a title, could use same query as in the "locate title" in admin panel
            } else if (userInput == 2) {
                System.out.println("Checkout!");
                //checkout could link to two options, rent or buy
                }
            } else if (userInput == 3) {
                System.out.println("Books to return: ");
                //query books that user has out as "rented", and give them price for each book?
            } else if (userInput == 4) {
                System.out.println("What would you like to do?");
                switch(/*needs some type of user input, not sure if we can use "userInput bc idk how to clear Scanner*/)
                    case 1:
                        //Check balance based on customer number?
                    case 2:
                        //Pay outstanding balance, and or late fees using "credit card" info?
            } else {
                System.out.println("Command not recognised");
            }
        } else if (user.equalsIgnoreCase("Admin")) {
            System.out.println("What would you like to do?");
            System.out.println("1. Locate Title\n2. Update inventory\n3. Check Balance\n4. Generate Reports\n5. Log Out\n");
            int userInput = input.nextInt();
            if(userInput == 1){
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
        }
        //closing userInput bc it yells at me for mem issues
        input.close();
    }
}

