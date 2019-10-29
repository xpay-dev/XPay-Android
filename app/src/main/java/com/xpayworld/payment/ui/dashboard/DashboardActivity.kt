package com.xpayworld.payment.ui.dashboard

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

import android.view.MotionEvent
import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.content.pm.PackageManager
import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.navigation.NavArgument
import androidx.navigation.fragment.findNavController
import androidx.navigation.navArgs
import com.google.gson.Gson
import com.xpayworld.payment.ui.link.LinkFragment
import com.xpayworld.payment.util.transaction
import com.xpayworld.sdk.EntryPoint
import com.xpayworld.sdk.XPAY_REQUEST
import com.xpayworld.sdk.XpayRequest


class DashboardActivity : BaseActivity(), DrawerLocker, ToolbarDelegate {
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

       getLinkEntryPoint()
    }

    private fun setUpDrawerToggle() {
        toogle = object : ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.openDrawer, R.string.closeDrawer) {}
        drawerLayout.addDrawerListener(toogle)
        toogle.syncState()
    }

    private fun getLinkEntryPoint(){

        val extras = intent.extras
        val request = extras?.getString(XPAY_REQUEST) ?: return

        val b = Bundle()
        b.putString(XPAY_REQUEST,request)
        navController.navigate(R.id.linkFragment,b)
        drawerEnabled(false)
        showToolbar(false)

//      navController.graph.startDestination =
//
//      navHostFragment.findNavController().navigate(R.id.linkFragment)

//      val gson = Gson()
//
//      val data = gson.fromJson(request, XpayRequest::class.java)
//
//      data ?: return
//
////    val args =  NavArgument.Builder().setDefaultValue(request).build()
////    graph.addArgument(XPAY_REQUEST ,args)

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

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (currentFocus != null) {
            val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        shouldSetToFullScreen()
        return super.dispatchTouchEvent(ev)
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

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//    }
}