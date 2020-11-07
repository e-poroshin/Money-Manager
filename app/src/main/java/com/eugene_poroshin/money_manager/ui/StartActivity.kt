package com.eugene_poroshin.money_manager.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.eugene_poroshin.money_manager.R
import com.eugene_poroshin.money_manager.databinding.ActivityStartBinding

class StartActivity : AppCompatActivity(R.layout.activity_start) {

    private var binding: ActivityStartBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartBinding.bind(findViewById(R.id.activity_start_root))
        binding?.buttonStart?.setOnClickListener { finish() }
    }
}