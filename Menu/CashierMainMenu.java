package Menu;

import Items.*;

import java.util.*;

public class CashierMainMenu {
    private final Scanner scanner = new Scanner(System.in);
    private List<String> orderSummary = new ArrayList<>(); // not used
    private Map<String, Order> orderSummaryMap = new HashMap<>();

    public void showCashierMenu(ArrayList<ItemCharacteristics> items) {
        while (true) {
            System.out.println("""
        ===========================================
                  CASHIER MENU
        ===========================================
        Please select an option:
        
        (1) Place Order  - Start a new transaction
        (2) Logout       - Exit the Cashier Menu
        
        ===========================================
        Please enter your choice (1-2):
        """);

            int choice = getValidInput(1, 2);

            switch (choice) {
                case 1 -> {
                    System.out.println("\nStarting new transaction...");
                    startTransaction(items);
                }
                case 2 -> {
                    System.out.println("\nLogging out... Thank you for using the system.");
                    return;
                }
            }
        }
    }

    private void startTransaction(ArrayList<ItemCharacteristics> items) {
        boolean continueTransaction = true;  // Flag to control the continuation of the transaction

        while (true) {
            // Display current order summary if available
            if (!orderSummaryMap.isEmpty()) {
                System.out.println("""
            ===========================================
                   CURRENT ORDER SUMMARY
            ===========================================
            """);
                float currentTransactionPrice = 0;
                for (Order order : orderSummaryMap.values()) {
                    System.out.println(order);
                    currentTransactionPrice += order.getTotalPrice();
                }
                System.out.printf("\nCurrent Transaction Price: $%.2f%n", currentTransactionPrice);
                System.out.println("==========================================\n");
            }

            // Select an Item Type
            System.out.println("""
        ===========================================
                   SELECT ITEM TYPE
        ===========================================
        (1) Drink        - Add a Drink to the order
        (2) Food         - Add Food to the order
        (3) Merchandise  - Add Merchandise to the order
        (4) Complete Transaction
        (5) Cancel Transaction
        
        ===========================================
        Please enter your choice (1-5):
        """);

            int choice = getValidInput(1, 5);

            switch (choice) {
                case 1 -> selectItemType(items, "Drink");
                case 2 -> selectItemType(items, "Food");
                case 3 -> selectItemType(items, "Merchandise");
                case 4 -> {
                    System.out.println("\n--- FINAL ORDER SUMMARY ---");
                    float transactionPrice = 0;
                    for (Order order : orderSummaryMap.values()) {
                        System.out.println(order);
                        transactionPrice += order.getTotalPrice();
                    }
                    System.out.printf("\nTotal Transaction Price: $%.2f%n", transactionPrice);
                    System.out.println("\nTransaction complete.\n");
                    return;
                }
                case 5 -> {
                    System.out.println("Transaction cancelled by user. Returning to Cashier Main Menu.\n");
                    orderSummaryMap.clear();
                    return;
                }
            }
        }
    }

    private void selectItemType(ArrayList<ItemCharacteristics> Items, String itemType) {
        ArrayList<ItemCharacteristics> itemList = new ArrayList<>();
        Set<String> categorySet = new HashSet<>();

        // Categorizing items based on type
        for (ItemCharacteristics item : Items) {
            if (itemType.equals("Drink") && item instanceof Drink) {
                itemList.add(item);
                categorySet.add(item.getCategory());
            } else if (itemType.equals("Food") && item instanceof Food) {
                itemList.add(item);
                categorySet.add(item.getCategory());
            } else if (itemType.equals("Merchandise") && item instanceof Merchandise) {
                itemList.add(item);
                categorySet.add(item.getCategory());
            }
        }

        // Handling empty item list for the selected item type
        if (itemList.isEmpty()) {
            System.out.println("""
        ===========================================
        No items available for the selected type.
        Returning to Select Item Type.
        ===========================================
        """);
            return;
        }

        // Display categories and allow the user to select one
        List<String> uniqueCategoryList = new ArrayList<>(categorySet);
        System.out.println("""
    ===========================================
             SELECT CATEGORY FOR """ + itemType.toUpperCase() + """
    ===========================================
    """);

        int index = 1;
        for (String category : uniqueCategoryList) {
            System.out.println("(" + index + ") " + category);
            index++;
        }

        int choice = getValidInput(1, uniqueCategoryList.size());
        String chosenCategory = uniqueCategoryList.get(choice - 1);

        // Filter items by the selected category
        ArrayList<ItemCharacteristics> categoryItems = new ArrayList<>();
        for (ItemCharacteristics item : itemList) {
            if (item.getCategory().equals(chosenCategory)) {
                categoryItems.add(item);
            }
        }

        // Show items of the selected category
        showCategoryItems(categoryItems, itemType, chosenCategory);
    }

    private void showCategoryItems(ArrayList<ItemCharacteristics> categoryItems, String itemType, String category) {
        if (categoryItems.isEmpty()) {
            System.out.println(
                    "===========================================\n" +
                            "No available items in the " + category + " category.\n" +
                            "Returning to Select Item Type.\n" +
                            "=========================================="
            );
            return;
        }

        System.out.println(
                "===========================================\n" +
                        "        SELECT A " + itemType.toUpperCase() + " \n" +
                        "        FROM " + category.toUpperCase() + " CATEGORY\n" +
                        "=========================================="
        );

        int index = 1;
        for (ItemCharacteristics item : categoryItems) {
            System.out.println("(" + index + ") " + item.getName());
            index++;
        }

        int choice = getValidInput(1, index);
        ItemCharacteristics chosenItem = categoryItems.get(choice - 1);

        String chosenSize = selectSizes(chosenItem.getSizesAndPrices());
        float basePrice = chosenItem.getSizesAndPrices().get(chosenSize);

        Map<String, List<String>> selectedCustomizations = new HashMap<>();
        if (chosenItem instanceof Drink) {
            selectedCustomizations = selectCustomizations(((Drink) chosenItem).getCustomizations());
        } else if (chosenItem instanceof Food) {
            selectedCustomizations = selectCustomizations(((Food) chosenItem).getCustomizations());
        }

        float customizationPrice = 0;
        for (List<String> options : selectedCustomizations.values()) {
            for (String option : options) {
                customizationPrice += Float.parseFloat(option.split(":")[1]);
            }
        }

        int quantity = getValidQuantityInput();

        Order newOrder = new Order(chosenItem.getName(), chosenSize, selectedCustomizations, quantity, (basePrice + customizationPrice) * quantity);
        String orderKey = newOrder.generateOrderKey();

        if (orderSummaryMap.containsKey(orderKey)) {
            Order existingOrder = orderSummaryMap.get(orderKey);
            existingOrder.increaseQuantity(quantity);
            existingOrder.increasePrice(newOrder.getTotalPrice());
            System.out.println("\nAdded to existing order.");
        } else {
            orderSummaryMap.put(orderKey, newOrder);
            System.out.println("\nAdded a new order.");
        }
    }

    private String selectSizes(Map<String, Float> sizesAndPrices) {
        System.out.println("""
    ===========================================
                AVAILABLE SIZES
    ===========================================
    """);

        List<String> sizeOptions = new ArrayList<>(sizesAndPrices.keySet());

        // Display size options in a formatted list
        for (int i = 0; i < sizeOptions.size(); i++) {
            System.out.printf("(%d) %-10s - $%.2f%n", (i + 1), sizeOptions.get(i), sizesAndPrices.get(sizeOptions.get(i)));
        }

        // Display the valid range for size input
        System.out.println("==========================================");
        System.out.println("Please enter your choice (1-" + sizeOptions.size() + "):");
        System.out.println("==========================================");

        int sizeChoice = getValidInput(1, sizeOptions.size());
        String chosenSize = sizeOptions.get(sizeChoice - 1);

        return chosenSize;
    }

    private Map<String, List<String>> selectCustomizations(Map<String, Map<String, Float>> customizations) {
        Map<String, List<String>> selectedCustomizations = new HashMap<>();

        if (customizations.isEmpty()) {
            System.out.println("""
        ===========================================
              NO CUSTOMIZATIONS AVAILABLE
        ===========================================
        """);
            return selectedCustomizations;
        }

        while (true) {
            System.out.println("""
        ===========================================
              SELECT A CUSTOMIZATION
        ===========================================
        """);

            List<String> customizationOptions = new ArrayList<>(customizations.keySet());

            // Display customization options
            for (int i = 0; i < customizationOptions.size(); i++) {
                System.out.printf("(%d) %-20s%n", (i + 1), customizationOptions.get(i));
            }
            System.out.println("(" + (customizationOptions.size() + 1) + ") Done");
            System.out.println("==========================================");

            int choice = getValidInput(1, customizationOptions.size() + 1);
            if (choice == customizationOptions.size() + 1) {
                break;
            }

            String customization = customizationOptions.get(choice - 1);
            Map<String, Float> options = customizations.get(customization);

            System.out.println("""
        ===========================================
              SELECT AN OPTION FOR """ + customization + """
        ===========================================
        """);

            // Display options for the selected customization
            List<String> optionList = new ArrayList<>(options.keySet());
            for (int i = 0; i < optionList.size(); i++) {
                System.out.printf("(%d) %-20s : $%.2f%n", (i + 1), optionList.get(i), options.get(optionList.get(i)));
            }

            System.out.println("==========================================");
            int optionChoice = getValidInput(1, optionList.size());
            String selectedOption = optionList.get(optionChoice - 1) + ":" + options.get(optionList.get(optionChoice - 1));

            System.out.println("Enter quantity for " + selectedOption.split(":")[0] + ":");
            int quantity = getValidQuantityInput();

            selectedCustomizations.putIfAbsent(customization, new ArrayList<>());
            for (int i = 0; i < quantity; i++) {
                selectedCustomizations.get(customization).add(selectedOption);
            }

            System.out.println(quantity + " instance(s) of " + selectedOption.split(":")[0] + " added.");
            System.out.println("==========================================\n");
        }

        return selectedCustomizations;
    }

    private int getValidInput(int min, int max) {
        int choice;
        while (true) {
            try {
                System.out.println("Select choice: ");
                choice = Integer.parseInt(scanner.nextLine());
                if (choice >= min && choice <= max) {
                    return choice;
                }
                System.out.println("Invalid input. Please enter a number between " + min + " and " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.\n");
            }
        }
    }

    private int getValidQuantityInput() {
        int choice;
        while (true) {
            try {
                System.out.print("Select quantity: ");
                choice = Integer.parseInt(scanner.nextLine());
                return choice;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.\n");
            }
        }
    }
}
