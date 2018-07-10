package com.tk.kotlinmvp.net.exception

/**
 *Zhaolei
 *时间:2018/5/3
 */
class ApiException : RuntimeException {

    private var code: Int? = null


    constructor(throwable: Throwable, code: Int) : super(throwable) {
        this.code = code
    }

    constructor(message: String) : super(Throwable(message))
}