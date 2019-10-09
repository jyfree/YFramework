package jy.cn.com.yframework.simple.http

import android.content.Context
import android.os.Bundle
import android.view.View
import jy.cn.com.yframework.R
import jy.cn.com.yframework.simple.http.mvp.ApiSimpleContract
import jy.cn.com.yframework.simple.http.mvp.ApiSimpleModel
import jy.cn.com.yframework.simple.http.mvp.ApiSimplePresenter
import jy.cn.com.ylibrary.base.contract.BaseContract
import jy.cn.com.ylibrary.base.mvp.MvpBaseActivity
import jy.cn.com.ylibrary.imageload.setImageDefaultLoadIconUrl
import jy.cn.com.ylibrary.util.ActivityUtils
import kotlinx.android.synthetic.main.simple_api_test_activity.*

/**

 * @Author Administrator
 * @Date 2019/9/27-9:43
 * @TODO mvp 请求
 */
class MvpApiSimpleActivity : MvpBaseActivity<ApiSimplePresenter>(), ApiSimpleContract.View {

    companion object {
        fun startAct(context: Context) {
            ActivityUtils.startActivity(context, MvpApiSimpleActivity::class.java)
        }
    }

    override fun initClassTag(): Any = MvpApiSimpleActivity::class.java.simpleName

    override fun initPresenter(): ApiSimplePresenter = ApiSimplePresenter()

    override fun initModel(): BaseContract.BaseModel = ApiSimpleModel()

    override fun initLayoutID(): Int = R.layout.simple_api_test_activity

    override fun initView(savedInstanceState: Bundle?) {
        imageView.setImageDefaultLoadIconUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1569672004826&di=3bc2d7d26f6b57726a8ac377e1aeb982&imgtype=0&src=http%3A%2F%2Fphotocdn.sohu.com%2F20130416%2FImg372885486.jpg")
    }

    fun onRequest(view: View) {
        when (view.id) {
            R.id.requestData -> mPresenter.getBanner(1)
        }
    }

    override fun updateInfo(msg: String?) {
        tv_msg.text = msg
    }
}