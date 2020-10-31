package homework04;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.List;
import java.util.Queue;

/**
 * Your implementation of a BST.
 *
 * @author Thomas Crawford
 * @version 1.0
 * @userid YOUR USER ID HERE (i.e. tcrawford38)
 * @GTID YOUR GT ID HERE (i.e. 903398010)
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class BST<T extends Comparable<? super T>> {

    // Do not add new instance variables or modify existing ones.
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     * <p>
     * This constructor should initialize an empty BST.
     * <p>
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     * <p>
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     * <p>
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add to the tree
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
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
     * Adds the element to the tree.
     * <p>
     * The data becomes a leaf in the tree.
     * <p>
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     * <p>
     * Must be O(log n) for a balanced tree and O(n) for worst case.
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
     * Helper Add method
     *
     * @param root the root
     * @param data the data to add
     * @return the relative root node
     */
    private BSTNode<T> helperAdd(BSTNode<T> root, T data) {
        if (root == null) {
            root = new BSTNode<>(data);
            size++;
            return root;
        }
        if (data.compareTo(root.getData()) < 0) {
            root.setLeft(helperAdd(root.getLeft(), data));
        } else if (data.compareTo(root.getData()) > 0) {
            root.setRight(helperAdd(root.getRight(), data));
        }
        return root;
    }

    /**
     * Removes and returns the element from the tree matching the given
     * parameter.
     * <p>
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its
     * child.
     * 3: The node containing the data has 2 children. Use the predecessor to
     * replace the data.
     * You MUST use recursion to find and remove the predecessor (you will
     * likely need an additional helper method to handle this case efficiently).
     * <p>
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     * <p>
     * Hint: Should you use value equality or reference equality?
     * <p>
     * Must be O(log n) for a balanced tree and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        }
        BSTNode<T> dummy = new BSTNode<>(null);
        root = helperRemove(root, data, dummy);
        return dummy.getData();
    }

    /**
     * Helper method for remove method
     *
     * @param root  relative root
     * @param data  Data to search for
     * @param dummy Data to remove
     * @return Root Node
     */
    private BSTNode<T> helperRemove(BSTNode<T> root, T data, BSTNode<T> dummy) {
        if (root == null) {
            throw new NoSuchElementException("Data is not in the tree");
        }
        if (data.compareTo(root.getData()) < 0) {
            root.setLeft(helperRemove(root.getLeft(), data, dummy));
        } else if (data.compareTo(root.getData()) > 0) {
            root.setRight(helperRemove(root.getRight(), data, dummy));
        } else {
            dummy.setData(root.getData());
            size--;
            if (root.getLeft() == null) {
                return root.getRight();
            } else if (root.getRight() == null) {
                return root.getLeft();
            } else {
                BSTNode<T> dummy2 = new BSTNode<>(null);
                root.setLeft(predecessor(root.getLeft(), dummy2));
                root.setData(dummy2.getData());
            }
        }
        return root;
    }

    /**
     * Helper method for removing successor
     *
     * @param root  Relative root
     * @param dummy Dummy Node
     * @return Successor children (if any) or relative root
     */
    private BSTNode<T> predecessor(BSTNode<T> root, BSTNode<T> dummy) {
        if (root.getRight() == null) {
            dummy.setData(root.getData());
            return root.getLeft();
        } else {
            root.setRight(predecessor(root.getRight(), dummy));
        }
        return root;
    }


    /**
     * Returns the element from the tree matching the given parameter.
     * <p>
     * Hint: Should you use value equality or reference equality?
     * <p>
     * Must be O(log n) for a balanced tree and O(n) for worst case.
     * <p>
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
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
     * @param root Relative root
     * @param data Data to be found
     * @return Root data
     */
    private T getHelper(BSTNode<T> root, T data) {
        if (root == null) {
            throw new NoSuchElementException();
        }
        if (data.compareTo(root.getData()) < 0) {
            return getHelper(root.getLeft(), data);
        } else if (data.compareTo(root.getData()) > 0) {
            return getHelper(root.getRight(), data);
        } else {
            return root.getData();
        }
    }


    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     * <p>
     * Hint: Should you use value equality or reference equality?
     * <p>
     * Must be O(log n) for a balanced tree and O(n) for worst case.
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
     * @param root Relative root
     * @param data Data to be found
     * @return root data
     */
    private boolean containsHelper(BSTNode<T> root, T data) {
        if (root == null) {
            return false;
        }
        if (data.compareTo(root.getData()) < 0) {
            return containsHelper(root.getLeft(), data);
        } else if (data.compareTo(root.getData()) > 0) {
            return containsHelper(root.getRight(), data);
        } else {
            return true;
        }
    }

    /**
     * Generate a pre-order traversal of the tree.
     * <p>
     * Must be O(n).
     *
     * @return the preorder traversal of the tree
     */
    public List<T> preorder() {
        ArrayList<T> list = new ArrayList<>();
        return preorderHelper(root, list);
    }

    /**
     * @param root Relative root
     * @param list ArrayList
     * @return Preorder list
     */
    private List<T> preorderHelper(BSTNode<T> root, ArrayList<T> list) {
        if (root == null) {
            return list;
        }
        list.add(root.getData());
        preorderHelper(root.getLeft(), list);
        preorderHelper(root.getRight(), list);
        return list;
    }

    /**
     * Generate a in-order traversal of the tree.
     * <p>
     * Must be O(n).
     *
     * @return the inorder traversal of the tree
     */
    public List<T> inorder() {
        ArrayList<T> list = new ArrayList<>();
        return inorderHelper(root, list);
    }

    /**
     * @param root Relative root
     * @param list ArrayList
     * @return Inorder List
     */
    private List<T> inorderHelper(BSTNode<T> root, ArrayList<T> list) {
        if (root == null) {
            return list;
        }
        inorderHelper(root.getLeft(), list);
        list.add(root.getData());
        inorderHelper(root.getRight(), list);
        return list;
    }

    /**
     * Generate a post-order traversal of the tree.
     * <p>
     * Must be O(n).
     *
     * @return the postorder traversal of the tree
     */
    public List<T> postorder() {
        ArrayList<T> list = new ArrayList<>();
        return postorderHelper(root, list);
    }

    /**
     * @param root Relative root
     * @param list ArrayList
     * @return Postorder list
     */
    private List<T> postorderHelper(BSTNode<T> root, ArrayList<T> list) {
        if (root == null) {
            return list;
        }
        postorderHelper(root.getLeft(), list);
        postorderHelper(root.getRight(), list);
        list.add(root.getData());
        return list;
    }

    /**
     * Generate a level-order traversal of the tree.
     * <p>
     * This does not need to be done recursively.
     * <p>
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     * <p>
     * Must be O(n).
     *
     * @return the level order traversal of the tree
     */
    public List<T> levelorder() {
        ArrayList<T> list = new ArrayList<>();
        Queue<BSTNode<T>> q = new LinkedList<>();
        if (root == null) {
            return list;
        }
        q.add(root);
        while (!(q.isEmpty())) {
            BSTNode<T> curr = q.poll();
            list.add(curr.getData());
            if (curr.getLeft() != null) {
                q.add(curr.getLeft());
            }
            if (curr.getRight() != null) {
                q.add(curr.getRight());
            }
        }
        return list;
    }

    /**
     * Returns the height of the root of the tree.
     * <p>
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child should be -1.
     * <p>
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return heightHelper(root);
    }

    /**
     * @param root Relative root
     * @return Height
     */
    private int heightHelper(BSTNode<T> root) {
        if (root == null) {
            return -1;
        } else {
            int leftHeight = heightHelper(root.getLeft());
            int rightHeight = heightHelper(root.getRight());
            if (leftHeight > rightHeight) {
                return leftHeight + 1;
            } else {
                return rightHeight + 1;
            }
        }
    }


    /**
     * Clears the tree.
     * <p>
     * Clears all data and resets the size.
     * <p>
     * Must be O(1).
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Finds the path between two elements in the tree, specifically the path
     * from data1 to data2, inclusive of both.
     * <p>
     * To do this, you must first find the deepest common ancestor of both data
     * and add it to the list. Then traverse to data1 while adding its ancestors
     * to the front of the list. Finally, traverse to data2 while adding its
     * ancestors to the back of the list. Please note that there is no
     * relationship between the data parameters in that they may not belong
     * to the same branch. You will most likely have to split off and
     * traverse the tree for each piece of data.
     * *
     * You may only use 1 list instance to complete this method. Think about
     * what type of list to use since you will have to add to the front and
     * back of the list.
     * <p>
     * This method only need to traverse to the deepest common ancestor once.
     * From that node, go to each data in one traversal each. Failure to do
     * so will result in a penalty.
     * <p>
     * If both data1 and data2 are equal and in the tree, the list should be
     * of size 1 and contain the element from the tree equal to data1 and data2.
     * <p>
     * Ex:
     * Given the following BST composed of Integers
     * 50
     * /        \
     * 25         75
     * /    \
     * 12    37
     * /  \    \
     * 10   15   40
     * /
     * 13
     * findPathBetween(13, 40) should return the list [13, 15, 12, 25, 37, 40]
     * findPathBetween(50, 37) should return the list [50, 25, 37]
     * findPathBetween(75, 75) should return the list [75]
     * <p>
     * Must be O(log n) for a balanced tree and O(n) for worst case.
     *
     * @param data1 the data to start the path from
     * @param data2 the data to end the path on
     * @return the unique path between the two elements
     * @throws java.lang.IllegalArgumentException if either data1 or data2 is
     *                                            null
     * @throws java.util.NoSuchElementException   if data1 or data2 is not in
     *                                            the tree
     */
    public List<T> findPathBetween(T data1, T data2) {
        if (data1 == null || data2 == null) {
            throw new IllegalArgumentException("Data1 or Data 2 is null");
        }
        if (get(data1).equals(false) || get(data2).equals(false)) {
            throw new NoSuchElementException("Both Data1 and Data2 "
                    + "are not in the tree");
        }
        LinkedList<T> list = new LinkedList<>();
        if (root == null) {
            return list;
        }
        BSTNode<T> commonAncestor = commonAncestor(root, data1, data2);
        if (commonAncestor == null) {
            throw new NoSuchElementException("Both Data1 and Data2 "
                   + "are not in the tree");
        }
        try {
            frontPathhelper(list, commonAncestor, data1);
            backPathhelper(list, commonAncestor, data2);
        } catch (NoSuchElementException e) {
            e.getMessage();
        }
        return list;
    }

    /**
     * @param root  Relative root
     * @param data1 First data
     * @param data2 Second data
     * @return Common Ancestor
     */
    private BSTNode<T> commonAncestor(BSTNode<T> root, T data1, T data2) {
        if (root == null) {
            return null;
        }
        if (root.getData().compareTo(data1) == 0
               || root.getData().compareTo(data2) == 0) {
            return root;
        }
        BSTNode<T> left = commonAncestor(root.getLeft(), data1, data2);
        BSTNode<T> right = commonAncestor(root.getRight(), data1, data2);
        if (left != null && right != null) {
            return root;
        } else if (left == null && right == null) {
            return null;
        } else if (left == null) {
            return right;
        } else {
            return left;
        }
    }

    /**
     * @param list     Linked List
     * @param ancestor Ancestor node
     * @param data     First data
     * @return Front path from ancestor
     */
    private List<T> frontPathhelper(
            LinkedList<T> list, BSTNode<T> ancestor, T data) {
        if (ancestor == null) {
            throw new NoSuchElementException("Data is not in the tree");
        }
        if (data.compareTo(ancestor.getData()) < 0) {
            list.addFirst(ancestor.getData());
            return frontPathhelper(list, ancestor.getLeft(), data);
        } else if (data.compareTo(ancestor.getData()) > 0) {
            list.addFirst(ancestor.getData());
            return frontPathhelper(list, ancestor.getRight(), data);
        } else {
            list.addFirst(data);
            return list;
        }
    }

    /**
     * @param list     Linked List
     * @param ancestor Ancestor node
     * @param data     Second data
     * @return Back path from ancestor
     */
    private List<T> backPathhelper(
            LinkedList<T> list, BSTNode<T> ancestor, T data) {
        if (ancestor == null) {
            throw new NoSuchElementException("Data is not in the tree");
        }
        if (data.compareTo(ancestor.getData()) < 0) {
            list.addLast(ancestor.getLeft().getData());
            return backPathhelper(list, ancestor.getLeft(), data);
        } else if (data.compareTo(ancestor.getData()) > 0) {
            list.addLast(ancestor.getRight().getData());
            return backPathhelper(list, ancestor.getRight(), data);
        } else {
            return list;
        }
    }


    /**
     * Returns the root of the tree.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS
        return size;
    }
}