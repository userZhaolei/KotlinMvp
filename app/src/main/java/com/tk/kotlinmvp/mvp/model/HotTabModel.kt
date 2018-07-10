package com.tk.kotlinmvp.mvp.model

import com.tk.kotlinmvp.mvp.model.bean.TabInfoBean
import com.tk.kotlinmvp.net.RetrofitManager
import com.tk.kotlinmvp.rx.scheduler.SchedulerUtils
import io.reactivex.Observable

/**
 *Zhaolei
 *时间:2018/6/14
 */
class HotTabModel {
    /**
     * 获取 TabInfo
     */
    fun getTabInfo(): Observable<TabInfoBean> {
        return RetrofitManager.service.getRankList()
                .compose(SchedulerUtils.ioToMain())
    }
}