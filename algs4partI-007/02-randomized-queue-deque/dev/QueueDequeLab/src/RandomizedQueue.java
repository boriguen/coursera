import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 
 */

/**
 * @author boris
 *
 */
public class RandomizedQueue<Item> implements Iterable<Item> {

    private int count = 0;
    private Item[] items = null;
    
    /**
     * Constructs an empty queue.
     */
    @SuppressWarnings("unchecked")
    public RandomizedQueue() {
        items = (Item[]) new Object[1];
    }
    
    /**
     * Allows unit testing.
     * @param args
     */
    public static void main(String[] args) {
        // Init test variables.
        int[] testValues = new int[]{1, 2, 3, 4};
        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
        
        // Run count/empty tests.
        assert queue.isEmpty();
        queue.enqueue(testValues[0]);
        assert !queue.isEmpty();
        assert queue.size() == 1;
        queue.enqueue(testValues[1]);
        assert queue.size() == 2;
        queue.dequeue();
        queue.dequeue();
        assert queue.size() == 0;
        
        // Run enqueue/dequeue tests.
        int times = 1000;
        int occurrences = 0;
        for (int i = 0; i < times; i++) {
            queue = new RandomizedQueue<Integer>();
            queue.enqueue(testValues[0]);
            queue.enqueue(testValues[1]);
            queue.enqueue(testValues[2]);
            queue.enqueue(testValues[3]);
            
            int dequeued = queue.dequeue();
            if (dequeued == testValues[0]) {
                occurrences++;
            }
        }
        System.out.printf("Probability of occurrence of value %d from "
                + "queue of size %d for %d tests is %.2f", 
                testValues[0], queue.size(), times, (float) occurrences / (float) times);
        
        // Run iterator tests.
        Iterator<Integer> it = queue.iterator();
        while (it.hasNext()) {
            System.out.printf("\nCurrent item: %d", it.next());
        }
        
    }

    /**
     * Is the queue empty?
     * @return
     */
    public boolean isEmpty() {
        return size() == 0;
    }
    
    /**
     * Returns the number of items on the queue.
     * @return
     */
    public int size() {
        return this.count;
    }
    
    /**
     * Enqueues an item.
     * @param item
     */
    public void enqueue(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        if (this.count == this.items.length) {
            resize(this.items.length * 2);
        }
        this.items[this.count] = item;
        this.count++;
    }
    
    /**
     * Removes and returns a random item.
     */
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("The queue is empty");
        }
        int index = StdRandom.uniform(this.count);
        Item dequeued = this.items[index];
        this.items[index] = this.items[--this.count];
        this.items[this.count] = null;
        if (this.count > 0 && this.count == this.items.length / 4) {
            resize(this.items.length / 2);
        }
        
        return dequeued;
    }
    
    /**
     * Returns without removing a random item.
     * @return
     */
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("The queue is empty");
        }
        int index = StdRandom.uniform(this.count);
        return this.items[index];
    }
    
    /**
     * Provides an iterator for this class.
     */
    public Iterator<Item> iterator() {
        return new QueueIterator();
    }
    
    /**
     * Resizes the underlying array if items by size.
     * @param size
     */
    @SuppressWarnings("unchecked")
    private void resize(int size) {
        Item[] copy = (Item[]) new Object[size];
        for (int i = 0; i < this.count; i++) {
            copy[i] = this.items[i];
        }
        this.items = copy;
    }
    
    /**
     * QueueIterator encapsulates the iteration logic for the Queue.
     * @author boris
     *
     */
    private class QueueIterator implements Iterator<Item> {

        private Item[] items = null;
        private int index = 0;
        
        @SuppressWarnings("unchecked")
        private QueueIterator() {
            this.items = (Item[]) new Object[RandomizedQueue.this.count];
            for (int i = 0; i < this.items.length; i++) {
                this.items[i] = RandomizedQueue.this.items[i];
            }
            StdRandom.shuffle(this.items);
        }
        
        @Override
        public boolean hasNext() {
            return this.index < this.items.length;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item current = this.items[this.index];
            this.index++;
            return current;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
            
        }
        
    }

}
