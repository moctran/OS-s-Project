import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExample {

    // Constants for the number of tasks and the size of the thread pool
    private static final int TASK_COUNT = 20;
    private static final int THREAD_POOL_SIZE = 4;

    public static void main(String[] args) {
        // Conventional approach 
        long startConventional = System.currentTimeMillis();
        runConventionalTasks();
        long endConventional = System.currentTimeMillis();
        System.out.println("Conventional approach took: " + (endConventional - startConventional) + " ms");

        // Thread pool approach
        long startThreadPool = System.currentTimeMillis();
        runThreadPoolTasks();
        long endThreadPool = System.currentTimeMillis();
        System.out.println("Thread pool approach took: " + (endThreadPool - startThreadPool) + " ms");
    }

    // Conventional approach: Run tasks sequentially
    private static void runConventionalTasks() {
        for (int i = 0; i < TASK_COUNT; i++) {
            performTaskWithDelay(i);
        }
    }

    // Thread pool approach: Use ExecutorService for parallel execution
    private static void runThreadPoolTasks() {
        // Create a fixed-size thread pool
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        
        // Submit tasks to the thread pool
        for (int i = 0; i < TASK_COUNT; i++) {
            final int taskNumber = i;
            executorService.submit(() -> performTaskWithDelay(taskNumber));
        }

        // Shut down the thread pool and wait for termination
        executorService.shutdown();
        try {
            // Waits for all threads in the ExecutorService to terminate
            // 'Long.MAX_VALUE' is used as the timeout, indicating an effectively infinite timeout
            // 'TimeUnit.NANOSECONDS' specifies the time unit for the timeout
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            // The purpose is to handle the 'InterruptedException' by catching it and then immediately restoring the interrupted status of the current thread
            Thread.currentThread().interrupt();
        }
    }

    // Simulate a task with a delay
    private static void performTaskWithDelay(int taskNumber) {
        // Print information about the current thread before the delay
        System.out.println("Task " + taskNumber + " started by thread: " + Thread.currentThread().getName());

        // Simulate a network call or I/O operation with a 100ms delay
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            // The purpose is to handle the 'InterruptedException' by catching it and then immediately restoring the interrupted status of the current thread
            Thread.currentThread().interrupt();
        }

        // Print information about the current thread after the delay
        System.out.println("Task " + taskNumber + " completed by thread: " + Thread.currentThread().getName());
    }
}
