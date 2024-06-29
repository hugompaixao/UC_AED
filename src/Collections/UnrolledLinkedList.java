//package aed.collections;
package Collections;

import java.util.Iterator;


@SuppressWarnings("unchecked")
class Node <T> {
    T[] items;
    int index;
    Node next;

    public Node(int blockSize, Node node) {
        this.items = (T[]) new Object[blockSize];
        this.index = 0;
        this.next = node;
    }

}


@SuppressWarnings("unchecked")
public class UnrolledLinkedList <T> implements IList <T> {
    int size, blockSize;
    Node first;

    public UnrolledLinkedList() {
        this.first = new Node(4, null);
        this.blockSize = 4;
        this.size = 1;
    }

    public UnrolledLinkedList(int blockSize) {
        this.first = new Node(blockSize, null);
        this.blockSize = blockSize;
        this.size = 1;
    }

    public void addNode() {
        int keep = this.blockSize / 2;
        Node old = this.first;
        this.first = new Node(this.blockSize, old);
        this.size++;
        for (int i = keep; i < this.blockSize; i++) {
            this.first.items[this.first.index++] = old.items[i];
            old.items[i] = null;
        }
        old.index = keep;
    }

    public Node findNode(int index) {
        Node tmp = this.first;
        int var = 0;
        while(var != size - index / this.size) {
            tmp = tmp.next;
            var++;
        }
        return tmp;
    }

    @Override
    public void add(T item) {
        if (this.first.index == this.blockSize)
            addNode();
        this.first.items[this.first.index++] = item;
    }

    @Override
    public void addAt(int index, T item) {
        if(index / this.size >= this.size) {
            this.first.items[index % this.blockSize] = item;
        } else {
            Node tmp = findNode(index);
            tmp.items[index % this.blockSize] = item;
        }
    }

    @Override
    public T remove() {
        if (isEmpty())
            return null;
        T item = (T) this.first.items[--this.first.index];
        if (this.first.index == 0) {
            this.first = this.first.next;
            this.size--;
        }
        return item;
    }

    @Override
    public T remove(int index) {
        T item;
        if(index / this.size >= this.size) {
            item = (T) this.first.items[index % this.blockSize];
            this.first.items[index % this.blockSize] = null;
        } else {
            Node tmp = findNode(index);
            item = (T) tmp.items[index % this.blockSize];
            tmp.items[index % this.blockSize] = null;
        }
        return item;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public T get(int index) {
        T item;
        if(index / this.size >= this.size) {
            item = (T) this.first.items[index % this.blockSize];
        } else {
            Node tmp = findNode(index);
            item = (T) tmp.items[index % this.blockSize];
        }
        return item;
    }

    @Override
    public void set(int index, T element) {
        if(index / this.blockSize == size) {
            this.first.items[index % this.blockSize] = element;
        } else if(index / this.blockSize < size){
            Node tmp = findNode(index);
            tmp.items[index % this.blockSize] = element;
        }
    }

    @Override
    public IList<T> shallowCopy() {
        UnrolledLinkedList<T> copy = new UnrolledLinkedList<T>();
        Node current = first;
        copy.first = new Node(blockSize, null);
        Node copied = copy.first;
        copy.first.items = current.items;
        while (current.next != null) {
            copied.next = new Node(blockSize, null);
            copied.next.items = current.next.items;
            current = current.next;
            copied = copied.next;
        }
        copy.size = this.size;
        return copy;
    }

    @Override
    public Iterator<T> iterator() {
        return new UnrolledLinkedListIterator();
    }

    private class UnrolledLinkedListIterator implements Iterator<T> {
        Node it;

        UnrolledLinkedListIterator() {
            it = first;
        }

        @Override
        public boolean hasNext() {
            return it != null;
        }

        @Override
        public T next() {
            T result = (T) it.items[--it.index];
            if (it.index == 1)
                it = it.next;
            return result;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("It doesn't support removal");
        }
    }

    public T[][] getArrayOfBlocks() {
        T[][] all = (T[][]) new Object[this.size][this.blockSize];
        for(int i = 0; i < this.size; i++) {
            for(int j = 0 ; j < this.blockSize; j++)
                all[i][j] = (T) this.first.items[j];
            this.first = this.first.next;
        }
        return all;
    }
}