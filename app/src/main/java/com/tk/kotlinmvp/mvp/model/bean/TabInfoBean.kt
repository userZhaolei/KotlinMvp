package com.tk.kotlinmvp.mvp.model.bean

/**
 *Zhaolei
 *时间:2018/6/14
 */
class TabInfoBean(val tabInfo: TabInfo) {
    data class TabInfo(val tabList: ArrayList<Tab>)

    data class Tab(val id: Long, val name: String, val apiUrl: String)
}