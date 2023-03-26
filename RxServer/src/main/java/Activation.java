import com.mongodb.rx.client.MongoClient;
import com.mongodb.rx.client.MongoClients;
import dao.ItemsCatalogDao;
import obj.CurrencyConvertion;

import dao.UserDao;
import rx.Observable;
import serve.ServerRx;
import io.reactivex.netty.protocol.http.server.HttpServer;

import java.util.HashMap;
import java.util.Map;


public class Activation {
    static Map<String, Integer> currencyMap(){
        Map<String, Integer> currencyMap = new HashMap<>();
        currencyMap.put("EUROS", 94);
        currencyMap.put("RUBLES", 100);
        currencyMap.put("DOLLARS", 75);
        return currencyMap;
    }

    public static void main(String[] args) {
        MongoClient client = MongoClients.create("mongodb://localhost:27017");
        CurrencyConvertion converter = new CurrencyConvertion(currencyMap());

        ItemsCatalogDao catalogDao = new ItemsCatalogDao(client, converter);
        ServerRx server = new ServerRx(new UserDao(client), catalogDao);

        HttpServer.newServer(8080)
                .start((req, resp) -> {
                    Observable<String> responser = server.getResponse(req);
                    return resp.writeString(responser.map(s -> s + System.lineSeparator()));
                }).awaitShutdown();
    }
}