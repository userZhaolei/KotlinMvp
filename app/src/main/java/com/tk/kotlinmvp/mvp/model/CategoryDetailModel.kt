package com.tk.kotlinmvp.mvp.model

import com.tk.kotlinmvp.net.RetrofitManager
import com.tk.kotlinmvp.rx.scheduler.SchedulerUtils
import com.tk.ktlinmvp.mvp.model.bean.HomeBean
import io.reactivex.Observable

/**
 *Zhaolei
 *时间:2018/5/27
 */
class CategoryDetailModel {


    /**
     * 获取分类下的 List 数据
     */
    fun getCategoryDetalList(id: Long): Observable<HomeBean.Issue> {
        return RetrofitManager.service.getCategoryDetailList(id)
                .compose(SchedulerUtils.ioToMain())
    }

    /**
     * 加载更多数据
     */
    fun loadMoreData(url: String): Observable<HomeBean.Issue> {
        return RetrofitManager.service.getIssueData(url)
                .compose(SchedulerUtils.ioToMain())
    }
}