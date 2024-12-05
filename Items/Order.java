package Items;
import java.util.Map;

public class Order {
    private String itemName;
    private String size;
    private Map<String, String> customizations; // Customization name to selected option and price
    private int quantity;
    private float totalPrice;

    public Order(String itemName, String size, Map<String, String> customizations, int quantity, float totalPrice) {
        this.itemName = itemName;
        this.size = size;
        this.customizations = customizations;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public String getItemName() {
        return itemName;
    }

    public String getSize() {
        return size;
    }

    public Map<String, String> getCustomizations() {
        return customizations;
    }

    public int getQuantity() {
        return quantity;
    }

    public void increaseQuantity(int quantity) {
        this.quantity += quantity;

    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void increasePrice(float price) {
        this.totalPrice += price;
    }

    /**
     * Generate a unique order key based on item name, size, and customizations.
     * This key will be used to check if the same item (with the same size and customizations) is ordered again.
     *
     * @return A unique order key as a string.
     */
    public String generateOrderKey() {
        StringBuilder keyBuilder = new StringBuilder();
        keyBuilder.append(itemName).append("_").append(size);  // Base key with item name and size

        // If there are customizations, add them to the key
        if (customizations != null && !customizations.isEmpty()) {
            customizations.forEach((customizationName, customizationOption) -> {
                keyBuilder.append("_").append(customizationName).append(":").append(customizationOption);
            });
        }

        return keyBuilder.toString();
    }

    @Override
    public String toString() {
        StringBuilder customizationsStr = new StringBuilder();
        if (customizations != null && !customizations.isEmpty()) {
            customizations.forEach((key, value) -> customizationsStr.append("\n    - ").append(key).append(": ").append(value));
        } else {
            customizationsStr.append("None");
        }

        return "Item: " + itemName +
                "\nSize: " + size +
                "\nCustomizations: " + customizationsStr +
                "\nQuantity: " + quantity +
                "\nTotal Price: ₱" + String.format("%.2f", totalPrice) + "\n";
    }
}