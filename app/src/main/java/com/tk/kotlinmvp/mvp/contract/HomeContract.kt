package com.tk.kotlinmvp.mvp.contract

import com.tk.kotlinmvp.base.IBaseView
import com.tk.kotlinmvp.base.IPresenter
import com.tk.ktlinmvp.mvp.model.bean.HomeBean


/**
 *Zhaolei
 *时间:2018/5/3
 */
interface HomeContract {

    interface View : IBaseView {

        /**
         * 设置第一次请求的数据
         */
        fun setHomeData(homeBean: HomeBean)

        /**
         * 设置加载更多的数据
         */
        fun setMoreData(itemList: ArrayList<HomeBean.Issue.Item>)

        /**
         * 显示错误信息
         */
        fun showError(msg: String, errorCode: Int)

    }

    interface Presenter : IPresenter<View> {
        /**
         * 获取首页精选数据
         */
        fun requestHomeData(num: Int)

        /**
         * 加载更多数据
         */
        fun loadMoreData()

    }
}