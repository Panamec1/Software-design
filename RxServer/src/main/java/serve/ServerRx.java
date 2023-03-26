package serve;

import dao.*;
import io.netty.buffer.ByteBuf;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import obj.Item;
import obj.User;
import rx.Observable;

import java.util.*;
import java.util.stream.Collectors;

public class ServerRx {
    private final UserDao userDao;
    private final ItemsCatalogDao catalogDao;

    public ServerRx(UserDao userDao, ItemsCatalogDao catalogDao) {
        this.userDao = userDao;
        this.catalogDao = catalogDao;
    }

    public Observable<String> getResponse(HttpServerRequest<ByteBuf> req) {
        String path = req.getDecodedPath().substring(1);
        switch (path) {
            case "register":
                Optional<String> regError = checkParams(req, Arrays.asList("id", "name", "currency"));
                if (regError.isPresent()) {return Observable.just(regError.get());}
                Map<String, List<String>> queryRegParameters = req.getQueryParameters();

                String regName = queryRegParameters.get("name").get(0);
                int regId = Integer.parseInt(queryRegParameters.get("id").get(0));
                String regCurrency = queryRegParameters.get("currency").get(0).toUpperCase();

                return userDao.registerUser(regId, regCurrency, regName).
                        map(Objects::toString).
                        onErrorReturn(Throwable::getMessage);


            case "catalog":
                Optional<String> catError = checkParams(req, List.of("id"));
                if (catError.isPresent()) { return Observable.just(catError.get()); }
                Map<String, List<String>> queryParameters = req.getQueryParameters();

                int id = Integer.parseInt(queryParameters.get("id").get(0));
                Observable<Item> catalogDaoItems = catalogDao.getCollectionItems();
                Observable<User> user = userDao.getUserById(id);

                return user.isEmpty().flatMap(isEmpty -> {
                    if (isEmpty) { return Observable.just("There is no such id " + id); }
                    Observable<Item> currencyItems = user.flatMap(u -> catalogDaoItems.
                            map(item -> catalogDao.convertItemTo(item, u.userCurren)));
                    return currencyItems.collect(StringBuilder::new, (sb, x) -> sb.append(x).append("\n")).
                            map(StringBuilder::toString); }
                );

            case "add_item":
                Optional<String> errCheck = checkParams(req, Arrays.asList("name", "value", "currency"));
                if (errCheck.isPresent()) { return Observable.just(errCheck.get()); }
                Map<String, List<String>> queryAddParameters = req.getQueryParameters();

                String addName = queryAddParameters.get("name").get(0);
                int value = Integer.parseInt(queryAddParameters.get("value").get(0));
                String addCurrency = queryAddParameters.get("currency").get(0).toUpperCase();

                return catalogDao.addItem(addName, value, addCurrency).
                        map(Objects::toString).
                        onErrorReturn(Throwable::getMessage);

        }
        return Observable.just("Unknown request" + "\n" +
                "add_item\n" +
                "Parameters: name, value, currency\n" +
                "register\n" +
                "Parameters: name, id, currency\n" +
                "catalog\n" +
                "Parameters: id\n"
        );
    }

    private Optional<String> checkParams(HttpServerRequest<ByteBuf> req, List<String> parameters) {
        String err = parameters.stream().
                filter(x -> !req.getQueryParameters().containsKey(x)).
                collect(Collectors.joining(", "));
        return (err.isEmpty()) ? Optional.empty() : Optional.
                of("no parameters in request: " + err);
    }
}
