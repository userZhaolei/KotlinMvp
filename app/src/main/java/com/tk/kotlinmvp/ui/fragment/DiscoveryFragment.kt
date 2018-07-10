package com.tk.kotlinmvp.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import com.tk.kotlinmvp.R
import com.tk.kotlinmvp.base.BaseFragment
import com.tk.kotlinmvp.base.BaseFragmentAdapter
import com.tk.kotlinmvp.utils.DisplayManager
import com.tk.kotlinmvp.utils.StatusBarUtil
import com.tk.kotlinmvp.utils.TabLayoutHelper
import kotlinx.android.synthetic.main.fragment_discovery.*

/**
 *Zhaolei
 *时间:2018/4/26
 */
class DiscoveryFragment : BaseFragment() {
    private var mTitle: String? = null

    private var tabList = ArrayList<String>()
    private var fragments = ArrayList<Fragment>()

    override fun getLayoutId(): Int = R.layout.fragment_discovery

    companion object {
        fun getInstance(title: String): DiscoveryFragment {
            var fragment = DiscoveryFragment()
            var bundle = Bundle()
            fragment.arguments = bundle
            fragment.mTitle = title
            return fragment
        }
    }

    override fun initView() {
        DisplayManager.init(activity)
        //状态栏透明和间距处理
        StatusBarUtil.darkMode(activity)
        StatusBarUtil.setPaddingSmart(activity, mToolbar)

        tv_header_title.text = mTitle

        tabList.add("关注")
        tabList.add("分类")


        tabList.add("关注")
        tabList.add("分类")
        fragments.add(FollowFragment.getInstance("关注"))
        fragments.add(CategoryFragment.getInstance("分类"))

        /**
         * getSupportFragmentManager() 替换为getChildFragmentManager()
         */
        mViewPager.adapter = BaseFragmentAdapter(childFragmentManager, fragments, tabList)
        mTabLayout.setupWithViewPager(mViewPager)
        TabLayoutHelper.setUpIndicatorWidth(mTabLayout)

    }

    override fun lazyLoad() {
    }
}