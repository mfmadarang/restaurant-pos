package Items;

import java.util.List;
import java.util.Map;

public class Order {
    private String itemName;
    private String sizeAndPrice;
    private Map<String, List<String>> customizations; // Customization name to list of selected options with prices
    private int quantity;
    private float totalPrice;

    public Order(String itemName, String sizeAndPrice, Map<String, List<String>> customizations, int quantity, float totalPrice) {
        this.itemName = itemName;
        this.sizeAndPrice = sizeAndPrice;
        this.customizations = customizations;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public String getItemName() {
        return itemName;
    }

    public String getSizeAndPrice() {
        return sizeAndPrice;
    }

    public Map<String, List<String>> getCustomizations() {
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
        keyBuilder.append(itemName).append("_").append(sizeAndPrice);  // Base key with item name and size

        // If there are customizations, add them to the key
        if (customizations != null && !customizations.isEmpty()) {
            customizations.forEach((customizationName, options) -> {
                keyBuilder.append("_").append(customizationName);
                options.forEach(option -> keyBuilder.append(":").append(option));
            });
        }

        return keyBuilder.toString();
    }

    @Override
    public String toString() {
        StringBuilder customizationsStr = new StringBuilder();
        if (customizations != null && !customizations.isEmpty()) {
            customizations.forEach((key, value) -> {
                customizationsStr.append("\n    - ").append(key).append(": ");
                for (String option : value) {
                    String[] parts = option.split(":");
                    customizationsStr.append(parts[0]).append(" (₱").append(parts[1]).append("), ");
                }
                // Remove trailing comma and space
                if (customizationsStr.length() > 2) {
                    customizationsStr.setLength(customizationsStr.length() - 2);
                }
            });
        } else {
            customizationsStr.append("None");
        }

        return "Item: " + itemName +
                "\nSize: " + sizeAndPrice +
                "\nCustomizations: " + customizationsStr +
                "\nQuantity: " + quantity +
                "\nTotal Price: ₱" + String.format("%.2f", totalPrice) + "\n";
    }
}
