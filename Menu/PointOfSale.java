package Menu;

import Items.*;

import java.io.*;
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

    Map <String, Map<String, Float>> drinkCustomizations = new HashMap<>();
    Map <String, Map<String, Float>> foodCustomizations = new HashMap<>();


    public void fetchDataFromTextFile() {
        try {
            // scanning items from item files and storing it into Items ArrayList
            BufferedReader reader = new BufferedReader(new FileReader("Database/Drink.txt"));
            scanItemsFromFile(reader);

            reader = new BufferedReader(new FileReader("Database/Food.txt"));
            scanItemsFromFile(reader);

            reader = new BufferedReader(new FileReader("Database/Merchandise.txt"));
            scanItemsFromFile(reader);

            // scanning category index from respective text files and storing it into respective category index map
            reader = new BufferedReader(new FileReader("Database/DrinkCategoryIndex.txt"));
            drinksCategoryIndex = scanCategoryIndexFromFile(reader);

            reader = new BufferedReader(new FileReader("Database/FoodCategoryIndex.txt"));
            foodCategoryIndex = scanCategoryIndexFromFile(reader);

            reader = new BufferedReader(new FileReader("Database/MerchandiseCategoryIndex.txt"));
            merchandiseCategoryIndex = scanCategoryIndexFromFile(reader);

            // scanning customizations from respective text files and storing it into customizations map
            reader = new BufferedReader(new FileReader("Database/DrinkCustomizations.txt"));
            drinkCustomizations = scanCustomizationsFromFile(reader);

            reader = new BufferedReader(new FileReader("Database/FoodCustomizations.txt"));
            foodCustomizations = scanCustomizationsFromFile(reader);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void scanItemsFromFile(BufferedReader reader) throws IOException {
        String line = reader.readLine();

        while (line != null) {
            String[] data = line.split(",");
            String itemCode = data[0];
            String name = data[1];
            String itemType = data[2];
            String category = data[3];
            Map<String, Float> sizesAndPricesMap = new HashMap<>();

            line = reader.readLine();
            data = line.split(",");

            for (String datum : data) {
                String[] data2 = datum.split(":");
                sizesAndPricesMap.put(data2[0], Float.parseFloat(data2[1]));
            }

            switch (itemType) {
                case "Drink" -> Items.add(new Drink(itemCode, name, itemType, sizesAndPricesMap, category));
                case "Food" -> Items.add(new Food(itemCode, name, itemType, sizesAndPricesMap, category));
                case "Merchandise" -> Items.add(new Merchandise(itemCode, name, itemType, sizesAndPricesMap, category));
            }

            line = reader.readLine();

        }
    }

    private Map<String, Integer> scanCategoryIndexFromFile(BufferedReader reader) throws IOException {
        String line = reader.readLine();
        Map<String, Integer> CategoryIndex = new HashMap<>();
        while (line != null) {
            String[] data = line.split(",");
            String category = data[0];
            Integer index = Integer.parseInt(data[1]);
            line = reader.readLine();

            CategoryIndex.put(category, index);
        }

        return CategoryIndex;
    }

    private Map<String, Map<String, Float>> scanCustomizationsFromFile(BufferedReader reader) throws IOException {
        String line = reader.readLine();
        Map<String, Map<String, Float>> customizations = new HashMap<>();

        while (line != null) {
            String[] data = line.split("-");
            String customization = data[0];
            String[] optionsAndPricesList = data[1].split(",");

            Map<String, Float> optionsAndPricesMap = new HashMap<>();

            for (String optionAndPrices : optionsAndPricesList) {
                String[] data2 = optionAndPrices.split(":");
                optionsAndPricesMap.put(data2[0], Float.parseFloat(data2[1]));
            }

            customizations.put(customization, optionsAndPricesMap);
            line = reader.readLine();
        }
        return customizations;
    }

    public void storeDataToTextFile() {
        try {
            //writing item details from Items ArrayList to respective text file
            BufferedWriter writer1 = new BufferedWriter(new FileWriter("Database/Drink.txt"));
            BufferedWriter writer2 = new BufferedWriter(new FileWriter("Database/Food.txt"));
            BufferedWriter writer3 = new BufferedWriter(new FileWriter("Database/Merchandise.txt"));

            for (ItemCharacteristics item : Items) {
                if(item instanceof Drink) {
                    writeItemsToTextFile(writer1, "Drink", item);
                }

                else if(item instanceof Food) {
                    writeItemsToTextFile(writer2, "Food", item);
                }
                else if(item instanceof Merchandise) {
                    writeItemsToTextFile(writer3, "Merchandise", item);
                }
            }
            writer1.close();
            writer2.close();
            writer3.close();

            // writing category index to respective text file
            BufferedWriter writer4 = new BufferedWriter(new FileWriter("Database/DrinkCategoryIndex.txt"));
            writeCategoryIndexToTextFile(writer4, drinksCategoryIndex);

            writer4 = new BufferedWriter(new FileWriter("Database/FoodCategoryIndex.txt"));
            writeCategoryIndexToTextFile(writer4, foodCategoryIndex);

            writer4 = new BufferedWriter(new FileWriter("Database/MerchandiseCategoryIndex.txt"));
            writeCategoryIndexToTextFile(writer4, merchandiseCategoryIndex);

            // writing each customization to respective text file
            writer4 = new BufferedWriter(new FileWriter("Database/DrinkCustomizations.txt"));
            writeCustomizationsToTextFile(writer4, drinkCustomizations);

            writer4 = new BufferedWriter(new FileWriter("Database/FoodCustomizations.txt"));
            writeCustomizationsToTextFile(writer4, foodCustomizations);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeItemsToTextFile(BufferedWriter writer, String itemType, ItemCharacteristics item) throws IOException {

        writer.write(item.getItemCode() + "," + item.getName() + "," + itemType + "," + item.getCategory());
        writer.newLine();
        for (Map.Entry<String, Float> entry : item.getSizesAndPrices().entrySet()) {
            writer.write(entry.getKey() + ":" + entry.getValue() + ",");
        }
        writer.newLine();
    }

    private void writeCategoryIndexToTextFile(BufferedWriter writer, Map<String, Integer> categoryIndex) throws IOException {
        for(Map.Entry<String, Integer> entry : categoryIndex.entrySet()) {
            writer.write(entry.getKey() + "," + entry.getValue());
            writer.newLine();
        }
    }

    private void writeCustomizationsToTextFile(BufferedWriter writer, Map<String, Map<String, Float>> customizations) throws IOException {
        for(Map.Entry<String, Map<String, Float>> entry : customizations.entrySet()) {
            writer.write(entry.getKey() + "-");
            Map<String, Float> optionAndPricesMap = entry.getValue();

            for(Map.Entry<String, Float> optionAndPricesEntry : optionAndPricesMap.entrySet()) {
                writer.write(optionAndPricesEntry.getKey() + ":" + optionAndPricesEntry.getValue() + ",");
            }
        }
    }


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
