package com.xpayworld.payment.ui.transaction

import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.xpayworld.payment.R

import com.xpayworld.payment.ui.base.kt.BaseActivitykt
import kotlinx.android.synthetic.main.activity_enter_amount.*
import kotlinx.android.synthetic.main.toolbar_main.*

class Dashboard  : BaseActivitykt(){

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    override fun initView() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title =""
        toolbar_title.text = "Transaction"


        val drawerNavController = Navigation.findNavController(this, R.id.nav_host_fragment)
        NavigationUI.setupWithNavController(nav_view, drawerNavController)
        drawerNavController.addOnDestinationChangedListener { controller, destination, arguments ->
        }
        setUpDrawerToggle()
    }


private fun setUpDrawerToggle() {
    val mDrawerToggle = object : ActionBarDrawerToggle(this, drawer_layout, toolbar,
            R.string.openDrawer, R.string.closeDrawer) {}
    drawer_layout.addDrawerListener(mDrawerToggle)
    mDrawerToggle.syncState()
    }

    override fun getLayout(): Int {
        return  R.layout.activity_enter_amount
    }
}