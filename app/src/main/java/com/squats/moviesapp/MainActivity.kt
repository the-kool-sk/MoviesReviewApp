package com.squats.moviesapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.squats.moviesapp.extentionfunctions.showSnackBar
import com.squats.moviesapp.utility.ConnectionLiveData
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var connectionLiveData: ConnectionLiveData
    private lateinit var navController: NavController
    var isConnected: MutableLiveData<Boolean> = MutableLiveData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        navController = findNavController(R.id.nav_host_fragment)
        setupActionBarWithNavController(navController)
        nav_view.setupWithNavController(navController)
        connectionLiveData = ConnectionLiveData(this)
        observeNetworkConnection()
    }

    private fun observeNetworkConnection() {
        connectionLiveData.observe(this, Observer { isConnected ->
            isConnected?.let {
                if (!it) {
                    this.isConnected.postValue(false)
                    drawer_layout.showSnackBar(getString(R.string.network_not_available_notification))
                } else {
                    this.isConnected.postValue(true)
                }
            }
        })
    }


    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
//            super.onBackPressed()
            finish()
        }
    }
}
