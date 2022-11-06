import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class LruCache<Key, Val> {
    private final Map<Key, Val> kvmapper  = new HashMap<>();
    private final List<Key> klist         = new LinkedList<>();
    private       int sizer               = 100;


    public LruCache(){}

    LruCache(int size) {
        assert (size >= 0):"Set size cannot be negative";
        assert (size <= sizer):"Set size is to big";
        sizer = size;
    }

    public int getCacheSize() {
        int size = klist.size();
        assert (size == klist.size()):"Size must not change";
        return size;
    }


    public Val get(Key key) {
        assert (key != null):"Key cannot be null";
        assert (kvmapper.containsKey(key)):"There's no key";

        int si    = klist.size();
        Val value = kvmapper.get(key);

        assert (value == kvmapper.get(key)):"Value didn't change";
        assert (si == klist.size()):"Size must not change";
        return value;
    }

    public Key getTopKey() {
        assert (klist.size()  != 0):"There is no elements in Cache";

        int size = klist.size();
        Key key  = klist.get(klist.size() - 1);


        assert (key  == klist.get(klist.size() - 1)):"Top key must not change";
        assert (size == klist.size()):"Size must not change";
        return key;
    }

    public void add(Key key, Val value) {
        assert (key   != null):"Add key cannot be null";
        assert (value != null):"Add value cannot be null";

        if (sizer == klist.size()) {
            Key oldest = klist.get(0);
            klist.remove(oldest);
            kvmapper.remove(oldest);
        }
        if (kvmapper.containsKey(key)) {
            kvmapper.replace(key, value);
            klist.remove(key);
            klist.add(key);
            assert (kvmapper.get(key) == value):"Inserted wrong value with for the key";
            assert (klist.size() <= sizer):"Add key cannot be null";
            return;
        }
        kvmapper.put(key, value);
        klist.add(key);

        assert (klist.size() <= sizer):"Add key cannot be null";
        assert (kvmapper.get(key) == value):"Inserted wrong value with for the key";
        assert (kvmapper.containsKey(key)):"There's no key";
    }
}
