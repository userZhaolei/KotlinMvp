package com.tk.kotlinmvp.mvp.model

import com.tk.kotlinmvp.net.RetrofitManager
import com.tk.kotlinmvp.rx.scheduler.SchedulerUtils
import com.tk.ktlinmvp.mvp.model.bean.HomeBean
import io.reactivex.Observable

/**
 *Zhaolei
 *时间:2018/5/21
 */
class SearchModel {
    /**
     * 请求热门关键词的数据
     */
    fun requestHotWordData(): Observable<ArrayList<String>> {
        return RetrofitManager.service.getHotWord().compose(SchedulerUtils.ioToMain())
    }

    /**
     * 搜索关键词返回的结果
     */
    fun getSearchResult(words: String): Observable<HomeBean.Issue> {
        return RetrofitManager.service.getSearchData(words).compose(SchedulerUtils.ioToMain())
    }

    /**
     * 加载更多数据
     */
    fun loadMoreData(url: String): Observable<HomeBean.Issue> {
        return RetrofitManager.service.getIssueData(url)
                .compose(SchedulerUtils.ioToMain())
    }

}