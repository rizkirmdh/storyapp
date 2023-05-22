package com.example.mystories

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mystories.DetailActivity.Companion.DETAIL
import com.example.mystories.databinding.ItemStoryBinding
import com.example.mystories.remote.Story
import com.example.mystories.utils.setDate
import com.example.mystories.utils.setUrl

class StoryAdapter : ListAdapter<Story, StoryAdapter.ViewHolder>(DiffCallback) {

    class ViewHolder(private val binding: ItemStoryBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(context: Context, story: Story){
            binding.apply {
                tvName.text = story.name
                tvDate.setDate(story.createdAt)
                ivStoryImage.setUrl(context, story.photoUrl)

                root.setOnClickListener{
                    Intent(context, DetailActivity::class.java).also {
                        it.putExtra(DETAIL, story)
                        context.startActivity(it)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val story = getItem(position)
        holder.bind(holder.itemView.context, story)
    }

    companion object{
        private val DiffCallback = object : DiffUtil.ItemCallback<Story>(){
            override fun areItemsTheSame(oldItem: Story, newItem: Story): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Story, newItem: Story): Boolean {
                return oldItem == newItem
            }
        }
    }
}