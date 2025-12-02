import java.io.*;
import java.util.concurrent.*;

public class Producer implements Runnable {
    private final String filePath;
    private final BlockingQueue<String> queue;

    public Producer(String filePath, BlockingQueue<String> queue) {
        this.filePath = filePath;
        this.queue = queue;
    }

    @Override
    public void run() {
        System.out.println("[Producer] Starting to read file: " + filePath);
        int lineCount = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            // Read each line from file
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    // Add line to queue
                    queue.put(line);
                    lineCount++;
                    System.out.println("[Producer] Added line " + lineCount + " to queue: " + line);
                }
            }

            // Send EOF signals to consumers
            for (int i = 0; i < 3; i++) {
                queue.put("EOF");
            }

            System.out.println("[Producer] Finished reading " + lineCount + " lines");

        } catch (FileNotFoundException e) {
            System.err.println("[Producer] File not found: " + filePath);
            System.err.println("Please create input.txt file in the same directory");
        } catch (IOException e) {
            System.err.println("[Producer] Error reading file: " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}