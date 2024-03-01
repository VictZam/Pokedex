package com.example.pokedex.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pokedex.R
import com.example.pokedex.adapters.PokemonAdapter
import com.example.pokedex.databinding.FragmentPokemonListBinding
import com.example.pokedex.fragments.dialogs.PokemonDetailDialog
import com.example.pokedex.models.PokemonModel
import com.example.pokedex.preferences
import com.example.pokedex.subscriptions
import com.example.pokedex.utils.ItemOffsetDecoration
import com.example.pokedex.utils.extensions.addTo
import com.example.pokedex.utils.hasInternet
import com.example.pokedex.utils.setProgressDialog
import com.example.pokedex.utils.showProgressDialog
import com.example.pokedex.utils.toPx
import com.example.pokedex.viewModels.PokemonListViewModel

class PokemonListFragment : Fragment() {

    fun newInstance(title: String): PokemonListFragment {
        val args = Bundle()
        args.putString("Title", title)
        val fragment = PokemonListFragment()
        fragment.arguments = args
        return fragment
    }

    private lateinit var binding: FragmentPokemonListBinding
    lateinit var viewModel: PokemonListViewModel
    var adapter = PokemonAdapter()
    var pokemonList = ArrayList<PokemonModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPokemonListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[PokemonListViewModel::class.java]
        viewModel.context = requireActivity()
        setProgressDialog(requireActivity())

        setUpUi()
        subcribers()
        loadData()
    }

    fun setUpUi() {
        initRecycler()
    }

    fun subcribers() {
        viewModel.observeProgressStatus().subscribe(this::onProgressRequestChange)
            .addTo(subscriptions)
        viewModel.observeFailLogin().subscribe(this::onFail).addTo(subscriptions)
        viewModel.onSuccessRequests().subscribe(this::addElement).addTo(subscriptions)
    }

    fun loadData() {
        pokemonList.clear()
        if(ifConected()) {
            viewModel.getPokemonList()
            preferences.deleteArrayList("pokemons")
        }
        else{
            try {
                addElements(preferences.getArrayList("pokemons"))
            } catch (e: Exception) {}
        }
    }

    private fun initRecycler() {
        binding.rvPokemonList.hasFixedSize()
        binding.rvPokemonList.layoutManager = GridLayoutManager(context, 3)
        binding.rvPokemonList.addItemDecoration(ItemOffsetDecoration(8.toPx()))
        binding.rvPokemonList.itemAnimator = DefaultItemAnimator()
        binding.rvPokemonList.adapter = adapter

        adapter.onClickItem().subscribe(this::showPokemonDetail).addTo(subscriptions)
    }

    fun addElements(list: ArrayList<PokemonModel>) {
        adapter.addElements(list)
    }
    fun addElement(item: PokemonModel) {
        adapter.addElement(item)
        pokemonList.add(item)
        preferences.saveArrayList(pokemonList, "pokemons")
    }

    fun showPokemonDetail(item: PokemonModel) {
        val dialog = PokemonDetailDialog().newInstance(item)
        dialog.show(activity!!.supportFragmentManager, "Calidad raíz")
    }

    fun onProgressRequestChange(show: Boolean) {
        showProgressDialog(show)
    }

    fun onFail(msg: String) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(R.string.app_name)
        builder.setMessage(msg)
        builder.setPositiveButton(android.R.string.yes) { dialogThis, _ ->
            dialogThis.dismiss()
        }
        builder.show()
    }

    fun ifConected(): Boolean {
        return if (requireContext().hasInternet())
            true
        else {
            onFail("Se requiere conexión a internet para obtener nuevos datos.")
            false
        }
    }



}