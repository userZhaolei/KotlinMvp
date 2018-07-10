package com.tk.kotlinmvp.mvp.model

import com.tk.kotlinmvp.mvp.model.bean.CategoryBean
import com.tk.kotlinmvp.net.RetrofitManager
import com.tk.kotlinmvp.rx.scheduler.SchedulerUtils
import io.reactivex.Observable

/**
 *Zhaolei
 *时间:2018/5/20
 */
class CotegoryModel {
    /**
     * 获取分类信息
     */
    fun getCategoryData(): Observable<ArrayList<CategoryBean>> {
        return RetrofitManager.service.getCategory().compose(SchedulerUtils.ioToMain())
    }

}