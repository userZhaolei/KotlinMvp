package com.tk.kotlinmvp.base

/**
 *Zhaolei
 *时间:2018/5/3
 */
interface IPresenter<in V : IBaseView> {

    fun attachView(mRootView: V)

    fun detachView()

}