package com.example.pokedex.viewModels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.pokedex.models.PokemonDetaillModel
import com.example.pokedex.models.PokemonModel
import com.example.pokedex.repositories.PokemonListRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

class PokemonListViewModel : ViewModel() {

    lateinit var context: Context

    private val repository = PokemonListRepository()
    private val progressStatus: BehaviorSubject<Boolean> = BehaviorSubject.create()
    private val onFail: BehaviorSubject<String> = BehaviorSubject.create()

    private val requestSuccess = BehaviorSubject.create<PokemonModel>()


    fun observeProgressStatus(): BehaviorSubject<Boolean> {
        return progressStatus
    }

    fun observeFailLogin(): BehaviorSubject<String> {
        return onFail
    }

    fun getPokemonList(): Disposable =
        repository.getPokemonList()
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { progressStatus.onNext(true) }
            .doOnTerminate { progressStatus.onNext(false) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                getDetaill(it)
            }, {
                onFail.onNext("Ocurrio un error al obtener los datos.")
            })

    fun getDetaill(list: ArrayList<PokemonModel>) {
        list.forEach { pokemon ->
            repository.getPokemonDetaill(pokemon.name)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe { progressStatus.onNext(true) }
                .doOnTerminate { progressStatus.onNext(false) }
                .observeOn(AndroidSchedulers.mainThread())
                .map { setPokemonData(pokemon, it) }
                .subscribe({
                    requestSuccess.onNext(it)
                }, {
                    onFail.onNext("Ocurrio un error al obtener los datos.")
                })
        }
    }

    private fun setPokemonData(pokemon: PokemonModel, detaill: PokemonDetaillModel) : PokemonModel {
        return PokemonModel(pokemon.name, detaill)
    }

    fun onSuccessRequests(): BehaviorSubject<PokemonModel> =
        requestSuccess

}