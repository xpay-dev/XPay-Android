package com.xpayworld.payment.ui.history


import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.rxbinding2.widget.RxTextView
import com.xpayworld.payment.R
import com.xpayworld.payment.network.transLookUp.TransResponse
import com.xpayworld.payment.ui.base.kt.BaseFragment
import com.xpayworld.payment.util.InjectorUtil
import kotlinx.android.synthetic.main.fragment_preference.recyclerView
import kotlinx.android.synthetic.main.fragment_transaction_history.*
import java.util.concurrent.TimeUnit

class TransactionHistoryFragment : BaseFragment() {

    private val viewModel: TransactionHistoryViewModel by viewModels {
        InjectorUtil.provideTransactionHistoryViewModel(requireContext())
    }
    override fun getLayout(): Int {
        return R.layout.fragment_transaction_history
    }

    private var adapter = TransactionHistoryAdapter()

    override fun initView(view: View, container: ViewGroup?) {
        recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter

      val search =  RxTextView.textChanges(edSearch)
              .debounce(300 ,TimeUnit.MILLISECONDS)
              .map { charSequence ->
                  charSequence.toString()}

       viewModel.callTransLookUp1(search)


        viewModel.loadingVisibility.observe(this, Observer {
            if (it) showProgress() else hideProgress()
        })

        viewModel.transResponse.observe(this , Observer {
            recyclerView.visibility = View.VISIBLE
            adapter.updatePostList(it)
            tvStatus.visibility = View.GONE
        })

        viewModel.requestError.observe(this , Observer {
            recyclerView.visibility = View.INVISIBLE
            tvStatus.visibility = View.VISIBLE
            tvStatus.text = it as String
        })

        viewModel.networkError.observe(this, Observer {
            showNetworkError(title = it)
        })

       // viewModel.callTransLookUp()
    }

}
