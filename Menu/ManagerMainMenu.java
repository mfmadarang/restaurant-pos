package Menu;

import Items.*;
import java.util.*;

public class ManagerMainMenu {
    private Scanner scanner;
    private PointOfSale pointOfSale;

    public ManagerMainMenu(PointOfSale pointOfSale) {
        this.pointOfSale = pointOfSale;
        this.scanner = new Scanner(System.in);
    }

    public ManagerMainMenu(ArrayList<ItemCharacteristics> items, Map<String, Integer> drinksCategoryIndex, Map<String, Integer> foodCategoryIndex, Map<String, Integer> merchandiseCategoryIndex) {
    }

    public void showManagerMenu() {
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
                pointOfSale.showMainMenu();
            }
        }
    }

    public void addItem() {
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
                Drink newDrink = new Drink(itemCode, itemName, "Drink", sizesAndPricesMap, category, customizations);
                pointOfSale.Items.add(newDrink);

                System.out.println("Drink added successfully with Item Code: " + itemCode);
            }
            case 2 -> {
                Food newFood = new Food(itemCode, itemName, "Food", sizesAndPricesMap, category, customizations);
                pointOfSale.Items.add(newFood);
                System.out.println("Food item added successfully with Item Code: " + itemCode);
            }
            case 3 -> {
                Merchandise newMerchandise = new Merchandise(itemCode, itemName, "Merchandise", sizesAndPricesMap, category);
                pointOfSale.Items.add(newMerchandise);
                System.out.println("Merchandise added successfully with Item Code: " + itemCode);
            }
        }

        // Store updated data
        pointOfSale.storeDataToTextFile();
    }

    private String generateItemCode(int itemTypeChoice, String category, String itemName) {
        return itemTypeChoice + "-" + category.toUpperCase() + "-" + itemName.substring(0, 3).toUpperCase();
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
        for (ItemCharacteristics item : pointOfSale.Items) {
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
        pointOfSale.storeDataToTextFile();
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
            customizationsMap = (Map<String, Map<String, Float>>) ((Drink) item).getCustomizations();
        } else if (item instanceof Food) {
            customizationsMap = (Map<String, Map<String, Float>>) ((Food) item).getCustomizations();
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
        boolean itemRemoved = pointOfSale.Items.removeIf(item -> item.getItemCode().equals(itemCode));

        if (itemRemoved) {
            System.out.println("Item with Code " + itemCode + " has been removed.");

            // Remove associated customizations if applicable
            pointOfSale.drinkCustomizations.entrySet().removeIf(entry -> entry.getKey().startsWith(itemCode));
            pointOfSale.foodCustomizations.entrySet().removeIf(entry -> entry.getKey().startsWith(itemCode));

            // Update category indices
            updateCategoryIndices();

            // Store updated data
            pointOfSale.storeDataToTextFile();
        } else {
            System.out.println("Item not found with Item Code: " + itemCode);
        }
    }

    private void updateCategoryIndices() {
        // Reset category indices
        pointOfSale.drinksCategoryIndex.clear();
        pointOfSale.foodCategoryIndex.clear();
        pointOfSale.merchandiseCategoryIndex.clear();

        // Rebuild category indices based on remaining items
        for (ItemCharacteristics item : pointOfSale.Items) {
            if (item instanceof Drink) {
                updateCategoryIndex(pointOfSale.drinksCategoryIndex, item.getCategory());
            } else if (item instanceof Food) {
                updateCategoryIndex(pointOfSale.foodCategoryIndex, item.getCategory());
            } else if (item instanceof Merchandise) {
                updateCategoryIndex(pointOfSale.merchandiseCategoryIndex, item.getCategory());
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

    private Map<String, Float> addSizes(int sizeChoice) {
        Map<String, Float> sizesAndPricesMap = new HashMap<>();

        while (sizeChoice == 1) {
            System.out.println("Enter Size Name (or press Enter to finish adding sizes):");
            String sizeName = scanner.nextLine();

            // Break the loop if no size name is entered
            if (sizeName.isEmpty()) {
                break;
            }

            System.out.println("Enter Price for " + sizeName + ":");
            float sizePrice = scanner.nextFloat();
            scanner.nextLine(); // consume newline

            // Add size and price to the map
            sizesAndPricesMap.put(sizeName, sizePrice);

            // Check if user wants to add more sizes
            System.out.println("Do you want to add another size?");
            System.out.println("(1) Yes");
            System.out.println("(2) No");
            sizeChoice = getValidInput(1, 2);
        }

        // If no sizes added, add a default "NS" (No Size) option
        if (sizesAndPricesMap.isEmpty()) {
            System.out.println("No sizes added. Adding default 'NS' (No Size).");
            System.out.println("Enter Price for No Size:");
            float defaultPrice = scanner.nextFloat();
            scanner.nextLine(); // consume newline
            sizesAndPricesMap.put("NS", defaultPrice);
        }

        return sizesAndPricesMap;
    }

    // Helper method for getting valid input between min and max values
    private int getValidInput(int min, int max) {
        int input;
        while (true) {
            try {
                input = scanner.nextInt();
                scanner.nextLine(); // consume newline

                if (input >= min && input <= max) {
                    return input;
                } else {
                    System.out.println("Invalid input. Please enter a number between " + min + " and " + max + ".");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); // clear invalid input
            }
        }
    }
}