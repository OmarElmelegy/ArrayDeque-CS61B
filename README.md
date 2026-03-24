# Array-Based Deque 

This repository contains a complete, highly-optimized implementation of a **Double-Ended Queue (Deque)** using a dynamically resizing circular array in Java (Project 2 of UC Berkley CS61B DSA course). 

It is designed to support generic types and perform addition, removal, and retrieval operations at both ends of the queue in **constant O(1) time**.

## Features

* **Circular Array Architecture:** Utilizes modulo arithmetic to efficiently wrap `front` and `back` pointers around the physical array bounds, treating the array as continuous and eliminating the need to shift elements.
* **Dynamic Resizing:** * **Scales Up:** Automatically doubles in capacity when the array is completely full.
  * **Scales Down:** Halves in capacity when memory usage drops to 25% or below, preventing memory waste.
  * **Re-centering:** When resizing, elements are mathematically re-centered in the new array to optimize pointer runway.
* **Generic Types:** Supports the storage of any object type (`<T>`).
* **Iterable:** Implements the `Iterable<T>` interface, allowing the deque to be traversed natively using standard Java `for-each` loops.
* **Deep Equality:** Includes a robust `equals()` method that checks for logical value equality (not just reference equality) against any other `Deque61B` implementation.

## File Structure

* `ArrayDeque61B.java`: The core data structure implementation. Includes custom iterator logic, modulo-based index fetching, and memory management algorithms.
* `ArrayDeque61BTest.java`: A comprehensive testing suite written using **JUnit 5** and **Google Truth** assertions. 

## Testing

The included test suite aggressively tests the edges of the circular array logic to ensure pointers do not cross or drop data. Tests include:

* **Basic Operations:** Adding, removing, and retrieving items.
* **Pointer Wrap-Around:** Forcing the internal pointers to wrap off the ends of the physical array to verify logical continuity.
* **Resize Up:** Flooding the deque with items to trigger multiple 2x capacity expansions.
* **Resize Down:** Inflating the deque and subsequently draining it to trigger array shrinkage and verify proper element retention during compression.

## Requirements

To compile and run the tests, you will need:
* Java (JDK 11 or higher recommended)
* JUnit 5
* Google Truth (for fluent assertions)

---

### A Note on Implementation details
The internal pointers (`front` and `back`) start offset by 1 in the center of the array. `front` always points to the index where the *next* first element will be placed, while `back` always points to the index where the *next* last element will be placed.

---