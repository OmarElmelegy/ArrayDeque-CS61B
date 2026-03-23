import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static com.google.common.truth.Truth.assertThat;

public class ArrayDeque61BTest {

    @Test
    public void addFirstAndIsEmptyTest() {
        // Assuming your implementation class will be named ArrayDeque61B
        Deque61B<Integer> deque = new ArrayDeque61B<>();

        // A newly initialized deque should be empty
        assertTrue(deque.isEmpty());
        assertEquals(0, deque.size());

        deque.addFirst(10);
        deque.addFirst(20);

        // After adding one element, it should no longer be empty
        assertFalse(deque.isEmpty());
        assertEquals(2, deque.size());
        
        // The first element should be the one we just added
        assertEquals(20, deque.getFirst());
    }

        @Test
    public void addFirstWrapAroundTest() {
        Deque61B<Integer> deque = new ArrayDeque61B<>();

        // 5 calls to addFirst will force a wrap-around to the right side.
        deque.addFirst(10); 
        deque.addFirst(15);
        deque.addFirst(20);
        deque.addFirst(25);
        deque.addFirst(30); // This insertion wraps around!

        // The logical order must remain perfectly linear, regardless of physical memory
        assertThat(deque.toList()).containsExactly(30, 25, 20, 15, 10).inOrder();
    }

    @Test
    public void addLastWrapAroundTest() {
        Deque61B<Integer> deque = new ArrayDeque61B<>();

        // 5 calls to addFirst will force a wrap-around to the right side.
        deque.addLast(10); 
        deque.addLast(15);
        deque.addLast(20);
        deque.addLast(25);
        deque.addLast(30); // This insertion wraps around!

        // The logical order must remain perfectly linear, regardless of physical memory
        assertThat(deque.toList()).containsExactly(10, 15, 20, 25, 30).inOrder();

        assertThat(deque.getLast()).isEqualTo(30);
    }

    @Test
    public void getAndGetRecursiveTest() {
        Deque61B<Integer> deque = new ArrayDeque61B<>();

        // 5 calls to addFirst will force a wrap-around to the right side.
        deque.addLast(10); 
        deque.addFirst(5);
        deque.addLast(15);

        assertThat(deque.get(1)).isEqualTo(10);
        assertThat(deque.get(2)).isEqualTo(15);
        assertThat(deque.get(0)).isEqualTo(5);

        assertThat(deque.getRecursive(0)).isNull();
    }
}
