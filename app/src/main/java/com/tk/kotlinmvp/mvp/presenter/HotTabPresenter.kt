package com.tk.kotlinmvp.mvp.presenter

import com.tk.kotlinmvp.base.BasePresenter
import com.tk.kotlinmvp.mvp.contract.HotTabContract
import com.tk.kotlinmvp.mvp.model.HotTabModel
import com.tk.kotlinmvp.net.exception.ExceptionHandle

/**
 *Zhaolei
 *时间:2018/6/14
 */
class HotTabPresenter : BasePresenter<HotTabContract.View>(), HotTabContract.Presenter {

    private val hotTabModel by lazy { HotTabModel() }

    override fun getTabInfo() {
        checkViewAttched()
        mRootView?.showLoading()
        val disposable = hotTabModel.getTabInfo().subscribe({ tabInfo ->
            mRootView?.setTabInfo(tabInfo)
        }, { error ->
            mRootView?.showErrow(ExceptionHandle.handleException(error), ExceptionHandle.errorCode)
        })
        addSubscription(disposable)
    }

}