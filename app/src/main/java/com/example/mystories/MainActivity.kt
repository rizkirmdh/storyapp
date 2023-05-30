package com.example.mystories


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mystories.databinding.ActivityMainBinding
import com.example.mystories.local.entity.Story
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalPagingApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    private lateinit var rvStory: RecyclerView
    private lateinit var listAdapter: StoryListAdapter

    private var token: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        token = intent.getStringExtra(TOKEN)!!

        val linearLayoutManager = LinearLayoutManager(this)

        rvStory = binding.rvStories
        rvStory.layoutManager = linearLayoutManager

        setStories()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.btnLogout -> {
                viewModel.saveToken("")
                startActivity(Intent(this, AuthenticationActivity::class.java))
            }
            R.id.btnAdd -> {
                startActivity(Intent(this, AddStoryActivity::class.java))
            }
            R.id.btnMaps ->{
                Intent(this@MainActivity, MapsActivity::class.java).also { intent ->
                    intent.putExtra(TOKEN, token)
                    startActivity(intent)
                    finish()
                }
            }
        }
        return true
    }

    private fun setStories(){
        listAdapter = StoryListAdapter()
        rvStory.adapter = listAdapter.withLoadStateFooter(
            footer = LoadingStateAdapter{
                listAdapter.retry()
            }
        )
        viewModel.getStories(token).observe(this, {
            listAdapter.submitData(lifecycle, it)
        })
    }


    companion object {
        const val TOKEN = "token"
    }
}