package com.tk.ktolinmvp.view.recyclerview

/**
 *Zhaolei
 *时间:2018/5/3
 */
interface MultipleType<in T>{
  fun getLayoutId(item:T,position:Int):Int
}