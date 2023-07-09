package com.example.daggerhilt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.daggerhilt.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var postAdapter: PostAdapter

    val viewModel by lazy {
        ViewModelProvider(this,defaultViewModelProviderFactory).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        prepareRecyclerView()

        viewModel.getLiveDataObserver()?.observe(this, object :Observer<List<Post>>{
            override fun onChanged(t: List<Post>?) {
                if (t !=null) {
                    postAdapter.setList(t)
                    postAdapter.notifyDataSetChanged()
                }
            }

        })

        viewModel.loadData()
    }


    private fun prepareRecyclerView() {
        val recyclerView = binding.recyclerView
        postAdapter = PostAdapter()
        recyclerView.adapter = postAdapter
       recyclerView.layoutManager = LinearLayoutManager(applicationContext)
    }

}