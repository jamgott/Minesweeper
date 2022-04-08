import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PathSetTest {

    @Test
    void testAddContainsOrder() {
        List<String> url1 = Arrays.asList(new String[] { "www", "upenn", "edu" });
        List<String> url2 = Arrays.asList(new String[] { "www", "upenn", "edu", "parking" });
        List<String> url3 = Arrays.asList(new String[] { "www", "upenn", "edu", "bookstore" });
        List<String> url4 = Arrays.asList(new String[] { "www" });

        PathSet s = new PathSet();
        Assertions.assertTrue(s.isEmpty());
        Assertions.assertFalse(s.contains(url1));
        Assertions.assertFalse(s.contains(url2));
        Assertions.assertFalse(s.contains(url3));
        Assertions.assertFalse(s.contains(url4));

        s.add(url1);
        Assertions.assertFalse(s.isEmpty());
        Assertions.assertTrue(s.contains(url1));
        Assertions.assertFalse(s.contains(url2));
        Assertions.assertFalse(s.contains(url3));
        Assertions.assertFalse(s.contains(url4));

        s.add(url2);
        Assertions.assertFalse(s.isEmpty());
        Assertions.assertTrue(s.contains(url1));
        Assertions.assertTrue(s.contains(url2));
        Assertions.assertFalse(s.contains(url3));
        Assertions.assertFalse(s.contains(url4));

        s.add(url3);
        Assertions.assertFalse(s.isEmpty());
        Assertions.assertTrue(s.contains(url1));
        Assertions.assertTrue(s.contains(url2));
        Assertions.assertTrue(s.contains(url3));
        Assertions.assertFalse(s.contains(url4));

        s.add(url4);
        Assertions.assertFalse(s.isEmpty());
        Assertions.assertTrue(s.contains(url1));
        Assertions.assertTrue(s.contains(url2));
        Assertions.assertTrue(s.contains(url3));
        Assertions.assertTrue(s.contains(url4));
    }


    @Test
    void testIterator() {
        List<String> url1 = Arrays.asList(new String[] {"a"});
        List<String> url2 = Arrays.asList(new String[] {"a", "b", "c"});
        List<String> url3 = Arrays.asList(new String[] {"a", "b", "d"});
        List<String> url4 = Arrays.asList(new String[] {"a", "b", "a"});
        List<String> url5 = Arrays.asList(new String[] {"a", "c"});
        
        PathSet s = new PathSet();
        Assertions.assertTrue(s.isEmpty());
        
        s.add(url2);
        s.add(url1);
        s.add(url4);
        s.add(url5);
        s.add(url3);
        Iterator<List<String>> another = s.iterator();
        another.next();

       

        /* OMITTED CODE */
               
        Iterator<List<String>> it = s.iterator();
        Assertions.assertTrue(it.hasNext());
        Assertions.assertEquals(url1, it.next());
        Assertions.assertEquals(url4, it.next());
        Assertions.assertEquals(url2, it.next());
        Assertions.assertEquals(url3, it.next());
        Assertions.assertEquals(url5, it.next());
        Assertions.assertFalse(it.hasNext());
        Assertions.assertThrows(NoSuchElementException.class, () -> {it.next();});
    }

}

