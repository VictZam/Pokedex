package com.example.pokedex.viewModels

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import com.example.pokedex.R
import com.example.pokedex.activities.login.LoginActivity
import com.example.pokedex.activities.main.MainActivity
import com.example.pokedex.databinding.ActivityMainBinding
import com.example.pokedex.fragments.PokemonListFragment
import com.example.pokedex.preferences
import com.simform.custombottomnavigation.Model
import com.simform.custombottomnavigation.SSCustomBottomNavigation

class MainViewModel : ViewModel() {

    lateinit var context: Context

    fun getMenuByRoll(menu: SSCustomBottomNavigation) {
        menu.apply {
            add(
                Model(
                    icon = R.drawable.ic_home_black_24dp,
                    id = 0,
                    text = R.string.menu_home
                )
            )
            add(
                Model(
                    icon = R.drawable.ic_pokeball,
                    id = 1,
                    text = R.string.menu_pokemon
                )
            )
        }
    }

    fun initBottonNavigation(
        binding: ActivityMainBinding,
        supportFragmentManager: FragmentManager
    ) {
        binding.bottomNavigationView.onMenuItemClick(0)

        binding.bottomNavigationView.setOnClickMenuListener {
            binding.bottomNavigationView.setBackgroundColor(Color.WHITE)
            when (it.id) {
                0 -> {
                    binding.tvTitle.text = "Inicio"
                    binding.llMain.visibility = View.VISIBLE
                    binding.fragmentMain.visibility = View.GONE
                    binding.bottomNavigationView.setBackgroundColor(Color.TRANSPARENT)
                    supportFragmentManager.popBackStackImmediate(
                        null,
                        FragmentManager.POP_BACK_STACK_INCLUSIVE
                    )
                }

                1 -> {
                    binding.tvTitle.text = "Pokemons"
                    replaceFragment(
                        binding,
                        supportFragmentManager,
                        PokemonListFragment().newInstance("Nómina")
                    )
                }
            }
        }
    }

    private fun replaceFragment(
        binding: ActivityMainBinding,
        supportFragmentManager: FragmentManager,
        fragment: Fragment
    ) {
        binding.llMain.visibility = View.GONE
        binding.fragmentMain.visibility = View.VISIBLE
        val fragmentManager = supportFragmentManager
        val fragmentTransation = fragmentManager.beginTransaction()
        fragmentTransation.replace(R.id.fragment_main, fragment)
        fragmentTransation.commit()
    }

    fun finishSession() {
        preferences.deleteUser()
        val intent = Intent(context, LoginActivity::class.java)
        context.startActivity(intent)
    }

}