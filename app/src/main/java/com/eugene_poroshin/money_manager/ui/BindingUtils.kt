package com.eugene_poroshin.money_manager.ui

import android.R
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.recyclerview.widget.RecyclerView
import com.eugene_poroshin.money_manager.repo.database.AccountEntity
import com.eugene_poroshin.money_manager.repo.database.CategoryEntity


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
    if (spinner.selectedItemPosition != position) {
        spinner.setSelection(position)
    }
}

@BindingAdapter("categoriesSpinnerClicks")
fun getCategorySelectedItem(spinner: Spinner, position: Int) = spinner.selectedItemPosition


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
    if (spinner.selectedItemPosition != position) {
        spinner.setSelection(position)
    }
}

@BindingAdapter("accountsSpinnerClicks")
fun getAccountSelectedItem(spinner: Spinner, position: Int) = spinner.selectedItemPosition


@BindingAdapter("categoriesEntries")
fun setCategoriesList(spinner: Spinner, value: List<CategoryEntity>) {
    spinner.setEntries(value.map { categoryList -> categoryList.name })
}

@BindingAdapter("accountsEntries")
fun updateAccountsSpinner(spinner: Spinner, value: List<AccountEntity>) {
    spinner.setEntries(value.map { accountList -> accountList.name })
}

private fun Spinner.setEntries(entries: List<String>) {
    val adapterAccounts = ArrayAdapter(
        context,
        R.layout.simple_spinner_item,
        entries
    )
    adapterAccounts.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
    adapter = adapterAccounts
    setSelection(0)
}
