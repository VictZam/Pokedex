package com.example.pokedex.activities.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.pokedex.R
import com.example.pokedex.databinding.ActivityLoginBinding
import com.example.pokedex.subscriptions
import com.example.pokedex.utils.extensions.addTo
import com.example.pokedex.utils.setProgressDialog
import com.example.pokedex.utils.showProgressDialog
import com.example.pokedex.viewModels.LoginViewModel
import com.jakewharton.rxbinding2.view.RxView

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        viewModel.context = this
        setProgressDialog(this)

        setUpUi()
        subcribers()
    }

    fun setUpUi() {
        window.statusBarColor = resources.getColor(R.color.redTop)

        RxView.clicks(binding.btnLogin)
            .subscribe {
                doLogin()
            }.addTo(subscriptions)
    }

    fun doLogin() {
        viewModel.doLogin(
            binding.etUsername.text.toString(),
            binding.etPassword.text.toString(),
        )
    }

    fun subcribers() {
        viewModel.observeProgressStatus().subscribe(this::onProgressRequestChange)
            .addTo(subscriptions)
        viewModel.observeSuccessLogin().subscribe(this::onLoginSuccess).addTo(subscriptions)
        viewModel.observeFailLogin().subscribe(this::onFail).addTo(subscriptions)
    }

    fun onProgressRequestChange(show: Boolean) {
        showProgressDialog(show)
    }

    fun onLoginSuccess(user: String) {
        viewModel.openMainActivity(user)
        finish()
    }

    fun onFail(msg: String) {
        viewModel.showFailError(msg, binding)
    }

}