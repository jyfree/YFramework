package jy.cn.com.ylibrary.thread;

import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author Administrator
 * @Date 2019/9/17-17:28
 * @TODO 加载器
 */
public class LoaderEngine {

    private LoaderConfiguration configuration;
    private Executor taskExecutor;
    private final Map<String, ReentrantLock> locks = new WeakHashMap<>();

    LoaderEngine(LoaderConfiguration configuration) {
        this.configuration = configuration;
        taskExecutor = configuration.taskExecutor;
    }

    void submit(Thread task) {
        if (((ExecutorService) taskExecutor).isShutdown()) {
            taskExecutor = ThreadPoolFactory.createExecutor(configuration.threadPoolSize, configuration.threadPriority, configuration.tasksProcessingType);
        }
        taskExecutor.execute(task);
    }

    /**
     * 停止所有线程
     */
    void stop() {
        ((ExecutorService) taskExecutor).shutdownNow();
        locks.clear();
    }

    /**
     * 获取锁
     *
     * @param tag
     * @return
     */
    ReentrantLock getLock(String tag) {
        ReentrantLock lock = locks.get(tag);
        if (lock == null) {
            lock = new ReentrantLock();
            locks.put(tag, lock);
        }
        return lock;
    }
}
