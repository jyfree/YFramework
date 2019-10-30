package jy.cn.com.ylibrary.sp;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

import jy.cn.com.ylibrary.thread.QueueProcessingType;
import jy.cn.com.ylibrary.thread.ThreadPoolFactory;

/**
 * @Author Administrator
 * @Date 2019/10/30-17:45
 * @TODO
 */
public class SharedPreferencesManager {


    private SharedPreferencesManager() {

    }

    public static synchronized SharedPreferencesManager getInstance() {
        return SPHolder.instance;
    }

    private static class SPHolder {
        private static final SharedPreferencesManager instance = new SharedPreferencesManager();
    }


    //线程池
    private Executor SYNC_EXECUTOR = ThreadPoolFactory.createExecutor(2, 2, QueueProcessingType.FIFO);

    public void submit(Runnable task) {
        if (((ExecutorService) SYNC_EXECUTOR).isShutdown()) {
            SYNC_EXECUTOR = ThreadPoolFactory.createExecutor(2, 2, QueueProcessingType.FIFO);
        }
        SYNC_EXECUTOR.execute(task);
    }
}
