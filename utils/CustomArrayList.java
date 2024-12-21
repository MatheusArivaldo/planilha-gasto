package utils;

public class CustomArrayList<T> {
  private Object[] elements;
  private int size;
  private static final int DEFAULT_CAPACITY = 10;

  public CustomArrayList() {
    elements = new Object[DEFAULT_CAPACITY];
    size = 0;
  }

  public CustomArrayList(int initialCapacity) {
    if (initialCapacity < 0) {
      throw new IllegalArgumentException("Capacidade inicial não pode ser negativa");
    }
    elements = new Object[initialCapacity];
    size = 0;
  }

  public void add(T element) {
    ensureCapacity();
    elements[size++] = element;
  }

  @SuppressWarnings("unchecked")
  public T get(int index) {
    checkIndex(index);
    return (T) elements[index];
  }

  public void remove(int index) {
    checkIndex(index);
    int numMoved = size - index - 1;
    if (numMoved > 0) {
      System.arraycopy(elements, index + 1, elements, index, numMoved);
    }
    elements[--size] = null;
  }

  public int indexOf(T element) {
    for (int i = 0; i < size; i++) {
      if (elements[i].equals(element)) {
        return i;
      }
    }
    return -1;
  }

  public int size() {
    return size;
  }

  public boolean isEmpty() {
    return size == 0;
  }

  private void ensureCapacity() {
    if (size == elements.length) {
      int newCapacity = elements.length * 2;
      Object[] newElements = new Object[newCapacity];
      System.arraycopy(elements, 0, newElements, 0, size);
      elements = newElements;
    }
  }

  private void checkIndex(int index) {
    if (index < 0 || index >= size) {
      throw new IndexOutOfBoundsException("Índice: " + index + ", Tamanho: " + size);
    }
  }
}