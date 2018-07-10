package com.tk.kotlinmvp.mvp.presenter

import com.tk.kotlinmvp.base.BasePresenter
import com.tk.kotlinmvp.mvp.contract.CotegoryContract
import com.tk.kotlinmvp.mvp.model.CotegoryModel
import com.tk.kotlinmvp.net.exception.ExceptionHandle

/**
 *Zhaolei
 *时间:2018/5/20
 */
class CotegoryPresenter : BasePresenter<CotegoryContract.View>(), CotegoryContract.Presenter {
    private val mCotegoryModel by lazy { CotegoryModel() }
    override fun getCategoryData() {
        checkViewAttched()
        val disposable = mCotegoryModel.getCategoryData().subscribe({ categoryList ->
            mRootView?.apply {
                dismissLoading()
                showCategory(categoryList)
            }
        }, { t ->
            mRootView?.apply {
                //处理异常
                showError(ExceptionHandle.handleException(t), ExceptionHandle.errorCode)
            }

        })
        addSubscription(disposable)
    }
}