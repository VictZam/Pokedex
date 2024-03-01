package com.example.pokedex.viewModels

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.example.pokedex.R
import com.example.pokedex.activities.main.MainActivity
import com.example.pokedex.databinding.ActivityLoginBinding
import com.example.pokedex.preferences
import com.example.pokedex.repositories.LoginRepository
import com.example.pokedex.utils.extensions.forceFocus
import com.example.pokedex.utils.extensions.hideKeyboard
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

class LoginViewModel : ViewModel() {

    lateinit var context: Context

    private val repository = LoginRepository()
    private val progressStatus: BehaviorSubject<Boolean> = BehaviorSubject.create()
    private val onSucess: BehaviorSubject<String> = BehaviorSubject.create()
    private val onFail: BehaviorSubject<String> = BehaviorSubject.create()

    fun observeProgressStatus(): BehaviorSubject<Boolean> {
        return progressStatus
    }

    fun doLogin(user: String, password: String) =
        repository.doLogin(user, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { progressStatus.onNext(true) }
            .doOnTerminate { progressStatus.onNext(false) }
            .subscribe({
                onSucess.onNext(it)
            }, {
                Log.e("login-error", it.message.toString())
                progressStatus.onNext(false)
                onFail.onNext(context.getString(R.string.log_in_error))
            })

    fun observeSuccessLogin(): BehaviorSubject<String> {
        return onSucess
    }

    fun observeFailLogin(): BehaviorSubject<String> {
        return onFail
    }

    fun showFailError(msg: String, binding: ActivityLoginBinding) {
        binding.textInputLayoutUser.setPasswordVisibilityToggleTintList(
            AppCompatResources.getColorStateList(context, android.R.color.white)
        )
        binding.textInputLayoutUser.isErrorEnabled = true
        binding.textInputLayoutUser.error = msg
        binding.textInputLayoutUser.setHintTextAppearance(R.style.CustomTextInputError)

        binding.textInputLayoutUser.setHintTextAppearance(R.style.CustomTextInputError)
        forceFocus(binding.txtInputPassword)
        binding.etUsername.background =
            ContextCompat.getDrawable(context, R.drawable.edittext_selector)
        hideKeyboard(context)
    }

    fun openMainActivity(user: String) {
        preferences.userName = user
        val intent = Intent(context, MainActivity::class.java)
        context.startActivity(intent)
    }

    fun validateIsLogin() {
        if(preferences.userName != null) {
            onSucess.onNext(preferences.userName ?: "No encontrado")
        }
    }


}