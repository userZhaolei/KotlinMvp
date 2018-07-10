package com.tk.kotlinmvp.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


/**
 *Zhaolei
 *时间:2018/5/1
 */
open class BasePresenter<T : IBaseView> : IPresenter<T> {
    var mRootView: T? = null

    private var compositeDisposable = CompositeDisposable()


    //取消现在正在执行的所有的订阅
    override fun detachView() {
        mRootView = null
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.clear()
        }
    }

    override fun attachView(mRootView: T) {
        this.mRootView = mRootView
    }

    private val isViewAttached: Boolean
        get() = mRootView != null

    //检查view是否连接
    fun checkViewAttched() {
        if (mRootView == null) {
            throw  MvpViewNotAttachedException()
        }
    }

    //添加订阅事件
    fun addSubscription(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    private class MvpViewNotAttachedException internal constructor() : RuntimeException("Please call IPresenter.attachView(IBaseView) before" + " requesting data to the IPresenter")

}