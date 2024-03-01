package com.example.pokedex.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.databinding.ItemPokemonListBinding
import com.example.pokedex.models.PokemonModel
import com.example.pokedex.utils.AndroidDisposable
import com.example.pokedex.utils.extensions.addTo
import com.example.pokedex.utils.setImageUrl
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class PokemonAdapter : RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {

    private var list = mutableListOf<PokemonModel>()
    private val subscriptions = AndroidDisposable()
    private val clickSubject = PublishSubject.create<PokemonModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val binding = ItemPokemonListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PokemonViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.bind(list[position])
    }

    fun addElements(pokemons: List<PokemonModel>) {
        list.addAll(pokemons)
        notifyDataSetChanged()
    }

    fun addElement(pokemon: PokemonModel) {
        list.add(pokemon)
        notifyDataSetChanged()
    }

    fun onClickItem() = clickSubject

    inner class PokemonViewHolder(private val binding : ItemPokemonListBinding) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var item: PokemonModel

        fun bind(item: PokemonModel) {
            binding.executePendingBindings()
            this.item = item
            if (adapterPosition > -1) {

                binding.tvTitle.text = item.name
                binding.ivItem.setImageUrl(item.detaill.sprites.frontDefault)

                RxView.clicks(binding.root)
                    .throttleFirst(1L, TimeUnit.SECONDS)
                    .subscribe {
                        clickSubject.onNext(item)
                    }
                    .addTo(subscriptions)

            }
        }

    }

}