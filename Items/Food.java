package Items;

import java.util.HashMap;
import java.util.Map;

public class Food extends ItemCharacteristics {
    private String foodCategory; // e.g., Pastries, Cakes, Sandwiches, Pastas, Others
    Map<String, Map<String, Float>> customizations = new HashMap<>();

    public Food(String itemCode, String itemName, String itemType, Map<String, Float> sizesAndPricesMap, String foodCategory, Map<String, Map<String, Float>> customizations) {
        super(itemCode, itemName, itemType, sizesAndPricesMap);
        this.foodCategory = foodCategory;
        this.customizations = customizations;
    }

    @Override
    public String getCategory() {
        return foodCategory;
    }

    public void setFoodCategory(String foodCategory) {
        this.foodCategory = foodCategory;
    }

    public Map<String, Map<String, Float>> getCustomizations() {
        return customizations;
    }
}
