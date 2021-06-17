package com.eugene_poroshin.money_manager.ui.operations

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.eugene_poroshin.money_manager.R
import com.eugene_poroshin.money_manager.databinding.ActivityAddOperationBinding
import org.koin.android.viewmodel.ext.android.viewModel

class AddOperationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddOperationBinding
    private val operationsViewModel: OperationsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_operation)
        binding.lifecycleOwner = this
        binding.operationsViewModel = operationsViewModel

        operationsViewModel.finishEvent.observe(this) {
            finish()
        }

        initToolbar()
        initRadioButtons()
    }

    private fun initToolbar() {
        binding.toolbarAddOperation.apply {
            inflateMenu(R.menu.add_operation_menu)
            setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
            setNavigationOnClickListener { onBackPressed() }
            setOnMenuItemClickListener {
                if (it.itemId == R.id.action_check) {
                    operationsViewModel.saveOperation()
                }
                true
            }
        }
    }

    private fun initRadioButtons() {
        //Destructuring
        binding.radioGroup.setOnCheckedChangeListener { group, _ ->
            val newTitle: String = when (group.checkedRadioButtonId) {
                R.id.radioButtonConsumption -> "Расход"
                R.id.radioButtonIncome -> "Доход"
                else -> throw IllegalArgumentException()
            }
            binding.toolbarAddOperation.title = newTitle
        }
    }
}