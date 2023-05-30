package com.example.mystories

import com.example.mystories.local.entity.Story

object DataDummy {

    fun generateDummyListStory(): List<Story> {
        val lists = arrayListOf<Story>()

        for (i in 0..20) {
            val story = Story(
                id = "story-FvU4u0Vp2S3PMsFg",
                name = "Dimas",
                desc = "Lorem Ipsum",
                photoUrl = "https://story-api.dicoding.dev/images/stories/photos-1641623658595_dummy-pic.png",
                createdAt = "2022-01-08T06:34:18.598Z",
                lon = -16.002,
                lat = -10.212
            )
            lists.add(story)
        }

        return lists
    }
}