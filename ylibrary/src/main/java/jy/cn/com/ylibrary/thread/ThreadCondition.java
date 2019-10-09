package jy.cn.com.ylibrary.thread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import jy.cn.com.ylibrary.util.YLogUtil;

/**
 * @Author Administrator
 * @Date 2019/9/17-16:52
 * @TODO 并发线程
 */
public class ThreadCondition extends Thread {

    private ReentrantLock lock;
    private Condition condition;
    private long waitTimeout = 60;//默认超时时间60s，0为没有超时时间
    private ThreadCall call;

    public ThreadCondition() {
        lock = new ReentrantLock();
        condition = lock.newCondition();
    }

    public ThreadCondition(ReentrantLock lock) {
        this.lock = lock;
        condition = this.lock.newCondition();
    }

    public void setCall(ThreadCall call) {
        this.call = call;
    }

    public void setWaitTimeout(long waitTimeout) {
        this.waitTimeout = waitTimeout;
    }

    @Override
    public void run() {
        await();
    }

    private void await() {
        try {
            lock.lock();
            YLogUtil.INSTANCE.d("await--线程启动--加锁--等待通知", Thread.currentThread().getName());
            //await 和 signal 对应
            if (waitTimeout == 0) {
                condition.await();
            } else {
                condition.await(waitTimeout, TimeUnit.SECONDS);
            }
            YLogUtil.INSTANCE.d("await--继续执行线程--已收到通知(或等待超时)", Thread.currentThread().getName());
            if (null != call) {
                call.apply();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
            YLogUtil.INSTANCE.d("await--执行结束--释放锁", Thread.currentThread().getName());

        }
    }

    public void signal() {
        try {
            lock.lock();
            YLogUtil.INSTANCE.d("singal--通知在等待的线程--加锁");
            condition.signal();//await 和 signal 对应
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
            YLogUtil.INSTANCE.d("singal--执行结束--释放锁");
        }
    }
}
