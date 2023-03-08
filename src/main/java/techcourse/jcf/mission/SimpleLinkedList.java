package techcourse.jcf.mission;

public class SimpleLinkedList implements SimpleList {
    private int size = 0;

    Node<String> first;
    Node<String> last;

    public SimpleLinkedList() {
    }

    @Override
    public boolean add(String value) {
        linkLast(value);
        return true;
    }

    @Override
    public void add(int index, String value) {
        if (index < 0 || index > size()) {
            throw new IndexOutOfBoundsException();
        }
        if (index == size) {
            linkLast(value);
        } else {
            linkBefore(value, node(index));
        }
    }

    @Override
    public String set(int index, String value) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        }
        Node<String> x = node(index);
        String oldValue = x.item;
        x.item = value;
        return oldValue;
    }

    @Override
    public String get(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        }
        return node(index).item;
    }

    @Override
    public boolean contains(String value) {
        return indexOf(value) >= 0;
    }

    @Override
    public int indexOf(String value) {
        int index = 0;
        if (value == null) {
            for (Node<String> x = first; x != null; x = x.next) {
                if (x.item == null) {
                    return index;
                }
                index++;
            }
        } else {
            for (Node<String> x = first; x != null; x = x.next) {
                if (value.equals(x.item)) {
                    return index;
                }
                index++;
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
        if (value == null) {
            for (Node<String> x = first; x != null; x = x.next) {
                if (x.item == null) {
                    unlink(x);
                    return true;
                }
            }
        } else {
            for (Node<String> x = first; x != null; x = x.next) {
                if (value.equals(x.item)) {
                    unlink(x);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String remove(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        }
        return unlink(node(index));
    }

    @Override
    public void clear() {
        for (Node<String> x = first; x != null; ) {
            Node<String> next = x.next;
            x.item = null;
            x.prev = null;
            x.next = null;
            x = next;
        }
        first = last = null;
        size = 0;
    }

    void linkLast(String value) {
        final Node<String> l = last;
        final Node<String> newNode = new Node<String>(l, value, null);
        last = newNode;
        if (l == null) {
            first = newNode;
        } else {
            l.next = newNode;
        }
        size++;
    }

    void linkBefore(String value, Node<String> succ) {
        final Node<String> pred = succ.prev;
        final Node<String> newNode = new Node<String>(pred, value, succ);
        succ.prev = newNode;
        if (pred == null) {
            first = newNode;
        } else {
            pred.next = newNode;
        }
        size++;
    }

    String unlink(Node<String> x) {
        final String element = x.item;
        final Node<String> next = x.next;
        final Node<String> prev = x.prev;

        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
        }

        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
        }
        x.item = null;
        x.prev = null;
        x.next = null;
        size--;
        return element;
    }

    private static class Node<String> {
        String item;
        Node<String> next;
        Node<String> prev;

        Node(Node<String> prev, String element, Node<String> next) {
            this.item = element;
            this.prev = prev;
            this.next = next;
        }
    }

    Node<String> node(int index) {
        if (index < (size >> 1)) {
            Node<String> x = first;
            for (int i = 0; i < index; i++) {
                x = x.next;
            }
            return x;
        } else {
            Node<String> x = last;
            for (int i = size - 1; i > index; i--) {
                x = x.prev;
            }
            return x;
        }
    }
}
