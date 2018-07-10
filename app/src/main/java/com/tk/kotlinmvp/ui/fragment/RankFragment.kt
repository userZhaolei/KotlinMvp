package com.tk.kotlinmvp.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.tk.kotlinmvp.R
import com.tk.kotlinmvp.base.BaseFragment
import com.tk.kotlinmvp.mvp.contract.RankContract
import com.tk.kotlinmvp.mvp.presenter.RankPresenter
import com.tk.kotlinmvp.net.exception.ErrorStatus
import com.tk.kotlinmvp.showToast
import com.tk.kotlinmvp.ui.adapter.CategoryDetailAdapter
import com.tk.ktlinmvp.mvp.model.bean.HomeBean
import kotlinx.android.synthetic.main.fragment_rank.*

/**
 *Zhaolei
 *时间:2018/6/26
 */
class RankFragment : BaseFragment(), RankContract.View {

    private var apiUrl: String? = null

    private val mPresenter by lazy { RankPresenter() }

    private val mAdapter by lazy { CategoryDetailAdapter(activity, itemList, R.layout.item_category_detail) }

    private var itemList = ArrayList<HomeBean.Issue.Item>()

    companion object {
        fun getInstance(apiUrl: String): RankFragment {
            val fragment = RankFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            fragment.apiUrl = apiUrl
            return fragment
        }
    }

    init {
        mPresenter.attachView(this)
    }


    override fun getLayoutId(): Int = R.layout.fragment_rank

    override fun initView() {
        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        mRecyclerView.adapter = mAdapter

        mLayoutStatusView = multipleStatusView
    }

    override fun lazyLoad() {
        if (!apiUrl.isNullOrEmpty()) {
            mPresenter.requestRankList(apiUrl!!)
        }
    }


    override fun showLoading() {
        multipleStatusView.showLoading()
    }

    override fun dismissLoading() {
    }

    override fun setRankList(itemList: ArrayList<HomeBean.Issue.Item>) {
        multipleStatusView.showContent()

        mAdapter.addData(itemList)
    }

    override fun showError(errorMsg: String, errorCode: Int) {
        showToast(errorMsg)
        if (errorCode == ErrorStatus.NETWORK_ERROR) {
            multipleStatusView.showNoNetwork()
        } else {
            multipleStatusView.showError()
        }
    }

}