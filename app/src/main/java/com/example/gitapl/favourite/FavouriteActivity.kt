package com.example.gitapl.favourite

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gitapl.R
import com.example.gitapl.databinding.ActivityFavouriteBinding
import com.example.gitapl.detailpage.DetailAct
import com.example.gitapl.local.Favourite
import com.example.gitapl.main.UserAdapter
import com.example.gitapl.mod.User

class FavouriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavouriteBinding
    private lateinit var adapter: UserAdapter
    private lateinit var viewModel: FavouriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavouriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        viewModel = ViewModelProvider(this).get(FavouriteViewModel::class.java)

        adapter.setOnItemClickCall(object : UserAdapter.OnItemClickCall {
            override fun onItemClicked(data: User) {
                Intent(this@FavouriteActivity, DetailAct::class.java).also {
                    it.putExtra(DetailAct.EXTRA_USER, data.login)
                    it.putExtra(DetailAct.EXTRA_ID, data.id)
                    it.putExtra(DetailAct.EXTRA_URL, data.avatar_url)
                    startActivity(it)
                }
            }
        })

        binding.apply {
            recyclerviewuser.setHasFixedSize(true)
            recyclerviewuser.layoutManager = LinearLayoutManager(this@FavouriteActivity)
            recyclerviewuser.adapter = adapter
        }
        viewModel.getFavouriteUser()?.observe(this,{
            if (it!=null){
                val list = mapList(it)
                adapter.setList(list)
            }
        })

        }
    private fun mapList(users:List<Favourite>): ArrayList<User> {
        val listUsers = ArrayList<User>()
        for(user in users){
            val userMapped = User(
                user.login,
                user.id,
                user.avatar_url
            )
            listUsers.add(userMapped)
        }
        return listUsers
    }
    }
