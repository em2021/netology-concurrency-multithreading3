import java.util.Arrays;
import java.util.concurrent.Callable;

public class SingleThreadTask implements Callable<Integer> {

    private final int[] arr;
    private final int threshold;

    public SingleThreadTask(int[] arr, int threshold) {
        this.arr = arr;
        this.threshold = threshold;
    }

    @Override
    public Integer call() throws Exception {
        return calculateSumOfArrayElements(arr, threshold);
    }

    public static Integer calculateSumOfArrayElements(int[] arr, int threshold) {
        if (arr.length > threshold) {
            int middle = arr.length / 2;
            return calculateSumOfArrayElements(Arrays.copyOfRange(arr, 0, middle), threshold) +
                    calculateSumOfArrayElements(Arrays.copyOfRange(arr, middle, arr.length), threshold);
        } else {
            return Arrays.stream(arr).sum();
        }
    }
}
