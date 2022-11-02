import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdOut;

public class DoublyLinkedList<T> implements ILinkedList<T>, Iterable<T>{
    private Node<T> front;
    private Node<T> back;
    private int len;


    private Node<T> getNodeAtPosition(int pos) throws NoSuchElementException {
        Node<T> n = front;
        int count = 0;
        if (pos >= len()) {
            throw new NoSuchElementException("requested position greater then length of list");
        }
        else {
            while (n != null && count < pos) {
                n = n.getNext();
                count++;
            }
        }
        return n;
    }

    public void insertFront(T item) {
        Node<T> n = new Node<T>(item);
        if (len() != 0) {
            front.setPrev(n);
            n.setNext(front);
            front = n;
            len++;
        }
        else {
            front = n;
            back = n;
            len = 1;
        }
    }

    public void insertBack(T item) {
        Node<T> n = new Node<T>(item);
        if (len() != 0) {
            back.setNext(n);
            n.setPrev(back);
            back = n;
            len++;
        }
        else {
            front = n;
            back = n;
            len = 1;
        }
    }

    public void insertAt(T item, int position) throws NoSuchElementException {
        Node<T> n = new Node<T>(item);
        if(position >= len()){
            insertBack(item);
        }
        else if (position == 0) {
            insertFront(item);
        }
        else {
            Node<T> j = getNodeAtPosition(position);
            n.setPrev(j.getPrev());
            n.setNext(j);
            j.setPrev(n);
            n.getPrev().setNext(n);
            len++;
        }
    }

    public T getFront() throws NoSuchElementException {
        if(isEmpty()){ throw new NoSuchElementException("List is empty");}
        Node<T> n = front;
        return n.getData();
    }

    public T getBack() throws NoSuchElementException {
        if(isEmpty()){ throw new NoSuchElementException("List is empty");}
        Node<T> n = back;
        return n.getData();
    }

    public T getAt(int position) throws NoSuchElementException {
        Node<T> n = null;
        if(isEmpty()){ throw new NoSuchElementException("List is empty");}
        if(position >= len()){
            throw new NoSuchElementException("position " + Integer.toString(position) + " does not exist in the list");
        }
        else {
            n = getNodeAtPosition(position);
        }
        return n.getData();
    }

    public void removeFront() throws NoSuchElementException {
        if(isEmpty()){ throw new NoSuchElementException("List is empty");}
        else {
            Node<T> n = front;
            front = n.getNext();
            len--;
        }
    }

    public void removeBack() throws NoSuchElementException {
        if(isEmpty()){ throw new NoSuchElementException("List is empty");}
        else {
            Node<T> n = back;
            if (n.getPrev() != null) {
                n.getPrev().setNext(null);
            }
            else {
                front = null;
                back = null;
            }
            back = n.getPrev();
            len--;
        }
    }

    public void removeAt(int position) throws NoSuchElementException {
        if(isEmpty()){ throw new NoSuchElementException("List is empty");}
        else if(position >= len()){ throw new NoSuchElementException("Position greater then list length");}
        else if (position == 0) { removeFront(); }
        else if (position == (len() - 1)) { removeBack(); }
        else {
            Node<T> n = getNodeAtPosition(position);
            n.getPrev().setNext(n.getNext());
            n.getNext().setPrev(n.getPrev());
            len--;
        }
    }

    public int len() {
        return len;
    }

    public boolean isEmpty() {
        return len() == 0;
    }

    public void print() {
        StdOut.print("front -> ");
        Node<T> n = front;
        while( n != null){
            StdOut.printf(n.getData() + " -> ");
            n = n.getNext();
        }
        StdOut.printf("back\n");
    }

    public void printReverse() {
        StdOut.print("back -> ");
        Node<T> n = back;
        while( n != null){
            StdOut.printf(n.getData() + " -> ");
            n = n.getPrev();
        }
        StdOut.printf("front\n");
    }

    public void printDataAt(int position) {
        if(position >= len()){
            throw new NoSuchElementException();
        }
        StdOut.printf(getNodeAtPosition(position).getData() + "\n");
    }

    public Iterator<T> iterator() {
        return new LinkedIterator(front);
    }

    private class LinkedIterator implements Iterator<T> {
        private Node<T> current;

        public LinkedIterator(Node<T> first) {
            current = first;
        }

        public boolean hasNext()  { return current != null; }

        public T next() {
            if (!hasNext()) throw new NoSuchElementException();
            T item = current.getData();
            current = current.getNext();
            return item;
        }
    }
