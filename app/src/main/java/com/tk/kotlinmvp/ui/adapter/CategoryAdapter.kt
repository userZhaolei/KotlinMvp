package com.tk.kotlinmvp.ui.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.tk.kotlinmvp.MyApplication
import com.tk.kotlinmvp.R
import com.tk.kotlinmvp.mvp.model.bean.CategoryBean
import com.tk.kotlinmvp.ui.activity.CategoryDetailActivity
import com.tk.kotlinmvp.utils.Constants
import com.tk.kotlinmvp.view.recyclerview.ViewHolder
import com.tk.kotlinmvp.view.recyclerview.adapter.CommonAdapter

/**
 *Zhaolei
 *时间:2018/5/20
 */
class CategoryAdapter(mContext: Context, categoryList: ArrayList<CategoryBean>, layoutId: Int) :
        CommonAdapter<CategoryBean>(mContext, categoryList, layoutId) {

    private var textTypeface: Typeface? = null

    init {
        //设置字体
        textTypeface = Typeface.createFromAsset(MyApplication.context.assets, "fonts/FZLanTingHeiS-DB1-GB-Regular.TTF")
    }

    override fun bindData(holder: ViewHolder, data: CategoryBean, position: Int) {

        holder.setText(R.id.tv_category_name, "#${data.name}")
        //设置方正兰亭细黑简体
        holder.getView<TextView>(R.id.tv_category_name).typeface = textTypeface

        holder.setImagePath(R.id.iv_category, object : ViewHolder.HolderImageLoader(data.bgPicture) {
            override fun loadImage(iv: ImageView, path: String) {
                Glide.with(mContext)
                        .load(path)
                        .transition(DrawableTransitionOptions().crossFade())
                        .thumbnail(0.5f)
                        .into(iv)
            }
        })

        holder.setOnItemClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                val intent = Intent(mContext as Activity, CategoryDetailActivity::class.java)
                intent.putExtra(Constants.BUNDLE_CATEGORY_DATA, data)
                mContext.startActivity(intent)
            }
        })
    }
    /**
     * 设置新数据
     */
    fun setData(categoryList: ArrayList<CategoryBean>){
        mData.clear()
        mData = categoryList
        notifyDataSetChanged()
    }


}