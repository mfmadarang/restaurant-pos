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
        pos.fetchDataFromTextFile();

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

            Map<String, Map<String, Float>> customizations;
            customizations = scanCustomizationsFromFile(reader);

            switch (itemType) {
                case "Drink" -> Items.add(new Drink(itemCode, name, itemType, sizesAndPricesMap, category, customizations));
                case "Food" -> Items.add(new Food(itemCode, name, itemType, sizesAndPricesMap, category, customizations));
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
        Map<String, Map<String, Float>> customizationsMap = new HashMap<>();;
        String line = reader.readLine();

        while (line != null) {
            if (line.trim().isEmpty()) {
                break;
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

        if (item instanceof Drink) {
            if (((Drink) item).getCustomizations() != null) {
                writeCustomizationsToTextFile(writer, ((Drink) item).getCustomizations());
            }
        }
        else if (item instanceof Food) {
            if (((Food) item).getCustomizations() != null) {
                writeCustomizationsToTextFile(writer, ((Food) item).getCustomizations());
            }
        }

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
        while (true) {
            System.out.println("""
        ===========================================
                     HELL WEEK COFFEE
        ===========================================
        Select the type of user:
        
        (1) Cashier   - Access the Cashier Menu
        (2) Manager   - Access the Manager Menu
        (3) Quit      - Exit the System
        
        ===========================================
        Please enter your choice (1-3):
        """);

            int choice = getValidInput(1, 3);

            switch (choice) {
                case 1 -> {
                    System.out.println("\nOpening Cashier Menu...");
                    CashierMainMenu cashierMainMenu = new CashierMainMenu();
                    cashierMainMenu.showCashierMenu(Items);
                }
                case 2 -> {
                    System.out.println("\nOpening Manager Menu...");
                    ManagerMainMenu managerMainMenu = new ManagerMainMenu(this);
                    managerMainMenu.showManagerMenu();
                }
                case 3 -> {
                    System.out.println("\nThank you for using the system. Goodbye!");
                    return;
                }
            }
        }
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