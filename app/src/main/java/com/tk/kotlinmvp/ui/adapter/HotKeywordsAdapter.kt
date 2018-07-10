package com.tk.kotlinmvp.ui.adapter

import android.content.Context
import android.view.View
import android.widget.TextView
import com.google.android.flexbox.FlexboxLayoutManager
import com.tk.kotlinmvp.R
import com.tk.kotlinmvp.view.recyclerview.ViewHolder
import com.tk.kotlinmvp.view.recyclerview.adapter.CommonAdapter

/**
 *Zhaolei
 *时间:2018/6/14
 */
class HotKeywordsAdapter(mContext: Context, mList: ArrayList<String>, layoutId: Int) : CommonAdapter<String>(mContext, mList, layoutId) {
    /**
     * Kotlin的函数可以作为参数，写callback的时候，可以不用interface了
     */
    private var mOnTagItemClick: ((tag: String) -> Unit)? = null

    fun setOnTagItemClickListener(onTagItemClickListener: (tag: String) -> Unit) {
        this.mOnTagItemClick = onTagItemClickListener
    }

    override fun bindData(holder: ViewHolder, data: String, position: Int) {
        holder.setText(R.id.tv_title, data)
        val params = holder.getView<TextView>(R.id.tv_title).layoutParams
        if (params is FlexboxLayoutManager.LayoutParams) {
            params.flexGrow = 1.0f
        }

        holder.setOnItemClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                mOnTagItemClick?.invoke(data)
            }
        })
    }


}