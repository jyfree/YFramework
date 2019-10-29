package jy.cn.com.ylibrary.db;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import jy.cn.com.ylibrary.util.YLogUtil;

/**
 * @Author Administrator
 * @Date 2019/10/28-14:08
 * @TODO 反射管理器
 */
public class DBFieldManager {

    private static final String TAG = DBFieldManager.class.getSimpleName();

    private static List<DBFieldInfo> register(Class<?> subClass) {
        Field[] fields = subClass.getDeclaredFields();
        List<DBFieldInfo> list = new ArrayList<>();
        for (Field fie : fields) {
            // 允许访问私有变量
            fie.setAccessible(true);

            boolean isPrimaryKey = false;
            boolean isAutoKey = false;
            boolean isUpdateField = false;
            int updateFieldVersion = 1;
            boolean isFilter = false;
            boolean isCompareField = false;

            //解析注解
            if (fie.isAnnotationPresent(Scope.class)) {
                Scope scope = fie.getAnnotation(Scope.class);

                isPrimaryKey = scope.isPrimaryKey();
                isAutoKey = scope.isAutoKey();
                isUpdateField = scope.isUpdateField();
                updateFieldVersion = scope.updateFieldVersion();
                isFilter = scope.isFilter();
                isCompareField = scope.isCompareField();
            }
            list.add(new DBFieldInfo(subClass, fie, isPrimaryKey, isAutoKey, isUpdateField, updateFieldVersion, fie.getName(), fie.getType(), isFilter, isCompareField));
        }

        return list;
    }

    private static List<DBFieldInfo> getScopeListInfo(Class<?> subClass) {

        return register(subClass);
    }


    public static String createTable(Class<?> subClass) {

        List<DBFieldInfo> list = getScopeListInfo(subClass);

        StringBuilder sb = new StringBuilder("CREATE TABLE ");
        sb.append(subClass.getSimpleName());
        sb.append(" (");

        for (DBFieldInfo it : list) {
            if (it.isUpdateField() || it.isFilter()) {
                continue;
            }
            sb.append(it.getName());
            sb.append(" ");
            //判断是否为主键
            if (it.isPrimaryKey()) {
                if (it.isAutoKey()) {
                    sb.append("INTEGER PRIMARY KEY AUTOINCREMENT");
                } else {
                    sb.append("PRIMARY KEY");
                }
            } else {
                if (int.class.equals(it.getType())) {
                    sb.append("INTEGER");
                } else if (long.class.equals(it.getType())) {
                    sb.append("INTEGER");
                } else if (float.class.equals(it.getType())) {
                    sb.append("float");
                } else if (String.class.equals(it.getType())) {
                    sb.append("TEXT");
                } else if (boolean.class.equals(it.getType())) {
                    sb.append("INTEGER");
                } else if (double.class.equals(it.getType())) {
                    sb.append("double");
                } else {
                    sb.append("TEXT");
                }
            }
            sb.append(", ");
        }

        String sqlMsg = sb.substring(0, sb.lastIndexOf(", ")) + ");";
        YLogUtil.INSTANCE.iTag(TAG, "创建表", sqlMsg);

        return sqlMsg;

    }

}
