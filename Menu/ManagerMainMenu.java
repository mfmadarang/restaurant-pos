package Menu;

import Items.*;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ManagerMainMenu {
    private final Scanner scanner;
    private final PointOfSale pointOfSale;

    public ManagerMainMenu(PointOfSale pointOfSale) {
        this.pointOfSale = pointOfSale;
        this.scanner = new Scanner(System.in);
    }

    public void showManagerMenu() {
        while (true) {
            System.out.println("""
        ===========================================
                     MANAGER MENU
        ===========================================
        Welcome, Manager! Please select an option:
        
        (1) Add Item              - Add a new item to the inventory
        (2) Modify Item           - Edit an existing item's details
        (3) Remove Item           - Delete an item from the inventory
        (4) Import Items from File - Upload items from an external file
        (5) Logout                - Exit to the Main Menu
        
        ===========================================
        Enter your choice (1-5):
        """);

            int choice = getValidInput(5);

            switch (choice) {
                case 1 -> addItem();
                case 2 -> modifyItem();
                case 3 -> removeItem();
                case 4 -> importItemsFromFile();
                case 5 -> {
                    System.out.println("\nLogging out... Returning to the Main Menu.");
                    return;
                }
            }
        }
    }

    public void importItemsFromFile() {
        System.out.println("""
    ===========================================
                  IMPORT ITEMS
    ===========================================
    Enter the path to the file you wish to import:
    """);

        String filePath = scanner.nextLine();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int successCount = 0;
            int errorCount = 0;

            while ((line = reader.readLine()) != null) {
                try {
                    processFileImportLine(line);
                    successCount++;
                } catch (Exception e) {
                    System.out.println("Error importing line: " + line + " - " + e.getMessage());
                    errorCount++;
                }
            }

            System.out.println("""
        ===========================================
                  IMPORT SUMMARY
        ===========================================
        Import completed.
        Successfully imported: %d items
        Failed to import: %d items
        ===========================================
        """.formatted(successCount, errorCount));

            pointOfSale.storeDataToTextFile();
        } catch (IOException e) {
            System.out.println("""
        ===========================================
                  IMPORT ERROR
        ===========================================
        Error reading file: %s
        ===========================================
        """.formatted(e.getMessage()));
        }
    }

    private void processFileImportLine(String line) {
        String[] parts = line.split(",");
        if (parts.length < 5) {
            throw new IllegalArgumentException("Insufficient data in line");
        }

        // Parse line: ItemType, Name, Category, Size1:Price1, Size2:Price2, Customization1:Option1:Price1, ...
        String itemType = parts[0].trim();
        String itemName = parts[1].trim();
        String category = parts[2].trim();

        // Parse sizes and prices
        Map<String, Float> sizesAndPricesMap = parseSizesAndPrices(parts[3], parts[4]);

        // Determine item type for creation
        int itemTypeChoice = getItemTypeChoice(itemType);

        // Parse customizations (optional)
        Map<String, Map<String, Float>> customizations =
                parts.length > 5 ? parseCustomizations(Arrays.copyOfRange(parts, 5, parts.length)) : null;

        // Generate item code
        String itemCode = generateItemCode(itemTypeChoice, category, itemName);

        // Create item based on type
        switch (itemTypeChoice) {
            case 1 -> {
                Drink newDrink = new Drink(itemCode, itemName, "Drink", sizesAndPricesMap, category, customizations);
                pointOfSale.Items.add(newDrink);
            }
            case 2 -> {
                Food newFood = new Food(itemCode, itemName, "Food", sizesAndPricesMap, category, customizations);
                pointOfSale.Items.add(newFood);
            }
            case 3 -> {
                Merchandise newMerchandise = new Merchandise(itemCode, itemName, "Merchandise", sizesAndPricesMap, category);
                pointOfSale.Items.add(newMerchandise);
            }
        }
    }

    private Map<String, Float> parseSizesAndPrices(String... sizePriceParts) {
        Map<String, Float> sizesAndPricesMap = new HashMap<>();
        for (String sizePricePart : sizePriceParts) {
            String[] sizePrice = sizePricePart.split(":");
            if (sizePrice.length == 2) {
                sizesAndPricesMap.put(sizePrice[0].trim(), Float.parseFloat(sizePrice[1].trim()));
            }
        }
        return sizesAndPricesMap;
    }

    private Map<String, Map<String, Float>> parseCustomizations(String... customizationParts) {
        Map<String, Map<String, Float>> customizations = new HashMap<>();
        for (String customizationPart : customizationParts) {
            String[] parts = customizationPart.split(":");
            if (parts.length >= 3) {
                String customizationName = parts[0].trim();
                String optionName = parts[1].trim();
                Float optionPrice = Float.parseFloat(parts[2].trim());

                customizations.computeIfAbsent(customizationName, k -> new HashMap<>())
                        .put(optionName, optionPrice);
            }
        }
        return customizations.isEmpty() ? null : customizations;
    }

    private int getItemTypeChoice(String itemType) {
        List<String> drinkTypes = Arrays.asList("Drink", "Espresso", "Blended", "Tea");
        List<String> foodTypes = Arrays.asList("Food", "Pastry", "Cake", "Sandwich", "Pasta");
        List<String> merchandiseTypes = Arrays.asList("Merchandise", "TShirt", "Bag", "Mug");

        if (drinkTypes.contains(itemType)) return 1;
        if (foodTypes.contains(itemType)) return 2;
        if (merchandiseTypes.contains(itemType)) return 3;

        throw new IllegalArgumentException("Unknown item type: " + itemType);
    }

    public void addItem() {
        System.out.println("""
===========================================
                ADD NEW ITEM
===========================================
Select the type of item to add:

(1) Drinks        - Add a drink to the inventory
(2) Food          - Add a food item to the inventory
(3) Merchandise   - Add merchandise to the inventory
===========================================
Enter your choice (1-3):
""");

        int itemTypeChoice = getValidInput(3);

        System.out.println("""
===========================================
            ITEM DETAILS
===========================================
Enter the name of the item:
""");
        String itemName = scanner.nextLine();

        System.out.println("""
===========================================
           SIZE OPTIONS
===========================================
Would you like to add different sizes?

(1) Yes
(2) No
===========================================
Enter your choice (1-2):
""");
        int sizeChoice = getValidInput(2);

        Map<String, Float> sizesAndPricesMap = addSizes(sizeChoice);

        System.out.println("""
===========================================
            CATEGORY DETAILS
===========================================
Enter the category of the item:
""");
        String category = scanner.nextLine();

        // Check for existing items before adding
        boolean itemExists = pointOfSale.Items.stream()
                .anyMatch(existingItem ->
                        existingItem.getName().equalsIgnoreCase(itemName) &&
                                existingItem.getCategory().equalsIgnoreCase(category)
                );

        if (itemExists) {
            System.out.println("""
===========================================
          ITEM ALREADY EXISTS
===========================================
An item with the same name and category already exists.
Please choose a different name or category.
===========================================
""");
            return; // Exit the method without adding the item
        }

        String itemCode = generateItemCode(itemTypeChoice, category, itemName);

        Map<String, Map<String, Float>> customizations = null;
        if (itemTypeChoice != 3) { // Add customizations for Drinks and Food
            customizations = addCustomizations(itemTypeChoice);
        }

        System.out.println("""
===========================================
            ADDING ITEM
===========================================
""");

        switch (itemTypeChoice) {
            case 1 -> {
                Drink newDrink = new Drink(itemCode, itemName, "Drink", sizesAndPricesMap, category, customizations);
                pointOfSale.Items.add(newDrink);
                System.out.println("Drink added successfully! Item Code: " + itemCode);
            }
            case 2 -> {
                Food newFood = new Food(itemCode, itemName, "Food", sizesAndPricesMap, category, customizations);
                pointOfSale.Items.add(newFood);
                System.out.println("Food item added successfully! Item Code: " + itemCode);
            }
            case 3 -> {
                Merchandise newMerchandise = new Merchandise(itemCode, itemName, "Merchandise", sizesAndPricesMap, category);
                pointOfSale.Items.add(newMerchandise);
                System.out.println("Merchandise added successfully! Item Code: " + itemCode);
            }
        }

        System.out.println("""
===========================================
        ITEM SUCCESSFULLY ADDED
===========================================
Item has been added to the inventory. The data has been saved.
===========================================
""");

        // Store updated data
        pointOfSale.storeDataToTextFile();
    }


    private String generateItemCode(int itemTypeChoice, String category, String itemName) {
        // Default categories if not provided
        if (category == null || category.trim().isEmpty()) {
            switch (itemTypeChoice) {
                case 1 -> category = "GENERAL DRINK";
                case 2 -> category = "GENERAL FOOD";
                case 3 -> category = "GENERAL MERCHANDISE";
            }
        }

        // Trim and clean category
        assert category != null;
        category = category.trim().toUpperCase();

        // Validate and clean item name input
        if (itemName == null || itemName.trim().isEmpty()) {
            itemName = "UNNAMED";
        }

        // Clean item name
        itemName = itemName.trim().toUpperCase();

        // Get category code (first and last characters)
        String categoryCode = category.length() >= 2 ?
                category.charAt(0) + category.substring(category.length() - 1) :
                category + "X";

        // Get item name prefix (first 4 characters)
        String itemNamePrefix = itemName.length() >= 4 ?
                itemName.substring(0, 4) :
                (itemName + "XXXX").substring(0, 4);

        // Determine the auto-incrementing number based on category
        int incrementingNumber;
        switch (itemTypeChoice) {
            case 1 -> { // Drink
                incrementingNumber = pointOfSale.drinksCategoryIndex.getOrDefault(category, 0) + 1;
                pointOfSale.drinksCategoryIndex.put(category, incrementingNumber);
            }
            case 2 -> { // Food
                incrementingNumber = pointOfSale.foodCategoryIndex.getOrDefault(category, 0) + 1;
                pointOfSale.foodCategoryIndex.put(category, incrementingNumber);
            }
            case 3 -> { // Merchandise
                incrementingNumber = pointOfSale.merchandiseCategoryIndex.getOrDefault(category, 0) + 1;
                pointOfSale.merchandiseCategoryIndex.put(category, incrementingNumber);
            }
            default -> incrementingNumber = 1;
        }

        // Format the incrementing number to always be 3 digits
        String formattedNumber = String.format("%03d", incrementingNumber);

        // Construct the item code
        return String.format("%s-%s-%s", categoryCode, itemNamePrefix, formattedNumber);
    }

    private Map<String, Map<String, Float>> addCustomizations(int itemTypeChoice) {
        System.out.println("""
    ===========================================
              ADD CUSTOMIZATIONS
    ===========================================
    Do you want to add customizations for this item?
    
    (1) Yes - Add custom options for the item
    (2) No  - Skip customization options
    ===========================================
    Enter your choice (1-2):
    """);

        Map<String, Map<String, Float>> customizations = new HashMap<>();
        int customizationChoice = getValidInput(2);

        while (customizationChoice == 1) {
            System.out.println("""
        ===========================================
               NEW CUSTOMIZATION
        ===========================================
        Enter the name of the customization:
        """);
            String customizationName = scanner.nextLine();

            Map<String, Float> optionsAndPrices = new HashMap<>();
            int optionCount = 0;

            while (optionCount < 5) {
                System.out.println("""
            ===========================================
                  ADD CUSTOMIZATION OPTION
            ===========================================
            Enter the name of the customization option 
            (or press Enter to finish adding options):
            """);
                String optionName = scanner.nextLine();

                if (optionName.isEmpty()) break;

                System.out.printf("Enter the price for '%s':%n", optionName);
                float optionPrice = scanner.nextFloat();
                scanner.nextLine(); // Consume newline

                optionsAndPrices.put(optionName, optionPrice);
                optionCount++;

                if (optionCount == 5) {
                    System.out.println("""
                ===========================================
                Maximum number of options (5) reached.
                ===========================================
                """);
                    break;
                }
            }

            customizations.put(customizationName, optionsAndPrices);

            System.out.println("""
        ===========================================
          ADD ANOTHER CUSTOMIZATION?
        ===========================================
        Do you want to add another customization?
        
        (1) Yes - Add another customization
        (2) No  - Finish adding customizations
        ===========================================
        Enter your choice (1-2):
        """);
            customizationChoice = getValidInput(2);
        }

        if (customizations.isEmpty()) {
            System.out.println("""
        ===========================================
              NO CUSTOMIZATIONS ADDED
        ===========================================
        """);
        } else {
            System.out.println("""
        ===========================================
             CUSTOMIZATIONS COMPLETED
        ===========================================
        Customizations have been successfully added.
        ===========================================
        """);
        }

        return customizations.isEmpty() ? null : customizations;
    }

    private void modifyItem() {
        System.out.println("""
    ===========================================
                  MODIFY ITEM
    ===========================================
    Enter the Item Code of the item you wish to modify:
    """);

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
            System.out.printf("""
        ===========================================
                  ITEM NOT FOUND
        ===========================================
        No item found with the Item Code: %s
        ===========================================
        """, itemCode);
            return;
        }

        System.out.printf("""
    ===========================================
             MODIFYING ITEM CODE: %s
    ===========================================
    Select an option to modify:
    
    (1) Sizes and Prices  - Change size options and prices
    (2) Customizations    - Adjust customizations
    (3) Cancel            - Return to the previous menu
    ===========================================
    Enter your choice (1-3):
    """, itemCode);

        int modifyChoice = getValidInput(3);

        switch (modifyChoice) {
            case 1 -> {
                System.out.println("""
            ===========================================
              MODIFYING SIZES AND PRICES
            ===========================================
            """);
                modifySizesAndPrices(itemToModify);
            }
            case 2 -> {
                System.out.println("""
            ===========================================
              MODIFYING CUSTOMIZATIONS
            ===========================================
            """);
                modifyCustomizations(itemToModify);
            }
            case 3 -> {
                System.out.println("""
            ===========================================
                MODIFICATION CANCELED
            ===========================================
            """);
                return;
            }
        }

        System.out.println("""
    ===========================================
             MODIFICATION SUCCESSFUL
    ===========================================
    The changes have been saved successfully.
    ===========================================
    """);

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

            int choice = getValidInput(3);

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
        Map<String, Map<String, Float>> customizationsMap;
        if (item instanceof Drink) {
            customizationsMap = ((Drink) item).getCustomizations();
        } else if (item instanceof Food) {
            customizationsMap = ((Food) item).getCustomizations();
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

            int choice = getValidInput(4);

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

                            int optionChoice = getValidInput(3);

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
            sizeChoice = getValidInput(2);
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
    private int getValidInput(int max) {
        int input;
        while (true) {
            try {
                input = scanner.nextInt();
                scanner.nextLine(); // consume newline

                if (input >= 1 && input <= max) {
                    return input;
                } else {
                    System.out.println("Invalid input. Please enter a number between " + 1 + " and " + max + ".");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); // clear invalid input
            }
        }
    }
}