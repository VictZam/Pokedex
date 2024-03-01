package com.example.pokedex.fragments.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pokedex.R
import com.example.pokedex.adapters.PokemonMoveAdapter
import com.example.pokedex.databinding.FragmentPokemonDetailDialogBinding
import com.example.pokedex.models.PokemonModel
import com.example.pokedex.subscriptions
import com.example.pokedex.utils.ItemOffsetDecoration
import com.example.pokedex.utils.extensions.addTo
import com.example.pokedex.utils.setImageUrl
import com.example.pokedex.utils.toPx
import com.jakewharton.rxbinding2.view.RxView
import java.util.concurrent.TimeUnit

class PokemonDetailDialog : DialogFragment() {

    companion object {
        var pokemon: PokemonModel? = null
    }

    fun newInstance(item: PokemonModel): PokemonDetailDialog {
        val fragment = PokemonDetailDialog()
        pokemon = item
        return fragment
    }

    private lateinit var binding: FragmentPokemonDetailDialogBinding
    lateinit var dialog: AlertDialog
    var adapter = PokemonMoveAdapter()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = FragmentPokemonDetailDialogBinding.inflate(LayoutInflater.from(context))
        val builder = AlertDialog.Builder(activity, R.style.AlertDialogCustom)
        builder.setView(binding.root)

        dialog = builder.create()
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(this.resources.getColor(R.color.whiteAlpha)))

        setUpUi()

        return dialog
    }

    fun setUpUi() {
        binding.ivFrom.setImageUrl(pokemon!!.detaill.sprites.frontDefault)
        binding.ivBack.setImageUrl(pokemon!!.detaill.sprites.backDefault)
        binding.tvName.text = pokemon?.name ?: "No encontrado"
        binding.tvNo.text = "#${pokemon?.detaill?.order ?: 0}"
        binding.tvExp.text = "Experiencia base: ${pokemon?.detaill?.baseExperience ?: 0}"

        RxView.clicks(binding.btnCancel)
            .throttleFirst(1L, TimeUnit.SECONDS)
            .subscribe {
                dialog.dismiss()
            }.addTo(subscriptions)

        initRecycler()
    }

    private fun initRecycler() {
        binding.rvPokemonList.hasFixedSize()
        binding.rvPokemonList.layoutManager = GridLayoutManager(context, 1)
        binding.rvPokemonList.addItemDecoration(ItemOffsetDecoration(8.toPx()))
        binding.rvPokemonList.itemAnimator = DefaultItemAnimator()
        binding.rvPokemonList.adapter = adapter

        adapter.addElements(pokemon!!.detaill.moves)
    }
}