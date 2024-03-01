package com.example.pokedex.activities.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.pokedex.R
import com.example.pokedex.databinding.ActivityMainBinding
import com.example.pokedex.preferences
import com.example.pokedex.utils.setProgressDialog
import com.example.pokedex.viewModels.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.context = this
        setProgressDialog(this)

        setUpUi()
    }

    fun setUpUi() {
        binding.tvName.text = "Nombre: ${preferences.userName}"
        window.statusBarColor = resources.getColor(R.color.redTop)
        viewModel.getMenuByRoll(binding.bottomNavigationView)
        viewModel.initBottonNavigation(binding, supportFragmentManager)
    }

}