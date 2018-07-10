package com.tk.kotlinmvp.ui.fragment

import android.os.Bundle
import com.tk.kotlinmvp.R
import com.tk.kotlinmvp.base.BaseFragment
import com.tk.kotlinmvp.utils.StatusBarUtil
import kotlinx.android.synthetic.main.fragment_mine.*

/**
 *Zhaolei
 *时间:2018/4/26
 */
class MineFragment : BaseFragment() {
    private var mTitle: String? = null
    override fun getLayoutId(): Int = R.layout.fragment_mine

    companion object {
        fun getInstance(title: String): MineFragment {
            var fragment = MineFragment()
            var bundle = Bundle()
            fragment.arguments = bundle
            fragment.mTitle = title
            return fragment
        }
    }

    override fun initView() {
        //状态栏透明和间距处理
        StatusBarUtil.darkMode(activity)
        StatusBarUtil.setPaddingSmart(activity, toolbar)
    }

    override fun lazyLoad() {

    }
}