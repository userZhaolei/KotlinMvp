package com.tk.kotlinmvp.mvp.contract

import com.tk.kotlinmvp.base.IBaseView
import com.tk.kotlinmvp.base.IPresenter
import com.tk.ktlinmvp.mvp.model.bean.HomeBean

/**
 *Zhaolei
 *时间:2018/6/26
 */
interface RankContract {

    interface View : IBaseView {
        /**
         * 设置排行榜的数据
         */
        fun setRankList(itemList: ArrayList<HomeBean.Issue.Item>)

        fun showError(errorMsg: String, errorCode: Int)
    }

    interface Presenter : IPresenter<View> {
        fun requestRankList(apiUrl: String)
    }
}