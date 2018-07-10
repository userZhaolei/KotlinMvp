package com.tk.kotlinmvp.ui.fragment

import android.graphics.Rect
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.tk.kotlinmvp.R
import com.tk.kotlinmvp.R.id.multipleStatusView
import com.tk.kotlinmvp.base.BaseFragment
import com.tk.kotlinmvp.mvp.contract.CotegoryContract
import com.tk.kotlinmvp.mvp.model.bean.CategoryBean
import com.tk.kotlinmvp.mvp.presenter.CotegoryPresenter
import com.tk.kotlinmvp.net.exception.ErrorStatus
import com.tk.kotlinmvp.showToast
import com.tk.kotlinmvp.ui.adapter.CategoryAdapter
import com.tk.kotlinmvp.utils.DisplayManager
import kotlinx.android.synthetic.main.fragment_category.*

/**
 *Zhaolei
 *时间:2018/5/20
 */
class CategoryFragment : BaseFragment(), CotegoryContract.View {
    override fun showLoading() {
        multipleStatusView.showLoading()
    }

    override fun dismissLoading() {
        multipleStatusView.showContent()
    }

    override fun showCategory(categoryList: ArrayList<CategoryBean>) {
        //  mPresenter.getCategoryData()
        mAdapter.setData(categoryList)
    }

    override fun showError(errorMsg: String, errorCode: Int) {
        showToast(errorMsg)
        if (errorCode == ErrorStatus.NETWORK_ERROR) {
            multipleStatusView?.showNoNetwork()
        } else {
            multipleStatusView?.showError()
        }
    }

    private val mPresenter by lazy { CotegoryPresenter() }

    private val mAdapter by lazy { CategoryAdapter(activity, mCategoryList, R.layout.item_category) }

    private var title: String? = null
    private var mCategoryList = ArrayList<CategoryBean>()

    companion object {
        fun getInstance(title: String): CategoryFragment {
            var fragment = CategoryFragment()
            var bundle = Bundle()
            fragment.arguments = bundle
            fragment.title = title
            return fragment
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_category


    override fun initView() {
        mPresenter.attachView(this)

        DisplayManager.init(activity)

        mLayoutStatusView = multipleStatusView

        mRecyclerView.adapter = mAdapter
        mRecyclerView.layoutManager = GridLayoutManager(activity, 2)
        /*    mRecyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {
                    val position = parent.getChildPosition(view)
                    val offset = DisplayManager.dip2px(2f)!!
                    //动态绘制矩形分割线
                    outRect.set(if (position % 2 == 0) 0 else offset, offset,
                            if (position % 2 == 0) offset else 0, offset)
                }

            })*/
        mRecyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
                super.getItemOffsets(outRect, view, parent, state)
                val position = parent!!.getChildPosition(view)
                val offset = DisplayManager.dip2px(5f)!!
                //动态绘制矩形分割线
                if (outRect != null) {
                    outRect.set(if (position % 2 == 0) 0 else offset, offset,
                            if (position % 2 == 0) offset else 0, offset)
                }
            }
        })

    }

    override fun lazyLoad() {
        //获取分类信息
        mPresenter.getCategoryData()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }

}