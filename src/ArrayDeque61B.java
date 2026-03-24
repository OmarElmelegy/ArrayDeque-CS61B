import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ArrayDeque61B<T> implements Deque61B<T>, Iterable<T> {

    private T[] items;
    private int size;
    private int capacity = 8;
    private int front;
    private int back;

    @SuppressWarnings("unchecked")
    public ArrayDeque61B() {
        items = (T[]) new Object[capacity];
        size = 0;
        front = capacity / 2;
        back = front + 1;
    }

    @Override
    public void addFirst(T x) {
        if (size == capacity) {
            resize(capacity * 2);
        }

        items[front] = x;
        front = (front - 1 + capacity) % capacity;
        size += 1;
    }

    @Override
    public void addLast(T x) {

        if (size == capacity) {
            resize(capacity * 2);
        }

        items[back] = x;
        back = (back + 1) % capacity;
        size += 1;
    }

    @Override
    public List<T> toList() {
        if (isEmpty()) {
            return new ArrayList<>();
        }
        List<T> returnList = new ArrayList<>(size);

        int currentIndex = (front + 1) % capacity;

        for (int count = 0; count < size; count++) {
            returnList.add(items[currentIndex]);
            currentIndex = (currentIndex + 1) % capacity;
        }

        return returnList;
    }

    @SuppressWarnings("unchecked")
    private void resize(int newCapacity) {
        T[] newItems = (T[]) new Object[newCapacity];

        int new_front = (newCapacity - size) / 2;
        int old_index;
        for (int count = 0; count < size; count++) {
            old_index = (front + 1 + count) % capacity;
            newItems[new_front + count] = items[old_index];
        }

        front = new_front - 1;
        back = new_front + size;

        capacity = newCapacity;
        items = newItems;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T getFirst() {
        if (isEmpty()) {
            return null;
        }

        T item = items[(front + 1) % capacity];
        return item;
    }

    @Override
    public T getLast() {
        if (isEmpty()) {
            return null;
        }

        T item = items[(back - 1 + capacity) % capacity];
        return item;
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }

        // Look at he next item (The first added one)
        front = (front + 1) % capacity;
        T item = items[front];
        items[front] = null;

        size -= 1;

        if (size <= capacity / 4) {
            resize(capacity / 2);
        }

        return item;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }

        // Look at previous item (the last added one)
        back = (back - 1 + capacity) % capacity;
        T item = items[back];
        items[back] = null;

        size -= 1;

        if (size <= capacity / 4) {
            resize(capacity / 2);
        }

        return item;
    }

    @Override
    public T get(int index) {
        if (isEmpty() || index < 0 || index >= size) {
            return null;
        }

        int physicalMemory = (front + index + 1) % capacity;
        T item = items[physicalMemory];

        return item;
    }

    @Override
    public T getRecursive(int index) {
        return null;
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayDeque61BIterator();
    }

    private class ArrayDeque61BIterator implements Iterator<T> {
        private int currIndex;
        private int count;
        private int itemIndex;

        public ArrayDeque61BIterator() {
            currIndex = (front + 1) % capacity;
            count = 0;
        }

        @Override
        public boolean hasNext() {
            return count < size;
        }

        @Override
        public T next() {
            itemIndex = (currIndex + count) % capacity;
            T item = items[itemIndex];
            count += 1;

            return item;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if ((o instanceof Deque61B)) {

            Deque61B<T> other = (Deque61B<T>) o;

            if (!(this.size() == other.size())) {
                return false;
            }

            for (int i = 0; i < size; i++) {
                if (!(this.get(i).equals(other.get(i)))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        List<String> listOfItems = new ArrayList<>();
        for (T x : this) {
            listOfItems.add(x.toString());
        }
        return "{" + String.join(", ", listOfItems) + "}";
    }

    public static void main(String[] args) {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();

        // Assuming starting capacity is 8.
        // Add elements to both sides to force the internal pointers
        // to wrap around to the middle of the physical array.
        deque.addLast(1);
        deque.addLast(2);
        deque.addLast(3);
        deque.addLast(4);

        deque.addFirst(0);
        deque.addFirst(-1);

        System.out.println(deque.toString());

        ArrayDeque61B<Integer> deque2 = new ArrayDeque61B<>();

        // Assuming starting capacity is 8.
        // Add elements to both sides to force the internal pointers
        // to wrap around to the middle of the physical array.
        deque2.addLast(1);
        deque2.addLast(2);
        deque2.addLast(3);
        deque2.addLast(4);

        deque2.addFirst(0);
        deque2.addFirst(-1);

        System.out.println(deque2.toString());

        System.out.println(deque.equals(deque2));

    }

}
