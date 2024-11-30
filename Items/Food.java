package Items;

import java.util.Map;

public class Food extends ItemCharacteristics {
    private String foodCategory; // e.g., Pastries, Cakes, Sandwiches, Pastas, Others

    public Food(String itemCode, String itemName, String itemType, Map<String, Float> sizesAndPricesMap, String foodCategory) {
        super(itemCode, itemName, itemType, sizesAndPricesMap);
        this.foodCategory = foodCategory;
    }

    public String getFoodCategory() {
        return foodCategory;
    }

    public void setFoodCategory(String foodCategory) {
        this.foodCategory = foodCategory;
    }
}
