package com.eugene_poroshin.money_manager.ui

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.eugene_poroshin.money_manager.R
import com.eugene_poroshin.money_manager.databinding.ActivityMainBinding
import com.eugene_poroshin.money_manager.ui.accounts.AccountsFragment
import com.eugene_poroshin.money_manager.ui.categories.CategoriesFragment
import com.eugene_poroshin.money_manager.ui.operations.OperationsFragment
import com.eugene_poroshin.money_manager.ui.statistics.StatisticsFragment

class MainActivity : AppCompatActivity(R.layout.activity_main), OnFragmentActionListener {

    private var binding: ActivityMainBinding? = null
    //зачем нулабельность?
    private var backPressed: Long = 0
    private val sharedPreferences: SharedPreferences by lazy { getPreferences(MODE_PRIVATE) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        openStartScreenIfNeeded() //splash screen on first visit
        binding = ActivityMainBinding.bind(findViewById(R.id.activity_main_root))
        initBottomNavigation()
        onOpenOperationsFragment()
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
                this, 
                R.string.backPressed_ru,
                Toast.LENGTH_SHORT
            ).show()
            backPressed = System.currentTimeMillis()
        }
    }

    private fun initBottomNavigation() {
        binding?.bottomNavigationView?.setOnNavigationItemSelectedListener { item ->
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

    private fun saveFirstVisit(state: Boolean) {
        sharedPreferences.edit()
            .putBoolean(SAVED_STATE, state)
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

    companion object {
        private const val SAVED_STATE = "SAVED_STATE"
    }
}