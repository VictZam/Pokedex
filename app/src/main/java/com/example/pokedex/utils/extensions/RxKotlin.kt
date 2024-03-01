package com.example.pokedex.utils.extensions

import com.example.pokedex.utils.AndroidDisposable
import io.reactivex.disposables.Disposable

fun Disposable.addTo(androidDisposable: AndroidDisposable): Disposable = apply { androidDisposable.add(this) }