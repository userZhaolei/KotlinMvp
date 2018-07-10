package com.tk.mykotlin.mvp.presenter

import com.tk.kotlinmvp.base.BasePresenter
import com.tk.kotlinmvp.mvp.contract.HomeContract
import com.tk.kotlinmvp.mvp.model.HomeModel
import com.tk.kotlinmvp.net.exception.ExceptionHandle
import com.tk.ktlinmvp.mvp.model.bean.HomeBean


/**
 *Zhaolei
 *时间:2018/5/3
 */
class HomePresenter : BasePresenter<HomeContract.View>(), HomeContract.Presenter {
    private var bannerHomeBean: HomeBean? = null
    private var nextPageUrl: String? = null
    private val homeModel: HomeModel  by lazy {
        HomeModel()
    }
    /**
     * 获取首页精选数据 banner 加 一页数据
     */
    override fun requestHomeData(num: Int) {
        // 检测是否绑定 View
        checkViewAttched()
        mRootView?.showLoading()
        var dispoasble = homeModel.requestHomeData(num)
                .flatMap({ homeBean ->
                    //获取bannaer的集合数据
                    val bannerItemList = homeBean.issueList[0].itemList

                    //找出数据中的 类型为banner2 与horizontalScrollCard的然后从集合中把它移除掉
                    bannerItemList.filter { item ->
                        item.type == "banner2" || item.type == "horizontalScrollCard"
                    }.forEach { item ->
                        bannerItemList.remove(item)
                    }

                    bannerHomeBean = homeBean //记录第一页是当做 banner 数据

                    //根据 nextPageUrl 请求下一页数据
                    homeModel.loadMoreData(homeBean.nextPageUrl)

                }).subscribe(
                { homeBean ->
                    mRootView?.apply {
                        dismissLoading()
                        nextPageUrl = homeBean.nextPageUrl
                        var newBannerItemList = homeBean.issueList[0].itemList

                        newBannerItemList.filter { item ->
                            item.type == "banner2" || item.type == "horizontalScrollCard"
                        }.forEach { item ->
                            newBannerItemList.remove(item)
                        }

                        bannerHomeBean!!.issueList[0].count = bannerHomeBean!!.issueList[0].itemList.size
                        bannerHomeBean!!.issueList[0].itemList.addAll(newBannerItemList)

                        setHomeData(bannerHomeBean!!)
                    }
                }, { t ->
            mRootView?.apply {
                dismissLoading()
                showError(ExceptionHandle.handleException(t), ExceptionHandle.errorCode)
            }
        })
        addSubscription(dispoasble)
    }

    /**
     * 加载更多
     */

    override fun loadMoreData() {
        val disposable = nextPageUrl?.let {
            homeModel.loadMoreData(it)
                    .subscribe({ homeBean ->
                        mRootView?.apply {
                            //过滤掉 Banner2(包含广告,等不需要的 Type), 具体查看接口分析
                            val newItemList = homeBean.issueList[0].itemList

                            newItemList.filter { item ->
                                item.type == "banner2" || item.type == "horizontalScrollCard"
                            }.forEach { item ->
                                //移除 item
                                newItemList.remove(item)
                            }

                            nextPageUrl = homeBean.nextPageUrl
                            setMoreData(newItemList)
                        }

                    }, { t ->
                        mRootView?.apply {
                            showError(ExceptionHandle.handleException(t), ExceptionHandle.errorCode)
                        }
                    })


        }
        if (disposable != null) {
            addSubscription(disposable)
        }
    }


}