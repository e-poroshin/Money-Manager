package com.eugene_poroshin.money_manager

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.eugene_poroshin.money_manager.accounts.AccountsFragment
import com.eugene_poroshin.money_manager.categories.CategoriesFragment
import com.eugene_poroshin.money_manager.fragments.OnFragmentActionListener
import com.eugene_poroshin.money_manager.operations.OperationsFragment
import com.eugene_poroshin.money_manager.statistics.StatisticsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), OnFragmentActionListener {

    private var backPressed: Long = 0
    private val sharedPreferences: SharedPreferences by lazy { getPreferences(MODE_PRIVATE) }

    override fun onCreate(savedInstanceState: Bundle?) {
        //todo splash screen
        openStartScreenIfNeeded()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initBottomNavigation()
        onOpenOperationsFragment()
    }

    private fun initBottomNavigation() {
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation_view)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_categories -> onOpenCategoriesFragment()
                R.id.action_accounts -> onOpenAccountsFragment()
                R.id.action_operations -> onOpenOperationsFragment()
                R.id.action_statistics -> onOpenStatisticsFragment()
            }
            true
        }
    }

    private fun openStartScreenIfNeeded() {
        val isFirstVisit: Boolean = sharedPreferences.getBoolean(SAVED_STATE, false)
        if (!isFirstVisit) {
            startActivity(Intent(this, StartActivity::class.java))
            saveFirstVisit(true)
        }
    }

    private fun saveFirstVisit(saveState: Boolean) {
        sharedPreferences.edit()
            .putBoolean(SAVED_STATE, saveState)
            .apply()
    }

    private fun openFragmentTab(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.fragmentContainer,
                fragment,
                fragment::class.java.simpleName
            )
            .commit()
    }

    override fun onOpenAccountsFragment() = openFragmentTab(AccountsFragment.getInstance())

    override fun onOpenOperationsFragment() = openFragmentTab(OperationsFragment.getInstance())

    override fun onOpenCategoriesFragment() = openFragmentTab(CategoriesFragment.getInstance())

    override fun onOpenStatisticsFragment() = openFragmentTab(StatisticsFragment.getInstance())

    override fun onBackPressed() {
        if (backPressed + 2000 > System.currentTimeMillis()) {
            super.onBackPressed()
            finish()
        } else {
            Toast.makeText(
                baseContext, R.string.backPressed_ru,
                Toast.LENGTH_SHORT
            ).show()
            backPressed = System.currentTimeMillis()
        }
    }

    companion object {
        private const val SAVED_STATE = "SAVED_STATE"
    }
}