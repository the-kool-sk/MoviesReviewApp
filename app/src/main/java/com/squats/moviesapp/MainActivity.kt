package com.squats.moviesapp
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.snackbar.Snackbar
import com.squats.moviesapp.utility.ConnectionLiveData
import kotlinx.android.synthetic.main.activity_main.*




class MainActivity : AppCompatActivity(){

    private lateinit var connectionLiveData: ConnectionLiveData
    private lateinit var navController: NavController

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
        val snakbar = Snackbar.make(drawer_layout, getString(R.string.network_not_available_notification), Snackbar.LENGTH_INDEFINITE)
        connectionLiveData.observe(this, Observer { isConnected ->
            isConnected?.let {
                if (!it)
                    snakbar.show()
                else
                    snakbar.dismiss()
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
            super.onBackPressed()
        }
    }
}
