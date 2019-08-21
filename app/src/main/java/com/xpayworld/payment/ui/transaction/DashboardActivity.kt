package com.xpayworld.payment.ui.transaction

import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.xpayworld.payment.R
import com.xpayworld.payment.databinding.ActivityDashboardBinding
import com.xpayworld.payment.ui.base.kt.BaseActivity
import kotlinx.android.synthetic.main.toolbar_main.*
import android.os.Build
import android.graphics.Color
import kotlinx.android.synthetic.main.fragment_enter_amount.*


class DashboardActivity : BaseActivity(), DrawerLocker , ToolbarDelegate {

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

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                // Set the content to appear under the system bars so that the
                // content doesn't resize when the system bars hide and show.
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                // Hide the nav bar and status bar
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
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

    override fun setTitle(title: String) {
        toolbar_title.text = title
    }
}