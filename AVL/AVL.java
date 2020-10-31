import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

/**
 *
 * @author Thomas Crawford
 * @version 1.0
 *
public class AVL<T extends Comparable<? super T>> {


     * Constructs a new AVL.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize the AVL with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * @param data the data to add to the tree
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Collection is null");
        }
        for (T s : data) {
            if (s == null) {
                throw new IllegalArgumentException("Data is null");
            }
            add(s);
        }
    }

    /**
     * Helper Methods for rotations and Height update
     */

    /**
     *
     * @param c Current node
     * @return Updated Node
     */
    private AVLNode<T> rightRotation(AVLNode<T> c) {
        AVLNode<T> b = c.getLeft();
        c.setLeft(b.getRight());
        b.setRight(c);
        c.setHeight(heightHelper(c));
        b.setHeight(heightHelper(b));
        c.setBalanceFactor(balanceFactor(c));
        b.setBalanceFactor(balanceFactor(b));
        return b;
    }

    /**
     *
     * @param c Current node
     * @return Updated node
     */
    private AVLNode<T> leftRotation(AVLNode<T> c) {
        AVLNode<T> b = c.getRight();
        c.setRight(b.getLeft());
        b.setLeft(c);
        c.setHeight(heightHelper(c));
        b.setHeight(heightHelper(b));
        c.setBalanceFactor(balanceFactor(c));
        b.setBalanceFactor(balanceFactor(b));
        return b;
    }

    /**
     * Adds the element to the tree.
     *
     * Start by adding it as a leaf like in a regular BST and then rotate the
     * tree as necessary.
     *
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after adding the element, making sure to rebalance if
     * necessary.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        }
        root = helperAdd(root, data);
    }

    /**
     *
     * @param curr Current node
     * @param data Data to add
     * @return Current node
     */
    private AVLNode<T> helperAdd(AVLNode<T> curr, T data) {
        if (curr == null) {
            AVLNode<T> node = new AVLNode<>(data);
            size++;
            return node;
        }
        if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(helperAdd(curr.getLeft(), data));
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(helperAdd(curr.getRight(), data));
        } else {
            return curr;
        }
        curr.setHeight(heightHelper(curr));
        curr.setBalanceFactor(balanceFactor(curr));
        if (curr.getBalanceFactor() > 1 && data.compareTo(
                curr.getLeft().getData()) < 0) {
            return rightRotation(curr);
        }
        if (curr.getBalanceFactor() < -1 && data.compareTo(
                curr.getRight().getData()) > 0) {
            return leftRotation(curr);
        }
        if (curr.getBalanceFactor() > 1 && data.compareTo(
                curr.getLeft().getData()) > 0) {
            curr.setLeft(leftRotation(curr.getLeft()));
            return rightRotation(curr);
        }
        if (curr.getBalanceFactor() < -1 && data.compareTo(
                curr.getRight().getData()) < 0) {
            curr.setRight(rightRotation(curr.getRight()));
            return leftRotation(curr);
        }
        return curr;
    }


    /**
     * Removes and returns the element from the tree matching the given
     * parameter.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException   if the data is not found
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        }
        AVLNode<T> dummy = new AVLNode<>(null);
        root = helperRemove(root, data, dummy);
        return dummy.getData();
    }
    /**
     * Helper method for remove method
     *
     * @param curr  Current node
     * @param data  Data to search for
     * @param dummy Data to remove
     * @return curr
     */
    private AVLNode<T> helperRemove(AVLNode<T> curr, T data, AVLNode<T> dummy) {
        if (curr == null) {
            throw new NoSuchElementException("Data is not in the tree");
        }
        if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(helperRemove(curr.getLeft(), data, dummy));
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(helperRemove(curr.getRight(), data, dummy));
        } else {
            dummy.setData(curr.getData());
            size--;
            if (curr.getLeft() == null) {
                return curr.getRight();
            } else if (curr.getRight() == null) {
                return curr.getLeft();
            } else {
                AVLNode<T> dummy2 = new AVLNode<>(null);
                curr.setRight(successor(curr.getRight(), dummy2));
                curr.setData(dummy2.getData());
            }
        }
        if (curr == null) {
            return curr;
        }
        curr.setHeight(heightHelper(curr));
        curr.setBalanceFactor(balanceFactor(curr));
        if (curr.getBalanceFactor() > 1
                && curr.getLeft().getBalanceFactor() >= 0) {
            return rightRotation(curr);
        }
        if (curr.getBalanceFactor() < -1
                && curr.getRight().getBalanceFactor() <= 0) {
            return leftRotation(curr);
        }
        if (curr.getBalanceFactor() > 1
                && curr.getLeft().getBalanceFactor() < 0) {
            curr.setLeft(leftRotation(curr.getLeft()));
            return rightRotation(curr);
        }
        if (curr.getBalanceFactor() < -1
                && curr.getRight().getBalanceFactor() > 0) {
            curr.setRight(rightRotation(curr.getRight()));
            return leftRotation(curr);
        }
        return curr;
    }
    /**
     * Helper method for removing successor
     *
     * @param curr  Current node
     * @param dummy Dummy Node
     * @return Successor children (if any) or curr
     */
    private AVLNode<T> successor(AVLNode<T> curr, AVLNode<T> dummy) {
        if (curr.getLeft() == null) {
            dummy.setData(curr.getData());
            return curr.getRight();
        } else {
            curr.setLeft(successor(curr.getLeft(), dummy));
        }
        curr.setHeight(heightHelper(curr));
        curr.setBalanceFactor(balanceFactor(curr));
        if (curr.getBalanceFactor() > 1
                && curr.getLeft().getBalanceFactor() >= 0) {
            return rightRotation(curr);
        }
        if (curr.getBalanceFactor() < -1
                && curr.getRight().getBalanceFactor() <= 0) {
            return leftRotation(curr);
        }
        if (curr.getBalanceFactor() > 1
                && curr.getLeft().getBalanceFactor() < 0) {
            curr.setLeft(leftRotation(curr.getLeft()));
            return rightRotation(curr);
        }
        if (curr.getBalanceFactor() < -1
                && curr.getRight().getBalanceFactor() > 0) {
            curr.setRight(rightRotation(curr.getRight()));
            return leftRotation(curr);
        }
        return curr;
    }

    /**
     * Returns the element from the tree matching the given parameter.
     *
     *
     * @param data the data to search for in the tree
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        try {
            if (data == null) {
                throw new IllegalArgumentException("Data is null");
            }
            return getHelper(root, data);
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("Data is not in the tree");
        }
    }
    /**
     * Helper method for get()
     *
     * @param curr Current node
     * @param data Data to be found
     * @return Current data
     */
    private T getHelper(AVLNode<T> curr, T data) {
        if (curr == null) {
            throw new NoSuchElementException();
        }
        if (data.compareTo(curr.getData()) < 0) {
            return getHelper(curr.getLeft(), data);
        } else if (data.compareTo(curr.getData()) > 0) {
            return getHelper(curr.getRight(), data);
        } else {
            return curr.getData();
        }
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * @param data the data to search for in the tree.
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        }
        return containsHelper(root, data);
    }

    /**
     * @param curr Current Node
     * @param data Data to be found
     * @return Current data
     */
    private boolean containsHelper(AVLNode<T> curr, T data) {
        if (curr == null) {
            return false;
        }
        if (data.compareTo(curr.getData()) < 0) {
            return containsHelper(curr.getLeft(), data);
        } else if (data.compareTo(curr.getData()) > 0) {
            return containsHelper(curr.getRight(), data);
        } else {
            return true;
        }
    }

    /**
     * Returns the height of the root of the tree.
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return heightHelper(root); }

    /**
     * @param curr Relative root
     * @return Height
     */
    private int heightHelper(AVLNode<T> curr) {
        if (curr == null) {
            return -1;
        } else {
            int leftHeight = heightHelper(curr.getLeft());
            int rightHeight = heightHelper(curr.getRight());
            if (leftHeight > rightHeight) {
                return leftHeight + 1;
            } else {
                return rightHeight + 1;
            }
        }
    }

    /**
     *
     * @param curr Current Node
     * @return Balance Factor
     */
    private int balanceFactor(AVLNode<T> curr) {
        if (curr == null) {
            return 0;
        }
        int left;
        int right;
        if (curr.getLeft() == null) {
            left = -1;
        } else {
            left = curr.getLeft().getHeight();
        }
        if (curr.getRight() == null) {
            right = -1;
        } else {
            right = curr.getRight().getHeight();
        }
        int factor = left - right;
        return factor;
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * This method retrieves (but not remove) the predecessor of the data
     * passed in.
     *
     * @param data the data to find the predecessor of
     * @return the predecessor of data. If there is no smaller data than the
     * one given, return null.
     * @throws java.lang.IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T predecessor(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        }
        AVLNode<T> pred = new AVLNode<>(null);
        pred = predHelper(root, pred, data);
        return pred.getData();
    }

    /**
     *
     * @param curr Current node
     * @param pred Predecessor when recursing right
     * @param data Data to find predecessor of
     * @return Predecessor
     */
    private AVLNode<T> predHelper(AVLNode<T> curr, AVLNode<T> pred, T data) {
        if (curr == null) {
            throw new NoSuchElementException("Data is not in the tree");
        }
        if (data.compareTo(curr.getData()) < 0) {
            return predHelper(curr.getLeft(), pred, data);
        } else if (data.compareTo(curr.getData()) > 0) {
            pred = curr;
            return predHelper(curr.getRight(), pred, data);
        } else {
            if (curr.getLeft() != null) {
                AVLNode<T> temp = curr.getLeft();
                while (temp.getRight() != null) {
                    temp = temp.getRight();
                }
                return temp;
            }
        }
        return pred;
    }

    /**
     * Finds and retrieves the k-smallest elements from the AVL in sorted order,
     * least to greatest.
     *
     * @param k the number of smallest elements to return
     * @return sorted list consisting of the k smallest elements
     * @throws java.lang.IllegalArgumentException if k < 0 or k > n, the number
     *                                            of data in the AVL
     */
    public List<T> kSmallest(int k) {
        if (k < 0 || k > size) {
            throw new IllegalArgumentException("k is less than 0 "
                    +  "or greater than number of data");
        }
        List<T> kSmallest = new ArrayList<>();
        kSmallesthelper(root, kSmallest, k);
        return kSmallest;
    }

    /***
     *
     * @param curr Current node
     * @param list kSmallest list
     * @param k number of smallest elements
     * @return AVL node
     */
    private AVLNode<T> kSmallesthelper(AVLNode<T> curr, List<T> list, int k) {
        if (curr == null) {
            return curr;
        }
        AVLNode<T> left = kSmallesthelper(curr.getLeft(), list, k);
        if (list.size() == k) {
            return curr;
        }
        if (left != null) {
            list.add(left.getData());
            return left;
        } else {
            list.add(curr.getData());
            return kSmallesthelper(curr.getRight(), list, k);
        }
    }

    /**
     * Returns the root of the tree.
     *
     * @return the root of the tree
     */
    public AVLNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
