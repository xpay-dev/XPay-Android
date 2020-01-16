package com.xpayworld.payment.ui.transaction.shope


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xpayworld.payment.databinding.FragmentShopeBinding
import com.xpayworld.payment.ui.base.kt.BaseFragment
import kotlinx.android.synthetic.main.fragment_shope.*


/**
 * A simple [Fragment] subclass.
 */
class ShopeFragment : BaseFragment() {

    private var adapter = ShopeAdapter()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentShopeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initView(view: View, container: ViewGroup?) {

        val items = arrayListOf<Shoppe>(
                Shoppe("item1","150.00","",1,"PHP"),
                Shoppe("item2","100.00","",2,"PHP"),
                Shoppe("item3","20.00","",3,"PHP"),
                Shoppe("item4","200.00","",4,"PHP")

        )

        rcView.adapter = adapter
        adapter.onItemClick = { shope ->
            println(shope.amount)
        }
        adapter.updatePostList(items  )

    }
}
