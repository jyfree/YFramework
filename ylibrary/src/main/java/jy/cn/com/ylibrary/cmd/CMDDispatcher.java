package jy.cn.com.ylibrary.cmd;

import android.util.SparseArray;

import java.lang.reflect.Method;

import jy.cn.com.ylibrary.util.YLogUtil;

/**
 * Administrator
 * created at 2019/9/28 12:37
 * TODO:cmd拦截器
 */
public class CMDDispatcher {

    private static final String TAG = "CMDDispatcher";

    private static SparseArray<Commander> commanders = new SparseArray<>();

    /**
     * 协议注册
     *
     * @param o class
     */
    public static void register(Object o) {
        try {
            Method[] methods = o.getClass().getDeclaredMethods();
            for (Method method : methods) {
                CMD cmd = method.getAnnotation(CMD.class);
                if (cmd != null && commanders.get(cmd.id()) == null) {
                    commanders.put(cmd.id(), new Commander(o, method));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            YLogUtil.INSTANCE.eTag(TAG, "协议加载出错!!!", o.getClass());
        }
    }

    /**
     * 释放协议
     */
    public static void release() {
        commanders.clear();
    }

    // 协议调用
    public static void invoke(int cmd, CMDMessage cmdMessage) throws Exception {
        Commander commander = commanders.get(cmd);
        if (commander == null) {
            YLogUtil.INSTANCE.eTag(TAG, "没有协议号 cmdMessage ", cmd);
            return;
        }

        Object[] params = null; // 实参
        if (commander.parameterTypes != null && commander.parameterTypes.length == 1) {
            params = new Object[1];
            YLogUtil.INSTANCE.iFormatTag(TAG, "CMDDispatcher接收 ... invoke() --> cmd = %s, result = %s", cmd, cmdMessage.toString());

            if (commander.parameterTypes[0].equals(CMDMessage.class)) {
                params[0] = cmdMessage;
            } else {
                YLogUtil.INSTANCE.iFormatTag(TAG, "CMDDispatcher接收 ... invoke() --> 不存在的类型");
            }
        }
        commander.method.invoke(commander.o, params); // 反射调用方法

    }

    static class Commander {
        private final Object o; // 类名
        private final Method method; // 方法名
        private final Class<?>[] parameterTypes; // 形参类型

        Commander(Object o, Method method) {
            this.o = o;
            this.method = method;
            parameterTypes = method.getParameterTypes();
        }
    }
}
