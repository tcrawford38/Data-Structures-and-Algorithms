package homework02;
import java.util.NoSuchElementException;

/**
 * Your implementation of a non-circular DoublyLinkedList with a tail pointer.
 *
 * @author Thomas crawford
 * @version 1.0
 * @userid tcrawford38
 * @GTID 903398010
 *
 * Collaborators:
 *
 * Resources:
 */
public class DoublyLinkedList<T> {

    // Do not add new instance variables or modify existing ones.
    private DoublyLinkedListNode<T> head;
    private DoublyLinkedListNode<T> tail;
    private int size;

    // Do not add a constructor.

    /**
     * Adds the element to the specified index.
     * <p>
     * Must be O(1) for indices 0 and size and O(n) for all other cases.
     *
     * @param index the index at which to add the new element
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index is negative or "
                    + "larger than list size");
        }
        if (data == null) {
            throw new IllegalArgumentException("Null data");
        }
        DoublyLinkedListNode<T> temp = new DoublyLinkedListNode<>(data);
        if (head == null) {
            head = temp;
            tail = temp;
        } else if (index == 0) {
            temp.setNext(head);
            head.setPrevious(temp);
            head = temp;
        } else if (index == size) {
            temp.setPrevious(tail);
            tail.setNext(temp);
            tail = temp;
        } else {
            DoublyLinkedListNode<T> ref;
            if (index < (size / 2)) {
                ref = head;
                for (int i = 0; i < index - 1: i++) {
                    ref = ref.getNext();
                }
            } else {
                ref = tail;
                for (int i = size - 1; i > index - 1; i--) {
                    ref = ref.getPrevious();
                }
            }
            temp.setNext(ref.getNext());
            ref.setNext(temp);
            temp.setPrevious(ref);
            temp.getNext().setPrevious(temp);
        }
        size++;
    }

    /**
     * Adds the element to the front of the list.
     * <p>
     * Must be O(1).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Null data");
        }
        DoublyLinkedListNode<T> temp = new DoublyLinkedListNode<>(
                data, null, head);
        if (head == null) {
            temp.setNext(null);
            head = temp;
            tail = temp;
        } else {
            head.setPrevious(temp);
            head = temp;
        }
        size++;
    }

    /**
     * Adds the element to the back of the list.
     * <p>
     * Must be O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Null data");
        }
        DoublyLinkedListNode<T> temp = new DoublyLinkedListNode<>(
                data, tail, null);
        if (head == null) {
            temp.setPrevious(null);
            tail = temp;
            head = temp;
        } else {
            tail.setNext(temp);
            tail = temp;
        }
        size++;
    }

    /**
     * Removes and returns the element at the specified index.
     * <p>
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is negative or "
                    + "larger than list size");
        }
        if (head == null) {
            return null;
        }
        if (size == 1 && index == 0) {
            DoublyLinkedListNode<T> temp = head;
            head = null;
            tail = null;
            size--;
            return temp.getData();
        } else if (index == 0) {
            DoublyLinkedListNode<T> temp = head;
            head = head.getNext();
            head.setPrevious(null);
            size--;
            return temp.getData();
        }  else if (index == size - 1) {
            DoublyLinkedListNode<T> temp = tail;
            tail = tail.getPrevious();
            tail.setNext(null);
            size--;
            return temp.getData();
        } else {
            DoublyLinkedListNode<T> temp;
            if (index < (size / 2)) {
                temp = head;
                for (int i = 0; i < index - 1: i++) {
                    temp = temp.getNext();
                }
            } else {
                temp = tail;
                for (int i = size - 1; i > index - 1; i--) {
                    temp = temp.getPrevious();
                }
            }
            temp.getNext().setPrevious(temp.getPrevious());
            temp.getPrevious().setNext(temp.getNext());
            size--;
            return temp.getData();
        }
    }

    /**
     * Removes and returns the first element of the list.
     * <p>
     * Must be O(1).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        DoublyLinkedListNode<T> temp = head;
        if (size == 0) {
            throw new NoSuchElementException("The list is empty");
        } else if (size == 1) {
            head = null;
            tail = null;
            size--;
            return temp.getData();
        } else {
            head = head.getNext();
            head.setPrevious(null);
            size--;
            return temp.getData();
        }
    }

    /**
     * Removes and returns the last element of the list.
     * <p>
     * Must be O(1).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        if (size == 0) {
            throw new NoSuchElementException("The list is empty");
        } else if (size == 1) {
            DoublyLinkedListNode<T> temp = tail;
            head = null;
            tail = null;
            size--;
            return temp.getData();
        } else {
            DoublyLinkedListNode<T> temp = tail;
            tail = tail.getPrevious();
            tail.setNext(null);
            size--;
            return temp.getData();
        }
    }

    /**
     * Returns the element at the specified index.
     * <p>
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is negative or "
                    + "larger than list size");
        }
        if (index == 0) {
            return head.getData();
        } else if (index == size - 1) {
            return tail.getData();
        } else {
            DoublyLinkedListNode<T> temp = head;
            if (index < (size / 2)) {
                temp = head;
                for (int i = 0; i < index - 1: i++) {
                    temp = temp.getNext();
                }
            } else {
                temp = tail;
                for (int i = size - 1; i > index - 1; i--) {
                    temp = temp.getPrevious();
                }
            }
            return temp.getData();
        }
    }

    /**
     * Returns whether or not the list is empty.
     * <p>
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the list.
     * <p>
     * Clears all data and resets the size.
     * <p>
     * Must be O(1).
     */
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Removes and returns the last copy of the given data from the list.
     * <p>
     * Do not return the same data that was passed in. Return the data that
     * was stored in the list.
     * <p>
     * Must be O(1) if data is in the tail and O(n) for all other cases.
     *
     * @param data the data to be removed from the list
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if data is not found
     */
    public T removeLastOccurrence(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        }
        DoublyLinkedListNode<T> temp = tail;
        int i = size - 1;
        while (temp != null) {
            if (temp.getData().equals(data)) {
                if (size == 1 && i == 0) {
                    head = null;
                    tail = null;
                    size--;
                    return temp.getData();
                } else if (i == 0) {
                    head = head.getNext();
                    head.setPrevious(null);
                    size--;
                    return temp.getData();
                }  else if (i == size - 1) {
                    tail = tail.getPrevious();
                    tail.setNext(null);
                    size--;
                    return temp.getData();
                } else {
                    temp.getNext().setPrevious(temp.getPrevious());
                    temp.getPrevious().setNext(temp.getNext());
                    size--;
                    return temp.getData();
                }
            }
            i--;
            temp = temp.getPrevious();
        }
        if (temp == null) {
            throw new NoSuchElementException("Data is not found");
        }
        return temp.getData();
    }

    /**
     * Returns an array representation of the linked list.
     * <p>
     * Must be O(n) for all cases.
     *
     * @return an array of length size holding all of the objects in the
     * list in the same order
     */
    public Object[] toArray() {
        Object[] array = new Object[size];
        DoublyLinkedListNode<T> temp = head;
        for (int i = 0; i < size; i++) {
            array[i] = temp.getData();
            temp = temp.getNext();
        }
        return array;
    }

    /**
     * Returns the head node of the list.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the head of the list
     */
    public DoublyLinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the tail node of the list.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the tail of the list
     */
    public DoublyLinkedListNode<T> getTail() {
        // DO NOT MODIFY!
        return tail;
    }

    /**
     * Returns the size of the list.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY!
        return size;
    }
}