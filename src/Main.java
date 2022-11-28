import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.FutureTask;

public class Main {

    public static void main(String[] args) throws Exception {
        System.out.println("Тест с небольшим массивом:");
        int smallArraySize = 100_000;
        System.out.printf("%s %d %s%n", "Создаю массив из", smallArraySize, "элементов...");
        int[] smallArray = generateArray(smallArraySize, 11);
        singleThreadTest(smallArray, 1000);
        multipleThreadsTest(smallArray, 1000);
        System.out.println();

        System.out.println("Тест с большим массивом:");
        int largeArraySize = 1_000_000;
        System.out.printf("%s %d %s%n", "Создаю массив из", largeArraySize, "элементов...");
        int[] largeArray = generateArray(largeArraySize, 11);
        singleThreadTest(largeArray, 1000);
        multipleThreadsTest(largeArray, 1000);
    }

    public static int[] generateArray(int length, int valueBoundary) {
        int[] arr = new int[length];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = new Random().nextInt(valueBoundary);
        }
        return arr;
    }

    public static void singleThreadTest(int[] array, int threshold) throws Exception {
        Callable<Integer> singleThreadTask = new SingleThreadTask(array, threshold);
        FutureTask<Integer> futureTask = new FutureTask<>(singleThreadTask);
        System.out.println("Запускаю вычисление в одном потоке...");
        long start = System.currentTimeMillis();
        new Thread(futureTask).start();
        Integer result = futureTask.get();
        long finish = System.currentTimeMillis();
        System.out.println("Результат вычислений в одном потоке: " + result);
        System.out.println("Время выполнения в одном потоке(msec):" + (finish - start));
    }

    public static void multipleThreadsTest(int[] array, int threshold) {
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        MultiThreadTask multiThreadTask = new MultiThreadTask(array, threshold);
        System.out.println("Запускаю вычисление в нескольких потоках...");
        long start = System.currentTimeMillis();
        Integer result = forkJoinPool.invoke(multiThreadTask);
        long finish = System.currentTimeMillis();
        forkJoinPool.shutdown();
        System.out.println("Результат вычислений в нескольких потоках: " + result);
        System.out.println("Время выполнения в нескольких потоках(msec): " + (finish - start));
    }
}