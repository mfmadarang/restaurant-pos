package Menu;

import Items.Drink;
import Items.ItemCharacteristics;

import java.util.*;

public class CashierMainMenu {
    private final Scanner scanner = new Scanner(System.in);
    private List<String> orderSummary = new ArrayList<>();

    public void showCashierMenu(ArrayList<ItemCharacteristics> Items) {
        // Create new transaction
        System.out.println("Welcome, Cashier!");
        System.out.println("Select option:");
        System.out.println("(1) Place Order");
        System.out.println("(2) Logout");

        int choice = getValidInput(1, 2);

        switch (choice) {
            case 1 -> startTransaction(Items);
            case 2 -> System.out.println("Logging out...");
        }
    }

    private void startTransaction(ArrayList<ItemCharacteristics> Items) {
        boolean continueTransaction = true;  // Flag to control the continuation of the transaction

        // Loop to continue the transaction until the user decides to stop
        while (continueTransaction) {
            // Display current order summary
            if (!orderSummary.isEmpty()) {
                System.out.println("\n--- Current Order Summary ---");
                for (String order : orderSummary) {
                    System.out.println(order);
                }
            }

            // Select an Item Type
            System.out.println("\nSelect Item Type:");
            System.out.println("(1) Drinks");
            System.out.println("(2) Food");
            System.out.println("(3) Merchandise");
            System.out.println("(4) Cancel Transaction");
            System.out.println("(5) Finish Order"); // least priority

            int choice = getValidInput(1, 4);

            switch (choice) {
                case 1 -> selectDrink();
                case 2 -> selectFood();
                case 3 -> selectMerchandise();
                case 4 -> {
                    System.out.println("Transaction cancelled by user. Return to Cashier Main Menu");
                    orderSummary.clear();
                    showCashierMenu(Items);
                }
            }

            // Ask the user if they want to continue adding more items to the order
            if (continueTransaction) {
                System.out.println("\nDo you want to add another item? (yes/no)");
                String continueResponse = scanner.nextLine().trim().toLowerCase();
                if (continueResponse.equals("no")) {
                    continueTransaction = false;  // Exit the loop if the user says "no"
                    System.out.println("\n--- Final Order Summary ---");
                    for (String order : orderSummary) {
                        System.out.println(order);
                    }
                    System.out.println("Transaction complete.");
                }
            }
        }
    }

    /**
     * Methods suggestions for efficient implementation of Cashier Main Menu
     *
     * @param Items
     */

    private void selectDrinkAlternative(ArrayList<ItemCharacteristics> Items) {
        ArrayList<ItemCharacteristics> DrinkList = new ArrayList<>();
        Set<String> categorySet = new HashSet<>();

        for (ItemCharacteristics item : Items) {
            if (item instanceof Drink) {
                DrinkList.add(item);
            }

            String category = item.getCategory();
            categorySet.add(category);

        }

        List<String> uniqueCategoryList = new ArrayList<>(categorySet);
        System.out.println("Select Drink Category:");
        int index = 1;
        for (String category : uniqueCategoryList) {
            System.out.println("(" + index + ") " + category);
            index++;
        }

        int choice = getValidInput(1, index);
        String chosenCategory = uniqueCategoryList.get(choice - 1);
        ArrayList<ItemCharacteristics> categoryItems = new ArrayList<>();

        for (ItemCharacteristics item : DrinkList) {
            if(item.getCategory().equals(chosenCategory)) {
                categoryItems.add(item);
            }
        }

        showCategoryItems(categoryItems, "Drink", chosenCategory);

    }

    private void showCategoryItems(ArrayList<ItemCharacteristics> categoryItems, String itemType, String category) {
        System.out.println("Select a" + itemType + " from " + category +" category:");
        int index = 1;
        for (ItemCharacteristics item : categoryItems) {
            System.out.println("(" + index + ") " + item.getName());
            index++;
        }
        int choice = getValidInput(1, index);
        ItemCharacteristics chosenItem = categoryItems.get(choice - 1);

       // Unsure with the implementation of getting sizes so left it blank

       // Handle Customizations
        List<String> customizations = new ArrayList<>();
        if (!chosenItem.getCustomizations().isEmpty()) {
            System.out.println("Select customizations (type 'done' to finish):");
            while (true) {
                index = 1;
                for (String customization : chosenItem.getCustomizations()) {
                    System.out.println("(" + index + ") " + customization);
                    index++;
                }
                System.out.println("(0) Done");

                int customizationChoice = getValidInput(0, chosenItem.getCustomizations().size());
                if (customizationChoice == 0) {
                    break; // User is done adding customizations
                }
                String selectedCustomization = chosenItem.getCustomizations().get(customizationChoice - 1).toString();
                customizations.add(selectedCustomization);
            }
        }

        /*
        If item has only one size, don't proceed to select options and prices. Otherwise, proceed
        If item has no customization proceed to input how many quantity of that order.
        If item has customizations, list down the menu of customizations,
        Nest the customizations under customizations, give condition "done" to end
        Add the item to order summary array list
        Ask the user if it wants to order
        If yes return to CashierMain Menu
           if no print the final summary order

         Note to self: similar to the selectItem method but has nested customizations and sizes.

         */
        /*

         */


        // Next step is select size and the next one is select customizations if any

        // implement logic for the chosenItem

    }

    private void selectDrink() {
        // Select a Category for Drinks
        System.out.println("Select Drink Category:");
        System.out.println("(1) Espresso Drinks");
        System.out.println("(2) Blended Drinks");
        System.out.println("(3) Tea");
        System.out.println("(4) Others");

        int choice = getValidInput(1, 4);
        switch (choice) {
            case 1 -> selectExpressoItem();
            case 2 -> selectBlendedItem();
            case 3 -> selectTeaItem();
            case 4 -> System.out.println("Implement logic here for Others");
        }
    }

    private void selectFood() {
        // Select a Category for Food
        System.out.println("Select Food Category:");
        System.out.println("(1) Pastries");
        System.out.println("(2) Cakes");
        System.out.println("(3) Sandwiches");
        System.out.println("(4) Pastas");
        System.out.println("(5) Others");

        int choice = getValidInput(1, 5);
        switch (choice) {
            case 1 -> selectPastries();
            case 2 -> selectCakes();
            case 3 -> selectSandwiches();
            case 4 -> selectPastas();
            case 5 -> System.out.println("Implement logic here for Others");
        }
    }

    private void selectMerchandise() {
        // Select a Category for Merchandise
        System.out.println("Select Merchandise Category:");
        System.out.println("(1) T-Shirts");
        System.out.println("(2) Bags");
        System.out.println("(3) Mugs");
        System.out.println("(4) Others");

        int choice = getValidInput(1, 4);
        switch (choice) {
            case 1 -> selectTshirts();
            case 2 -> selectBags();
            case 3 -> selectMugs();
            case 4 -> System.out.println("Implement logic here for Others");
        }
    }

    private void selectOthers() {

    }

    private void selectExpressoItem() {
        List<String> espressoItems = new ArrayList<>();
        espressoItems.add("Latte");
        espressoItems.add("Cappuccino");
        espressoItems.add("Americano");

        Map<String, Double> espressoPrices = new HashMap<>();
        espressoPrices.put("Latte", 3.00);
        espressoPrices.put("Cappuccino", 4.00);
        espressoPrices.put("Americano", 3.50);

        List<String> espressoCustomizations = new ArrayList<>();
        espressoCustomizations.add("Extra Sugar");
        espressoCustomizations.add("Extra Milk");

        Map<String, Double> espressoCustomizationPrices = new HashMap<>();
        espressoCustomizationPrices.put("Extra Sugar", 0.50);
        espressoCustomizationPrices.put("Extra Milk", 0.75);

        // Call the generic method
        selectItem("Espresso", espressoItems, espressoPrices, espressoCustomizations, espressoCustomizationPrices);
    }

    private void selectBlendedItem() {
        List<String> blendedItems = new ArrayList<>();
        blendedItems.add("Frappuccino");
        blendedItems.add("Smoothie");
        blendedItems.add("Iced Blended Coffee");

        Map<String, Double> blendedPrices = new HashMap<>();
        blendedPrices.put("Frappuccino", 5.00);
        blendedPrices.put("Smoothie", 4.50);
        blendedPrices.put("Iced Blended Coffee", 4.75);

        List<String> blendedCustomizations = new ArrayList<>();
        blendedCustomizations.add("Whipped Cream");
        blendedCustomizations.add("Extra Flavor");
        blendedCustomizations.add("Sprinkles");

        Map<String, Double> blendedCustomizationPrices = new HashMap<>();
        blendedCustomizationPrices.put("Whipped Cream", 0.50);
        blendedCustomizationPrices.put("Extra Flavor", 0.75);
        blendedCustomizationPrices.put("Sprinkles", 0.25);

        // Call the generic method
        selectItem("Blended", blendedItems, blendedPrices, blendedCustomizations, blendedCustomizationPrices);
    }

    private void selectTeaItem() {
        // Define tea items and their prices
        List<String> teaItems = new ArrayList<>();
        teaItems.add("Green Tea");
        teaItems.add("Chai Tea");
        teaItems.add("Matcha Latte");

        Map<String, Double> teaPrices = new HashMap<>();
        teaPrices.put("Green Tea", 3.00);
        teaPrices.put("Chai Tea", 3.50);
        teaPrices.put("Matcha Latte", 4.00);

        //Customization for tea
        List<String> teaCustomizations = new ArrayList<>();
        teaCustomizations.add("Extra Milk");
        teaCustomizations.add("Honey");
        teaCustomizations.add("Lemon");

        Map<String, Double> teaCustomizationPrices = new HashMap<>();
        teaCustomizationPrices.put("Extra Milk", 0.50);
        teaCustomizationPrices.put("Honey", 0.75);
        teaCustomizationPrices.put("Lemon", 0.25);

        // Call the generic selectItem method to handle the tea item selection and customization
        selectItem("Tea", teaItems, teaPrices, teaCustomizations, teaCustomizationPrices);
    }

    private void selectPastries() {
        // Define pastry items and their prices
        List<String> pastryItems = new ArrayList<>();
        pastryItems.add("Croissant");
        pastryItems.add("Danish Pastry");
        pastryItems.add("Scone");

        Map<String, Double> pastryPrices = new HashMap<>();
        pastryPrices.put("Croissant", 2.50);
        pastryPrices.put("Danish Pastry", 3.00);
        pastryPrices.put("Scone", 2.75);

        //Customization for pastries
        List<String> pastryCustomizations = new ArrayList<>();
        pastryCustomizations.add("Add Jam");
        pastryCustomizations.add("Add Butter");
        pastryCustomizations.add("Extra Sugar");

        Map<String, Double> pastryCustomizationPrices = new HashMap<>();
        pastryCustomizationPrices.put("Add Jam", 0.50);
        pastryCustomizationPrices.put("Add Butter", 0.75);
        pastryCustomizationPrices.put("Extra Sugar", 0.25);

        // Call the generic selectItem method to handle the pastry item selection and customization
        selectItem("Pastry", pastryItems, pastryPrices, pastryCustomizations, pastryCustomizationPrices);
    }

    private void selectCakes() {
        // Define cake items and their prices
        List<String> cakeItems = new ArrayList<>();
        cakeItems.add("Chocolate Cake");
        cakeItems.add("Cheesecake");
        cakeItems.add("Carrot Cake");

        Map<String, Double> cakePrices = new HashMap<>();
        cakePrices.put("Chocolate Cake", 4.50);
        cakePrices.put("Cheesecake", 5.00);
        cakePrices.put("Carrot Cake", 4.75);

        //Customization for Cake
        List<String> cakeCustomizations = new ArrayList<>();
        cakeCustomizations.add("Add Frosting");
        cakeCustomizations.add("Add Fruit");
        cakeCustomizations.add("Extra Cream");

        Map<String, Double> cakeCustomizationPrices = new HashMap<>();
        cakeCustomizationPrices.put("Add Frosting", 1.00);
        cakeCustomizationPrices.put("Add Fruit", 1.50);
        cakeCustomizationPrices.put("Extra Cream", 0.75);

        // Call the generic selectItem method to handle the cake item selection and customization
        selectItem("Cake", cakeItems, cakePrices, cakeCustomizations, cakeCustomizationPrices);
    }

    private void selectSandwiches() {
        // Define sandwich items and their prices
        List<String> sandwichItems = new ArrayList<>();
        sandwichItems.add("Grilled Cheese Sandwich");
        sandwichItems.add("Turkey and Swiss Sandwich");
        sandwichItems.add("BLT (Bacon, Lettuce, Tomato)");

        Map<String, Double> sandwichPrices = new HashMap<>();
        sandwichPrices.put("Grilled Cheese Sandwich", 5.50);
        sandwichPrices.put("Turkey and Swiss Sandwich", 6.00);
        sandwichPrices.put("BLT (Bacon, Lettuce, Tomato)", 6.25);

        // Define customization options for sandwiches (e.g., add extra cheese, add avocado)
        List<String> sandwichCustomizations = new ArrayList<>();
        sandwichCustomizations.add("Add Extra Cheese");
        sandwichCustomizations.add("Add Avocado");
        sandwichCustomizations.add("Add Bacon");

        Map<String, Double> sandwichCustomizationPrices = new HashMap<>();
        sandwichCustomizationPrices.put("Add Extra Cheese", 1.00);
        sandwichCustomizationPrices.put("Add Avocado", 1.50);
        sandwichCustomizationPrices.put("Add Bacon", 1.75);

        // Call the generic selectItem method to handle the sandwich item selection and customization
        selectItem("Sandwich", sandwichItems, sandwichPrices, sandwichCustomizations, sandwichCustomizationPrices);
    }

    private void selectPastas() {
        // Define pasta items and their prices
        List<String> pastaItems = new ArrayList<>();
        pastaItems.add("Spaghetti Carbonara");
        pastaItems.add("Fettuccine Alfredo");
        pastaItems.add("Penne Arrabbiata");

        Map<String, Double> pastaPrices = new HashMap<>();
        pastaPrices.put("Spaghetti Carbonara", 8.50);
        pastaPrices.put("Fettuccine Alfredo", 9.00);
        pastaPrices.put("Penne Arrabbiata", 7.75);

        // Define customization options for pastas (e.g., add extra sauce, add extra cheese)
        List<String> pastaCustomizations = new ArrayList<>();
        pastaCustomizations.add("Add Extra Sauce");
        pastaCustomizations.add("Add Extra Cheese");
        pastaCustomizations.add("Add Mushrooms");

        Map<String, Double> pastaCustomizationPrices = new HashMap<>();
        pastaCustomizationPrices.put("Add Extra Sauce", 1.00);
        pastaCustomizationPrices.put("Add Extra Cheese", 1.25);
        pastaCustomizationPrices.put("Add Mushrooms", 1.50);

        // Call the generic selectItem method to handle the pasta item selection and customization
        selectItem("Pasta", pastaItems, pastaPrices, pastaCustomizations, pastaCustomizationPrices);
    }

    private void selectTshirts() {
        List<String> tshirtItems = new ArrayList<>();
        tshirtItems.add("Basic T-Shirt");
        tshirtItems.add("Graphic T-Shirt");
        tshirtItems.add("Premium T-Shirt");

        Map<String, Double> tshirtPrices = new HashMap<>();
        tshirtPrices.put("Basic T-Shirt", 15.00);
        tshirtPrices.put("Graphic T-Shirt", 20.00);
        tshirtPrices.put("Premium T-Shirt", 25.00);

        selectItem("T-Shirt", tshirtItems, tshirtPrices);
    }

    private void selectBags() {
        List<String> bagItems = new ArrayList<>();
        bagItems.add("Backpack");
        bagItems.add("Tote Bag");
        bagItems.add("Messenger Bag");

        Map<String, Double> bagPrices = new HashMap<>();
        bagPrices.put("Backpack", 30.00);
        bagPrices.put("Tote Bag", 25.00);
        bagPrices.put("Messenger Bag", 35.00);

        selectItem("Bag", bagItems, bagPrices);
    }

    private void selectMugs() {
        List<String> mugItems = new ArrayList<>();
        mugItems.add("Ceramic Mug");
        mugItems.add("Travel Mug");
        mugItems.add("Coffee Cup");

        Map<String, Double> mugPrices = new HashMap<>();
        mugPrices.put("Ceramic Mug", 10.00);
        mugPrices.put("Travel Mug", 15.00);
        mugPrices.put("Coffee Cup", 12.00);

        selectItem("Mug", mugItems, mugPrices);
    }

    private int getValidInput(int min, int max) {
        int choice;
        while (true) {
            try {
                choice = Integer.parseInt(scanner.nextLine());
                if (choice >= min && choice <= max) {
                    return choice;
                }
                System.out.println("Invalid input. Please enter a number between " + min + " and " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    //With Customizations
    private void selectItem(String itemType, List<String> items, Map<String, Double> itemPrices,
                            List<String> customizations, Map<String, Double> customizationPrices) {
        System.out.println("Select a " + itemType + ":");
        for (int i = 0; i < items.size(); i++) {
            System.out.printf("(%d) %s $%.2f%n", (i + 1), items.get(i), itemPrices.get(items.get(i)));
        }
        System.out.println("(" + (items.size() + 1) + ") Cancel");

        int choice = getValidInput(1, items.size() + 1);

        if (choice == items.size() + 1) {
            System.out.println("Canceled " + itemType + " selection.");
            return; // Exit the selection method if canceled
        }

        String selectedItem = items.get(choice - 1);
        System.out.println("You selected: " + selectedItem);

        // Customizations
        List<String> selectedCustomizations = new ArrayList<>();
        double customizationPrice = 0.0;

        System.out.println("Do you want to customize your " + selectedItem + "? (Yes/No)");
        String customizationResponse = scanner.nextLine().trim().toLowerCase();

        if (customizationResponse.equals("yes")) {
            System.out.println("Select customizations (enter 'done' to finish):");
            for (int i = 0; i < customizations.size(); i++) {
                System.out.printf("(%d) %s ₱%.2f%n", (i + 1), customizations.get(i), customizationPrices.get(customizations.get(i)));
            }
            System.out.println("(" + (customizations.size() + 1) + ") Done");

            while (true) {
                int customizationChoice = getValidInput(1, customizations.size() + 1);

                if (customizationChoice == customizations.size() + 1) {
                    break; // Stop customization if done
                }

                String selectedCustomization = customizations.get(customizationChoice - 1);
                selectedCustomizations.add(selectedCustomization);
                customizationPrice += customizationPrices.get(selectedCustomization);

                System.out.println(selectedCustomization + " added. Total customization cost: ₱" + customizationPrice);
            }
        }

        // Quantity
        System.out.println("Enter the quantity for " + selectedItem + ":");
        int quantity = getValidInput(1, 10); // Limit quantity to 10 for simplicity

        // Total price calculation
        double itemPrice = itemPrices.get(selectedItem) + customizationPrice;
        double totalItemPrice = itemPrice * quantity;

        // Add the selected item to the order summary
        String customizationSummary = selectedCustomizations.isEmpty() ? "None" : String.join(", ", selectedCustomizations);
        String orderDetails = selectedItem + " x" + quantity + " - Customizations: " + customizationSummary + " - ₱" + totalItemPrice;
        orderSummary.add(orderDetails);

        // Current order summary
        System.out.println("\n--- Current Order Summary ---");
        for (String order : orderSummary) {
            System.out.println(order);
        }

        return;
    }

    //Without Customizations - Used method overloading
    private void selectItem(String itemType, List<String> items, Map<String, Double> itemPrices) {
        System.out.println("Select a " + itemType + ":");
        for (int i = 0; i < items.size(); i++) {
            System.out.printf("(%d) %s $%.2f%n", (i + 1), items.get(i), itemPrices.get(items.get(i)));
        }
        System.out.println("(" + (items.size() + 1) + ") Cancel");

        int choice = getValidInput(1, items.size() + 1);

        if (choice == items.size() + 1) {
            System.out.println("Canceled " + itemType + " selection.");
            return; // Exit the selection method if canceled
        }

        String selectedItem = items.get(choice - 1);
        System.out.println("You selected: " + selectedItem);

        // No customization options, proceed to quantity
        System.out.println("Enter the quantity for " + selectedItem + ":");
        int quantity = getValidInput(1, 10); // Limit quantity to 10 for simplicity

        // Total price calculation
        double itemPrice = itemPrices.get(selectedItem);
        double totalItemPrice = itemPrice * quantity;

        // Add the selected item to the order summary
        String orderDetails = selectedItem + " x" + quantity + " - ₱" + totalItemPrice;
        orderSummary.add(orderDetails);

        // Current order summary
        System.out.println("\n--- Current Order Summary ---");
        for (String order : orderSummary) {
            System.out.println(order);
        }

        return;
    }


}
