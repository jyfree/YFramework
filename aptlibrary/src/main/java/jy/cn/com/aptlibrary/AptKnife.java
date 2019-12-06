package jy.cn.com.aptlibrary;

import java.lang.reflect.Method;

/**
 * @Author Administrator
 * @Date 2019/12/6-17:05
 * @TODO
 */
public class AptKnife {

    public static void bind(Object activity) {

        Class clazz = activity.getClass();
        try {
            Class bindViewClass = Class.forName(clazz.getName() + "_ViewBinding");
            Method method = bindViewClass.getMethod("bind", activity.getClass());
            method.invoke(bindViewClass.newInstance(), activity);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
