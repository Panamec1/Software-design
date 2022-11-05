import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestForLruCache {

    @Test
    public void NotExistsKey() {
        LruCache<Integer, Integer> lcache = new LruCache<>();
        lcache.add(1, 1);
        assertThrows(AssertionError.class, () -> System.out.println(lcache.get(3)));
    }

    @Test
    public void AddOneCheckOne() {
        LruCache<Integer, Integer> lcache = new LruCache<>();
        lcache.add(1, 1);
        assertEquals(lcache.getCacheSize(), 1);
        assertEquals(lcache.get(1), 1);
    }

    @Test
    public void CheckTop() {
        LruCache<Integer, Integer> lcache = new LruCache<>();
        lcache.add(1, 1);
        lcache.add(2, 2);
        assertEquals(lcache.getCacheSize(), 2);
        assertEquals(lcache.getTopKey(), 2);
    }

    @Test
    public void AddFourNotSetLength() {
        LruCache<Integer, Integer> lcache = new LruCache<>();
        lcache.add(1, 1);
        lcache.add(2, 2);
        lcache.add(3, 3);
        lcache.add(4, 4);
        assertEquals(lcache.getCacheSize(), 4);
        assertEquals(lcache.get(1), 1);
        assertEquals(lcache.get(2), 2);
        assertEquals(lcache.get(3), 3);
        assertEquals(lcache.get(4), 4);

    }

    @Test
    public void Length2Add4() {
        LruCache<Integer, Integer> lcache = new LruCache<>(2);
        lcache.add(1, 1);
        lcache.add(2, 2);
        lcache.add(3, 3);
        lcache.add(4, 4);
        lcache.add(1, 1);
        assertEquals(lcache.getCacheSize(), 2);
        assertEquals(lcache.get(1), 1);
        assertEquals(lcache.get(4), 4);
        assertThrows(AssertionError.class, () -> System.out.println(lcache.get(3)));
        assertThrows(AssertionError.class, () -> System.out.println(lcache.get(2)));
    }

    @Test
    public void AddFiveNotSetLength() {
        LruCache<Integer, Integer> lcache = new LruCache<>();
        lcache.add(1, 1);
        lcache.add(3, 3);
        lcache.add(2, 2);
        lcache.add(4, 4);
        lcache.add(1, 1);
        assertEquals(lcache.getCacheSize(), 4);
        assertEquals(lcache.get(1), 1);
        assertEquals(lcache.get(4), 4);
        assertEquals(lcache.get(3), 3);
        assertEquals(lcache.get(2), 2);
    }

    @Test
    public void TopError() {
        LruCache<Integer, Integer> lcache = new LruCache<>();
        assertThrows(AssertionError.class, () -> System.out.println(lcache.getTopKey()));
    }

    @Test
    public void WrongSetSize() {
        assertThrows(AssertionError.class, () -> {
            LruCache<Integer, Integer> c = new LruCache<>(1000);
        });
    }

}