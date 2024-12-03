package Items;

import java.util.HashMap;
import java.util.Map;

public class Drink extends ItemCharacteristics {
    private String drinkCategory; // e.g., Espresso Drinks, Blended Drinks, Tea, Others
    Map<String, Map<String, Float>> customizations = new HashMap<>();

    public Drink(String itemCode, String itemName, String itemType, Map<String, Float> sizesAndPricesMap, String drinkCategory, Map<String, Map<String, Float>> customizations) {
        super(itemCode, itemName, itemType, sizesAndPricesMap);
        this.drinkCategory = drinkCategory;
        this.customizations = customizations;
    }

    public String getDrinkCategory() {
        return drinkCategory;
    }

    public void setDrinkCategory(String drinkCategory) {
        this.drinkCategory = drinkCategory;
    }

    public Map<String, Map<String, Float>> getCustomizations() {
        return customizations;
    }

    public void displayCustomizationOptionAndPrices() { // method is used for test cases only

        for(Map.Entry<String, Map<String, Float>> entry : customizations.entrySet()) {
            System.out.println("Customization Name: " + entry.getKey());
            Map<String, Float> optionsAndPrices = entry.getValue();
            for(Map.Entry<String, Float> customizationEntry : optionsAndPrices.entrySet()) {
                System.out.println("Option: " + customizationEntry.getKey() + ", Price: " + customizationEntry.getValue());
            }
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}
