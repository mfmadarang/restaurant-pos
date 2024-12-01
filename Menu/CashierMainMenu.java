package Menu;

import java.io.*;
import java.util.*;

public class CashierMainMenu {
    private final Scanner scanner = new Scanner(System.in);

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
        // Select an Item Type
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
            case 4 -> System.out.println("Transaction canceled.");
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
        List<Item> expressoItems = readItemsFromFile("Database/Drink.txt");
        if (expressoItems.isEmpty()) {
            System.out.println("No items found in the database.");
            return;
        }

        System.out.println("List of Espresso items:");
        for (Item item : expressoItems) {
            System.out.println(item.getName());
        }

        // Ask for the item name exactly as it appears in the list
        System.out.println("Select the item you want (must be worded exactly of the Name):");
        String selectedItemName = scanner.nextLine().trim();

        // Find the selected item
        Item selectedItem = null;
        for (Item item : expressoItems) {
            if (item.getName().equalsIgnoreCase(selectedItemName)) {
                selectedItem = item;
                break;
            }
        }

        if (selectedItem != null) {
            displayItemDetails(selectedItem);
        } else {
            System.out.println("Item not found. Please ensure the name is spelled correctly.");
        }
    }

    private void selectBlendedItem() {

    }

    private void selectTeaItem() {

    }

    private List<Item> readItemsFromFile(String filePath) {
        List<Item> items = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            Item currentItem = null;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                // Parse item information
                if (line.contains(",")) {
                    // Item line: item code, name, type, category
                    String[] itemDetails = line.split(",");
                    String itemCode = itemDetails[0];
                    String name = itemDetails[1];
                    String type = itemDetails[2];
                    String category = itemDetails[3];

                    currentItem = new Item(itemCode, name, type, category);
                } else {
                    // Size and price line (e.g., Small:50.0, Medium:100.0)
                    String[] sizePriceDetails = line.split(",");
                    for (String sizePrice : sizePriceDetails) {
                        String[] parts = sizePrice.split(":");
                        String size = parts[0].trim();
                        double price = Double.parseDouble(parts[1].trim());
                        currentItem.addSizePrice(size, price);
                    }

                    // Add item after processing sizes
                    if (currentItem != null) {
                        items.add(currentItem);
                        currentItem = null; // Reset for next item
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return items;
    }

    private void displayItemDetails(Item item) {
        System.out.println("Item Code: " + item.getItemCode());
        System.out.println("Name: " + item.getName());
        System.out.println("Item Type: " + item.getType());
        System.out.println("Category: " + item.getCategory());
        System.out.println("Sizes and Prices:");
        for (Map.Entry<String, Double> entry : item.getSizePrices().entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
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

    // Item class to hold the item details
    private static class Item {
        private String itemCode;
        private String name;
        private String type;
        private String category;
        private Map<String, Double> sizePrices = new HashMap<>();

        public Item(String itemCode, String name, String type, String category) {
            this.itemCode = itemCode;
            this.name = name;
            this.type = type;
            this.category = category;
        }

        public void addSizePrice(String size, double price) {
            sizePrices.put(size, price);
        }

        public String getItemCode() {
            return itemCode;
        }

        public String getName() {
            return name;
        }

        public String getType() {
            return type;
        }

        public String getCategory() {
            return category;
        }

        public Map<String, Double> getSizePrices() {
            return sizePrices;
        }
    }
}
