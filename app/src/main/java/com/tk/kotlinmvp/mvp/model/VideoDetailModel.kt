package com.tk.kotlinmvp.mvp.model

import com.tk.kotlinmvp.net.RetrofitManager
import com.tk.kotlinmvp.rx.scheduler.SchedulerUtils
import com.tk.ktlinmvp.mvp.model.bean.HomeBean
import io.reactivex.Observable


/**
 *Zhaolei
 *时间:2018/5/17
 */
class VideoDetailModel {

    //获取具体视频详情
    fun requestRelatedData(id: Long): Observable<HomeBean.Issue> {

        return RetrofitManager.service.getRelatedData(id)
                .compose(SchedulerUtils.ioToMain())
    }
}