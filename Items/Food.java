package Items;

import java.util.Map;

public class Food extends ItemCharacteristics {
    private String foodType; // e.g., Pastries, Cakes, Sandwiches, Pastas, Others

    public Food(String itemCode, String itemName, String itemCategory, Map<String, Float> sizesAndPricesMap, String foodType) {
        super(itemCode, itemName, itemCategory, sizesAndPricesMap);
        this.foodType = foodType;
    }

    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }
}
