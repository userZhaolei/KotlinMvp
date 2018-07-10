package com.tk.kotlinmvp.ui.activity

import android.graphics.Color
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tk.kotlinmvp.R
import com.tk.kotlinmvp.base.BaseActivity
import com.tk.kotlinmvp.mvp.contract.CategoryDetailContract
import com.tk.kotlinmvp.mvp.model.bean.CategoryBean
import com.tk.kotlinmvp.mvp.presenter.CotegoryDetailPresenter
import com.tk.kotlinmvp.ui.adapter.CategoryDetailAdapter
import com.tk.kotlinmvp.utils.Constants
import com.tk.kotlinmvp.utils.StatusBarUtil
import com.tk.ktlinmvp.mvp.model.bean.HomeBean
import kotlinx.android.synthetic.main.activity_category_detail.*
import kotlinx.android.synthetic.main.fragment_home.*

class CategoryDetailActivity : BaseActivity(), CategoryDetailContract.View {

    private val mAdapter by lazy { CategoryDetailAdapter(this, itemList, R.layout.item_category_detail) }

    private var categoryData: CategoryBean? = null

    private var itemList = ArrayList<HomeBean.Issue.Item>()

    private var headerUrl: String? = null

    /**
     * 是否加载更多
     */
    private var loadingMore = false

    override fun showLoading() {
        multipleStatusView.showLoading()
    }

    override fun dismissLoading() {

    }

    override fun setCateDetailList(itemList: ArrayList<HomeBean.Issue.Item>) {
        loadingMore = false
        mAdapter.addData(itemList)
    }

    override fun showError(errorMsg: String) {
        multipleStatusView.showError()
    }

    private val mPresenter by lazy { CotegoryDetailPresenter() }

    init {
        mPresenter.attachView(this)
    }

    override fun initData() {
        categoryData = intent.getSerializableExtra(Constants.BUNDLE_CATEGORY_DATA) as CategoryBean?
        headerUrl = categoryData!!.headerImage
        tv_category_desc.text = "#${categoryData?.description}#"
        collapsing_toolbar_layout.title = categoryData?.name
        // 加载headerImage
        Glide.with(this)
                .load(headerUrl)
                .into(imageView)

    }

    override fun layoutId(): Int = R.layout.activity_category_detail

    override fun initView() {

        setSupportActionBar(toolbar1)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar1.setNavigationOnClickListener { finish() }

        collapsing_toolbar_layout.setExpandedTitleColor(Color.WHITE) //设置还没收缩时状态下字体颜色
        collapsing_toolbar_layout.setCollapsedTitleTextColor(Color.BLACK) //设置收缩后Toolbar上字体的颜色

        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.adapter = mAdapter
        //实现自动加载
        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val itemCount = mRecyclerView.layoutManager.itemCount
                val lastVisibleItem = (mRecyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                if (!loadingMore && lastVisibleItem == (itemCount - 1)) {
                    loadingMore = true
                    mPresenter.loadMoreData()
                }
            }
        })

        //状态栏透明和间距处理
        StatusBarUtil.darkMode(this)
        StatusBarUtil.setPaddingSmart(this, toolbar1)
    }

    override fun star() {
        //获取当前分类列表
        mPresenter.getCategoryDetailList(categoryData?.id!!)
    }

    override fun initListerent() {
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }


}
