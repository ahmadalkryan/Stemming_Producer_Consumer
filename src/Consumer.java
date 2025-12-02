import java.util.concurrent.*;

public class Consumer implements Runnable {
    private final BlockingQueue<String> inputQueue;
    private final BlockingQueue<String> outputQueue;
    private final int consumerId;
    private final Stemmer stemmer;

    public Consumer(BlockingQueue<String> inputQueue, BlockingQueue<String> outputQueue, int consumerId) {
        this.inputQueue = inputQueue;
        this.outputQueue = outputQueue;
        this.consumerId = consumerId;
        this.stemmer = new Stemmer();
    }

    @Override
    public void run() {
        System.out.println("[Consumer " + consumerId + "] Starting work...");
        int processedLines = 0;

        try {
            while (true) {
                // Take line from input queue
                String line = inputQueue.take();

                // Check for EOF signal
                if (line.equals("EOF")) {
                    outputQueue.put("EOF");
                    System.out.println("[Consumer " + consumerId + "] Received EOF signal");
                    break;
                }

                processedLines++;
                System.out.println("[Consumer " + consumerId + "] Processing line: \"" + line + "\"");

                // Stem words in the line
                String processedLine = processLine(line);

                // Put result in output queue
                outputQueue.put(processedLine);
                System.out.println("[Consumer " + consumerId + "] Sent result: \"" + processedLine + "\"");
            }

            System.out.println("[Consumer " + consumerId + "] Finished processing " + processedLines + " lines");

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private String processLine(String line) {
        // Split line into words
        String[] words = line.split("\\s+");
        StringBuilder result = new StringBuilder();

        // Stem each word
        for (String word : words) {
            if (!word.trim().isEmpty()) {
                String stemmedWord = stemmer.stem(word);
                result.append(stemmedWord).append(" ");
            }
        }

        return result.toString().trim();
    }
}