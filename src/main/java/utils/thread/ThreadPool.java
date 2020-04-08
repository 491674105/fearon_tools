package utils.thread;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @description: 线程池
 * @author: Fearon
 * Tips: ThreadPool.getPoolProxy().execute(runnable);
 * @create: 2018/6/5 15:31
 **/
public class ThreadPool {
    private static final String TAG = ThreadPool.class.getSimpleName();
    private static ThreadPoolProxy poolProxy;

    /**
     * N(cpu) --> number of CPUs
     * U(cpu) --> target of utilization , 0<=U(cpu)<=1
     * W/C --> ratio of wait time to compute time
     * N(threads) = N(cpu)*U(cpu)*(1+W/C) ≈ U(cpu)*3 (Because, JVM can only provide you with the available CPU.)
     * @return
     */
    public static ThreadPoolProxy getPoolProxy() {
        if (poolProxy == null) {
            synchronized (TAG) {
                if (poolProxy == null) {
                    int processorCount = Runtime.getRuntime().availableProcessors();
                    int maxAvailable = Math.max(processorCount * 3, 10);
                    // 线程池的核心线程数、最大线程数，以及keepAliveTime都需要根据项目需要做修改
                    // PS：创建线程的开销 高于 维护线程(wait)的开销
                    poolProxy = new ThreadPoolProxy(processorCount, maxAvailable, 15000);
                }
            }
        }
        return poolProxy;
    }

    public static class ThreadPoolProxy {
        // 线程池
        private ThreadPoolExecutor threadPoolExecutor;
        //线程池中核心线程数
        private int corePoolSize;
        //线程池中最大线程数，若并发数高于该数，后面的任务则会等待
        private int maximumPoolSize;
        // 超出核心线程数的线程在执行完后保持alive时长
        private int keepAliveTime;

        /**
         * @param keepAliveTime time in milliseconds
         */
        public ThreadPoolProxy(int corePoolSize, int maximumPoolSize,
                               int keepAliveTime) {
            this.corePoolSize = corePoolSize;
            this.maximumPoolSize = maximumPoolSize;
            this.keepAliveTime = keepAliveTime;
        }

        public void execute(Runnable runnable) {
            if (runnable == null) {
                return;
            } else {
                if (threadPoolExecutor == null || threadPoolExecutor.isShutdown()) {
                    synchronized (TAG) {
                        if (threadPoolExecutor == null || threadPoolExecutor.isShutdown()) {
                            threadPoolExecutor = createExecutor();
                            threadPoolExecutor.allowCoreThreadTimeOut(false); // 核心线程始终不消失
                        }
                    }
                }
                threadPoolExecutor.execute(runnable);
            }
        }

        private ThreadPoolExecutor createExecutor() {
            return new ThreadPoolExecutor(corePoolSize,
                    maximumPoolSize, keepAliveTime,
                    TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<Runnable>(),
                    new DefaultThreadFactory(Thread.NORM_PRIORITY, "fearon-pool-"),
                    new AbortPolicy());
        }
    }

    /**
     * 创建线程的工厂
     */
    private static class DefaultThreadFactory implements ThreadFactory {
        // 线程池的计数
        private static final AtomicInteger poolNumber = new AtomicInteger(1);
        // 线程的计数
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        // 分组
        private final ThreadGroup group;
        // 线程前缀
        private final String namePrefix;
        // 线程优先级
        private final int threadPriority;

        DefaultThreadFactory(int threadPriority, String threadNamePrefix) {
            this.threadPriority = threadPriority;
            this.group = Thread.currentThread().getThreadGroup();
            namePrefix = threadNamePrefix + poolNumber.getAndIncrement() + "-thread-";
        }

        @Override
        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(group, runnable, namePrefix + threadNumber.getAndIncrement(), 0);
            if (thread.isDaemon()) {
                thread.setDaemon(false);
            }
            thread.setPriority(threadPriority);
            return thread;
        }
    }
}
