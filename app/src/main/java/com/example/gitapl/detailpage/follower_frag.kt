package com.example.gitapl.detailpage

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gitapl.R
import com.example.gitapl.databinding.FollowFragBinding
import com.example.gitapl.main.UserAdapter

class follower_frag: Fragment(R.layout.follow_frag) {

    private var _binding : FollowFragBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FollowerViewModel
    private lateinit var  adapter: UserAdapter
    private lateinit var username: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments
        username=args?.getString(DetailAct.EXTRA_USER).toString()
        _binding = FollowFragBinding.bind(view)

        adapter=UserAdapter()
        adapter.notifyDataSetChanged()

        binding.apply {
            recyclerviewuser.setHasFixedSize(true)
            recyclerviewuser.layoutManager = LinearLayoutManager(activity)
            recyclerviewuser.adapter = adapter
        }
        loading(true)
        viewModel = ViewModelProvider(this,ViewModelProvider.NewInstanceFactory()).get(FollowerViewModel::class.java)
        viewModel.setfollowerlist(username)
        viewModel.getfollowerslist().observe(viewLifecycleOwner, {
            if (it != null){
                adapter.setList(it)
                loading(false)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun loading(state: Boolean){
        if (state){
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}