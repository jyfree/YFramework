package jy.cn.com.yframework.simple.db

import android.content.Context
import android.os.Bundle
import jy.cn.com.yframework.R
import jy.cn.com.ylibrary.base.BaseAppCompatActivity
import jy.cn.com.ylibrary.coroutine.CoroutineResultCallback
import jy.cn.com.ylibrary.db.DownloadDao
import jy.cn.com.ylibrary.util.ActivityUtils

/**

 * @Author Administrator
 * @Date 2019/10/24-18:30
 * @TODO
 */
class DBSimpleActivity : BaseAppCompatActivity() {

    companion object {
        fun startAct(context: Context) {
            ActivityUtils.startActivity(context, DBSimpleActivity::class.java)
        }
    }

    override fun initLayoutID(): Int = R.layout.simple_db_activity

    override fun initClassTag(): Any = DBSimpleActivity::class.java.simpleName

    override fun initUI(savedInstanceState: Bundle?) {
        DownloadDao.getListInfo(CoroutineResultCallback {

        }, this)
    }
}