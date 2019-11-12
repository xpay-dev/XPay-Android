package com.xpayworld.payment.ui.activation

import android.os.AsyncTask
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.lifecycle.Observer
import com.xpayworld.payment.R
import com.xpayworld.payment.ui.base.kt.BaseFragment
import com.xpayworld.payment.ui.dashboard.DrawerLocker
import kotlinx.android.synthetic.main.fragment_activation_code.*
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import com.xpayworld.payment.util.InjectorUtil
import androidx.navigation.fragment.findNavController
import com.xpayworld.payment.data.Transaction
import com.xpayworld.payment.databinding.FragmentActivationCodeBinding
import com.xpayworld.payment.databinding.FragmentEnterPinBinding
import kotlinx.coroutines.launch


class ActivationFragment : BaseFragment() {

    var edtextList = listOf<EditText>()
    var strCode = ""

    private val viewModel: ActivationViewModel by viewModels {
        InjectorUtil.provideActivationViewModelFactor(requireContext())
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentActivationCodeBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun initView(view: View, container: ViewGroup?) {

        edtextList = listOf(edtext1, edtext2, edtext3, edtext4)
        edtextList.forEach { it.addTextChangedListener(OnChangedEditText()) }

    viewModel.addTransaction()

        AsyncTask.execute {
            val transRepository = Transaction(
                    amount = 1002.00,
                    cardData = "asdasdddd",
                    currency = "PHPsss",
                    transNumber = "sdas",
                    transDate = "ddd",
                    merchantName =  "dasda",
                    posEntry = 1
            )

            InjectorUtil.getTransactionRepository(requireContext()).createTransaction(transRepository)
        }

        val  trans =  InjectorUtil.getTransactionRepository(requireContext()).getTransaction()

        trans.value?.forEach {
            Log.e("Trasaction",it.cardData)

        }
//        ioThread {
//            getInstance(context).dataDao()
//                    .insert(PREPOPULATE_DATA)
//        }

        btnActivate.setOnClickListener{
             viewModel.callActivationAPI(strCode)
        }

        viewModel.loadingVisibility.observe(this, Observer{
            isShow -> if (isShow == true) showProgress() else hideProgress()
        })
        viewModel.toolbarVisibility.observe(this, Observer{(activity as DrawerLocker).drawerEnabled(it)})

        viewModel.requestError.observe(this, Observer {
           if (it is Boolean) {
               edtextList.forEach { editText -> editText.setBackgroundResource(R.drawable.tv_error)}
               errorGroup.visibility = View.VISIBLE
           }
           else{
               errorGroup.visibility = View.GONE
               edtextList.forEach { editText -> editText.setBackgroundResource(R.drawable.tv_activation)}
           }
        })
        viewModel.networkError.observe(this, Observer {
            showNetworkError()
        })

        viewModel.navigateToEnterPin.observe(this , Observer {
            val direction = ActivationFragmentDirections.actionActiviationFragmentToEnterPinFragment()
            findNavController().navigate(direction)
        })


    }

    internal inner class OnChangedEditText : TextWatcher {
        override fun onTextChanged(char: CharSequence?, p1: Int, p2: Int, count: Int) {
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(p0: Editable?) {
            strCode = ""
            if (edtext1.isFocused && edtext1.text.length == 4) {
                edtext2.requestFocus()
            }
            if (edtext2.isFocused && edtext2.text.length == 4) {
                edtext3.requestFocus()
            }
            if (edtext3.isFocused && edtext3.text.length == 4) {
                edtext4.requestFocus()
            }
            edtextList.forEach { action: EditText ->
                strCode += action.text.toString()
            }

            btnActivate.isEnabled = strCode.length == 16
        }
    }

}




