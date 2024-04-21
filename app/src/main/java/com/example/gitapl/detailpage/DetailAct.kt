package com.example.gitapl.detailpage

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.gitapl.databinding.ActivityDetailBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailAct : AppCompatActivity() {

    companion object{
        const val EXTRA_USER = "extra_username"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_URL = "extra_url"
    }

    private lateinit var binding: ActivityDetailBinding
    private lateinit var ViewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_USER)
        val id = intent.getIntExtra(EXTRA_ID,0)
        val avatarUrl = intent.getStringExtra(EXTRA_URL)
        val bundle = Bundle()
        bundle.putString(EXTRA_USER, username)

        ViewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        ViewModel.setDetail(username.toString())

        ViewModel.isLoading.observe(this){
            binding.progressBar.isVisible = it
            binding.avatarProgressBar.isVisible = it
            binding.followersProgressBar.isVisible = it
            binding.followersProgressBar.isVisible = it
        }

        ViewModel.getDetail().observe(this, {
            if (it != null) {
                binding.apply {
                    detailname.text = it.name
                    detailusername.text = it.login
                    followers.text = "${it.followers} Followers"
                    following.text = "${it.following} Following"
                    Glide.with(this@DetailAct)
                        .load(it.avatar_url)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .centerCrop()
                        .into(profilepicture)

                    avatarProgressBar.visibility = View.GONE
                    followingProgressBar.visibility = View.GONE
                    followersProgressBar.visibility = View.GONE
                }
            }
        })

        var _isChecked = false
        var count = 0

        CoroutineScope(Dispatchers.IO).launch {
            count = ViewModel.checkUser(id) as Int
            withContext(Dispatchers.Main) {
                if (count > 0) {
                    binding.favToggle.isChecked = true
                    _isChecked = true
                } else {
                    binding.favToggle.isChecked = false
                    _isChecked = false
                }
            }
        }

        binding.favToggle.setOnClickListener {
            _isChecked = !_isChecked
            if (_isChecked){
                ViewModel.addFavourite(username.toString(), id, avatarUrl.toString())
            } else {
                ViewModel.removeFromFavourite(id)
            }
            binding.favToggle.isChecked = _isChecked
        }

        val pageradapter = PagerAdapter(this, supportFragmentManager, bundle)
        binding.apply {
            viewPager.adapter = pageradapter
            tabs.setupWithViewPager(viewPager)
        }
    }
}
