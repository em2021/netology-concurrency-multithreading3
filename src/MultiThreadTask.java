import java.util.Arrays;
import java.util.concurrent.RecursiveTask;

public class MultiThreadTask extends RecursiveTask<Integer> {
    private final int[] arr;
    private final int threshold;

    public MultiThreadTask(int[] arr, int threshold) {
        this.arr = arr;
        this.threshold = threshold;
    }

    @Override
    protected Integer compute() {
        if (arr.length > threshold) {
            int middle = arr.length / 2;
            MultiThreadTask t1 = new MultiThreadTask(Arrays.copyOfRange(arr, 0, middle), threshold);
            MultiThreadTask t2 = new MultiThreadTask(Arrays.copyOfRange(arr, middle, arr.length), threshold);
            t1.fork();
            t2.fork();
            int result = 0;
            result += t1.join();
            result += t2.join();
            return result;
        } else {
            return Arrays.stream(arr).sum();
        }
    }
}