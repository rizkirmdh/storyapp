package com.example.mystories

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mystories.DetailActivity.Companion.DETAIL
import com.example.mystories.databinding.ItemStoryBinding
import com.example.mystories.databinding.LoadingStoryBinding
import com.example.mystories.local.entity.Story
import com.example.mystories.remote.StoriesResponse
import com.example.mystories.utils.setDate
import com.example.mystories.utils.setUrl

class StoryListAdapter : PagingDataAdapter<Story, StoryListAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(holder.itemView.context, data)
        }
    }

    class ViewHolder(private val binding: ItemStoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(context: Context, story: Story) {
            binding.apply {
                tvName.text = story.name
                tvDate.setDate(story.createdAt)
                ivStoryImage.setUrl(context, story.photoUrl)

                root.setOnClickListener {
                    Intent(context, DetailActivity::class.java).also { intent ->
                        intent.putExtra(DETAIL, story)
                        context.startActivity(intent)
                    }
                }
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Story>() {
            override fun areItemsTheSame(oldItem: Story, newItem: Story): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Story, newItem: Story): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}