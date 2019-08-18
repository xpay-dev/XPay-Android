package com.xpayworld.payment.ui.transaction.processTransaction

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.NavArgs
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.xpayworld.payment.R
import com.xpayworld.payment.databinding.ActivityDashboardBinding
import com.xpayworld.payment.databinding.ActivityProcessTransaction2Binding
import kotlinx.android.synthetic.main.toolbar_main.*

class ProcessTransactionActivity : BaseDeviceActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityProcessTransaction2Binding = DataBindingUtil.setContentView(this,
                R.layout.activity_process_transaction2)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = ""

        navController = findNavController(R.id.nav_host_fragment)
        navController.setGraph(R.navigation.device_graph, intent.extras)
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            toolbar_title.text = controller.currentDestination?.label
        }
        toolbarTitle.observe(this , Observer {
            toolbar_title.text = it
        })
    }
}
