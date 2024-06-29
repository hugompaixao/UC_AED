package Treap;//aed.tables;

import java.util.*;
import java.util.Queue;

// src = https://users.cs.fiu.edu/~weiss/dsaajava2/code/Treap.java
// src = https://www.techiedelight.com/implementation-treap-data-structure-cpp-java-insert-search-delete/
// src = https://algs4.cs.princeton.edu/code/edu/princeton/cs/algs4/BST.java.html

public class Treap<Key extends Comparable<Key>,Value> {

    //if you want to use a different organization than a set of nodes with pointers, you can do it, but you will have to change
    //the implementation of the getHeapArray method, and eventually the printing method (so that is prints a "node" in the same way)
    private class Node {
        private Key key;
        private int priority, size;
        private Value value;
        private Node left, right;

        //you can add additional properties and methods, as long as you maintain this constructor and the toString method with the same semantic
        public Node(Key key, Value value, int size, int priority) {
            this.key = key;
            this.value = value;
            this.size = size;
            this.priority = priority;
            this.left = this.right = null;
        }

        public String toString() {
            return "[k:" + this.key + " v:" + this.value + " p:" + this.priority + " s:" + this.size + "]";
        }

        //if needed, you can add additional methods to the private class Node
    }

    private Node root;
    private static Random random;

    public Treap() {
        root = null;
        random = new Random();
    }

    public Treap(Random r) {
        root = null;
        random = r;
    }

    private int size(Node node) {
        return (node == null) ? 0 : node.size;
    }

    public int size() {
        return size(root);
    }

    private Node get(Node node, Key key) {
        if(node == null) return null;

        int cmp = key.compareTo(node.key);
        if(cmp == 0) return node;
        else if(cmp < 0) return get(node.left, key);
        else return get(node.right, key);
    }

    public Value get(Key key) {
        if(key == null) return null;
        Node result = get(root, key);
        return (result == null) ? null : result.value;
    }

    public boolean containsKey(Key key) {
        return get(root, key) != null;
    }

    private int sizeRotation(Node node) {
        if(node.left == null && node.right == null)
            return 1;
        else if(node.left != null && node.right != null)
            return size(node.left) + size(node.right) + 1;
        else {
            Node tmp = (node.left != null) ? node.left : node.right;
            return size(tmp) + 1;
        }
    }

    private Node leftRotation(Node node) {
        Node newRoot = node.right;
        Node disownedChild = node.right.left;
        newRoot.left = node;
        node.right = disownedChild;
        node.size = sizeRotation(node);
        return newRoot;
    }

    private Node rightRotation(Node node) {
        Node newRoot = node.left;
        Node disownedChild = node.left.right;
        newRoot.right = node;
        node.left = disownedChild;
        node.size = sizeRotation(node);
        return newRoot;
    }

    private Node put(Node node, Key key, Value value) {
        if(node == null) return new Node(key, value, 1, random.nextInt());

        int cmp = key.compareTo(node.key);
        if(cmp < 0) {
            node.left = put(node.left, key, value);
            if(node.left != null && node.left.priority > node.priority)
                node = rightRotation(node);
        } else if(cmp > 0) {
            node.right = put(node.right, key, value);
            if(node.right != null && node.right.priority > node.priority)
                node = leftRotation(node);
        } else
            node.value = value;

        node.size = size(node.left) + size(node.right) + 1;
        return node;
    }

    public void put(Key key, Value value) {
        if(key == null) return;
        else if(value == null) delete(key);
        else
            root = put(root, key, value);
    }

    private Node delete(Node node, Key key) {
        if(node == null) return null;

        int cmp = key.compareTo(node.key);
        if(cmp < 0)
            node.left = delete(node.left, key);
        else if(cmp > 0)
            node.right = delete(node.right, key);
        else {
            if (node.left == null && node.right == null) return null;
            else if(node.left != null && node.right != null) {
                node = (node.left.priority > node.right.priority) ? rightRotation(node) : leftRotation(node);
                delete(node, key);
            } else {
                node = (node.left == null) ? leftRotation(node) : rightRotation(node);
                delete(node, key);
            }
        }

        node.size = size(node.left) + size(node.right) +1;
        return node;
    }

    public void delete(Key key) {
        if(key == null) return;
        else
            root = delete(root, key);
    }

    /** ----------------------------------------- DONE TILL HERE ---------------------------------------------------- */

    private Node breakTreap(Node node, Key key) {
        int cmp = key.compareTo(node.key);
        if(cmp < 0) {
            if(node.left.key.compareTo(key) == 0){
                node.left = null;
                return node;
            }
            node.left = breakTreap(node.left, key);
        } else if(cmp > 0){
            if(node.right.key.compareTo(key) == 0){
                node.right = null;
                return node;
            }
            node.right =  breakTreap(node.right, key);
        }

        return null;
    }

    @SuppressWarnings("rawtypes")
    public Treap[] split(Key key) {
        /*Treap[] split = new Treap[2];
        split[0].root = breakTreap(root, key);
        split[1].root = get(root, key);
        return split;*/
        return null;
    }

    /** ----------------------------------------- DONE FROM HERE ---------------------------------------------------- */

    private Node min(Node node) {
        return (node.left == null) ? node : min(node.left);
    }

    public Key min() {
        return root == null ? null : min(root).key;
    }

    private Node max(Node node) {
        return (node.right == null) ? node : max(node.right);
    }

    public Key max() {
        return root == null ? null : max(root).key;
    }

    private Node deleteMin(Node node) {
        if (node.left == null) return node.right;
        node.left = deleteMin(node.left);
        node.size = size(node.left) + size(node.right) + 1;
        return node;
    }

    public void deleteMin() {
        if(root == null) return;
        root = deleteMin(root);
    }

    private Node deleteMax(Node node) {
        if (node.right == null) return node.left;
        node.right = deleteMax(node.right);
        node.size = size(node.left) + size(node.right) + 1;
        return node;
    }

    public void deleteMax() {
        if(root == null) return;
        root = deleteMax(root);
    }

    private int rank(Node node, Key key) {
        if(node == null) return 0;

        int cmp = key.compareTo(node.key);
        if(cmp == 0) return size(node.left);
        else if(cmp < 0) return rank(node.left, key);
        else return 1 + size(node.left) + rank(node.right, key);
    }

    public int rank(Key key) {
        return rank(root, key);
    }

    public int size(Key min, Key max) {
        return root == null ? 0 : (containsKey(max)) ? rank(max) - rank(min) + 1 : rank(max) - rank(min);
    }

    private Key select(Node node, int n) {
        if(node == null) return null;
        if(size(node.left) > n)
            return select(node.left, n);
        else if(size(node.left) < n)
            return select(node.right, n - size(node.left) - 1);
        else
            return node.key;
    }

    public Key select(int n) {
        return select(root, n);
    }

    /** ----------------------------------------- TILL HERE --------------------------------------------------------- */

    public Iterable<Key> keys() {
        Queue<Key> key = null;
        while(root != null) {
            Key min = min();
            key.add(min);
            delete(min);
        }
        return key;
    }

    public Iterable<Value> values() {
        Queue<Value> value = null;
        Queue<Key> keys = (Queue<Key>) keys();
        for(Key var : keys)
            value.add(get(root, var).value);

        return value;
    }

    public Iterable<Integer> priorities() {
        Queue<Integer> priorities = null;
        Queue<Key> keys = (Queue<Key>) keys();
        for(Key var : keys)
            priorities.add(get(root, var).priority);

        return priorities;
    }

    public Iterable<Key> keys(Key min, Key max) {
        Queue<Key> key = null;
        Queue<Key> keys = (Queue<Key>) keys();
        for(Key var : keys) {
            if(var.compareTo(min) >= 0 && var.compareTo(max) <= 0)
                key.add(get(root, var).key);
        }
        return key;
    }

    public Treap<Key,Value> shallowCopy() {
        //TODO: implement
        return null;
    }

    //helper method that uses the treap to build an array with a heap structure
    private void fillHeapArray(Node n, Object[] a, int position) {
        if(n == null) return;

        if(position < a.length) {
            a[position] = n;
            fillHeapArray(n.left,a,2*position);
            fillHeapArray(n.right,a,2*position+1);
        }
    }

    //if you want to use a different organization that a set of nodes with pointers, you can do it, but you will have to change
    //this method to be consistent with your implementation
    private Object[] getHeapArray() {
        //we only store the first 31 elements (position 0 is not used, so we need a size of 32), to print the first 5 rows of the treap
        Object[] a = new Object[32];
        fillHeapArray(this.root,a,1);
        return a;
    }

    private void printCentered(String line) {
        //assuming 120 characters width for a line
        int padding = (120 - line.length())/2;
        if(padding < 0) padding = 0;
        String paddingString = "";
        for(int i = 0; i < padding; i++) {
            paddingString +=" ";
        }

        System.out.println(paddingString + line);
    }

    //this is used by some automatic tests in Mooshak. It is used to print the first 5 levels of a Treap.
    //feel free to use it for your own tests
    public void printTreapBeginning() {
        Object[] heap = getHeapArray();
        int size = size(this.root);
        int printedNodes = 0;
        String nodeToPrint;
        int i = 1;
        int nodes;
        String line;

        //only prints the first five levels
        for (int depth = 0; depth < 5; depth++) {
            //number of nodes to print at a particular depth
            nodes = (int) Math.pow(2, depth);
            line = "";
            for (int j = 0; j < nodes && i < heap.length; j++) {
                if (heap[i] != null) {
                    nodeToPrint = heap[i].toString();
                    printedNodes++;
                } else {
                    nodeToPrint = "[null]";
                }
                line += " " + nodeToPrint;
                i++;
            }

            printCentered(line);
            if (i >= heap.length || printedNodes >= size) break;
        }
    }

    public static void main(String[] args) {
        Treap<Integer, Integer> a = new Treap<Integer, Integer>();
        for(int i = 0; i < 100; i++)
            a.put(i, random.nextInt(100));

        System.out.println(a.size()); // 100
        a.put(4199, 123);
        System.out.println(a.size()); // 101
        System.out.println(a.containsKey(99)); // true
        System.out.println(a.containsKey(456776)); //false
        System.out.println(a.rank(39)); //39
        System.out.println(a.min() + "  " + a.max()); // 0 4199
        a.deleteMin();
        a.deleteMax();
        System.out.println(a.min() + "  " + a.max()); // 1 99
        System.out.println(a.size()); // 99
        a.delete(7);
        a.delete(22);
        a.delete(44);
        System.out.println(a.size()); // 96
        System.out.println(a.rank(39)); //37
    }
}