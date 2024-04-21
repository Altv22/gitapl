package com.example.gitapl.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.Menu
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.appcompat.app.AppCompatActivity
import com.example.gitapl.databinding.ActivityMainBinding
import com.example.gitapl.detailpage.DetailAct
import com.example.gitapl.mod.User
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.gitapl.favourite.FavouriteActivity
import com.example.gitapl.R
import com.example.gitapl.Theme.SettingActivity
import com.example.gitapl.Theme.SettingPreferences


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: UserAdapter

    private val viewModel by viewModels<UserViewModel> {
        UserViewModel.Factory(SettingPreferences(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d("MainActivity", "onCreate: started")

        viewModel.getTheme().observe(this) {
            if (it) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()
        adapter.setOnItemClickCall(object : UserAdapter.OnItemClickCall {
            override fun onItemClicked(data: User) {
                Intent(this@MainActivity, DetailAct::class.java).also {
                    it.putExtra(DetailAct.EXTRA_USER, data.login)
                    it.putExtra(DetailAct.EXTRA_ID, data.id)
                    it.putExtra(DetailAct.EXTRA_URL, data.avatar_url)
                    startActivity(it)
                }
            }
        })

        binding.apply {
            recyclerviewuser.layoutManager = LinearLayoutManager(this@MainActivity)
            recyclerviewuser.setHasFixedSize(true)
            recyclerviewuser.adapter = adapter

            buttonSearch.setOnClickListener {
                search()
            }

            edittextQuery.setOnKeyListener { v, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    search()
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }
        }
        viewModel.getUser().observe(this, {
            if (it != null) {
                adapter.setList(it)
                loading(false)
            }
        })

    }

    private fun search() {
        binding.apply {
            val query = edittextQuery.text.toString()
            if (query.isEmpty()) return
            loading(true)
            viewModel.setSearch(query)
        }
    }

    private fun loading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.opt_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favourite_menu -> {
                Intent(this, FavouriteActivity::class.java).also {
                    startActivity(it)
                }
            }
            R.id.themeToggle -> {
                Intent(this, SettingActivity::class.java).apply {
                    startActivity(this)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}