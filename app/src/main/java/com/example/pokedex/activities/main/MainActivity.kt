package com.example.pokedex.activities.main

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.pokedex.R
import com.example.pokedex.databinding.ActivityMainBinding
import com.example.pokedex.preferences
import com.example.pokedex.subscriptions
import com.example.pokedex.utils.extensions.addTo
import com.example.pokedex.utils.setProgressDialog
import com.example.pokedex.viewModels.MainViewModel
import com.jakewharton.rxbinding2.view.RxView
import java.util.concurrent.TimeUnit

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

        RxView.clicks(binding.tvCloseSession)
            .throttleFirst(1L, TimeUnit.SECONDS)
            .subscribe {
                viewModel.finishSession()
                finish()
            }.addTo(subscriptions)

        showMsg()
    }

    fun showMsg() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.app_name)
        builder.setMessage("Bienvenid@ ${preferences.userName}")
        builder.setPositiveButton(android.R.string.yes) { dialogThis, _ ->
            dialogThis.dismiss()
        }
        builder.show()
    }

}