
public class Subset {

    public static void main(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("Only 1 argument is expected");
        }
        // Extract k.
        int k = Integer.parseInt(args[0]);
        
        // Queue all input strings.
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        while(!StdIn.isEmpty()) {
            String s = StdIn.readString();
            if (s != null) {
                queue.enqueue(s);
            }
        }
        
        // Display k strings.
        for (int i = 0; i < k; i++) {
            StdOut.println(queue.dequeue());
        }
    }

}
