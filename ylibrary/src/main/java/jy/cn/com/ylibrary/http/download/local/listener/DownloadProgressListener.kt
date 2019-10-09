package jy.cn.com.ylibrary.http.download.local.listener

interface DownloadProgressListener {
    /**
     * 下载进度
     * @param read
     * @param count
     * @param done
     */
    fun update(read: Long, count: Long, done: Boolean)
}