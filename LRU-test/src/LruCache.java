import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class LruCache<Key, Val> {

    private final int maxSize            = 100;
    private       int size               = 0;
    private final Map<Key, Val> kvmapper = new HashMap<>();
    private final List<Key> klist         = new LinkedList<>();
    private       int sizer              = maxSize;


    public LruCache(){}

    LruCache(int size) {
        assert (size >= 0 && size <= maxSize);
        sizer = size;
    }

    public int getCacheSize() {
        return size;
    }


    public Val get(Key key) {
        assert (kvmapper.containsKey(key));
        return kvmapper.get(key);
    }

    public Key getTopKey() {
        assert (size != 0);
        return klist.get(klist.size() - 1);
    }

    public void add(Key key, Val value) {
        size = size + 1;

        if (sizer == klist.size() ) {
            size = size - 1;
            Key oldest = klist.get(0);
            klist.remove(oldest);
            kvmapper.remove(oldest);


        }

        if (kvmapper.containsKey(key)) {
            size = size - 1;
            kvmapper.replace(key, value);
            klist.remove(key);
            klist.add(key);
            return;
        }
        kvmapper.put(key, value);
        klist.add(key);
    }
}
