package com.tk.kotlinmvp.mvp.contract

import com.tk.kotlinmvp.base.IBaseView
import com.tk.kotlinmvp.base.IPresenter
import com.tk.ktlinmvp.mvp.model.bean.HomeBean

/**
 *Zhaolei
 *时间:2018/5/27
 */
interface CategoryDetailContract {
    interface View : IBaseView {
        /**
         * 显示详情的信息
         */
        fun setCateDetailList(itemList: ArrayList<HomeBean.Issue.Item>)

        /**
         * 显示错误信息
         */
        fun showError(errorMsg: String)
    }

    interface Presenter : IPresenter<View> {

        fun getCategoryDetailList(id: Long)

        fun loadMoreData()
    }
}