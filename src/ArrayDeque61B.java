import java.util.ArrayList;
import java.util.List;

public class ArrayDeque61B<T> implements Deque61B<T> {

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

        int currentIndex = (front + 1)  % capacity;

        for (int count = 0; count < size; count++) {
            returnList.add(items[currentIndex]);
            currentIndex = (currentIndex + 1) % capacity;
        }

        return returnList;
    }

     private void resize(int i) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'resize'");
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

        T item = items[front + 1];
        return item;
    }

    @Override
    public T getLast() {
        if (isEmpty()) {
            return null;
        }

        T item = items[back - 1];
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

        size -= 1;

        if (size <= capacity / 4) {
            resize(capacity / 2);
            capacity /= 2;
        }

        return item;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }

        // Look at previous item (the last added one)
        back = (back - 1) % capacity;
        T item = items[back];

        size -= 1;

        if (size <= capacity / 4) {
            resize(capacity / 2);
            capacity /= 2;
        }

        return item;
    }

    @Override
    public T get(int index) {
        if (isEmpty() || index < 0 || index >= size) {
            return null;
        }

        T item = items[front + index + 1];
        
        return item;
    }

    @Override
    public T getRecursive(int index) {
        return null;
    }

}
