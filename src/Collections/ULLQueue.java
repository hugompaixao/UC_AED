package Collections;
//package aed.collections;

import java.util.Iterator;

public class ULLQueue <T> implements IQueue <T> {
    class Node{
        T item;
        Node next;

        Node(T item, Node next) {
            this.item = item;
            this.next = next;

        }
    }

    Node first, last;
    int size;


    public ULLQueue() {
        this.first = null;
        this.last = null;
        this.size = 0;
    }
    @Override
    public void enqueue(T item) {
        Node node = new Node(item, null);
        if(this.first == null) {
            this.first = node;
        } else {
            this.last.next = node;
        }
        this.last = node;
        this.size++;
    }

    @Override
    public T dequeue() {
        if(isEmpty())
            return null;
        Node tmp = this.first;
        this.first = this.first.next;
        this.size--;
        return tmp.item;
    }

    @Override
    public T peek() {
        if(isEmpty())
            return null;
        return this.first.item;
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
    public IQueue<T> shallowCopy() {
        ULLQueue<T> copy = new ULLQueue<T>();
        Node current = first;
        copy.first = new Node(null, null);
        Node copied = copy.first;
        copy.first.item = current.item;
        while(current.next != null) {
            copied.next = new Node(null, null);
            copied.next.item = current.next.item;
            current = current.next;
            copied = copied.next;
        }
        copy.size = this.size;
        return copy;
    }

    @Override
    public Iterator<T> iterator() {
        return new ULLQueueIterator();
    }

    private class ULLQueueIterator implements Iterator <T>{
        Node it;

        ULLQueueIterator() {
            this.it = first;
        }

        @Override
        public boolean hasNext() {
            return this.it != null;
        }

        @Override
        public T next() {
            T result = (T) this.it.item;
            this.it = this.it.next;
            return result;
        }
    }

    public static void main(String[] args) {}
}