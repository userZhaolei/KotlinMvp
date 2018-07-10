package com.tk.kotlinmvp.mvp.model.bean

import java.io.Serializable

/**
 *Zhaolei
 *时间:2018/5/20
 */
data class CategoryBean(val id: Long, val name: String, val description: String, val bgPicture: String, val bgColor: String, val headerImage: String) : Serializable
