package com.tk.kotlinmvp.ui.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import cn.bingoogolapple.bgabanner.BGABanner
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.tk.kotlinmvp.R
import com.tk.kotlinmvp.durationFormat
import com.tk.kotlinmvp.ui.activity.VideoDetailActivity
import com.tk.kotlinmvp.utils.Constants
import com.tk.kotlinmvp.view.recyclerview.ViewHolder
import com.tk.kotlinmvp.view.recyclerview.adapter.CommonAdapter
import com.tk.ktlinmvp.mvp.model.bean.HomeBean
import io.reactivex.Observable


/**
 *Zhaolei
 *时间:2018/5/3
 */
class HomeAdapter(context: Context, data: ArrayList<HomeBean.Issue.Item>) : CommonAdapter<HomeBean.Issue.Item>(context, data, -1) {

    // banner 作为 RecyclerView 的第一项
    var bannerItemSize = 0

    companion object {
        private val ITEM_TYPE_BANNER = 1 //Banner类型
        private val ITEM_TYPE_TEXT_HEADER = 2   //textHeader
        private val ITEM_TYPE_CONTENT = 3 //item
    }

    override fun bindData(holder: ViewHolder, data: HomeBean.Issue.Item, position: Int) {
        when (getItemViewType(position)) {
            ITEM_TYPE_BANNER -> {
                val bannerItemData: ArrayList<HomeBean.Issue.Item> = mData.take(bannerItemSize).toCollection(ArrayList())
                val bannerFeedList = ArrayList<String>()
                val bannerTitleList = ArrayList<String>()
                Observable.fromIterable(bannerItemData)
                        .subscribe({ list ->
                            bannerFeedList.add(list.data?.cover?.feed ?: "")
                            bannerTitleList.add(list.data?.title ?: "")
                        })
                with(holder) {
                    getView<BGABanner>(R.id.banner).run {
                        setAutoPlayAble(bannerFeedList.size > 1)
                        setData(bannerFeedList, bannerTitleList)
                        setAdapter(object : BGABanner.Adapter<ImageView, String> {
                            override fun fillBannerItem(p0: BGABanner?, imageView: ImageView?, feedImageUrl: String?, p3: Int) {
                                if (imageView != null) {
                                    Glide.with(mContext)
                                            .load(feedImageUrl)
                                            .transition(DrawableTransitionOptions().crossFade())
                                            .into(imageView)
                                }

                            }
                        })

                    }
                    getView<BGABanner>(R.id.banner).run {
                        setAutoPlayAble(bannerFeedList.size > 1)
                        setData(bannerFeedList, bannerTitleList)
                        setAdapter(object : BGABanner.Adapter<ImageView, String> {
                            override fun fillBannerItem(bgaBanner: BGABanner?, imageView: ImageView?, feedImageUrl: String?, position: Int) {
                                if (imageView != null) {
                                    Glide.with(mContext)
                                            .load(feedImageUrl)
                                            .transition(DrawableTransitionOptions().crossFade())
                                            .into(imageView)
                                }
                            }
                        })
                    }

                    //没有使用到的参数在 kotlin 中用"_"代替
                    holder.getView<BGABanner>(R.id.banner).setDelegate { _, imageView, _, i ->
                        goToVideoPlayer(mContext as Activity, imageView, bannerItemData[i])
                    }

                }
            }
            ITEM_TYPE_CONTENT -> {
                setVideoItem(holder, mData[position + bannerItemSize - 1])
            }
            ITEM_TYPE_TEXT_HEADER -> {
                holder.setText(R.id.tvHeader, mData[position + bannerItemSize - 1].data?.text ?: "")
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            ITEM_TYPE_BANNER -> ViewHolder(inflaterView(R.layout.item_home_banner, parent!!))
            ITEM_TYPE_TEXT_HEADER -> ViewHolder(inflaterView(R.layout.item_home_header, parent!!))
            else -> ViewHolder(inflaterView(R.layout.item_home_content, parent!!))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            position == 0 ->         //第一条目为banner    后面固定的为textHeader
                ITEM_TYPE_BANNER
            mData[position + bannerItemSize - 1].type == "textHeader" ->
                ITEM_TYPE_TEXT_HEADER
            else ->
                ITEM_TYPE_CONTENT
        }
    }

    fun setBannerSize(count: Int) {
        bannerItemSize = count
    }

    fun addItemData(itemList: ArrayList<HomeBean.Issue.Item>) {
        this.mData.addAll(itemList)
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int {
        return when {
            mData.size > bannerItemSize -> mData.size - bannerItemSize + 1  //判断banner返回数量
            mData.isEmpty() -> 0                                            //如果为空就返回为0
            else -> 1                                                       //所有都不是为1
        }
    }

    /**
     * 加载布局
     */
    private fun inflaterView(mLayoutId: Int, parent: ViewGroup): View {
        //创建view
        val view = mInflater?.inflate(mLayoutId, parent, false)
        return view!!
    }

    /**
     * 跳转到视频详情页面播放
     *
     * @param activity
     * @param view
     */
    private fun goToVideoPlayer(activity: Activity, view: View, itemData: HomeBean.Issue.Item) {
        val intent = Intent(activity, VideoDetailActivity::class.java) //跳转播放页面
        intent.putExtra(Constants.BUNDLE_VIDEO_DATA, itemData)   //播放数据进行传输
        intent.putExtra(VideoDetailActivity.Companion.TRANSITION, true) //是否为透明色
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            val pair = Pair<View, String>(view, VideoDetailActivity.Companion.IMG_TRANSITION)
            val activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    activity, pair)
            ActivityCompat.startActivity(activity, intent, activityOptions.toBundle())
        } else {
            activity.startActivity(intent)
            activity.overridePendingTransition(R.anim.anim_in, R.anim.anim_out)  //设置跳转动画
        }
    }

    /**
     * 加载 content item
     */
    private fun setVideoItem(holder: ViewHolder, item: HomeBean.Issue.Item) {
        val itemData = item.data
        val defAvatar = R.mipmap.default_avatar
        val cover = itemData?.cover?.feed
        var avatar = itemData?.author?.icon
        var tagText: String? = "#"

        // 作者出处为空，就显获取提供者的信息
        if (avatar.isNullOrEmpty()) {
            avatar = itemData?.provider?.icon
        }
        Glide.with(mContext)
                .load(cover)
                .transition(DrawableTransitionOptions().crossFade())  //决定图片加载完成如何从占位符图片(或者之前的图片)过渡。
                .into(holder.getView(R.id.iv_cover_feed))

        // 如果提供者信息为空，就显示默认
        if (avatar.isNullOrEmpty()) {
            Glide.with(mContext)
                    .load(defAvatar)
                    .transition(DrawableTransitionOptions().crossFade())
                    .into(holder.getView(R.id.iv_avatar))

        } else {
            Glide.with(mContext)
                    .load(avatar)
                    .transition(DrawableTransitionOptions().crossFade())
                    .into(holder.getView(R.id.iv_avatar))
        }
        holder.setText(R.id.tv_title, itemData?.title ?: "")

        //遍历标签
        itemData?.tags?.take(4)?.forEach {
            tagText += (it.name + "/")
        }
        // 格式化时间
        val timeFormat = durationFormat(itemData?.duration)

        tagText += timeFormat

        holder.setText(R.id.tv_tag, tagText!!)

        holder.setText(R.id.tv_category, "#" + itemData?.category)

        holder.setOnItemClickListener(listener = View.OnClickListener {
            goToVideoPlayer(mContext as Activity, holder.getView(R.id.iv_cover_feed), item)
        })


    }

}