package com.tk.kotlinmvp.mvp.model

import com.tk.kotlinmvp.net.RetrofitManager
import com.tk.kotlinmvp.rx.scheduler.SchedulerUtils
import com.tk.ktlinmvp.mvp.model.bean.HomeBean
import io.reactivex.Observable

/**
 *Zhaolei
 *时间:2018/6/26
 */
class RankModel {

    fun getRankList(apiUrl: String): Observable<HomeBean.Issue> {
        return RetrofitManager.service.getIssueData(apiUrl).compose(SchedulerUtils.ioToMain())
    }

}