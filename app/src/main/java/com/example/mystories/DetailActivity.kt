package com.example.mystories

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.mystories.databinding.ActivityDetailBinding
import com.example.mystories.remote.Story
import com.example.mystories.utils.setDate

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val storyData = intent.getParcelableExtra<Story>(DETAIL)

        setStory(storyData)
    }

    private fun setStory(story: Story?){
        if (story != null){
            binding.apply {
                detailName.text = story.name
                detailDate.setDate(story.createdAt)
                detaiDesc.text = story.description

                Glide
                    .with(this@DetailActivity)
                    .load(story.photoUrl)
                    .into(detailImage)

            }
        }
    }

    companion object {
        const val DETAIL = "detail"
    }
}