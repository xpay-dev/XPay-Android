package com.xpayworld.payment.ui.activation;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.xpayworld.payment.R;
import com.xpayworld.payment.ui.base.BaseActivity;

public class ActivationActivity extends BaseActivity{

    EditText text;


    @Override
    public void initViews() {
        text.addTextChangedListener(new onChanged());


    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_activation_code;
    }



    class onChanged implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }

}
