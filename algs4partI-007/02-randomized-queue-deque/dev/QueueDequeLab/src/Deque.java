import java.util.Iterator;
import java.util.NoSuchElementException;


public class Deque<Item> implements Iterable<Item> {
    
    private int count = 0;
    private Node head = null;
    private Node tail = null;
    
    /**
     * Constructs an empty deque.
     */
    public Deque() {
        
    }
    
    /**
     * Allows unit testing.
     * @param args
     */
    public static void main(String[] args) {
        
        // Init test variables.
        int[] testValues = new int[]{1, 2, 3, 4};
        Deque<Integer> deque = new Deque<Integer>();
        
        // Run count/empty tests.
        assert deque.size() == 0;
        assert deque.isEmpty();
        deque.addFirst(testValues[0]);
        assert deque.size() == 1;
        assert !deque.isEmpty();
        deque.addLast(testValues[1]);
        assert deque.size() == 2;
        deque.removeFirst();
        assert deque.size() == 1;
        deque.removeLast();
        assert deque.size() == 0;
        
        // Run add/remove tests.
        deque.addLast(testValues[0]);
        deque.addLast(testValues[1]);
        deque.addLast(testValues[2]);
        deque.addFirst(testValues[3]);
        assert deque.removeFirst() == testValues[3];
        assert deque.removeFirst() == testValues[0];
        assert deque.removeLast() == testValues[2];
        assert deque.removeFirst() == testValues[1];
        
        // Run iterator tests.
        deque.addLast(testValues[0]);
        deque.addLast(testValues[1]);
        deque.addLast(testValues[2]);
        deque.addFirst(testValues[3]);
        Iterator<Integer> it = deque.iterator();
        assert it.next() == testValues[3];
        assert it.next() == testValues[0];
        assert it.next() == testValues[1];
        assert it.next() == testValues[2];
        
    }
    
    /**
     * Is the deque empty?
     * @return
     */
    public boolean isEmpty() {
        return this.count == 0;
    }
    
    /**
     * Returns the number of items on the deque.
     * @return
     */
    public int size() {
        return this.count;
    }
    
    /**
     * Adds the item to the front.
     * @param item
     */
    public void addFirst(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        
        Node newNode = new Node(item);
        if (isEmpty()) {
            this.head = this.tail = newNode;
        } else {
            Node formerHead = this.head;
            this.head = newNode;
            formerHead.next = newNode;
            newNode.prev = formerHead;
        }
        count++;
    }
    
    /**
     * Adds the item to the end.
     * @param item
     */
    public void addLast(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        
        Node newNode = new Node(item);
        if (isEmpty()) {
            this.head = this.tail = newNode;
        } else {
            Node formerTail = this.tail;
            this.tail = newNode;
            formerTail.prev = newNode;
            newNode.next = formerTail;
        }
        count++;
    }
    
    /**
     * Removes and returns the item from the front.
     * @return
     */
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        this.count--;
        Node formerHead = this.head;
        this.head = formerHead.prev;
        if (!isEmpty()) {
            this.head.next = null;
        }
        return formerHead.data;
    }
    
    /**
     * Removes and returns the item from the end.
     * @return
     */
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        this.count--;
        Node formerTail = this.tail;
        this.tail = formerTail.next;
        if (!isEmpty()) {
            this.tail.prev = null;
        }
        return formerTail.data;
    }
    
    /**
     * Returns an iterator over items in order from front to end.
     */
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }
    
    /**
     * Node encapsulates the node data and the node siblings.
     * @author boris
     *
     */
    private class Node {
        Item data = null;
        Node prev = null;
        Node next = null;
        
        public Node(Item data) {
            this.data = data;
        }
    }
    
    /**
     * DequeIterator encapsulates the iteration logic for the Deque.
     * @author boris
     *
     * @param <Item>
     */
    private class DequeIterator implements Iterator<Item> {

        private Node current = Deque.this.head;
        
        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (this.current == null) {
                throw new NoSuchElementException();
            }
            Item data = this.current.data;
            this.current = this.current.prev; 
            return data;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
        
    }

}
