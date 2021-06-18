package com.eugene_poroshin.money_manager.ui

import android.R
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.lifecycle.LiveData
import com.eugene_poroshin.money_manager.repo.database.AccountEntity
import com.eugene_poroshin.money_manager.repo.database.CategoryEntity


//@BindingAdapter("accounts")
//fun setAccounts(view: RecyclerView, accounts: List<AccountEntity>) {
//
//}

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

@InverseBindingAdapter(attribute = "categoriesSpinnerClicks")
fun getCategorySelectedItem(spinner: Spinner) = spinner.selectedItemPosition


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

@InverseBindingAdapter(attribute = "accountsSpinnerClicks")
fun getAccountSelectedItem(spinner: Spinner) = spinner.selectedItemPosition


@BindingAdapter("categoriesEntries")
fun setCategoriesList(spinner: Spinner, categoriesLiveData: LiveData<List<CategoryEntity>>) {
    categoriesLiveData.value?.let { spinner.setEntries(it.map { categoryList -> categoryList.name }) }
}

@BindingAdapter("accountsEntries")
fun setAccountsList(spinner: Spinner, accountsLiveData: LiveData<List<AccountEntity>>) {
    accountsLiveData.value?.let { spinner.setEntries(it.map { accountList -> accountList.name }) }
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
