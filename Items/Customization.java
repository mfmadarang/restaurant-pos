package Items;

import java.util.HashMap;
import java.util.Map;

public class Customization {
    private String customizationName;
    private Map<String, Float> optionsAndPrices;

    public Customization(String customizationName, Map<String, Float> optionsAndPrices) {
        this.customizationName = customizationName;
        this.optionsAndPrices = optionsAndPrices;
    }

    public String getCustomizationName() {
        return customizationName;
    }

    public Map<String, Float> getOptionsAndPrices() {
        return optionsAndPrices;
    }

}
