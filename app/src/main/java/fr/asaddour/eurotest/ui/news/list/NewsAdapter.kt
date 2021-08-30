package fr.asaddour.eurotest.ui.news.list

import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fr.asaddour.eurotest.R
import fr.asaddour.eurotest.ui.utils.TimeAgo
import org.joda.time.DateTime

class NewsAdapter(
    private val onClick: (Item, Int) -> Unit
) : ListAdapter<NewsAdapter.Item, RecyclerView.ViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(
            R.layout.row_news,
            parent,
            false
        )
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when (getItemViewType(position)){
            ROW_STORY -> (holder as NewsViewHolder).bind(item as Item.Story)
            ROW_VIDEO -> (holder as NewsViewHolder).bind(item as Item.Video)
        }
        holder.itemView.setOnClickListener { onClick(item, position) }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is Item.Story -> ROW_STORY
            is Item.Video -> ROW_VIDEO
        }

    }

    private class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val image: ImageView = itemView.findViewById(R.id.newsImage)
        val videoIcon: ImageView = itemView.findViewById(R.id.videoIcon)
        val sportLabel: TextView = itemView.findViewById(R.id.sportName)
        val title: TextView = itemView.findViewById(R.id.title)
        val subTitle: TextView = itemView.findViewById(R.id.subTitle)

        fun bind(item: Item.Story){
            Glide.with(itemView.context).load(item.image).into(image)
            sportLabel.text = item.sport
            title.text = item.title
            subTitle.text = run {
                val author = itemView.context.getString(R.string.by_author, item.author)
                val date = TimeAgo.getTimeAgo(item.date.millis)
                itemView.context.getString(R.string.pair_with_dash, author, date)
            }
            videoIcon.visibility = GONE
        }

        fun bind(item: Item.Video){
            Glide.with(itemView.context).load(item.thumb).into(image)
            sportLabel.text = item.sport
            title.text = item.title
            subTitle.text = itemView.context.getString(R.string.views, item.views)
            videoIcon.visibility = VISIBLE
        }

    }

    sealed interface Item {

        data class Story(
            val id: Int,
            val date: DateTime,
            val sport: String,
            val title: String,
            val author: String,
            val image: String,
        ) : Item

        data class Video(
            val id: Int,
            val sport: String,
            val title: String,
            val thumb: String,
            val url: String,
            val views: String
        ) : Item

    }

    companion object {

        private const val ROW_STORY = 0
        private const val ROW_VIDEO = 1

        private val diffCallback = object : DiffUtil.ItemCallback<Item>() {
            override fun areItemsTheSame(oldItem: Item, newItem: Item) = when (oldItem) {
                is Item.Story -> when (newItem) {
                    is Item.Story -> oldItem.id == newItem.id
                    is Item.Video -> false
                }
                is Item.Video -> when (newItem) {
                    is Item.Story -> false
                    is Item.Video -> oldItem.id == newItem.id
                }
            }

            override fun areContentsTheSame(oldItem: Item, newItem: Item) = oldItem == newItem

        }
    }



}

