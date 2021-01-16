package com.tapaafandi.githubsearchuserapi.ui

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.tapaafandi.githubsearchuserapi.R
import com.tapaafandi.githubsearchuserapi.adapters.UsersAdapter
import com.tapaafandi.githubsearchuserapi.data.network.models.Users
import com.tapaafandi.githubsearchuserapi.databinding.ActivityMainBinding
import com.tapaafandi.githubsearchuserapi.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: UsersAdapter
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_GithubSearchUserApi)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showWelcome(true)

        adapter = UsersAdapter()
        adapter.notifyDataSetChanged()

        showRecyclerViewList()

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            MainViewModel::class.java)

        viewModel.getUsers().observe(this, { userItems ->
            if (userItems != null) {
                adapter.setData(userItems)
                showLoading(false, "")
                showWelcome(false)
                showRecyclerView(true)
            }
        })
    }

    private fun showRecyclerViewList() {
        binding.apply {
            rvSearchUserItem.layoutManager = LinearLayoutManager(this@MainActivity)
            rvSearchUserItem.setHasFixedSize(true)
            rvSearchUserItem.adapter = adapter
        }

        adapter.setOnItemClickCallback(object : UsersAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Users) {
                Intent(this@MainActivity, DetailUserActivity::class.java).apply {
                    putExtra(DetailUserActivity.EXTRA_DETAIL, data)
                    startActivity(this)
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.search_bar_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search_menu).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    showRecyclerView(false)
                    showLoading(true, query)
                    showWelcome(false)
                    viewModel.searchUser(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.search_menu -> {
                true
            }
            R.id.change_language -> {
                Intent(Settings.ACTION_LOCALE_SETTINGS).apply {
                    startActivity(this)
                }
                true
            }
            else -> {
                false
            }
        }
    }

    private fun showLoading(state: Boolean, query: String) {
        if (state) {
            binding.pbSearchUser.visibility = View.VISIBLE
            binding.tvSearching.visibility = View.VISIBLE
            binding.tvSearching.text = getString(R.string.searching_for, query)
        } else {
            binding.pbSearchUser.visibility = View.INVISIBLE
            binding.tvSearching.visibility = View.INVISIBLE
        }
    }

    private fun showWelcome(state: Boolean) {
        if (state) {
            binding.tvWelcome.visibility = View.VISIBLE
            binding.ivGithubLogo.visibility = View.VISIBLE
        } else {
            binding.tvWelcome.visibility = View.INVISIBLE
            binding.ivGithubLogo.visibility = View.INVISIBLE
        }
    }

    private fun showRecyclerView(state: Boolean) {
        if (state) {
            binding.rvSearchUserItem.visibility = View.VISIBLE
        } else {
            binding.rvSearchUserItem.visibility = View.INVISIBLE
        }
    }
}