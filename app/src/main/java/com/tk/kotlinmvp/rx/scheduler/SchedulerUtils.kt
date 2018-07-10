package com.tk.kotlinmvp.rx.scheduler

/**
 *Zhaolei
 *时间:2018/5/3
 */
object SchedulerUtils {
    fun <T> ioToMain(): IoMainScheduler<T> {
        return IoMainScheduler()
    }
}