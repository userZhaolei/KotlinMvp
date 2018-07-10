package com.tk.kotlinmvp.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import com.tk.kotlinmvp.R
import com.tk.kotlinmvp.base.BaseFragment
import com.tk.kotlinmvp.base.BaseFragmentAdapter
import com.tk.kotlinmvp.mvp.contract.HotTabContract
import com.tk.kotlinmvp.mvp.model.bean.TabInfoBean
import com.tk.kotlinmvp.mvp.presenter.HotTabPresenter
import com.tk.kotlinmvp.net.exception.ErrorStatus
import com.tk.kotlinmvp.showToast
import com.tk.kotlinmvp.utils.StatusBarUtil
import kotlinx.android.synthetic.main.fragment_hot.*

/**
 *Zhaolei
 *时间:2018/4/26
 */
class HotFragment : BaseFragment(), HotTabContract.View {

    /**
     * 存放 tab 标题
     */
    private val mTabTitleList = ArrayList<String>()
    private val mFragmentList = ArrayList<Fragment>()

    override fun showLoading() {
        multipleStatusView.showLoading()
    }

    override fun dismissLoading() {
    }

    override fun setTabInfo(tabInfoBean: TabInfoBean) {
        multipleStatusView.showContent()

        tabInfoBean.tabInfo.tabList.mapTo(mTabTitleList) { it.name }
        tabInfoBean.tabInfo.tabList.mapTo(mFragmentList) { RankFragment.getInstance(it.apiUrl) }

        mViewPager.adapter = BaseFragmentAdapter(childFragmentManager, mFragmentList, mTabTitleList)
        mTabLayout.setupWithViewPager(mViewPager)
    }

    override fun showErrow(errorMsg: String, errorCode: Int) {
        showToast(errorMsg)
        if (errorCode == ErrorStatus.NETWORK_ERROR) {
            multipleStatusView.showNoNetwork()
        } else {
            multipleStatusView.showError()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }


    private var mTitle: String? = null
    override fun getLayoutId(): Int = R.layout.fragment_hot

    private val mPresenter by lazy { HotTabPresenter() }

    companion object {
        fun getInstance(title: String): HotFragment {
            var fragment = HotFragment()
            var bundle = Bundle()
            fragment.arguments = bundle
            fragment.mTitle = title
            return fragment
        }
    }

    init {
        mPresenter.attachView(this)
    }

    override fun initView() {
        mLayoutStatusView = multipleStatusView
        //状态栏透明和间距处理
        StatusBarUtil.darkMode(activity)
        StatusBarUtil.setPaddingSmart(activity, mToolbar)
    }

    override fun lazyLoad() {
        mPresenter.getTabInfo()
    }
}