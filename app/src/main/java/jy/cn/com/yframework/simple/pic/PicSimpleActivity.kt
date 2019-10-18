package jy.cn.com.yframework.simple.pic

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import jy.cn.com.yframework.R
import jy.cn.com.yframework.TestApplication
import jy.cn.com.ylibrary.base.BaseActivity
import jy.cn.com.ylibrary.helper.PicHelper
import jy.cn.com.ylibrary.pic.PicListener
import jy.cn.com.ylibrary.pic.PicOptions
import jy.cn.com.ylibrary.util.*
import kotlinx.android.synthetic.main.simple_pic_activity.*
import java.io.File

/**

 * @Author Administrator
 * @Date 2019/10/18-11:37
 * @TODO 拍照&图库
 */
class PicSimpleActivity : BaseActivity(), PicListener {

    companion object {
        fun startAct(context: Context) {
            ActivityUtils.startActivity(context, PicSimpleActivity::class.java)
        }
    }

    override fun initLayoutID(): Int = R.layout.simple_pic_activity

    override fun initUI(savedInstanceState: Bundle?) {

    }

    override fun initClassTag(): Any = PicSimpleActivity::class.java.simpleName

    fun take(view: View) {
        when (view.id) {
            R.id.takeCamera -> PicHelper.takePic(this, PicHelper.TAKE_CAMERA, getPicOptions(), this)
            R.id.takePhotoAlbum -> PicHelper.takePic(this, PicHelper.TAKE_PICTURE, getPicOptions(), this)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cancelCompress()
    }


    override fun onTakePicSuccess(uri: Uri?) {

        YLogUtil.i("takePic--uri", uri)

        uri?.let {
            imageView.setImageURI(it)
        }
        //测试压缩图片
        compressBitmap(uri)
    }

    override fun onTakePicFail() {
        YLogUtil.i("takePic--fail")
    }

    private fun getPicOptions(): PicOptions {
        val cacheFile = File(FileUtils.getSdcardPath(), TimeUtil.nowTimeStr + ".jpg")
        return PicOptions.beginBuilder()
                .setCacheFile(cacheFile)
                .setCrop(checkBox.isChecked)
                .setCropHeight(200)
                .setCropWidth(200)
                .build()
    }


    //******************************压缩图片********************************************
    private var downloadDisposable: Disposable? = null

    private fun compressBitmap(uri: Uri?) {
        if (uri == null) return
        downloadDisposable = Observable.just(uri)
                .observeOn(Schedulers.io())
                .map {
                    val cacheFile = File(FileUtils.getSdcardPath(), TimeUtil.nowTimeStr + ".jpg")
                    BitmapUtils.imageZoom(TestApplication.getInstance(), it, cacheFile, 80, 800.0)
                    cacheFile
                }
                .observeOn(AndroidSchedulers.mainThread())
                .bindToLifecycle(lifecycleProvider)
                .subscribe {
                    YLogUtil.i("compressBitmap", it.absolutePath)
                }
    }

    private fun cancelCompress() {
        downloadDisposable?.dispose()
    }
}