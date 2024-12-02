package Menu;

import Items.*;

import java.io.*;
import java.util.*;

public class PointOfSale {
    private Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        PointOfSale pos = new PointOfSale();
        pos.fetchDataFromTextFile();
        pos.showMainMenu();

        for(ItemCharacteristics item: pos.Items) {
            System.out.println(item.getItemCode());
            System.out.println(item.getName());
            System.out.println(item.getCategory());
            item.displaySizesAndPrices();
            System.out.println();
        }
        Map<String, Float> sizesAndPricesMap = new HashMap<>();
        sizesAndPricesMap.put("Large", 50F);
        pos.Items.add(new Drink("SS-SPRI-002", "Sprite", "Drink", sizesAndPricesMap, "Soft Drinks"));

        pos.storeDataToTextFile();

        //pos.showMainMenu();

    }

    ArrayList<ItemCharacteristics> Items = new ArrayList<>();

    Map <String, Integer> drinksCategoryIndex = new HashMap<>();
    Map <String, Integer> foodCategoryIndex = new HashMap<>();
    Map <String, Integer> merchandiseCategoryIndex = new HashMap<>();

    Map<String, Map<String, Float>> drinkCustomizations = new HashMap<>();
    Map<String, Map<String, Float>> foodCustomizations = new HashMap<>();

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
            if (line.trim().isEmpty()) {
                line = reader.readLine();
                continue;
            }

            String[] data = line.split("(?<!\\\\),");
            String itemCode = data[0];
            String name = data[1].replace("\\,", ",");
            String itemType = data[2];
            String category = data[3].replace("\\,", ",");
            Map<String, Float> sizesAndPricesMap = new HashMap<>();

            line = reader.readLine();
            data = line.split("(?<!\\\\),");

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
            if (line.trim().isEmpty()) {
                line = reader.readLine();
                continue;
            }
            String[] data = line.split("(?<!\\\\),");
            String category = data[0].replace("\\,", ",");
            Integer index = Integer.parseInt(data[1]);
            line = reader.readLine();

            CategoryIndex.put(category, index);
        }

        return CategoryIndex;
    }

    private Map<String, Map<String, Float>> scanCustomizationsFromFile(BufferedReader reader) throws IOException {
        Map<String, Map<String, Float>> customizationsMap = new HashMap<>();
        String line = reader.readLine();
        while (line != null) {
            if (line.trim().isEmpty()) {
                line = reader.readLine();
                continue;
            }

            String[] data = line.split("(?<!\\\\)_");
            String customizationName = data[0].replace("\\_", "_").replace("\\,", ",").replace("\\:", ":");

            String[] optionsAndPricesList = data[1].split("(?<!\\\\),");
            Map<String, Float> optionsAndPricesMap = new HashMap<>();
            for (String optionAndPrices : optionsAndPricesList) {
                String[] data2 = optionAndPrices.split("(?<!\\\\):");
                String option = data2[0].replace("\\,", ",").replace("\\:", ":").replace("\\_", "_");
                optionsAndPricesMap.put(option, Float.parseFloat(data2[1]));
            }

            customizationsMap.put(customizationName, optionsAndPricesMap);
            line = reader.readLine();
        }
        return customizationsMap;
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
            BufferedWriter drinkCategoryWriter = new BufferedWriter(new FileWriter("Database/DrinkCategoryIndex.txt"));
            writeCategoryIndexToTextFile(drinkCategoryWriter, drinksCategoryIndex);
            drinkCategoryWriter.close();

            BufferedWriter foodCategoryWriter = new BufferedWriter(new FileWriter("Database/FoodCategoryIndex.txt"));
            writeCategoryIndexToTextFile(foodCategoryWriter, foodCategoryIndex);
            foodCategoryWriter.close();

            BufferedWriter merchandiseCategoryWriter = new BufferedWriter(new FileWriter("Database/MerchandiseCategoryIndex.txt"));
            writeCategoryIndexToTextFile(merchandiseCategoryWriter, merchandiseCategoryIndex);
            merchandiseCategoryWriter.close();


            // writing each customization to respective text file
            BufferedWriter drinkCustomizationWriter = new BufferedWriter(new FileWriter("Database/DrinkCustomizations.txt"));
            writeCustomizationsToTextFile(drinkCustomizationWriter, drinkCustomizations);
            drinkCustomizationWriter.close();

            BufferedWriter foodCustomizationWriter = new BufferedWriter(new FileWriter("Database/FoodCustomizations.txt"));
            writeCustomizationsToTextFile(foodCustomizationWriter, foodCustomizations);
            foodCustomizationWriter.close();


        } catch (IOException e) {
            throw new RuntimeException("Error storing data to text files: " +e.getMessage());
        }
    }

    private void writeItemsToTextFile(BufferedWriter writer, String itemType, ItemCharacteristics item) throws IOException {
        String escapedCategory = item.getCategory().replace(",", "\\,");
        String escapedName = item.getName().replace(",", "\\,");

        writer.write(item.getItemCode() + "," + escapedName + "," + itemType + "," + escapedCategory);
        writer.newLine();

        StringJoiner joiner = new StringJoiner(",");

        for (Map.Entry<String, Float> entry : item.getSizesAndPrices().entrySet()) {
            joiner.add(entry.getKey().replace(",", "\\,").replace(":", "\\:") + ":" + entry.getValue());
        }
        writer.write(joiner.toString());
        writer.newLine();
        writer.newLine();
    }

    private void writeCategoryIndexToTextFile(BufferedWriter writer, Map<String, Integer> categoryIndex) throws IOException {
        for(Map.Entry<String, Integer> entry : categoryIndex.entrySet()) {
            writer.write(entry.getKey().replace(",", "\\,") + "," + entry.getValue());
            writer.newLine();
        }
    }

    private void writeCustomizationsToTextFile(BufferedWriter writer, Map<String, Map<String, Float>> customizations) throws IOException {
        for (Map.Entry<String, Map<String, Float>> customizationEntry : customizations.entrySet()) {
            // Escape customization name
            String escapedCustomizationName = customizationEntry.getKey()
                    .replace("_", "\\_")
                    .replace(",", "\\,")
                    .replace(":", "\\:");

            writer.write(escapedCustomizationName + "_");

            // Process the options and prices
            Map<String, Float> optionsAndPrices = customizationEntry.getValue();
            StringJoiner joiner = new StringJoiner(",");

            for (Map.Entry<String, Float> optionEntry : optionsAndPrices.entrySet()) {
                String escapedOptionName = optionEntry.getKey()
                        .replace(",", "\\,")
                        .replace(":", "\\:")
                        .replace("_", "\\_");
                joiner.add(escapedOptionName + ":" + optionEntry.getValue());
            }

            writer.write(joiner.toString());
            writer.newLine();
        }
    }

    // Main Menu for user role selection
    public void showMainMenu() {
        System.out.println("Select User:");
        System.out.println("(1) Cashier");
        System.out.println("(2) Manager");
        System.out.println("(3) Quit");

        int choice = getValidInput(1, 3);

        CashierMainMenu cashierMainMenu = new CashierMainMenu();

        switch (choice) {
            case 1 -> cashierMainMenu.showCashierMenu();
            case 2 -> {
                ManagerMainMenu managerMainMenu = new ManagerMainMenu(
                        Items,
                        drinksCategoryIndex,
                        foodCategoryIndex,
                        merchandiseCategoryIndex,
                        drinkCustomizations,
                        foodCustomizations
                );
                managerMainMenu.showManagerMenu();
            }
            case 3 -> System.out.println("Exiting the system. Goodbye!");
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

        // Add customizations for Drinks and Food
        Map<String, Map<String, Float>> customizations = null;
        if (itemTypeChoice != 3) { // Not Merchandise
            customizations = addCustomizations(itemTypeChoice);
        }

        // Create the item based on type
        switch (itemTypeChoice) {
            case 1 -> {
                Drink newDrink = new Drink(itemCode, itemName, "Drink", sizesAndPricesMap, category);
                Items.add(newDrink);
                if (customizations != null) {
                    drinkCustomizations.putAll(customizations);
                }
                System.out.println("Drink added successfully with Item Code: " + itemCode);
            }
            case 2 -> {
                Food newFood = new Food(itemCode, itemName, "Food", sizesAndPricesMap, category);
                Items.add(newFood);
                if (customizations != null) {
                    foodCustomizations.putAll(customizations);
                }
                System.out.println("Food item added successfully with Item Code: " + itemCode);
            }
            case 3 -> {
                Merchandise newMerchandise = new Merchandise(itemCode, itemName, "Merchandise", sizesAndPricesMap, category);
                Items.add(newMerchandise);
                System.out.println("Merchandise added successfully with Item Code: " + itemCode);
            }
        }

        // Store updated data
        storeDataToTextFile();
    }

    private Map<String, Map<String, Float>> addCustomizations(int itemTypeChoice) {
        Map<String, Map<String, Float>> customizations = new HashMap<>();

        System.out.println("Do you want to add customizations?");
        System.out.println("(1) Yes");
        System.out.println("(2) No");
        int customizationChoice = getValidInput(1, 2);

        while (customizationChoice == 1) {
            System.out.println("Enter Customization Name:");
            String customizationName = scanner.nextLine();

            Map<String, Float> optionsAndPrices = new HashMap<>();
            int optionCount = 0;
            while (optionCount < 5) {
                System.out.println("Enter Customization Option (or press Enter to finish):");
                String optionName = scanner.nextLine();
                if (optionName.isEmpty()) break;

                System.out.println("Enter Price for " + optionName + ":");
                float optionPrice = scanner.nextFloat();
                scanner.nextLine(); // consume newline

                optionsAndPrices.put(optionName, optionPrice);
                optionCount++;

                if (optionCount == 5) {
                    System.out.println("Maximum number of options (5) reached.");
                    break;
                }
            }

            customizations.put(customizationName, optionsAndPrices);

            System.out.println("Do you want to add another customization?");
            System.out.println("(1) Yes");
            System.out.println("(2) No");
            customizationChoice = getValidInput(1, 2);
        }

        return customizations.isEmpty() ? null : customizations;
    }

    private void modifyItem() {
        System.out.println("Enter Item Code to Modify:");
        String itemCode = scanner.nextLine();

        // Find the item with the given item code
        ItemCharacteristics itemToModify = null;
        for (ItemCharacteristics item : Items) {
            if (item.getItemCode().equals(itemCode)) {
                itemToModify = item;
                break;
            }
        }

        if (itemToModify == null) {
            System.out.println("Item not found with Item Code: " + itemCode);
            return;
        }

        System.out.println("Select what to modify:");
        System.out.println("(1) Sizes and Prices");
        System.out.println("(2) Customizations");
        System.out.println("(3) Cancel");

        int modifyChoice = getValidInput(1, 3);

        switch (modifyChoice) {
            case 1 -> modifySizesAndPrices(itemToModify);
            case 2 -> modifyCustomizations(itemToModify);
            case 3 -> {
                System.out.println("Modification canceled.");
                return;
            }
        }

        // Store updated data
        storeDataToTextFile();
    }

    private void modifySizesAndPrices(ItemCharacteristics item) {
        Map<String, Float> sizesAndPrices = item.getSizesAndPrices();

        while (true) {
            System.out.println("Current Sizes and Prices:");
            for (Map.Entry<String, Float> entry : sizesAndPrices.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }

            System.out.println("Select action:");
            System.out.println("(1) Add/Modify Size");
            System.out.println("(2) Remove Size");
            System.out.println("(3) Finish");

            int choice = getValidInput(1, 3);

            switch (choice) {
                case 1 -> {
                    System.out.println("Enter Size Name:");
                    String sizeName = scanner.nextLine();
                    System.out.println("Enter Price:");
                    float price = scanner.nextFloat();
                    scanner.nextLine(); // consume newline
                    sizesAndPrices.put(sizeName, price);
                }
                case 2 -> {
                    System.out.println("Enter Size Name to Remove:");
                    String sizeToRemove = scanner.nextLine();
                    if (sizesAndPrices.containsKey(sizeToRemove)) {
                        sizesAndPrices.remove(sizeToRemove);
                    } else {
                        System.out.println("Size not found.");
                    }
                }
                case 3 -> {
                    return;
                }
            }
        }
    }

    private void modifyCustomizations(ItemCharacteristics item) {
        // Determine which customization map to use based on item type
        Map<String, Map<String, Float>> customizationsMap = null;
        if (item instanceof Drink) {
            customizationsMap = drinkCustomizations;
        } else if (item instanceof Food) {
            customizationsMap = foodCustomizations;
        } else {
            System.out.println("Customizations are only available for Drinks and Food.");
            return;
        }

        while (true) {
            System.out.println("Current Customizations:");
            if (customizationsMap.isEmpty()) {
                System.out.println("No customizations found.");
            } else {
                for (String customization : customizationsMap.keySet()) {
                    System.out.println(customization + ":");
                    Map<String, Float> options = customizationsMap.get(customization);
                    for (Map.Entry<String, Float> option : options.entrySet()) {
                        System.out.println("  " + option.getKey() + ": " + option.getValue());
                    }
                }
            }

            System.out.println("Select action:");
            System.out.println("(1) Add/Modify Customization");
            System.out.println("(2) Remove Customization");
            System.out.println("(3) Modify Customization Options");
            System.out.println("(4) Finish");

            int choice = getValidInput(1, 4);

            switch (choice) {
                case 1 -> {
                    System.out.println("Enter Customization Name:");
                    String customizationName = scanner.nextLine();
                    Map<String, Float> optionsAndPrices = new HashMap<>();

                    int optionCount = 0;
                    while (optionCount < 5) {
                        System.out.println("Enter Customization Option (or press Enter to finish):");
                        String optionName = scanner.nextLine();
                        if (optionName.isEmpty()) break;

                        System.out.println("Enter Price for " + optionName + ":");
                        float optionPrice = scanner.nextFloat();
                        scanner.nextLine(); // consume newline

                        optionsAndPrices.put(optionName, optionPrice);
                        optionCount++;

                        if (optionCount == 5) {
                            System.out.println("Maximum number of options (5) reached.");
                            break;
                        }
                    }

                    customizationsMap.put(customizationName, optionsAndPrices);
                }
                case 2 -> {
                    System.out.println("Enter Customization Name to Remove:");
                    String customizationToRemove = scanner.nextLine();
                    if (customizationsMap.containsKey(customizationToRemove)) {
                        customizationsMap.remove(customizationToRemove);
                    } else {
                        System.out.println("Customization not found.");
                    }
                }
                case 3 -> {
                    System.out.println("Enter Customization Name to Modify:");
                    String customizationToModify = scanner.nextLine();
                    if (customizationsMap.containsKey(customizationToModify)) {
                        Map<String, Float> options = customizationsMap.get(customizationToModify);

                        while (true) {
                            System.out.println("Current Options for " + customizationToModify + ":");
                            for (Map.Entry<String, Float> option : options.entrySet()) {
                                System.out.println(option.getKey() + ": " + option.getValue());
                            }

                            System.out.println("Select action:");
                            System.out.println("(1) Add/Modify Option");
                            System.out.println("(2) Remove Option");
                            System.out.println("(3) Finish");

                            int optionChoice = getValidInput(1, 3);

                            switch (optionChoice) {
                                case 1 -> {
                                    System.out.println("Enter Option Name:");
                                    String optionName = scanner.nextLine();
                                    System.out.println("Enter Price:");
                                    float optionPrice = scanner.nextFloat();
                                    scanner.nextLine(); // consume newline
                                    options.put(optionName, optionPrice);
                                }
                                case 2 -> {
                                    System.out.println("Enter Option Name to Remove:");
                                    String optionToRemove = scanner.nextLine();
                                    if (options.containsKey(optionToRemove)) {
                                        options.remove(optionToRemove);
                                    } else {
                                        System.out.println("Option not found.");
                                    }
                                }
                                case 3 -> {
                                    break;
                                }
                            }

                            if (optionChoice == 3) break;
                        }
                    } else {
                        System.out.println("Customization not found.");
                    }
                }
                case 4 -> {
                    return;
                }
            }
        }
    }

    private void removeItem() {
        System.out.println("Enter Item Code to Remove:");
        String itemCode = scanner.nextLine();

        // Find and remove the item with the given item code
        boolean itemRemoved = Items.removeIf(item -> item.getItemCode().equals(itemCode));

        if (itemRemoved) {
            System.out.println("Item with Code " + itemCode + " has been removed.");

            // Remove associated customizations if applicable
            drinkCustomizations.entrySet().removeIf(entry -> entry.getKey().startsWith(itemCode));
            foodCustomizations.entrySet().removeIf(entry -> entry.getKey().startsWith(itemCode));

            // Update category indices
            updateCategoryIndices();

            // Store updated data
            storeDataToTextFile();
        } else {
            System.out.println("Item not found with Item Code: " + itemCode);
        }
    }

    private void updateCategoryIndices() {
        // Reset category indices
        drinksCategoryIndex.clear();
        foodCategoryIndex.clear();
        merchandiseCategoryIndex.clear();

        // Rebuild category indices based on remaining items
        for (ItemCharacteristics item : Items) {
            if (item instanceof Drink) {
                updateCategoryIndex(drinksCategoryIndex, item.getCategory());
            } else if (item instanceof Food) {
                updateCategoryIndex(foodCategoryIndex, item.getCategory());
            } else if (item instanceof Merchandise) {
                updateCategoryIndex(merchandiseCategoryIndex, item.getCategory());
            }
        }
    }

    private void updateCategoryIndex(Map<String, Integer> categoryIndex, String category) {
        if (categoryIndex.containsKey(category)) {
            categoryIndex.put(category, categoryIndex.get(category) + 1);
        } else {
            categoryIndex.put(category, 1);
        }
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