package com.tk.kotlinmvp.mvp.presenter

import com.tk.kotlinmvp.MyApplication
import com.tk.kotlinmvp.base.BasePresenter
import com.tk.kotlinmvp.mvp.contract.VideoContract
import com.tk.kotlinmvp.mvp.model.VideoDetailModel
import com.tk.kotlinmvp.net.exception.ExceptionHandle
import com.tk.kotlinmvp.utils.NetworkUtil
import com.tk.ktlinmvp.mvp.model.bean.HomeBean


/**
 *Zhaolei
 *时间:2018/5/17
 */
class VideoDetailPresenter : BasePresenter<VideoContract.View>(), VideoContract.Presenter {

    private val videoDetailModel: VideoDetailModel by lazy {
        VideoDetailModel()
    }

    /**
     * 加载视频相关数据
     */
    override fun loadVideoInfo(itemInfo: HomeBean.Issue.Item) {
        if (mRootView == null) {
            return
        }
        val playInfo = itemInfo.data?.playInfo
        val netType = NetworkUtil.isWifi(MyApplication.context)
        checkViewAttched()

        if (playInfo!!.size > 0) {
            //无线网络时
            if (netType) {
                for (i in playInfo) {
                    if (i.type == "high") {
                        val url = i.url
                        mRootView?.setVideo(url)
                    }
                }
            } else {
                for (i in playInfo) {
                    if (i.type == "normal") {
                        val url = i.url
                        mRootView?.setVideo(url)
                    }
                }
            }
        } else {
            mRootView?.setVideo(itemInfo.data.playUrl)
        }
        //设置背景
        /*   val backgroundUrl = itemInfo.data.cover.blurred + "/thumbnail/${DisplayManager.getScreenHeight()!! - DisplayManager.dip2px(250f)!!}x${DisplayManager.getScreenWidth()}"
           backgroundUrl.let { mRootView?.setBackground(it) }*/
        mRootView!!.setVideoInfo(itemInfo)
    }

    override fun requestRelatedVideo(id: Long) {
        mRootView!!.showLoading()  //显示loadinag图
        val disposable = videoDetailModel.requestRelatedData(id)   //请求数
                .subscribe({ issue ->
                    //这个表示bean对象
                    mRootView?.apply {
                        //使用这个函数的话可以调用者这个函数的里面的方法
                        dismissLoading()     //关闭是loding图
                        setRecentRelatedVideo(issue.itemList)
                    }
                }, { t ->
                    mRootView?.apply {
                        dismissLoading()   //设置error
                        setErrorMsg(ExceptionHandle.handleException(t))
                    }
                })

        addSubscription(disposable)
    }
}