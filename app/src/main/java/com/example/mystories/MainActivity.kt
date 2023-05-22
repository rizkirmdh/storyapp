package com.example.mystories


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mystories.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    private lateinit var rvStory: RecyclerView
    private lateinit var listAdapter: StoryAdapter

    private var token: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

       token = intent.getStringExtra(TOKEN)!!


        setStories()

        val linearLayoutManager = LinearLayoutManager(this)
        listAdapter = StoryAdapter()

        rvStory = binding.rvStories
        rvStory.apply {
            adapter = listAdapter
            layoutManager = linearLayoutManager
        }
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
                startActivity(Intent(this, MapsActivity::class.java))
            }
        }
        return true
    }

    private fun setStories(){
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED){
                viewModel.getStories(token).collect{ result ->
                    result.onSuccess {
                        val rvState = rvStory.layoutManager?.onSaveInstanceState()
                        listAdapter.submitList(it.stories)
                        rvStory.layoutManager?.onRestoreInstanceState(rvState)

                    }

                    result.onFailure {
                        Toast.makeText(
                            this@MainActivity,
                            getString(R.string.error_rv),
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }
            }
        }
    }

    companion object {
        const val TOKEN = "token"
    }
}