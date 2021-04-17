package java0.conc0303;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 本周作业：（必做）思考有多少种方式，在main函数启动一个新线程或线程池，
 * 异步运行一个方法，拿到这个方法的返回值后，退出主线程？
 * 写出你的方法，越多越好，提交到github。
 * <p>
 * 一个简单的代码参考：
 */
public class Homework006 {

    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {

        long start=System.currentTimeMillis();

        ThreadPoolExecutor threadPoolExecutor=new ThreadPoolExecutor(
                2, 5, 1, TimeUnit.MINUTES,
                new LinkedBlockingDeque<>(6),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy());

        FutureTask<Integer> future = new FutureTask<Integer>(()->{return sum();});
        threadPoolExecutor.submit(future);
        // 确保  拿到result 并输出
        System.out.println("异步计算结果为：" + future.get(1,TimeUnit.MINUTES));

        try {
            threadPoolExecutor.shutdown();
            if (!threadPoolExecutor.awaitTermination(1, TimeUnit.SECONDS)) {
                threadPoolExecutor.shutdownNow();
            }
        } catch (InterruptedException e) {
            threadPoolExecutor.shutdownNow();
        }

        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");

    }

    private static int sum() {
        return fibo(36);
    }

    private static int fibo(int a) {
        if (a < 2) {
            return 1;
        }
        return fibo(a - 1) + fibo(a - 2);
    }
}
