package com.tk.kotlinmvp.ui.fragment

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.orhanobut.logger.Logger
import com.scwang.smartrefresh.header.MaterialHeader
import com.tk.kotlinmvp.R
import com.tk.kotlinmvp.base.BaseFragment
import com.tk.kotlinmvp.mvp.contract.HomeContract
import com.tk.kotlinmvp.net.exception.ErrorStatus
import com.tk.kotlinmvp.ui.activity.SearchActivity
import com.tk.kotlinmvp.ui.adapter.HomeAdapter
import com.tk.kotlinmvp.utils.StatusBarUtil
import com.tk.ktlinmvp.mvp.model.bean.HomeBean
import com.tk.mykotlin.mvp.presenter.HomePresenter
import kotlinx.android.synthetic.main.fragment_home.*
import java.text.SimpleDateFormat
import java.util.*

@Suppress("DEPRECATION")
/**
 *Zhaolei
 *时间:2018/4/20
 */
class HomeFragment : BaseFragment(), HomeContract.View {

    private val mPresenter by lazy { HomePresenter() }
    private var num: Int = 1
    private var mTitle: String? = null
    private var isRefresh = false
    private var mHomeAdapter: HomeAdapter? = null
    private var loadingMore = false

    override fun getLayoutId(): Int = R.layout.fragment_home

    companion object {
        fun getInstance(title: String): HomeFragment {
            var fragment = HomeFragment()
            var bundle = Bundle()
            fragment.arguments = bundle
            fragment.mTitle = title
            return fragment
        }
    }

    init {
        mPresenter.attachView(this) //注册View
    }

    private var mMaterialHeader: MaterialHeader? = null

    override fun initView() {
        //内容跟随偏移
        mmRefreshLayout.setEnableHeaderTranslationContent(true)
        mmRefreshLayout.setOnRefreshListener {
            isRefresh = true
            mPresenter.requestHomeData(num) //刷新条目
        }
        mMaterialHeader = mmRefreshLayout.refreshHeader as MaterialHeader?
        //打开下拉刷新区域块背景:
        mMaterialHeader?.setShowBezierWave(true)
        //设置下拉刷新主题颜色
        mmRefreshLayout.setPrimaryColorsId(R.color.color_light_black, R.color.abc_search_url_text_selected)


        mmRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {  //判断是否滑动到底部，然后自动加载
                    val childCount = mmRecyclerView.childCount
                    val itemCount = mmRecyclerView.layoutManager.itemCount
                    val firstVisibleItem = (mmRecyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                    if (firstVisibleItem + childCount == itemCount) {
                        if (!loadingMore) {
                            loadingMore = true
                            mPresenter.loadMoreData()
                        }
                    }
                }
            }

            //RecyclerView滚动的时候调用
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val currentVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition()  //根据管理器拿到第一条可见条目
                if (currentVisibleItemPosition == 0) {  //为第一条时背景颜色设备透明
                    //背景设置为透明
                    toolbar.setBackgroundColor(getColor(R.color.color_translucent))
                    iv_search.setImageResource(R.mipmap.ic_action_search_white)
                    tv_header_title.text = ""
                } else {
                    if (mHomeAdapter?.mData!!.size > 1) {    //当数据集中长度大于1时
                        toolbar.setBackgroundColor(getColor(R.color.color_title_bg))  //设置为灰色
                        iv_search.setImageResource(R.mipmap.ic_action_search_black)
                        val itemList = mHomeAdapter!!.mData
                        val item = itemList[currentVisibleItemPosition + mHomeAdapter!!.bannerItemSize - 1]
                        if (item.type == "textHeader") {
                            tv_header_title.text = item.data?.text
                        }
                    }
                }
            }
        })

        iv_search.setOnClickListener {
            openSearchActivity()
        }

        mLayoutStatusView = multipleStatusView

        //状态栏透明和间距处理
        StatusBarUtil.darkMode(activity)
        StatusBarUtil.setPaddingSmart(activity, toolbar)
    }


    private val linearLayoutManager by lazy {
        LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
    }

    //获取固定时间的格式
    private val simpleDateFormat by lazy {
        SimpleDateFormat("- MMM. dd, 'Brunch' -", Locale.ENGLISH)
    }


    override fun lazyLoad() {
        mPresenter.requestHomeData(num)
    }

    override fun showLoading() {
        if (!isRefresh) {
            isRefresh = false
            mLayoutStatusView?.showLoading()
        }
    }

    override fun dismissLoading() {
        mmRefreshLayout.finishRefresh()
    }

    override fun setHomeData(homeBean: HomeBean) {
        mLayoutStatusView?.showContent()  //设置状态
        Logger.d(homeBean)

        // Adapter
        mHomeAdapter = HomeAdapter(activity!!, homeBean.issueList[0].itemList)
        //设置 banner 大小
        mHomeAdapter?.setBannerSize(homeBean.issueList[0].count)

        mmRecyclerView.adapter = mHomeAdapter
        mmRecyclerView.layoutManager = linearLayoutManager
        mmRecyclerView.itemAnimator = DefaultItemAnimator()
    }

    override fun setMoreData(itemList: ArrayList<HomeBean.Issue.Item>) {
        loadingMore = false
        mHomeAdapter?.addItemData(itemList)
    }

    override fun showError(msg: String, errorCode: Int) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()
        if (errorCode == ErrorStatus.NETWORK_ERROR) {
            mLayoutStatusView?.showNoNetwork()
        } else {
            mLayoutStatusView?.showError()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }

    fun getColor(colorId: Int): Int {
        return resources.getColor(colorId)
    }

    private fun openSearchActivity() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, iv_search, iv_search.transitionName)//跳转过滤动画
            startActivity(Intent(activity, SearchActivity::class.java), options.toBundle())
        } else {
            startActivity(Intent(activity, SearchActivity::class.java))
        }
    }
}