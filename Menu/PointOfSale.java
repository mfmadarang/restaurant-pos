package Menu;

import Items.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class PointOfSale {
    private Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        PointOfSale pos = new PointOfSale();
        pos.showMainMenu();
    }

    ArrayList<ItemCharacteristics> Items = new ArrayList<>();
    Map <String, Integer> drinksCategoryIndex = new HashMap<>();
    Map <String, Integer> foodCategoryIndex = new HashMap<>();
    Map <String, Integer> merchandiseCategoryIndex = new HashMap<>();

    // Main Menu for user role selection
    public void showMainMenu() {
        System.out.println("Select User:");
        System.out.println("(1) Cashier");
        System.out.println("(2) Manager");
        System.out.println("(3) Quit");

        int choice = getValidInput(1, 3);

        switch (choice) {
            case 1 -> showCashierMenu();
            case 2 -> showManagerMenu();
            case 3 -> System.out.println("Exiting the system. Goodbye!");
        }
    }

    // Menu for Cashier
    private void showCashierMenu() {
        System.out.println("Welcome, Cashier!");
        System.out.println("Select option:");
        System.out.println("(1) Place Order");
        System.out.println("(2) Logout");

        int choice = getValidInput(1, 2);

        switch (choice) {
            case 1 -> startTransaction();
            case 2 -> {
                System.out.println("Logging out...");
                showMainMenu();
            }
        }
    }

    // Menu for Manager
    private void showManagerMenu() {
        System.out.println("Welcome, Manager!");
        System.out.println("Select option:");
        System.out.println("(1) Add Item");
        System.out.println("(2) Modify Item");
        System.out.println("(3) Remove Item");
        System.out.println("(4) Logout");

        int choice = getValidInput(1, 4);

        switch (choice) {
            case 1 -> addItem();
            case 2 -> modifyItem();
            case 3 -> removeItem();
            case 4 -> {
                System.out.println("Logging out...");
                showMainMenu();
            }
        }
    }

    // Start a new transaction for the Cashier
    private void startTransaction() {
        System.out.println("Select Item Type:");
        System.out.println("(1) Drinks");
        System.out.println("(2) Food");
        System.out.println("(3) Merchandise");
        System.out.println("(4) Cancel Transaction");

        int choice = getValidInput(1, 4);

        switch (choice) {
            case 1 -> selectDrink();
            case 2 -> selectFood();
            case 3 -> selectMerchandise();
            case 4 -> {
                System.out.println("Transaction canceled.");
                showCashierMenu();
            }
        }
    }

    // Methods for item selection
    private void selectDrink() {
        System.out.println("Select Drink Category:");
        System.out.println("(1) Espresso Drinks");
        System.out.println("(2) Blended Drinks");
        System.out.println("(3) Tea");
        System.out.println("(4) Others");

        int choice = getValidInput(1, 4);
        System.out.println("You selected drink category " + choice + ". Implement logic here.");
    }

    private void selectFood() {
        System.out.println("Select Food Category:");
        System.out.println("(1) Pastries");
        System.out.println("(2) Cakes");
        System.out.println("(3) Sandwiches");
        System.out.println("(4) Pastas");
        System.out.println("(5) Others");

        int choice = getValidInput(1, 5);
        System.out.println("You selected food category " + choice + ". Implement logic here.");
    }

    private void selectMerchandise() {
        System.out.println("Select Merchandise Category:");
        System.out.println("(1) T-Shirts");
        System.out.println("(2) Bags");
        System.out.println("(3) Mugs");
        System.out.println("(4) Others");

        int choice = getValidInput(1, 4);
        System.out.println("You selected merchandise category " + choice + ". Implement logic here.");
    }


    private void addItem() {
        System.out.println("Select Item Type: ");
        System.out.println("(1) Drinks");
        System.out.println("(2) Food");
        System.out.println("(3) Merchandise");

        int itemTypeChoice = getValidInput(1, 3);

        System.out.println("Input Item Name: ");
        String itemName = scanner.nextLine();

        System.out.println("Add different Sizes? ");
        System.out.println("(1) Yes");
        System.out.println("(2) No");
        int sizeChoice = getValidInput(1, 2);

        Map<String, Float> sizesAndPricesMap = addSizes(sizeChoice);

        System.out.println("Enter Category: ");
        String category = scanner.nextLine();

        String itemCode = generateItemCode(itemTypeChoice, category, itemName);

    }

    private void modifyItem() {
        System.out.println("Modifying an item...");
        System.out.println("Implement logic to modify items here.");
    }

    private void removeItem() {
        System.out.println("Removing an item...");
        System.out.println("Implement logic to remove items here.");
    }

    // Utility method for addItem()
    private Map<String, Float> addSizes(int sizeChoice) {
        Map<String, Float> sizesAndPricesMap = new HashMap<>();

        while (sizeChoice == 1) {
            System.out.println("Input size: ");
            String sizeName = scanner.nextLine();
            System.out.println("Input price: ");
            float price = scanner.nextFloat();
            scanner.nextLine();

            sizesAndPricesMap.put(sizeName, price);
            System.out.println("Add another size? ");
            System.out.println("(1) Yes");
            System.out.println("(2) No");
            sizeChoice = getValidInput(1, 2);
        }

        if (sizeChoice == 2) {
            System.out.println("Input price: ");
            float price = scanner.nextFloat();
            scanner.nextLine();
            sizesAndPricesMap.put("NS", price);
        }

        return sizesAndPricesMap;
    }

    // Utility method for addItem()
    private String generateItemCode(int itemTypeChoice, String category, String itemName) {
        category = category.toUpperCase();
        String categoryFirstLetter = String.valueOf(category.charAt(0));
        String categoryLastLetter = String.valueOf(category.charAt(category.length() - 1));

        itemName = itemName.toUpperCase();

        return categoryFirstLetter + categoryLastLetter + "-" + itemName.substring(0, 4) + "-" + getItemCodeIndex(itemTypeChoice, category);
    }

    private String getItemCodeIndex (int itemTypeChoice, String category) {
        String itemCodeIndex = "";
        switch (itemTypeChoice) {
            case 1:
                if (drinksCategoryIndex.containsKey(category)) {
                    itemCodeIndex = String.format("%03d", drinksCategoryIndex.get(category)); // get the index with the format of 3 digits
                    drinksCategoryIndex.put(category, drinksCategoryIndex.get(category) + 1); // increment the index

                }
                else {
                    drinksCategoryIndex.put(category, 1);
                }
                break;

            case 2:
                if (foodCategoryIndex.containsKey(category)) {
                    itemCodeIndex = String.format("%03d", foodCategoryIndex.get(category));
                    foodCategoryIndex.put(category, foodCategoryIndex.get(category) + 1);
                }
                else {
                    foodCategoryIndex.put(category, 1);
                }
                break;
            case 3:
                if (merchandiseCategoryIndex.containsKey(category)) {
                    itemCodeIndex = String.format("%03d", merchandiseCategoryIndex.get(category));
                    merchandiseCategoryIndex.put(category, merchandiseCategoryIndex.get(category) + 1);
                }
                else {
                    merchandiseCategoryIndex.put(category, 1);
                }
                break;
        }

        return itemCodeIndex;
    }

    // Utility method for input validation
    private int getValidInput(int min, int max) {
        int input;
        while (true) {
            try {
                System.out.print("Enter your choice: ");
                input = Integer.parseInt(scanner.nextLine());
                if (input >= min && input <= max) {
                    return input;
                } else {
                    System.out.println("Invalid choice. Please select between " + min + " and " + max + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }
}
