package com.tk.kotlinmvp.base

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 *Zhaolei
 *时间:2018/5/20
 */

/**
 * Created by xuhao on 2017/11/30.
 * 该类内的每一个生成的 Fragment 都将保存在内存之中，
 * 因此适用于那些相对静态的页，数量也比较少的那种；
 * 如果需要处理有很多页，并且数据动态性较大、占用内存较多的情况，
 * 应该使用FragmentStatePagerAdapter。
 */
class BaseFragmentAdapter : FragmentPagerAdapter {
    private var fragmentList: List<Fragment> = ArrayList<Fragment>()
    private var mTitles: List<String>? = null

    constructor(fm: FragmentManager, fragmentList: List<Fragment>) : super(fm) {
        this.fragmentList = fragmentList
    }

    constructor(fm: FragmentManager, fragmentList: List<Fragment>, mTitles: List<String>) : super(fm) {
        this.mTitles = mTitles
        setFragments(fm, fragmentList, mTitles)
    }


    override fun getItem(position: Int): Fragment {
        return fragmentList!![position]
    }

    override fun getCount(): Int {
        return fragmentList!!.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return if (null != mTitles) mTitles!![position] else ""
    }


    //刷新fragment
    private fun setFragments(fm: FragmentManager, fragments: List<Fragment>, mTitles: List<String>) {
        this.mTitles = mTitles
        if (this.fragmentList != null) { //判断fragmentList不为空
            var ft = fm.beginTransaction()  //开启fragment栈
            for (f in this.fragmentList) {  //遍历fragmentList集合
                ft!!.remove(f)              //移除所有的fragment
            }
            ft.commitAllowingStateLoss()    //？？？ 也是关闭
            fm.executePendingTransactions() //？？？也是清除
        }
        this.fragmentList = fragments      //重新赋值
        notifyDataSetChanged()
    }
}