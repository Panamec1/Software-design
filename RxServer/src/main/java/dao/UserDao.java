package dao;

import com.mongodb.client.model.Filters;
import com.mongodb.rx.client.MongoClient;
import com.mongodb.rx.client.MongoCollection;
import com.mongodb.rx.client.Success;
import obj.User;
import org.bson.Document;
import rx.Observable;

public class UserDao {
    MongoCollection<Document> userCollection;

    public UserDao(MongoClient client) {
        userCollection = client.getDatabase("catalog").
                getCollection("users");
    }

    public Observable<Success> registerUser(int id, String currencyString, String name) {
        return userCollection.find(Filters.eq("id", id))
                .toObservable()
                .isEmpty()
                .flatMap(isEmpty -> {
                    if (!isEmpty) {
                        return Observable.error(new IllegalArgumentException("User with id '" + id + "'is already exists"));
                    }
                    return userCollection.insertOne(new User(id, name, currencyString).toDocument());
                });
    }

    public Observable<User> getUserById(int id) {
        return userCollection.find(Filters.eq("id", id))
                .toObservable()
                .map(User::new);
    }
}
