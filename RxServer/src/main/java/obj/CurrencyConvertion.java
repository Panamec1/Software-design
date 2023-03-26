package obj;



import java.util.Map;

public class CurrencyConvertion {

    private final Map<String, Integer> values;
    public CurrencyConvertion(Map<String, Integer> values) {
        this.values = values;
    }


    // Перевод цену в к конкретному курсу
    public int makeValueFromUni(int unifiedValue, String currentConcurrency) {
        int unifiedValueInCurrency = values.get(currentConcurrency);
        return unifiedValue / unifiedValueInCurrency;
    }

    // Переводит цену в к универсальному значению
    public int makeUni(int concurrencyValue, String currentConcurrency) {
        int unifiedValueInCurrency = values.get(currentConcurrency);
        return concurrencyValue * unifiedValueInCurrency;
    }

}
