package MainMenu;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class PointOfSale {

    public void displayUserSelection() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select User: ");
        System.out.println("(1) Cashier");
        System.out.println("(2) Manager");
        System.out.println("(3) Quit");
        System.out.print("Input choice: ");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                   displayCashierMainMenu();
                   break;
            case "2":
                    displayManagerMainMenu();
                    break;
            case "3":
                System.out.println("You have exited the Point of Sale System. Thank you!");
                break;
            default:
                System.out.println("Invalid input please try again.");
        }
    }

    public void displayCashierMainMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome, Cashier!");
        System.out.println("Select option: ");
        System.out.println("(1) Place order");
        System.out.println("(2) Logout");
        System.out.print("Input choice: ");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                selectCashierItemType();
                break;

            case "2":
                displayUserSelection();
                break;

            default:
                System.out.println("Invalid input please try again.");
                /** Do I add displayUserSelection() again so that it could return to the UserSelection or wag na? */
        }

    }

    public void displayManagerMainMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome, Manager!");
        System.out.println("Select option: ");
        System.out.println("(1) Add Item");
        System.out.println("(2) Modify Item");
        System.out.println("(3) Remove Item");
        System.out.print("Input choice: ");
        String choice = scanner.nextLine();

        // Manager manager = new Manager();
        switch (choice) {
            case "1":
                System.out.println("Select Item Type: ");
                System.out.println("(1) Drinks");
                System.out.println("(2) Food");
                System.out.println("(3) Tea");
                System.out.print("Input choice: ");
                String itemTypeChoice = scanner.nextLine();
                String itemType;

                switch (itemTypeChoice) {
                    case "1":
                        itemType = "Drinks";
                    case "2":
                        itemType = "Food";
                    case "3":
                        itemType = "Merchandise";

                    default:
                        System.out.println("Invalid input, please try again.");
                }

                System.out.print("Input item name: ");
                String itemName = scanner.nextLine();

                System.out.println("Add Different Sizes? [Y/N]: ");
                String sizeChoice = scanner.nextLine();

                Map<String, Float> sizesAndPricesMap = new HashMap<String, Float>();

                switch (sizeChoice) {
                    case "Y":

                        while (sizeChoice.equals("Y")) {
                            System.out.println("Input Size: ");
                            String size = scanner.nextLine();
                            System.out.println("Input Price: ");
                            float price = scanner.nextFloat();
                            scanner.nextLine();
                            sizesAndPricesMap.put(size, price);

                            boolean condition = true;

                            while (condition) {
                                System.out.println("Add another size? [Y/N]: ");
                                sizeChoice = scanner.nextLine();

                                if (sizeChoice.equals("Y") || sizeChoice.equals("N")) {
                                    condition = false;
                                }
                                else {
                                    System.out.println("Invalid input, please try again.");
                                }
                            }

                        }

                    case "N":
                        System.out.println("Input price: ");
                        float price = scanner.nextFloat();
                        scanner.nextLine();
                        sizesAndPricesMap.put("NS", price);

                } // end of sizeChoice switch statement
                break;

            case "2":
                break;

            case "3":
                break;

            default:
                System.out.println("Invalid input, please try again.");
        } // end of Cashier Main Menu Switch Statement

    }

    public void selectCashierItemType(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select Item Type");
        System.out.println("(1) Drinks");
        System.out.println("(2) Food");
        System.out.println("(3) Merchandise");
        System.out.println("(4) Cancel Transaction");
        System.out.print("Input choice: ");
        String itemchoice = scanner.nextLine();

        switch (itemchoice) {
            case "1":
                selectDrinkType();
                break;

            case "2":
                selectFoodType();
                break;

            case "3":
                selectMerchandiseType();
                break;

            case "4":
                displayCashierMainMenu();
                break;

            default:
                System.out.println("Invalid input, please try again.");
        }

    }

    public void selectDrinkType(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Options: ");
        System.out.println("(1) Espresso Drinks");
        System.out.println("(2) Blended Drinks");
        System.out.println("(3) Tea");
        System.out.println("(4) Others");
        System.out.print("Input drink choice: ");
        String drinkchoice = scanner.nextLine();
        switch (drinkchoice) {

            case "1":
                break;

            case "2":
                break;

            case "3":
                break;

            case "4":
                break;

            default:
                System.out.println("Invalid input, please try again.");
                System.out.println("Returning to Cashier to restart order");
                displayCashierMainMenu();
        }
    }

    public void selectFoodType(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Options: ");
        System.out.println("(1) Pastries");
        System.out.println("(2) Cakes");
        System.out.println("(3) Sandwiches");
        System.out.println("(4) Pastas");
        System.out.print("Input food type: ");
        String foodtype = scanner.nextLine();
        switch (foodtype) {

            case "1":
                break;

            case "2":
                break;

            case "3":
                break;

            case "4":
                break;

            default:
                System.out.println("Invalid input, please try again.");
                System.out.println("Returning to Cashier to restart order");
                displayCashierMainMenu();
        }
    }

    public void selectMerchandiseType(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Options: ");
        System.out.println("(1) T-shirts");
        System.out.println("(2) Bags");
        System.out.println("(3) Mugs");
        System.out.println("(4) Others");
        System.out.print("Input the merchandise type: ");
        String merchandisechoice = scanner.nextLine();
        switch (merchandisechoice) {

            case "1":
                break;

            case "2":
                break;

            case "3":
                break;

            case "4":
                break;

            default:
                System.out.println("Invalid input, please try again.");
                System.out.println("Returning to Cashier to restart order");
                displayCashierMainMenu();
        }
    }

}
