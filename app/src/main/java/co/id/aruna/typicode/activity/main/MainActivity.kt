package co.id.aruna.typicode.activity.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import co.id.aruna.typicode.R
import co.id.aruna.typicode.data.adapter.UserAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            MainViewModel::class.java
        )

        setSupportActionBar(findViewById(R.id.toolbar))
        searcUser.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    mainViewModel.searchTitle(query)
                } else {
                    mainViewModel.loadUser(applicationContext)
                }
                return false
            }
        })
        setSupportActionBar(findViewById(R.id.toolbar))

        mainViewModel.initDatabase(applicationContext)
        mainViewModel.loadUser(applicationContext)
        mainViewModel.getListUser.observe(this, Observer {
            rvUser.adapter = UserAdapter(applicationContext, it)
        })
        rvUser.layoutManager = LinearLayoutManager(this)

        swipeRefresh.setOnRefreshListener {
            mainViewModel.loadUser(applicationContext)
            swipeRefresh.isRefreshing = false
        }
    }
}