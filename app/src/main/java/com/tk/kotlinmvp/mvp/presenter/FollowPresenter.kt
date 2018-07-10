package com.tk.kotlinmvp.mvp.presenter

import com.tk.kotlinmvp.base.BasePresenter
import com.tk.kotlinmvp.mvp.contract.FollowContract
import com.tk.kotlinmvp.mvp.model.FollowModel
import com.tk.kotlinmvp.net.exception.ExceptionHandle

/**
 *Zhaolei
 *时间:2018/5/20
 */
class FollowPresenter : BasePresenter<FollowContract.View>(), FollowContract.Presenter {

    private val followModel by lazy { FollowModel() }
    private var nextPageUrl: String? = null
    /**
     * 请求关注数据
     */
    override fun requestFollowList() {
        checkViewAttched()
        mRootView?.showLoading()
        val subscribe = followModel.requestFollowList().subscribe({ issue ->
            mRootView?.dismissLoading()
            nextPageUrl = issue.nextPageUrl
            mRootView?.setFollowInfo(issue)


        }, { throwable ->
            mRootView?.apply {
                //处理异常
                showError(ExceptionHandle.handleException(throwable), ExceptionHandle.errorCode)
            }
        })
        addSubscription(subscribe)
    }

    override fun loadMoreData() {
        val disposable = nextPageUrl?.let {
            followModel.loadMoreData(it).subscribe({ issue ->
                mRootView?.apply {
                    nextPageUrl = issue.nextPageUrl
                    setFollowInfo(issue)
                }
            }, { t ->
                mRootView?.apply {
                    showError(ExceptionHandle.handleException(t), ExceptionHandle.errorCode)
                }
            })
        }
        if (disposable != null) {
            addSubscription(disposable)
        }

    }

}