package jy.cn.com.yframework.simple.db;

import jy.cn.com.ylibrary.db.Scope;

/**
 * @Author Administrator
 * @Date 2019/10/28-13:59
 * @TODO
 */
public class TestDao {
    @Scope(isPrimaryKey = true, isAutoKey = true)
    public int id;
    public String savePath;
    public int countLength;
    public int readLength;
    public int connectionTime;
    public int stateInt;
    @Scope(isCompareField = true)
    public String url;
    public int updateProgress;
}
