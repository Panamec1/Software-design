import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LruCacheTest {

    @Test
    public void NotExistsKey() {
        LruCache<Integer, Integer> lcache = new LruCache<>();
        lcache.add(1, 1);
        assertThrows(AssertionError.class, () -> System.out.println(lcache.getValueForKey(3)));
    }

    @Test
    public void AddOneCheckOne() {
        LruCache<Integer, Integer> lcache = new LruCache<>();
        lcache.add(1, 1);
        assertEquals(lcache.size(), 1);
        assertEquals(lcache.getValueForKey(1), 1);
    }



    @Test
    public void AddFourNotSetLength() {
        LruCache<Integer, Integer> lcache = new LruCache<>();
        lcache.add(1, 1);
        lcache.add(2, 2);
        lcache.add(3, 3);
        lcache.add(4, 4);
        assertEquals(lcache.size(), 4);
        assertEquals(lcache.getValueForKey(1), 1);
        assertEquals(lcache.getValueForKey(2), 2);
        assertEquals(lcache.getValueForKey(3), 3);
        assertEquals(lcache.getValueForKey(4), 4);

    }

    @Test
    public void CheckTop() {
        LruCache<Integer, Integer> lcache = new LruCache<>();
        lcache.add(1, 1);
        lcache.add(2, 2);
        lcache.add(3, 3);
        assertEquals(lcache.size(), 3);
        assertEquals(lcache.getTopKey(), 3);
    }

    @Test
    public void Length2Add4() {
        LruCache<Integer, Integer> lcache = new LruCache<>(2);
        //System.out.println(lcache.size());
        lcache.add(1, 1);
        //System.out.println(lcache.size());
        lcache.add(2, 2);
        //System.out.println(lcache.size());
        lcache.add(3, 3);
        //System.out.println(lcache.size());
        lcache.add(4, 4);
        //System.out.println(lcache.size());
        lcache.add(1, 1);
        assertEquals(lcache.size(), 2);
        assertEquals(lcache.getValueForKey(1), 1);
        assertEquals(lcache.getValueForKey(4), 4);
        assertThrows(AssertionError.class, () -> System.out.println(lcache.getValueForKey(2)));
        assertThrows(AssertionError.class, () -> System.out.println(lcache.getValueForKey(3)));
    }

    @Test
    public void AddFiveNotSetLength() {
        LruCache<Integer, Integer> lcache = new LruCache<>();
        lcache.add(1, 1);
        lcache.add(3, 3);
        lcache.add(2, 2);
        lcache.add(4, 4);
        lcache.add(1, 1);
        assertEquals(lcache.size(), 4);
        assertEquals(lcache.getValueForKey(1), 1);
        assertEquals(lcache.getValueForKey(4), 4);
        assertEquals(lcache.getValueForKey(3), 3);
        assertEquals(lcache.getValueForKey(2), 2);
    }



    @Test
    public void WrongSetSize() {
        assertThrows(AssertionError.class, () -> {
            LruCache<Integer, Integer> c = new LruCache<>(1000);
        });
    }

}