package com.eugene_poroshin.money_manager

import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.eugene_poroshin.money_manager.repo.database.AccountEntity


@BindingAdapter("accounts")
fun setAccounts(view: RecyclerView, accounts: List<AccountEntity>) {
    //второй способ для recyclerview?
}

@BindingAdapter("spinnerClicks")
fun listenClicks(spinner: Spinner, result: MutableLiveData<Int>) {
    spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {

        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            result.value = parent?.getPositionForView(view)
        }
    }
}