import java.util.HashMap;
import java.util.Map;

public class LruCache<Key, Val> {
    public int maxSize;

    private LruNode headNode = new LruNode(null, null);
    private LruNode tailNode = new LruNode(null, null);
    private final Map<Key, LruNode> kvmapper;

    private int sizer = 100;



    public LruCache() {
        headNode.prev = tailNode;
        tailNode.next = headNode;

        maxSize = sizer;
        kvmapper = new HashMap<>(sizer);
    }


    public LruCache(int setSize) {
        assert (setSize >= 0) : "Set size cannot be negative";
        assert (setSize <= sizer) : "Set size is to big";

        headNode.prev = tailNode;
        tailNode.next = headNode;

        maxSize = setSize;
        kvmapper = new HashMap<>(setSize);
    }

    public Val getValueForKey(Key key) {
        assert (key != null) : "Key cannot be null";
        assert (kvmapper.containsKey(key)) : "There's no key";

        int sizeOld = size();
        LruNode dummy = new LruNode(null, null);
        LruNode node = kvmapper.getOrDefault(key, dummy);

        // Переподвешивание изпользованной вершины
        node.prev.next = node.next;
        node.next.prev = node.prev;

        node.next = headNode;
        node.prev = headNode.prev;
        headNode.prev.next = node;
        headNode.prev = node;

        assert (sizeOld == size()) : "Size must not change";
        assert (node.val == headNode.prev.val) : "Value wasn't inserted";
        return node.val;
    }

    public Key getTopKey() {
        assert (size() != 0) : "There is no elements in Cache";

        int size = size();
        Key key = headNode.prev.key;

        assert (size == size()) : "Size must not change";
        assert (key == headNode.prev.key) : "Top key must not change";
        return key;
    }


    public void add(Key key, Val value) {
        assert (key != null) : "Add key cannot be null";
        assert (value != null) : "Add value cannot be null";
        assert (kvmapper.size() <= maxSize) : "Cache size is too big";


        LruNode node = kvmapper.get(key);

        if (kvmapper.size() == maxSize) {
            // Удалиение последнего хвоста
            Key tailKey = tailNode.next.key;
            tailNode.next.next.prev = tailNode;
            tailNode.next = tailNode.next.next;
            kvmapper.remove(tailKey);
        }

        if (node != null) {
            // Подвешивание новой вершины
            node.prev = headNode.prev;
            node.next = headNode;

            // Переподвешивание вершины
            headNode.prev.next = node;
            headNode.prev = node;

        } else {
            // Подвешивание новой вершины
            LruNode newNode = new LruNode(key, value);
            newNode.prev = headNode.prev;
            newNode.next = headNode;

            // Переподвешиввание вершин
            headNode.prev.next = newNode;
            headNode.prev = newNode;

            kvmapper.put(key, newNode);
        }

        assert (size() <= sizer) : "Add key cannot be null";
        assert (kvmapper.containsKey(key)) : "There's no key";
        assert (kvmapper.get(key).val == value) : "Inserted wrong value with for the key";
        assert (headNode.prev.key == key) : "Key wasn't inserted";
        assert (headNode.prev.val == value) : "Value wasn't inserted";
    }

    public int size() {return kvmapper.size();}

    private final class LruNode {
        Key key;
        Val val;
        LruNode prev;
        LruNode next;

        public LruNode(Key key, Val val) {
            this.key = key;
            this.val = val;
        }
    }
}