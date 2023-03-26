package obj;



import java.util.Map;

public class CurrencyConvertion {

    private final Map<String, Integer> values;
    public CurrencyConvertion(Map<String, Integer> values) {
        this.values = values;
    }


    public int makeValueFromUni(int unifiedValue, String currentConcurrency) {
        int unifiedValueInCurrency = values.get(currentConcurrency);
        return unifiedValue / unifiedValueInCurrency;
    }

    public int makeUni(int concurrencyValue, String currentConcurrency) {
        int unifiedValueInCurrency = values.get(currentConcurrency);
        return concurrencyValue * unifiedValueInCurrency;
    }

}
