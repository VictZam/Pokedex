package com.example.pokedex.fragments

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
import com.example.pokedex.databinding.ActivityMainBinding
import com.example.pokedex.databinding.FragmentPokemonListBinding
import com.example.pokedex.models.PokemonModel
import com.example.pokedex.subscriptions
import com.example.pokedex.utils.ItemOffsetDecoration
import com.example.pokedex.utils.extensions.addTo
import com.example.pokedex.utils.setProgressDialog
import com.example.pokedex.utils.showProgressDialog
import com.example.pokedex.utils.toPx
import com.example.pokedex.viewModels.LoginViewModel
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
        viewModel.getPokemonList()
    }

    private fun initRecycler() {
        binding.rvPokemonList.hasFixedSize()
        binding.rvPokemonList.layoutManager = GridLayoutManager(context, 3)
        binding.rvPokemonList.addItemDecoration(ItemOffsetDecoration(8.toPx()))
        binding.rvPokemonList.itemAnimator = DefaultItemAnimator()
        binding.rvPokemonList.adapter = adapter

        adapter.onClickItem().subscribe(this::showPokemonDetail).addTo(subscriptions)
    }

    fun addElement(item: PokemonModel) {
        adapter.addElement(item)
    }

    fun showPokemonDetail(item: PokemonModel) {

    }

    fun onProgressRequestChange(show: Boolean) {
        showProgressDialog(show)
    }

    fun onFail(msg: String) {

    }

}