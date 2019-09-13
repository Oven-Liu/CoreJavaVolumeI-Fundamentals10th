package v1ch14.blockingQueue;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Cay Horstmann
 * @version 1.02 2015-06-21
 */
public class BlockingQueueTest {
    private static final int FILE_QUEUE_SIZE = 10;
    private static final int SEARCH_THREADS = 100;
    private static final File DUMMY = new File("");
    private static BlockingQueue<File> queue = new ArrayBlockingQueue<>(FILE_QUEUE_SIZE);

    public static void main(String[] args) {
        try (Scanner in = new Scanner(System.in)) {
            System.out.print("Enter base directory (e.g. /opt/jdk1.8.0/src): ");
            String directory = in.nextLine();
            System.out.print("Enter keyword (e.g. volatile): ");
            String keyword = in.nextLine();

            Runnable enumerator = () -> {
                try {
                    enumerate(new File(directory));
                    queue.put(DUMMY);
                } catch (InterruptedException e) {
                }
            };

            new Thread(enumerator).start();
            for (int i = 1; i <= SEARCH_THREADS; i++) {
                Runnable searcher = () -> {
                    try {
                        boolean done = false;
                        while (!done) {
                            File file = queue.take();
                            if (file == DUMMY) {
                                queue.put(file);
                                done = true;
                            } else search(file, keyword);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                    }
                };
                new Thread(searcher).start();
            }
        }
    }

    /**
     * Recursively enumerates all files in a given directory and its subdirectories.
     *
     * @param directory the directory in which to start
     */
    public static void enumerate(File directory) throws InterruptedException {
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) enumerate(file);
            else queue.put(file);
        }
    }

    /**
     * Searches a file for a given keyword and prints all matching lines.
     *
     * @param file    the file to search
     * @param keyword the keyword to search for
     */
    public static void search(File file, String keyword) throws IOException {
        try (Scanner in = new Scanner(file, "UTF-8")) {
            int lineNumber = 0;
            while (in.hasNextLine()) {
                lineNumber++;
                String line = in.nextLine();
                if (line.contains(keyword))
                    System.out.printf("%s:%d:%s%n", file.getPath(), lineNumber, line);
            }
        }
        ConcurrentHashMap<String, Long> map = new ConcurrentHashMap<>();
        int threshold = 1;
        // 查找第一个出现次数超过 1000 次的单词
        String result = map.search(threshold, (k, v) -> v > 1000 ? k : null);
        map.forEach(threshold, (k, v) -> System.out.println(k + " -> " + v));
        map.forEach(threshold,
                (k, v) -> k + " -> " + v, // Transformer
                System.out::println); // Consumer
        // 打印有大值的条目
        map.forEach(threshold,
                (k, v) -> v > 1000 ? k + " -> " + v : null, // Filter and transformer
                System.out::println); // The nulls are not passed to the consumer
        // 计算所有值的总和
        Long sum = map.reduceValues(threshold, Long::sum);
        // 计算最长的键的长度
        Integer maxlength = map.reduceKeys(threshold,
                String::length, // Transformer
                Integer::max); // Accumulator
        // 统计多少个条目的值 > 1000
        Long count = map.reduceValues(threshold,
                v -> v > 1000 ? 1L : null,
                Long::sum);
        Long sum2 = map.reduceValuesToLong(threshold,
                Long::longValue, // Transformer to primitive type
                0, // Default value for empty map
                Long::sum); // Primitive type accumulator
    }

}
