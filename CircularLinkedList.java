import java.util.NoSuchElementException;
import java.util.Iterator;
import edu.princeton.cs.algs4.StdOut;

public class CircularList<T> implements ILinkedList<T>, Iterable<T>{
    private Node<T> front;
    private int len;


    private Node<T> getNodeAtPosition(int pos) throws NoSuchElementException {
        Node<T> n = front;
        int count = 0;
        if (pos >= len()) {
            throw new NoSuchElementException("requested position greater then length of list");
        }
        else {
            count++;
            n = n.getNext();
            while (n.getData() != front.getData() && count < pos) {
                n = n.getNext();
                count++;
            }
        }
        return n;
    }

    public void insertFront(T item) {
        Node<T> n = new Node<T>(item);
        if (len() != 0) {
            Node<T> j = front.getPrev();
            n.setNext(front);
            front.setPrev(n);
            n.setPrev(j);
            j.setNext(n);
            front = n;
            len++;
        }
        else {
            front = n;
            n.setNext(front);
            n.setPrev(front);
            len = 1;
        }
    }

    public void insertBack(T item) {
        Node<T> n = new Node<T>(item);
        if (len() != 0) {
            Node<T> j = front.getPrev();
            j.setNext(n);
            n.setPrev(j);
            n.setNext(front);
            front.setPrev(n);
            len++;
        }
        else {
            front = n;
            n.setNext(front);
            n.setPrev(front);
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
        Node<T> n = front;
        return n.getPrev().getData();
    }

    public T getAt(int position) throws NoSuchElementException {
        Node<T> n = null;
        if(isEmpty()){ throw new NoSuchElementException("List is empty");}
        if(position >= len()){
            throw new NoSuchElementException("position " + Integer.toString(position) + " does not exist in the list");
        }
        else if (position == 0) {
            n = front;
        }
        else {
            n = getNodeAtPosition(position);
        }
        return n.getData();
    }

    public void removeFront() throws NoSuchElementException {
        if(isEmpty()){ throw new NoSuchElementException("List is empty");}
        else {
            Node<T> n = front.getPrev();
            Node<T> j = front.getNext();
            n.setNext(j);
            j.setPrev(n);
            front = j;
            len--;
        }
    }

    public void removeBack() throws NoSuchElementException {
        if(isEmpty()){ throw new NoSuchElementException("List is empty");}
        else {
            Node<T> n = front;
            Node<T> j = front.getPrev().getPrev();
            j.setNext(n);
            n.setPrev(j);
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
        if(isEmpty()){
            StdOut.println("back");
            return;
        }
        else {
            Node<T> n = front;
            StdOut.print(n.getData() + " -> ");
            n = n.getNext();
            while (n.getData() != front.getData()) {
                StdOut.printf(n.getData() + " -> ");
                n = n.getNext();
            }
            StdOut.print("back");
        }
        StdOut.print("\n");
    }

    public void printReverse() {
        StdOut.print("back -> ");
        if(isEmpty()){
            StdOut.println("front");
            return;
        }
        else {
            Node<T> n = front.getPrev();
            StdOut.print(n.getData() + " -> ");
            n = n.getPrev();
            while (n.getData() != front.getPrev().getData()) {
                StdOut.print(n.getData() + " -> ");
                n = n.getPrev();
            }
            StdOut.print("front");
        }
        StdOut.print("\n");
    }

    public void printDataAt(int position) {
        if(position >= len()){
            throw new NoSuchElementException("position is greater then the length of the list");
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

        public boolean hasNext()  {
            return current.getNext() != front;
        }

        public T next() {
            if (!hasNext()) throw new NoSuchElementException();
            T item = current.getData();
            current = current.getNext();
            return item;
        }
    }

    public boolean isCircularForward(){
        Node<T> n = front;
        if(isEmpty()){
            return true;
        }
        do {
            n = n.getNext();
            if(n == null){
                return false;
            }
        } while(n != front);
        return true;
    }

    public boolean isCircularBackward(){
        if(isEmpty()){
            return true;
        }
        Node<T> n = front.prev;
        do {
            n = n.getPrev();
            if(n == null){
                return false;
            }
        } while(n != front.prev);
        return true;
    }
