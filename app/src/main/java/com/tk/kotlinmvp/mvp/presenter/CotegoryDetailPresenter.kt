package com.tk.kotlinmvp.mvp.presenter

import com.tk.kotlinmvp.base.BasePresenter
import com.tk.kotlinmvp.mvp.contract.CategoryDetailContract
import com.tk.kotlinmvp.mvp.model.CategoryDetailModel

/**
 *Zhaolei
 *时间:2018/5/27
 */
class CotegoryDetailPresenter : BasePresenter<CategoryDetailContract.View>(), CategoryDetailContract.Presenter {
    private val model by lazy { CategoryDetailModel() }


    private var nextPageUrl: String? = null

    override fun getCategoryDetailList(id: Long) {
        checkViewAttched()
        val subscribe = model.getCategoryDetalList(id).subscribe({ issue ->

            mRootView?.apply {
                nextPageUrl = issue.nextPageUrl
                setCateDetailList(issue.itemList)
            }
        })

        addSubscription(subscribe)
    }

    override fun loadMoreData() {
        val disposable = nextPageUrl?.let {
            model.loadMoreData(it)
                    .subscribe({ issue ->
                        mRootView?.apply {
                            nextPageUrl = issue.nextPageUrl
                            setCateDetailList(issue.itemList)
                        }
                    }, { throwable ->
                        mRootView?.apply {
                            showError(throwable.toString())
                        }
                    })
        }

        disposable?.let { addSubscription(it) }
    }


}