package obj;

import org.bson.Document;

public class User {
    private final int userId;
    private final String userName;
    public final String userCurren;

    public User(int id, String name, String currency) {
        this.userId = id;
        this.userName = name;
        this.userCurren = currency;
    }

    public Document toDocument() {
        return new Document()
                .append("id", userId)
                .append("name", userName)
                .append("currency", userCurren);
    }

    public User(Document document){
        this(document.getInteger("id"),
                document.getString("name"),
                document.getString("currency"));
    }
}
