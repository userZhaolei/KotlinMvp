package com.tk.kotlinmvp.mvp.bean

import com.flyco.tablayout.listener.CustomTabEntity

/**
 *Zhaolei
 *时间:2018/4/20
 */
class TabEntity(var title: String, var selectIcon: Int, var unSelecIcon: Int) : CustomTabEntity {

    override fun getTabUnselectedIcon(): Int {
        return unSelecIcon;
    }

    override fun getTabSelectedIcon(): Int {
        return selectIcon;
    }

    override fun getTabTitle(): String {
        return title;
    }


}