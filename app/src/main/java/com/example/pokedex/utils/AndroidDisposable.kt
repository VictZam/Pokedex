package com.example.pokedex.utils

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class AndroidDisposable {

    private var compositeDisposable: CompositeDisposable? = null

    fun add(disposable: Disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = CompositeDisposable()
        }
        compositeDisposable?.add(disposable)
    }

    fun dispose() {
        compositeDisposable?.clear()
        compositeDisposable = null
    }

}