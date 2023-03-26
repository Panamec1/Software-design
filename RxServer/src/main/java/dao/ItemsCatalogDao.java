package dao;

import com.mongodb.client.model.Filters;
import com.mongodb.rx.client.MongoClient;
import com.mongodb.rx.client.MongoCollection;
import com.mongodb.rx.client.Success;
import obj.Item;
import org.bson.Document;
import rx.Observable;
import obj.CurrencyConvertion;

public class ItemsCatalogDao {
    MongoCollection<Document> itemsCollection;
    private final CurrencyConvertion converter;

    public ItemsCatalogDao(MongoClient client, CurrencyConvertion converter) {
        this.converter = converter;
        this.itemsCollection = client.getDatabase("catalog").getCollection("items");

    }

    // Добавление нового элемента в каталог
    public Observable<Success> addItem(String name, int value, String currencyString) {
        return itemsCollection.find(Filters.eq("name", name))
                .toObservable()
                .isEmpty()
                .flatMap(isEmpty -> {
                    if (!isEmpty) { return Observable.error(new IllegalArgumentException("Item with name '" +
                            name + "' is already exists")); }
                    int unifiedValue = converter.makeUni(value, currencyString);
                    return itemsCollection.insertOne(new Item(name, unifiedValue).toDocument());
                });
    }

    // Предоставления списка товаров
    public Observable<Item> getCollectionItems() {
        return itemsCollection.find()
                .toObservable()
                .map(Item::new);
    }

    public Item convertItemTo(Item item, String currency) {
        return new Item(item.itemName, converter.makeValueFromUni(item.itemValue, currency));
    }
}
