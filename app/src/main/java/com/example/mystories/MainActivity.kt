package com.example.mystories


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.mystories.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        isLogin()
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
        }
        return true
    }

    private fun isLogin(){
        lifecycleScope.launchWhenCreated {
            launch {
                viewModel.getToken().collect() {
                    if (it.isNullOrEmpty()){
                        Intent(this@MainActivity, AuthenticationActivity::class.java).also { intent ->
                            startActivity(intent)
                            finish()
                        }
                    }
                    else{

                    }
                }
            }
        }
    }

    companion object {
        const val TOKEN = "token"
    }
}