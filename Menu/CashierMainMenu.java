package Menu;

import java.util.*;

public class CashierMainMenu {
    private final Scanner scanner = new Scanner(System.in);
    private List<String> orderSummary = new ArrayList<>();

    public void showCashierMenu() {
        // Create new transaction
        System.out.println("Welcome, Cashier!");
        System.out.println("Select option:");
        System.out.println("(1) Place Order");
        System.out.println("(2) Logout");

        int choice = getValidInput(1, 2);

        switch (choice) {
            case 1 -> startTransaction();
            case 2 -> System.out.println("Logging out...");
        }
    }

    private void startTransaction() {
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

        int choice = getValidInput(1, 4);

        switch (choice) {
            case 1 -> selectDrink();
            case 2 -> selectFood();
            case 3 -> selectMerchandise();
            case 4 -> {
                System.out.println("\n--- Final Order Summary ---");
                for (String order : orderSummary) {
                    System.out.println(order);
                }
                System.out.println("Transaction canceled.");
                orderSummary.clear(); // Clear the order summary after cancellation
            }
        }
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
        // Define T-shirt items and their prices
        List<String> tShirtItems = new ArrayList<>();
        tShirtItems.add("Graphic T-shirt");
        tShirtItems.add("Plain White T-shirt");
        tShirtItems.add("Vintage Logo T-shirt");

        Map<String, Double> tShirtPrices = new HashMap<>();
        tShirtPrices.put("Graphic T-shirt", 20.00);
        tShirtPrices.put("Plain White T-shirt", 15.00);
        tShirtPrices.put("Vintage Logo T-shirt", 25.00);

        // Define customization options for T-shirts (e.g., add a print, change size)
        List<String> tShirtCustomizations = new ArrayList<>();
        tShirtCustomizations.add("Add Custom Print");
        tShirtCustomizations.add("Change Size (S, M, L, XL)");

        Map<String, Double> tShirtCustomizationPrices = new HashMap<>();
        tShirtCustomizationPrices.put("Add Custom Print", 5.00);
        tShirtCustomizationPrices.put("Change Size (S, M, L, XL)", 3.00);

        // Call the generic selectItem method to handle the T-shirt item selection and customization
        selectItem("T-shirt", tShirtItems, tShirtPrices, tShirtCustomizations, tShirtCustomizationPrices);
    }

    private void selectBags() {
        // Define Bag items and their prices
        List<String> bagItems = new ArrayList<>();
        bagItems.add("Canvas Tote Bag");
        bagItems.add("Leather Backpack");
        bagItems.add("Crossbody Purse");

        Map<String, Double> bagPrices = new HashMap<>();
        bagPrices.put("Canvas Tote Bag", 15.00);
        bagPrices.put("Leather Backpack", 50.00);
        bagPrices.put("Crossbody Purse", 30.00);

        //Customizations
        List<String> bagCustomizations = new ArrayList<>();
        bagCustomizations.add("Add Monogramming");
        bagCustomizations.add("Choose Color");

        Map<String, Double> bagCustomizationPrices = new HashMap<>();
        bagCustomizationPrices.put("Add Monogramming", 8.00);
        bagCustomizationPrices.put("Choose Color", 5.00);

        // Call the generic selectItem method to handle the bag item selection and customization
        selectItem("Bag", bagItems, bagPrices, bagCustomizations, bagCustomizationPrices);
    }

    private void selectMugs() {
        // Define Mug items and their prices
        List<String> mugItems = new ArrayList<>();
        mugItems.add("Ceramic Coffee Mug");
        mugItems.add("Travel Mug");
        mugItems.add("Espresso Cup");

        Map<String, Double> mugPrices = new HashMap<>();
        mugPrices.put("Ceramic Coffee Mug", 10.00);
        mugPrices.put("Travel Mug", 12.00);
        mugPrices.put("Espresso Cup", 8.00);

        // Define customization options for Mugs (e.g., add engraving, choose color)
        List<String> mugCustomizations = new ArrayList<>();
        mugCustomizations.add("Custom Engraving");
        mugCustomizations.add("Choose Color");

        Map<String, Double> mugCustomizationPrices = new HashMap<>();
        mugCustomizationPrices.put("Custom Engraving", 5.00);
        mugCustomizationPrices.put("Choose Color", 3.00);

        // Call the generic selectItem method to handle the mug item selection and customization
        selectItem("Mug", mugItems, mugPrices, mugCustomizations, mugCustomizationPrices);
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
            return;
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
                    break;
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

        System.out.println("\nDo you want to continue the transaction? (Yes/No):");
        String continueResponse = scanner.nextLine().trim().toLowerCase();

        if (continueResponse.equals("yes")) {
            startTransaction(); // Go back to the main transaction method
        } else {
            System.out.println("\n--- Final Order Summary ---");
            for (String order : orderSummary) {
                System.out.println(order);
            }
            System.out.println("Thank you for your order!");
            orderSummary.clear();
        }
    }

}
