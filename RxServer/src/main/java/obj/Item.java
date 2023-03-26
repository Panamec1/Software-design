package obj;

import org.bson.Document;

public class Item {
    public final String itemName;
    public final int itemValue;
    public Item(String name, int value) {
        this.itemName = name;
        this.itemValue = value;
    }

    public Document toDocument() {
        return new Document()
                .append("name", itemName)
                .append("value", itemValue);
    }

    @Override
    public String toString() {
        return "item: "+itemName+", price: "+itemValue;
    }
    public Item(Document document) {
        this(document.getString("name"), document.getInteger("value"));
    }
}
