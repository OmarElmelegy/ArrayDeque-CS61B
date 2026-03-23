import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

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
                assertThat(deque.toList()).containsExactly().inOrder();


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
        assertThat(deque.getFirst()).isNull();
        assertThat(deque.getLast()).isNull();
        assertThat(deque.get(1)).isNull();
        assertThat(deque.get(15)).isNull();
        assertThat(deque.get(-1)).isNull();

        // 5 calls to addFirst will force a wrap-around to the right side.
        deque.addLast(10);
        deque.addFirst(5);
        deque.addLast(15);

        assertThat(deque.get(1)).isEqualTo(10);
        assertThat(deque.get(2)).isEqualTo(15);
        assertThat(deque.get(0)).isEqualTo(5);

        assertThat(deque.getRecursive(0)).isNull();
    }

    @Test
    public void removeFirstTest() {
        Deque61B<Integer> deque = new ArrayDeque61B<>();
        assertThat(deque.removeFirst()).isNull();

        // 5 calls to addFirst will force a wrap-around to the right side.
        deque.addLast(10);
        deque.addFirst(5);
        deque.addLast(15);
        deque.addLast(20);
        deque.addLast(25);
        deque.addLast(30);

        assertThat(deque.removeFirst()).isEqualTo(5);
        assertThat(deque.toList()).containsExactly(10, 15, 20, 25, 30).inOrder();

    }

    @Test
    public void removeLastTest() {
        Deque61B<Integer> deque = new ArrayDeque61B<>();
        assertThat(deque.removeLast()).isNull();

        // 5 calls to addFirst will force a wrap-around to the right side.
        deque.addLast(10);
        deque.addFirst(5);
        deque.addLast(15);
        deque.addLast(20);
        deque.addLast(25);
        deque.addLast(30);

        assertThat(deque.removeLast()).isEqualTo(30);
        assertThat(deque.toList()).containsExactly(5, 10, 15, 20, 25).inOrder();

    }

    @Test
    public void resizeUpBasicTest() {
        Deque61B<Integer> deque = new ArrayDeque61B<>();

        // Add 100 items. If starting capacity is 8,
        // this will force multiple resize-ups (8 -> 16 -> 32 -> 64 -> 128)
        for (int i = 0; i < 100; i++) {
            deque.addLast(i);
        }

        // Check that size is correct
        assertThat(deque.toList()).hasSize(100);

        // Check that all elements are there in the correct order
        List<Integer> list = deque.toList();
        for (int i = 0; i < 100; i++) {
            assertThat(list.get(i)).isEqualTo(i);
        }
    }

    @Test
    public void resizeUpWithWrapAroundTest() {
        Deque61B<Integer> deque = new ArrayDeque61B<>();

        // Assuming starting capacity is 8.
        // Add elements to both sides to force the internal pointers
        // to wrap around to the middle of the physical array.
        deque.addLast(1);
        deque.addLast(2);
        deque.addLast(3);
        deque.addLast(4);

        deque.addFirst(0);
        deque.addFirst(-1);
        deque.addFirst(-2);
        deque.addFirst(-3);
        // At this point, the array is completely full (size 8).
        // Physically, the array might look like: [1, 2, 3, 4, x, -3, -2, -1, 0]

        // This next addition MUST trigger a resize.
        deque.addLast(5);

        // The logical order should remain perfectly intact!
        assertThat(deque.toList())
                .containsExactly(-3, -2, -1, 0, 1, 2, 3, 4, 5)
                .inOrder();
    }

    @Test
    public void resizeDownTest() {
        Deque61B<Integer> deque = new ArrayDeque61B<>();

        // Inflate the array up to 100 elements
        for (int i = 0; i < 50; i++) {
            deque.addLast(i);
        }

        for (int i = 0; i < 50; i++) {
            deque.addFirst(i);
        }

        // Deflate the array down to just 10 elements.
        // This should force a resize-down (usage drops well below 25%).
        for (int i = 0; i < 40; i++) {
            deque.removeLast();
        }

        for (int i = 0; i < 50; i++) {
            deque.removeFirst();
        }

        // Verify that the remaining elements are exactly 0 through 9
        assertThat(deque.toList()).hasSize(10);
        assertThat(deque.toList())
                .containsExactly(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)
                .inOrder();

        // Let's add an item just to make sure the pointers are still
        // functioning correctly after the array shrunk!
        deque.addLast(10);
        assertThat(deque.toList().get(10)).isEqualTo(10);
    }

}
