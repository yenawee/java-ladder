package techcourse.jcf.mission;

public class SimpleArrayList implements SimpleList {
    private static int DEFAULT_CAPACITY = 10;

    private int size = 0;
    private int capacity;
    private String[] elements;

    public SimpleArrayList() {
        this.capacity = DEFAULT_CAPACITY;
        this.elements = new String[DEFAULT_CAPACITY];
    }

    public SimpleArrayList(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("초기 캐패시티는 양수여야 합니다.");
        }
        this.capacity = initialCapacity;
        this.elements = new String[initialCapacity];
    }

    @Override
    public boolean add(String value) {
        if (size < capacity) {
            elements[size] = value;
        } else {
            capacity <<= 2;
            String[] newElements = new String[capacity];
            for (int i = 0; i < elements.length; i++) {
                newElements[i] = elements[i];
            }
            newElements[size] = value;
            this.elements = newElements;
        }
        size++;
        return true;
    }

    @Override
    public void add(int index, String value) {
        if (index < 0 || index > size()) {
            throw new IndexOutOfBoundsException("잘못된 index 입력입니다");
        }
        if (size == capacity) {
            capacity <<= 2;
        }
        String[] newElements = new String[capacity];
        for (int i = 0; i < index; i++) {
            newElements[i] = elements[i];
        }
        newElements[index] = value;
        for (int i = index; i < size; i++) {
            newElements[i] = elements[i];
        }
        this.elements = newElements;
        size++;
    }

    @Override
    public String set(int index, String value) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        }
        String oldValue = elements[index];
        elements[index] = value;
        return oldValue;
    }

    @Override
    public String get(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        }
        return elements[index];
    }

    @Override
    public boolean contains(String value) {
        return indexOf(value) >= 0;
    }

    @Override
    public int indexOf(String value) {
        return indexOfRange(value, 0, size);
    }

    int indexOfRange(Object o, int start, int end) {
        Object[] es = elements;
        if (o == null) {
            for (int i = start; i < end; i++) {
                if (es[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = start; i < end; i++) {
                if (o.equals(es[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean remove(String value) {
        int index = indexOfRange(value, 0, size);
        if (index >= 0) {
            final Object[] es = elements;
            int newSize = size - 1;
            System.arraycopy(es, index + 1, es, index, newSize - index);
            es[size = newSize] = null;
            return true;
        }
        return false;
    }

    @Override
    public String remove(int index) {
        if (index < 0 || index > size()) {
            throw new IndexOutOfBoundsException();
        }
        final Object[] es = elements;
        String oldValue = (String) es[index];
        int newSize = size - 1;
        System.arraycopy(es, index + 1, es, index, newSize - index);
        es[size = newSize] = null;
        return oldValue;
    }

    @Override
    public void clear() {
        for (int to = size, i = size = 0; i < to; i++) {
            elements[i] = null;
        }
    }
}
