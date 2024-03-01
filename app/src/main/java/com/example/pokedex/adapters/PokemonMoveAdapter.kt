package com.example.pokedex.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.databinding.ItemPokemonMoveBinding
import com.example.pokedex.models.PokemonMovesModel
import com.example.pokedex.utils.AndroidDisposable
import io.reactivex.subjects.PublishSubject

class PokemonMoveAdapter : RecyclerView.Adapter<PokemonMoveAdapter.PokemonMoveViewHolder>() {

    private var list = mutableListOf<PokemonMovesModel>()
    private val clickSubject = PublishSubject.create<PokemonMovesModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonMoveViewHolder {
        val binding = ItemPokemonMoveBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PokemonMoveViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: PokemonMoveViewHolder, position: Int) {
        holder.bind(list[position])
    }

    fun addElements(pokemons: List<PokemonMovesModel>) {
        list.addAll(pokemons)
        notifyDataSetChanged()
    }

    fun addElement(pokemon: PokemonMovesModel) {
        list.add(pokemon)
        notifyDataSetChanged()
    }

    inner class PokemonMoveViewHolder(private val binding : ItemPokemonMoveBinding) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var item: PokemonMovesModel

        fun bind(item: PokemonMovesModel) {
            binding.executePendingBindings()
            this.item = item
            if (adapterPosition > -1) {
                binding.tvTitle.text = item.move.name
            }
        }

    }

}