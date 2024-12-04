package Items;

import java.util.HashMap;
import java.util.Map;

public abstract class ItemCharacteristics {
    private String itemCode;                     // Unique code for the item
    private String name;                         // Name of the item
    private String itemType;                     // Type of the item (e.g., Food, Drink)
    private Map<String, Float> sizesAndPrices;   // Sizes and corresponding prices

    // Constructor
    public ItemCharacteristics(String itemCode, String name, String itemType, Map<String, Float> sizesAndPrices) {
        this.itemCode = itemCode;
        this.name = name;
        this.itemType = itemType;
        this.sizesAndPrices = sizesAndPrices != null ? sizesAndPrices : new HashMap<>();
    }

    // Getters and Setters
    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return itemType;
    }

    public void setCategory(String category) {
        this.itemType = category;
    }

    public Map<String, Float> getSizesAndPrices() {
        return sizesAndPrices;
    }

    public void setSizesAndPrices(Map<String, Float> sizesAndPrices) {
        this.sizesAndPrices = sizesAndPrices;
    }

    // Utility Methods
    public void addSizeAndPrice(String size, Float price) {
        sizesAndPrices.put(size, price);
    }

    public void removeSize(String size) {
        sizesAndPrices.remove(size);
    }

    public Map<String, Map<String, Float>> getCustomizations(){
        return null;
    }

    public void displaySizesAndPrices() {
        System.out.println("Sizes and Prices:");
        for (Map.Entry<String, Float> entry : sizesAndPrices.entrySet()) {
            System.out.println("Size: " + entry.getKey() + ", Price: " + entry.getValue());
        }
    }
}
