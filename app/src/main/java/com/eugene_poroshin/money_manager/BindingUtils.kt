package com.eugene_poroshin.money_manager

import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.recyclerview.widget.RecyclerView
import com.eugene_poroshin.money_manager.repo.database.AccountEntity


@BindingAdapter("accounts")
fun setAccounts(view: RecyclerView, accounts: List<AccountEntity>) {
    //test
}

@BindingAdapter("categoriesSpinnerClicksAttrChanged")
fun onCategoryItemSelect(spinner: Spinner, listener: InverseBindingListener) {
    spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) { }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            listener.onChange()
        }
    }
}

@BindingAdapter("categoriesSpinnerClicks")
fun setCategorySelectedItem(spinner: Spinner, position: Int) {
    CategoryPosition.NEW = position
    if (!isSameCategoryPosition()) {
        spinner.setSelection(position)
        CategoryPosition.CURRENT = position
    }
}

fun isSameCategoryPosition() = CategoryPosition.CURRENT == CategoryPosition.NEW

@BindingAdapter("categoriesSpinnerClicks")
fun getCategorySelectedItem(spinner: Spinner) = spinner.selectedItemPosition

object CategoryPosition {
    var CURRENT: Int = 0
    var NEW: Int = 1
}


@BindingAdapter("accountsSpinnerClicksAttrChanged")
fun onAccountItemSelect(spinner: Spinner, listener: InverseBindingListener) {
    spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) { }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            listener.onChange()
        }
    }
}

@BindingAdapter("accountsSpinnerClicks")
fun setAccountSelectedItem(spinner: Spinner, position: Int) {
    AccountPosition.NEW = position
    if (!isSameAccountPosition()) {
        spinner.setSelection(position)
        AccountPosition.CURRENT = position
    }
}

fun isSameAccountPosition() = AccountPosition.CURRENT == AccountPosition.NEW

@BindingAdapter("accountsSpinnerClicks")
fun getAccountSelectedItem(spinner: Spinner) = spinner.selectedItemPosition

object AccountPosition {
    var CURRENT: Int = 0
    var NEW: Int = 1
}