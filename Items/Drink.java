package Items;

import java.util.Map;

public class Drink extends ItemCharacteristics {
    private String drinkCategory; // e.g., Espresso Drinks, Blended Drinks, Tea, Others

    public Drink(String itemCode, String itemName, String itemType, Map<String, Float> sizesAndPricesMap, String drinkCategory) {
        super(itemCode, itemName, itemType, sizesAndPricesMap);
        this.drinkCategory = drinkCategory;
    }

    public String getDrinkCategory() {
        return drinkCategory;
    }

    public void setDrinkCategory(String drinkCategory) {
        this.drinkCategory = drinkCategory;
    }
}
