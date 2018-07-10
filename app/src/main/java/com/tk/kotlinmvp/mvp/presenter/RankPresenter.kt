package com.tk.kotlinmvp.mvp.presenter

import com.tk.kotlinmvp.base.BasePresenter
import com.tk.kotlinmvp.mvp.contract.RankContract
import com.tk.kotlinmvp.mvp.model.RankModel
import com.tk.kotlinmvp.net.exception.ExceptionHandle

/**
 *Zhaolei
 *时间:2018/6/26
 */
class RankPresenter : BasePresenter<RankContract.View>(), RankContract.Presenter {
    private val rankModel by lazy { RankModel() }
    override fun requestRankList(apiUrl: String) {
        checkViewAttched()
        mRootView?.showLoading()
        val disposable = rankModel.getRankList(apiUrl).subscribe({ rankBean ->
            mRootView?.setRankList(rankBean.itemList)
        }, { throwable ->
            mRootView?.showError(ExceptionHandle.handleException(throwable), ExceptionHandle.errorCode)
        })
        addSubscription(disposable)
    }
}