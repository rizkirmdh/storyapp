package com.example.mystories

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.mystories.databinding.ActivityDetailBinding
import com.example.mystories.local.entity.Story
import com.example.mystories.remote.StoryItem
import com.example.mystories.utils.setDate

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val storyItemData = intent.getParcelableExtra<Story>(DETAIL)

        setStory(storyItemData)
    }

    private fun setStory(storyItem: Story?){
        if (storyItem != null){
            binding.apply {
                detailName.text = storyItem.name
                detailDate.setDate(storyItem.createdAt)
                detaiDesc.text = storyItem.desc

                Glide
                    .with(this@DetailActivity)
                    .load(storyItem.photoUrl)
                    .into(detailImage)

            }
        }
    }

    companion object {
        const val DETAIL = "detail"
    }
}