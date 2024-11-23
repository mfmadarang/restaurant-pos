package Items;

import java.util.Map;

public class Drink extends ItemCharacteristics {
    private String drinkType; // e.g., Espresso Drinks, Blended Drinks, Tea, Others

    public Drink(String itemCode, String itemName, String itemCategory, Map<String, Float> sizesAndPricesMap, String drinkType) {
        super(itemCode, itemName, itemCategory, sizesAndPricesMap);
        this.drinkType = drinkType;
    }

    public String getDrinkType() {
        return drinkType;
    }

    public void setDrinkType(String drinkType) {
        this.drinkType = drinkType;
    }
}
