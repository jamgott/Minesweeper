public interface SimpleCollection<E> extends Iterable<E> { 
  boolean isEmpty();
  void add(E x);
  boolean contains(Object o);
}