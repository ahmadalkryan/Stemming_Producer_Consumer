import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {
        // Initialize basic variables
        String inputFile = "src/input.txt";
        String outputFile = "output.txt";
        int numConsumers = 3; // Number of processing consumers

        System.out.println("Starting text processing system with word stemming");
        System.out.println("=================================================");
        System.out.println("Input file: " + inputFile);
        System.out.println("Output file: " + outputFile);
        System.out.println("Number of consumer threads: " + numConsumers);

        // Create concurrent queues
        BlockingQueue<String> queue1 = new LinkedBlockingQueue<>();
        BlockingQueue<String> queue2 = new LinkedBlockingQueue<>();

        // Create and start the producer
        Producer producer = new Producer(inputFile, queue1);
        Thread producerThread = new Thread(producer);
        producerThread.start();

        // Create and start consumer threads
        for (int i = 0; i < numConsumers; i++) {
            Thread consumerThread = new Thread(new Consumer(queue1, queue2, i + 1));
            consumerThread.start();
        }

        // Create and start the writer
        Writer writer = new Writer(outputFile, queue2, numConsumers);
        Thread writerThread = new Thread(writer);
        writerThread.start();

        try {
            // Wait for all threads to complete
            producerThread.join();
            writerThread.join();

            System.out.println("=================================================");
            System.out.println("File processing completed successfully!");
            System.out.println("You can check the output.txt file to see the results");

        } catch (InterruptedException e) {
            System.err.println("Error during program execution: " + e.getMessage());
            e.printStackTrace();
        }
    }
}