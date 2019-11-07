package jy.cn.com.yframework.simple.db.bean;

import jy.cn.com.ylibrary.db.Scope;

/**
 * @Author Administrator
 * @Date 2019/11/7-15:27
 * @TODO
 */
public class TestInfo {
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
    @Scope(isUpdateField = true, updateFieldVersion = 2)
    public String testUpdate;
    @Scope(isUpdateField = true, updateFieldVersion = 3)
    public String testUpdateTwo;
    @Scope(isFilter = true)
    public String testFilter;
}
