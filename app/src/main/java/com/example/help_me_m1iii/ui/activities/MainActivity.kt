package com.example.help_me_m1iii.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import androidx.fragment.app.FragmentTransaction
import com.example.help_me_m1iii.R
import com.example.help_me_m1iii.ui.fragments.FavoriteFragments
import com.example.help_me_m1iii.ui.fragments.HomeFragments
import com.example.help_me_m1iii.ui.fragments.SettingsFragments
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    lateinit var homeFragments: HomeFragments
    lateinit var favoriteFragments: FavoriteFragments
    lateinit var settingsFragments: SettingsFragments

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigation : BottomNavigationView = findViewById(R.id.bottomNav)

        homeFragments = HomeFragments()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, homeFragments)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()

        bottomNavigation.setOnNavigationItemSelectedListener { item ->

            when (item.itemId) {
                R.id.home -> {
                    homeFragments = HomeFragments()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, homeFragments)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }
                R.id.favorite -> {
                    favoriteFragments = FavoriteFragments()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, favoriteFragments)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }
                R.id.settings -> {
                    settingsFragments = SettingsFragments()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, settingsFragments)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }

            }

            true
            }

        }

   }

