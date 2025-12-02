import java.io.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Writer implements Runnable {
    private final String filePath;
    private final BlockingQueue<String> queue;
    private final int expectedEofCount;
    private final AtomicInteger eofCounter;

    public Writer(String filePath, BlockingQueue<String> queue, int expectedEofCount) {
        this.filePath = filePath;
        this.queue = queue;
        this.expectedEofCount = expectedEofCount;
        this.eofCounter = new AtomicInteger(0);
    }

    @Override
    public void run() {
        System.out.println("[Writer] Starting work...");
        int linesWritten = 0;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {

            while (true) {
                // Take line from queue
                String line = queue.take();

                if (line.equals("EOF")) {
                    // Count EOF signals
                    int currentCount = eofCounter.incrementAndGet();
                    System.out.println("[Writer] Received EOF signal (" + currentCount + "/" + expectedEofCount + ")");

                    // Check if all consumers have finished
                    if (currentCount >= expectedEofCount) {
                        System.out.println("[Writer] Finished writing " + linesWritten + " lines to file");
                        break;
                    }
                } else {
                    // Write the processed line to file
                    writer.write(line);
                    writer.newLine();
                    linesWritten++;
                    System.out.println("[Writer] Wrote line " + linesWritten + ": " + line);
                }
            }

        } catch (IOException e) {
            System.err.println("[Writer] Error writing to file: " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}