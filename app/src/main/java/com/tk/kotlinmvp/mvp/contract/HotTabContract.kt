package com.tk.kotlinmvp.mvp.contract

import com.tk.kotlinmvp.base.IBaseView
import com.tk.kotlinmvp.base.IPresenter
import com.tk.kotlinmvp.mvp.model.bean.TabInfoBean

/**
 *Zhaolei
 *时间:2018/6/26
 */
interface HotTabContract {
    interface View : IBaseView {
        /**
         * 设置 TabInfo
         */
        fun setTabInfo(tabInfoBean: TabInfoBean)

        fun showErrow(errorMsg: String, errorCode: Int)
    }

    interface Presenter : IPresenter<View> {
        /**
         * 获取 TabInfo
         */
        fun getTabInfo()
    }
}