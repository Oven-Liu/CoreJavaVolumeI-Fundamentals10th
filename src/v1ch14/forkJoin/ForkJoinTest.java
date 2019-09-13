package v1ch14.forkJoin;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Set;
import java.util.concurrent.*;
import java.util.function.DoublePredicate;

/**
 * This program demonstrates the fork-join framework.
 *
 * @author Cay Horstmann
 * @version 1.01 2015-06-21
 */
public class ForkJoinTest {
    public static void main(String[] args) {
        final int SIZE = 10000000;
        double[] numbers = new double[SIZE];
        for (int i = 0; i < SIZE; i++) numbers[i] = Math.random();
        Counter counter = new Counter(numbers, 0, numbers.length, x -> x > 0.5);
        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(counter);
        System.out.println(counter.join());

        ConcurrentHashMap<String, Boolean> concurrentHashMap = new ConcurrentHashMap<>();
        concurrentHashMap.put("this", true);
        concurrentHashMap.put("is", true);
        concurrentHashMap.put("a", true);
        concurrentHashMap.put("java", true);
        concurrentHashMap.put("book", true);
        System.out.println("concurrentHashMap = " + concurrentHashMap); // {a=true, java=true, book=true, this=true, is=true}
        // Set<String> javaContent = concurrentHashMap.keySet();
        Set<String> javaContent = concurrentHashMap.keySet(false);
        javaContent.remove("java");
        javaContent.add("basic"); // java.lang.UnsupportedOperationException
        System.out.println("javaContent = " + javaContent); // [a, book, this, is, basic]
        System.out.println("concurrentHashMap = " + concurrentHashMap); // {a=true, book=true, this=true, is=true, basic=false}

        int [] ages = {23, 34, 24, 21, 26};
        Arrays.parallelSort(ages, 1, 4); // 包头不包尾，值排序中间三个值
        System.out.println("ages = " + Arrays.toString(ages)); // [23, 21, 24, 34, 26]
        String [] names = {"Jessica", "Oven", "Mike", "Nikita"};
        Arrays.parallelSort(names, Comparator.comparing(String::length));
        System.out.println("names = " + Arrays.toString(names)); // [Oven, Mike, Nikita, Jessica]

        int [] arr = new int[5];
        Arrays.parallelSetAll(arr, i -> (i + 1) % 10); // i 表示数组元素的角标
        System.out.println("arr = " + Arrays.toString(arr)); // [1, 2, 3, 4, 5]
        Arrays.parallelPrefix(arr, (x, y) -> x * y);
        System.out.println("arr = " + Arrays.toString(arr)); // [1, 2, 6, 24, 120]
    }
}

class Counter extends RecursiveTask<Integer> {
    public static final int THRESHOLD = 1000;
    private double[] values;
    private int from;
    private int to;
    private DoublePredicate filter;

    public Counter(double[] values, int from, int to, DoublePredicate filter) {
        this.values = values;
        this.from = from;
        this.to = to;
        this.filter = filter;
    }

    protected Integer compute() {
        if (to - from < THRESHOLD) {
            int count = 0;
            for (int i = from; i < to; i++) {
                if (filter.test(values[i])) count++;
            }
            return count;
        } else {
            int mid = (from + to) / 2;
            Counter first = new Counter(values, from, mid, filter);
            Counter second = new Counter(values, mid, to, filter);
            invokeAll(first, second);
            return first.join() + second.join();
        }
    }
}
