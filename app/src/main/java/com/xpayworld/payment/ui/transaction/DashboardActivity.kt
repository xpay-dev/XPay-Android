package com.xpayworld.payment.ui.transaction

import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController

import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.xpayworld.payment.R
import com.xpayworld.payment.ui.base.kt.BaseActivitykt
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.toolbar_main.*



class DashboardActivity  : BaseActivitykt() , DrawerLocker{


    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navController: NavController
    private lateinit var toolbar : Toolbar
    private lateinit var toogle :  ActionBarDrawerToggle

    override fun getLayout(): Int {
        return  R.layout.activity_dashboard
    }
    override fun initView() {


        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title =""

        drawerLayout = drawer_layout

        navController = findNavController( R.id.nav_host_fragment)
        setupWithNavController(nav_view, navController)

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            toolbar_title.text = controller.currentDestination?.label
        }
        setUpDrawerToggle()
    }

    private fun setUpDrawerToggle() {
        toogle = object : ActionBarDrawerToggle(this, drawerLayout, toolbar,
            R.string.openDrawer, R.string.closeDrawer) {

    }
        drawerLayout.addDrawerListener(toogle)
        toogle.syncState()
    }
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        drawerEnabled(true)
    }

    // to disable / hide humberger menu
    override fun drawerEnabled(enabled: Boolean) {
        val lockMode = if (enabled) DrawerLayout.LOCK_MODE_UNLOCKED else DrawerLayout.LOCK_MODE_LOCKED_CLOSED
        drawerLayout.setDrawerLockMode(lockMode)
        toogle.isDrawerIndicatorEnabled = enabled
    }
}