package com.xpayworld.payment.ui.transaction

import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.xpayworld.payment.R
import com.xpayworld.payment.databinding.ActivityDashboardBinding
import com.xpayworld.payment.ui.base.kt.BaseActivitykt
import kotlinx.android.synthetic.main.toolbar_main.*
import android.graphics.Point


class DashboardActivity : BaseActivitykt(), DrawerLocker , ToolbarDelegate{

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navController: NavController
    private lateinit var toolbar: Toolbar
    private lateinit var toogle: ActionBarDrawerToggle


    override fun initView() {

        val binding: ActivityDashboardBinding = DataBindingUtil.setContentView(this,
                R.layout.activity_dashboard)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = ""

        drawerLayout = binding.drawerLayout

        navController = findNavController(R.id.nav_host_fragment)

        binding.navigationView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            toolbar_title.text = controller.currentDestination?.label

        }
        setUpDrawerToggle()
    }

    private fun setUpDrawerToggle() {
        toogle = object : ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.openDrawer, R.string.closeDrawer) {}
        drawerLayout.addDrawerListener(toogle)
        toogle.syncState()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }

    override fun onBackPressed() {
        super.onBackPressed()
//        navController.popBackStack()
//        findNavController().popBackStack()
//        drawerEnabled(true)
    }

    // to disable / hide hamburger menu
    override fun drawerEnabled(enabled: Boolean) {
        val lockMode = if (enabled) DrawerLayout.LOCK_MODE_UNLOCKED else DrawerLayout.LOCK_MODE_LOCKED_CLOSED
        drawerLayout.setDrawerLockMode(lockMode)
        toogle.isDrawerIndicatorEnabled = enabled
    }
    override fun showToolbar(visible: Boolean) {
        if (visible) supportActionBar?.show() else supportActionBar?.hide()
        drawerEnabled(visible)
    }
}